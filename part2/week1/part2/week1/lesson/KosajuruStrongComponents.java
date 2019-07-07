package part2.week1.lesson;

public class KosajuruStrongComponents {
    private final Digraph graph;
    private boolean[] marked;
    private int[] id;
    private int count;

    public KosajuruStrongComponents(Digraph digraph) {
        this.graph = digraph;
        marked = new boolean[digraph.V()];
        id = new int[digraph.V()];
        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(digraph.reversed());
        for (int v: depthFirstOrder.reversePost()) {
            if (!marked[v]) {
                DFS(v);
                count++;
            }
        }
    }

    public int numComponents() {
        return count;
    }

    private void DFS(int start) {
        marked[start] = true;
        this.id[start] = count;
        for (int neighbor: graph.adj(start)) {
            if (!marked[neighbor]) {
                DFS(neighbor);
            }
        }
    }

    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }
}
