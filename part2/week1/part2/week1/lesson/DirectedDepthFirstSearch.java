package part2.week1.lesson;

import edu.princeton.cs.algs4.Stack;

public class DirectedDepthFirstSearch {
    private final Digraph graph;
    private final int start;
    private boolean[] marked;
    private int[] edgeTo;

    public DirectedDepthFirstSearch(Digraph digraph, int start) {
        this.graph = digraph;
        this.start = start;
        this.marked = new boolean[digraph.V()];
        this.edgeTo = new int[digraph.V()];

        DFS(start);
    }

    private void DFS(int start) {
        marked[start] = true;
        for (int neighbor: graph.adj(start)) {
            if (!marked[neighbor]) {
                edgeTo[neighbor] = start;
                DFS(neighbor);
            }
        }
    }

    public boolean hasPathTo(int vertex) {
        return marked[vertex];
    }

    public Iterable<Integer> pathTo(int vertex) {
        if (!hasPathTo(vertex)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        while (edgeTo[vertex] != start) {
            path.push(vertex);
            vertex = edgeTo[vertex];
        }
        return path;
    }
}
