package part2.week2.lesson;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;

public class DijkstraSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> distPQ;

    public DijkstraSP(EdgeWeightedDigraph G, int source) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        distPQ = new IndexMinPQ<>(G.V());
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[source] = 0.0;
        distPQ.insert(source, 0.0);
        // Consider vertices in increasing order of distance from source.
        while (!distPQ.isEmpty()) {
            int nextClosestVertex = distPQ.delMin();
            for (DirectedEdge e : G.adj(nextClosestVertex)) {
                if (relax(e)) {
                    int w = e.to();
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
    private boolean relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            return true;
        }
        return false;
    }
}