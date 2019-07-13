package part2.week2.lesson;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;

public class DijkstraSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> distPQ;

    public DijkstraSP(EdgeWeightedDigraph graph, int source) {
        edgeTo = new DirectedEdge[graph.V()];
        distTo = new double[graph.V()];
        distPQ = new IndexMinPQ<>(graph.V());
        for (int v = 0; v < graph.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[source] = 0.0;
        distPQ.insert(source, 0.0);
        // Consider vertices in increasing order of distance from source.
        while (!distPQ.isEmpty()) {
            int nextClosestVertex = distPQ.delMin();
            for (DirectedEdge edge : graph.adj(nextClosestVertex)) {
                if (relax(edge)) {
                    int w = edge.to();
                    if (distPQ.contains(w)) {
                        distPQ.decreaseKey(w, distTo[w]);
                    } else {
                        distPQ.insert(w, distTo[w]);
                    }
                }
            }
        }
    }

    // If e=v->w gives shorter path to w via v, successful relaxation.
    // Return true if successful. Otherwise false.
    private boolean relax(DirectedEdge edge) {
        int v = edge.from(), w = edge.to();
        if (distTo[w] > distTo[v] + edge.weight()) {
            distTo[w] = distTo[v] + edge.weight();
            edgeTo[w] = edge;
            return true;
        }
        return false;
    }
}