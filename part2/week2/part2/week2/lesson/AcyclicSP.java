package part2.week2.lesson;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.Topological;

public class AcyclicSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;

    public AcyclicSP(EdgeWeightedDigraph graph, int source) {
        edgeTo = new DirectedEdge[graph.V()];
        distTo = new double[graph.V()];
        for (int vertex = 0; vertex < graph.V(); vertex++)
            distTo[vertex] = Double.POSITIVE_INFINITY;
        distTo[source] = 0.0;
        Topological topological = new Topological(graph);
        for (int vertex : topological.order()) {
            for (DirectedEdge edge : graph.adj(vertex)) {
                relax(edge);
            }
        }
    }

    // If e=v->w gives shorter path to w via v, successful relaxation.
    private void relax(DirectedEdge e) {
        int v = e.from(), w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
        }
    }
}
