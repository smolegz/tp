package seedu.address.logic.parser.fuzzytest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.fuzzy.LevenshteinDistance;

public class LevenshteinTest {
    @Test
    void testCalculate_sameInput() {
        String input = "pneumonoultramicroscopicsilicovolcanoconiosis";
        String compare = "pneumonoultramicroscopicsilicovolcanoconiosis";

        int expectedDistance = 0;
        LevenshteinDistance<String> distanceCal = new LevenshteinDistance<>();
        assertEquals(expectedDistance, distanceCal.calculateDistance(input, compare));
    }

    @Test
    void testCalculate_diffInput() {
        String input = "pneumonoultramicroscopicsilicovolcanoconiosis";
        String compare = "scoliosis";

        int expectedDistance = 36;
        LevenshteinDistance<String> distanceCal = new LevenshteinDistance<>();
        assertEquals(expectedDistance, distanceCal.calculateDistance(input, compare));
    }
}
