package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOpportunityAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;
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
import seedu.address.model.opportunity.Opportunity;
import seedu.address.testutil.OpportunityBuilder;

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
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_ACTIVE,
                                    count, Messages.getOpportunityWord(count));
        assertCommandSuccess(new ListCommand(false), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showOpportunityAtIndex(model, INDEX_FIRST_OPPORTUNITY);
        int count = getTypicalOpportunities().size();
        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_ACTIVE,
                                    count, Messages.getOpportunityWord(count));
        assertCommandSuccess(new ListCommand(false), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyList_showsEmptyMessage() {
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedEmptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        assertCommandSuccess(new ListCommand(false), emptyModel,
                                    ListCommand.MESSAGE_EMPTY_ACTIVE, expectedEmptyModel);
    }

    @Test
    public void execute_noArchivedOpportunities_showsEmptyMessage() {
        expectedModel.setArchiveView(true);
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);
        assertCommandSuccess(new ListCommand(true), model,
                                    ListCommand.MESSAGE_EMPTY_ARCHIVED, expectedModel);
    }

    @Test
    public void execute_someArchivedOpportunities_showsCorrectCount() {
        AddressBook ab = new AddressBook();
        Opportunity archived1 = new OpportunityBuilder().withName("Alice Tan")
                .withCompany("Stripe").withRole("SWE Intern")
                .withArchived(true).build();
        Opportunity archived2 = new OpportunityBuilder().withName("Bob Lim")
                .withEmail("bob@tiktok.com").withCompany("TikTok")
                .withRole("Backend Intern").withArchived(true).build();

        ab.addOpportunity(archived1);
        ab.addOpportunity(archived2);

        Model testModel = new ModelManager(ab, new UserPrefs());
        Model expectedTestModel = new ModelManager(ab, new UserPrefs());
        expectedTestModel.setArchiveView(true);
        expectedTestModel.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);

        assertCommandSuccess(new ListCommand(true), testModel,
                            String.format(ListCommand.MESSAGE_SUCCESS_ARCHIVED, 2,
                                Messages.getOpportunityWord(2)), expectedTestModel);
    }

    @Test
    public void execute_afterFilteredView_showsOnlyArchived() {
        // Simulate user having run a find command (filtered view), then running list archive
        AddressBook ab = new AddressBook();
        Opportunity archived = new OpportunityBuilder().withName("Alice Tan")
                .withCompany("Stripe").withRole("SWE Intern").withArchived(true).build();
        Opportunity unarchived = new OpportunityBuilder().withName("Bob Lim")
                .withEmail("bob@tiktok.com").withCompany("TikTok")
                .withRole("Backend Intern").withArchived(false).build();
        ab.addOpportunity(archived);
        ab.addOpportunity(unarchived);

        Model filteredModel = new ModelManager(ab, new UserPrefs());
        // Simulate an active find filter showing only unarchived opportunities
        filteredModel.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);

        Model expectedFilteredModel = new ModelManager(ab, new UserPrefs());
        expectedFilteredModel.setArchiveView(true);
        expectedFilteredModel.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);

        // list archive should reset the filter and show only archived opportunities
        assertCommandSuccess(new ListCommand(true), filteredModel,
                            String.format(ListCommand.MESSAGE_SUCCESS_ARCHIVED, 1,
                                Messages.getOpportunityWord(1)), expectedFilteredModel);
    }

}
