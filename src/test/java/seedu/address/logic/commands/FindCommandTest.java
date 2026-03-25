package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_OPPORTUNITIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;
import static seedu.address.testutil.TypicalOpportunities.ALICE;
import static seedu.address.testutil.TypicalOpportunities.BENSON;
import static seedu.address.testutil.TypicalOpportunities.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.OpportunityContainsSubstringPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.OpportunityBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        OpportunityContainsSubstringPredicate firstPredicate =
                new OpportunityContainsSubstringPredicate(Collections.singletonList("first"));
        OpportunityContainsSubstringPredicate secondPredicate =
                new OpportunityContainsSubstringPredicate(Collections.singletonList("first"),
                        Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different opportunity -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // different archive scope -> returns false
        assertFalse(findFirstCommand.equals(new FindCommand(firstPredicate, true)));
    }

    @Test
    public void execute_zeroKeywords_noOpportunityFound() {
        String expectedMessage = String.format(MESSAGE_OPPORTUNITIES_LISTED_OVERVIEW, 0);
        OpportunityContainsSubstringPredicate predicate = new OpportunityContainsSubstringPredicate(
                Collections.emptyList());
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOpportunityList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredOpportunityList());
    }

    @Test
    public void execute_matchingNameKeywords_opportunityFound() {
        String expectedMessage = String.format(MESSAGE_OPPORTUNITIES_LISTED_OVERVIEW, 1);
        OpportunityContainsSubstringPredicate predicate = preparePredicate("Ali Tan");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOpportunityList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredOpportunityList());
    }

    @Test
    public void execute_partialKeyword_opportunityFound() {
        String expectedMessage = String.format(MESSAGE_OPPORTUNITIES_LISTED_OVERVIEW, 1);
        OpportunityContainsSubstringPredicate predicate = preparePredicate("lic");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOpportunityList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredOpportunityList());
    }

    @Test
    public void execute_companyOnlyKeyword_opportunityFound() {
        String expectedMessage = String.format(MESSAGE_OPPORTUNITIES_LISTED_OVERVIEW, 1);
        OpportunityContainsSubstringPredicate predicate =
                new OpportunityContainsSubstringPredicate(Collections.emptyList(), List.of("Tik"));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOpportunityList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BENSON), model.getFilteredOpportunityList());
    }

    @Test
    public void execute_nameAndCompanyKeywords_matchesBothFilters() {
        String expectedMessage = String.format(MESSAGE_OPPORTUNITIES_LISTED_OVERVIEW, 1);
        OpportunityContainsSubstringPredicate predicate =
                new OpportunityContainsSubstringPredicate(List.of("Ben"), List.of("Tik"));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredOpportunityList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(BENSON), model.getFilteredOpportunityList());
    }

    @Test
    public void execute_matchingArchivedAndUnarchivedKeywords_returnsOnlyUnarchivedByDefault() {
        Opportunity archivedJan = new OpportunityBuilder()
                .withName("Janice Tan")
                .withEmail("janice@meta.com")
                .withCompany("Meta")
                .withRole("Data Intern")
                .withArchived(true)
                .build();
        Opportunity activeJan = new OpportunityBuilder()
                .withName("Jan Koh")
                .withEmail("jan@google.com")
                .withCompany("Google")
                .withRole("SWE Intern")
                .build();
        Model scopedModel = new ModelManager(new AddressBookBuilder()
                .withOpportunity(archivedJan)
                .withOpportunity(activeJan)
                .build(), new UserPrefs());
        Model expectedScopedModel = new ModelManager(scopedModel.getAddressBook(), new UserPrefs());
        OpportunityContainsSubstringPredicate predicate = preparePredicate("jan");
        FindCommand command = new FindCommand(predicate);

        expectedScopedModel.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES.and(predicate));
        assertCommandSuccess(command, scopedModel,
                String.format(MESSAGE_OPPORTUNITIES_LISTED_OVERVIEW, 1), expectedScopedModel);
        assertEquals(Collections.singletonList(activeJan), scopedModel.getFilteredOpportunityList());
    }

    @Test
    public void execute_archivedSearch_returnsOnlyArchivedMatches() {
        Opportunity archivedJan = new OpportunityBuilder()
                .withName("Janice Tan")
                .withEmail("janice@meta.com")
                .withCompany("Meta")
                .withRole("Data Intern")
                .withArchived(true)
                .build();
        Opportunity activeJan = new OpportunityBuilder()
                .withName("Jan Koh")
                .withEmail("jan@google.com")
                .withCompany("Google")
                .withRole("SWE Intern")
                .build();
        Model scopedModel = new ModelManager(new AddressBookBuilder()
                .withOpportunity(archivedJan)
                .withOpportunity(activeJan)
                .build(), new UserPrefs());
        Model expectedScopedModel = new ModelManager(scopedModel.getAddressBook(), new UserPrefs());
        OpportunityContainsSubstringPredicate predicate = preparePredicate("jan");
        FindCommand command = new FindCommand(predicate, true);

        expectedScopedModel.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES.and(predicate));
        assertCommandSuccess(command, scopedModel,
                String.format(MESSAGE_OPPORTUNITIES_LISTED_OVERVIEW, 1), expectedScopedModel);
        assertEquals(Collections.singletonList(archivedJan), scopedModel.getFilteredOpportunityList());
    }

    @Test
    public void toStringMethod() {
        OpportunityContainsSubstringPredicate predicate =
                new OpportunityContainsSubstringPredicate(Arrays.asList("keyword"), Arrays.asList("company"));
        FindCommand findCommand = new FindCommand(predicate, true);
        String expected = FindCommand.class.getCanonicalName()
                + "{predicate=" + predicate + ", searchArchived=true}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code OpportunityContainsSubstringPredicate}.
     */
    private OpportunityContainsSubstringPredicate preparePredicate(String userInput) {
        return new OpportunityContainsSubstringPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
