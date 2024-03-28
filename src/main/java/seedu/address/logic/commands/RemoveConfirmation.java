package seedu.address.logic.commands;

import seedu.address.ui.CommandBox;
import seedu.address.logic.parser.RemoveCommandParser;

/**
 * Prompts user confirmation upon remove to ensure safe deletion.
 */
public abstract class RemoveConfirmation extends Command {
    protected static final String MESSAGE_INVALID_DECISION_MAKING = "'yes' or 'no' is only used for decision making!"
            + "Please start with a valid command word!";

    private final RemoveCommandParser parserHelper = new RemoveCommandParser();

    // TO PROPERLY IMPLEMENT!! - done, left with slap
    // to check if previous command from command history is remove INDEX, as in other cases, typing in the command
    // "yes" or "no" will not make sense
    public boolean isValidConfirmation() {
        String previousCommand = CommandBox.getPreviousCommand().trim();
        if (previousCommand.startsWith("remove ")) {
            String indexString = previousCommand.substring(7).trim();
            if (indexString.isEmpty()) {
                return false;
            }
            try {
                Integer.parseInt(indexString);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
