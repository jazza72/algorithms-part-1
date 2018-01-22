import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author jameswilson
 *
 */
public class FastCollinearPoints {
  
  private final Point[] points;
  private int numSegments = 0;
  
  public FastCollinearPoints(Point[] points) {
     
    if (points == null) 
      throw new IllegalArgumentException("Nulls not permitted");
    
    for (int i = 0; i<=points.length-1; i++) {
      if (points[i] == null)
        throw new IllegalArgumentException("Null Points are not permitted");      
    }
    
    //now check for duplicate points
    for (int i = 0; i<=points.length-1; i++) {
      for (int j = i+1; j<=points.length-1; j++) {
        if (points[i].compareTo(points[j]) == 0)
            throw new IllegalArgumentException("Duplicate Points not allowed");
      }
    }
    
    //assign to member variable
    this.points = points;
     
   }
  
   public int numberOfSegments()  {    
     return numSegments;
   }    
         
   public LineSegment[] segments()  {
     
     ArrayList<LineSegment> segments = new ArrayList<>();
     
     //go through the array of points, one by one
     //and sort the remaining sub-array using the slopeOrder() comparatory
     //returned from that Point
     
     Arrays.sort(points);
     
     for (int i=0; i<points.length-1; i++) {
       Comparator<Point >so = points[i].slopeOrder();
       
       //sort the remaining sub-array
       Arrays.sort(points, i+1, points.length, so);
       
       //debugging - need to look at the sorted slope orders
       //MAYBE SKIP wgeb i+1==points.length making a Point[0] length array
       double[] sortOrderCopyDEBUG1 = new double[points.length-(i+1)];
       
       for (int x = 0; x<sortOrderCopyDEBUG1.length; x++) {
         
         sortOrderCopyDEBUG1[x] = points[i].slopeTo(points[i+1+x]);
         
       }
       
       //now go through the sub-array, comparing the slopeTo() values 
       //from the pivot point [i] with each point in the subarray
       //when a sequence of 3 or more points in the subarray is found with 
       //the same slopeTo() values, then this is a collinear point set
       
       
       int count = 0;
       
       for (int j=i+1; j<=points.length-1; j++) {
         if (j<points.length-1 && points[i].slopeTo(points[j]) == points[i].slopeTo(points[j+1])) {
           count++;
         } 
         else {
           //the adjacent slopes are different
           //copy the pivot point and the set of matching slope points to a new array
           //but only if there are 3 or more points with the same slope
           //we check the count is >= 2 as the count of matches is one less than the number of points
                      
           if (count >= 2) { 
             Point[] pointMatches = new Point[count+2];
             pointMatches[0] = points[i];
             
             for (int p=0; p<=count; p++) {
               pointMatches[p+1] = points[j-p];
             }
             
              //now sort the pointMatches array
             Arrays.sort(pointMatches);
             
             segments.add(new LineSegment(pointMatches[0], pointMatches[pointMatches.length-1]));
             numSegments++;        
             
           }
           
           count = 0;
         }
       }
       
       Arrays.sort(points);
       
     }
     
     return segments.toArray(new LineSegment[segments.size()]);
     
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