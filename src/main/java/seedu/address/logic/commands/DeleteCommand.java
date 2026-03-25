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
 * Deletes one or more opportunities identified using their displayed indices from the tracker.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the opportunity identified by the index number used in the displayed opportunity list.\n"
            + "Parameters: INDEX [MORE_INDICES]... (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 2 3";

    public static final String MESSAGE_DELETE_OPPORTUNITY_SUCCESS = "Deleted Opportunity: %1$s";

    private final List<Index> targetIndices;

    public DeleteCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (new HashSet<>(targetIndices).size() != targetIndices.size()) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_INDICES);
        }

        List<Opportunity> lastShownList = model.getFilteredOpportunityList();

        for (Index targetIndex : targetIndices) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
            }
        }

        // Sort indices in descending order to prvent shifting index trap
        List<Index> sortedIndices = new ArrayList<>(targetIndices);
        sortedIndices.sort((index1, index2) -> index2.getZeroBased() - index1.getZeroBased());

        StringBuilder deletedOpportunities = new StringBuilder();
        for (Index targetIndex : sortedIndices) {
            Opportunity opportunityToDelete = lastShownList.get(targetIndex.getZeroBased());
            model.deleteOpportunity(opportunityToDelete);
            deletedOpportunities.append(String.format("\n%1$s", Messages.format(opportunityToDelete)));
        }

        return new CommandResult(
                String.format(MESSAGE_DELETE_OPPORTUNITY_SUCCESS, deletedOpportunities.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndices.equals(otherDeleteCommand.targetIndices);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .toString();
    }
}
