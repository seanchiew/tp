package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.opportunity.Opportunity;

/**
 * Unarchives one or more opportunities identified using their displayed indices from the tracker.
 */
public class UnarchiveCommand extends Command {

    public static final String COMMAND_WORD = "unarchive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unarchives the opportunity identified by the index number used in the displayed opportunity list.\n"
            + "Parameters: INDEX [MORE_INDICES]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_UNARCHIVE_OPPORTUNITY_SUCCESS = "Unarchived Opportunity:%1$s";

    public static final String MESSAGE_NOT_IN_ARCHIVE_VIEW =
            "You are not viewing the archive. Use 'list archive' first to see archived opportunities.";

    private final List<Index> targetIndices;

    public UnarchiveCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.isArchiveView()) {
            throw new CommandException(MESSAGE_NOT_IN_ARCHIVE_VIEW);
        }

        if (new HashSet<>(targetIndices).size() != targetIndices.size()) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_INDICES);
        }

        List<Opportunity> displayedArchivedOpportunities = new ArrayList<>(model.getFilteredOpportunityList());

        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= displayedArchivedOpportunities.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
            }
        }

        // Sort indices in descending order to prevent index mismatch
        // due to shifting when unarchiving multiple opportunities
        List<Index> sortedIndices = new ArrayList<>(targetIndices);
        sortedIndices.sort((index1, index2) -> index2.getZeroBased() - index1.getZeroBased());

        StringBuilder unarchivedOpportunities = new StringBuilder();

        for (Index targetIndex : sortedIndices) {
            Opportunity opportunityToUnarchive = displayedArchivedOpportunities.get(targetIndex.getZeroBased());
            Opportunity unarchivedOpportunity = opportunityToUnarchive.unarchive();

            model.setOpportunity(opportunityToUnarchive, unarchivedOpportunity);
            unarchivedOpportunities.append(String.format("\n%1$s", Messages.format(unarchivedOpportunity)));
        }
        model.commitAddressBook();
        return new CommandResult(
                String.format(MESSAGE_UNARCHIVE_OPPORTUNITY_SUCCESS, unarchivedOpportunities.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnarchiveCommand)) {
            return false;
        }

        UnarchiveCommand otherUnarchiveCommand = (UnarchiveCommand) other;
        return targetIndices.equals(otherUnarchiveCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }
}
