import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException();
        }
        
        int k = Integer.parseInt(args[0]);
        
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        
        int inIndex = 0;
        
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (inIndex < k) {
                rq.enqueue(s);
            }
            
            // just like the suffle
            if (StdRandom.uniform(inIndex + 1) < k && inIndex >= k) {
                rq.dequeue();
                rq.enqueue(s);
            }
            
            inIndex++;
        }
        
        for (int i = 0; i < k; ++i) {
            StdOut.println(rq.dequeue());
        }
    }
}