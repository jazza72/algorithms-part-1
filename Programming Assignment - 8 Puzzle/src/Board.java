import java.util.Arrays;
import java.util.Stack;

import edu.princeton.cs.algs4.StdRandom;

/**
 * This class is a representation of a NxN board of numbered tiles with an empty tile represented by
 * the zero (0) char.
 * 
 * @author jameswilson
 *
 */
public class Board {

  public final int[] gridAsArray;
  private final int dimension;
  private int hamming;
  private int manhattan;

  // static variable to represent goal Board state
  // can be used in the isGoal() method
  private static Board goalBoard;

  /**
   * Construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column
   * j). Also set the hamming() and manhattan() return values for later use.
   * 
   * @param blocks
   */
  public Board(int[][] blocks) {

    if (blocks == null)
      throw new IllegalArgumentException("Argument to constructor may not be null");

    dimension = blocks.length;

    if (dimension == 0)
      throw new IllegalArgumentException("Argument to constructor may not be zero length array");

    gridAsArray = Board.flattenGrid(blocks);   
  }

  /**
   * Method to generate goal Board instance - lazy initialization of a private static member Ideally
   * 
   * @param grid
   *          - the grid passed from the main public constructor
   */
  private void generateGoalBoard(int[] flatArray) {
    
    // generate the goal grid state from the grid on this board
    // copy and sort 1D array
    int[] arrCopy = Arrays.copyOf(flatArray, flatArray.length);
    
    Arrays.sort(arrCopy);
    
    // now reconstruct the 2D array
    // now put it back into a 2D array
    int[][] goalGrid = new int[dimension][dimension];

    // start at element 1, since element zero will be 0 (space)
    for (int x = 1; x < arrCopy.length; x++) {
      // index arithmetic - subtract 1 to access 0th element of sorted goal grid
      goalGrid[(x - 1) / dimension][(x - 1) % dimension] = arrCopy[x];
    }

    // now move the first element of the 1D array to the last element of the 2D array
    // as this will be the zero spacer
    goalGrid[dimension - 1][dimension - 1] = arrCopy[0];

    // now create and assign goal Board
    goalBoard = new Board(goalGrid);

  }

  /**
   * Board dimension n
   * 
   * @return dimenions of board nxn
   */
  public int dimension() {
    return dimension;
  }

  /**
   * TESTING
   * 
   */
  // public int[][] getGrid() {
  // return grid;
  // }

  /**
   * Number of blocks out of place
   * 
   * @return hamming priority
   */
  public int hamming() {
   
    int count = 0;
    
    //only do this once and store result
    if (this.hamming == 0 && !this.isGoal())
    {
      //calculate hamming  
      for (int i=0; i<gridAsArray.length; i++) {
        if (goalBoard.gridAsArray[i] != 0 && (goalBoard.gridAsArray[i] != this.gridAsArray[i])) {
          count++;
        }
      }
    }
    
    this.hamming = count;
    
    return hamming;
  }
  
  /**
   * Helper function to convert 1D arrays to 2D arrays
   * 
   * @param flatArray - array to convert to grid
   * @return int[] - 1D array
   */
  private static int[][] inflateArray(int[] flatArray) {

    int dim = (int) Math.sqrt((double)flatArray.length);
    
    int[][] grid = new int[dim][dim];

    for (int i = 0; i < flatArray.length; i++) {
      // index arithmetic - subtract 1 to access 0th element of sorted goal grid
      grid[i/dim][i%dim] = flatArray[i];
    }

    return grid;
  }
  
  
  /**
   * Helper function to convert 2D arrays to 1D arrays
   * 
   * @return int[][] - 2D array
   */
  private static int[] flattenGrid(int[][] grid) {
    // 1D array to hold values from 2D array
    int dim = grid.length;
    
    int[] array = new int[(int) Math.pow(dim, 2)];

    if (grid != null && grid.length != 0 && grid[0].length != 0) {
      int counter = 0;

      for (int i = 0; i < grid.length; i++) {

        if (grid[i] == null)
          throw new IllegalArgumentException("Argument to method must not have null columns");

        for (int j = 0; j < grid[i].length; j++) {

          if (grid.length != grid[i].length)
            throw new IllegalArgumentException(
                "Argument to method must have equal dimensions for rows and columns");

          array[counter++] = grid[i][j];

        }
      }
    }

    return array;
  }

  /**
   * Sum of Manhattan distances between blocks and goal
   * 
   * @return manhattan priority
   */
  public int manhattan() {
    //some amount of array index arithmetic required here
    //go through each element the gaol array, if the current board is not correct 
    //for the element, locate the element in the current board array
    int moves = 0;
    
    //only do this once and store result
    if (this.manhattan == 0 && !this.isGoal())
    {
      //calculate hamming  
      for (int i=0; i<gridAsArray.length; i++) {
        if (goalBoard.gridAsArray[i] != 0 && (goalBoard.gridAsArray[i] != this.gridAsArray[i])) {
          //locate the target element in this board
          for (int x=0; x<gridAsArray.length; x++) {
            if (goalBoard.gridAsArray[i] == gridAsArray[x]) {
              //calculate the differences between the array indices to get the moves required
              int rowMove = Math.abs((i/dimension) - (x/dimension));
              int colMove = Math.abs((i%dimension) - (x%dimension));
              
              //add to moves count
              moves += rowMove + colMove;
            }
              
          }
        }
      }
    }
    
    this.manhattan = moves;
    
    return manhattan;

  }

  /**
   * Is this board the goal board?
   * We would use the Singleton() pattern (double-checked locking) here, but no concurrency issues here
   * @return true/false
   */
  public boolean isGoal() {
    if (Board.goalBoard == null)
      this.generateGoalBoard(gridAsArray);
    
    //now it's simply a matter of comparing this Board with the static goal Board
    return this.equals(Board.goalBoard);

  }

  /**
   * A board that is obtained by exchanging any pair of blocks
   * 
   * @return Board
   */
  public Board twin() {
    //obtain twin by swapping any pair of elements (except zero (0))
    //make a copy
    int[] tempArr = Arrays.copyOf(gridAsArray, gridAsArray.length);
    
    //swap random elements 
    int swap0, swap1;
    
    swap0 = StdRandom.uniform((int)Math.pow(dimension, 2));
    swap1 = StdRandom.uniform((int)Math.pow(dimension, 2));

    //check that different elements selected and that neither is 0
    while (swap0 == swap1 && tempArr[swap0] != 0 && tempArr[swap1] != 0) {
      swap0 = StdRandom.uniform((int)Math.pow(dimension, 2));
      swap1 = StdRandom.uniform((int)Math.pow(dimension, 2));
    }
    
    //now swap them 
    int temp = tempArr[swap0];
    tempArr[swap0] = tempArr[swap1];
    tempArr[swap1] = temp;
    
    //and return new Board instance
    return new Board(Board.inflateArray(tempArr));
    
  }

  /**
   * Does this board equal y The only way to truly know this is to compare every array index with
   * it's equivalent - will try with a public grid member first If this is not permitted, then it
   * means that equivalence is based on some other intrinsic property exposed by the public
   * interface.
   * 
   * @ereturn true/false
   */
  public boolean equals(Object y) {

    // equality tests
    if (y == null)
      return false;

    if (!(y instanceof Board))
      return false;

    if (this == y)
      return true;

    if (((Board) y).dimension() != this.dimension())
      return false;

    Board tempBoard = (Board) y;
    
    if (tempBoard.gridAsArray == null)
        return false;
    
    for (int i = 0; i < Math.pow(dimension, 2); i++) {
       if (this.gridAsArray[i] != tempBoard.gridAsArray[i])
          return false;
    }

    return true;

  }

  /**
   * All neighboring boards
   * 
   * @return iterable object TBD
   */
  public Iterable<Board> neighbors() {
    return this.generateNeighbours();
  }
  
  /**
   * Helper method to generate the collection of neighbour boards
   * @return
   */
  private Stack<Board> generateNeighbours () {
    
    Stack<Board> store = new Stack<>();
    
    //find the zero (0) item - this will be the pivot around which other 
    //elements will move to create the neighbours
    int row = 0;
    int col = 0;
    int count;
    
    for (count=0; count<gridAsArray.length; count++) {
      if (gridAsArray[count] == 0) {
        //index arithmetic
        row = count/dimension;
        col = count%dimension;
        break;
      }
    }
    
    //now add and subtract 1 from each row and col index to get the element to swap with
    //but only if the resulting index is within the bounds of the 2D array
    //this can be optimized by reinstating the orginal state of the grid instead of creating
    //a new copy to work on each time
    
    int [] tempArr;
    
    //row-1
    if (row-1 >= 0 && row-1 <= dimension-1) {
      //copy array
      tempArr = Arrays.copyOf(gridAsArray, gridAsArray.length);
      
      //swap elements
      int temp = gridAsArray[count];
      tempArr[count] = tempArr[((row-1)*dimension)+col];
      tempArr[((row-1)*dimension)+col] = temp;
      store.push(new Board(Board.inflateArray(tempArr)));
    }
    
    //row+1
    if (row+1 >= 0 && row+1 <= dimension-1) {
      //copy array
      tempArr = Arrays.copyOf(gridAsArray, gridAsArray.length);
     
      int temp = gridAsArray[count];
      tempArr[count] = tempArr[((row+1)*dimension)+col];
      tempArr[((row+1)*dimension)+col] = temp;
      store.push(new Board(Board.inflateArray(tempArr)));
    }
    
    //col-1
    if (col-1 >= 0 && col-1 <= dimension-1) {
      //copy array
      tempArr = Arrays.copyOf(gridAsArray, gridAsArray.length);
      
      //swap elements
      int temp = gridAsArray[count];
      tempArr[count] = tempArr[((row)*dimension)+col-1];
      tempArr[((row)*dimension)+col-1] = temp;
      store.push(new Board(Board.inflateArray(tempArr)));
    }
    
    //col+1
    if (col+1 >= 0 && col+1 <= dimension-1) {
      //copy array
      tempArr = Arrays.copyOf(gridAsArray, gridAsArray.length);
     
      int temp = gridAsArray[count];
      tempArr[count] = tempArr[((row)*dimension)+col+1];
      tempArr[((row)*dimension)+col+1] = temp;
      store.push(new Board(Board.inflateArray(tempArr)));
    }
    
    return store;
    
  }

  /**
   * String representation of this board (in the output format specified below) 
   * eg: 3 
   *      1 0 3 
   *      4 2 5
   *      7 8 6
   */
  public String toString() {
    
    int[][] grid = Board.inflateArray(this.gridAsArray);
    
    StringBuilder s = new StringBuilder();

    s.append(dimension + "\n");

    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        s.append(String.format("%2d ", grid[i][j]));
      }
      s.append("\n");
    }

    return s.toString();
  }

  public static void main(String[] args) {
  } // unit tests (not graded)
}