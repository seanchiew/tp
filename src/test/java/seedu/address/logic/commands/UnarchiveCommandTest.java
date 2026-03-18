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

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnarchiveCommand}.
 */
public class UnarchiveCommandTest {

    @Test
    public void execute_validIndexFullStorageArchivedList_success() {
        Model model = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY);

        Opportunity opportunityToUnarchive = getArchivedOpportunities(model)
                .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity unarchivedOpportunity = createUnarchivedOpportunity(opportunityToUnarchive);

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_OPPORTUNITY_SUCCESS,
                "\n" + Messages.format(unarchivedOpportunity));

        Model expectedModel = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY);
        expectedModel.setOpportunity(opportunityToUnarchive, unarchivedOpportunity);
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFullStorageArchivedList_throwsCommandException() {
        Model model = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY);
        // ensures that outOfBoundIndex is still in bounds of address book list
        Index outOfBoundIndex = Index.fromOneBased(getArchivedOpportunities(model).size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(List.of(outOfBoundIndex));

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_multipleValidIndicesFullStorageArchivedList_success() {
        Model model = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY, INDEX_SECOND_OPPORTUNITY);

        Opportunity firstOpportunityToUnarchive = getArchivedOpportunities(model)
                .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity secondOpportunityToUnarchive = getArchivedOpportunities(model)
                .get(INDEX_SECOND_OPPORTUNITY.getZeroBased());

        Opportunity unarchivedFirstOpportunity = createUnarchivedOpportunity(firstOpportunityToUnarchive);
        Opportunity unarchivedSecondOpportunity = createUnarchivedOpportunity(secondOpportunityToUnarchive);

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

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validAndInvalidIndicesFullStorageArchivedList_throwsCommandException() {
        Model model = createModelWithArchivedOpportunities(INDEX_FIRST_OPPORTUNITY);
        // Should throw exception as long as one index is invalid
        Index outOfBoundIndex = Index.fromOneBased(getArchivedOpportunities(model).size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY, outOfBoundIndex));

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
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
        model.setOpportunity(original, createArchivedOpportunity(original));
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
            Opportunity archivedOpportunity = createArchivedOpportunity(originalOpportunity);
            model.setOpportunity(originalOpportunity, archivedOpportunity);
        }

        model.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);
        return model;
    }

    /**
     * Returns a list of archived opportunities from storage.
     *
     * @param model the model to retrieve archived opportunities from
     * @return a list of archived opportunities from storage
     */
    private List<Opportunity> getArchivedOpportunities(Model model) {
        return model.getAddressBook().getOpportunityList().stream()
                .filter(Opportunity::isArchived)
                .toList();
    }

    /**
     * Creates an archived copy of the given opportunity.
     *
     * @param opportunityToArchive the opportunity to archive
     * @return an archived copy of the given opportunity
     */
    private Opportunity createArchivedOpportunity(Opportunity opportunity) {
        return new Opportunity(
                opportunity.getName(),
                opportunity.getEmail(),
                opportunity.getContactRole(),
                opportunity.getCompany(),
                opportunity.getRole(),
                opportunity.getStatus(),
                true,
                opportunity.getPhone().orElse(null)
        );
    }

    /**
     * Creates an unarchived copy of the given opportunity.
     *
     * @param opportunity the opportunity to unarchive
     * @return an unarchived copy of the given opportunity
     */
    private Opportunity createUnarchivedOpportunity(Opportunity opportunity) {
        return new Opportunity(
                opportunity.getName(),
                opportunity.getEmail(),
                opportunity.getContactRole(),
                opportunity.getCompany(),
                opportunity.getRole(),
                opportunity.getStatus(),
                false,
                opportunity.getPhone().orElse(null)
        );
    }
}
