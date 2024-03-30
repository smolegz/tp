package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Copy the fields of a person based on the provided index.
 */
public class CopyCommand extends Command {

    public static final String COMMAND_WORD = "copy";
    public static final String MESSAGE_SUCCESS = "Successfully copied!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Copies the information of a contact.\n"
            + "copy INDEX name/contact/address/email\n"
            + "Example: copy 1 name email\n"
            + "INDEX should be a positive integer";

    private final Index targetIndex;
    private final List<String> fieldsToCopyList;

    /**
     * Creates a CopyCommand to copy a person's fields based on provided {@code Index}.
     * @param fieldsToCopyList Fields to copy.
     */
    public CopyCommand(Index targetIndex, List<String> fieldsToCopyList) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.fieldsToCopyList = fieldsToCopyList.stream()
                                  .distinct()
                                  .collect(Collectors.toList());
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Person person = model.getPerson(targetIndex.getZeroBased());
        StringBuilder result = new StringBuilder();

        for (String field : fieldsToCopyList) {
            if (field.equals("name")) {
                Name name = person.getName();
                result.append(name).append(" ");
            }
            if (field.equals("phone")) {
                Phone phone = person.getPhone();
                result.append(phone).append(" ");
            }
            if (field.equals("email")) {
                Email email = person.getEmail();
                result.append(email).append(" ");
            }
            if (field.equals("address")) {
                Address address = person.getAddress();
                result.append(address).append(" ");
            }
        }

        StringSelection toCopyString = new StringSelection(result.toString().trim());
        clipboard.setContents(toCopyString, null);

        return new CommandResult(MESSAGE_SUCCESS, false, false);
    }


}
