package seedu.address.logic.parser.fuzzytest;

import org.junit.jupiter.api.Test;
import seedu.address.logic.parser.fuzzy.BkTreeCommandMatcher;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BkTreeTest {
    @Test
    void testBkTree_FindClosestMatch() {
        List<String> items = Arrays.asList("taylar", "taytay", "tayler", "taylor");
        BkTreeCommandMatcher<String> bkTree = new BkTreeCommandMatcher<>(items);
        String query = "tailor";
        String expectedMatch = "taylor";
        assertEquals(expectedMatch, bkTree.findClosestMatch(query));
    }

    @Test
    void testBkTree_GetClosestDistance() {
        List<String> items = Arrays.asList("taylar", "taytay", "tayler", "taylor");
        BkTreeCommandMatcher<String> bkTree = new BkTreeCommandMatcher<>(items);
        String query = "taylo";
        bkTree.findClosestMatch(query);
        int expectedDistance = 1;
        assertEquals(expectedDistance, bkTree.getClosestDistance());
    }
}
