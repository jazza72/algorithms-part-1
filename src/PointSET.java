import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * @author jameswilson
 *
 */
public class PointSET {
  
  TreeSet<Point2D> tree;
  
  /**
   * Construct an empty set of points
   */
   public PointSET() {
    tree = new TreeSet<>();
   }
   
   /** Is the set empty?
    * 
    * @return boolean 
    */
   public boolean isEmpty()  {
     return tree.isEmpty();
   }
   
   /**
    * Number of points in the set
    * @return number
    */
   public int size() {
     return tree.size();
   }
   
   /**
    * Add the point to the set (if it is not already in the set)
    * @param p
    */
   public void insert(Point2D p)     {     
     if (p == null) 
       throw new IllegalArgumentException("Argument must not be null");
     
     if (!tree.contains(p)) 
       tree.add(p);
     
   }
   
   /**
    * Does the set contain point p? 
    * @param p
    * @return
    */
   public boolean contains(Point2D p) {
     if (p == null) 
       throw new IllegalArgumentException("Argument must not be null");

     return tree.contains(p);
   }
   
   /**
    * Draw all points to standard draw
    */
   public void draw() {
     
     StdDraw.setPenColor(StdDraw.BLACK);
     StdDraw.setPenRadius(0.01);
     
     for (Point2D point : tree) {
      point.draw();
     }

   }
   
   /**
    * All points that are inside the rectangle (or on the boundary)
    * @param rect
    * @return
    */
   public Iterable<Point2D> range(RectHV rect)  {
     if (rect == null) 
       throw new IllegalArgumentException("Argument must not be null");
     
     List<Point2D> l = new ArrayList<>();
     
     for (Point2D point : tree) {
       if (rect.contains(point))
         l.add(point);
     }
     
     return l;
  
   }   
   
   /**
    * A nearest neighbor in the set to point p; null if the set is empty 
    * @param p
    * @return
    */
   public Point2D nearest(Point2D p)    {
     if (p == null) 
       throw new IllegalArgumentException("Argument must not be null");

     Point2D nearest = null;
     
     for (Point2D point : tree) {
       if (nearest == null) {
         nearest = point;
         continue;
       }
       
       if (point.distanceTo(p) < nearest.distanceTo(p))
         nearest = point;
     }
     
     return nearest;
        
   }

   public static void main(String[] args)     {
     // initialize the data structures from file
     String filename = args[0];
     In in = new In(filename);
     PointSET brute = new PointSET();
     while (!in.isEmpty()) {
         double x = in.readDouble();
         double y = in.readDouble();
         Point2D p = new Point2D(x, y);
        // kdtree.insert(p);
         brute.insert(p);
     }
     
     brute.draw();
   }
}
