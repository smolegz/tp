package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undid the most recent command!\n"
            + "You may view the previous contact list now :)";
    public static final String MESSAGE_FAILURE = "There is no command to undo.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.canUndoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        model.undoAddressBook();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
