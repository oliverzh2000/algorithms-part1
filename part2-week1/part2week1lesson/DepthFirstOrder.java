package part2week1lesson;

import edu.princeton.cs.algs4.Stack;

/**
 * Can be used for toplogical sort.
 */
public class DepthFirstOrder {
    private Digraph digraph;
    private boolean[] marked;
    private Stack<Integer> reversePost;

    public DepthFirstOrder(Digraph digraph) {
        this.digraph = digraph;
        this.marked = new boolean[digraph.V()];
        this.reversePost = new Stack<>();

        for (int v = 0; v < digraph.V(); v++) {
            if (!marked[v]) {
                DFS(v);
            }
        }
    }

    private void DFS(int v) {
        marked[v] = true;
        for (int w : digraph.adj(v)) {
            if (!marked[w]) {
                DFS(w);
            }
        }
        reversePost.push(v);
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
