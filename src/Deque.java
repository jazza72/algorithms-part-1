import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

/**
 * Implmmentation of double ended queue - allowing add/take operations at both ends All operations
 * are in O(1) time
 * 
 * @author jameswilson
 */
public class Deque<E> implements Iterable<E> {

  Node first;
  Node last;
  int size = 0;

  /**
   * Construct empty Deque
   */
  public Deque() {
  }

  /**
   * Check if queue is empty
   * 
   * @return boolean flag
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Return size of queue
   * 
   * @return size
   */
  public int size() {
    return size;
  }

  /**
   * Add item to front of queue
   * 
   * @param item
   */
  public void addFirst(E item) {

    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (first == null) {
      first = new Node(item);
    } else {
      Node temp = first;
      first = new Node(item);
      first.next = temp;
      temp.prev = first;
    }
    
    if (last == null)
      last = first;

    // increase size
    size++;

  }

  /**
   * Add item to the end of the queue.
   * 
   * @param item
   */
  public void addLast(E item) {

    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (last == null) {
      last = new Node(item);
    } else {
      Node temp = last;
      last = new Node(item);
      temp.next = last;
      last.prev = temp;
    }

    if (first == null) {
      first = last;
    }
    // increase size
    size++;

  }

  /**
   * Remove and return the item from the front.
   * 
   * @return E
   */
  public E removeFirst() {

    if (size == 0) {
      throw new NoSuchElementException();
    }

    Node temp = first;
    first = temp.next;
    
    if (first != null)
      first.prev = null;
    
    temp.next = null;

    size--;
    
    if (size == 0)
      last = first;

    return (E) temp.get();
  }

  /**
   * Remove and return the item from the end
   * 
   * @return e
   */
  public E removeLast() {

    if (size == 0) {
      throw new NoSuchElementException();
    }

    Node temp = last;
    last = temp.prev;
    
    if (last != null)
      last.next = null;
    
    temp.prev = null;

    size--;
    
    if (size == 0)
      first = last;

    return (E) temp.get();
  }

  /**
   * Return an iterator over items in order from front to end
   * 
   * @return iterator
   */
  public Iterator<E> iterator() {

    Iterator<E> iter = new Iterator<E>() {

      Node current;

      @Override
      public boolean hasNext() {

        if (current == null) {
          // first try
          if (first == null) {
            return false;
          } else {
            //current = first;
            return true;
          }
        } else {
          return (current.next == null) ? false : true;
        }

      }

      @Override
      public E next() {
        
        if (current == null && first != null) {
          current = first;
          return (E) current.data;
        } else if (current != null) {
           current = current.next;
           return (E) current.data;
        } else {
          throw new NoSuchElementException();
        }
        
      }
    };

    return iter;

  }

  private class Node<E> {

    E data;
    Node next;
    Node prev;

    Node(E data) {
      this.data = data;
    }

    E get() {
      return data;
    }

  }

  public static void main(String[] args) {

    Deque<String> q = new Deque<>();

    q.addFirst("james");
    q.addFirst("fiona");
    q.addLast("archie");
    q.addLast("india");

    for (String s : q)
      StdOut.print("\n" + s);
    
    StdOut.print("\n");
    q.removeLast();
    q.removeFirst();
    
    Iterator i = q.iterator();
    
    try {
      i.remove();
    } catch (Exception ex) {
      StdOut.print("Exeption caught: " + ex.getClass().getName());
    }
    
    for (String s : q)
      StdOut.print("\n" + s);
    
    StdOut.print("\n");
    q.removeLast();
    q.removeLast();
    
    for (String s : q)
      StdOut.print("\n" + s);
    
    StdOut.print("\n");
    
  }
}
