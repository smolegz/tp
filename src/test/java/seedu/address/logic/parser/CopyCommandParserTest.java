package seedu.address.logic.parser;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CopyCommand;

public class CopyCommandParserTest {

    private static final String WHITESPACE = " ";
    private static final String NAME_FIELD = "name";
    private static final String PHONE_FIELD = "phone";
    private static final String EMAIL_FIELD = "email";
    private static final String ADDRESS_FIELD = "address";

    private static final String INVALID_NAME_FIELD = "naame";
    private static final String INVALID_PHONE_FIELD = "phoene";
    private static final String INVALID_EMAIL_FIELD = "emailemail";
    private static final String INVALID_ADDRESS_FIELD = "daddress";

    private CopyCommandParser parser = new CopyCommandParser();

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void parse_nameField_success(int index) {
        String input = index + WHITESPACE + NAME_FIELD;
        ArrayList<String> fieldList = new ArrayList<>();
        fieldList.add(NAME_FIELD);
        assertParseSuccess(parser, input, new CopyCommand(Index.fromOneBased(index), fieldList));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void parse_phoneField_success(int index) {
        String input = index + WHITESPACE + PHONE_FIELD;
        ArrayList<String> fieldList = new ArrayList<>();
        fieldList.add(PHONE_FIELD);
        assertParseSuccess(parser, input, new CopyCommand(Index.fromOneBased(index), fieldList));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void parse_emailField_success(int index) {
        String input = index + WHITESPACE + EMAIL_FIELD;
        ArrayList<String> fieldList = new ArrayList<>();
        fieldList.add(EMAIL_FIELD);
        assertParseSuccess(parser, input, new CopyCommand(Index.fromOneBased(index), fieldList));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void parse_addressField_success(int index) {
        String input = index + WHITESPACE + ADDRESS_FIELD;
        ArrayList<String> fieldList = new ArrayList<>();
        fieldList.add(ADDRESS_FIELD);
        assertParseSuccess(parser, input, new CopyCommand(Index.fromOneBased(index), fieldList));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4 , 9, 555})
    public void parse_duplicateFields_success(int index) {
        String duplicateNameInput = index + WHITESPACE + NAME_FIELD + WHITESPACE + NAME_FIELD;
        ArrayList<String> fieldList = new ArrayList<>();
        fieldList.add(NAME_FIELD);
        fieldList.add(NAME_FIELD);
        assertParseSuccess(parser, duplicateNameInput, new CopyCommand(Index.fromOneBased(index), fieldList));

        fieldList.clear();
        String duplicatePhoneInput = index + WHITESPACE + PHONE_FIELD + WHITESPACE + PHONE_FIELD;
        fieldList.add(PHONE_FIELD);
        fieldList.add(PHONE_FIELD);
        assertParseSuccess(parser, duplicatePhoneInput, new CopyCommand(Index.fromOneBased(index), fieldList));

        fieldList.clear();
        String duplicateEmailInput = index + WHITESPACE + EMAIL_FIELD + WHITESPACE + EMAIL_FIELD;
        fieldList.add(EMAIL_FIELD);
        fieldList.add(EMAIL_FIELD);
        assertParseSuccess(parser, duplicateEmailInput, new CopyCommand(Index.fromOneBased(index), fieldList));

        fieldList.clear();
        String duplicateAddressInput = index + WHITESPACE + ADDRESS_FIELD + WHITESPACE + ADDRESS_FIELD;
        fieldList.add(ADDRESS_FIELD);
        fieldList.add(ADDRESS_FIELD);
        assertParseSuccess(parser, duplicateAddressInput, new CopyCommand(Index.fromOneBased(index), fieldList));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 9, 555})
    public void parse_multipleValidFields_success(int index) {
        String input = index + WHITESPACE + NAME_FIELD + WHITESPACE + PHONE_FIELD
                               + WHITESPACE + EMAIL_FIELD + WHITESPACE + ADDRESS_FIELD;
        List<String> fieldList = new ArrayList<>();
        fieldList.add("name");
        fieldList.add("phone");
        fieldList.add("email");
        fieldList.add("address");
        assertParseSuccess(parser, input, new CopyCommand(Index.fromOneBased(index), fieldList));

        input = index + WHITESPACE + EMAIL_FIELD + WHITESPACE + PHONE_FIELD + WHITESPACE + NAME_FIELD;
        fieldList.clear();
        fieldList.add("email");
        fieldList.add("phone");
        fieldList.add("name");
        assertParseSuccess(parser, input, new CopyCommand(Index.fromOneBased(index), fieldList));
    }

    @ParameterizedTest
    @MethodSource("indexAndFieldsProvider")
    public void parse_validAndInvalidField_failure(int index, String firstField, String secondField) {
        String input = index + WHITESPACE + firstField + WHITESPACE + secondField;
        assertParseFailure(parser, input, ParserUtil.MESSAGE_INVALID_FIELDS);
    }

    /**
     * Parameterized arguments of various index and fields.
     * @return Stream of arguments
     */
    private static Stream<Arguments> indexAndFieldsProvider() {
        return Stream.of(
                arguments(1, INVALID_NAME_FIELD, PHONE_FIELD),
                arguments(5, EMAIL_FIELD, INVALID_PHONE_FIELD),
                arguments(8, ADDRESS_FIELD, INVALID_EMAIL_FIELD),
                arguments(10, INVALID_PHONE_FIELD, INVALID_EMAIL_FIELD),
                arguments(800, INVALID_ADDRESS_FIELD, NAME_FIELD)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 9, 555})
    public void parse_multipleWhitespaces_failure(int index) {
        String input = index + WHITESPACE + WHITESPACE + NAME_FIELD;
        List<String> fieldList = new ArrayList<>();
        fieldList.add("name");
        assertParseFailure(parser, input, ParserUtil.MESSAGE_INVALID_FIELDS);

        input = index + WHITESPACE + PHONE_FIELD + WHITESPACE + WHITESPACE + EMAIL_FIELD;
        fieldList.clear();
        fieldList.add("phone");
        fieldList.add("email");
        assertParseFailure(parser, input, ParserUtil.MESSAGE_INVALID_FIELDS);
    }

    @ParameterizedTest
    @MethodSource("indexAndFieldProvider")
    public void parse_trailingAndLeadingWhitespaces_success(int index, String field) {
        // trailing whitespaces
        String input = index + WHITESPACE + field + WHITESPACE + WHITESPACE;
        List<String> fieldList = new ArrayList<>();
        fieldList.add(field);
        assertParseSuccess(parser, input, new CopyCommand(Index.fromOneBased(index), fieldList));

        // leading whitespaces
        input = WHITESPACE + WHITESPACE + WHITESPACE + index + WHITESPACE + field;
        assertParseSuccess(parser, input, new CopyCommand(Index.fromOneBased(index), fieldList));

        // leading and trailing whitespaces
        input = WHITESPACE + WHITESPACE + WHITESPACE + index + WHITESPACE + field + WHITESPACE + WHITESPACE;
        assertParseSuccess(parser, input, new CopyCommand(Index.fromOneBased(index), fieldList));
    }

    /**
     * Parameterized arguments of various index and fields.
     * @return Stream of arguments
     */
    private static Stream<Arguments> indexAndFieldProvider() {
        return Stream.of(
                arguments(1, NAME_FIELD),
                arguments(1, PHONE_FIELD),
                arguments(3, EMAIL_FIELD),
                arguments(800, ADDRESS_FIELD)
        );
    }
}
