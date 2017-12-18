import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * .
 */

/**
 * .
 * @author jameswilson
 *
 */
public class RandomizedQueue<Item> implements Iterable<Item>{

  private int size;
  private Item[] queue;
  
  /**
   * NoArg Constructor
   */
  //@SuppressWarnings("unchecked")
  public RandomizedQueue() {
    //create array for queue
    queue = (Item[]) new Object[2];
  }

  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return size == 0;
  }

  public int size() {
    // TODO Auto-generated method stub
    return size;
  }

  /**
   * Adds an element E onto the tail of the queue
   * @param data
   */
  public void enqueue(Item data) {
    
    if (data == null)
      throw new IllegalArgumentException();
    
    
    
    if (size+1 > queue.length) {
      //resize by factor of 2*size
      resize(2*size);
    }
      
    size++;
    queue[size-1] = data;
    
  }
  
  /**
   * Remove random element from queue
   * @return int - the random element selected
   */
  public Item dequeue() {
    
    //if size<1 
    if (size<1) {
      throw new NoSuchElementException();
    }
     
    //resolve to random array index
    int index = randomIndex();
    
    //set element to null
    Item element = queue[index];
    queue[index] = queue[size-1];
    queue[size-1] = null;
    
    //decrement size
    size--;
    
    //check if the backing arrqy needs resizing 
    //if the occupied size (non-null elements) is <= 0.25 of array capacity 
    //then resize(2*size)
    if (size <= queue.length/4)
      resize(2*size);
    
    return element;
    
  }
  
  public Item sample() {
	  if (size < 1) {
		  throw new NoSuchElementException();
	  }
	  
	  return queue[randomIndex()];
  }
  
  private int randomIndex() {
    return StdRandom.uniform(0, size);    
  }
  
  /**
   * Method for resizing arroy when it fills or empties
   * @param integer factor to resize by
   */
  private void resize (int factor) {
    
    //@SuppressWarnings("unchecked")
    Item[] resized = (Item[]) new Object[factor];
    
    //now copy elements from old to new queue
    for (int i = 0; i<size; i++) {
        resized[i] = queue[i];
    }
      
    //assign back
    queue = resized;
      
  }

  @Override
  public Iterator<Item> iterator() {
    
    return new RandomizedQueueIterator();

  }
  private class RandomizedQueueIterator implements Iterator<Item>{
    

    Item[] items;
    int current = 0;
    
    //@SuppressWarnings("unchecked")
    public RandomizedQueueIterator() {
      
      //copy over main queue array
      items = (Item[]) new Object[size];
      
      for (int i=0; i<size; i++) {
        items[i] = queue[i];
      }
      
      //and shuffle
      StdRandom.shuffle(items);
      
    }

    @Override
    public boolean hasNext() {
      
      if (current+1<=size-1) {
        return true;
      }
        
      return false;
        
    }

    @Override
    public Item next() {
    		
    		if (!hasNext()) {
    			throw new NoSuchElementException();
    		}
    		
      return items[++current];
    }
       
    
  }
   
}
