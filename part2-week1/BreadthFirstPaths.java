import edu.princeton.cs.algs4.Queue;

public class BreadthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;

    private void bfs(Graph g, int s) {
        Queue<Integer> q = new Queue<Integer>();
        q.enqueue(s);
        marked[s] = true;
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int x : g.adj(v)) {
                if (!marked[x]) {
                    edgeTo[x] = v;
                    marked[x] = true;
                    q.enqueue(x);
                }
            }
        }
    }
}