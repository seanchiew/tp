package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
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

    public static final String MESSAGE_UNARCHIVE_OPPORTUNITY_SUCCESS = "Unarchived %1$d %2$s:%3$s";

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

        List<Opportunity> displayedArchivedOpportunities = new ArrayList<>(model.getFilteredOpportunityList());

        // Sort indices in descending order so the success message lists later indices first.
        List<Index> sortedIndices = IndexCommandUtil.getIndicesInDescendingOrder(targetIndices);
        List<Opportunity> opportunitiesToUnarchive =
                IndexCommandUtil.getItemsAtIndices(
                        sortedIndices, displayedArchivedOpportunities,
                        Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);

        StringBuilder unarchivedOpportunities = new StringBuilder();

        for (Opportunity opportunityToUnarchive : opportunitiesToUnarchive) {
            Opportunity unarchivedOpportunity = opportunityToUnarchive.unarchive();

            model.setOpportunity(opportunityToUnarchive, unarchivedOpportunity);
            unarchivedOpportunities.append(String.format("\n%1$s", Messages.format(unarchivedOpportunity)));
        }
        model.commitAddressBook();
        int count = sortedIndices.size();
        return new CommandResult(
                String.format(MESSAGE_UNARCHIVE_OPPORTUNITY_SUCCESS, count,
                        Messages.getOpportunityWord(count), unarchivedOpportunities.toString()));
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
