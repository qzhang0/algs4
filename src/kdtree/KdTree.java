import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Queue;
import java.util.LinkedList;
import edu.princeton.cs.algs4.In;

public class KdTree {
    
    // root of KdTree
    private Node root;
    
    private static class Node {
        private Point2D p;
        private final RectHV rect;
        private Node lb;     // left or bottom node
        private Node rt;     // right or top node
        private int size;    // number of nodes in subtree;
        private final boolean xcoord; // true if x-coordinate
        
        public Node(Point2D p, RectHV rect, int size, boolean xcoord) {
            this.p = p;
            this.rect = rect;
            this.size = size;
            this.lb = null;
            this.rt = null;
            this.xcoord = xcoord;
        }
    }
    
    /**
     * construct an empty set of points.
     */
    public KdTree() { }
    
     /**
     * Returns true if this symbol table is empty.
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }
    
    /**
     * Returns the number of node in this symbol table.
     * @return the number of node in this symbol table
     */
    public int size() {
        return size(root);
    }
    
    // return number of node in KdTree rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }
    
    private Node put(Node curr, Point2D p, RectHV rect, boolean xcoord) {
        if (curr == null) return new Node(p, rect, 1, xcoord);
        
        int cmp = 0;
        RectHV newRect;
        if (p.equals(curr.p)) {
            cmp = 0; 
            newRect = null;
        } else {
            if (curr.xcoord) {
                if (p.x() < curr.p.x()) {
                    double xmin = curr.rect.xmin();
                    double ymin = curr.rect.ymin();
                    double xmax = curr.p.x();
                    double ymax = curr.rect.ymax();
                    newRect = new RectHV(xmin, ymin, xmax, ymax);
                    cmp = -1;
                } else {
                    double xmin = curr.p.x();
                    double ymin = curr.rect.ymin();
                    double xmax = curr.rect.xmax();
                    double ymax = curr.rect.ymax();
                    newRect = new RectHV(xmin, ymin, xmax, ymax);
                    cmp = 1;
                }
            } else {
                if (p.y() < curr.p.y()) {
                    double xmin = curr.rect.xmin();
                    double ymin = curr.rect.ymin();
                    double xmax = curr.rect.xmax();
                    double ymax = curr.p.y();
                    newRect = new RectHV(xmin, ymin, xmax, ymax);
                    cmp = -1;
                } else {
                    double xmin = curr.rect.xmin();
                    double ymin = curr.p.y();
                    double xmax = curr.rect.xmax();
                    double ymax = curr.rect.ymax();
                    newRect = new RectHV(xmin, ymin, xmax, ymax);
                    cmp = 1;
                }
            }
        }
        
        if (cmp < 0) curr.lb = put(curr.lb, p, newRect, !curr.xcoord);
        else if (cmp > 0) curr.rt = put(curr.rt, p, newRect, !curr.xcoord);
        else curr.p = p;
        
        curr.size = 1 + size(curr.lb) + size(curr.rt);
        return curr;
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("calls insert() with a null");
        }
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        root = put(root, p, rect, true);
    }
    
    private Node get(Node curr, Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("calls get() with null key");
        }
        if (curr == null) return null;
        if (p.equals(curr.p)) return curr;
        
        int cmp = 0;
        if (curr.xcoord) {
            if (p.x() < curr.p.x()) {
                cmp = -1;
            } else {
                cmp = 1;
            }
        } else {
            if (p.y() < curr.p.y()) {
                cmp = -1;
            } else {
                cmp = 1;
            }
        }
        
        if (cmp < 0) return get(curr.lb, p);
        else if (cmp > 0) return get(curr.rt, p);
        else throw new IllegalArgumentException("get() cmp set error!");
    }
    
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("calls contains() with null");
        }
        return get(root, p) != null;
    }
    
    // draw all points to standard draw 
    public void draw() {
        if (root == null) return;
        
        StdDraw.clear();
        
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            Point2D p = node.p;
            p.draw();
            
            if (node.xcoord) {
                StdDraw.setPenColor(StdDraw.RED);
                // StdDraw.setPenRadius(0.001);
                StdDraw.line(p.x(), node.rect.ymin(), p.x(), node.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                // StdDraw.setPenRadius(0.001);
                StdDraw.line(node.rect.xmin(), p.y(), node.rect.xmax(), p.y());
            }
            
            if (node.lb != null) queue.add(node.lb);
            if (node.rt != null) queue.add(node.rt);
        }
    }
    
    private void rangeSearch(Queue<Point2D> rangeQueue, RectHV queryRect, Node curr) {
        if (curr == null) return;
        
        if (queryRect.intersects(curr.rect)) {
            if (queryRect.contains(curr.p)) {
                rangeQueue.add(curr.p);
            }
            rangeSearch(rangeQueue, queryRect, curr.lb);
            rangeSearch(rangeQueue, queryRect, curr.rt);
        }
    }
    
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("calls range() with null key");
        }
        Queue<Point2D> rangeQueue = new LinkedList<>();
        rangeSearch(rangeQueue, rect, root);
        
        Iterable<Point2D> results = rangeQueue;
        return results;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Point2D champion = null;
        double championDistanceSquare = Double.POSITIVE_INFINITY;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node x = queue.poll();
            if (x == null || x.rect.distanceSquaredTo(p) > championDistanceSquare) {
                continue;
            }
            double ds = p.distanceSquaredTo(x.p);
            if (ds < championDistanceSquare) {
                champion = x.p;
                championDistanceSquare = ds;
            }
            
            if (x.xcoord) {
                if (p.x() < x.p.x()) {
                    queue.add(x.lb);
                    queue.add(x.rt);
                } else {
                    queue.add(x.rt);
                    queue.add(x.lb);
                }
            } else {
                if (p.y() < x.p.y()) {
                    queue.add(x.lb);
                    queue.add(x.rt);
                } else {
                    queue.add(x.rt);
                    queue.add(x.lb);
                }
            }
        }
        return champion;
    }
    
    // unit testing of the methods (optional)
    public static void main(String[] args) {
        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

        // process nearest neighbor queries
        StdDraw.enableDoubleBuffering();
        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            kdtree.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            StdDraw.setPenRadius(0.02);

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            kdtree.nearest(query).draw();
            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}