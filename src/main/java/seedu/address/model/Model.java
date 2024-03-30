package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    AddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given person with a name that already exists in the address book.
     * {@code person}'s name already exists in the address book.
     */
    void addDuplicatePerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must be the same as another existing person in the address book.
     */
    void setDuplicatePerson(Person target, Person editedPerson);

    /**
     * Returns {@code person}'s name that already exists in the address book.
     */
    Person getPerson(int indexOfTarget);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the previous filtered list of persons.
     */
    ObservableList<Person> getPreviousList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the single person list to filter by the given {@code person}.
     * @throws NullPointerException if {@code person} is null.
     */
    void updateSinglePersonList(Person person);

    /**
     * Undoes the address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Returns true if {@code undo()} has address book state to restore to.
     */
    boolean canUndoAddressBook();

    /**
     * Redoes the address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Returns true if {@code redo()} has address book state to restore to, after an undo.
     */
    boolean canRedoAddressBook();

    /**
     * Saves a copy of the current {@code AddressBook} state at the end of the state list
     * after state-changing operations (e.g. add, remove).
     */
    void commitAddressBook();
}
