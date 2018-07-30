import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class Board {
    private int[][] blocks;
    private int n; // dimension

    // construct a board from an n-by-n array of blocks
    public Board(int[][] blocks) {
        this.n = blocks[0].length;
        this.blocks = new int[n][n];
        for (int row = 0; row < n; row++) {
            this.blocks[row] = Arrays.copyOf(blocks[row], n);
        }
    }

    // (where blocks[i][j] = block in row i, column j)
    // board dimension n
    public int dimension() {
        return n;
    }

    // number of blocks out of place
    public int hamming() {
        int wrongBlocks = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (blocks[row][col] != 0 && blocks[row][col] != (to1D(row, col) + 1)) {
                    wrongBlocks++;
                }
            }
        }
        return wrongBlocks;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattanDist = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (blocks[row][col] != 0) {
                    // for each block, add to the manhattan distance the vertical and horizontal distance
                    // to the block's goal position.
                    int goal = blocks[row][col];
                    int goalRow = (goal - 1) / n;
                    int goalCol = (goal - 1) - goalRow * n;
                    manhattanDist += Math.abs(row - goalRow) + Math.abs(col - goalCol);
                }
            }
        }
        return manhattanDist;
    }

    // sum of Manhattan distances between blocks and goal
    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        Board twin = new Board(blocks);
        if (blocks[0][0] != 0 && blocks[0][1] != 0) {  // if first two blocks on first row exist, swap them
            twin.swap(0, 0, 0, 1);
        } else {  // otherwise swap the first two blocks on the second row.
            twin.swap(1, 0, 1, 1);
        }
        return twin;
    }

    private void swap(int rowA, int colA, int rowB, int colB) {
        assert isInBounds(rowA, colA);
        assert isInBounds(rowB, colB);
        int temp = blocks[rowA][colA];
        blocks[rowA][colA] = blocks[rowB][colB];
        blocks[rowB][colB] = temp;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        return Arrays.deepEquals(blocks, ((Board) y).blocks);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedList<Board> neighbors = new LinkedList<>();
        // coordinates of the blank block.
        int blankRow = 0;
        int blankCol = 0;
        outer: for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (blocks[row][col] == 0) {
                    blankRow = row;
                    blankCol = col;
                    break outer;
                }
            }
        }
        // top
        if (isInBounds(blankRow - 1, blankCol)) {
            Board neighbor = new Board(blocks);
            neighbor.swap(blankRow, blankCol, blankRow - 1, blankCol);
            neighbors.add(neighbor);
        }
        // down
        if (isInBounds(blankRow + 1, blankCol)) {
            Board neighbor = new Board(blocks);
            neighbor.swap(blankRow, blankCol, blankRow + 1, blankCol);
            neighbors.add(neighbor);
        }
        // left
        if (isInBounds(blankRow, blankCol - 1)) {
            Board neighbor = new Board(blocks);
            neighbor.swap(blankRow, blankCol, blankRow, blankCol - 1);
            neighbors.add(neighbor);
        }
        // right
        if (isInBounds(blankRow, blankCol + 1)) {
            Board neighbor = new Board(blocks);
            neighbor.swap(blankRow, blankCol, blankRow, blankCol + 1);
            neighbors.add(neighbor);
        }
        return neighbors;
    }

    // string representation of this board (in the output format specified below)
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                s.append(String.format("%2d ", blocks[row][col]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private boolean isInBounds(int row, int col) {
        return 0 <= row && row < n && 0 <= col && col < n;
    }

    private int to1D(int row, int col) {
        assert isInBounds(row, col);
        return row * n + col;
    }

    public static void main(String[] args) {
        Board parent = new Board(new int[][]{{8, 1, 3}, {4, 2, 0}, {7, 6, 5}});
        for (Board n : parent.neighbors()) {
            System.out.println(n.toString());
        }
    }
}