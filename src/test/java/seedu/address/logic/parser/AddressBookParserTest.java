package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OPPORTUNITY;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ArchiveCycleCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditOpportunityDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListArchiveCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.opportunity.Cycle;
import seedu.address.model.opportunity.Opportunity;
import seedu.address.model.opportunity.OpportunityContainsSubstringPredicate;
import seedu.address.testutil.EditOpportunityDescriptorBuilder;
import seedu.address.testutil.OpportunityBuilder;
import seedu.address.testutil.OpportunityUtil;

/**
 * Tests the routing logic of the AddressBookParser.
 */
public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Opportunity opportunity = new OpportunityBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(OpportunityUtil.getAddCommand(opportunity));
        assertEquals(new AddCommand(opportunity), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + "  ") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_OPPORTUNITY.getOneBased());
        assertEquals(new DeleteCommand(List.of(INDEX_FIRST_OPPORTUNITY)), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Opportunity opportunity = new OpportunityBuilder().build();
        EditOpportunityDescriptor descriptor = new EditOpportunityDescriptorBuilder(opportunity).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_OPPORTUNITY.getOneBased() + " "
                + OpportunityUtil.getEditOpportunityDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_OPPORTUNITY, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + "  ") instanceof ExitCommand);

    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new OpportunityContainsSubstringPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findWithCompany() throws Exception {
        FindCommand command = (FindCommand) parser.parseCommand(FindCommand.COMMAND_WORD + " alex c/meta");
        assertEquals(new FindCommand(new OpportunityContainsSubstringPredicate(List.of("alex"), List.of("meta"))),
                command);
    }

    @Test
    public void parseCommand_findArchived() throws Exception {
        FindCommand command = (FindCommand) parser.parseCommand(FindCommand.COMMAND_WORD + " c/meta a/alex");
        assertEquals(new FindCommand(new OpportunityContainsSubstringPredicate(List.of("alex"), List.of("meta")),
                true), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + "  ") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        // Routes to standard ListCommand
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + "  ") instanceof ListCommand);

        // Routes to ListArchiveCommand
        assertTrue(parser.parseCommand(ListArchiveCommand.COMMAND_WORD) instanceof ListArchiveCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_mixedCaseCommandWord_returnsCommand() throws Exception {
        Opportunity validOpportunity = new OpportunityBuilder().build();

        assertTrue(parser.parseCommand("aDd " + OpportunityUtil
                                        .getOpportunityDetails(validOpportunity)) instanceof AddCommand);

        assertTrue(parser.parseCommand("cLeAr") instanceof ClearCommand);
        assertTrue(parser.parseCommand("eXiT") instanceof ExitCommand);
        assertTrue(parser.parseCommand("lIsT") instanceof ListCommand);
        assertTrue(parser.parseCommand("dElEtE 1") instanceof DeleteCommand);
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_archive() throws Exception {
        assertTrue(parser.parseCommand("archive 1") instanceof ArchiveCommand);
    }

    @Test
    public void parseCommand_archiveCycle() throws Exception {
        assertEquals(new ArchiveCycleCommand(new Cycle("SUMMER 2026")),
                parser.parseCommand("archive cycle SUMMER 2026"));
    }

    @Test
    public void parseCommand_unarchive() throws Exception {
        assertTrue(parser.parseCommand("unarchive 1") instanceof UnarchiveCommand);
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + "  ") instanceof UndoCommand);
    }
}
