package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOpportunities.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        // Simulate a mutating command being executed prior to the test
        model.deleteOpportunity(model.getFilteredOpportunityList().get(0));
        model.commitAddressBook();
    }

    @Test
    public void execute_previousStateExists_success() {
        // EP: Model has at least one undoable state

        // Defensively align expectedModel's history state with the live model
        expectedModel.deleteOpportunity(expectedModel.getFilteredOpportunityList().get(0));
        expectedModel.commitAddressBook();

        // Explicitly undo expectedModel so it becomes the exact target state
        expectedModel.undoAddressBook();

        // Execute the command
        UndoCommand undoCommand = new UndoCommand();
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noPreviousState_throwsCommandException() {
        // BVA: Model has exactly zero undoable states (boundary value)
        model.undoAddressBook(); // Empty the state list pointer back to 0
        UndoCommand undoCommand = new UndoCommand();
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
}
