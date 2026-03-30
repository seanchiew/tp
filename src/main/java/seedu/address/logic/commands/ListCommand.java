package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CYCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;

import seedu.address.logic.Messages;
import seedu.address.model.Model;

/**
 * Lists all opportunities in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tracked opportunities.\n"
                                    + "Example: " + COMMAND_WORD + "\n"
                                    + "To list archived opportunities, use: " + ListArchiveCommand.COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Showing %1$d %2$s.";

    public static final String MESSAGE_EMPTY = "No opportunities yet. Add one with: "
            + PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_CONTACT_ROLE + "CONTACT_ROLE "
            + PREFIX_COMPANY + "COMPANY "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_STATUS + "STATUS "
            + PREFIX_CYCLE + "CYCLE "
            + "[" + PREFIX_PHONE + "PHONE]";

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setArchiveView(false);
        model.updateFilteredOpportunityList(PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES);
        int count = model.getFilteredOpportunityList().size();
        String feedback = count == 0 ? MESSAGE_EMPTY
            : String.format(MESSAGE_SUCCESS, count, Messages.getOpportunityWord(count));
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof ListCommand;
    }
}
