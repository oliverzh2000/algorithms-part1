package part2.week1.lesson;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.UF;

import java.util.ArrayList;
import java.util.List;

public class KruskalMST {
    private List<Edge> mst = new ArrayList<>();

    public KruskalMST(EdgeWeightedGraph graph) {
        MinPQ<Edge> edgePQ = new MinPQ<>();
        graph.edges().forEach(edgePQ::insert);

        UF mstForest = new UF(graph.V());
        while (!edgePQ.isEmpty() && mst.size() < graph.V() - 1) {
            Edge edge = edgePQ.min();
            int v = edge.either();
            int w = edge.other(v);
            if (!mstForest.connected(v, w)) {
                mst.add(edge);
                mstForest.union(v, w);
            }
        }
    }

    public Iterable<Edge> mst() {
        return mst;
    }
}
