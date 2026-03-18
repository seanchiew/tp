package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES;
import static seedu.address.testutil.TypicalOpportunities.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.testutil.OpportunityBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListArchiveCommand.
 */
public class ListArchiveCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noArchivedOpportunities_showsEmptyMessage() {
        // All typical opportunities are unarchived by default
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);
        assertCommandSuccess(new ListArchiveCommand(), model,
                ListArchiveCommand.MESSAGE_EMPTY, expectedModel);
    }

    @Test
    public void execute_someArchivedOpportunities_showsCorrectCount() {
        AddressBook ab = new AddressBook();
        Opportunity archived1 = new OpportunityBuilder().withName("Alice Tan")
                .withCompany("Stripe").withRole("SWE Intern").withArchived(true).build();
        Opportunity archived2 = new OpportunityBuilder().withName("Bob Lim")
                .withEmail("bob@tiktok.com").withCompany("TikTok")
                .withRole("Backend Intern").withArchived(true).build();
        Opportunity unarchived = new OpportunityBuilder().withName("Carl Ng")
                .withEmail("carl@apple.com").withCompany("Apple").withRole("iOS Intern").withArchived(false).build();
        ab.addOpportunity(archived1);
        ab.addOpportunity(archived2);
        ab.addOpportunity(unarchived);

        Model modelWithArchived = new ModelManager(ab, new UserPrefs());
        Model expectedModelWithArchived = new ModelManager(ab, new UserPrefs());
        expectedModelWithArchived.updateFilteredOpportunityList(
                PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);

        String expectedMessage = String.format(ListArchiveCommand.MESSAGE_SUCCESS, 2);
        assertCommandSuccess(new ListArchiveCommand(), modelWithArchived, expectedMessage, expectedModelWithArchived);
    }

    @Test
    public void execute_allOpportunitiesArchived_showsAllInArchive() {
        AddressBook ab = new AddressBook();
        Opportunity archived1 = new OpportunityBuilder().withName("Alice Tan")
                .withCompany("Stripe").withRole("SWE Intern").withArchived(true).build();
        Opportunity archived2 = new OpportunityBuilder().withName("Bob Lim")
                .withEmail("bob@tiktok.com").withCompany("TikTok")
                .withRole("Backend Intern").withArchived(true).build();
        ab.addOpportunity(archived1);
        ab.addOpportunity(archived2);

        Model allArchivedModel = new ModelManager(ab, new UserPrefs());
        Model expectedAllArchivedModel = new ModelManager(ab, new UserPrefs());
        expectedAllArchivedModel.updateFilteredOpportunityList(
                PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);

        String expectedMessage = String.format(ListArchiveCommand.MESSAGE_SUCCESS, 2);
        assertCommandSuccess(new ListArchiveCommand(), allArchivedModel, expectedMessage, expectedAllArchivedModel);
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
        // Simulate an active find filter showing only unarchived
        filteredModel.updateFilteredOpportunityList(
                seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);

        Model expectedFilteredModel = new ModelManager(ab, new UserPrefs());
        expectedFilteredModel.updateFilteredOpportunityList(
                PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);

        String expectedMessage = String.format(ListArchiveCommand.MESSAGE_SUCCESS, 1);
        assertCommandSuccess(new ListArchiveCommand(), filteredModel, expectedMessage, expectedFilteredModel);
    }

    @Test
    public void execute_emptyAddressBook_showsEmptyMessage() {
        Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        Model expectedEmptyModel = new ModelManager(new AddressBook(), new UserPrefs());
        assertCommandSuccess(new ListArchiveCommand(), emptyModel,
                ListArchiveCommand.MESSAGE_EMPTY, expectedEmptyModel);
    }
}
