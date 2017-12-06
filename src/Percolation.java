import edu.princeton.cs.algs4.*;

/**
 * @author jameswilson
 *
 */
public class Percolation {
	private boolean[][] grid;
	private int size, opensites;
	private WeightedQuickUnionUF uf;
	private int top, bottom;

	public Percolation(int n) {

		if (n <= 0)
			throw new IllegalArgumentException();

		size = n;

		// add 2 extra on here as our virtual top and bottom site, and 1 more as we
		// don't start indexing the array at 0
		uf = new WeightedQuickUnionUF(size * size + 2);

		// create n-by-n grid, with all sites blocked
		// no need to initialize as boolean is intialized to false by default
		grid = new boolean[n + 1][n + 1];

		// for (int x=0; x<n; x++)
		// {
		// for (int y; y<n; y++)
		// {
		// grid[x][y] = 0;
		// }
		// }

		// top and bottom indices
		top = size * size;
		bottom = size * size + 1;
	}

	// open site (row, col) if it is not open already
	public void open(int row, int col) {
		validate(row, col);

		if (grid[row][col] == false) {
			grid[row][col] = true;
		} else {
			return;
		}

		opensites++;

		// now connect the open cell to the surrounding ones

		// if this is a top row site being opened, then connect it to the virtual top
		// site
		if (row == 1) {
			uf.union(top, xyToID(row, col));
		}

		if (row > 1) {
			if (isOpen(row - 1, col)) {
				uf.union(xyToID(row, col), xyToID(row - 1, col));
			}
		}

		if (col > 1) {
			if (isOpen(row, col - 1)) {
				uf.union(xyToID(row, col), xyToID(row, col - 1));
			}
		}

		if (col < size) {
			if (isOpen(row, col + 1)) {
				uf.union(xyToID(row, col), xyToID(row, col + 1));
			}
		}

		if (row < size) {
			if (isOpen(row + 1, col)) {
				uf.union(xyToID(row, col), xyToID(row + 1, col));
			}
		}

		// connect bottom row to virtual bottom site
		if (row == size) {
			uf.union(bottom, xyToID(row, col));
		}
	}

	public boolean isOpen(int row, int col) {
		validate(row, col);
		// is site (row, col) open?
		return grid[row][col];
	}

	public boolean isFull(int row, int col) {
		validate(row, col);
		if (isOpen(row, col)) {
			return (uf.connected(top, xyToID(row, col)));
		}
		
		return false;
	}
		

	public int numberOfOpenSites() {
		return opensites; // number of open sites
	}

	/**
	 * This method should simply check if there is a connection between the virtual
	 * top and bottom sites
	 * 
	 * @return
	 */
	public boolean percolates() {
		return uf.connected(top, bottom);
	}

	/**
	 * Method to covert 2D grid position to 1D arrary position in union-find object
	 * 
	 * @param x
	 * @param y
	 * @return array index for union-find
	 */
	private int xyToID(int x, int y) {
		// we always start the grid ref from 1 (not 0)
		// uf array position is calculated from x, y coord values

		return (y + (x - 1) * size)-1;

	}

	private boolean validate(int x, int y) {

		if (x < 1 || x > size || y < 1 || y > size)
			throw new IllegalArgumentException();

		return true;
	}

	public static void main(String[] args) {

		Percolation p = new Percolation(5);

		p.open(1, 1);
		p.open(2, 1);
		p.open(3, 1);
		p.open(4, 1);
		p.open(5, 1);
		p.open(3, 4);

		if (p.percolates()) {
			System.out.println("true");
		} else {
			System.out.println("false");
		}

		// test client (optional)
	}
}
