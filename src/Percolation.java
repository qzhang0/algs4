import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private boolean isPercolated; // true if the whole system percolates 
    private boolean[] grid; // percolation grid
    private final int gridSize;
    private boolean[] toTop;
    private boolean[] toBottom;
    private int openSitesCount;
    private final WeightedQuickUnionUF uf;
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be larger than 0");
        }
        isPercolated   = false;
        grid           = new boolean[n * n];
        gridSize       = n;
        toTop          = new boolean[n * n];
        toBottom       = new boolean[n * n];
        openSitesCount = 0;
        uf                = new WeightedQuickUnionUF(n * n);
        
        for (int i = 0; i < n * n; ++i) {
            grid[i]     = false;
            toTop[i]    = false;
            toBottom[i] = false;
        }
    }
    
    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRowCol(row, col);
        
        if (!isOpen(row, col)) {
            int index      = convertTo1D(row, col);
            grid[index]    = true;
            boolean top    = false;
            boolean bottom = false;
            
            openSitesCount++;
            
            if (row == 1) {
                top = true;
            }
            if (row == gridSize) {
                bottom = true;
            }
            
            // up
            if (row > 1 && grid[index - gridSize]) {
                if (toTop[uf.find(index - gridSize)] || toTop[uf.find(index)]) {
                    top = true;
                }
                if (toBottom[uf.find(index - gridSize)] || toBottom[uf.find(index)]) {
                    bottom = true;
                }
                uf.union(index, index - gridSize);
            }
            
            // down
            if (row < gridSize && grid[index + gridSize]) {
                if (toTop[uf.find(index + gridSize)] || toTop[uf.find(index)]) {
                    top = true;
                }
                if (toBottom[uf.find(index + gridSize)] || toBottom[uf.find(index)]) {
                    bottom = true;
                }
                uf.union(index, index + gridSize);
            }
            
            // left
            if (col > 1 && grid[index - 1]) {
                if (toTop[uf.find(index - 1)] || toTop[uf.find(index)]) {
                    top = true;
                }
                if (toBottom[uf.find(index - 1)] || toBottom[uf.find(index)]) {
                    bottom = true;
                }
                uf.union(index, index - 1);
            }
            
            // right
            if (col < gridSize && grid[index + 1]) {
                if (toTop[uf.find(index + 1)] || toTop[uf.find(index)]) {
                    top = true;
                }
                if (toBottom[uf.find(index + 1)] || toBottom[uf.find(index)]) {
                    bottom = true;
                }
                uf.union(index, index + 1);
            }
            
            toTop[uf.find(index)]    = top;
            toBottom[uf.find(index)] = bottom;
            
            if (toTop[uf.find(index)] && toBottom[uf.find(index)]) {
                isPercolated = true;
            }
        }
    }
    
    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);
        
        return grid[convertTo1D(row, col)];
    }
    
    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRowCol(row, col);
        
        return toTop[uf.find(convertTo1D(row, col))];
    }
    
    // number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }
    
    // does the system percolate?
    public boolean percolates() {
        return isPercolated;
    }
    
    private void validateRowCol(int row, int col) {
        if (row < 1 || col < 1 || row > gridSize || col > gridSize) {
            throw new IllegalArgumentException("row or col is illegal");
        }
    }
    
    private int convertTo1D(int row, int col) {
        validateRowCol(row, col);
        
        return col - 1 + (row - 1) * gridSize;
    }
    
    public static void main(String[] args) {
        Percolation p = new Percolation(3);
        if (p.isOpen(2, 2)) {
            System.out.println("(2, 2) is open");
        } else {
            System.out.println("(2, 2) is not open");
        }
        
        if (p.percolates()) {
            System.out.println("System percolates!");
        } else {
            System.out.println("System does not percolate!");
        }
        
        p.open(2, 2);
        System.out.println("Opening (2, 2)");
        
        if (p.percolates()) {
            System.out.println("System percolates!");
        } else {
            System.out.println("System does not percolate!");
        }
        
        if (p.isOpen(2, 2)) {
            System.out.println("(2, 2) is open");
        } else {
            System.out.println("(2, 2) is not open");
        }
        
        p.open(1, 3);
        System.out.println("Opening (1, 3)");
        
        if (p.percolates()) {
            System.out.println("System percolates!");
        } else {
            System.out.println("System does not percolate!");
        }
        
        p.open(2, 3);
        System.out.println("Opening (2, 3)");
        
        if (p.percolates()) {
            System.out.println("System percolates!");
        } else {
            System.out.println("System does not percolate!");
        }
        
        p.open(3, 3);
        System.out.println("Opening (2, 3)");
        
        if (p.percolates()) {
            System.out.println("System percolates!");
        } else {
            System.out.println("System does not percolate!");
        }
        
        System.out.println("There are " + p.numberOfOpenSites() + " opening sites now");
    }
}