package seedu.address.model.exceptions;

/**
 * Signals that the operation is unable to find a previous state to restore.
 */
public class NoUndoableStateException extends RuntimeException {
    public NoUndoableStateException() {
        super("Current state pointer is at the beginning of the state list, no previous state to restore");
    }
}
