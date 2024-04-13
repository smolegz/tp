package seedu.address.logic.parser.fuzzytest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.fuzzy.FuzzyCommandParser;

public class FuzzyCommandParserTest {

    @Test
    void testFuzzyParserExact() {
        String userInput1 = "add";
        String expected1 = "add";
        assertEquals(expected1, FuzzyCommandParser.parseCommand(userInput1));

        String userInput2 = "delete";
        String expected2 = "delete";
        assertEquals(expected2, FuzzyCommandParser.parseCommand(userInput2));
    }

    @Test
    void testFuzzyParserWithinMaxError() {
        String userInput1 = "wdd";
        String expected = "add";
        assertEquals(expected, FuzzyCommandParser.parseCommand(userInput1));

        String userInput2 = "ado";
        assertEquals(expected, FuzzyCommandParser.parseCommand(userInput2));

        String userInput3 = "ad";
        assertEquals(expected, FuzzyCommandParser.parseCommand(userInput3));
    }

    @Test
    void testFuzzyParserBeyondMaxError() {
        String userInput1 = "adding";
        String expected = "error";
        assertEquals(expected, FuzzyCommandParser.parseCommand(userInput1));

        String userInput2 = "worcestershire";
        assertEquals(expected, FuzzyCommandParser.parseCommand(userInput2));

        String userInput3 = "a";
        assertEquals(expected, FuzzyCommandParser.parseCommand(userInput3));
    }
}
