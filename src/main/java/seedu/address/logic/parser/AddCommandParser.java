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

        checkRequiredPrefixes(argMultimap, errorMessages);
        checkNoDuplicatePrefixes(argMultimap, errorMessages);

        Name name = parseName(argMultimap, errorMessages);
        Email email = parseEmail(argMultimap, errorMessages);
        ContactRole contactRole = parseContactRole(argMultimap, errorMessages);
        Company company = parseCompany(argMultimap, errorMessages);
        Role role = parseRole(argMultimap, errorMessages);
        Status status = parseStatus(argMultimap, errorMessages);
        Cycle cycle = parseCycle(argMultimap, errorMessages);
        Phone phone = parsePhone(argMultimap, errorMessages);

        ParserUtil.throwCombinedParseException(errorMessages);

        // Newly added opportunities are not archived by default
        Opportunity opportunity = new Opportunity(name, email, contactRole, company, role, status, cycle, false, phone);

        return new AddCommand(opportunity);
    }

    private void checkRequiredPrefixes(ArgumentMultimap argMultimap, List<String> errorMessages) {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EMAIL, PREFIX_CONTACT_ROLE,
                PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS, PREFIX_CYCLE)
                || !argMultimap.getPreamble().isEmpty()) {
            errorMessages.add(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    private void checkNoDuplicatePrefixes(ArgumentMultimap argMultimap, List<String> errorMessages) {
        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_EMAIL, PREFIX_CONTACT_ROLE,
                    PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS, PREFIX_CYCLE, PREFIX_PHONE);
        } catch (ParseException pe) {
            errorMessages.add(pe.getMessage());
        }
    }

    private Name parseName(ArgumentMultimap argMultimap, List<String> errorMessages) {
        if (!argMultimap.getValue(PREFIX_NAME).isPresent()) {
            return null;
        }
        try {
            return ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        } catch (ParseException pe) {
            errorMessages.add(pe.getMessage());
            return null;
        }
    }

    private Email parseEmail(ArgumentMultimap argMultimap, List<String> errorMessages) {
        if (!argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            return null;
        }
        try {
            return ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        } catch (ParseException pe) {
            errorMessages.add(pe.getMessage());
            return null;
        }
    }

    private ContactRole parseContactRole(ArgumentMultimap argMultimap, List<String> errorMessages) {
        if (!argMultimap.getValue(PREFIX_CONTACT_ROLE).isPresent()) {
            return null;
        }
        try {
            return ParserUtil.parseContactRole(argMultimap.getValue(PREFIX_CONTACT_ROLE).get());
        } catch (ParseException pe) {
            errorMessages.add(pe.getMessage());
            return null;
        }
    }

    private Company parseCompany(ArgumentMultimap argMultimap, List<String> errorMessages) {
        if (!argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            return null;
        }
        try {
            return ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get());
        } catch (ParseException pe) {
            errorMessages.add(pe.getMessage());
            return null;
        }
    }

    private Role parseRole(ArgumentMultimap argMultimap, List<String> errorMessages) {
        if (!argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            return null;
        }
        try {
            return ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        } catch (ParseException pe) {
            errorMessages.add(pe.getMessage());
            return null;
        }
    }

    private Status parseStatus(ArgumentMultimap argMultimap, List<String> errorMessages) {
        if (!argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            return null;
        }
        try {
            return ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        } catch (ParseException pe) {
            errorMessages.add(pe.getMessage());
            return null;
        }
    }

    private Cycle parseCycle(ArgumentMultimap argMultimap, List<String> errorMessages) {
        if (!argMultimap.getValue(PREFIX_CYCLE).isPresent()) {
            return null;
        }
        try {
            return ParserUtil.parseCycle(argMultimap.getValue(PREFIX_CYCLE).get());
        } catch (ParseException pe) {
            errorMessages.add(pe.getMessage());
            return null;
        }
    }

    private Phone parsePhone(ArgumentMultimap argMultimap, List<String> errorMessages) {
        if (!argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            return null;
        }
        try {
            return ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        } catch (ParseException pe) {
            errorMessages.add(pe.getMessage());
            return null;
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
