package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CYCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.ContactRole;
import seedu.address.model.opportunity.Cycle;
import seedu.address.model.opportunity.Email;
import seedu.address.model.opportunity.Name;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.Phone;
import seedu.address.model.opportunity.Role;
import seedu.address.model.opportunity.Status;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_EMAIL, PREFIX_CONTACT_ROLE,
                        PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS, PREFIX_CYCLE, PREFIX_PHONE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EMAIL, PREFIX_CONTACT_ROLE,
                PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS, PREFIX_CYCLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_EMAIL, PREFIX_CONTACT_ROLE,
                PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS, PREFIX_CYCLE, PREFIX_PHONE);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        ContactRole contactRole = ParserUtil.parseContactRole(argMultimap.getValue(PREFIX_CONTACT_ROLE).get());
        Company company = ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get());
        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        Cycle cycle = ParserUtil.parseCycle(argMultimap.getValue(PREFIX_CYCLE).get());

        Phone phone = null;
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        }

        //Newly added opportunities are not archived by default
        Opportunity opportunity = new Opportunity(name, email, contactRole, company, role, status, cycle, false, phone);

        return new AddCommand(opportunity);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
