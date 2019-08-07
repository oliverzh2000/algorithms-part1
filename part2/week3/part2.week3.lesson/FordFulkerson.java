package part2.week3.lesson;

import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.FlowEdge;

public class FordFulkerson {
    private boolean[] marked; // true if s->v path in residual network
    private FlowEdge[] edgeTo; // last edge on s->v path
    private double flow; // flow of flow

    public FordFulkerson(FlowNetwork G, int s, int t) {
        flow = 0.0;
        while (true) {
            edgeTo = new FlowEdge[G.V()];
            marked = new boolean[G.V()];
            Queue<Integer> queue = new Queue<>();
            queue.enqueue(s);
            marked[s] = true;
            while (!queue.isEmpty()) {
                int v = queue.dequeue();
                for (FlowEdge e : G.adj(v)) {
                    int w = e.other(v);
                    if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                        edgeTo[w] = e;
                        marked[w] = true;
                        queue.enqueue(w);
                    }
                }
            }
            // Has augmenting path.
            if (marked[t]) {
                double bottle = Double.POSITIVE_INFINITY;
                for (int v = t; v != s; v = edgeTo[v].other(v))
                    bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
                for (int v = t; v != s; v = edgeTo[v].other(v))
                    edgeTo[v].addResidualFlowTo(v, bottle);
                flow += bottle;
            } else {
                return;
            }
        }

    }

    public double value() {
        return flow;
    }

    public boolean inCut(int v) {
        return marked[v];
    }
}