package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.opportunity.OpportunityContainsSubstringPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        boolean searchArchived = false;
        String argsToParse = args;
        String trimmedArgs = args.trim();

        if (!trimmedArgs.isEmpty()) {
            String[] splitArgs = trimmedArgs.split("\\s+", 2);
            if (splitArgs[0].equals(FindCommand.ARCHIVED_FLAG)) {
                searchArchived = true;
                argsToParse = splitArgs.length == 1 ? "" : splitArgs[1];
            }
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + argsToParse, PREFIX_COMPANY);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_COMPANY);

        List<String> nameKeywords = splitKeywords(argMultimap.getPreamble());
        Optional<String> companyValue = argMultimap.getValue(PREFIX_COMPANY);
        List<String> companyKeywords = companyValue.map(this::splitKeywords).orElse(List.of());

        if ((nameKeywords.isEmpty() && companyKeywords.isEmpty())
                || (companyValue.isPresent() && companyKeywords.isEmpty())) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new OpportunityContainsSubstringPredicate(nameKeywords, companyKeywords),
                searchArchived);
    }

    private List<String> splitKeywords(String value) {
        String trimmedValue = value.trim();
        return trimmedValue.isEmpty() ? List.of() : Arrays.asList(trimmedValue.split("\\s+"));
    }
}
