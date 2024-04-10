package seedu.address;


import java.util.HashSet;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.FormattedCommandPerson;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.ui.Status;


/**
 * A helper class that prompts the user to enter all the
 * necessary fields one by one. It helps to format all the
 * fields into an "Add" Command so the user can copy it
 * over to LookMeUp.
 */
public class AddCommandHelper {

    private static final String COPY_COMMAND = "cp";

    private static final String SUCCESS_MESSAGE = "You have successfully entered ";

    private static final String NEXT_PROMPT = ". Next, please enter ";

    private static final String COMPLETE_COMMAND_MESSAGE = "You have entered all the fields! "
            + "Please enter \"cp\" to copy the command to your clipboard.";

    private static final String COPY_SUCCESSS_MESSAGE = "You have copied the command to your clipboard! "
            + "Type \"cp\" if you wish to copy again. You may close the window to go back to LookMeUp";


    private Status status;

    private Name name;

    private Email email;

    private Address address;
    private Phone phone;


    /**
     * Creates a new instance of AddCommandHelper
     */
    public AddCommandHelper() {
        this.status = Status.GET_NAME;
    }


    /**
     * Returns the outcome of a user's input.
     * If the input is valid, a success message is returned.
     * If the input is invalid, ParseException is thrown.
     *
     * @param text String representing user input.
     * @return Success message if the input is valid.
     * @throws ParseException if the user input is invalid.
     */
    public String getResponse(String text) throws ParseException {
        return processInput(text);
    }
    private String processInput(String input) throws ParseException {
        Status oldStatus = this.status;

        switch (this.status) {
        case GET_NAME:
            processName(input);
            break;
        case GET_NUMBER:
            processNumber(input);
            break;
        case GET_EMAIL:
            processEmail(input);
            break;
        case GET_ADDRESS:
            processAddress(input);
            break;
        case COMPLETE:
            processCommand(input);
            break;
        case COPY:
            break;
        default:
            assert false : "The status should only be those values";
        }
        String output = getSuccessMessage(oldStatus, status);

        return output;
    }
    private void setStatus(Status status) {
        switch (this.status) {
        case GET_NAME:
            this.status = Status.GET_NUMBER;
            break;
        case GET_NUMBER:
            this.status = Status.GET_EMAIL;
            break;
        case GET_EMAIL:
            this.status = Status.GET_ADDRESS;
            break;
        case GET_ADDRESS:
            this.status = Status.COMPLETE;
            break;
        case COMPLETE:
            this.status = Status.COPY;
            break;
        case COPY:
            break;
        default:
            assert false : "The status should only be those values";
        }
    }

    private void processName(String name) throws ParseException {
        Name processedName = ParserUtil.parseName(name);
        this.name = processedName;
        setStatus(this.status);
    }

    private void processNumber(String num) throws ParseException {
        Phone processedNum = ParserUtil.parsePhone(num);
        this.phone = processedNum;
        setStatus(this.status);
    }

    private void processEmail(String email) throws ParseException {
        Email processedEmail = ParserUtil.parseEmail(email);
        this.email = processedEmail;
        setStatus(this.status);
    }

    private void processAddress(String address) throws ParseException {
        Address processedAddress = ParserUtil.parseAddress(address);
        this.address = processedAddress;
        setStatus(this.status);
    }



    private void processCommand(String cmd) {
        if (cmd.equals(COPY_COMMAND)) {
            copyCommand();
            setStatus(this.status);
        }
    }

    private String formattedCommand() {
        assert this.status == Status.COMPLETE : "Command should have been completed "
                + "before this function is called.";
        FormattedCommandPerson p = new FormattedCommandPerson(name, phone, email, address, new HashSet<>());
        return "add " + p.getFormattedCommand();
    }



    private String getSuccessMessage(Status oldStatus, Status newStatus) {
        if (newStatus.equals(Status.COPY)) {
            return COPY_SUCCESSS_MESSAGE;
        } else if (newStatus.equals(Status.COMPLETE)) {
            return COMPLETE_COMMAND_MESSAGE;
        } else {
            return SUCCESS_MESSAGE + oldStatus + NEXT_PROMPT + newStatus;
        }

    }

    private void copyCommand() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent command = new ClipboardContent();
        assert this.status.equals(Status.COMPLETE) : "Command must be completed before "
                + "it is copied.";
        command.putString(formattedCommand());
        clipboard.setContent(command);
    }

    public Status getStatus() {
        return this.status;
    }

}
