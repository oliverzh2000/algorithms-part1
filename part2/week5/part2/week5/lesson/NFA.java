package part2.week5.lesson;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;

public class NFA {
    private final char[] re;
    private final Digraph epsilonGraph;
    private final int M;

    public NFA(String regex) {
        re = regex.toCharArray();
        M = regex.length();
        epsilonGraph = epsilonTransitionsGraph();
    }

    public boolean matches(String text) {
        Bag<Integer> reacheableStates = new Bag<>();
        DirectedDFS dfs = new DirectedDFS(epsilonGraph, 0);
        // Add states reachable from start after taking e-transitions
        for (int v = 0; v < epsilonGraph.V(); v++) {
            if (dfs.marked(v)) {
                reacheableStates.add(v);
            }
        }

        for (int i = 0; i < text.length(); i++) {
            // Add states reachable after scanning text.charAt(i)
            Bag<Integer> matches = new Bag<>();
            for (int v : reacheableStates) {
                if (v != M && (re[v] == text.charAt(i) || re[v] == '.')) {
                    matches.add(v + 1);
                }
            }

            dfs = new DirectedDFS(epsilonGraph, matches);
            reacheableStates = new Bag<>();
            for (int v = 0; v < epsilonGraph.V(); v++) {
                if (dfs.marked(v)) {
                    reacheableStates.add(v);
                }
            }
        }
        for (int v : reacheableStates) {
            if (v == M) {
                return true;
            }
        }
        return false;
    }

    public Digraph epsilonTransitionsGraph() {

    }
}
