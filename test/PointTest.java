import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PointTest {
  
  static Point a, b, c, d, e, f;

  @BeforeAll
  static void setup() {
    //a < b < c < d
     a = new Point(2, 1);
     b = new Point(3, 2);
     c = new Point(4, 3); 
     d = new Point(5, 5);
     
     e = new Point(2, 4);
     f = new Point(4, 4);
  }
      
  @Test
  void testSlopeTo() {
    assertEquals(1.0 , a.slopeTo(b));
    assertEquals(1.0, b.slopeTo(a));
    assertEquals(1.0, b.slopeTo(d));
    assertEquals(-2.0, e.slopeTo(b));
    assertEquals(Double.POSITIVE_INFINITY, a.slopeTo(e));
    assertEquals(Double.NEGATIVE_INFINITY, a.slopeTo(a));
    assertEquals(+0.0, e.slopeTo(f));
  }

  @Test
  void testCompareTo() {
    assertEquals(a.compareTo(b), -1); 
    assertEquals(d.compareTo(b), 1);
    assertEquals(c.compareTo(d), -1);
    
  }

  @Test
  void testSlopeOrder() {
    fail("Not yet implemented");
  }

}
