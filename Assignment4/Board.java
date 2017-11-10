import java.util.*;
import java.lang.Math;
import edu.princeton.cs.algs4.MinPQ;


public class Board {

	private final int n;
	private final int[][] blocks;
	private final int[] emptyBlockIndex = new int[2];

	// construct a board from an n-by-n array of blocks
	public Board(int[][] blocks){          // (where blocks[i][j] = block in row i, column j)

		// get the dimension of the blocks
		n = blocks.length;

		// allocate memory for blocks structure
		this.blocks = new int[n][n]; 
		
		// fill the blockData with input
		for (int i = 0; i < n; ++i)
			for(int j = 0; j < n; ++j){
				this.blocks[i][j] = blocks[i][j];
			}

		// identify the location of the empty block
		findEmptyBlockIndices();
	}   


	// CONSTRUCTOR 2: construct a board from an n-by-n array of blocks, and pass the empty block indices
	private Board(int[][] blocks, int empty_i, int empty_j){          // (where blocks[i][j] = block in row i, column j)

		// get the dimension of the blocks
		n = blocks.length;

		// allocate memory for blocks structure
		this.blocks = new int[n][n]; 
		
		// fill the blockData with input
		for (int i = 0; i < n; ++i)
			for(int j = 0; j < n; ++j){
				this.blocks[i][j] = blocks[i][j];
			}

		// assign the indices of the empty blocks
		emptyBlockIndex[0] = empty_i;
		emptyBlockIndex[1] = empty_j;
	}    


	// board dimension n
	public int dimension() {              
		return this.blocks.length;
	}


	// number of blocks out of place
	public int hamming() {                  
		int hammingCount = 0;
		for (int i = 0; i < n-1; ++i)
			for (int j = 0; j < n; ++j)
				if (this.blocks[i][j] != (n*i + j + 1))
					++hammingCount;
		
		for (int j = 0; j < n-1; ++j)
			if (this.blocks[n-1][j] != (n*(n-1) + j + 1))
				++hammingCount;
		
		// return the number of blocks out of place
		return hammingCount;
	}
	

	// sum of Manhattan distances between blocks and goal
	public int manhattan() {  
		int manhattanCount = 0;
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < n; ++j)
				if ((this.blocks[i][j] != (n*i + j + 1)) && (this.blocks[i][j] != 0)){
					manhattanCount += Math.abs(((this.blocks[i][j]-1) / n) - i); // for y motion
					manhattanCount += Math.abs(((this.blocks[i][j]-1) % n) - j); // for x motion
				}
		
		// return the number of blocks out of place
		return manhattanCount;

	}
	

	// is this board the goal board?
	public boolean isGoal() {              
		for (int i = 0; i < n-1; ++i)
			for (int j = 0; j < n; ++j)
				if (this.blocks[i][j] != (n*i + j + 1))
					return false;
				

		for (int j = 0; j < n-1; ++j)
			if (this.blocks[n-1][j] != (n*(n-1) + j + 1))
				return false;

		return true;
	}
	

	// a board that is obtained by exchanging any pair of blocks
	public Board twin(){
		Board twinBoard;
		if (blocks[0][0] == 0)
			twinBoard = new Board(twinBlocks(0,1,n-1,n-1));
		else if (blocks[n-1][n-1] == 0)
			twinBoard = new Board(twinBlocks(0,0,n-1,n-2));
		else
			twinBoard = new Board(twinBlocks(0,0,n-1,n-1));

		return twinBoard;
	}


	// does this board equal y?
	public boolean equals(Object y){        
		if (y == this) return true;
		if (y == null) return false;
		if (y.getClass() != this.getClass()) return false;
		
		Board that = (Board) y;
		if (this.n != that.n) 
			return false;
		else
			for (int i = 0; i < n; ++i)
				for (int j = 0; j < n; ++j)
					if (this.blocks[i][j] != that.blocks[i][j])
						return false;
		
		// if all tests has been passed return true
		return true;
	}


	// all neighboring boards
	public Iterable<Board> neighbors(){
		//define the container to be returned
		List<Board> neighbors = new LinkedList<Board>();

		// CHECK all conditions based on the location of zero index
		// NORTH condition
		if (emptyBlockIndex[0] != 0)
			neighbors.add(new Board(twinBlocks(emptyBlockIndex[0],emptyBlockIndex[1],(emptyBlockIndex[0]-1),emptyBlockIndex[1]), (emptyBlockIndex[0]-1), emptyBlockIndex[1]));

		// SOUTH condition
		if (emptyBlockIndex[0] != (n-1))
			neighbors.add(new Board(twinBlocks(emptyBlockIndex[0],emptyBlockIndex[1],(emptyBlockIndex[0]+1),emptyBlockIndex[1]), (emptyBlockIndex[0]+1), emptyBlockIndex[1]));

		// WEST condition
		if (emptyBlockIndex[1] != 0)
			neighbors.add(new Board(twinBlocks(emptyBlockIndex[0],emptyBlockIndex[1],emptyBlockIndex[0],(emptyBlockIndex[1]-1)), emptyBlockIndex[0], (emptyBlockIndex[1]-1)));

		// EAST condition
		if (emptyBlockIndex[1] != (n-1))
			neighbors.add(new Board(twinBlocks(emptyBlockIndex[0],emptyBlockIndex[1],emptyBlockIndex[0],(emptyBlockIndex[1]+1)), emptyBlockIndex[0], (emptyBlockIndex[1]+1)));

		// return the container
		return neighbors;
	}


	// find the location of the empty block
	private void findEmptyBlockIndices(){
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < n; ++j)
				if (blocks[i][j] == 0){
					emptyBlockIndex[0] = i;
					emptyBlockIndex[1] = j;
					return;
				}
	}
	

	// swapping two elements of the blocks
	private int[][] twinBlocks(int i1, int j1, int i2, int j2){
		
		// allocate memory for twinData
		int[][] twinData = new int[n][n];
		
		// copy the contents of the original block data
		for (int i = 0; i < n; ++i)
			for(int j = 0; j < n; ++j)
				twinData[i][j] = blocks[i][j];

		// swap two elements of the twinData
		twinData[i1][j1] = blocks[i2][j2];
		twinData[i2][j2] = blocks[i1][j1];

		return twinData;
	}
	

	// Convert Data to String
	public String toString(){               // string representation of this board (in the output format specified below)
		StringBuilder s = new StringBuilder();
		s.append(n + "\n");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				s.append(String.format("%2d ", this.blocks[i][j]));
		    }
			s.append("\n");
	    }
		return s.toString();
	}


	// unit tests (not graded)
	public static void main(String[] args){} 	
}
