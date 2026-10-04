# DAA Assignment 2

## Dynamic Array, Linked List and Min-Heap

Student: Talshyn Otegen

Course: Design and Analysis of Algorithms


# Project Description

This project implements and analyzes three data structures from scratch:

DynamicArray

MyLinkedList

MinHeap

The implementations use primitive int values.

Java ArrayList, LinkedList, and PriorityQueue are not used for the main data structures.


# Project Structure

src/main/java/structures

Contains DynamicArray, MyLinkedList, and MinHeap.


src/main/java/metrics

Contains the Metrics class for counting steps, moves, and comparisons.


src/main/java/benchmark

Contains Benchmark.java.


src/test/java

Contains JUnit 5 tests.


results/results.csv

Contains benchmark results.


results/plots

Contains PNG charts generated from the benchmark results.


REPORT.md

Contains complexity analysis, loop invariant proofs, experimental results, discussion, and conclusion.


# How to Run Tests

Open the project in IntelliJ IDEA.

Open Maven.

Select Lifecycle.

Run:

test

All JUnit tests should pass.


# How to Run Benchmark

Open:

src/main/java/benchmark/Benchmark.java

Run the main method.

The benchmark tests the following input sizes:

100

1000

10000

100000

The benchmark uses Random(42).

One warm-up run is discarded.

Five measured runs are performed.

The median execution time is saved.


# Workloads

W1 – Random Access

10,000 random get(index) operations.


W2 – Search

1,000 contains(x) operations.

Half of the values are present.

Half of the values are absent.


W3 – Insert and Remove

1,000 insertions and 1,000 removals.

Two variants are tested:

head

middle


W4 – Priority Processing

Random values are inserted into MinHeap.

All values are extracted using extractMin().

The output is verified to be in nondecreasing order.


# Results

Benchmark results are stored in:

results/results.csv


Charts are stored in:

results/plots


# Metrics

The project measures:

steps

moves

comparisons


# Testing

JUnit 5 is used for correctness testing.

The tests cover normal operations and edge cases.

They include empty structures, single elements, duplicates, invalid indexes, and heap extraction order.