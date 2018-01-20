# Deques and Randomized Queues

## Summary

Write a generic data type for a deque and a randomized queue. The goal of this assignment is to implement elementary data structures using arrays and linked lists, and to introduce you to generics and iterators. Check the programming assignment [specification](http://coursera.cs.princeton.edu/algs4/assignments/queues.html) | [local](./queues.html) that describes the assignment requirements.

## Testing

[Tests](../../tests/queues)

## Result

[Report](../../reports/queues_output)

## Note

- Be ware of pointer moving to prevent loitering in `removeFirst` and `removeLast` in `Deque`:
```java
Item item = first.item;
Node newFirst = first.next;
first = newFirst;

sz--;
if (!isEmpty()) first.prev = null;
```

VS

```java
Item item = first.item;
first = first.next;
sz--;
if (!isEmpty()) first.prev = null;
else last = null; // here fix the issue!
```
- Use _Knuth Shuffle_ to simulate permutation, use only one `RandomizedQueue` object of maximum size at most `k`
