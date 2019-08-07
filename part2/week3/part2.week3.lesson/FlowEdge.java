package part2.week3.lesson;

public class FlowEdge {
    private final int from, to; // from and to
    private final double capacity;
    private double flow;

    public FlowEdge(int from, int to, double capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public double capacity() {
        return capacity;
    }

    public double flow() {
        return flow;
    }

    public int other(int vertex) {
        if (vertex == from) return to;
        else if (vertex == to) return from;
        else throw new RuntimeException("Illegal endpoint");
    }

    public double residualCapacityTo(int vertex) {
        if (vertex == from) return flow;
        else if (vertex == to) return capacity - flow;
        else throw new IllegalArgumentException();
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if (vertex == from) flow -= delta;
        else if (vertex == to) flow += delta;
        else throw new IllegalArgumentException();
    }
}