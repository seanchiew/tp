package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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

    public static final String MESSAGE_DELETE_OPPORTUNITY_SUCCESS = "Deleted %1$d %2$s:%3$s";

    private final List<Index> targetIndices;

    public DeleteCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Opportunity> lastShownList = model.getFilteredOpportunityList();

        // Sort indices in descending order so the success message lists later indices first.
        List<Index> sortedIndices = IndexCommandUtil.getIndicesInDescendingOrder(targetIndices);
        List<Opportunity> opportunitiesToDelete = IndexCommandUtil.getItemsAtIndices(
                sortedIndices, lastShownList, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);

        StringBuilder deletedOpportunities = new StringBuilder();
        for (Opportunity opportunityToDelete : opportunitiesToDelete) {
            model.deleteOpportunity(opportunityToDelete);
            deletedOpportunities.append(String.format("\n%1$s", Messages.format(opportunityToDelete)));
        }
        model.commitAddressBook();
        int count = sortedIndices.size();
        return new CommandResult(
                String.format(MESSAGE_DELETE_OPPORTUNITY_SUCCESS, count,
                        Messages.getOpportunityWord(count), deletedOpportunities.toString()));
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
