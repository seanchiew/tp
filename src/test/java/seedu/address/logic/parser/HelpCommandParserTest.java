package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HelpCommand;

public class HelpCommandParserTest {
    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_validArgs_returnsHelpCommand() {
        // EP: Empty strings and strings with only whitespaces
        assertParseSuccess(parser, "", new HelpCommand());
        assertParseSuccess(parser, "  ", new HelpCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // EP: Strings containing any alphanumeric characters or garbage
        assertParseFailure(parser, " 123", String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }
}
