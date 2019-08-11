package part2.week3.lesson;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreeWayQuicksortTest {

    @Test
    void sort() {
        String[] original = {"she", "sells", "seashells", "by", "the", "sea", "shore", "the", "shells", "she", "sells", "are", "surely", "seashells"};
        String[] sorted = {"are", "by", "sea", "seashells", "seashells", "sells", "sells", "she", "she", "shells", "shore", "surely", "the", "the"};
        MSDStringSort.sort(original);
        assertArrayEquals(original, sorted);
    }
}