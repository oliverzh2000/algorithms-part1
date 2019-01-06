import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WordNet {
    private SAP sap;
    private String[][] synsets;  // synsets[i] gives the Strings belonging to the ith synset
    private Map<String, ArrayList<Integer>> nounToSynset = new HashMap<>();  // map nounToSynset to the synsets the belong to
    private Digraph hypernyms;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("arguments cannot be null");
        String[] synsetsLines = (new In(synsets)).readAllLines();
        this.synsets = new String[synsetsLines.length][];
        for (int i = 0; i < synsetsLines.length; i++) {
            String[] synset = synsetsLines[i].split(",");
            this.synsets[i] = synset[1].split(" ");
        }
        String[] hypernymsLines = (new In(hypernyms)).readAllLines();
        this.hypernyms = new Digraph(this.synsets.length);
        for (int i = 0; i < hypernymsLines.length; i++) {
            String[] line = hypernymsLines[i].split(",");
            for (int j = 1; j < line.length; j++) {
                this.hypernyms.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[j]));
            }
        }
        if ((new DirectedCycle(this.hypernyms)).hasCycle())
            throw new IllegalArgumentException("the input is not a DAG: has cycle");
        int roots = 0;
        for (int i = 0; i < this.hypernyms.V(); i++)
            if (this.hypernyms.outdegree(i) == 0) roots++;
        if (roots != 1)
            throw new IllegalArgumentException("the input is not a DAG: does not have exactly one root");
        for (int i = 0; i < this.synsets.length; i++) {
            for (String noun : this.synsets[i]) {
                nounToSynset.putIfAbsent(noun, new ArrayList<>());
                nounToSynset.get(noun).add(i);
            }
        }
        this.sap = new SAP(this.hypernyms);
//        System.out.println(Arrays.deepToString(this.synsets));
//        System.out.println(this.hypernyms);
//        System.out.println(this.nounToSynset);
    }

    // returns all WordNet nounToSynset
    public Iterable<String> nouns() {
        return this.nounToSynset.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("word cannot be null");
        return this.nounToSynset.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!(isNoun(nounA) && isNoun(nounB)))
            throw new IllegalArgumentException("arguments are not WordNet nouns");
        return sap.length(nounToSynset.get(nounA), nounToSynset.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!(isNoun(nounA) && isNoun(nounB)))
            throw new IllegalArgumentException("arguments are not WordNet nouns");
        return String.join(" ", synsets[sap.ancestor(nounToSynset.get(nounA), nounToSynset.get(nounB))]);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("wordnet/synsets15.txt", "wordnet/hypernyms15Tree.txt");
    }
}