import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Part1Week3Test {
    private int[] arraySmall;

    @BeforeEach
    void setUp() {
        arraySmall = new int[] {10, 9, 8, 3, 5, 1, 8, 8};
    }

    @Test
    void swapTest() {
        Week3.swap(arraySmall, 0, 1);
        assertEquals(9, arraySmall[0]);
        assertEquals(10, arraySmall[1]);
    }

    @Test
    void mergeTest() {
        int[] array = {1, 20, 2, 21};
        Week3.merge(array, new int[4], 0, 2, 4);
    }

    @Test
    void mergeSortTest() {
        Week3.mergeSort(arraySmall, new int[arraySmall.length], 0, arraySmall.length);
        assertTrue(Week3.isSorted(arraySmall, 0, arraySmall.length));
    }

    @Test
    void isSortedTest() {
        assertTrue(Week3.isSorted(new int[]{0, 0, 1, 2, 3, 4, 5, 5, 5, 6, 10, 100, 100000}, 0, 6));
        assertFalse(Week3.isSorted(new int[]{1, 2, 3, 4, 6, 5}, 0, 6));
    }

    @Test
    void bottomUpMergeSortTest() {
        Week3.bottomUpMergeSort(arraySmall);
        assertTrue(Week3.isSorted(arraySmall, 0, arraySmall.length));
    }
}