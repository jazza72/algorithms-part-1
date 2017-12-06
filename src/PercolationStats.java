
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * @author jameswilson
 *
 */
public class PercolationStats {

	private double[] data;
	private int t, size;
	private double mean;
	private double stddev;

	public PercolationStats(int n, int trials) {
		// perform trials independent experiments on an n-by-n grid
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException();
		
		size = n;
		t = trials;
		data = new double[trials];

		for (int i = 0; i < trials; i++) {
			data[i] = runTests();
		}
	}

	private double runTests() {

		Percolation perc = new Percolation(size);

		while (!perc.percolates()) {
			int x = StdRandom.uniform(1, size+1);
			int y = StdRandom.uniform(1, size+1);

			if (!perc.isOpen(x, y)) {
				perc.open(x, y);
			}
		}

		// return the p* threshold value
		return perc.numberOfOpenSites() / Math.pow(size, 2);

	}

	public double mean() {
		// sample mean of percolation threshold
		this.mean = StdStats.mean(data);
		return this.mean;
	}

	public double stddev() {
		// sample standard deviation of percolation threshold
		this.stddev = StdStats.stddev(data);
		return this.stddev;
	}

	public double confidenceLo() {
		// low endpoint of 95% confidence interval
		return mean() - ((1.96*stddev())/Math.sqrt(t));
	}
	
	public double confidenceHi() {
		// high endpoint of 95% confidence interval
		return mean() + ((1.96*stddev())/Math.sqrt(t));
	}

	public static void main(String[] args) {
		// test client (described below)
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);

		PercolationStats ps = new PercolationStats(n, t);
		
		StdOut.println("mean\t\t\t= " + ps.mean());
		StdOut.println("stddev\t\t\t= " + ps.stddev());
		StdOut.println("95% confidence interval\t= [" + ps.confidenceLo() + "," + ps.confidenceHi() + "]");

	}
}
