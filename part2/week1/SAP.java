import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SAP {
    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph digraph) {
        this.digraph = digraph;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return length(Collections.singletonList(v), Collections.singletonList(w));
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return ancestor(Collections.singletonList(v), Collections.singletonList(w));
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(digraph, w);

        List<Integer> commonAncestors = new ArrayList<>();
        for (int i = 0; i < digraph.V(); i++) {
            if (vPaths.hasPathTo(i) && wPaths.hasPathTo(i)) {
                commonAncestors.add(i);
            }
        }
        int shortestCA = commonAncestors
                .stream()
                .min(Comparator.comparingInt((Integer a) -> vPaths.distTo(a) + wPaths.distTo(a)))
                .orElse(-1);
        if (shortestCA != -1) {
            return vPaths.distTo(shortestCA) + wPaths.distTo(shortestCA);
        }
        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(digraph, w);

        List<Integer> commonAncestors = new ArrayList<>();
        for (int i = 0; i < digraph.V(); i++) {
            if (vPaths.hasPathTo(i) && wPaths.hasPathTo(i)) {
                commonAncestors.add(i);
            }
        }
        return commonAncestors
                .stream()
                .min(Comparator.comparingInt((Integer a) -> vPaths.distTo(a) + wPaths.distTo(a)))
                .orElse(-1);
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}