import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {

	// Define internal parameters of the class Deque
	private Node first, last;
	private int size;
	private class Node{
		Item item;
		Node previous;
		Node next;
	}


	public Deque(){                           // construct an empty deque
		// Initialize the Deque
		first = null;
		last = null;
		size = 0;
	}


    public boolean isEmpty(){                 // is the deque empty?
		return (size == 0);
	}


    public int size(){                        // return the number of items on the deque
		return size;
	}


    public void addFirst(Item item){          // add the item to the front
		if (item == null){ throw new NullPointerException("Can not queue null!");}	
		
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.next = oldFirst;
		
		if (isEmpty()) { last = first; }
		else{ oldFirst.previous = first; }  
		
		// increase the size of the deque
		size++;
	}
    

    public void addLast(Item item){           // add the item to the end
		if (item == null){ throw new NullPointerException("Can not queue null!");}
		
		Node oldLast = last;
		last = new Node();
		last.item = item;
		last.previous = oldLast;

		if (isEmpty()) { first = last; }
		else{ oldLast.next = last; }

		// increase the size of the deque
		size++;
	}
   

	public Item removeFirst(){                // remove and return the item from the front
		if (isEmpty()){ throw new NoSuchElementException("Deque is empty nothing to remove!"); }
		
		Item item = first.item;
		first = first.next;

		// decrease the size of the deque
		size--;

		if (isEmpty()){ last = null; }
		else { first.previous = null; }
		
		// return the item 
		return item;
	}
    	
    public Item removeLast(){                 // remove and return the item from the end
    	if (isEmpty()){ throw new NoSuchElementException("Deque is empty nothing to remove!"); }

    	Item item = last.item;
    	last = last.previous;

    	// decrease the size of the deque
    	size--;

    	if (isEmpty()){ first = null; }
		else { last.next = null; }

		// return the item
		return item;
    }
    
    public Iterator<Item> iterator(){         // return an iterator over items in order from front to end
		return new ListIterator();
	}


	// Implement iterator structor
    private class ListIterator implements Iterator<Item>{
		private Node current = first; 

		public boolean hasNext(){ return current != null; }
		public void remove(){	
			throw new UnsupportedOperationException("Unsupported Operation!"); 
		}
		public Item next()
		{
			if (!hasNext())
				throw new NoSuchElementException("The end of queue is reached! No more next elements..."); 
			
			Item item = current.item;
			current   = current.next;
			return item;
		}
	}
    

    //public static void main(String[] args){   // unit testing (optional)
	//}
}
