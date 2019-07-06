import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {
    private Digraph hypernyms;
    private List<Bag<String>> synsetNouns;
    private Map<String, Bag<Integer>> nounIds;
    private SAP sap;
    private final int N;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        this.synsetNouns = new ArrayList<>();

        In synsetsIn = new In(synsets);
        while (synsetsIn.hasNextLine()) {
            String line = synsetsIn.readLine();
            Bag synset = new Bag<>();
            for (String noun : line.split(",")[1].split(" ")) {
                synset.add(noun);
            }
            this.synsetNouns.add(synset);
        }
        this.N = this.synsetNouns.size();

        this.nounIds = new HashMap<>();
        // Loop over the nouns in each synset and add the noun->id mapping to nounIds.
        // nounIds is the inverse mapping of synsetNouns.
        for (int i = 0; i < N; i++) {
            for (String noun : this.synsetNouns.get(i)) {
                if (!this.nounIds.containsKey(noun)) {
                    this.nounIds.put(noun, new Bag<>());
                }
                this.nounIds.get(noun).add(i);
            }
        }

        this.hypernyms = new Digraph(N);
        In hypernymsIn = new In(hypernyms);
        for (int i = 0; i < N; i++) {
            String line = hypernymsIn.readLine();
            for (String idStr : line.split(",")) {
                if (i != Integer.parseInt(idStr)) {
                    int hypernymId = Integer.parseInt(idStr);
                    this.hypernyms.addEdge(i, hypernymId);
                }
            }
        }
        assert !hypernymsIn.hasNextLine();
        if (new DirectedCycle(this.hypernyms).hasCycle()) {
            throw new IllegalArgumentException("Hypernyms directed graph is cyclic.");
        }
        if (!isRooted(this.hypernyms)) {
            throw new IllegalArgumentException("Hypernyms directed graph is not rooted");
        }
        this.sap = new SAP(this.hypernyms);
    }

    private boolean isRooted(Digraph digraph) {
        if (new DirectedCycle(digraph).hasCycle()) {
            return false;
        }
        int rootCount = 0;
        for (int i = 0; i < digraph.V(); i++) {
            if (digraph.outdegree(i) == 0) {
                rootCount++;
            }
        }
        return rootCount == 1;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return nounIds.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        return sap.length(nounIds.get(nounA), nounIds.get(nounB));
    }

    // a synset (second field of synsetNouns.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        // Only need one noun from the common ancestor synset, so just take the first.
        return synsetNouns.get(sap.ancestor(nounIds.get(nounA), nounIds.get(nounB))).iterator().next();
    }

    // do unit testing of this class
    public static void main(String[] args) {
//        WordNet wn = new WordNet("wordnet/synsets3.txt", "wordnet/hypernyms3InvalidCycle.txt");
        WordNet wn = new WordNet("wordnet/synsets.txt", "wordnet/hypernyms.txt");

        System.out.println();
    }
}