import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercolationTest {
    Percolation percSmall;

    @BeforeEach
    void setUp() {
        this.percSmall = new Percolation(4);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void open() {
        assertThrows(IllegalArgumentException.class, () -> percSmall.open(-1, 5));
    }

    @Test
    void isOpen() {
    }

    @Test
    void isFull() {
    }

    @Test
    void numberOfOpenSites() {
        assertEquals(0, percSmall.numberOfOpenSites());
        percSmall.open(1, 1);
        assertEquals(1, percSmall.numberOfOpenSites());
        percSmall.open(1, 1);
        assertEquals(1, percSmall.numberOfOpenSites());
    }

    @Test
    void percolates() {
        percSmall.open(1, 1);
        percSmall.open(2, 1);
        percSmall.open(3, 1);
        percSmall.open(4, 1);
        assertTrue(percSmall.percolates());
    }


    @Test
    void percolatesCornerCases() {
        Percolation perc1 = new Percolation(1);
        assertFalse(perc1.percolates());

        Percolation perc2 = new Percolation(2);
        assertFalse(perc2.percolates());
    }
}