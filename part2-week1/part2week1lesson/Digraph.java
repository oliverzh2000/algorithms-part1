package part2week1lesson;

import edu.princeton.cs.algs4.Bag;

public class Digraph {
    private final int V;
    private int E;
    private Bag<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        this.adj = (Bag<Integer>[]) new Bag[V];
        for (int i = 0; i < V; i++) {
            this.adj[i] = new Bag<>();
        }
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public Digraph reversed() {
        Digraph reversed = new Digraph(V());
        for (int i = 0; i < V(); i++) {
            for (int other: adj(i)) {
                reversed.addEdge(other, i);
            }
        }
        return reversed;
    }
}
