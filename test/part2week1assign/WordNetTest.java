import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordNetTest {
    private static WordNet wordNet;

    @BeforeAll
    static void setUp() {
        wordNet = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt");
    }

    @Test
    void constructor() {
        assertThrows(IllegalArgumentException.class,
                () -> new WordNet("wordnet/synsets3.txt", "wordnet/hypernyms3InvalidCycle.txt"));
        assertThrows(IllegalArgumentException.class,
                () -> new WordNet("wordnet/synsets3.txt", "wordnet/hypernyms3InvalidTwoRoots.txt"));

        assertThrows(IllegalArgumentException.class,
                () -> new WordNet("wordnet/synsets6.txt", "wordnet/hypernyms6InvalidCycle+Path.txt"));
        assertThrows(IllegalArgumentException.class,
                () -> new WordNet("wordnet/synsets6.txt", "wordnet/hypernyms6InvalidCycle.txt"));
        assertThrows(IllegalArgumentException.class,
                () -> new WordNet("wordnet/synsets6.txt", "wordnet/hypernyms6InvalidTwoRoots.txt"));
    }

    @Test
    void nouns() {
        long numNouns = wordNet.nouns().spliterator().getExactSizeIfKnown();
        assertEquals(119188, numNouns);
    }

    @Test
    void isNoun() {
        assertTrue(wordNet.isNoun("fluid_drive"));
        assertFalse(wordNet.isNoun("fluid_drivet"));
    }

    @Test
    void distance() {
        assertEquals(23, wordNet.distance("white_marlin", "mileage"));
        assertEquals(23, wordNet.distance("white_marlin", "mileage"));
        assertEquals(33, wordNet.distance("Black_Plague", "black_marlin"));
        assertEquals(27, wordNet.distance("American_water_spaniel", "histology"));
        assertEquals(29, wordNet.distance("Brown_Swiss", "barrel_roll"));
    }

    @Test
    void sap() {
    }

    @Test
    void main() {
    }
}