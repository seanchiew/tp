package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOpportunityAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OPPORTUNITY;
import static seedu.address.testutil.TypicalOpportunities.getTypicalAddressBook;
import static seedu.address.testutil.TypicalOpportunities.getTypicalOpportunities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        int count = getTypicalOpportunities().size();
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, count, Messages.getOpportunityWord(count));
        assertCommandSuccess(new ListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showOpportunityAtIndex(model, INDEX_FIRST_OPPORTUNITY);
        int count = getTypicalOpportunities().size();
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS, count, Messages.getOpportunityWord(count));
        assertCommandSuccess(new ListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyList_showsEmptyMessage() {
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedEmptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        assertCommandSuccess(new ListCommand(), emptyModel, ListCommand.MESSAGE_EMPTY, expectedEmptyModel);
    }
}
