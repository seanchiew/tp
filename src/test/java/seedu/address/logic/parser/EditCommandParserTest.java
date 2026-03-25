package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CYCLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_CLEAR;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CYCLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OPPORTUNITY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditOpportunityDescriptor;
import seedu.address.model.opportunity.Company;
import seedu.address.model.opportunity.Phone;
import seedu.address.model.opportunity.Role;
import seedu.address.testutil.EditOpportunityDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_COMPANY_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_COMPANY_DESC, Company.MESSAGE_CONSTRAINTS); // invalid company
        assertParseFailure(parser, "1" + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS); // invalid role

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_COMPANY_DESC + INVALID_ROLE_DESC, Company.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_OPPORTUNITY;
        String userInput = targetIndex.getOneBased() + COMPANY_DESC_BOB + ROLE_DESC_AMY;

        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder().withCompany(VALID_COMPANY_BOB)
                                        .withRole(VALID_ROLE_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_OPPORTUNITY;
        String userInput = targetIndex.getOneBased() + COMPANY_DESC_BOB;

        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder().withCompany(VALID_COMPANY_BOB)
                                        .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_cycleOnlySpecified_success() {
        Index targetIndex = INDEX_FIRST_OPPORTUNITY;
        String userInput = targetIndex.getOneBased() + CYCLE_DESC_BOB;

        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder()
                .withCycle(VALID_CYCLE_BOB).build();
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
                .withPhone(VALID_PHONE_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidPhoneValue_failure() {
        // A phone that is non-empty but invalid should still fail
        assertParseFailure(parser, "1 " + " p/abc", Phone.MESSAGE_CONSTRAINTS);
    }
}
