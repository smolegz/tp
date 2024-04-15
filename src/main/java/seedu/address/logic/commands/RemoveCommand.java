package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Removes a contact identified based on matching name and further confirmation of removal with index.
 */
public class RemoveCommand extends Command {

    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove the person identified by the matching index in the contacts list.\n"
            + "Parameters: INDEX\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String CONFIRMATION_MESSAGE_PROMPT = "Are you sure you want to remove this entry? (yes/no): ";

    private final Index targetIndex;

    /**
     * Constructs the second RemoveCommand with the given index of the contact.
     *
     * @param targetIndex Index of the contact to be removed.
     */
    public RemoveCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the RemoveCommand, ensuring safe removal of contacts, preventing accidental deletions.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A command result reflecting the type of RemoveCommand.
     * @throws CommandException If the command cannot be executed.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (isValidRemoveExecution()) {
            return removeExecutionHelper(model);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    /**
     * Checks if the second RemoveCommand constructor is used.
     */
    public boolean isValidRemoveExecution() {
        return targetIndex != null;
    }

    /**
     * Executes the second part of the RemoveCommand function.
     * Returns a CommandResult that seeks confirmation for the contact to remove.
     *
     * @param model {@code Model} which the command should operate on.
     * @return A command result reflecting the second type of RemoveCommand.
     * @throws CommandException If the index is invalid for removal.
     */
    public CommandResult removeExecutionHelper(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (!isValidRemovalIndex(targetIndex, lastShownList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        model.updateSinglePersonList(getPerson(targetIndex, lastShownList));
        return new CommandResult(CONFIRMATION_MESSAGE_PROMPT);
    }

    /**
     * Checks if the index is valid for removal.
     */
    private boolean isValidRemovalIndex(Index targetIndex, List<Person> lastShownList) {
        return (targetIndex.getZeroBased() < lastShownList.size()) && (targetIndex.getZeroBased() >= 0);
    }

    /**
     * Retrieves the person to be removed based on the index.
     */
    private Person getPerson(Index targetIndex, List<Person> lastShownList) {
        return lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveCommand)) {
            return false;
        }

        RemoveCommand otherRemoveCommand = (RemoveCommand) other;

        assert targetIndex != null;
        return targetIndex.equals(otherRemoveCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }

}
