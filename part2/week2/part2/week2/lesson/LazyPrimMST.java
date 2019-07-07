package part2.week2.lesson;

import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

public class LazyPrimMST {
    List<Edge> mst = new ArrayList<>();

    public LazyPrimMST(EdgeWeightedGraph graph) {
        boolean[] marked = new boolean[graph.V()];
        MinPQ<Edge> edgesMinPQ = new MinPQ<>(); // Edges with (at least) one endpoint in mst

        graph.adj(0).forEach(edgesMinPQ::insert);
        while (!edgesMinPQ.isEmpty() && mst.size() < graph.V() - 1) {
            Edge minEdge = edgesMinPQ.delMin();
            mst.add(minEdge);
            int v = minEdge.either();
            int w = minEdge.other(v);
            if (!marked[v] || !marked[w]) {
                int unmarkedVertex = !marked[v] ? v : w;
                for (Edge edge : graph.adj(unmarkedVertex)) {
                    if (!marked[edge.other(unmarkedVertex)]) {
                        edgesMinPQ.insert(edge);
                    }
                }
                marked[unmarkedVertex] = true;
            }
        }
    }

    public Iterable<Edge> mst() {
        return mst;
    }
}
