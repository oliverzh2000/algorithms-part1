package part2week1lesson;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.Stack;

public class DepthFirstPaths {
    private final Graph graph;
    private final int start;
    private boolean[] marked;
    private int[] edgeTo;

    public DepthFirstPaths(Graph graph, int start) {
        this.graph = graph;
        this.start = start;
        this.marked = new boolean[graph.V()];
        this.edgeTo = new int[graph.V()];
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

    boolean hasPathTo(int vertex) {
        return marked[vertex];
    }

    Iterable<Integer> pathTo(int vertex) {
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
