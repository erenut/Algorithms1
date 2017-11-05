import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
	
	private int T;
	private double[] percData;
	
	public PercolationStats(int n, int trials){    // perform trials independent experiments on an n-by-n grid
		// Throw exceptions
		if (n <= 0 || trials <= 0) throw new IllegalArgumentException("problem is ill-defined");
		
		// Initialization of parameters
		T = trials;
		percData = new double[T];
		
		// Perform independent Trials
		for (int i = 0; i < T; i++){
			// create the percolation instance
			Percolation perc = new Percolation(n);
			
			while (!perc.percolates()){
				// open randomly picked site
				perc.open(StdRandom.uniform(1, n+1), StdRandom.uniform(1, n+1));
			}
			
			// Calculate the probability estimate
			percData[i] = (double) perc.numberOfOpenSites()/(n*n);
		}
	}
	   
	   public double mean(){ 		// sample mean of percolation threshold
		   double sum = 0;
		   for(int i = 0; i < T; i++)
			   sum = sum + percData[i];
		   return (sum/T);
	   }
	   
	   public double stddev(){      // sample standard deviation of percolation threshold
		   double mean = mean();
		   double std = 0;
		   for(int i = 0; i < T; i++)
			   std = std + (percData[i] - mean)*(percData[i] - mean);
		   return Math.sqrt(std/(T-1));
	   }
	   
	   public double confidenceLo(){ // low  endpoint of 95% confidence interval
		   return (mean() - (1.96*stddev())/(Math.sqrt(T)));
	   }
	   
	   public double confidenceHi(){                  // high endpoint of 95% confidence interval
		   return (mean() + (1.96*stddev())/(Math.sqrt(T)));
	   }
		   
	   public static void main(String[] args){        // test client (described below) 
		   
		   // Get the data from user
		   int n = Integer.parseInt(args[0]);
		   int T = Integer.parseInt(args[1]);
		   
			// Throw exceptions
			if (n <= 0 || T <= 0) throw new IndexOutOfBoundsException("problem is ill-defined");
		   
		   // Calculate the trials
		   PercolationStats deneme = new PercolationStats(n,T);
		   
		   // Output the results
		   StdOut.printf("mean                    = %.10f\n", deneme.mean());
		   StdOut.printf("stddev                  = %.10f\n", deneme.stddev());
		   StdOut.printf("95%% confidence interval = [%.10f, %.10f]\n", deneme.confidenceLo(), deneme.confidenceHi());
	   }
	}
