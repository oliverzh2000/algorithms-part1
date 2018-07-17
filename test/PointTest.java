import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    private Point origin;

    @BeforeEach
    void setUp() {
        origin = new Point(0, 0);
    }

    @Test
    void slopeTo() {
        assertEquals(2.0, origin.slopeTo(new Point(1, 2)));
        assertEquals(2.5, origin.slopeTo(new Point(2, 5)));
        assertEquals(+0.0, origin.slopeTo(new Point(5, 0)));
        assertEquals(Double.POSITIVE_INFINITY, origin.slopeTo(new Point(0, 10)));
        assertEquals(Double.NEGATIVE_INFINITY, origin.slopeTo(origin));
    }

    @Test
    void compareTo() {
    }

    @Test
    void slopeOrder() {
    }
}