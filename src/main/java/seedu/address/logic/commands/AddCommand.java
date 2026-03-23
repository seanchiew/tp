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

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.opportunity.Opportunity;

/**
 * Adds an opportunity to the tracker.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an opportunity to the tracker. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_CONTACT_ROLE + "CONTACT_ROLE "
            + PREFIX_COMPANY + "COMPANY "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_STATUS + "STATUS "
            + "[" + PREFIX_PHONE + "PHONE]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Jane Lim "
            + PREFIX_EMAIL + "jane@stripe.com "
            + PREFIX_CONTACT_ROLE + "recruiter "
            + PREFIX_COMPANY + "Stripe "
            + PREFIX_ROLE + "SWE Intern "
            + PREFIX_STATUS + "APPLIED "
            + PREFIX_CYCLE + "SUMMER 2026 "
            + PREFIX_PHONE + "98765432";

    public static final String MESSAGE_SUCCESS = "New opportunity added: %1$s";
    public static final String MESSAGE_DUPLICATE_OPPORTUNITY = "This opportunity already exists in the tracker.";

    private final Opportunity toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Opportunity}
     */
    public AddCommand(Opportunity opportunity) {
        requireNonNull(opportunity);
        toAdd = opportunity;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasOpportunity(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_OPPORTUNITY);
        }

        model.addOpportunity(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
