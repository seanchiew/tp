package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showOpportunityAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;
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
 * {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Opportunity opportunityToArchive = model.getFilteredOpportunityList()
                .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity archivedOpportunity = opportunityToArchive.archive();
        ArchiveCommand archiveCommand = new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_OPPORTUNITY_SUCCESS,
                "\n" + Messages.format(archivedOpportunity));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOpportunity(opportunityToArchive, archivedOpportunity);
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);

        expectedModel.commitAddressBook();
        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOpportunityList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(List.of(outOfBoundIndex));

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showOpportunityAtIndex(model, INDEX_FIRST_OPPORTUNITY);

        Opportunity opportunityToArchive = model.getFilteredOpportunityList()
                .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity archivedOpportunity = opportunityToArchive.archive();
        ArchiveCommand archiveCommand = new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_OPPORTUNITY_SUCCESS,
                "\n" + Messages.format(archivedOpportunity));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setOpportunity(opportunityToArchive, archivedOpportunity);
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);

        expectedModel.commitAddressBook();
        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showOpportunityAtIndex(model, INDEX_FIRST_OPPORTUNITY);

        Index outOfBoundIndex = INDEX_SECOND_OPPORTUNITY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getOpportunityList().size());

        ArchiveCommand archiveCommand = new ArchiveCommand(List.of(outOfBoundIndex));

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_multipleValidIndicesUnfilteredList_success() {
        Opportunity firstOpportunityToArchive = model.getFilteredOpportunityList()
                .get(INDEX_FIRST_OPPORTUNITY.getZeroBased());
        Opportunity secondOpportunityToArchive = model.getFilteredOpportunityList()
                .get(INDEX_SECOND_OPPORTUNITY.getZeroBased());

        Opportunity archivedFirstOpportunity = firstOpportunityToArchive.archive();
        Opportunity archivedSecondOpportunity = secondOpportunityToArchive.archive();

        ArchiveCommand archiveCommand = new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY, INDEX_SECOND_OPPORTUNITY));

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_OPPORTUNITY_SUCCESS,
                "\n" + Messages.format(archivedFirstOpportunity)
                        + "\n" + Messages.format(archivedSecondOpportunity));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.setOpportunity(firstOpportunityToArchive, archivedFirstOpportunity);
        expectedModel.setOpportunity(secondOpportunityToArchive, archivedSecondOpportunity);
        expectedModel.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);

        expectedModel.commitAddressBook();
        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validAndInvalidIndicesUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOpportunityList().size() + 1);
        // Should throw exception as long as one index is invalid
        ArchiveCommand archiveCommand = new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY, outOfBoundIndex));

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_OPPORTUNITY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateIndicesUnfilteredList_throwsCommandException() {
        ArchiveCommand archiveCommand = new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY, INDEX_FIRST_OPPORTUNITY));

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_DUPLICATE_INDICES);
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(List.of(INDEX_SECOND_OPPORTUNITY));

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY));
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different opportunity -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ArchiveCommand archiveCommand = new ArchiveCommand(List.of(targetIndex));
        String expected = ArchiveCommand.class.getCanonicalName() + "{targetIndices=" + List.of(targetIndex) + "}";
        assertEquals(expected, archiveCommand.toString());
    }

}
