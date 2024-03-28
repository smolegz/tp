package seedu.address.logic.commands;

import seedu.address.ui.CommandBox;
import seedu.address.logic.parser.RemoveCommandParser;

/**
 * Prompts user confirmation upon remove to ensure safe deletion.
 */
public abstract class RemoveConfirmation extends Command {
    protected static final String MESSAGE_INVALID_DECISION_MAKING = "'yes' or 'no' is only used for decision making!\n"
            + "Please only use these two commands when prompted by the system during contact removal!";

    /**
     * Parser object to parse the previous command to verify if it is a remove command.
     */
    private final RemoveCommandParser removeParser = new RemoveCommandParser();

    /**
     * Checks if this confirmation command is valid, specifically used in the context of confirming a contact removal.
     *
     * @return True if the previous command is a remove command and has an index argument.
     */
    public boolean isValidConfirmation() {
        String previousCommand = CommandBox.getPreviousCommand().trim();
        if (removeParser.isRemoveCommand(previousCommand)) {
            return removeParser.hasIndexArgument(previousCommand);
        } else {
            return false;
        }
    }

}
