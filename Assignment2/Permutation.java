import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class Permutation {
	
	public static void main(String[] args){
		
		// take the input 
		int k = Integer.parseInt(args[0]);

		// define the container for the data
		RandomizedQueue<String> container = new RandomizedQueue<String>(); 		
		
		// read strings from a file!
		while (StdIn.hasNextChar()){
			String inputData = StdIn.readString();
			
			//enqueue the incoming data
			container.enqueue(inputData);

			//StdOut.printf("%s\n", inputData);
		}

		for (int i = 0; i < k; ++i){
			// take a data from the queue
			StdOut.printf("%s\n", container.dequeue());
		}

	}
}
