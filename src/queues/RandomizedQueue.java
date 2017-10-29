/******************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:    java RandomizedQueue
 *  Dependencies: 
 *  Data files:   
 *
 *  % java RandomizedQueue
 *  Dequeue item: B 
 *  Sample is: C 
 *  C 
 *  F 
 *  A 
 *  E 
 *  D 
 *  
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] rq;
    private int sz;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        rq = (Item[]) new Object[1];
        sz = 0;
    }
    
    // is the randomized queue empty?
    public boolean isEmpty() {
        return sz == 0;
    }
    
    // return the number of items on the randomized queue
    public int size() {
        return sz;
    }
    
    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        
        if (sz == rq.length) resize(2 * rq.length);
        rq[sz++] = item;
    }
    
    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        
        int deIndex = StdRandom.uniform(sz);
        
        swap(deIndex, sz - 1);
        
        Item item = rq[sz-1];
        rq[--sz] = null;
        
        if (sz > 0 && sz <= rq.length / 4) resize(rq.length / 2);
        
        return item;
    }
    
    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        
        int deIndex = StdRandom.uniform(sz);
        
        return rq[deIndex];
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        
        private int[] order;
        private int index;
        
        public RandomizedQueueIterator() {
            order = StdRandom.permutation(sz);
            index = 0;
        }
        
        public boolean hasNext() {
            return index < sz;
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = rq[order[index++]];
            
            return item;
        }
        
        // Throw a java.lang.UnsupportedOperationException if the client calls 
        // the remove() method in the iterator.
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < sz; ++i) {
            copy[i] = rq[i];
        }
        rq = copy;
    }
    
    private void swap(int i, int j) {
        Item tmp = rq[i];
        rq[i] = rq[j];
        rq[j] = tmp;
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("A");
        rq.enqueue("B");
        rq.enqueue("C");
        rq.enqueue("D");
        rq.enqueue("E");
        rq.enqueue("F");
        
        System.out.println("Dequeue item: " + rq.dequeue());
        
        System.out.println("Sample is: " + rq.sample());
        
        Iterator<String> iter = rq.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}