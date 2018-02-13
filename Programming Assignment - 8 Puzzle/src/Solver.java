import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Stack;

import java.util.Deque;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 * 
 */

/**
 * This class implements the A* algorithm, using a min priority queue, to solve an initial n*n
 * puzzle board state to it's goal state (sorted integers) with zero as the final elememt on the
 * board.
 * 
 * For example: 1 8 5 0 2 4 3 6 7
 * 
 * solves to 1 2 3 4 5 6 7 8 0
 * 
 * via a series of intermediate board states
 * 
 * The steps in solving the puzzle take the following steps:
 * 
 * 1. The initial board is added to the minPQ 2. The board is dequeued and it's neighbors are then
 * added to the minPQ 2. The minimum Board is then dequeued and it's neighbours added to the minPQ
 * 3. This process continues until the goal board is dequeued
 * 
 * Whether or not the puzzle is solvable can be ascertained by processing a parallel minPQ with an
 * initial starting Board which is a "twin" of thj e
 * 
 * @author jameswilson
 *
 */
public class Solver {

  // 2 queues to hold each board as it is dequed from the minPQ
  // one for the intial board and one for the twin board
  private Deque<Board> solution = new ArrayDeque<>();

  private MinPQ<Node> initialPQ;
  private MinPQ<Node> twinPQ;

  private boolean solvable = false;

  /**
   * Find a solution to the initial board (using the A* algorithm)
   * 
   * @param initial
   *          Board
   */
  public Solver(Board initial) {

    solvable = false;

    // Board twin = initial.twin();
    // Set<Node> set = new TreeSet<Node>(new BoardComparator());
    initialPQ = new MinPQ<Node>(new Comparator<Node>() {

      @Override
      public int compare(Node o1, Node o2) {
        return (o1.priority < o2.priority ? -1
            : (o1.priority > o2.priority ? 1
                : (o1.brd.manhattan() < o2.brd.manhattan() ? -1
                    : (o1.brd.manhattan() > o2.brd.manhattan() ? 1 : 0))));
      }

    });

    twinPQ = new MinPQ<Node>(new Comparator<Node>() {

      @Override
      public int compare(Node o1, Node o2) {
        return (o1.priority < o2.priority ? -1
            : (o1.priority > o2.priority ? 1
                : (o1.brd.manhattan() < o2.brd.manhattan() ? -1
                    : (o1.brd.manhattan() > o2.brd.manhattan() ? 1 : 0))));
      }

    });

    initialPQ.insert(new Node(0, initial, null));
    twinPQ.insert(new Node(0, initial.twin(), null));

    int count = 0;

    Node x = null, y = null;

    while (true) {
      // dequeue each
      x = initialPQ.delMin();
      y = twinPQ.delMin();

      // check if we have reached the goal on either Node
      // exactly one of an initial board or its twin is solvable
      if (x.brd.isGoal()) {
        // the puzzle is solvable
        this.solvable = true;
        break;
      }

      if (y.brd.isGoal()) {
        // the puzzle is not solvable
        this.solvable = false;
        break;
      }

      // now iterate through the neigbour boards
      // and add to the respective PQs

      for (Board b : x.brd.neighbors()) {
        // check that the neighbour board is not the same as the predecessor board
        Node no = new Node(x.moves + 1, b, x);
        if (x.prev == null || !x.prev.equals(no)) {
          initialPQ.insert(no);
        }
      }

      for (Board b : y.brd.neighbors()) {
        // check that the neighbour board is not the same as the predecessor board
        Node no = new Node(y.moves + 1, b, y);
        if (y.prev == null || !y.prev.equals(no)) {
          twinPQ.insert(no);
        }
      }

    }

    if (x.brd.isGoal()) {
      Node no = x;
      while (no != null) {
        solution.add(no.brd);
        no = no.prev;
      }
    }

  }

  /**
   * Is the initial board solvable?
   * 
   * @return true/false
   */
  public boolean isSolvable() {
    return solvable;
  }

  /**
   * Min number of moves to solve initial board; -1 if unsolvable
   * 
   * @return number of moves to solve initial board
   */
  public int moves() {
    if (isSolvable()) {
      return solution.size() - 1;
    } else {
      return -1;
    }
  }

  /**
   * Sequence of boards in a shortest solution; null if unsolvable
   * 
   * @return
   */
  public Iterable<Board> solution() {
    // return the queue
    if (isSolvable()) {
      return solution;
    } else {
      // not solvable
      return null;
    }
  }

  /**
   * Private class to represent a node on the minPQ Allows setting of priority based on number of
   * moves to board and manhattan number from board
   * 
   * @author jameswilson
   *
   */
  private class Node {

    // number of moves to get to this Board node
    int moves;
    int priority;
    Board brd;
    Node prev;

    private Node(int m, Board b, Node p) {
      // set priority
      moves = m;
      brd = b;
      prev = p;
      priority = moves + brd.manhattan();
    }

    public String toString() {
      return brd.toString();
    }

    @Override
    public boolean equals(Object y) {
      if (!(y instanceof Node)) {
        return false;
      }

      Node that = (Node) y;
      return brd.equals(that.brd);
    }
  }

  /**
   * Solve a slider puzzle (given below)
   * 
   * @param args
   */
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
  }
}
