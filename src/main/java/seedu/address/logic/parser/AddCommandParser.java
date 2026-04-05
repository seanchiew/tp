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

import java.util.ArrayList;
import java.util.List;
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

        List<String> errorMessages = new ArrayList<>();

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EMAIL, PREFIX_CONTACT_ROLE,
                PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS, PREFIX_CYCLE)
                || !argMultimap.getPreamble().isEmpty()) {
            errorMessages.add(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_EMAIL, PREFIX_CONTACT_ROLE,
                    PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS, PREFIX_CYCLE, PREFIX_PHONE);
        } catch (ParseException pe) {
            errorMessages.add(pe.getMessage());
        }

        Name name = null;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            try {
                name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }

        Email email = null;
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            try {
                email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }

        ContactRole contactRole = null;
        if (argMultimap.getValue(PREFIX_CONTACT_ROLE).isPresent()) {
            try {
                contactRole = ParserUtil.parseContactRole(argMultimap.getValue(PREFIX_CONTACT_ROLE).get());
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }

        Company company = null;
        if (argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            try {
                company = ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get());
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }

        Role role = null;
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            try {
                role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }

        Status status = null;
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            try {
                status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }

        Cycle cycle = null;
        if (argMultimap.getValue(PREFIX_CYCLE).isPresent()) {
            try {
                cycle = ParserUtil.parseCycle(argMultimap.getValue(PREFIX_CYCLE).get());
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }

        Phone phone = null;
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            try {
                phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }

        ParserUtil.throwCombinedParseException(errorMessages);

        // Newly added opportunities are not archived by default
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
