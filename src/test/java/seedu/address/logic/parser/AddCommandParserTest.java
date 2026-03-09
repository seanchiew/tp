package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalOpportunities.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.Role;
import seedu.address.testutil.OpportunityBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Opportunity expectedOpportunity = new OpportunityBuilder(BOB).build();

        // normal input
        assertParseSuccess(parser, COMPANY_DESC_BOB + ROLE_DESC_BOB, new AddCommand(expectedOpportunity));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = "Missing required fields. Company and Role are required.\n"
                                        + String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing company prefix
        assertParseFailure(parser, VALID_COMPANY_BOB + ROLE_DESC_BOB, expectedMessage);

        // missing role prefix
        assertParseFailure(parser, COMPANY_DESC_BOB + VALID_ROLE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid company
        assertParseFailure(parser, INVALID_COMPANY_DESC + ROLE_DESC_BOB, Company.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, COMPANY_DESC_BOB + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS);
    }
}
