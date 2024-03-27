package seedu.address.logic.commands;

import seedu.address.logic.commands.Command;

/**
 * Prompts user confirmation upon remove to ensure safe deletion.
 */
public abstract class RemoveConfirmation extends Command {

    protected static final String MESSAGE_INVALID_DECISION_MAKING = "'yes' or 'no' is only used for decision making!"
            + "Please start with a valid command word!";

    // TO PROPERLY IMPLEMENT!!
    // to check if previous command from command history is remove INDEX, as in other cases, typing in the command
    // "yes" or "no" will not make sense
    public boolean isValidConfirmation() {
        return true;
    }
}
