package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Redoes the most recent undo command.
 */
public class RedoCommand extends Command {

        public static final String COMMAND_WORD = "redo";
        public static final String MESSAGE_SUCCESS = "Redid the most recent command";
        public static final String MESSAGE_FAILURE = "There is no command to redo";

        @Override
        public CommandResult execute(Model model) throws CommandException {
            if (!model.canRedoAddressBook()) {
                throw new CommandException(MESSAGE_FAILURE);
            }
            model.redoAddressBook();
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS);
        }
}
