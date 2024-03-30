package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

public class VersionedAddressBook extends AddressBook {
    private final List<ReadOnlyAddressBook> addressBookStateList;
    private int currentStatePointer;

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

    public boolean canUndoAddressBook() {
        return currentStatePointer > 0;
    }

    public boolean canRedoAddressBook() {
        return currentStatePointer < addressBookStateList.size() - 1;
    }

    /**
    * Thrown when an undo operation is called when there is no earlier address book state to restore to.
    */
    public static class NoUndoableStateException extends RuntimeException {
        public static final String UNDO_ERROR_MESSAGE = "There is no command to undo.";

        public NoUndoableStateException() {
            super(UNDO_ERROR_MESSAGE);
        }
    }

    /**
    * Thrown when a redo operation is called when there is no address book state to restore to.
    */
    public static class NoRedoableStateException extends RuntimeException {
        public static final String REDO_ERROR_MESSAGE = "There is no command to redo.";

        public NoRedoableStateException() {
            super(REDO_ERROR_MESSAGE);
        }
    }
}
