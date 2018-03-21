import java.util.ArrayList;
import java.util.List;

import apple.laf.JRSUIUtils.Tree;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import jdk.nashorn.internal.ir.Node;

/**
 * @author jameswilson
 *
 */
public class KdTree {
  
  Node root = null;
  int nodeCount;
  
  //temp variable to hold nodes for drawing
  List<Node> l = new ArrayList<>();
  
  /**
    * Construct an empty set of points 
    */
   public KdTree() {
     // construct an empty set of points 
   }
   
   /**
    * Is the set empty?
    * @return
    */
   public boolean isEmpty() {
     return (root == null);
   }
   
   /**
    * Number of points in the set
    * @return number
    */
   public int size() {
     return nodeCount; 
   }
   
   /**
    * Add the point to the set (if it is not already in the set)
    * @param p
    */
   public void insert(Point2D p) {
        
     //if root is null, then assign p to root
     if (root == null) {
       root = new Node (p, true);
       nodeCount++;
       l.add(root);
       return;
     }
     
     //if not, then we need to start traversing the tree
     //comparing the x-axis value of the insertion point with the
     //x-axis value of the tree Mode on odd levels in the tree, and 
     //the y-axis comparison on even levels in the tree
     put(null, root, p, true, false);

  }
   
   /**
    * Private helper method to find Point2D node in tree
    * @return boolean - was it found?
    */
   private boolean find(Node node, Point2D p, boolean isXLevel) {
     
     Node x = node;
     
     //traverse tree according to rules until 
     //either node is found (true) or null (false)
     while (x != null) {
       
       //point found
       if (p.compareTo(x.p) == 0) 
         return true;
       
       if (isXLevel) {
         if (p.x() < x.p.x()) {
           x = x.lb;
         } else {
           x = x.rt;
         }
       } else {
         if (p.y() < x.p.y()) {
           x = x.lb;
         } else {
           x = x.rt;
         }

       }
       
     }
     
     return false;
     
   }
   
   /**
    * Private helper method to insert Point2D nodes
    * @param root
    * @param p
    */
  private Node put(Node parent, Node child, Point2D p, boolean isXLevel, boolean isLeft) {

    // if node passsed is null, then insert new node here
    if (child == null) {
      nodeCount++;
      Node tmp = new Node(p, parent, isXLevel, isLeft);
      l.add(tmp);
      return tmp;
    }
    
    // same point is found, return
    if (child.p.compareTo(p) == 0)
      return null;


    // are we on an even or odd level?
    if (isXLevel) {

      if (p.x() < child.p.x()) {
        child.lb = put(child, child.lb, p, !isXLevel, true);
      } else {
        child.rt = put(child, child.rt, p, !isXLevel, false);
      }

    } else {
      if (p.y() < child.p.y()) {
        child.lb = put(child, child.lb, p, !isXLevel, true);
      } else {
        child.rt = put(child, child.rt, p, !isXLevel, false);
      }

    }
        
    return child;

    //
    //
    //
    // while (search) {
    //
    // //same point is found, return
    // if (checkNode.p.compareTo(p) == 0)
    // return;
    //
    // if (!isYLevel) {
    // {
    // if (p.x() < checkNode.p.x()) {
    // // go left
    // if (checkNode.lb == null) {
    // checkNode.lb = new Node(p);
    // search = false;
    // } else {
    // checkNode = checkNode.lb;
    // }
    // } else {
    // // go right
    // if (checkNode.rt == null) {
    // checkNode.rt = new Node(p);
    // search = false;
    // } else {
    // checkNode = checkNode.rt;
    // }
    // }
    // }
    // isYLevel = true;
    // } else {
    // if (p.y() < checkNode.p.y()) {
    // // go left
    // if (checkNode.lb == null) {
    // checkNode.lb = new Node(p);
    // search = false;
    // } else {
    // checkNode = checkNode.lb;
    // }
    // } else {
    // // go right
    // if (checkNode.rt == null) {
    // checkNode.rt = new Node(p);
    // search = false;
    // } else {
    // checkNode = checkNode.rt;
    // }
    // }
    // isYLevel = false;
    // }
    //
    // }

  }

   /**
    * Does the set contain point p?
    * @param p
    * @return
    */
   public boolean contains(Point2D p) {
     return find(root, p, true);
   }
   
   /**
    * Draw all points to standard draw 
    */
  public void draw() {
    
    //get the set of nodes in the main rectangle
    List<Node> l = new ArrayList<>();
    
    this.findNodesByRange(new RectHV(0.0, 0.0, 1.0, 1.0), root, l);
    
    for (Node n : l) {
      // draw point
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(0.01);
      n.p.draw();

      // now draw separator line for this node
      if (n.isXLevel) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();
        StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
      } else {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius();
        StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
      }
    }
  }
   
   /**
    * All points that are inside the rectangle (or on the boundary) 
    * @param rect
    * @return
    */
   public Iterable<Point2D> range(RectHV rect) {
     
     //list to hold found nodes
     List myList = new ArrayList<>();
     
     //recursively search each node, checking if its
     //containing rectangle intersects with the search rectangle
     findByRange(rect, root, myList);
     
     return myList;
    
   }
   
   /**
    * Helper method to range method
    */
   private void findByRange(RectHV rect, Node n, List<Point2D> l) {
     
     //this node is null?
     if (n == null) 
       return;
     
     //recursively search each node, checking if its
     //containing rectangle intersects with the search rectangle
     if (rect.intersects(n.rect)) {
       //check if point is in range rect
       if (rect.contains(n.p)) {
         l.add(n.p);
       }
       
       //now call the findByRange function on the sub-trees
       findByRange(rect, n.lb, l);
       findByRange(rect, n.rt, l);
       
     }
     
     //no intersection?
     return;
   }
   
   /**
    * Helper method to range method (returns nodes)
    */
   private void findNodesByRange(RectHV rect, Node n, List<Node> l) {
     
     //this node is null?
     if (n == null) 
       return;
     
     //recursively search each node, checking if its
     //containing rectangle intersects with the search rectangle
     if (rect.intersects(n.rect)) {
       //check if point is in range rect
       if (rect.contains(n.p)) {
         l.add(n);
       }
       
       //now call the findByRange function on the sub-trees
       findNodesByRange(rect, n.lb, l);
       findNodesByRange(rect, n.rt, l);
       
     }
     
     //no intersection?
     return;
   }
   
   /**
    * A nearest neighbor in the set to point p; null if the set is empty 
    * @return nearest point
    */
   public Point2D nearest(Point2D p) {
     
     //start at the root
     if (this.isEmpty()) {
       return null;
     } else {
       return findNearest(p, root, null).p;
     }
    
     
   }
   
   private Node findNearest(Point2D p, Node next, Node nearest) {
     
     //termination condition
     if (next == null) {
       return nearest;
     }
     
     if (next == root) {
       //initialize nearest
       nearest = next;
     }
             
     //start at the root
     //assign if closer than current nearest node
     if (next.p.distanceTo(p) < nearest.p.distanceTo(p)) {
       nearest = next;
     }
     
     
     //which side of the divider is the search point on?
     if (next.isXLevel) {
       if (p.x() < next.p.x()) {
         //go left 
            if (next.lb != null && next.rt != null && p.distanceTo(nearest.p) < p.distanceTo(new Point2D(next.rt.rect.xmin(), p.y()))) {
             //no need to go down right tree
            nearest = findNearest(p, next.lb, nearest);
           } else {
             //check both trees
             Node n1 = findNearest(p, next.lb, nearest);
             Node n2 = findNearest(p, next.rt, nearest);
             
             nearest = n1.p.distanceTo(p) <= n2.p.distanceTo(p) ? n1 : n2;             
           }
         } else {
           //go right
           if (next.lb != null && next.rt != null && p.distanceTo(nearest.p) < p.distanceTo(new Point2D(next.lb.rect.xmax(), p.y()))) {
               //no need to go down left tree
              nearest = findNearest(p, next.rt, nearest);
             } else {
               //check both trees
               Node n1 = findNearest(p, next.lb, nearest);
               Node n2 = findNearest(p, next.rt, nearest);
               
               nearest = n1.p.distanceTo(p) <= n2.p.distanceTo(p) ? n1: n2;             
             }

         }
       } else {
         if (p.y() < next.p.y()) {
           //go left 
              if (next.lb != null && next.rt != null && p.distanceTo(nearest.p) < p.distanceTo(new Point2D(p.x(), next.rt.rect.ymin()))) {
               //no need to go down right tree
              nearest = findNearest(p, next.lb, nearest);
             } else {
               //check both trees
               Node n1 = findNearest(p, next.lb, nearest);
               Node n2 = findNearest(p, next.rt, nearest);
               
               nearest = n1.p.distanceTo(p) <= n2.p.distanceTo(p) ? n1 : n2;             
             }
         } else {
              //go right 
              if (next.lb != null && next.rt != null && p.distanceTo(nearest.p) < p.distanceTo(new Point2D(p.x(), next.lb.rect.ymax()))) {
               //no need to go down right tree
              nearest = findNearest(p, next.rt, nearest);
             } else {
               //check both trees
               Node n1 = findNearest(p, next.lb, nearest);
               Node n2 = findNearest(p, next.rt, nearest);
               
               nearest = n1.p.distanceTo(p) <= n2.p.distanceTo(p) ? n1 : n2;             
             }

       }
       
     }
       
    return nearest;
     
   }

   /**
    * Node class 
    * @author jameswilson
    *
    */
   static class Node {
     
     //boolean level orientation flag
     boolean isXLevel;
     
     //the point
     private final Point2D p;      
     
     //the axis-aligned rectangle corresponding to this node
     private final RectHV rect;    
     
     //the left/bottom subtree
     private Node lb;      
     
     //the right/top subtree
     private Node rt;
     
     private Node (Point2D point, boolean isX) {
       p = point;
       
       isXLevel = isX;
      
       //root unit square
       rect = new RectHV(0.0, 0.0, 1.0, 1.0);
     }
     
     private Node (Point2D point, Node parent, boolean isX, boolean isLeft) {
       
       if (point == null || parent == null) 
         throw new IllegalArgumentException();
       
       p = point;
       
       isXLevel = isX;
       
       
       if (isX) {
         if(isLeft) {
           rect = new RectHV (parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax() , parent.p.y());
         } else {
           rect = new RectHV (parent.rect.xmin(), parent.p.y(), parent.rect.xmax(), parent.rect.ymax());
         }
       } else {
         if(isLeft) {
           rect = new RectHV (parent.rect.xmin(), parent.rect.ymin(), parent.p.x() , parent.rect.ymax());
         } else {
           rect = new RectHV (parent.p.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax());
         }
       }  
         
      // rect = new RectHV (xmin, ymin, xmax, ymax);
       //if root, set to full unit sq dimensions
//       if (parent == null) {
//         rect = new RectHV(0.0, 0.0, 1.0, 1.0);
//       } else {
//         if (isLeft) {
//           rect = new RectHV (parent.rect.xmin()) 
//         }
       }
     //}
  }
   
   public static void main(String[] args)     {
     
     Point2D p1 = new Point2D(0.7, 0.2);
     Point2D p2 = new Point2D(0.5, 0.4);
     Point2D p3 = new Point2D(0.2, 0.3);
     Point2D p4 = new Point2D(0.4, 0.7);
     Point2D p5 = new Point2D(0.9, 0.6);

     
     KdTree tree = new KdTree();
    
     tree.insert(p1);
     tree.insert(p2);
     tree.insert(p3);
     tree.insert(p4);
     tree.insert(p5);
     
     boolean found = false;
     found = tree.contains(p3);
     System.out.println("found p1 == " + found);
     found = tree.contains(p5);
     System.out.println("found p5 == " + found);

     tree.draw();
     
     Point2D pTest = tree.nearest(new Point2D(0.2, 0.5));
     pTest = tree.nearest(new Point2D(0.35, 0.8));
     System.out.println("here");
     
   }  
}
