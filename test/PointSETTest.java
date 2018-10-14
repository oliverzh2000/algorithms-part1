import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointSETTest {
    PointSET ps;

    @BeforeEach
    void setUp() {
        ps = new PointSET();
    }

    @Test
    void isEmpty() {
        assertTrue(ps.isEmpty());
        ps.insert(new Point2D(0, 0));
        assertFalse(ps.isEmpty());
    }

    @Test
    void size() {
        assertEquals(0, ps.size());
        ps.insert(new Point2D(0, 0));
        assertEquals(1, ps.size());
        ps.insert(new Point2D(0, 0));
        assertEquals(1, ps.size());
        ps.insert(new Point2D(1, 0));
        assertEquals(2, ps.size());
    }

    @Test
    void insert() {
        ps.insert(new Point2D(0, 0));
        assertTrue(ps.contains(new Point2D(0, 0)));
        ps.insert(new Point2D(10, 0));
        assertTrue(ps.contains(new Point2D(10, 0)));
        assertFalse(ps.contains(new Point2D(11, 0)));
    }

    @Test
    void contains() {
    }

    @Test
    void draw() {
    }

    @Test
    void range() {
        RectHV range = new RectHV(0, 0, 0.5, 0.5);
        ps.insert(new Point2D(0, 0));
        ps.insert(new Point2D(0.1, 0.1));
        ps.insert(new Point2D(0.5, 0.9));
    }

    @Test
    void nearest() {
    }

    @Test
    void main() {
    }
}