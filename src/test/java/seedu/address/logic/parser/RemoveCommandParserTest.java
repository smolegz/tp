package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemoveCommand;

/**
 * Contains unit tests for the RemoveCommandParser class.
 */
public class RemoveCommandParserTest {
    private RemoveCommandParser parser = new RemoveCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveCommand() {
        assertParseSuccess(parser, "1", new RemoveCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_INDEX, RemoveCommand.MESSAGE_USAGE));
    }
}
