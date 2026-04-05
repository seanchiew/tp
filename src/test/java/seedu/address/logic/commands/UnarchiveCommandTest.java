package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OPPORTUNITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OPPORTUNITY;
import static seedu.address.testutil.TypicalOpportunities.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.OpportunityContainsSubstringPredicate;
import seedu.address.testutil.OpportunityBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnarchiveCommand}.
 */
public class UnarchiveCommandTest {

    @Test
    public void execute_validIndexDisplayedArchivedList_success() {
        Model model = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY);

        Opportunity opportunityToUnarchive = getDisplayedArchivedOpportunities(model)
                .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity unarchivedOpportunity = opportunityToUnarchive.unarchive();

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_OPPORTUNITY_SUCCESS,
                "\n" + Messages.format(unarchivedOpportunity));

        Model expectedModel = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY);
        expectedModel.setOpportunity(opportunityToUnarchive, unarchivedOpportunity);
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);

        expectedModel.commitAddressBook();
        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexDisplayedArchivedList_throwsCommandException() {
        Model model = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY);
        Index outOfBoundIndex = Index.fromOneBased(getDisplayedArchivedOpportunities(model).size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(List.of(outOfBoundIndex));

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_multipleValidIndicesDisplayedArchivedList_success() {
        Model model = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY, INDEX_SECOND_OPPORTUNITY);

        Opportunity firstOpportunityToUnarchive = getDisplayedArchivedOpportunities(model)
                .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity secondOpportunityToUnarchive = getDisplayedArchivedOpportunities(model)
                .get(INDEX_SECOND_OPPORTUNITY.getZeroBased());

        Opportunity unarchivedFirstOpportunity = firstOpportunityToUnarchive.unarchive();
        Opportunity unarchivedSecondOpportunity = secondOpportunityToUnarchive.unarchive();

        // Pass indices in ascending order to test the descending sort in execute()
        UnarchiveCommand unarchiveCommand =
                new UnarchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY, INDEX_SECOND_OPPORTUNITY));

        // The expected message will have them in descending order because of the sorting implementation
        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_OPPORTUNITY_SUCCESS,
                "\n" + Messages.format(unarchivedSecondOpportunity)
                        + "\n" + Messages.format(unarchivedFirstOpportunity));

        Model expectedModel = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY, INDEX_SECOND_OPPORTUNITY);

        // Archive in descending order to match expectedModel state execution safely
        expectedModel.setOpportunity(secondOpportunityToUnarchive, unarchivedSecondOpportunity);
        expectedModel.setOpportunity(firstOpportunityToUnarchive, unarchivedFirstOpportunity);
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);

        expectedModel.commitAddressBook();
        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validAndInvalidIndicesDisplayedArchivedList_throwsCommandException() {
        Model model = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY);
        Index outOfBoundIndex = Index.fromOneBased(getDisplayedArchivedOpportunities(model).size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY, outOfBoundIndex));

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_archivedFindView_usesDisplayedArchivedResult() {
        Model model = createModelWithArchivedSearchResults();
        Opportunity opportunityToUnarchive = model.getFilteredOpportunityList()
                .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity unarchivedOpportunity = opportunityToUnarchive.unarchive();

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_OPPORTUNITY_SUCCESS,
                "\n" + Messages.format(unarchivedOpportunity));

        Model expectedModel = createModelWithArchivedSearchResults();
        expectedModel.setOpportunity(opportunityToUnarchive, unarchivedOpportunity);

        expectedModel.commitAddressBook();
        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
        assertTrue(model.getFilteredOpportunityList().isEmpty());
    }

    @Test
    public void execute_archivedFindViewOutOfBounds_throwsCommandException() {
        Model model = createModelWithArchivedSearchResults();
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(List.of(INDEX_SECOND_OPPORTUNITY));

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateIndicesFullStorageArchivedList_throwsCommandException() {
        Model model = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY);
        UnarchiveCommand unarchiveCommand =
                new UnarchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY, INDEX_FIRST_OPPORTUNITY));

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_DUPLICATE_INDICES);
    }

    @Test
    public void execute_notInArchiveView_throwsCommandException() {
        // Model on main (unarchived) view — guard should trigger
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));

        assertCommandFailure(unarchiveCommand, model, UnarchiveCommand.MESSAGE_NOT_IN_ARCHIVE_VIEW);
    }

    @Test
    public void execute_notInArchiveViewWithArchivedEntries_throwsCommandException() {
        // Has archived entries in storage but user is on unarchived view — guard should still trigger
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Opportunity original = model.getAddressBook().getOpportunityList().get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        model.setOpportunity(original, original.archive());
        // Stays on unarchived view (default filter)

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));

        assertCommandFailure(unarchiveCommand, model, UnarchiveCommand.MESSAGE_NOT_IN_ARCHIVE_VIEW);
    }

    @Test
    public void equals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(List.of(INDEX_SECOND_OPPORTUNITY));

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        UnarchiveCommand unarchiveFirstCommandCopy = new UnarchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different opportunity -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(List.of(targetIndex));
        String expected = UnarchiveCommand.class.getCanonicalName() + "{targetIndices=" + List.of(targetIndex) + "}";
        assertEquals(expected, unarchiveCommand.toString());
    }

    /**
     * Creates a model where the given one-based indices are archived.
     */
    private Model createModelWithArchivedOpportunities(Index... indicesToArchive) {
        Model model = new ModelManager(new AddressBook(getTypicalAddressBook()), new UserPrefs());

        for (Index index : indicesToArchive) {
            Opportunity originalOpportunity = model.getAddressBook().getOpportunityList().get(index.getZeroBased());
            Opportunity archivedOpportunity = originalOpportunity.archive();
            model.setOpportunity(originalOpportunity, archivedOpportunity);
        }

        model.setArchiveView(true);
        model.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);
        return model;
    }

    /**
     * Returns a snapshot of the currently displayed archived opportunities.
     */
    private List<Opportunity> getDisplayedArchivedOpportunities(Model model) {
        return List.copyOf(model.getFilteredOpportunityList());
    }

    /**
     * Creates a model with multiple archived opportunities and an archived search view showing a subset.
     */
    private Model createModelWithArchivedSearchResults() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        Opportunity archivedAlice = new OpportunityBuilder()
                .withName("Alice Tan")
                .withEmail("alice@stripe.com")
                .withCompany("Stripe")
                .withRole("SWE Intern")
                .withArchived(true)
                .build();
        Opportunity archivedBenson = new OpportunityBuilder()
                .withName("Benson Yeo")
                .withEmail("benson@tiktok.com")
                .withCompany("TikTok")
                .withRole("Backend Engineer")
                .withArchived(true)
                .build();
        Opportunity activeBenson = new OpportunityBuilder()
                .withName("Benson Active")
                .withEmail("benson.active@tiktok.com")
                .withCompany("TikTok")
                .withRole("Frontend Engineer")
                .build();

        model.addOpportunity(archivedAlice);
        model.addOpportunity(archivedBenson);
        model.addOpportunity(activeBenson);

        model.setArchiveView(true);
        OpportunityContainsSubstringPredicate predicate =
                new OpportunityContainsSubstringPredicate(List.of("Ben"), List.of("Tik"));
        model.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES.and(predicate));
        return model;
    }
}
