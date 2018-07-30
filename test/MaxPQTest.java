import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxPQTest {
    MaxPQ<Integer> maxPQ;

    @BeforeEach
    void setUp() {
        this.maxPQ = new MaxPQ<>(100);
    }

    @Test
    void isEmpty() {

    }

    @Test
    void insert() {
        maxPQ.insert(4);
        maxPQ.insert(9);
        maxPQ.insert(6);
        maxPQ.insert(3);
        maxPQ.insert(5);
        maxPQ.insert(8);
        maxPQ.insert(10);
        maxPQ.insert(7);
        int i = 0;
    }

    @Test
    void delMax() {
        maxPQ.insert(6);
        maxPQ.insert(9);
        maxPQ.insert(10);
        maxPQ.insert(8);
        maxPQ.insert(7);

        assertEquals(10, (int) maxPQ.delMax());
        assertEquals(9, (int) maxPQ.delMax());
        assertEquals(8, (int) maxPQ.delMax());
        assertEquals(7, (int) maxPQ.delMax());
        assertEquals(6, (int) maxPQ.delMax());

        int i = 0;
    }
}