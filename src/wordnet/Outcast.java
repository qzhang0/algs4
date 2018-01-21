import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;
    
    // constructor takes a WordNet object
    public Outcast(WordNet wordNet) {
        this.wordNet = wordNet;
    }
    
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int d = 0;
        String outcast = nouns[0];
        
        for (int i = 0; i < nouns.length; ++i) {
            int tmp = distance(nouns[i], nouns);
            if (tmp > d) {
                d = tmp;
                outcast = nouns[i];
            }
        }
        
        return outcast;
    }
    
    private int distance(String noun, String[] nouns) {
        int res = 0;

        for (int i = 0; i < nouns.length; ++i) {
            res += wordNet.distance(noun, nouns[i]);
        }
        return res;
    }
    
    // see test client below
    public static void main(String[] args) {
        String[] outcasts = new String[3];
        outcasts[0] = "./wordnet/outcast5.txt";
        outcasts[1] = "./wordnet/outcast8.txt";
        outcasts[2] = "./wordnet/outcast11.txt";
        WordNet wordnet = new WordNet("./wordnet/synsets.txt", "./wordnet/hypernyms.txt");
        Outcast outcast = new Outcast(wordnet);
        for (int t = 0; t < outcasts.length; t++) {
            In in = new In(outcasts[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(outcasts[t] + ": " + outcast.outcast(nouns));
        }
    }
}