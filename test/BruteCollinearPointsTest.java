import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BruteCollinearPointsTest {
 
  static Point[] points = new Point[6];

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
   
    
    //a < b < c < d
    points[0] = new Point(2, 1);
    points[1] = new Point(3, 2);
    points[2] = new Point(4, 3); 
    points[3] = new Point(5, 5);
    points[4] = new Point(3, 2);
    points[5] = new Point(4, 4);
    
  }

  @AfterAll
  static void tearDownAfterClass() throws Exception {
  }

  @Test
  void testBruteCollinearPoints() {
    assertThrows(IllegalArgumentException.class, () -> new BruteCollinearPoints(null));
    
    assertThrows(IllegalArgumentException.class, () -> new BruteCollinearPoints(points));  
    
    //set points[4] to null
    points[4] = null;
    
    assertThrows(IllegalArgumentException.class, () -> new BruteCollinearPoints(points)); 
    
  }

  @Test
  void testNumberOfSegments() {
    fail("Not yet implemented");
  }

  @Test
  void testSegments() {
    fail("Not yet implemented");
  }

}
