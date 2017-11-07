import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;


public class BruteCollinearPoints {

	// allocate segment data
	private ArrayList<LineSegment> segments = new ArrayList<>();
	

	public BruteCollinearPoints(Point[] points){    // finds all line segments containing 4 points
		if (points == null)
			throw new NullPointerException("No Points are given!"); 

		// get the number of points
		int n_Points = points.length;

		// sort the points
		Point[] sortedPoints = points.clone();
		Arrays.sort(sortedPoints);

		if (hasDuplicate(sortedPoints))
			throw new IllegalArgumentException("Duplicate points!");


		// allocate memory 
		double s1, s2;

		for (int i=0; i<n_Points; i++)
			for (int j=i+1; j<n_Points; j++)
				for (int k=j+1; k<n_Points; k++)
					for (int l=k+1; l<n_Points; l++){
						s1 = sortedPoints[i].slopeTo(sortedPoints[j]);
						s2 = sortedPoints[k].slopeTo(sortedPoints[l]);

						// if slopes are equal check if all slopes are equal
						if (s1 == s2)
							if (s1 == sortedPoints[j].slopeTo(sortedPoints[k])){
								segments.add(new LineSegment(sortedPoints[i],sortedPoints[l]));
							}		
					}
	}


	private boolean hasDuplicate(Point[] points){ // check if there is a duplicate points
		for (int i = 0; i < points.length-1; i++)
			if (points[i].compareTo(points[i+1]) == 0)
				return true;
		return false;
	}


	public int numberOfSegments(){        // the number of line segments
		return segments.size();
	}


	public LineSegment[] segments(){      // the line segments
		return segments.toArray(new LineSegment[numberOfSegments()]);		
	}


	public static void main(String[] args) {
		
		// read the n points from a file
	    In in = new In(args[0]);
		int n = in.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}
		
		// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();    

		// print and draw the line segments
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}

}
