package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.RemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new RemoveCommand object
 */
public class RemoveCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveCommand
     * and returns a RemoveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveCommand parse(String args) throws ParseException {

        String[] identifiers = argsToArray(args);

        if (isIndexArg(identifiers)) {
            return new RemoveCommand(ParserUtil.parseIndex(identifiers[0]));
        } else {
            return new RemoveCommand(new NameContainsKeywordsPredicate(Arrays.asList(identifiers)));
        }
    }

    /**
     * Splits the arguments into an array of strings.
     *
     * @param args The arguments that user has keyed in.
     * @return An array of string(s).
     * @throws ParseException If the user only input "remove" without any arguments.
     */
    public String[] argsToArray(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        }
        return trimmedArgs.split("\\s+");
    }

    /**
     * Returns true if the array only contains a single argument, which is an integer.
     *
     * @param args The arguments that user has keyed in.
     * @return True if the argument is a single index.
     */
    public boolean isIndexArg(String[] args) {
        return args.length == 1 && isInteger(args[0]);
    }

    /**
     * Returns true if the string is an integer.
     */
    public boolean isInteger(String str) {
        if (str.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns true if the command starts with COMMAND_WORD "remove".
     */
    public boolean isRemoveCommand(String fullCommand) {
        return fullCommand.trim().startsWith("remove");
    }

    /**
     * Returns the arguments of the remove command.
     */
    public String getRemoveCommandArguments(String removeFullCommand) {
        return removeFullCommand.trim().substring(7).trim();
    }

    /**
     * Returns true if the remove command has an index argument.
     */
    public boolean hasIndexArgument(String fullCommand) {
        if (!isRemoveCommand(fullCommand)) {
            return false;
        }
        String arguments = getRemoveCommandArguments(fullCommand);
        return isInteger(arguments);
    }
}
