import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class FastCollinearPoints  {
    
    private final LineSegment[] segmentsArray;
    
    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        
        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null) throw new IllegalArgumentException();
        }
        
        int sz = 0;
        LineSegment[] segmentsArrayTmp = new LineSegment[points.length];
        
        Point[] pCopy = new Point[points.length];
        for (int i = 0; i < points.length; ++i) {
            pCopy[i] = points[i];
        }
        for (int i = 0; i < points.length; ++i) {
            Point origin = points[i];
            Arrays.sort(pCopy, origin.slopeOrder());
            
            int startI = 1;
            
            while (startI < pCopy.length) {
                
                if (origin.slopeTo(pCopy[startI]) == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException();
                }
                
                int endI = startI;
                Point minP = origin;
                Point maxP = origin;
                
                while (endI < pCopy.length && 
                     ((origin.slopeTo(pCopy[startI])
                           == Double.POSITIVE_INFINITY &&
                       origin.slopeTo(pCopy[endI])
                           == Double.POSITIVE_INFINITY) ||
                      (origin.slopeTo(pCopy[startI]) - 
                       origin.slopeTo(pCopy[endI]) == 0))) {
                    
                    if (minP.compareTo(pCopy[endI]) > 0) {
                        minP = pCopy[endI];
                    }
                    if (maxP.compareTo(pCopy[endI]) < 0) {
                        maxP = pCopy[endI];
                    }
                    
                    endI++;
                }
                
                if (endI - startI > 2 && 
                    origin.slopeTo(minP) == Double.NEGATIVE_INFINITY) {
                    
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
                
                startI = endI;
            }
        }
        
        LineSegment[] copy = new LineSegment[sz];
        for (int x = 0; x < sz; ++x) {
            copy[x] = segmentsArrayTmp[x];
        }
        segmentsArray = copy;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}