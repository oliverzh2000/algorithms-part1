import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OutcastTest {
    private static Outcast outcast;

    @BeforeAll
    static void setUp() {
        outcast = new Outcast(new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt"));
    }

    @Test
    void outcast() {
        Assertions.assertEquals("table", outcast.outcast(new In("wordnet/outcast5.txt").readAllStrings()));
        Assertions.assertEquals("bed", outcast.outcast(new In("wordnet/outcast8.txt").readAllStrings()));
        Assertions.assertEquals("potato", outcast.outcast(new In("wordnet/outcast11.txt").readAllStrings()));
    }

    @Test
    void main() {
    }
}