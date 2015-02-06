public class Percolation {
    private int                  N;
    private boolean[][]          grid;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;

    private int xyTo1D(int i, int j) {
        return (i - 1) * N + j;
    }

    private void testBounds(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N)
            throw new java.lang.IndexOutOfBoundsException();
    }

    // Create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0)
            throw new java.lang.IllegalArgumentException();
        this.N = N;
        grid = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }

        uf = new WeightedQuickUnionUF(N * N + 2);
        uf2 = new WeightedQuickUnionUF(N * N + 1);
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        testBounds(i, j);
        if (!isOpen(i, j)) {
            grid[i - 1][j - 1] = true;
        }
        if (i == 1) {
            uf.union(0, xyTo1D(i, j));
            uf2.union(0, xyTo1D(i, j));
        }

        if (i == N)
            uf.union(N * N + 1, xyTo1D(i, j));

        if (i > 1) {
            if (isOpen(i - 1, j))
                uf.union(xyTo1D(i - 1, j), xyTo1D(i, j));
            if (isOpen(i - 1, j))
                uf2.union(xyTo1D(i - 1, j), xyTo1D(i, j));
        }

        if (i < N) {
            if (isOpen(i + 1, j))
                uf.union(xyTo1D(i + 1, j), xyTo1D(i, j));
            if (isOpen(i + 1, j))
                uf2.union(xyTo1D(i + 1, j), xyTo1D(i, j));
        }

        if (j > 1) {
            if (isOpen(i, j - 1))
                uf.union(xyTo1D(i, j - 1), xyTo1D(i, j));
            if (isOpen(i, j - 1))
                uf2.union(xyTo1D(i, j - 1), xyTo1D(i, j));
        }

        if (j < N) {
            if (isOpen(i, j + 1))
                uf.union(xyTo1D(i, j + 1), xyTo1D(i, j));
            if (isOpen(i, j + 1))
                uf2.union(xyTo1D(i, j + 1), xyTo1D(i, j));
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        testBounds(i, j);
        return grid[i - 1][j - 1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        testBounds(i, j);
        return uf2.connected(0, xyTo1D(i, j));
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, N * N + 1);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(1);
        p.open(1, 1);
        StdOut.println(p.percolates());
    }
}
