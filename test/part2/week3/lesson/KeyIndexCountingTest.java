package part2.week3.lesson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyIndexCountingTest {

    @Test
    void sortTest() {
        int[] original = {3, 0, 2, 5, 5, 1, 3, 1, 5, 1, 4, 0};
        int[] sorted = {0, 0, 1, 1, 1, 2, 3, 3, 4, 5, 5, 5};

        KeyIndexCounting.sort(original, 6);
        assertArrayEquals(original, sorted);
    }
}