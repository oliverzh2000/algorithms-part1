import edu.princeton.cs.algs4.Graph;

public class CC {
    private boolean marked[];
    private int id[];
    private int count;

    public CC(Graph g) {
        this.marked = new boolean[g.V()];
        this.id = new int[g.V()];
        for (int i = 0; i < g.V(); i++) {
            if (!marked[i]) {
                dfs(g, i);
                count++;
            }
        }
    }

    private void dfs(Graph g, int s) {
        marked[s] = true;
        id[s] = count;
        for (int v : g.adj(s)) {
            if (!marked[v]) {
                dfs(g, v);
            }
        }
    }
}
