package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;

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
 * Archives one or more opportunities identified using their displayed indices from the tracker.
 */
public class ArchiveCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Archives active opportunities either by their displayed indices or by cycle.\n"
            + "Parameters: INDEX [MORE_INDICES]... (must be positive integers)\n"
            + "            or: cycle CYCLE\n"
            + "Examples: " + COMMAND_WORD + " 1 2 3\n"
            + "          " + COMMAND_WORD + " cycle SUMMER 2026\n"
            + "          " + COMMAND_WORD + " cycle S2 2026";

    public static final String MESSAGE_ARCHIVE_OPPORTUNITY_SUCCESS = "Archived Opportunity:%1$s";

    public static final String MESSAGE_NOT_IN_UNARCHIVED_VIEW =
            "You are viewing the archive. Use 'list' first to see unarchived opportunities.";

    private final List<Index> targetIndices;

    public ArchiveCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isArchiveView()) {
            throw new CommandException(MESSAGE_NOT_IN_UNARCHIVED_VIEW);
        }

        if (new HashSet<>(targetIndices).size() != targetIndices.size()) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_INDICES);
        }

        List<Opportunity> lastShownList = model.getFilteredOpportunityList();
        List<Opportunity> opportunitiesToArchive = new ArrayList<>();

        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
            }
            opportunitiesToArchive.add(lastShownList.get(targetIndex.getZeroBased()));
        }

        StringBuilder archivedOpportunities = new StringBuilder();
        for (Opportunity opportunityToArchive : opportunitiesToArchive) {
            Opportunity archivedOpportunity = opportunityToArchive.archive();
            model.setOpportunity(opportunityToArchive, archivedOpportunity);

            archivedOpportunities.append(String.format("\n%1$s", Messages.format(archivedOpportunity)));
        }

        model.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);
        model.commitAddressBook();
        return new CommandResult(
                String.format(MESSAGE_ARCHIVE_OPPORTUNITY_SUCCESS, archivedOpportunities.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ArchiveCommand)) {
            return false;
        }

        ArchiveCommand otherArchiveCommand = (ArchiveCommand) other;
        return targetIndices.equals(otherArchiveCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }
}
