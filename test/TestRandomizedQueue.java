import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
class TestRandomizedQueue {

  RandomizedQueue<Integer> q;
  /**
   * 
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    
    q = new RandomizedQueue<>();
    q.enqueue(1);
    q.enqueue(2);
    q.enqueue(3);
    
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {
    q = null;
  }
  
  @Test
  void testSize() {
    assertTrue(q.size() == 3);
  }
  
  
  @Test
  void testIsEmpty() {
    assertFalse(q.isEmpty());
  }
  
  @Test
  void testDeqeue() {
    q.dequeue();
    assertTrue(q.size()==2);
  }
  
  @Test
  void testDequeuResize() {
    q.enqueue(1);
    q.enqueue(2);
    q.enqueue(3);
    q.enqueue(1);
    q.enqueue(2);
    q.enqueue(3);
    q.enqueue(1);
    q.enqueue(2);
    q.enqueue(3);
    q.enqueue(3);
    q.enqueue(1);
    q.enqueue(2);
    q.enqueue(3);
    q.dequeue();
    q.dequeue();
    q.dequeue();
    q.dequeue();
    q.dequeue();
    q.dequeue();
    q.dequeue();
    q.dequeue();
    q.dequeue();
    q.dequeue();
    q.dequeue();
    q.dequeue();
  }
  
  @Test 
  void testDequeueWhenEmpty() {
    
    //empty the queue
    q.dequeue();
    q.dequeue();
    q.dequeue();
    assertThrows(NoSuchElementException.class, () -> q.dequeue());
    
  }
  
  @Test
  void testEnqueue () {
    q.enqueue(1);
    assertFalse(q.isEmpty());
    q.enqueue(2);
    q.enqueue(3);
    assertTrue(q.size()==6);
  }
  
  @Test
  void testEnqueueWithNullElement() {
    assertThrows(IllegalArgumentException.class, () -> q.enqueue(null)); 
  }
  
  @Test
  void testIterator() {
    assertNotNull(q.iterator());
    
    Iterator<Integer> iteratorA = q.iterator();
    Iterator<Integer> iteratorB = q.iterator();
    
    assertTrue(iteratorA.next()!=iteratorB.next());

  }

}
