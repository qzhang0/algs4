import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private static final int INFINITY = Integer.MAX_VALUE;
    private final Digraph digraph;
    
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        digraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(digraph, w);
        
        return helper(vPaths, wPaths, true);
    }
    
    private int helper(BreadthFirstDirectedPaths vPaths, 
                       BreadthFirstDirectedPaths wPaths, 
                       boolean isLength) {
        int minLength = INFINITY;
        int ancestor = -1;
        for (int i = 0; i < digraph.V(); ++i) {
            if (vPaths.hasPathTo(i) && wPaths.hasPathTo(i)) {
                int vLen = vPaths.distTo(i);
                int wLen = wPaths.distTo(i);
                if (vLen + wLen < minLength) {
                    minLength = vLen + wLen;
                    ancestor = i;
                }
            }
        }
        if (isLength) {
            return minLength < INFINITY ? minLength : -1;
        } else {
            return ancestor;
        }
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(digraph, w);
        
        return helper(vPaths, wPaths, false);
    }
    
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertex(v);
        validateVertex(w);
        
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(digraph, w);
        
        return helper(vPaths, wPaths, true);
    }
    
    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertex(v);
        validateVertex(w);
        
        BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(digraph, w);
        
        return helper(vPaths, wPaths, false);
    }
    
    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int size = digraph.V();
        if (v < 0 || v >= size)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (size-1));
    }
    
    
    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(Iterable<Integer> v) {
        if (v == null) throw new NullPointerException();
        int size = digraph.V();
        for (int i : v) {
            if (i < 0 || i >= size)
            throw new IllegalArgumentException("vertex " + i + " is not between 0 and " + (size-1));
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("./digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}