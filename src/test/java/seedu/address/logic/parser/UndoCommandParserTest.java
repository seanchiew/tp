package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UndoCommand;

public class UndoCommandParserTest {
    private UndoCommandParser parser = new UndoCommandParser();

    @Test
    public void parse_validArgs_returnsUndoCommand() {
        // EP: Empty strings and strings with only whitespaces
        assertParseSuccess(parser, "", new UndoCommand());
        assertParseSuccess(parser, "  ", new UndoCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // EP: Strings containing any alphanumeric characters or garbage
        assertParseFailure(parser, " 123", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
    }
}
