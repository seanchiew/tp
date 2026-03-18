package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.opportunity.OpportunityContainsSubstringPredicate;

/**
 * Finds and lists all opportunities in address book whose name matches the provided keywords and,
 * when supplied, whose company matches the provided company keyword(s). Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all opportunities whose names contain any of "
            + "the specified name keywords (case-insensitive). You can optionally add a company filter with c/.\n"
            + "Parameters: [NAME_KEYWORD [MORE_NAME_KEYWORDS]...] [c/COMPANY_KEYWORD [MORE_COMPANY_KEYWORDS]...]\n"
            + "Examples: " + COMMAND_WORD + " alice bob\n"
            + "          " + COMMAND_WORD + " alice c/stripe\n"
            + "          " + COMMAND_WORD + " c/tiktok";

    private final OpportunityContainsSubstringPredicate predicate;

    public FindCommand(OpportunityContainsSubstringPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredOpportunityList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_OPPORTUNITIES_LISTED_OVERVIEW,
                        model.getFilteredOpportunityList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
