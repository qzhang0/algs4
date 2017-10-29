/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque
 *  Dependencies: 
 *  Data files:   
 *
 *  % java Deque
 *  After addLast, size is: 1 
 *  After addFirst, size is: 1 
 *  After addFirst, size is: 2 
 *  After addLast, size is: 3 
 *  2 
 *  4 
 *  
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int sz;
    
    private class Node {
        Item item;
        Node next;
        Node prev;
    }
    
    // construct an empty deque
    public Deque() {
        first = null;
        last  = null;
        sz    = 0;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return sz == 0;
    }
    
    // return the number of items on the deque
    public int size() {
        return sz;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
        
        if (item == null) throw new IllegalArgumentException();
        
        Node newFirst = new Node();
        newFirst.item = item;
        
        if (isEmpty()) {
            last = newFirst;
        } else {
            newFirst.next = first;
            newFirst.prev = null;
            
            first.prev = newFirst;
        }

        first = newFirst;
        sz++;
    }
    
    // add the item to the end
    public void addLast(Item item) {
        
        if (item == null) throw new IllegalArgumentException();
        
        Node newLast = new Node();
        newLast.item = item;
        
        if (isEmpty()) {
            first = newLast;
        } else {
            newLast.next = null;
            newLast.prev = last;
            
            last.next = newLast;
        }

        last = newLast;
        sz++;
    }
    
    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        
        Item item = first.item;
        first = first.next;
        sz--;
        if (!isEmpty()) first.prev = null;
        else last = null;
        
        return item;
    }
    
    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        
        Item item = last.item;
        last = last.prev;
        sz--;
        
        if (!isEmpty()) last.next = null;
        else first = null;
        
        return item;
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        
        private Node current;
        
        public DequeIterator() {
            if (!isEmpty()) {
                current = first;
            }
        }
        
        public boolean hasNext() {
            return current != null;
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
        
        // Throw a java.lang.UnsupportedOperationException if the client calls 
        // the remove() method in the iterator.
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        
        d.addLast("1");
        System.out.println("After addLast, size is: " + d.size());
        
        d.removeLast();
        
        Iterator<String> iter1 = d.iterator();
        while (iter1.hasNext()) {
            System.out.println(iter1.next());
        }
        
        d.addFirst("2");
        System.out.println("After addFirst, size is: " + d.size());
        
        d.addFirst("3");
        System.out.println("After addFirst, size is: " + d.size());
        
        d.addLast("4");
        System.out.println("After addLast, size is: " + d.size());
        
        d.removeFirst();
        
        Iterator<String> iter2 = d.iterator();
        while (iter2.hasNext()) {
            System.out.println(iter2.next());
        }
    }

}