package seedu.address.logic.parser.fuzzytest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.fuzzy.BkTreeCommandMatcher;


public class BkTreeTest {
    @Test
    void testBkTreeFindClosestMatch() {
        List<String> items = Arrays.asList("taylar", "taytay", "tayler", "taylor");
        BkTreeCommandMatcher<String> bkTree = new BkTreeCommandMatcher<>(items);
        String query = "tailor";
        String expectedMatch = "taylor";
        assertEquals(expectedMatch, bkTree.findClosestMatch(query));
    }

    @Test
    void testBkTreeGetClosestDistance() {
        List<String> items = Arrays.asList("taylar", "taytay", "tayler", "taylor");
        BkTreeCommandMatcher<String> bkTree = new BkTreeCommandMatcher<>(items);
        String query = "taylo";
        bkTree.findClosestMatch(query);
        int expectedDistance = 1;
        assertEquals(expectedDistance, bkTree.getClosestDistance());
    }
}
