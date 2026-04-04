package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;
import static seedu.address.testutil.TypicalOpportunities.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.testutil.OpportunityBuilder;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        expectedModel.commitAddressBook();
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);

        expectedModel.commitAddressBook();
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_clearWhileInArchiveView_preservesArchiveView() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Simulate user having run "list archive" prior to clear
        model.setArchiveView(true);
        model.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);
        expectedModel.setArchiveView(true);
        expectedModel.setAddressBook(new AddressBook());
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);

        expectedModel.commitAddressBook();
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
        assertTrue(model.isArchiveView());

        // Verify the archive predicate was preserved by loading data back via setAddressBook
        // (bypasses addOpportunity, which has its own predicate reset side-effect)
        Opportunity unarchived = new OpportunityBuilder().build();
        Opportunity archived = new OpportunityBuilder()
                .withCompany("ArchivedCo").withRole("Archived Role").withArchived(true).build();
        AddressBook bookWithBoth = new AddressBook();
        bookWithBoth.addOpportunity(unarchived);
        bookWithBoth.addOpportunity(archived);
        model.setAddressBook(bookWithBoth);

        // After clear the predicate must remain PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES,
        // so only the archived opportunity should be visible
        assertEquals(1, model.getFilteredOpportunityList().size());
        assertTrue(model.getFilteredOpportunityList().get(0).isArchived());
    }

}
