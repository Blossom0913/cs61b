package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // Main union-find structure for checking percolation
    private WeightedQuickUnionUF unionUF;
    // Secondary union-find structure for checking fullness (prevent backwash)
    private WeightedQuickUnionUF fullUF;
    // Grid size (N x N)
    private int n;
    // Total number of grid sites (N * N)
    private int totalNum;
    // Counter for the number of open sites
    private int openNum;
    // Array to track if each site is open
    private boolean[] isOpen;


    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if(N < 0) {
            throw new java.lang.IllegalArgumentException();
        }
        n = N;
        unionUF = new WeightedQuickUnionUF(n * n + 2);
        fullUF = new WeightedQuickUnionUF(n * n + 1);
        isOpen = new boolean[N * N + 2];

        isOpen = new boolean[N * N + 2];

        totalNum = N * N;
        openNum = 0;

        // Connect top row to virtual top site and bottom row to virtual bottom site
        for (int i = 0; i < n; ++i) {
            unionUF.union(i, n * n);
            fullUF.union(i, n * n);
            unionUF.union(i + (n - 1) * n, n * n + 1);
        }

    }

    private int xyToOneDimension(int row, int col) {
        return row * n + col;
    }

    private void connectOpenSite(int row, int col) {
        int pos = xyToOneDimension(row, col);

        // Try to connect to the neighboring open site above (row - 1, col)
        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            unionUF.union(pos, xyToOneDimension(row - 1, col));
            fullUF.union(pos, xyToOneDimension(row - 1, col));
        }

        // Try to connect to the neighboring open site below (row + 1, col)
        if (row + 1 < n && isOpen(row + 1, col)) {
            unionUF.union(pos, xyToOneDimension(row + 1, col));
            fullUF.union(pos, xyToOneDimension(row + 1, col));
        }

        // Try to connect to the neighboring open site to the left (row, col - 1)
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            unionUF.union(pos, xyToOneDimension(row, col - 1));
            fullUF.union(pos, xyToOneDimension(row, col - 1));
        }

        // Try to connect to the neighboring open site to the right (row, col + 1)
        if (col + 1 < n && isOpen(row, col + 1)) {
            unionUF.union(pos, xyToOneDimension(row, col + 1));
            fullUF.union(pos, xyToOneDimension(row, col + 1));
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > n || col < 0 || col > n) {
            throw new IndexOutOfBoundsException();
        }

        int pos = xyToOneDimension(row, col);
        if (!isOpen[pos]) {
            isOpen[pos] = true;
            openNum++;
            connectOpenSite(row, col);
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        return isOpen[xyToOneDimension(row,col)];
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if(row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }

        return unionUF.connected(n * n, xyToOneDimension(row, col))
                && isOpen(row, col)
                && fullUF.connected(n * n, xyToOneDimension(row, col));
    }
    // number of open sites
    public int numberOfOpenSites() {
        return openNum;
    }
    // does the system percolate?
    public boolean percolates() {
        if (openNum == 0) {  // if no sites are open, it cannot percolate
            return false;
        }
        return unionUF.connected(n * n, n * n + 1);
    }
    // use for unit testing (not required)
    public static void main(String[] args) {

    }
}
