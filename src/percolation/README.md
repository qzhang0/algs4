## Summary

A program to estimate the value of the _percolation threshold_ via Monte Carlo simulation. Check the programming assignment [specification](http://coursera.cs.princeton.edu/algs4/assignments/percolation.html) that describes the assignment requirements.

## Usage

Use [InteractivePercolationVisualizer.java](../../tests/percolation/InteractivePercolationVisualizer.java) and [PercolationVisualizer.java](../../tests/percolation/PercolationVisualizer.java) in the [tests](../../tests/percolation)

## Result

[Report](../../reports/percolation_output)

## Note

- Use `enum` to implement constant in Java
- Do not call `stddev()` and `mean()` multiple times(once in the main method itself, and the other times for calculating the `confidenceHi` and `confidenceLo`). Store the value in a variable once all the trials finish, and just use the variable instead.
- CheckStyle
  - The instance variable must start with a lowercase letter and use camelCase.
  - The constant '1.96' appears more than once. Define a constant variable (such as 'CONFIDENCE_95') to hold the constant '1.96'
