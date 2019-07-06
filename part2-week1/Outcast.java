import java.util.Arrays;
import java.util.Comparator;

public class Outcast {
    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        // To identify an outcast, compute the sum of the distances between each noun and every other one:
        // di = distance(xi, x1) + distance(xi, x2) + ... + distance(xi, xn)
        // and return a noun xt for which dt is maximum. Note that distance(xi, xi) = 0, so it will not contribute to the sum.
        return Arrays.stream(nouns)
                .max(Comparator.comparingInt(
                        (String currentNoun) -> Arrays.stream(nouns)
                                .mapToInt((String otherNoun) -> wordNet.distance(otherNoun, currentNoun))
                                .sum()))
                .get();
    }

    // see test client below
    public static void main(String[] args) {

    }
}