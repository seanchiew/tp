package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all opportunity contacts.\n"
                                                + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "All opportunities have been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setArchiveView(false); // Switch back to main list after clearing
        model.setAddressBook(new AddressBook());
        model.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof ClearCommand;
    }
}
