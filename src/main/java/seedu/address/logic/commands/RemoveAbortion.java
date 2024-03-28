package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;
import seedu.address.logic.commands.exceptions.CommandException;

public class RemoveAbortion extends RemoveConfirmation {

    public static final String COMMAND_WORD = "no";
    public static final String MESSAGE_REMOVE_PERSON_ABORTED = "Removal of contact aborted.\n" +
            "Here is the list of existing contacts!";

    public CommandResult execute(Model model) throws CommandException {
        if (!isValidConfirmation(model)) {
            throw new CommandException(MESSAGE_INVALID_DECISION_MAKING);
        }
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_REMOVE_PERSON_ABORTED);
    }
}
