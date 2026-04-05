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
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.opportunity.Opportunity;

/**
 * Lists all opportunities in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String ARCHIVE_KEYWORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all tracked opportunities.\n"
                                    + "Example: " + COMMAND_WORD + "\n"
                                    + "To list archived opportunities, use: " + COMMAND_WORD + " "
                                    + ARCHIVE_KEYWORD;

    public static final String MESSAGE_SUCCESS_ACTIVE = "Showing %1$d %2$s.";

    public static final String MESSAGE_EMPTY_ACTIVE = "No opportunities yet. Add one with: "
            + PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_CONTACT_ROLE + "CONTACT_ROLE "
            + PREFIX_COMPANY + "COMPANY "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_STATUS + "STATUS "
            + PREFIX_CYCLE + "CYCLE "
            + "[" + PREFIX_PHONE + "PHONE]";

    public static final String MESSAGE_SUCCESS_ARCHIVED = "Showing %1$d archived %2$s.";
    public static final String MESSAGE_EMPTY_ARCHIVED = "No archived opportunities.";

    private final boolean isArchiveView;

    public ListCommand() {
        this(false);
    }

    public ListCommand(boolean isArchiveView) {
        this.isArchiveView = isArchiveView;
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setArchiveView(isArchiveView);
        Predicate<Opportunity> predicate = isArchiveView ? PREDICATE_SHOW_ARCHIVED_OPPORTUNITIES
                                                         : PREDICATE_SHOW_UNARCHIVED_OPPORTUNITIES;
        model.updateFilteredOpportunityList(predicate);
        int count = model.getFilteredOpportunityList().size();

        String feedback;
        if (isArchiveView) {
            feedback = count == 0 ? MESSAGE_EMPTY_ARCHIVED
                                  : String.format(MESSAGE_SUCCESS_ARCHIVED, count,
                                                    Messages.getOpportunityWord(count));
        } else {
            feedback = count == 0 ? MESSAGE_EMPTY_ACTIVE
                                  : String.format(MESSAGE_SUCCESS_ACTIVE, count,
                                                    Messages.getOpportunityWord(count));
        }
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListCommand)) {
            return false;
        }
        ListCommand otherCommand = (ListCommand) other;
        return isArchiveView == otherCommand.isArchiveView;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(isArchiveView);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("isArchiveView", isArchiveView)
                .toString();
    }
}
