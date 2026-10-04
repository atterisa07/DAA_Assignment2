package structures;

import metrics.Metrics;

public class MinHeap {

    private int[] heap;
    private int size;
    private final Metrics metrics;

    public MinHeap() {
        heap = new int[10];
        size = 0;
        metrics = new Metrics();
    }

    public void insert(int x) {
        if (size == heap.length) {
            grow();
        }

        heap[size] = x;
        metrics.move();

        bubbleUp(size);
        size++;
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        metrics.step();
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException();
        }

        int min = heap[0];
        metrics.step();

        heap[0] = heap[size - 1];
        metrics.step();
        metrics.move();

        size--;

        if (size > 0) {
            bubbleDown(0);
        }

        return min;
    }

    private void bubbleUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            metrics.step();
            metrics.step();
            metrics.comparison();

            if (heap[parent] <= heap[index]) {
                break;
            }

            swap(parent, index);
            index = parent;
        }
    }

    private void bubbleDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size) {
                metrics.step();
                metrics.step();
                metrics.comparison();

                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }

            if (right < size) {
                metrics.step();
                metrics.step();
                metrics.comparison();

                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }

            if (smallest == index) {
                break;
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        metrics.step();

        heap[i] = heap[j];
        metrics.step();
        metrics.move();

        heap[j] = temp;
        metrics.move();
    }

    private void grow() {
        int[] newHeap = new int[heap.length * 2];

        for (int i = 0; i < heap.length; i++) {
            newHeap[i] = heap[i];

            metrics.step();
            metrics.move();
        }

        heap = newHeap;
    }

    public int size() {
        return size;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void resetMetrics() {
        metrics.reset();
    }
}