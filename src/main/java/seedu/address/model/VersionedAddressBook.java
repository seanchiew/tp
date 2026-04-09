package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.exceptions.NoUndoableStateException;

/**
 * {@code AddressBook} that keeps track of its own history.
 */
public class VersionedAddressBook extends AddressBook {

    private final List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

    /**
     * Constructs a {@code VersionedAddressBook} using the given
     * {@code ReadOnlyAddressBook} as the initial state.
     *
     * @param initialState The initial state of the address book.
     */
    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);

        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new AddressBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Copy constructor.
     */
    public VersionedAddressBook(VersionedAddressBook toCopy) {
        super(requireNonNull(toCopy));

        addressBookStateList = new ArrayList<>();
        copyStateListFrom(toCopy.addressBookStateList);
        currentStatePointer = toCopy.currentStatePointer;
    }

    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the
     * state list. Undone states are purged from the history.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        addressBookStateList.add(new AddressBook(this));
        currentStatePointer++;
    }

    /**
     * Restores both the current data and the undo history from {@code toRestore}.
     */
    public void restore(VersionedAddressBook toRestore) {
        requireNonNull(toRestore);

        resetData(toRestore);
        addressBookStateList.clear();
        copyStateListFrom(toRestore.addressBookStateList);
        currentStatePointer = toRestore.currentStatePointer;
    }

    private void copyStateListFrom(List<ReadOnlyAddressBook> states) {
        for (ReadOnlyAddressBook state : states) {
            addressBookStateList.add(new AddressBook(state));
        }
    }

    private void removeStatesAfterCurrentPointer() {
        addressBookStateList.subList(currentStatePointer + 1, addressBookStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     *
     * @throws NoUndoableStateException if there is no previous state to restore.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if there is a previous state to restore.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAddressBook)) {
            return false;
        }

        VersionedAddressBook otherVersionedAddressBook = (VersionedAddressBook) other;

        // state check
        return super.equals(otherVersionedAddressBook)
                                        && addressBookStateList.equals(otherVersionedAddressBook.addressBookStateList)
                                        && currentStatePointer == otherVersionedAddressBook.currentStatePointer;
    }
}
