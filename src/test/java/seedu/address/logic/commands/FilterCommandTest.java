package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for FilterCommand.
 */
public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @ParameterizedTest
    @ValueSource(strings = {"Friends", "Colleagues", "Neighbours"})
    public void execute_filteredListByTag_success(String tag) {
        String[] tagKeywords = tag.split("\\s+");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords));
        FilterCommand filterCommand =  new FilterCommand(predicate);

        String expectedMessage = String.format(FilterCommand.MESSAGE_SUCCESS);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
    }
    @ParameterizedTest
    @ValueSource(strings = {"Alex", "Bernice", "Charlotte"})
    public void execute_filteredListByName_produceEmptyList_success(String name) {
        String trimmedArgs = "name " + name;
        String[] tagKeywords = trimmedArgs.split("\\s+");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords));
        FilterCommand filterCommand =  new FilterCommand(predicate);

        String expectedMessage = String.format(FilterCommand.MESSAGE_SUCCESS);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
    }

    @ParameterizedTest
    @ValueSource(strings = {"87438807", "99272758", "93210283"})
    public void execute_filteredListByPhone_produceEmptyList_success(String phone) {
        String trimmedArgs = "phone " + phone;
        String[] tagKeywords = trimmedArgs.split("\\s+");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords));
        FilterCommand filterCommand =  new FilterCommand(predicate);

        String expectedMessage = String.format(FilterCommand.MESSAGE_SUCCESS);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Blk 30 Geylang Street 29, #06-40", "Blk 30 Lorong 3 Serangoon Gardens, #07-18",
            "Blk 11 Ang Mo Kio Street 74, #11-04"})
    public void execute_filteredListByAddress_produceEmptyList_success(String address) {
        String trimmedArgs = "address " + address;
        String[] tagKeywords = trimmedArgs.split("\\s+");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords));
        FilterCommand filterCommand =  new FilterCommand(predicate);

        String expectedMessage = String.format(FilterCommand.MESSAGE_SUCCESS);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
    }

    @ParameterizedTest
    @ValueSource(strings = {"alexyeoh@example.com", "berniceyu@example.com", "charlotte@example.com"})
    public void execute_filteredListByEmail_produceEmptyList_success(String email) {
        String trimmedArgs = "email " + email;
        String[] tagKeywords = trimmedArgs.split("\\s+");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords));
        FilterCommand filterCommand =  new FilterCommand(predicate);

        String expectedMessage = String.format(FilterCommand.MESSAGE_SUCCESS);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "91247393", "aLeX"})
    public void execute_filteredListByInvalidValue_produceEmptyList_success(String name) {
        String trimmedArgs = "name " + name;
        String[] tagKeywords = trimmedArgs.split("\\s+");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords));
        FilterCommand filterCommand = new FilterCommand(predicate);

        String expectedMessage = String.format(FilterCommand.MESSAGE_SUCCESS);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateFilteredPersonList(predicate);

        assertCommandSuccess(filterCommand, model, expectedMessage, expectedModel);
    }
}
