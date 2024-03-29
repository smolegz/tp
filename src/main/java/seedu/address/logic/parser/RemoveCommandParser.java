package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.RemoveCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new RemoveCommand object.
 * Determines if the user input argument is an index or a name.
 */
public class RemoveCommandParser {

    private static Index index;

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveCommand
     * and returns a RemoveCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveCommand parse(String args) throws ParseException {

        String[] identifiers = argsToArray(args);

        if (isIndexArg(identifiers)) {
            index = ParserUtil.parseIndex(identifiers[0]);
            return new RemoveCommand(index);
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
     * Returns true if the array only contains a single argument, and that single argument is an integer.
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
     * Returns the arguments keyed in by the user after the "remove" COMMAND_WORD, if any.
     */
    public String getRemoveCommandArguments(String removeFullCommand) {
        if (!isRemoveCommand(removeFullCommand)) {
            return "";
        } else {
            return removeFullCommand.trim().substring(7).trim();
        }
    }

    /**
     * Returns true if there are arguments, false if there are none following the "remove" COMMAND_WORD.
     */
    public boolean hasArgument(String arguments) {
        return (arguments != null) && (!arguments.isEmpty());
    }

    /**
     * Returns true if the remove command has an index argument.
     */
    public boolean hasIndexArgument(String fullCommand) {
        String arguments = getRemoveCommandArguments(fullCommand);
        return isRemoveCommand(fullCommand) && hasArgument(arguments) && isInteger(arguments);
    }

    /**
     * Returns true if the command is a valid "remove INDEX" command.
     */
    public boolean isValidRemoveIndexCommand(String fullCommand, Model model) {
        if (!hasIndexArgument(fullCommand)) {
            return false;
        } else {
            String arguments = getRemoveCommandArguments(fullCommand);
            List<Person> lastShownList = model.getPreviousList();
            logger.info("List size:" + lastShownList.size());
            logger.info("Index:" + arguments);
            return (index.getZeroBased() < lastShownList.size()) && (index.getZeroBased() > 0);
        }
    }

}
