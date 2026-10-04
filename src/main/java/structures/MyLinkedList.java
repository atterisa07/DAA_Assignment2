package structures;

import metrics.Metrics;

public class MyLinkedList {

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private int size;
    private final Metrics metrics;

    public MyLinkedList() {
        head = null;
        size = 0;
        metrics = new Metrics();
    }

    public void add(int x) {
        Node newNode = new Node(x);

        if (head == null) {
            head = newNode;
            metrics.move();
        } else {
            Node current = head;

            while (current.next != null) {
                current = current.next;
                metrics.step();
            }

            current.next = newNode;
            metrics.move();
        }

        size++;
    }

    public void add(int index, int x) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node newNode = new Node(x);

        if (index == 0) {
            newNode.next = head;
            metrics.move();

            head = newNode;
            metrics.move();
        } else {
            Node current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                metrics.step();
            }

            newNode.next = current.next;
            metrics.move();

            current.next = newNode;
            metrics.move();
        }

        size++;
    }

    public int remove(int index) {
        checkIndex(index);

        int removed;

        if (index == 0) {
            removed = head.value;
            metrics.step();

            head = head.next;
            metrics.move();
        } else {
            Node current = head;

            for (int i = 0; i < index - 1; i++) {
                current = current.next;
                metrics.step();
            }

            removed = current.next.value;
            metrics.step();

            current.next = current.next.next;
            metrics.move();
        }

        size--;
        return removed;
    }

    public int get(int index) {
        checkIndex(index);

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
            metrics.step();
        }

        metrics.step();
        return current.value;
    }

    public boolean contains(int x) {
        Node current = head;

        while (current != null) {
            metrics.step();
            metrics.comparison();

            if (current.value == x) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    public int size() {
        return size;
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