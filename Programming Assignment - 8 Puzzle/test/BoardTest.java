import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Stack;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * 
 */

/**
 * @author jameswilson
 *
 */
class BoardTest {

  static Board boardA, boardB, boardC, boardD, goal;
  
  /**
   * @throws java.lang.Exception
   */
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    
    //create a new 3x3 Board
    int[][] gridA = new int[3][3];
    
    //instantiate grids
    gridA[0][0] = 1;
    gridA[0][1] = 8;
    gridA[0][2] = 5;
    gridA[1][0] = 0;
    gridA[1][1] = 2;
    gridA[1][2] = 4;
    gridA[2][0] = 3;
    gridA[2][1] = 6;
    gridA[2][2] = 7;
    
    int[][] gridA1 = new int[3][3];
    
    //instantiate grids
    gridA1[0][0] = 7;
    gridA1[0][1] = 6;
    gridA1[0][2] = 3;
    gridA1[1][0] = 4;
    gridA1[1][1] = 0;
    gridA1[1][2] = 2;
    gridA1[2][0] = 5;
    gridA1[2][1] = 8;
    gridA1[2][2] = 1;
    
    int[][] gridA2 = new int[3][3];
    
    //instantiate grids
    gridA2[0][0] = 7;
    gridA2[0][1] = 6;
    gridA2[0][2] = 3;
    gridA2[1][0] = 4;
    gridA2[1][1] = 5;
    gridA2[1][2] = 2;
    gridA2[2][0] = 0;
    gridA2[2][1] = 8;
    gridA2[2][2] = 1;
    
    int[][] gridA3 = new int[3][3];
    
    //instantiate goal Board
    gridA3[0][0] = 1;
    gridA3[0][1] = 2;
    gridA3[0][2] = 3;
    gridA3[1][0] = 4;
    gridA3[1][1] = 5;
    gridA3[1][2] = 6;
    gridA3[2][0] = 7;
    gridA3[2][1] = 8;
    gridA3[2][2] = 0;
    
    boardA = new Board(gridA);
    boardB = new Board(gridA);
    boardC = new Board(gridA1);
    boardD = new Board(gridA2);
    goal = new Board(gridA3);
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterAll
  static void tearDownAfterClass() throws Exception {
  }

  /**
   * Test method for {@link Board#Board(int[][])}.
   */
  @Test
  final void testBoard() {
    assertThrows(IllegalArgumentException.class, () -> new Board(null));
  }

  /**
   * Test method for {@link Board#dimension()}.
   */
  @Test
  final void testDimension() {
    assertEquals(3, boardA.dimension());
  }

  /**
   * Test method for {@link Board#hamming()}.
   */
  @Test
  final void testHamming() {
    assertEquals(5, boardC.hamming());
  }

  /**
   * Test method for {@link Board#manhattan()}.
   */
  @Test
  final void testManhattan() {
    assertEquals(12, boardC.manhattan());
    assertEquals(15, boardB.manhattan());
  }

  /**
   * Test method for {@link Board#isGoal()}.
   */
  @Test
  final void testIsGoal() {
    assertFalse(boardA.isGoal());
    assertTrue(goal.isGoal());
  }
  
//  @Test
//  final void testGenerateGoalBoard() {
//    
//    Board x = boardA.generateGoalBoard(boardA.getGrid());
//    
//    System.out.println(x);
//    
//  }

  /**
   * Test method for {@link Board#twin()}.
   */
  @Test
  final void testTwin() {
    Board a = boardA.twin();
    System.out.println("here");
  }

  /**
   * Test method for {@link Board#equals(java.lang.Object)}.
   */
  @Test
  final void testEqualsObject() {
    assertFalse(boardA.equals(new Object()));
    assertFalse(boardA.equals(null));
    assertTrue(boardA.equals(boardA));
    assertTrue(boardA.equals(boardB));
    assertFalse(boardA.equals(boardC));
  }

  /**
   * Test method for {@link Board#neighbors()}.
   */
  @Test
  final void testNeighbors() {
    Iterable<Board> iterable = boardA.neighbors();
    
    //test this by counting the number of items in the iterator to 
    //see if it matches our expectations
    int count = 0;
    for (Board b : iterable) {
      count++;
    }
    
    assertEquals(3, count);
    
    iterable = boardC.neighbors();
    
    //test this by counting the number of items in the iterator to 
    //see if it matches our expectations
    count = 0;
    for (Board b : iterable) {
      count++;
    }
    
    assertEquals(4, count);
    
    iterable = boardD.neighbors();
    
    //test this by counting the number of items in the iterator to 
    //see if it matches our expectations
    count = 0;
    for (Board b : iterable) {
      count++;
    }
    
    assertEquals(2, count);
  }

  /**
   * Test method for {@link Board#toString()}.
   */
  @Test
  final void testToString() {
    //System.out.println(boardA.toString());
  }

}
