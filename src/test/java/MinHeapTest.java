import org.junit.jupiter.api.Test;
import structures.MinHeap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MinHeapTest {

    @Test
    void testPeekMin() {
        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);

        assertEquals(10, heap.peekMin());
    }

    @Test
    void testExtractMin() {
        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);

        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
        assertEquals(30, heap.extractMin());
    }

    @Test
    void testDuplicates() {
        MinHeap heap = new MinHeap();

        heap.insert(5);
        heap.insert(5);
        heap.insert(3);

        assertEquals(3, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(5, heap.extractMin());
    }

    @Test
    void testSingleElement() {
        MinHeap heap = new MinHeap();

        heap.insert(42);

        assertEquals(42, heap.peekMin());
        assertEquals(42, heap.extractMin());
        assertEquals(0, heap.size());
    }

    @Test
    void testEmptyPeek() {
        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::peekMin
        );
    }

    @Test
    void testEmptyExtract() {
        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::extractMin
        );
    }

    @Test
    void testSortedOutput() {
        MinHeap heap = new MinHeap();

        int[] values = {7, 2, 9, 1, 5, 3, 8};

        for (int value : values) {
            heap.insert(value);
        }

        int previous = Integer.MIN_VALUE;

        while (heap.size() > 0) {
            int current = heap.extractMin();

            assertTrue(current >= previous);

            previous = current;
        }
    }

    @Test
    void testMinimumAfterEveryInsert() {
        MinHeap heap = new MinHeap();

        int[] values = {20, 15, 30, 5, 10, 2, 40};
        int minimum = Integer.MAX_VALUE;

        for (int value : values) {
            heap.insert(value);

            minimum = Math.min(minimum, value);

            assertEquals(minimum, heap.peekMin());
        }
    }

    @Test
    void testRandomCorrectness() {
        MinHeap heap = new MinHeap();
        ArrayList<Integer> expected = new ArrayList<>();
        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt();

            heap.insert(value);
            expected.add(value);
        }

        Collections.sort(expected);

        for (int value : expected) {
            assertEquals(value, heap.extractMin());
        }

        assertEquals(0, heap.size());
    }
}