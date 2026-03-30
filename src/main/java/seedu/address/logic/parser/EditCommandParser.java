package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
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

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditOpportunityDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_EMAIL, PREFIX_CONTACT_ROLE,
                        PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS, PREFIX_CYCLE, PREFIX_PHONE);

        Index index = null;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_EMAIL, PREFIX_CONTACT_ROLE,
                PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS, PREFIX_CYCLE, PREFIX_PHONE);

        EditOpportunityDescriptor editOpportunityDescriptor = new EditOpportunityDescriptor();
        List<String> errorMessages = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            try {
                editOpportunityDescriptor.setName(
                    ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            try {
                editOpportunityDescriptor.setEmail(
                    ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }
        if (argMultimap.getValue(PREFIX_CONTACT_ROLE).isPresent()) {
            try {
                editOpportunityDescriptor.setContactRole(
                    ParserUtil.parseContactRole(argMultimap.getValue(PREFIX_CONTACT_ROLE).get()));
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }
        if (argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            try {
                editOpportunityDescriptor.setCompany(
                    ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get()));
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            try {
                editOpportunityDescriptor.setRole(
                    ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get()));
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            try {
                editOpportunityDescriptor.setStatus(
                    ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()));
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }
        if (argMultimap.getValue(PREFIX_CYCLE).isPresent()) {
            try {
                editOpportunityDescriptor.setCycle(
                    ParserUtil.parseCycle(argMultimap.getValue(PREFIX_CYCLE).get()));
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String rawPhone = argMultimap.getValue(PREFIX_PHONE).get();
            if (rawPhone.trim().isEmpty()) {
                // Empty value signals intent to clear the optional phone field
                editOpportunityDescriptor.setClearPhone(true);
            } else {
                try {
                    editOpportunityDescriptor.setPhone(ParserUtil.parsePhone(rawPhone));
                } catch (ParseException pe) {
                    errorMessages.add(pe.getMessage());
                }
            }
        }

        if (!errorMessages.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < errorMessages.size(); i++) {
                sb.append(i + 1)
                        .append(". ")
                        .append(errorMessages.get(i));
                if (i < errorMessages.size() - 1) {
                    sb.append("\n\n");
                }
            }
            throw new ParseException(sb.toString());
        }

        if (!editOpportunityDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editOpportunityDescriptor);
    }

}
