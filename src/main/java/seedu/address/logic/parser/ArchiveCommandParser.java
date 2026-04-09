package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ArchiveCycleCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new archive-related command object.
 */
public class ArchiveCommandParser implements Parser<Command> {

    /**
     * Parses the given {@code String} of arguments in the context of the archive command
     * and returns an archive-related command object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));
        }

        String[] splitArgs = trimmedArgs.split("\\s+", 2);

        if (splitArgs[0].equalsIgnoreCase(ArchiveCycleCommand.SUBCOMMAND_WORD)) {
            return parseCycleArchiveCommand(splitArgs.length < 2 ? "" : splitArgs[1]);
        }

        return parseIndexArchiveCommand(trimmedArgs);
    }

    private Command parseCycleArchiveCommand(String cycleArgs) throws ParseException {
        try {
            if (cycleArgs.trim().isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCycleCommand.MESSAGE_USAGE));
            }

            return new ArchiveCycleCommand(ParserUtil.parseCycle(cycleArgs));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCycleCommand.MESSAGE_USAGE), pe);
        }
    }

    private Command parseIndexArchiveCommand(String trimmedArgs) throws ParseException {
        try {
            return new ArchiveCommand(ParserUtil.parseIndices(trimmedArgs));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE), pe);
        }
    }

}
