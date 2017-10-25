import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private enum MathConstants {
        CONFIDENCE_95(1.96);
        
        private final double value;
        private MathConstants(double v) {
            value = v;
        }
        public double getValue() {
            return value;
        }
    }
    
    private final double[] thd;
    private final int t;
    private double mn; // mean value
    private double sv; // stddev value
    
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be both larger than 0");
        }
        
        t = trials;
        
        thd = new double[trials];
        
        for (int i = 0; i < trials; ++i) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                p.open(row, col);
            }
            thd[i] = 1.0 * p.numberOfOpenSites() / (n * n);
        }
        
        mn = StdStats.mean(thd);
        sv = StdStats.stddev(thd);
    }
    
    // sample mean of percolation threshold
    public double mean() {
        return mn;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return sv;
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (MathConstants.CONFIDENCE_95.getValue() * stddev() / Math.sqrt((double) t));
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (MathConstants.CONFIDENCE_95.getValue() * stddev() / Math.sqrt((double) t));
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Require 2 arguments!");
        }
        
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);
        
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", "
                + ps.confidenceHi() + "]");
    }
}