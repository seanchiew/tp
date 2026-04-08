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
import java.util.function.Consumer;

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

        List<String> errorMessages = new ArrayList<>();
        Index index = null;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            errorMessages.add(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_EMAIL, PREFIX_CONTACT_ROLE,
                    PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS, PREFIX_CYCLE, PREFIX_PHONE);
        } catch (ParseException pe) {
            errorMessages.add(pe.getMessage());
        }

        EditOpportunityDescriptor editOpportunityDescriptor = new EditOpportunityDescriptor();

        populateDescriptor(argMultimap, editOpportunityDescriptor, errorMessages);

        ParserUtil.throwCombinedParseException(errorMessages);

        if (!editOpportunityDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editOpportunityDescriptor);
    }

    /**
     * Populates the given {@code EditOpportunityDescriptor} with the parsed values from {@code argMultimap}.
     * Any parsing errors are caught and added to the {@code errorMessages} list.
     */
    private void populateDescriptor(ArgumentMultimap argMultimap, EditOpportunityDescriptor editOpportunityDescriptor,
                                    List<String> errorMessages) {
        parseAndSetField(argMultimap, PREFIX_NAME, ParserUtil::parseName,
                editOpportunityDescriptor::setName, errorMessages);
        parseAndSetField(argMultimap, PREFIX_EMAIL, ParserUtil::parseEmail,
                editOpportunityDescriptor::setEmail, errorMessages);
        parseAndSetField(argMultimap, PREFIX_CONTACT_ROLE, ParserUtil::parseContactRole,
                editOpportunityDescriptor::setContactRole, errorMessages);
        parseAndSetField(argMultimap, PREFIX_COMPANY, ParserUtil::parseCompany,
                editOpportunityDescriptor::setCompany, errorMessages);
        parseAndSetField(argMultimap, PREFIX_ROLE, ParserUtil::parseRole,
                editOpportunityDescriptor::setRole, errorMessages);
        parseAndSetField(argMultimap, PREFIX_STATUS, ParserUtil::parseStatus,
                editOpportunityDescriptor::setStatus, errorMessages);
        parseAndSetField(argMultimap, PREFIX_CYCLE, ParserUtil::parseCycle,
                editOpportunityDescriptor::setCycle, errorMessages);
        parseAndSetPhone(argMultimap, editOpportunityDescriptor, errorMessages);
    }

    /**
     * Parses the value for {@code prefix} using {@code parser} and, if successful, passes the result to
     * {@code setter}. If the prefix is absent, nothing happens. Any {@link ParseException} is collected
     * into {@code errorMessages}.
     */
    private <T> void parseAndSetField(ArgumentMultimap argMultimap, Prefix prefix,
                                      FieldParser<T> parser, Consumer<T> setter,
                                      List<String> errorMessages) {
        T value = ParserUtil.parseField(argMultimap, prefix, parser, errorMessages);
        if (value != null) {
            setter.accept(value);
        }
    }

    private void parseAndSetPhone(ArgumentMultimap argMultimap, EditOpportunityDescriptor descriptor,
                                  List<String> errorMessages) {
        if (!argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            return;
        }
        String rawPhone = argMultimap.getValue(PREFIX_PHONE).get();
        if (rawPhone.trim().isEmpty()) {
            // Empty value signals intent to clear the optional phone field
            descriptor.setShouldClearPhone(true);
        } else {
            try {
                descriptor.setPhone(ParserUtil.parsePhone(rawPhone));
            } catch (ParseException pe) {
                errorMessages.add(pe.getMessage());
            }
        }
    }
}
