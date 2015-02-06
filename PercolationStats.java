public class PercolationStats {
    private double[] threshold;
    private int      T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new java.lang.IllegalArgumentException();
        this.T = T;
        threshold = new double[T];
        int[] random = new int[N * N];

        for (int i = 0; i < T; i++) {
            int count = 0;
            for (int j = 0; j < random.length; j++)
                random[j] = j + 1;

            StdRandom.shuffle(random, 0, random.length - 1);

            Percolation perc = new Percolation(N);
            while (!perc.percolates()) {
                int row = random[count] / N;
                int col = random[count] - (N * row);
                if (col == 0)
                    col = N;
                else
                    row++;

                count++;
                perc.open(row, col);
            }

            threshold[i] = (double) count / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (T == 1)
            return Double.NaN;
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();
        return mean - (1.96 * stddev) / Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();
        return mean + (1.96 * stddev) / Math.sqrt(T);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(N, T);
        StdOut.print("mean\t\t\t= " + ps.mean() + "\n");
        StdOut.print("stddev\t\t\t= " + ps.stddev() + "\n");
        StdOut.print("95% confidence interval = ");
        StdOut.print(ps.confidenceLo() + ", " + ps.confidenceHi() + "\n");
    }

}
