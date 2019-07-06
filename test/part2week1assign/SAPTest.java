import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SAPTest {
    private static SAP sap1;

    @BeforeAll
    static void setUp() {
        sap1 = new SAP(new Digraph(new In("wordnet/digraph1.txt")));
    }

    @Test
    void length() {
        assertEquals(4, sap1.length(3, 11));
        assertEquals(3, sap1.length(9, 12));
        assertEquals(4, sap1.length(7, 2));
        assertEquals(-1, sap1.length(1, 6));
    }

    @Test
    void ancestor() {
        assertEquals(1, sap1.ancestor(3, 11));
        assertEquals(5, sap1.ancestor(9, 12));
        assertEquals(0, sap1.ancestor(7, 2));
        assertEquals(-1, sap1.ancestor(1, 6));
    }

    @Test
    void lengthIter() {
    }

    @Test
    void ancestorIter() {
    }

    @Test
    void main() {
    }
}