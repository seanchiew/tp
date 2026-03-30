package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES;

import seedu.address.logic.Messages;
import seedu.address.model.Model;

/**
 * Lists all archived opportunities in the address book to the user.
 */
public class ListArchiveCommand extends Command {

    public static final String COMMAND_WORD = "list archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all archived opportunities.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Showing %1$d archived %2$s.";

    public static final String MESSAGE_EMPTY = "No archived opportunities.";

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setArchiveView(true);
        model.updateFilteredOpportunityList(PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES);
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
        return other instanceof ListArchiveCommand;
    }
}
