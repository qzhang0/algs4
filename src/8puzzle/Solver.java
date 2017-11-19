import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Deque;
import java.util.LinkedList;

public class Solver {
    private final boolean solved;
    private Deque<Board> solutions;
    // private final String priorityFunction;
    
    private static class SearchNode implements Comparable<SearchNode> {
        private final int priority;
        private final int moves;
        private final Board board;
        private final SearchNode prev;
        
        public SearchNode(Board board, SearchNode prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
            
            this.priority = moves + board.manhattan();
        }
        
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        // priorityFunction = "manhattan";
        
        Board initialTwin = initial.twin();
        
        SearchNode node = new SearchNode(initial, null, 0);
        SearchNode twinNode = 
            new SearchNode(initialTwin, null, 0);
        
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> twinPq = new MinPQ<>();
        
        pq.insert(node);
        twinPq.insert(twinNode);
        
        while (true) {
            // curr
            node = pq.delMin();
            if (node.board.isGoal()) {
                solutions = new LinkedList<>();
                while (node.prev != null) {
                    solutions.addFirst(node.board);
                    node = node.prev;
                }
                solutions.addFirst(node.board);
                solved = true;
                break;
            } else {
                Iterable<Board> neighbors = node.board.neighbors();
                for (Board neighbor : neighbors) {
                    SearchNode prevNode = node.prev;
                    if (prevNode != null 
                            && neighbor.equals(node.prev.board)) {
                        continue;
                    }
                    SearchNode newNode = 
                        new SearchNode(neighbor, node, node.moves + 1);
                    pq.insert(newNode);  
                }
            }
            
            // twin
            twinNode = twinPq.delMin();
            if (twinNode.board.isGoal()) {
                solutions = null;
                solved = false;
                break;
            } else {
                Iterable<Board> twinNeighbors = twinNode.board.neighbors();
                for (Board neighbor : twinNeighbors) {
                    SearchNode prevNode = twinNode.prev;
                    if (prevNode != null 
                            && neighbor.equals(twinNode.prev.board)) {
                        continue;
                    }
                    SearchNode newNode = 
                        new SearchNode(neighbor, twinNode, twinNode.moves + 1);
                    twinPq.insert(newNode);  
                }
            }
        }
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return solved;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable() && solutions != null) {
            return solutions.size() - 1;
        } else {
            return -1;
        }
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solutions;
    }
    
    // solve a slider puzzle
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}