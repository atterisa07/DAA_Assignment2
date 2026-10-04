package structures;

import metrics.Metrics;

public class DynamicArray {

    private int[] data;
    private int size;
    private final Metrics metrics;

    public DynamicArray() {
        data = new int[10];
        size = 0;
        metrics = new Metrics();
    }

    public void add(int x) {
        if (size == data.length) {
            grow();
        }

        data[size] = x;
        metrics.move();
        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        if (size == data.length) {
            grow();
        }

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];

            metrics.step();
            metrics.move();
        }

        data[index] = x;
        metrics.move();

        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        int removed = data[index];
        metrics.step();

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];

            metrics.step();
            metrics.move();
        }

        size--;

        return removed;
    }

    public int get(int index) {
        checkIndex(index);

        metrics.step();

        return data[index];
    }

    public boolean contains(int x) {

        for (int i = 0; i < size; i++) {

            metrics.step();
            metrics.comparison();

            if (data[i] == x) {
                return true;
            }
        }

        return false;
    }

    public int size() {
        return size;
    }

    private void grow() {
        int[] newData = new int[data.length * 2];

        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];

            metrics.step();
            metrics.move();
        }

        data = newData;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void resetMetrics() {
        metrics.reset();
    }
}