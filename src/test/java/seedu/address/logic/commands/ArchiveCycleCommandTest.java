package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.opportunity.Cycle;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.testutil.OpportunityBuilder;

/**
 * Contains integration tests and unit tests for {@code ArchiveCycleCommand}.
 */
public class ArchiveCycleCommandTest {

    private static final Cycle SUMMER_2026 = new Cycle("SUMMER 2026");
    private static final Cycle WINTER_2026 = new Cycle("WINTER 2026");

    @Test
    public void execute_matchingActiveCycle_archivesAllMatchingActiveOpportunities() {
        Opportunity summerFirst = new OpportunityBuilder()
                .withName("Summer One")
                .withEmail("summer.one@example.com")
                .withCompany("Alpha")
                .withCycle("SUMMER 2026")
                .build();
        Opportunity summerSecond = new OpportunityBuilder()
                .withName("Summer Two")
                .withEmail("summer.two@example.com")
                .withCompany("Beta")
                .withCycle("SUMMER 2026")
                .build();
        Opportunity winterOnly = new OpportunityBuilder()
                .withName("Winter One")
                .withEmail("winter.one@example.com")
                .withCompany("Gamma")
                .withCycle("WINTER 2026")
                .build();
        Opportunity archivedSummer = new OpportunityBuilder()
                .withName("Archived Summer")
                .withEmail("archived.summer@example.com")
                .withCompany("Delta")
                .withCycle("SUMMER 2026")
                .withArchived(true)
                .build();

        Model model = createModel(summerFirst, summerSecond, winterOnly, archivedSummer);
        model.updateFilteredOpportunityList(opportunity -> opportunity.getName().fullName.contains("Summer One"));

        ArchiveCycleCommand archiveCycleCommand = new ArchiveCycleCommand(SUMMER_2026);

        Opportunity archivedSummerFirst = summerFirst.archive();
        Opportunity archivedSummerSecond = summerSecond.archive();
        String expectedMessage = String.format(ArchiveCycleCommand.MESSAGE_ARCHIVE_CYCLE_SUCCESS,
                2, "opportunities", SUMMER_2026,
                "\n" + Messages.format(archivedSummerFirst)
                        + "\n" + Messages.format(archivedSummerSecond));

        Model expectedModel = createModel(summerFirst, summerSecond, winterOnly, archivedSummer);
        expectedModel.setOpportunity(summerFirst, archivedSummerFirst);
        expectedModel.setOpportunity(summerSecond, archivedSummerSecond);
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);

        expectedModel.commitAddressBook();
        assertCommandSuccess(archiveCycleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_whileViewingArchiveList_archivesMatchingActiveOpportunities() {
        Opportunity summerFirst = new OpportunityBuilder()
                .withName("Summer One")
                .withEmail("summer.one@example.com")
                .withCompany("Alpha")
                .withCycle("SUMMER 2026")
                .build();
        Opportunity winterOnly = new OpportunityBuilder()
                .withName("Winter One")
                .withEmail("winter.one@example.com")
                .withCompany("Gamma")
                .withCycle("WINTER 2026")
                .build();
        Opportunity archivedSummer = new OpportunityBuilder()
                .withName("Archived Summer")
                .withEmail("archived.summer@example.com")
                .withCompany("Delta")
                .withCycle("SUMMER 2026")
                .withArchived(true)
                .build();

        Model model = createModel(summerFirst, winterOnly, archivedSummer);
        model.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);

        ArchiveCycleCommand archiveCycleCommand = new ArchiveCycleCommand(SUMMER_2026);
        Opportunity archivedSummerFirst = summerFirst.archive();
        String expectedMessage = String.format(ArchiveCycleCommand.MESSAGE_ARCHIVE_CYCLE_SUCCESS,
                1, "opportunity", SUMMER_2026, "\n" + Messages.format(archivedSummerFirst));

        Model expectedModel = createModel(summerFirst, winterOnly, archivedSummer);
        expectedModel.setOpportunity(summerFirst, archivedSummerFirst);
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);

        expectedModel.commitAddressBook();
        assertCommandSuccess(archiveCycleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noMatchingActiveCycle_throwsCommandException() {
        Opportunity winterOnly = new OpportunityBuilder()
                .withName("Winter One")
                .withEmail("winter.one@example.com")
                .withCompany("Gamma")
                .withCycle("WINTER 2026")
                .build();
        Opportunity archivedSummer = new OpportunityBuilder()
                .withName("Archived Summer")
                .withEmail("archived.summer@example.com")
                .withCompany("Delta")
                .withCycle("SUMMER 2026")
                .withArchived(true)
                .build();

        Model model = createModel(winterOnly, archivedSummer);
        ArchiveCycleCommand archiveCycleCommand = new ArchiveCycleCommand(SUMMER_2026);

        assertCommandFailure(archiveCycleCommand, model,
                String.format(ArchiveCycleCommand.MESSAGE_NO_ACTIVE_OPPORTUNITIES_FOR_CYCLE, SUMMER_2026));
    }

    @Test
    public void equals() {
        ArchiveCycleCommand summerCommand = new ArchiveCycleCommand(SUMMER_2026);
        ArchiveCycleCommand summerCommandCopy = new ArchiveCycleCommand(new Cycle("SUMMER 2026"));
        ArchiveCycleCommand winterCommand = new ArchiveCycleCommand(WINTER_2026);

        assertTrue(summerCommand.equals(summerCommand));
        assertTrue(summerCommand.equals(summerCommandCopy));
        assertFalse(summerCommand.equals(1));
        assertFalse(summerCommand.equals(null));
        assertFalse(summerCommand.equals(winterCommand));
    }

    @Test
    public void toStringMethod() {
        ArchiveCycleCommand archiveCycleCommand = new ArchiveCycleCommand(SUMMER_2026);
        String expected = ArchiveCycleCommand.class.getCanonicalName() + "{targetCycle=" + SUMMER_2026 + "}";
        assertEquals(expected, archiveCycleCommand.toString());
    }

    private Model createModel(Opportunity... opportunities) {
        AddressBook addressBook = new AddressBook();
        for (Opportunity opportunity : opportunities) {
            addressBook.addOpportunity(opportunity);
        }
        return new ModelManager(addressBook, new UserPrefs());
    }

}
