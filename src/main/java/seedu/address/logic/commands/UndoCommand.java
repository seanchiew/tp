package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.exceptions.NoUndoableStateException;

/**
 * Reverts the address book to its previously modified state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_USAGE = COMMAND_WORD
                                    + ": Reverts the tracker to its previously modified state.\n"
                                    + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Undo successful!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        try {
            model.undoAddressBook();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (NoUndoableStateException e) {
            // Defensive programming fallback
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof UndoCommand;
    }
}
