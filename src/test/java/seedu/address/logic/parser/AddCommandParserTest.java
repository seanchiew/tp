package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CONTACT_ROLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CYCLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CYCLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CYCLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalOpportunities.AMY;
import static seedu.address.testutil.TypicalOpportunities.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.Cycle;
import seedu.address.model.opportunity.Email;
import seedu.address.model.opportunity.Name;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.Role;
import seedu.address.model.opportunity.Status;
import seedu.address.testutil.OpportunityBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // BOB has no phone
        Opportunity expectedOpportunity = new OpportunityBuilder(BOB).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                new AddCommand(expectedOpportunity));

        // AMY has phone
        Opportunity expectedWithPhone = new OpportunityBuilder(AMY).build();
        assertParseSuccess(parser,
                NAME_DESC_AMY + EMAIL_DESC_AMY + CONTACT_ROLE_DESC_AMY
                        + COMPANY_DESC_AMY + ROLE_DESC_AMY + STATUS_DESC_AMY
                        + CYCLE_DESC_AMY + PHONE_DESC_AMY,
                new AddCommand(expectedWithPhone));
    }

    @Test
    public void parse_optionalFieldMissing_success() {
        // phone is optional — BOB has no phone and should still parse successfully
        Opportunity expectedOpportunity = new OpportunityBuilder(BOB).build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                new AddCommand(expectedOpportunity));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser,
                EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                + CYCLE_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + CONTACT_ROLE_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                + CYCLE_DESC_BOB,
                expectedMessage);

        // missing contact role prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + EMAIL_DESC_BOB + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                + CYCLE_DESC_BOB,
                expectedMessage);

        // missing company prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + VALID_COMPANY_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                expectedMessage);

        // missing role prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + VALID_ROLE_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                expectedMessage);

        // missing status prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + CYCLE_DESC_BOB,
                expectedMessage);

        // missing cycle prefix
        assertParseFailure(parser, NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB,
                        expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser,
                INVALID_NAME_DESC + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser,
                NAME_DESC_BOB + INVALID_EMAIL_DESC + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid company
        assertParseFailure(parser,
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + INVALID_COMPANY_DESC + ROLE_DESC_BOB + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                Company.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser,
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + INVALID_ROLE_DESC + STATUS_DESC_BOB
                        + CYCLE_DESC_BOB,
                Role.MESSAGE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser,
                NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                        + COMPANY_DESC_BOB + ROLE_DESC_BOB + INVALID_STATUS_DESC
                        + CYCLE_DESC_BOB,
                Status.MESSAGE_CONSTRAINTS);

        // invalid cycle
        assertParseFailure(parser, NAME_DESC_BOB + EMAIL_DESC_BOB + CONTACT_ROLE_DESC_BOB
                + COMPANY_DESC_BOB + ROLE_DESC_BOB + STATUS_DESC_BOB + INVALID_CYCLE_DESC,
                Cycle.MESSAGE_CONSTRAINTS);
    }
}
