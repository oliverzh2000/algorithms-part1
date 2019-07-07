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
        int maxDist = Integer.MIN_VALUE;
        String outcast = nouns[0];
        for (String noun : nouns) {
            int dist = Arrays.stream(nouns).mapToInt((String otherNoun) -> wordNet.distance(otherNoun, noun)).sum();
            if (dist > maxDist) {
                outcast = noun;
                maxDist = dist;
            }
        }
        return outcast;
    }

    // see test client below
    public static void main(String[] args) {

    }
}