package seedu.address.model;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.opportunity.Opportunity;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Opportunity> PREDICATE_SHOW_ALL_OPPORTUNITIES = unused -> true;

    /** {@code Predicate} that shows only unarchived opportunities */
    Predicate<Opportunity> PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES = opportunity -> !opportunity.isArchived();

    /** {@code Predicate} that shows only archived opportunities */
    Predicate<Opportunity> PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES = opportunity -> opportunity.isArchived();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a opportunity with the same identity as {@code opportunity} exists in the address book.
     */
    boolean hasOpportunity(Opportunity opportunity);

    /**
     * Returns an {@code Optional} containing the existing opportunity that has the same identity
     * as {@code opportunity}, or an empty Optional if no such opportunity exists.
     */
    Optional<Opportunity> getConflictingOpportunity(Opportunity opportunity);

    /**
     * Deletes the given opportunity.
     * The opportunity must exist in the address book.
     */
    void deleteOpportunity(Opportunity target);

    /**
     * Adds the given opportunity.
     * {@code opportunity} must not already exist in the address book.
     */
    void addOpportunity(Opportunity opportunity);

    /**
     * Replaces the given opportunity {@code target} with {@code editedOpportunity}.
     * {@code target} must exist in the address book.
     * The opportunity identity of {@code editedOpportunity} must not be the same as another existing opportunity
     * in the address book.
     */
    void setOpportunity(Opportunity target, Opportunity editedOpportunity);

    /** Returns an unmodifiable view of the filtered opportunity list */
    ObservableList<Opportunity> getFilteredOpportunityList();

    /**
     * Updates the filter of the filtered opportunity list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredOpportunityList(Predicate<Opportunity> predicate);

    /** Returns true if the model is currently in the archive view. */
    default boolean isArchiveView() {
        return false;
    }

    /** Sets the archive view state of the model. */
    default void setArchiveView(boolean isArchiveView) {
        // do nothing by default
    }

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Saves the current address book state for undo.
     */
    void commitAddressBook();

    /**
     * Opaque snapshot of model state needed to roll back a mutating command if saving fails.
     */
    interface StateSnapshot { }

    /**
     * Returns a snapshot of the current model state.
     */
    StateSnapshot getStateSnapshot();

    /**
     * Restores the model to a previously captured snapshot.
     */
    void restoreState(StateSnapshot stateSnapshot);
}
