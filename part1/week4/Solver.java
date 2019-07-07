import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Solver {
//    private List<Board> solution = new LinkedList<>();
    private LinkedList<Board> solution = new LinkedList<>();
    private int solutionMoves = -1;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
        MinPQ<SearchNode> leafNodes = new MinPQ<>();
        leafNodes.insert(new SearchNode(initial, 0, null));

        MinPQ<SearchNode> twinLeafNodes = new MinPQ<>();
        twinLeafNodes.insert(new SearchNode(initial.twin(), 0, null));
        while (!leafNodes.isEmpty() && !twinLeafNodes.isEmpty()) {
            SearchNode minPriorityNode = leafNodes.delMin();
            if (minPriorityNode.board.isGoal()) {
                while (minPriorityNode != null) {
                    solution.addFirst(minPriorityNode.board);
                    minPriorityNode = minPriorityNode.predecessor;
                    solutionMoves++;
                }
                return;
            }
            minPriorityNode.neighbors().forEach(leafNodes::insert);

            SearchNode twinMinPriorityNode = twinLeafNodes.delMin();
            if (twinMinPriorityNode.board.isGoal()) {
                return;
            }
            twinMinPriorityNode.neighbors().forEach(twinLeafNodes::insert);
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solutionMoves != -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return solutionMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solutionMoves != -1) {
            return solution;
        }
        return null;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

//        Solver solver = new Solver(new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}}));
//        solver.solution().forEach(System.out::println);

    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private int manhattanDist;
        private SearchNode predecessor;

        SearchNode(Board board, int moves, SearchNode predecessor) {
            this.board = board;
            this.moves = moves;
            this.manhattanDist = board.manhattan();
            this.predecessor = predecessor;
        }

        List<SearchNode> neighbors() {
            LinkedList<SearchNode> neighbors = new LinkedList<>();
            for (Board neighbor : board.neighbors()) {
                if (predecessor == null || !neighbor.equals(predecessor.board)) {
                    neighbors.add(new SearchNode(neighbor, moves + 1, this));
                }
            }
            return neighbors;
        }

        @Override
        public int compareTo(SearchNode other) {
            int thisPriority = this.manhattanDist + this.moves;
            int otherPriority = other.manhattanDist + other.moves;
            return Integer.compare(thisPriority, otherPriority);
        }


    }
}