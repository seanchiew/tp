package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CYCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCommandInputs.COMPANY_DESC_AMY;
import static seedu.address.testutil.TypicalCommandInputs.COMPANY_DESC_BOB;
import static seedu.address.testutil.TypicalCommandInputs.CONTACT_ROLE_DESC_AMY;
import static seedu.address.testutil.TypicalCommandInputs.CONTACT_ROLE_DESC_BOB;
import static seedu.address.testutil.TypicalCommandInputs.CYCLE_DESC_AMY;
import static seedu.address.testutil.TypicalCommandInputs.CYCLE_DESC_BOB;
import static seedu.address.testutil.TypicalCommandInputs.EMAIL_DESC_AMY;
import static seedu.address.testutil.TypicalCommandInputs.EMAIL_DESC_BOB;
import static seedu.address.testutil.TypicalCommandInputs.EXTRA_TEXT_AT_START;
import static seedu.address.testutil.TypicalCommandInputs.INVALID_COMPANY_DESC;
import static seedu.address.testutil.TypicalCommandInputs.INVALID_CONTACT_ROLE_DESC;
import static seedu.address.testutil.TypicalCommandInputs.INVALID_CYCLE_DESC;
import static seedu.address.testutil.TypicalCommandInputs.INVALID_EMAIL_DESC;
import static seedu.address.testutil.TypicalCommandInputs.INVALID_NAME_DESC;
import static seedu.address.testutil.TypicalCommandInputs.INVALID_PHONE_DESC;
import static seedu.address.testutil.TypicalCommandInputs.INVALID_ROLE_DESC;
import static seedu.address.testutil.TypicalCommandInputs.INVALID_STATUS_DESC;
import static seedu.address.testutil.TypicalCommandInputs.NAME_DESC_AMY;
import static seedu.address.testutil.TypicalCommandInputs.NAME_DESC_BOB;
import static seedu.address.testutil.TypicalCommandInputs.PHONE_DESC_AMY;
import static seedu.address.testutil.TypicalCommandInputs.PHONE_DESC_CLEAR;
import static seedu.address.testutil.TypicalCommandInputs.ROLE_DESC_AMY;
import static seedu.address.testutil.TypicalCommandInputs.ROLE_DESC_BOB;
import static seedu.address.testutil.TypicalCommandInputs.STATUS_DESC_AMY;
import static seedu.address.testutil.TypicalCommandInputs.STATUS_DESC_BOB;
import static seedu.address.testutil.TypicalCommandInputs.VALID_COMPANY_BOB;
import static seedu.address.testutil.TypicalCommandInputs.VALID_ROLE_BOB;
import static seedu.address.testutil.TypicalOpportunities.AMY;
import static seedu.address.testutil.TypicalOpportunities.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.ContactRole;
import seedu.address.model.opportunity.Cycle;
import seedu.address.model.opportunity.Email;
import seedu.address.model.opportunity.Name;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.Phone;
import seedu.address.model.opportunity.Role;
import seedu.address.model.opportunity.Status;
import seedu.address.testutil.OpportunityBuilder;

public class AddCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

    private static final String ERROR_MESSAGE_SEPARATOR = "\n\n";

    private final AddCommandParser parser = new AddCommandParser();

    // POSITIVE TEST CASES
    @Test
    public void parse_requiredFieldsOnly_success() {
        Opportunity expectedOpportunity = new OpportunityBuilder(BOB).build();
        // phone is optional — BOB has no phone and should still parse successfully
        assertParseSuccess(parser,
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                new AddCommand(expectedOpportunity));
    }

    @Test
    public void parse_optionalPhonePresent_success() {
        Opportunity expectedOpportunity = new OpportunityBuilder(AMY).build();
        // AMY has a phone number, and it should parse successfully
        assertParseSuccess(parser,
                NAME_DESC_AMY + EMAIL_DESC_AMY + CONTACT_ROLE_DESC_AMY
                        + COMPANY_DESC_AMY + ROLE_DESC_AMY + STATUS_DESC_AMY
                        + CYCLE_DESC_AMY + PHONE_DESC_AMY,
                new AddCommand(expectedOpportunity));
    }

    @Test
    public void parse_allFieldsInReverseOrder_success() {
        Opportunity expectedOpportunity = new OpportunityBuilder(AMY).build();

        assertParseSuccess(parser,
                PHONE_DESC_AMY + CYCLE_DESC_AMY + STATUS_DESC_AMY
                        + ROLE_DESC_AMY + COMPANY_DESC_AMY
                        + CONTACT_ROLE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY,
                new AddCommand(expectedOpportunity));
    }

    // NEGATIVE TEST CASES
    @Test
    public void parse_extraTextBeforeArguments_throwsParseException() {
        assertParseFailure(parser,
                EXTRA_TEXT_AT_START + NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                formatExpectedErrorMessages(MESSAGE_INVALID_FORMAT));
    }

    @Test
    public void parse_noArguments_throwsParseException() {
        assertMissingRequiredPrefix("");
    }

    @Test
    public void parse_missingRequiredPrefixes_throwsParseException() {
        // missing name prefix and value not present
        assertMissingRequiredPrefix(
                EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB
                        + STATUS_DESC_BOB + CYCLE_DESC_BOB);

        // missing email prefix and value not present
        assertMissingRequiredPrefix(
                NAME_DESC_BOB + CONTACT_ROLE_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB
                        + STATUS_DESC_BOB + CYCLE_DESC_BOB);

        // missing contact role prefix and value not present
        assertMissingRequiredPrefix(
                NAME_DESC_BOB + EMAIL_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB
                        + STATUS_DESC_BOB + CYCLE_DESC_BOB);

        // missing company prefix but valid company value present
        assertMissingRequiredPrefix(
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + VALID_COMPANY_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB);

        // missing role prefix but valid role value present
        assertMissingRequiredPrefix(
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + VALID_ROLE_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB);

        // missing status prefix and value not present
        assertMissingRequiredPrefix(
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + CYCLE_DESC_BOB);

        // missing cycle prefix and value not present
        assertMissingRequiredPrefix(
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB);
    }

    private void assertMissingRequiredPrefix(String userInput) {
        assertParseFailure(parser, userInput, formatExpectedErrorMessages(MESSAGE_INVALID_FORMAT));
    }

    @Test
    public void parse_duplicateNamePrefix_throwsParseException() {
        assertParseFailure(parser,
                NAME_DESC_BOB + NAME_DESC_AMY
                        + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB
                        + STATUS_DESC_BOB + CYCLE_DESC_BOB,
                formatExpectedErrorMessages(getDuplicateFieldsMessage(PREFIX_NAME)));
    }

    @Test
    public void parse_multipleDuplicateSingleValuedPrefixes_throwsParseException() {
        // Uses a second valid phone value for duplication because BOB has no phone number.
        assertParseFailure(parser,
                NAME_DESC_AMY + NAME_DESC_BOB
                        + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                        + CONTACT_ROLE_DESC_AMY + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_AMY + COMPANY_DESC_BOB
                        + ROLE_DESC_AMY + ROLE_DESC_BOB
                        + STATUS_DESC_AMY + STATUS_DESC_BOB
                        + CYCLE_DESC_AMY + CYCLE_DESC_BOB
                        + PHONE_DESC_AMY + buildPhoneDesc("98765432"),
                formatExpectedErrorMessages(
                        getDuplicateFieldsMessage(
                                PREFIX_NAME,
                                PREFIX_EMAIL,
                                PREFIX_CONTACT_ROLE,
                                PREFIX_COMPANY,
                                PREFIX_ROLE,
                                PREFIX_STATUS,
                                PREFIX_CYCLE,
                                PREFIX_PHONE)));
    }

    private static String buildPhoneDesc(String phone) {
        return " " + PREFIX_PHONE + phone;
    }

    private static String getDuplicateFieldsMessage(Prefix... duplicatedPrefixes) {
        StringBuilder builder = new StringBuilder(MESSAGE_DUPLICATE_FIELDS);

        for (int i = 0; i < duplicatedPrefixes.length; i++) {
            if (i > 0) {
                builder.append(" ");
            }
            builder.append(duplicatedPrefixes[i]);
        }

        return builder.toString();
    }

    @Test
    public void parse_invalidValue_throwsParseException() {
        // invalid name
        assertInvalidValue(
                INVALID_NAME_DESC + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid email
        assertInvalidValue(
                NAME_DESC_BOB + INVALID_EMAIL_DESC + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid contact role
        assertInvalidValue(
                NAME_DESC_BOB + EMAIL_DESC_BOB + INVALID_CONTACT_ROLE_DESC
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                ContactRole.MESSAGE_CONSTRAINTS);

        // invalid company
        assertInvalidValue(
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + INVALID_COMPANY_DESC + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                Company.MESSAGE_CONSTRAINTS);

        // invalid role
        assertInvalidValue(
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + INVALID_ROLE_DESC + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                Role.MESSAGE_CONSTRAINTS);

        // invalid status
        assertInvalidValue(
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + INVALID_STATUS_DESC
                        + CYCLE_DESC_BOB,
                Status.MESSAGE_CONSTRAINTS);

        // invalid cycle
        assertInvalidValue(
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + INVALID_CYCLE_DESC,
                Cycle.PARSER_MESSAGE_CONSTRAINTS);

        // invalid phone
        assertInvalidValue(
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleInvalidValues_throwsParseException() {
        String userInput = INVALID_NAME_DESC + INVALID_EMAIL_DESC + INVALID_CONTACT_ROLE_DESC
                + INVALID_COMPANY_DESC + INVALID_ROLE_DESC + INVALID_STATUS_DESC
                + INVALID_CYCLE_DESC + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput,
                formatExpectedErrorMessages(
                        Name.MESSAGE_CONSTRAINTS,
                        Email.MESSAGE_CONSTRAINTS,
                        ContactRole.MESSAGE_CONSTRAINTS,
                        Company.MESSAGE_CONSTRAINTS,
                        Role.MESSAGE_CONSTRAINTS,
                        Status.MESSAGE_CONSTRAINTS,
                        Cycle.PARSER_MESSAGE_CONSTRAINTS,
                        Phone.MESSAGE_CONSTRAINTS));
    }

    private void assertInvalidValue(String userInput, String expectedMessage) {
        assertParseFailure(parser, userInput, formatExpectedErrorMessages(expectedMessage));
    }

    private static String formatExpectedErrorMessages(String... errorMessages) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < errorMessages.length; i++) {
            if (i > 0) {
                builder.append(ERROR_MESSAGE_SEPARATOR);
            }

            builder.append(i + 1).append(". ").append(errorMessages[i]);
        }

        return builder.toString();
    }

    @Test
    public void parse_emptyPhoneValue_throwsParseException() {
        assertInvalidValue(
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB + PHONE_DESC_CLEAR,
                Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allErrorTypesJumbled_throwsAggregatedParseException() {
        // missing cycle prefix + duplicate prefixes + invalid values in jumbled order
        assertParseFailure(parser,
                NAME_DESC_BOB + EMAIL_DESC_BOB
                        + NAME_DESC_AMY + INVALID_EMAIL_DESC
                        + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + INVALID_COMPANY_DESC
                        + ROLE_DESC_BOB
                        + STATUS_DESC_BOB + INVALID_STATUS_DESC
                        + PHONE_DESC_AMY + INVALID_PHONE_DESC,
                formatExpectedErrorMessages(
                        MESSAGE_INVALID_FORMAT,
                        getDuplicateFieldsMessage(
                                PREFIX_NAME,
                                PREFIX_EMAIL,
                                PREFIX_COMPANY,
                                PREFIX_STATUS,
                                PREFIX_PHONE),
                        Email.MESSAGE_CONSTRAINTS,
                        Company.MESSAGE_CONSTRAINTS,
                        Status.MESSAGE_CONSTRAINTS,
                        Phone.MESSAGE_CONSTRAINTS));
    }
}
