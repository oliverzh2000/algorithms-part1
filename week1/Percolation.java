import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private boolean[] openSites;
    private int numOpenSites;
    private WeightedQuickUnionUF UF;
    private int topSite;
    private int bottomSite;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        this.n = n;
        this.topSite = n * n;
        this.bottomSite = topSite + 1;
        this.openSites = new boolean[n * n];
        this.UF = new WeightedQuickUnionUF(n * n + 2); // 2 extra sites for virtual top and bottom.
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isInBounds(row, col)) {
            throw new IllegalArgumentException("row or col out of bounds");
        }
        if (!isOpen(row, col)) {
            openSites[indexOf(row, col)] = true;
            numOpenSites++;
            if (row == 1) {
                UF.union(indexOf(row, col), topSite);
            }
            if (row == n) {
                UF.union(indexOf(row, col), bottomSite);
            }
            tryUnion(row, col, row + 1, col); // up
            tryUnion(row, col, row - 1, col); // down
            tryUnion(row, col, row, col + 1); // right
            tryUnion(row, col, row, col - 1); // left
        }
    }

    // union the sites only if they are both in bounds and already open.
    private void tryUnion(int row1, int col1, int row2, int col2) {
        if (isInBounds(row1, col1) && isInBounds(row2, col2) && isOpen(row1, col1) && isOpen(row2, col2)) {
            UF.union(indexOf(row1, col1), indexOf(row2, col2));
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isInBounds(row, col)) {
            throw new IllegalArgumentException("row or col out of bounds");
        }
        return openSites[indexOf(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isInBounds(row, col)) {
            throw new IllegalArgumentException("row or col out of bounds");
        }
        return isOpen(row, col) && UF.connected(topSite, indexOf(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return UF.connected(topSite, bottomSite);
    }

    private boolean isInBounds(int row, int col) {
        return 0 < row && row <= n && 0 < col && col <= n;
    }

    private int indexOf(int row, int col) {
        return (row - 1) * n + (col - 1);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(1);
    }
}