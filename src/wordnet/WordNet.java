import java.util.HashMap;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;

public class WordNet {
    private final HashMap<Integer, Bag<String>> idMap;
    private final HashMap<String, Bag<Integer>> wordMap;
    private final Digraph G;
    private final SAP sap;
    private int sizeV;
    
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        idMap = new HashMap<Integer, Bag<String>>();
        wordMap = new HashMap<String, Bag<Integer>>();
        readSynsets(synsets);
        
        G = new Digraph(sizeV);
        readHypernyms(hypernyms);
        sap = new SAP(G);
        
        checkCycle();
        checkRoot();
    }
    
    private void readSynsets(String synsets) {
        In in = new In(synsets);
        sizeV = 0;
        
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            int id = Integer.parseInt(tokens[0]);
            String[] nouns = tokens[1].split(" ");
            Bag<String> words = new Bag<String>();
            
            for (int i = 0; i < nouns.length; ++i) {
                words.add(nouns[i]);
                Bag<Integer> ids = new Bag<Integer>();
                if (wordMap.containsKey(nouns[i]))
                    ids = wordMap.get(nouns[i]);
                ids.add(id);
                wordMap.put(nouns[i], ids);
            }
            
            idMap.put(id, words);
            
            if (id > sizeV) sizeV = id;
        }
        sizeV = sizeV + 1;
    }
    
    private void readHypernyms(String hypernyms) {
        In in = new In(hypernyms);

        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] token = line.split(",");
            int v = Integer.parseInt(token[0]);
            for (int i = 1; i < token.length; i++) {
                int w = Integer.parseInt(token[i]);
                G.addEdge(v, w);
            }
        }
    }
    
    private void checkCycle() {
        DirectedCycle cycle = new DirectedCycle(this.G);
        if (cycle.hasCycle()) throw new IllegalArgumentException("Digraph has cycle!");
    }
    
    private void checkRoot() {
        Digraph Gr = this.G.reverse();
        for (int v = 0; v < sizeV; v++) {
            if (this.G.outdegree(v) == 0) {
                int count = 1;
                boolean[] marked = new boolean[sizeV];
                marked[v] = true;
                Queue<Integer> visited = new Queue<Integer>();
                visited.enqueue(v);
                while (!visited.isEmpty()) {
                    int w = visited.dequeue();
                    for (int i : Gr.adj(w)) {
                        if (!marked[i]) {
                            marked[i] = true;
                            count++;
                            visited.enqueue(i);
                        }
                    }
                }
                if (count != sizeV) throw new IllegalArgumentException("Digraph not rooted!");
            }
        }
    }
    
    private String concat(Bag<String> nouns) {
        StringBuilder sb = new StringBuilder();
        for (String noun : nouns) {
            sb.append(noun);
        }
        return sb.toString();
    }

   // returns all WordNet nouns
    public Iterable<String> nouns() {
        return wordMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException();
        return wordMap.containsKey(word);
    }
    
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        Iterable<Integer> a = wordMap.get(nounA);
        Iterable<Integer> b = wordMap.get(nounB);

        return sap.length(a, b);
    }
    
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        Iterable<Integer> a = wordMap.get(nounA);
        Iterable<Integer> b = wordMap.get(nounB);
        
        int ancestor = sap.ancestor(a, b);
        Bag<String> res = idMap.get(ancestor);
        
        return concat(res);
    }
    
    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("./wordnet/synsets8.txt", "./wordnet/hypernyms8ManyAncestors.txt");
        System.out.println(wordNet.distance("a", "c"));
        System.out.println(wordNet.sap("a", "c"));
    }
}