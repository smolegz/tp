package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks the history of {@code AddressBook}.
 */
public class VersionedAddressBook extends AddressBook {

    /**
     * List of states that the address book has been in.
     */
    private final List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

    /**
     * Constructs a {@code VersionedAddressBook} with the given {@code ReadOnlyAddressBook}.
     *
     * @param initialState The initial state of the address book.
     */
    public VersionedAddressBook(ReadOnlyAddressBook initialState) {
        super(initialState);

        addressBookStateList = new ArrayList<>();
        addressBookStateList.add(new AddressBook(initialState));
        currentStatePointer = 0;
    }

    /**
    * Saves a copy of the current {@code AddressBook} state at the end of the state list.
    * Undoes any earlier states.
    */
    public void commit() {
        int size = addressBookStateList.size();
        addressBookStateList.subList(currentStatePointer + 1, size).clear();
        addressBookStateList.add(new AddressBook(this));
        currentStatePointer++;
    }

    /**
    * Restores the address book to its previous state.
    */
    public void undo() {
        if (currentStatePointer == 0) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
    * Restores the address book to its previously undone state.
    */
    public void redo() {
        if (currentStatePointer == addressBookStateList.size() - 1) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(addressBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book state to restore to.
     */
    public boolean canUndoAddressBook() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book state to restore to, after an undo.
     */
    public boolean canRedoAddressBook() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }

    /**
    * Handles case where an undo operation is called when there is no earlier address book state to restore to.
    */
    public static class NoUndoableStateException extends RuntimeException {
        /**
         * Error message for when there is no earlier address book state to restore to.
         */
        public static final String UNDO_ERROR_MESSAGE = "There is no command to undo.";

        /**
         * Constructs a {@code NoUndoableStateException} with the default error message.
         */
        public NoUndoableStateException() {
            super(UNDO_ERROR_MESSAGE);
        }
    }

    /**
    * Handles case where a redo operation is called when it is not preceded 'undo' and thus
    * no address book state to restore to.
    */
    public static class NoRedoableStateException extends RuntimeException {
        /**
         * Error message for when there is no address book state to restore to after an undo operation.
         */
        public static final String REDO_ERROR_MESSAGE = "There is no command to redo.";

        /**
         * Constructs a {@code NoRedoableStateException} with the default error message.
         */
        public NoRedoableStateException() {
            super(REDO_ERROR_MESSAGE);
        }
    }
}
