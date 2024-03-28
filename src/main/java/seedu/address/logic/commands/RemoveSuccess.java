package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Performs the actual deletion of contact after user has been prompted for confirmation.
 */
public class RemoveSuccess extends RemoveConfirmation {

    public static final String COMMAND_WORD = "yes";

    public static final String MESSAGE_REMOVE_PERSON_SUCCESS = "Successfully Removed Contact: %1$s\n"
            + "Here is the updated list of contacts!";

    private final int DEFAULT_INDEX = 0;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!isValidConfirmation()) {
            throw new CommandException(MESSAGE_INVALID_DECISION_MAKING);
        }
        Person personToRemove = model.getFilteredPersonList().get(DEFAULT_INDEX);
        model.deletePerson(personToRemove);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(successMessage(personToRemove));
    }

    /**
     * Returns the success message for the removal of the contact.
     * @param personToRemove Contact to be removed.
     * @return Success message for the removal of the contact.
     */
    public String successMessage(Person personToRemove) {
        return String.format(MESSAGE_REMOVE_PERSON_SUCCESS, Messages.format(personToRemove));
    }
}
