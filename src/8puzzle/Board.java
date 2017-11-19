import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.LinkedList;
import java.util.Queue;

public class Board {
    
    private final int dims;
    private final int[][] blocks;
    private final int hammingValue;
    private final int manhattanValue;
    private Board twinBoard;
    
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new IllegalArgumentException();
        }
        if (blocks.length == 0 || blocks[0].length == 0) {
            throw new IllegalArgumentException();
        }
        if (blocks.length != blocks[0].length) {
            throw new IllegalArgumentException();
        }
        
        dims = blocks.length;
        this.blocks = new int[dims][dims];
        for (int i = 0; i < dims; ++i) {
            for (int j = 0; j < dims; ++j) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
        
        int hv = 0;
        for (int i = 0; i < dims; ++i) {
            for (int j = 0; j < dims; ++j) {
                if (blocks[i][j] != 0 && blocks[i][j] != i * dims + j + 1) {
                    ++hv;
                }
            }
        }
        hammingValue = hv;
        
        int mv = 0;
        for (int i = 0; i < dims; ++i) {
            for (int j = 0; j < dims; ++j) {
                if (blocks[i][j] != 0) {
                    int index = blocks[i][j] - 1;
                    mv += Math.abs(index / dims - i) + Math.abs(index % dims - j);
                }
            }
        }
        manhattanValue = mv;
    }
    
    // board dimension n
    public int dimension() {
        return dims;
    }
    
    // number of blocks out of place
    public int hamming() {
        return hammingValue;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhattanValue;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0 && manhattan() == 0;
        // return manhattan() == 0;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        if (twinBoard == null) {
            int[][] twinBlocks = new int[dims][dims];
            for (int i = 0; i < dims; ++i) {
                for (int j = 0; j < dims; ++j) {
                    twinBlocks[i][j] = blocks[i][j];
                }
            }
            
            int index = StdRandom.uniform(dims * dims);
            while (twinBlocks[index / dims][index % dims] == 0) {
                ++index;
                if (index >= dims * dims) index = 0;
            }
            int nextIndex = index + 1;
            if (nextIndex >= dims * dims) nextIndex = 0;
            while (twinBlocks[nextIndex / dims][nextIndex % dims] == 0) {
                ++nextIndex;
                if (nextIndex >= dims * dims) nextIndex = 0;
            }
            
            swap(twinBlocks, index, nextIndex);
            twinBoard = new Board(twinBlocks);
        }
        return twinBoard;
    }
    
    private void swap(int[][] a, int i, int j) {
        int tmp = a[i / dims][i % dims];
        a[i / dims][i % dims] = a[j / dims][j % dims];
        a[j / dims][j % dims] = tmp;
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        
        if (((Board) y).blocks.length != dims 
                || ((Board) y).blocks[0].length != dims) {
            return false;
        }
        // assert y.getClass().equals(Board.class);
        for (int i = 0; i < dims; ++i) {
            for (int j = 0; j < dims; ++j) {
                if (blocks[i][j] != ((Board) y).blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> boards = new LinkedList<>();
        
        int zeroIndex = 0;
        
        for (int i = 0; i < dims; ++i) {
            for (int j = 0; j < dims; ++j) {
                if (blocks[i][j] == 0) {
                    zeroIndex = i * dims + j;
                }
            }
        }
        
        int[][] newBlocks = new int[dims][dims];
        // up
        if (zeroIndex - dims >= 0) {
            for (int i = 0; i < dims; ++i) {
                for (int j = 0; j < dims; ++j) {
                    newBlocks[i][j] = blocks[i][j];
                }
            }
            swap(newBlocks, zeroIndex, zeroIndex - dims);
            boards.add(new Board(newBlocks));
        }
        // down
        if (zeroIndex + dims < dims * dims) {
            for (int i = 0; i < dims; ++i) {
                for (int j = 0; j < dims; ++j) {
                    newBlocks[i][j] = blocks[i][j];
                }
            }
            swap(newBlocks, zeroIndex, zeroIndex + dims);
            boards.add(new Board(newBlocks));
        }
        // left
        if ((zeroIndex - 1) >= 0 
                && ((zeroIndex - 1) / dims == (zeroIndex / dims))) {
            for (int i = 0; i < dims; ++i) {
                for (int j = 0; j < dims; ++j) {
                    newBlocks[i][j] = blocks[i][j];
                }
            }
            swap(newBlocks, zeroIndex, zeroIndex - 1);
            boards.add(new Board(newBlocks));
        }    
        // right
        if ((zeroIndex + 1) / dims == (zeroIndex / dims)) {
            for (int i = 0; i < dims; ++i) {
                for (int j = 0; j < dims; ++j) {
                    newBlocks[i][j] = blocks[i][j];
                }
            }
            swap(newBlocks, zeroIndex, zeroIndex + 1);
            boards.add(new Board(newBlocks));
        }
        return boards;
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dims);
        sb.append(System.lineSeparator());
        for (int i = 0; i < dims; ++i) {
            for (int j = 0; j < dims; ++j) {
                sb.append(" " + blocks[i][j] + " ");
            }
            sb.append(System.lineSeparator());
        }
        
        return sb.toString();
    }
    
    // unit tests (not graded)
    public static void main(String[] args) {
        // create initial board from file
        // StdOut.println((-1)/3);
        // StdOut.println(0/3);
        
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        StdOut.println(initial);
        StdOut.println("Hamming: " + initial.hamming());
        StdOut.println("Manhattan: " + initial.manhattan());
        
        Iterable<Board> neighbors = initial.neighbors();
        neighbors.forEach(StdOut::println);
        
        StdOut.println("Next Example: ");
        In in2 = new In(args[1]);
        int n2 = in2.readInt();
        int[][] blocks2 = new int[n2][n2];
        for (int i = 0; i < n2; i++)
            for (int j = 0; j < n2; j++)
            blocks2[i][j] = in2.readInt();
        Board initial2 = new Board(blocks2);
        StdOut.println(initial2);
        StdOut.println("Hamming: " + initial2.hamming());
        StdOut.println("Manhattan: " + initial2.manhattan());
        
        StdOut.println("Twin Board is: ");
        StdOut.println(initial2.twin());
        
        if (initial.equals(initial2)) {
            StdOut.println("Equals");
        } else {
            StdOut.println("Not Equals");
        }
    }
}