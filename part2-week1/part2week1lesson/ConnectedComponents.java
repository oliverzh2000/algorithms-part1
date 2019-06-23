package part2week1lesson;

public class ConnectedComponents {
    private final Graph graph;
    private boolean[] marked;
    private int[] id;
    private int count;

    public ConnectedComponents(Graph graph) {
        this.graph = graph;
        marked = new boolean[graph.V()];
        id = new int[graph.V()];
        for (int v = 0; v < graph.V(); v++) {
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

    public boolean connected(int v, int w) {
        return id[v] == id[w];
    }
}
