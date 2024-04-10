package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;



/**
 * Contains integration tests and unit tests for EditCommand.
 */
public class CopyCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setup() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        System.setProperty("java.awt.headless", "false");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 2, 5, 7, 6, 4})
    public void execute_copyName_success(int index) throws IOException, UnsupportedFlavorException {
        List<String> field = new ArrayList<>();
        field.add("name");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        assertCommandSuccess(new CopyCommand(Index.fromOneBased(index), field),
                model,
                CopyCommand.MESSAGE_SUCCESS, expectedModel);

        String actualResult = (String) clipboard.getData(DataFlavor.stringFlavor);
        String expectedResult = String.valueOf(expectedModel.getPerson(index - 1).getName());
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 2, 5, 7, 6, 4})
    public void execute_copyPhone_success(int index) throws IOException, UnsupportedFlavorException {
        List<String> field = new ArrayList<>();
        field.add("phone");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        assertCommandSuccess(new CopyCommand(Index.fromOneBased(index), field),
                model,
                CopyCommand.MESSAGE_SUCCESS, expectedModel);

        String actualResult = (String) clipboard.getData(DataFlavor.stringFlavor);
        String expectedResult = String.valueOf(expectedModel.getPerson(index - 1).getPhone());
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 2, 5, 7, 6, 4})
    public void execute_copyAddress_success(int index) throws IOException, UnsupportedFlavorException {
        List<String> field = new ArrayList<>();
        field.add("address");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        assertCommandSuccess(new CopyCommand(Index.fromOneBased(index), field),
                model,
                CopyCommand.MESSAGE_SUCCESS, expectedModel);

        String actualResult = (String) clipboard.getData(DataFlavor.stringFlavor);
        String expectedResult = String.valueOf(expectedModel.getPerson(index - 1).getAddress());
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 2, 5, 7, 6, 4})
    public void execute_copyEmail_success(int index) throws IOException, UnsupportedFlavorException {
        List<String> field = new ArrayList<>();
        field.add("email");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        assertCommandSuccess(new CopyCommand(Index.fromOneBased(index), field),
                model,
                CopyCommand.MESSAGE_SUCCESS, expectedModel);

        String actualResult = (String) clipboard.getData(DataFlavor.stringFlavor);
        String expectedResult = String.valueOf(expectedModel.getPerson(index - 1).getEmail());
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5})
    public void execute_duplicateName_success(int index) throws IOException, UnsupportedFlavorException {
        List<String> fields = Arrays.asList("name", "name");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        assertCommandSuccess(new CopyCommand(Index.fromOneBased(index), fields),
                model,
                CopyCommand.MESSAGE_SUCCESS, expectedModel);

        String actualResult = (String) clipboard.getData(DataFlavor.stringFlavor);
        String expectedResult = String.valueOf(expectedModel.getPerson(index - 1).getName());
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6})
    public void execute_duplicateEmail_success(int index) throws IOException, UnsupportedFlavorException {
        List<String> fields = Arrays.asList("email", "email" , "email");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        assertCommandSuccess(new CopyCommand(Index.fromOneBased(index), fields),
                model,
                CopyCommand.MESSAGE_SUCCESS, expectedModel);

        String actualResult = (String) clipboard.getData(DataFlavor.stringFlavor);
        String expectedResult = String.valueOf(expectedModel.getPerson(index - 1).getEmail());
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 7})
    public void execute_copyPhoneAndAddress_success(int index) throws IOException, UnsupportedFlavorException {
        List<String> fields = Arrays.asList("phone", "address");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        assertCommandSuccess(new CopyCommand(Index.fromOneBased(index), fields),
                model,
                CopyCommand.MESSAGE_SUCCESS, expectedModel);

        String actualResult = (String) clipboard.getData(DataFlavor.stringFlavor);
        Person person = expectedModel.getPerson(index - 1);
        String expectedResult = person.getPhone() + " " + person.getAddress();
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 5})
    public void execute_copyNameAndEmail_success(int index) throws IOException, UnsupportedFlavorException {
        List<String> fields = Arrays.asList("name", "email");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        assertCommandSuccess(new CopyCommand(Index.fromOneBased(index), fields),
                model,
                CopyCommand.MESSAGE_SUCCESS, expectedModel);

        String actualResult = (String) clipboard.getData(DataFlavor.stringFlavor);
        Person person = expectedModel.getPerson(index - 1);
        String expectedResult = person.getName() + " " + person.getEmail();
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 7, 4})
    public void execute_copyEmailWithDuplicateAddress_success(int index)
            throws IOException, UnsupportedFlavorException {
        List<String> fields = Arrays.asList("email", "address", "address");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        assertCommandSuccess(new CopyCommand(Index.fromOneBased(index), fields),
                model,
                CopyCommand.MESSAGE_SUCCESS, expectedModel);

        String actualResult = (String) clipboard.getData(DataFlavor.stringFlavor);
        Person person = expectedModel.getPerson(index - 1);
        String expectedResult = person.getEmail() + " " + person.getAddress();
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 100})
    public void execute_indexBeyondAddressBookSize_failure(int index) throws IndexOutOfBoundsException {
        List<String> fields = Arrays.asList("name", "email"); // does not matter
        assertCommandFailure(new CopyCommand(Index.fromOneBased(index), fields),
                model,
                CopyCommand.MESSAGE_PERSON_NOT_FOUND);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -20})
    public void execute_negativeIndex_throwsException(int index) throws IndexOutOfBoundsException {
        List<String> fields = Arrays.asList("name", "email"); // does not matter
        assertThrows(IndexOutOfBoundsException.class, () -> {
            new CopyCommand(Index.fromOneBased(index), fields); }
        );
    }

}
