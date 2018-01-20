# Pattern Recognition

## Summary

Write a program to recognize line patterns in a given set of points. Check the programming assignment [specification](http://coursera.cs.princeton.edu/algs4/assignments/collinear.html) | [local](./collinear.html) that describes the assignment requirements.

## Testing

[Tests](../../tests/collinear)

## Result

[Report](../../reports/collinear_output)

## Note

- Use `points[i].slopeTo(points[j]) == Double.NEGATIVE_INFINITY` to determine point i and point j are equal.

- On FastCollinearPoints.
	- To avoid repeated segments, during each inner loop, create a "max point" and a "min point" to keep track of th maximum point and the minimum point. Only add line segment if "origin" equals "min point".

- Use resize function to dynamic alloc tmp array

- Do not forget corner cases on vertical lines

- Throw a `java.lang.IllegalArgumentException` if the argument to the constructor is `null`, if any point in the array is `null`, or if the argument to the constructor contains a __repeated__ point.
