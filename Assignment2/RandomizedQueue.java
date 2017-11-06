import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] itemArray;
	private int size;


	public RandomizedQueue(){                 // construct an empty randomized queue
	    itemArray = (Item[]) new Object[2];
		size = 0;
	}


	public boolean isEmpty(){                 // is the queue empty?
		return (size == 0);
	}
	

	public int size(){                        // return the number of items on the queue
		return size;
	}


	public void enqueue(Item item){           // add the item
		if (item == null)
			throw new NullPointerException("Can not add null!");
		
		if (itemArray.length == size)
			resizeArray(2*itemArray.length);
	
		// add the element to the array
		itemArray[size++] = item;
	}


	private void resizeArray(int capacity){
		Item[] placeHolder = (Item[]) new Object[capacity];
		for (int i = 0; i < size; ++i){
			placeHolder[i] = itemArray[i];
		}
		// replace the itemArray with resized one
		itemArray = placeHolder;
	}


	public Item dequeue(){                    // remove and return a random item
		if (isEmpty())
			throw new NoSuchElementException("The Array is empty nothing to sample!");

		int randomIndex = StdRandom.uniform(0,size);

		// replace the randomly picked element with the last one!
		Item sample = itemArray[randomIndex];
		itemArray[randomIndex] = itemArray[size-1];
		itemArray[size-1] = null;
		size--;
		
		// check if shrinking required
		if ((size > 0) && (size == itemArray.length/4))
			resizeArray(itemArray.length/2);

		return sample;
	}


	public Item sample(){                     // return (but do not remove) a random item
		if (isEmpty())
			throw new NoSuchElementException("The Array is empty nothing to sample!");
		
		int randomIndex = StdRandom.uniform(0,size);
		
		// return the random element in the array
		return itemArray[randomIndex];
	}


	public Iterator<Item> iterator(){         // return an independent iterator over items in random order
		return new ReverseArrayIterator();
	}

	private class ReverseArrayIterator implements Iterator<Item>{
		
		private int i = size;

		public boolean hasNext(){ return i > 0; }
		public void remove(){ throw new UnsupportedOperationException("Unsupported Operation!"); }
		public Item next(){	
			
			if (!hasNext()){ throw new NoSuchElementException("The end of queue is reached! No more next elements..."); }
			return itemArray[--i];
		}
	}

	//public static void main(String[] args)   // unit testing (optional)
}
