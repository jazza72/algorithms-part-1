import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 */

/**
 * @author jameswilson
 *
 */
class TestDeque {
  
  Deque<String> q;

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    
    q = new Deque<>();

    q.addFirst("james");
    q.addFirst("fiona");
    q.addLast("archie");
    q.addLast("india");

  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {
    
    q = null;
    
  }

  /**
   * Test method for {@link Deque#isEmpty()}.
   */
  @Test
  void testIsEmpty() {
    
    assertFalse(q.isEmpty());
    
    q.removeLast();
    q.removeLast();
    
    assertFalse(q.isEmpty());
    
    q.removeFirst();
    q.removeFirst();
    
    assertTrue(q.isEmpty());
    
    q.addFirst("poppy");
    
    assertFalse(q.isEmpty());
  }

  /**
   * Test method for {@link Deque#size()}.
   */
  @Test
  void testSize() {

    assertTrue(q.size() == 4);
    
    q.removeLast();
    q.removeLast();
    
    assertTrue(q.size() == 2);
    
    q.removeFirst();
    q.removeFirst();
    
    assertTrue(q.size() == 0);
    
    q.addFirst("poppy");
        
    assertTrue(q.size() == 1);
    
  }

  /**
   * Test method for {@link Deque#addFirst(java.lang.Object)}.
   */
  @Test  
  void testAddFirstWhenItemNull() {
    
    assertThrows(IllegalArgumentException.class, () -> q.addFirst(null));
    
  }
  
  /**
   * Test method for {@link Deque#addFirst(java.lang.Object)}.
   */
  @Test  
  void testAddFirst() {
    q.addFirst("poppy");
    assertTrue(q.removeFirst().equals("poppy"));
    
  }

  /**
   * Test method for {@link Deque#addLast(java.lang.Object)}.
   */
  @Test
  void testAddLast() {
    q.addLast("poppy");
    assertTrue(q.removeFirst().equals("poppy"));

  }

  /**
   * Test method for {@link Deque#removeFirst()}.
   */
  @Test
  void testRemoveFirst() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link Deque#removeLast()}.
   */
  @Test
  void testRemoveLast() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link Deque#iterator()}.
   */
  @Test
  void testIterator() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link Deque#main(java.lang.String[])}.
   */
  @Test
  void testMain() {
    fail("Not yet implemented");
  }

}
