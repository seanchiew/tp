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
import static seedu.address.testutil.TypicalCommandInputs.VALID_COMPANY_AMY;
import static seedu.address.testutil.TypicalCommandInputs.VALID_CONTACT_ROLE_AMY;
import static seedu.address.testutil.TypicalCommandInputs.VALID_CYCLE_AMY;
import static seedu.address.testutil.TypicalCommandInputs.VALID_EMAIL_AMY;
import static seedu.address.testutil.TypicalCommandInputs.VALID_NAME_AMY;
import static seedu.address.testutil.TypicalCommandInputs.VALID_PHONE_AMY;
import static seedu.address.testutil.TypicalCommandInputs.VALID_ROLE_AMY;
import static seedu.address.testutil.TypicalCommandInputs.VALID_STATUS_AMY;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OPPORTUNITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OPPORTUNITY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditOpportunityDescriptor;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.ContactRole;
import seedu.address.model.opportunity.Cycle;
import seedu.address.model.opportunity.Email;
import seedu.address.model.opportunity.Name;
import seedu.address.model.opportunity.Phone;
import seedu.address.model.opportunity.Role;
import seedu.address.model.opportunity.Status;
import seedu.address.testutil.EditOpportunityDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private static final String ERROR_MESSAGE_SEPARATOR = "\n\n";

    private final EditCommandParser parser = new EditCommandParser();


    // POSITIVE TEST CASES
    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_OPPORTUNITY;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;

        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_OPPORTUNITY;
        String userInput = targetIndex.getOneBased()
                + NAME_DESC_AMY + EMAIL_DESC_AMY + CONTACT_ROLE_DESC_AMY
                + COMPANY_DESC_AMY + ROLE_DESC_AMY + STATUS_DESC_AMY
                + CYCLE_DESC_AMY + PHONE_DESC_AMY;

        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withContactRole(VALID_CONTACT_ROLE_AMY)
                .withCompany(VALID_COMPANY_AMY)
                .withRole(VALID_ROLE_AMY)
                .withStatus(VALID_STATUS_AMY)
                .withCycle(VALID_CYCLE_AMY)
                .withPhone(VALID_PHONE_AMY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsInReverseOrder_success() {
        Index targetIndex = INDEX_FIRST_OPPORTUNITY;
        String userInput = targetIndex.getOneBased()
                + PHONE_DESC_AMY + CYCLE_DESC_AMY + STATUS_DESC_AMY
                + ROLE_DESC_AMY + COMPANY_DESC_AMY
                + CONTACT_ROLE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY;

        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withContactRole(VALID_CONTACT_ROLE_AMY)
                .withCompany(VALID_COMPANY_AMY)
                .withRole(VALID_ROLE_AMY)
                .withStatus(VALID_STATUS_AMY)
                .withCycle(VALID_CYCLE_AMY)
                .withPhone(VALID_PHONE_AMY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyPhoneValue_setsClearPhone() {
        // p/ with no value should produce a descriptor with clearPhone=true, not a ParseException
        Index targetIndex = INDEX_FIRST_OPPORTUNITY;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_CLEAR;

        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder().withClearPhone().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validPhoneValue_setsPhone() {
        Index targetIndex = INDEX_FIRST_OPPORTUNITY;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;

        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder()
                .withPhone(VALID_PHONE_AMY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }


    // NEGATIVE TEST CASES
    @Test
    public void parse_invalidCommandFormat_throwsParseException() {
        // no index specified
        assertInvalidCommandFormat(VALID_COMPANY_AMY);

        // non-positive index
        assertInvalidCommandFormat("-1" + COMPANY_DESC_BOB);

        // multiple indices specified
        assertInvalidCommandFormat(
                INDEX_FIRST_OPPORTUNITY.getOneBased() + " "
                        + INDEX_SECOND_OPPORTUNITY.getOneBased() + COMPANY_DESC_BOB);

        // no index and no field specified
        assertInvalidCommandFormat("");
    }

    private void assertInvalidCommandFormat(String userInput) {
        assertParseFailure(parser, userInput, formatExpectedErrorMessages(MESSAGE_INVALID_FORMAT));
    }

    @Test
    public void parse_extraTextAroundIndex_throwsParseException() {
        // extra text before index
        assertInvalidCommandFormat(
                EXTRA_TEXT_AT_START + INDEX_FIRST_OPPORTUNITY.getOneBased() + COMPANY_DESC_BOB);

        // extra text after index
        assertInvalidCommandFormat(
                INDEX_FIRST_OPPORTUNITY.getOneBased() + " " + EXTRA_TEXT_AT_START + COMPANY_DESC_BOB);
    }

    @Test
    public void parse_noFieldsSpecified_throwsParseException() {
        // no field specified
        assertParseFailure(parser,
                String.valueOf(INDEX_FIRST_OPPORTUNITY.getOneBased()),
                EditCommand.MESSAGE_NOT_EDITED);
    }


    @Test
    public void parse_duplicateNamePrefix_throwsParseException() {
        // duplicate name prefix
        assertParseFailure(parser,
                String.valueOf(INDEX_FIRST_OPPORTUNITY.getOneBased())
                        + NAME_DESC_AMY + NAME_DESC_BOB,
                formatExpectedErrorMessages(getDuplicateFieldsMessage(PREFIX_NAME)));
    }

    @Test
    public void parse_multipleDuplicateSingleValuedPrefixes_throwsParseException() {
        // Uses a second valid phone value for duplication because BOB has no phone number.
        assertParseFailure(parser,
                String.valueOf(INDEX_FIRST_OPPORTUNITY.getOneBased())
                        + NAME_DESC_AMY + NAME_DESC_BOB
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
                String.valueOf(INDEX_FIRST_OPPORTUNITY.getOneBased()) + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // invalid email
        assertInvalidValue(
                String.valueOf(INDEX_FIRST_OPPORTUNITY.getOneBased()) + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS);

        // invalid contact role
        assertInvalidValue(
                String.valueOf(INDEX_FIRST_OPPORTUNITY.getOneBased()) + INVALID_CONTACT_ROLE_DESC,
                ContactRole.MESSAGE_CONSTRAINTS);

        // invalid company
        assertInvalidValue(
                String.valueOf(INDEX_FIRST_OPPORTUNITY.getOneBased()) + INVALID_COMPANY_DESC,
                Company.MESSAGE_CONSTRAINTS);

        // invalid role
        assertInvalidValue(
                String.valueOf(INDEX_FIRST_OPPORTUNITY.getOneBased()) + INVALID_ROLE_DESC,
                Role.MESSAGE_CONSTRAINTS);

        // invalid status
        assertInvalidValue(
                String.valueOf(INDEX_FIRST_OPPORTUNITY.getOneBased()) + INVALID_STATUS_DESC,
                Status.MESSAGE_CONSTRAINTS);

        // invalid cycle
        assertInvalidValue(
                String.valueOf(INDEX_FIRST_OPPORTUNITY.getOneBased()) + INVALID_CYCLE_DESC,
                Cycle.PARSER_MESSAGE_CONSTRAINTS);

        // invalid phone
        assertInvalidValue(
                String.valueOf(INDEX_FIRST_OPPORTUNITY.getOneBased()) + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleInvalidValues_throwsParseException() {
        String userInput = String.valueOf(INDEX_FIRST_OPPORTUNITY.getOneBased())
                + INVALID_NAME_DESC
                + INVALID_EMAIL_DESC
                + INVALID_CONTACT_ROLE_DESC
                + INVALID_COMPANY_DESC
                + INVALID_ROLE_DESC
                + INVALID_STATUS_DESC
                + INVALID_CYCLE_DESC
                + INVALID_PHONE_DESC;

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
        assertParseFailure(parser, userInput, formatExpectedErrorMessage(expectedMessage));
    }

    private static String formatExpectedErrorMessage(String errorMessage) {
        return formatExpectedErrorMessages(errorMessage);
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
    public void parse_allErrorTypesJumbled_throwsAggregatedParseException() {
        // invalid index + duplicate prefixes + invalid values in jumbled order
        assertParseFailure(parser,
                "0"
                        + NAME_DESC_BOB + INVALID_EMAIL_DESC
                        + NAME_DESC_AMY
                        + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + INVALID_COMPANY_DESC
                        + ROLE_DESC_BOB
                        + STATUS_DESC_BOB + INVALID_STATUS_DESC
                        + PHONE_DESC_AMY + INVALID_PHONE_DESC,
                formatExpectedErrorMessages(
                        MESSAGE_INVALID_FORMAT,
                        getDuplicateFieldsMessage(
                                PREFIX_NAME,
                                PREFIX_COMPANY,
                                PREFIX_STATUS,
                                PREFIX_PHONE),
                        Email.MESSAGE_CONSTRAINTS,
                        Company.MESSAGE_CONSTRAINTS,
                        Status.MESSAGE_CONSTRAINTS,
                        Phone.MESSAGE_CONSTRAINTS));
    }
}
