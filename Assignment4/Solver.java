import java.util.*;
import java.lang.NullPointerException;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Solver {

	private boolean solvability;
	private int minMoveCount;
	private Node finalNode;


	// Define a private class 
	private class Node implements Comparable<Node> {
		private final Board board;
		private final int currentMoveCount;
		private final Node parent;
		private final int priority;

		public Node(Board board, int currentMoveCount, Node parent){
			this.board = board;
			this.currentMoveCount = currentMoveCount;
			this.parent = parent;

			// priority with Manhattan
			priority = currentMoveCount + board.manhattan();
		}
		
		public int compareTo(Node that){
			if (that == null)
				throw new NullPointerException("Null Node to Compare!");
		
			// return the compare ouput
			return (this.priority - that.priority);
		}
	};


	// find a solution to the initial board (using the A* algorithm)
	public Solver(Board initial){
		// initialize the internal values
		solvability = true;
		minMoveCount = 0;

		// define the queue structures for the solver
		MinPQ<Node> priorityQueue = new MinPQ<Node>();
		MinPQ<Node> twin_priorityQueue = new MinPQ<Node>();

		// push the initial node to the priority queue and its twin to twin queue
		priorityQueue.insert(new Node(initial, 0, null));
		twin_priorityQueue.insert(new Node(initial.twin(), 0, null));

		// main loop for traversing the tree
		boolean terminationFlag = false;
		while (!terminationFlag){
			
			// run oneStepMove for each board until one of them is solvable
			terminationFlag = oneStepMove(priorityQueue);

			// update the keepGoingFlag
			if (oneStepMove(twin_priorityQueue)){
				terminationFlag = true;
				solvability = false;
			}
		}
	}
    

    // pull node from queue and add its neighbors to the queue
	private boolean oneStepMove(MinPQ<Node> currentQueue){
		
		// pull the node with minimum priourity 
		Node currentNode = currentQueue.delMin();

		// check if goal is reached
		if (currentNode.board.isGoal()){
			finalNode = currentNode;
			minMoveCount = currentNode.currentMoveCount;
			return true;
		}

		// define iterable and iterable and iterator
		Iterable<Board> neighbors = currentNode.board.neighbors();
		Iterator<Board> iter = neighbors.iterator();
		
		// loop over Boards in the iterable object 
		while(iter.hasNext()){
			Board currentNeighborBoard = iter.next();
			// if the board is not equal to parents node then insert it to queue
			if((currentNode.parent == null) || (!currentNode.parent.board.equals(currentNeighborBoard))){
				currentQueue.insert(new Node(currentNeighborBoard,(currentNode.currentMoveCount+1),currentNode)); 
			}
		}
	
		return false;
	}


	// is the initial board solvable?
    public boolean isSolvable(){
    	return solvability;
	}
    
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
		if (!isSolvable())
			return -1;
		return minMoveCount;
	}


	// sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
		if (!isSolvable())
			return null;
		
		// define the container that contains the solution
		Stack<Board> solution = new Stack<Board>();

		// backtrack the path of solution from the final node
		Node headNote = finalNode;
		while (headNote.parent != null){
			// push the solution board to the set of solutions
			solution.push(headNote.board);
			// update the head note to traverse the solution
			headNote = headNote.parent;
		}
		// add the first node to the solution
		solution.push(headNote.board);

		// return the final container
		Collections.reverse(solution);
		return solution;
	}
	

	// solve a slider puzzle (given below)
	public static void main(String[] args){ 

		// create initial board from file
		In in = new In(args[0]);
		int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	    	for (int j = 0; j < n; j++)
	    		blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else{ 
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);     
		}
	}
}
