package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOpportunities.ALICE;
import static seedu.address.testutil.TypicalOpportunities.BOB;
import static seedu.address.testutil.TypicalOpportunities.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.model.exceptions.NoUndoableStateException;
import seedu.address.testutil.AddressBookBuilder;

public class VersionedAddressBookTest {

    private final ReadOnlyAddressBook initialState = new AddressBookBuilder().withOpportunity(ALICE).build();
    private final ReadOnlyAddressBook state1 = new AddressBookBuilder().withOpportunity(ALICE)
                                                .withOpportunity(BOB).build();
    private final ReadOnlyAddressBook state2 = new AddressBookBuilder().withOpportunity(ALICE)
                                                .withOpportunity(BOB).withOpportunity(CARL).build();

    @Test
    public void commit_singleState_pointerAdvanced() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook(initialState);
        versionedAddressBook.resetData(state1);
        versionedAddressBook.commit();

        // EP: Normal commit operation where pointer is at the end of the state list
        assertTrue(versionedAddressBook.canUndo());
    }

    @Test
    public void undo_hasPreviousState_success() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook(initialState);
        versionedAddressBook.resetData(state1);
        versionedAddressBook.commit();

        // EP: Undo operation when a valid previous state exists
        versionedAddressBook.undo();
        assertEquals(initialState, versionedAddressBook);
    }

    @Test
    public void undo_noPreviousState_throwsNoUndoableStateException() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook(initialState);

        // Boundary Value Analysis: Undo operation at the lower bound boundary (currentStatePointer == 0)
        assertThrows(NoUndoableStateException.class, versionedAddressBook::undo);
    }

    @Test
    public void commit_afterUndo_purgesRedundantStates() {
        VersionedAddressBook versionedAddressBook = new VersionedAddressBook(initialState);
        versionedAddressBook.resetData(state1);
        versionedAddressBook.commit();
        versionedAddressBook.undo();

        // Commit a different state after an undo
        versionedAddressBook.resetData(state2);
        versionedAddressBook.commit();

        // EP: Commit operation when pointer is NOT at the end of the state list.
        // Verifies that orphaned states (state1) are correctly purged.
        VersionedAddressBook expectedVab = new VersionedAddressBook(initialState);
        expectedVab.resetData(state2);
        expectedVab.commit();

        assertEquals(expectedVab, versionedAddressBook);
    }
}
