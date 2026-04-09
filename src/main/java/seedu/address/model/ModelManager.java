package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.opportunity.Opportunity;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Opportunity> filteredOpportunities;
    private boolean isArchiveView = false;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredOpportunities = new FilteredList<>(this.addressBook.getOpportunityList(),
                PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasOpportunity(Opportunity opportunity) {
        requireNonNull(opportunity);
        return addressBook.hasOpportunity(opportunity);
    }

    @Override
    public Optional<Opportunity> getConflictingOpportunity(Opportunity opportunity) {
        requireNonNull(opportunity);
        return addressBook.getOpportunityList().stream()
                .filter(opportunity::isSameOpportunity)
                .findFirst();
    }

    @Override
    public void deleteOpportunity(Opportunity target) {
        addressBook.removeOpportunity(target);
    }

    @Override
    public void addOpportunity(Opportunity opportunity) {
        addressBook.addOpportunity(opportunity);
        setArchiveView(false);
        updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);
    }

    @Override
    public void setOpportunity(Opportunity target, Opportunity editedOpportunity) {
        requireAllNonNull(target, editedOpportunity);

        addressBook.setOpportunity(target, editedOpportunity);
    }

    @Override
    public boolean isArchiveView() {
        return isArchiveView;
    }

    @Override
    public void setArchiveView(boolean isArchiveView) {
        this.isArchiveView = isArchiveView;
    }

    //=========== Filtered Opportunity List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Opportunity} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Opportunity> getFilteredOpportunityList() {
        return filteredOpportunities;
    }

    @Override
    public void updateFilteredOpportunityList(Predicate<Opportunity> predicate) {
        requireNonNull(predicate);
        filteredOpportunities.setPredicate(predicate);
    }

    @Override
    public StateSnapshot getStateSnapshot() {
        Predicate<? super Opportunity> currentPredicate = filteredOpportunities.getPredicate();
        Predicate<Opportunity> snapshotPredicate;

        if (currentPredicate == null) {
            snapshotPredicate = PREDICATE_SHOW_ALL_OPPORTUNITIES;
        } else {
            snapshotPredicate = opportunity -> currentPredicate.test(opportunity);
        }

        return new ModelStateSnapshot(
                addressBook,
                snapshotPredicate,
                isArchiveView
        );
    }

    @Override
    public void restoreState(StateSnapshot stateSnapshot) {
        requireNonNull(stateSnapshot);

        if (!(stateSnapshot instanceof ModelStateSnapshot)) {
            throw new IllegalArgumentException("Unsupported state snapshot type");
        }

        ModelStateSnapshot modelState = (ModelStateSnapshot) stateSnapshot;
        addressBook.restore(modelState.addressBookSnapshot);
        filteredOpportunities.setPredicate(modelState.filteredListPredicate);
        isArchiveView = modelState.isArchiveView;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredOpportunities.equals(otherModelManager.filteredOpportunities)
                && isArchiveView == otherModelManager.isArchiveView;
    }

    // =========== Undo / State Management
    // ====================================================================

    @Override
    public boolean canUndoAddressBook() {
        return addressBook.canUndo();
    }

    @Override
    public void undoAddressBook() {
        addressBook.undo();
    }

    @Override
    public void commitAddressBook() {
        addressBook.commit();
    }

    /**
     * Internal immutable snapshot implementation.
     */
    private static final class ModelStateSnapshot implements StateSnapshot {
        private final VersionedAddressBook addressBookSnapshot;
        private final Predicate<Opportunity> filteredListPredicate;
        private final boolean isArchiveView;

        private ModelStateSnapshot(VersionedAddressBook addressBookSnapshot,
                                   Predicate<Opportunity> filteredListPredicate,
                                   boolean isArchiveView) {
            this.addressBookSnapshot = new VersionedAddressBook(addressBookSnapshot);
            this.filteredListPredicate = filteredListPredicate;
            this.isArchiveView = isArchiveView;
        }
    }
}
