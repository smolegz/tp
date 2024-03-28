package seedu.address.logic.commands;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.LogicManager;
import seedu.address.logic.parser.RemoveCommandParser;
import seedu.address.model.Model;
import seedu.address.ui.CommandBox;

/**
 * Prompts user confirmation upon remove to ensure safe deletion.
 */
public abstract class RemoveConfirmation extends Command {

    protected static final String MESSAGE_INVALID_DECISION_MAKING = "'yes' or 'no' is only used for decision making!\n"
            + "Please only use these two commands when prompted by the system during contact removal!";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    /**
     * Parser object to parse the previous command to verify if it is a remove command.
     */
    private final RemoveCommandParser removeParser = new RemoveCommandParser();

    /**
     * Checks if this confirmation command is valid, specifically used in the context of confirming a contact removal.
     *
     * @return True if the previous command is a remove command and has an index argument.
     */
    public boolean isValidConfirmation(Model model) {
        String previousCommand = CommandBox.getPreviousCommand().trim();
        logger.info("Previous command: " + previousCommand);
        return removeParser.isRemoveCommand(previousCommand)
                && removeParser.isValidRemoveIndexCommand(previousCommand, model);

    }

}
