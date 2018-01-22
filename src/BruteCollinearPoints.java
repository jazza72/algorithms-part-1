import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
  
  private final Point[] points;
  private int numSegments = 0;
  
  public BruteCollinearPoints(Point[] points) {
     
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
            throw new IllegalArgumentException("Duplicate Points not permitted");
      }
    }
    
    //assign to member varioble
    this.points = points;

   }
   public int numberOfSegments()  {    
     return numSegments;
   }    
   
    
   private boolean checkCollinearPoints(Point[] data) {
     
     return (data[0].slopeTo(data[1]) == data[1].slopeTo(data[2]) &&
         data[1].slopeTo(data[2]) == data[2].slopeTo(data[3]));
     
   }
   
   public LineSegment[] segments()  {
   
     Point[] data = new Point[4];
     ArrayList<LineSegment> segments = new ArrayList<>();
     
      for (int i=0; i<=points.length-1; i++) {
        for (int j=i+1; j<=points.length-1; j++) {
          for (int k=j+1; k<=points.length-1; k++) {
            for (int p=k+1; p<=points.length-1; p++) {
              data[0] = points[i];
              data[1] = points[j];
              data[2] = points[k];
              data[3] = points[p];
              
              Arrays.sort(data);
              
              if (this.checkCollinearPoints(data)) {
                segments.add(new LineSegment(data[0], data[3]));
                numSegments++;                
              }
            }
          }
        }
      }
      
      return segments.toArray(new LineSegment[segments.size()]);
     
   }    
   
//   private Point[] extractPointsFromSegment (LineSegment segment) {
//     
//     Point[] pointsForSegment = new Point[2];
//     
//     //the regex pattern to match
//     //(4872, 22890) -> (5424, 24273)
//     Pattern pattern = Pattern.compile("\\((\\d+)\\W\\s(\\d+)\\)\\s\\W+\\s\\((\\d+)\\W\\s(\\d+)\\)");
//     Matcher matcher = pattern.matcher(segment.toString());
//
//     matcher.matches();
//     
//     //instantiate the points the points 
//     pointsForSegment[0] = new Point (Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))); 
//     pointsForSegment[1] = new Point (Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4))); 
//     
//     
//     return pointsForSegment;
//     
//   }
//   
//  private boolean isSubSegment(LineSegment existing, LineSegment current) {
//    
//    Point[] existingAsPoints = this.extractPointsFromSegment(existing);
//    Point[] currentAsPoints = this.extractPointsFromSegment(current);
//    
//    //compare point ranges for overlap
//    if (existingAsPoints[0].compareTo(currentAsPoints[0]) <= 0
//        && existingAsPoints[0].compareTo(currentAsPoints[0]) >= 0) {
//      return true;
//    } else {
//      return false;
//    }
//   
//  }
   
   public static void main(String[] args) {

//       // read the n points from a file
//       In in = new In(args[0]);
//       int n = in.readInt();
//       Point[] points = new Point[n];
//       for (int i = 0; i < n; i++) {
//           int x = in.readInt();
//           int y = in.readInt();
//           points[i] = new Point(x, y);
//       }
//
//       // draw the points
//       StdDraw.enableDoubleBuffering();
//       StdDraw.setXscale(0, 32768);
//       StdDraw.setYscale(0, 32768);
//       for (Point p : points) {
//           p.draw();
//       }
//       StdDraw.show();
//
//       // print and draw the line segments
//       BruteCollinearPoints collinear = new BruteCollinearPoints(points);
//       for (LineSegment segment : collinear.segments()) {
//           StdOut.println(segment);
//           segment.draw();
//       }
//       StdDraw.show();
//   }
//     BruteCollinearPoints.extractPointsFromSegment();

  }
     

}