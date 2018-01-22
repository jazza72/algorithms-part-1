import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

/**
 * Implmmentation of double ended queue - allowing add/take operations at both ends All operations
 * are in O(1) time
 * 
 * @author jameswilson
 */
public class Deque<Item> implements Iterable<Item> {

  private Node<Item> first;
  private Node<Item> last;
  private int size = 0;

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
  public void addFirst(Item item) {

    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (first == null) {
      first = new Node<Item>(item);
    } else {
      Node<Item> temp = first;
      first = new Node<Item>(item);
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
  public void addLast(Item item) {

    if (item == null) {
      throw new IllegalArgumentException();
    }

    if (last == null) {
      last = new Node<Item>(item);
    } else {
      Node<Item> temp = last;
      last = new Node<Item>(item);
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
  public Item removeFirst() {

    if (size == 0) {
      throw new NoSuchElementException();
    }

    Node<Item> temp = first;
    
    first = temp.next;
    
    if (first != null)
      first.prev = null;
    
    temp.next = null;

    size--;
    
    if (size == 0)
      last = first;

    return temp.get();
  }

  /**
   * Remove and return the item from the end
   * 
   * @return e
   */
  public Item removeLast() {

    if (size == 0) {
      throw new NoSuchElementException();
    }

    Node<Item> temp = last;

    last = temp.prev;
    
    if (last != null)
      last.next = null;
    
    temp.prev = null;

    size--;
    
    if (size == 0)
      first = last;

    return temp.get();
  }

  /**
   * Return an iterator over items in order from front to end
   * 
   * @return iterator
   */
  public Iterator<Item> iterator() {

    Iterator<Item> iter = new Iterator<Item>() {

      Node<Item> current;

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
      public Item next() {
        
        if (current == null && first != null) {
          current = first;
          return current.data;
        } else if (current != null) {
           current = current.next;
           return current.data;
        } else {
          throw new NoSuchElementException();
        }
        
      }
    };

    return iter;

  }


  //@SuppressWarnings("hiding")
private class Node<Item> {

    Item data;
    Node<Item> next;
    Node<Item> prev;

    Node(Item data) {
      this.data = data;
    }

    Item get() {

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
    
    Iterator<String> i = q.iterator();
    
    //try {
      i.remove();
    //} catch (Exception ex) {
    //  StdOut.print("Exeption caught: " + ex.getClass().getName());
    //}
    
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
