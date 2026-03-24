package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OPPORTUNITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OPPORTUNITY;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ArchiveCycleCommand;
import seedu.address.model.opportunity.Cycle;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ArchiveCommandParser code. For example, inputs "1" and "1 abc" take the
 * same path through the ArchiveCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class ArchiveCommandParserTest {

    private ArchiveCommandParser parser = new ArchiveCommandParser();

    @Test
    public void parse_validArgs_returnsArchiveCommand() {
        assertParseSuccess(parser, "1", new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY)));
    }

    @Test
    public void parse_multipleValidArgs_returnsArchiveCommand() {
        assertParseSuccess(parser, "1 2",
                new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY, INDEX_SECOND_OPPORTUNITY)));
    }

    @Test
    public void parse_multipleValidArgsWithExtraSpaces_returnsArchiveCommand() {
        assertParseSuccess(parser, " \n 1 \t 2 \n",
                new ArchiveCommand(List.of(INDEX_FIRST_OPPORTUNITY, INDEX_SECOND_OPPORTUNITY)));
    }

    @Test
    public void parse_cycleArgs_returnsArchiveCycleCommand() {
        assertParseSuccess(parser, "cycle S1 2026",
                new ArchiveCycleCommand(new Cycle("S1 2026")));
    }

    @Test
    public void parse_secondCycleArgs_returnsArchiveCycleCommand() {
        assertParseSuccess(parser, "cycle S2 2026",
                new ArchiveCycleCommand(new Cycle("S2 2026")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_mixedValidAndInvalidArgs_throwsParseException() {
        assertParseFailure(parser, "1 a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_cycleWithoutCycle_throwsParseException() {
        assertParseFailure(parser, "cycle",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCycleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCycleArgs_throwsParseException() {
        assertParseFailure(parser, "cycle autumn 2026",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCycleCommand.MESSAGE_USAGE));
    }
}
