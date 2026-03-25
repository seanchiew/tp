package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOpportunityAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OPPORTUNITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OPPORTUNITY;
import static seedu.address.testutil.TypicalOpportunities.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.opportunity.Opportunity;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Opportunity opportunityToDelete = model.getFilteredOpportunityList()
                .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_OPPORTUNITY));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_OPPORTUNITY_SUCCESS,
                "\n" + Messages.format(opportunityToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteOpportunity(opportunityToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOpportunityList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showOpportunityAtIndex(model, INDEX_FIRST_OPPORTUNITY);

        Opportunity opportunityToDelete = model.getFilteredOpportunityList()
                .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_OPPORTUNITY));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_OPPORTUNITY_SUCCESS,
                "\n" + Messages.format(opportunityToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteOpportunity(opportunityToDelete);
        showNoOpportunity(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showOpportunityAtIndex(model, INDEX_FIRST_OPPORTUNITY);

        Index outOfBoundIndex = INDEX_SECOND_OPPORTUNITY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getOpportunityList().size());

        DeleteCommand deleteCommand = new DeleteCommand(List.of(outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_multipleValidIndicesUnfilteredList_success() {
        Opportunity firstOpportunityToDelete = model.getFilteredOpportunityList()
                                        .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity secondOpportunityToDelete = model.getFilteredOpportunityList()
                                        .get(INDEX_SECOND_OPPORTUNITY.getZeroBased());

        // Pass indices in ascending order to test the descending sort in execute()
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_OPPORTUNITY, INDEX_SECOND_OPPORTUNITY));

        // The expected message will have them in descending order because of the sorting implementation
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_OPPORTUNITY_SUCCESS,
                                        "\n" + Messages.format(secondOpportunityToDelete) + "\n"
                                                                        + Messages.format(firstOpportunityToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        // Delete in descending order to match expectedModel state execution safely
        expectedModel.deleteOpportunity(secondOpportunityToDelete);
        expectedModel.deleteOpportunity(firstOpportunityToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validAndInvalidIndicesUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOpportunityList().size() + 1);
        // Should throw exception as long as one index is invalid
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_OPPORTUNITY, outOfBoundIndex));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateIndicesUnfilteredList_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(List.of(INDEX_FIRST_OPPORTUNITY, INDEX_FIRST_OPPORTUNITY));

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_DUPLICATE_INDICES);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(List.of(INDEX_FIRST_OPPORTUNITY));
        DeleteCommand deleteSecondCommand = new DeleteCommand(List.of(INDEX_SECOND_OPPORTUNITY));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(List.of(INDEX_FIRST_OPPORTUNITY));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different opportunity -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(List.of(targetIndex));
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndices=" + List.of(targetIndex) + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoOpportunity(Model model) {
        model.updateFilteredOpportunityList(p -> false);

        assertTrue(model.getFilteredOpportunityList().isEmpty());
    }
}
