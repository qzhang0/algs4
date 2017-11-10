import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
    
    private final LineSegment[] segmentsArray;
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null) throw new IllegalArgumentException();
            for (int j = i + 1; j < points.length; ++j) {
                if (points[j] == null) throw new IllegalArgumentException();
                if (points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
            }
        }
        
        int sz = 0;
        LineSegment[] segmentsArrayTmp = new LineSegment[points.length];
        
        for (int i = 0; i < points.length - 3; ++i) {
            for (int j = i + 1; j < points.length - 2; ++j) {
                for (int m = j + 1; m < points.length - 1; ++m) {
                    for (int n = m + 1; n < points.length; ++n) {
                        
                        Point minP, maxP;
                        Point pi = points[i];
                        Point pj = points[j];
                        Point pm = points[m];
                        Point pn = points[n];
                        
                        // do not forget VERTICAL state
                        if ((pi.slopeTo(pj) == Double.POSITIVE_INFINITY &&
                             pj.slopeTo(pm) == Double.POSITIVE_INFINITY &&
                             pm.slopeTo(pn) == Double.POSITIVE_INFINITY) ||
                            (pi.slopeTo(pj) - pj.slopeTo(pm) == 0 &&
                             pj.slopeTo(pm) - pm.slopeTo(pn) == 0)) {
                            
                            Point[] ps = new Point[]{pi, pj, pm, pn};
                            minP = minPoint(ps);
                            maxP = maxPoint(ps);
                            
                            LineSegment ls = new LineSegment(minP, maxP);
                            
                            if (sz == segmentsArrayTmp.length) {
                                LineSegment[] copy = new LineSegment[2 * sz];
                                for (int x = 0; x < sz; ++x) {
                                    copy[x] = segmentsArrayTmp[x];
                                }
                                segmentsArrayTmp = copy;
                            }
                            segmentsArrayTmp[sz++] = ls;
                        }
                    }
                }
            }
        }
        
        LineSegment[] copy = new LineSegment[sz];
        for (int x = 0; x < sz; ++x) {
            copy[x] = segmentsArrayTmp[x];
        }
        segmentsArray = copy;
    }
    
    private Point minPoint(Point[] ps) {
        Point p = ps[0];
        
        for (int i = 1; i < ps.length; ++i) {
            if (p.compareTo(ps[i]) > 0) {
                p = ps[i];
            }
        }
        
        return p;
        
    }
    
    private Point maxPoint(Point[] ps) {
        Point p = ps[0];
        
        for (int i = 1; i < ps.length; ++i) {
            if (p.compareTo(ps[i]) < 0) {
                p = ps[i];
            }
        }
        
        return p;
        
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return segmentsArray.length;
    }
    
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[segmentsArray.length];
        for (int i = 0; i < segmentsArray.length; ++i) {
            result[i] = segmentsArray[i];
        }
        return result;
    }
    
    public static void main(String[] args) {
        
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        // StdOut.println(n);
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            // StdOut.println(points[i]);
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