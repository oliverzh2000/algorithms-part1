import edu.princeton.cs.algs4.In;

public class Outcast {
    private WordNet wordNet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String outcast = null;
        int max_dist_sum = 0;
        for (String noun : nouns) {
            int dist_sum = 0;
            for (String other : nouns) {
                dist_sum += wordNet.distance(noun, other);
            }
            if (dist_sum > max_dist_sum) {
                max_dist_sum = dist_sum;
                outcast = noun;
            }
        }
        return outcast;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        In in = new In("wordnet/outcast8.txt");
        String[] nouns = in.readAllStrings();
        System.out.println(outcast.outcast(nouns));
    }
}