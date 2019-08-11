package part2.week3.lesson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LSDStringSortTest {

    @Test
    void sort() {
        String[] original = {"dab", "add", "cab", "fad", "fee", "bad", "dad", "bee", "fed", "bed", "ebb", "ace"};
        String[] sorted = {"ace", "add", "bad", "bed", "bee", "cab", "dab", "dad", "ebb", "fad", "fed", "fee"};
        LSDStringSort.sort(original, 3);
        assertArrayEquals(original, sorted);
    }
}