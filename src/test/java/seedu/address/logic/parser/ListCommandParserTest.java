package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArgs_returnsListCommand() {
        // EP (Valid Partition 1): Empty strings and strings with only whitespaces (Main view)
        assertParseSuccess(parser, "", new ListCommand());
        assertParseSuccess(parser, "  ", new ListCommand());
    }

    @Test
    public void parse_archiveArg_returnsListCommandWithArchiveView() {
        // EP (Valid Partition 2): The exact string "archive" (with or without surrounding spaces)
        assertParseSuccess(parser, "archive", new ListCommand(true));
        assertParseSuccess(parser, " archive  ", new ListCommand(true));

        // Tests case-insensitivity
        assertParseSuccess(parser, " ARCHIVE ", new ListCommand(true));
        assertParseSuccess(parser, " ArCHivE ", new ListCommand(true));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // EP (Invalid Partition): Strings containing any other garbage arguments
        // BVA: "archives" (plural) or "archive 1" (extra args) are just outside the valid exact match boundary
        assertParseFailure(parser, " 123", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " abc", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " main", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " archives",
                                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " archive 1",
                                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}
