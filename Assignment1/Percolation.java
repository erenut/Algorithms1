import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private int openCount;
	private int N;
	private int domSize;
	private boolean[][] domain;
	private WeightedQuickUnionUF perc; 
	
	public Percolation(int n){                // create n-by-n grid, with all sites blocked
		// throw exception for domainSize
		if (n <= 0) throw new IllegalArgumentException("domain is ill-defined");
		
		// Initialize number of open sites
		openCount = 0;
		
		// Total number of nodes Node[0] is being one pole 
		domSize = n;
		N = domSize*domSize + 1;
	    
		// Initialize the Domain with Blocked Sites
		domain = new boolean[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				domain[i][j] = false;
		
		// initialize union labor
		perc = new WeightedQuickUnionUF(N);
	}
	
	public void open(int row, int col){       // open site (row, col) if it is not open already
		// throw exceptions
		if (row <= 0 || row > domSize) throw new IndexOutOfBoundsException("row index i out of bounds");
		if (col <= 0 || col > domSize) throw new IndexOutOfBoundsException("column index j out of bounds");
		// map domain to id number
		int idxSite = 1 + (row-1)*domSize + (col-1);  
		
		// Check if the site is open
		if (!isOpen(row,col)){
			domain[row-1][col-1] = true;
			openCount++;
		
			// if the site is open, connect it to other open sites
			// UP bins
			if (row == 1){
				// Connect it to virtual node
				perc.union(0, idxSite);
			}
			else{
				if(isOpen((row-1),col))
					perc.union(idxSite-domSize, idxSite);
			}
			
			// DOWN bins
			if (row != (domSize)){
				if(isOpen((row+1),col))
					perc.union(idxSite+domSize, idxSite);
			}
			
			// LEFT bins
			if (col != 1){
				if(isOpen(row,(col-1)))
					perc.union(idxSite-1, idxSite);
			}
			
			// RIGHT bins
			if (col != (domSize)){
				if(isOpen(row,(col+1)))
					perc.union(idxSite+1, idxSite);
			}	
		}
	}
	
	public boolean isOpen(int row, int col){  // is site (row, col) open?
		// throw exceptions
		if (row <= 0 || row > domSize) throw new IndexOutOfBoundsException("row index i out of bounds");
		if (col <= 0 || col > domSize) throw new IndexOutOfBoundsException("column index j out of bounds");
		return domain[row-1][col-1];
	}
	
	public int numberOfOpenSites(){       // number of open sites
		return openCount;
	}

	public boolean isFull(int row, int col){  // is site (row, col) full?
		// throw exceptions
		if (row <= 0 || row > domSize) throw new IndexOutOfBoundsException("row index i out of bounds");
		if (col <= 0 || col > domSize) throw new IndexOutOfBoundsException("column index j out of bounds");
		// map domain to id number
		int idxSite = 1 + (row-1)*domSize + (col-1);  
		// check if the site is connected to the first virtual site
		return perc.connected(0, idxSite);
	}
	
	public boolean percolates(){              // does the system percolate?
		boolean ans = false;
		for (int j = 0; j < domSize; j++){
			if (perc.connected(0, (1 + (domSize-1)*domSize + j))){
				ans = true;
				break;
			}
		}
		return ans;
	}

	public static void main(String[] args){   // test client (optional)
		
		// Size of the domain
		int size = 10;
		
		// placeHolder for the probability estimate
		double probabilityEstimate = 0;
		
		// Generate an instance of Perculation
		Percolation deneme = new Percolation(size);
		
		while (!deneme.percolates()){
			// open randomly picked site
			deneme.open(StdRandom.uniform(1, size+1), StdRandom.uniform(1, size+1));
		}
		
		// Calculate the probability estimate
		probabilityEstimate = (double) deneme.numberOfOpenSites()/(size*size);
		
		System.out.println(probabilityEstimate);
		//System.out.println(StdRandom.uniform(0, size));
	}
}
