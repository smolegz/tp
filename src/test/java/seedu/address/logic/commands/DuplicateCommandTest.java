package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import javafx.collections.ObservableList;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests and unit tests for DuplicateCommand.
 */
public class DuplicateCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }
    @Test
    public void execute_personAcceptedByModel_duplicateSuccessful() throws Exception {
        Name name = new Name("Alice Pauline");
        Phone phone = new Phone("91234567");
        Email email = new Email("alicepauline@example.com");
        Address address = new Address("Blk 230, Sembawang Crescent");
        Person validPerson = new Person(name, phone, email, address, new HashSet<>());
        CommandResult commandResult = new DuplicateCommand(validPerson).execute(model);

        assertEquals(String.format(DuplicateCommand.MESSAGE_SUCCESS, Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        DuplicateCommand duplicateAliceCommand = new DuplicateCommand(alice);
        DuplicateCommand duplicateBobCommand = new DuplicateCommand(bob);

        // same object -> returns true
        assertTrue(duplicateAliceCommand.equals(duplicateAliceCommand));

        // same values -> returns true
        DuplicateCommand duplicateAliceCommandCopy = new DuplicateCommand(alice);
        assertTrue(duplicateAliceCommand.equals(duplicateAliceCommandCopy));

        // different types -> returns false
        assertFalse(duplicateAliceCommand.equals(1));

        // null -> returns false
        assertFalse(duplicateAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(duplicateAliceCommand.equals(duplicateBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDuplicatePerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public AddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDuplicatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Person getPerson(int indexOfTarget) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getPreviousList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSinglePersonList(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends DuplicateCommandTest.ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubDuplicatePersonAdded extends DuplicateCommandTest.ModelStub {
        final ArrayList<Person> personsDuplicated = new ArrayList<>();
        private boolean commitAddressBookCalled = false;

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsDuplicated.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addDuplicatePerson(Person person) {
            requireNonNull(person);
            personsDuplicated.add(person);
        }

        @Override
        public AddressBook getAddressBook() {
            return new AddressBook();
        }

        @Override
        public void commitAddressBook() {
            commitAddressBookCalled = true;
        }
    }

}
