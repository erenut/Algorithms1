import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;


public class FastCollinearPoints {

	// allocate segment data
	private ArrayList<LineSegment> segments = new ArrayList<>();
	// allocate endpoints data for segments
	private class endPoints{
		Point e1;
		Point e2;
		private endPoints(Point e1, Point e2){
			this.e1 = e1;
			this.e2 = e2;
		}
	}
	// create array of endpoints for segments
	private ArrayList<endPoints> segmentsEndPoints = new ArrayList<>();
		


	public FastCollinearPoints(Point[] points){    // finds all line segments containing 4 points
		if (points == null)
			throw new NullPointerException("No Points are given!"); 

		// get the number of points
		int n_Points = points.length;

		// allocate memory 
		double s1, s2;

		// check if duplicate
		Point[] cPoints = points.clone();
		Arrays.sort(cPoints);
		if (hasDuplicate(cPoints))
			throw new IllegalArgumentException("Duplicate points!");


		// start sorting points with slopes from each node
		for(int i=0; i < n_Points; i++){

			// Assign the current point 
			Point currentPoint = points[i];
			
			// Take a copy of the array
			Point[] sortedPoints = points.clone();

			// Sort array by via slopes from the ith element
			Arrays.sort(sortedPoints, currentPoint.slopeOrder());
			
			// allocate memory for candidate aligned points
			ArrayList<Point> alignedPoints = new ArrayList<>();
			
			// initialize slope variables
			int pointCounter = 0;
			s1 = currentPoint.slopeTo(sortedPoints[0]);
			for (int j=1; j < n_Points; j++){
				
				s2 = currentPoint.slopeTo(sortedPoints[j]);
				if (s1 == s2){
					if (pointCounter == 0){
		 				alignedPoints.add(sortedPoints[j-1]);
						pointCounter++;
					}
					alignedPoints.add(sortedPoints[j]);
					pointCounter++;
				}
				else{
					if (pointCounter >= 3){
						alignedPoints.add(currentPoint);
						add_if_New_Segment(alignedPoints.toArray(new Point[alignedPoints.size()]));	
					}
					alignedPoints.clear();
					pointCounter = 0;
				}	
				s1 = s2;
			}

			if (pointCounter >= 3){
				alignedPoints.add(currentPoint);
				add_if_New_Segment(alignedPoints.toArray(new Point[alignedPoints.size()]));	
			}
		}
	}

	// add segment if it is new! 
	private void add_if_New_Segment(Point[] candidatePoints){
		// sort the points based on natural order
		Arrays.sort(candidatePoints);

		// get min and max points
		Point minPoint = candidatePoints[0];
		Point maxPoint = candidatePoints[candidatePoints.length-1];

		if (numberOfSegments() == 0){
			// update segment endpoints
			segmentsEndPoints.add(new endPoints(minPoint,maxPoint));
			// add new segment to the lineSegment arraylisy
			segments.add(new LineSegment(minPoint,maxPoint));
		}
		else{
			for (int k=0; k < numberOfSegments(); k++){
				if ((minPoint.compareTo(segmentsEndPoints.get(k).e1) == 0) && (maxPoint.compareTo(segmentsEndPoints.get(k).e2) == 0))
					return;
			}
			
			//StdOut.print("BURAYA GIRDIM!\n");
			//StdOut.print(minPoint.toString());
			//StdOut.print("   ");
			//StdOut.print(maxPoint.toString());
			//StdOut.print("         ");
			//StdOut.print(segmentsEndPoints.get(numberOfSegments()-1).e1.toString());
			//StdOut.print("   ");
			//StdOut.print(segmentsEndPoints.get(numberOfSegments()-1).e2.toString());				
			//StdOut.print("         ");
			//StdOut.print((minPoint.compareTo(segmentsEndPoints.get(k).e1)));
			//StdOut.print("   ");
			//StdOut.print((maxPoint.compareTo(segmentsEndPoints.get(k).e2)));
			//StdOut.print("\n");
			segmentsEndPoints.add(new endPoints(minPoint,maxPoint));
			segments.add(new LineSegment(minPoint,maxPoint));
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
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}
}
