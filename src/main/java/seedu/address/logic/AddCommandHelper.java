package seedu.address.logic;


import java.util.HashSet;
import java.util.logging.Logger;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import seedu.address.commons.core.LogsCenter;
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

    private static final String COMPLETE_COMMAND_MESSAGE = "You have entered all the fields! "
            + "Please enter \"cp\" to copy the command to your clipboard.";

    private static final String COPY_SUCCESSS_MESSAGE = "You have copied the command to your clipboard! "
            + "Type \"cp\" if you wish to copy again. You may close the window to go back to LookMeUp";

    private static final String NEXT_PROMPT = ". Next, please enter ";

    private static final String SUCCESS_MESSAGE = "You have successfully entered ";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);


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
            checkNameIsValid(input);
            break;
        case GET_NUMBER:
            checkNumberIsValid(input);
            break;
        case GET_EMAIL:
            checkEmailIsValid(input);
            break;
        case GET_ADDRESS:
            checkAddressIsValid(input);
            break;
        case COMPLETE:
            checkCommandIsCopy(input);
            break;
        case COPY:
            break;
        default:
            assert false : "The status should only be those values";
        }
        Status newStatus = this.status;
        String output = getSuccessMessage(oldStatus, newStatus);

        return output;
    }
    private void updateStatus(Status status) {
        Status oldStatus = this.status;
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
        Status newStatus = this.status;
        logger.info("Status has been changed from " + oldStatus + " to " + newStatus + ". ");
    }

    /**
     * Checks whether the name entered by the user is valid.
     * If the name is valid, the status will be updated.
     * If the name is invalid, ParseException is thrown.
     *
     * @param name String representing the name entered by the user.
     * @throws ParseException if the name entered by the user is invalid.
     */
    private void checkNameIsValid(String name) throws ParseException {
        Name processedName = ParserUtil.parseName(name);
        this.name = processedName;
        logger.info("Name that finished processing is: " + name + ".");
        updateStatus(this.status);
    }

    /**
     * Checks whether the number entered by the user is valid.
     * If the number is valid, the status will be updated.
     * If the number is invalid, ParseException is thrown.
     *
     * @param number String representing the number entered by the user.
     * @throws ParseException if the number entered by the user is invalid.
     */
    private void checkNumberIsValid(String number) throws ParseException {
        Phone processedNumber = ParserUtil.parsePhone(number);
        this.phone = processedNumber;
        logger.info("Number that finished processing is: " + number + ".");
        updateStatus(this.status);
    }

    /**
     * Checks whether the email entered by the user is valid.
     * If the email is valid, the status will be updated.
     * If the email is invalid, ParseException is thrown.
     *
     * @param email String representing the email entered by the user.
     * @throws ParseException if the email entered by the user is invalid.
     */
    private void checkEmailIsValid(String email) throws ParseException {
        Email processedEmail = ParserUtil.parseEmail(email);
        this.email = processedEmail;
        logger.info("Email that finished processing is: " + email + ".");
        updateStatus(this.status);
    }

    /**
     * Checks whether the address entered by the user is valid.
     * If the address is valid, the status will be updated.
     * If the address is invalid, ParseException is thrown.
     *
     * @param address String representing the address entered by the user.
     * @throws ParseException if the address entered by the user is invalid.
     */
    private void checkAddressIsValid(String address) throws ParseException {
        Address processedAddress = ParserUtil.parseAddress(address);
        this.address = processedAddress;
        logger.info("Address that finished processing is: " + address + ".");
        updateStatus(this.status);
    }


    private void checkCommandIsCopy(String cmd) {
        if (cmd.equals(COPY_COMMAND)) {
            copyCommand();
            updateStatus(this.status);
        }
    }

    private String formattedCommand() {
        assert this.status.equals(Status.COMPLETE) : "Command should have been completed "
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
