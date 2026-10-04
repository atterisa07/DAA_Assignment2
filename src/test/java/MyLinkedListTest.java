import org.junit.jupiter.api.Test;
import structures.MyLinkedList;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    @Test
    void testAddAndGet() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
        assertEquals(3, list.size());
    }

    @Test
    void testAddAtHead() {
        MyLinkedList list = new MyLinkedList();

        list.add(20);
        list.add(0, 10);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
    }

    @Test
    void testAddAtMiddle() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(30);
        list.add(1, 20);

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void testAddAtLast() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(list.size(), 30);

        assertEquals(30, list.get(2));
    }

    @Test
    void testRemove() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(20, list.remove(1));
        assertEquals(30, list.get(1));
        assertEquals(2, list.size());
    }

    @Test
    void testRemoveFirstAndLast() {
        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(10, list.remove(0));
        assertEquals(30, list.remove(list.size() - 1));
        assertEquals(20, list.get(0));
    }

    @Test
    void testContainsAndDuplicates() {
        MyLinkedList list = new MyLinkedList();

        list.add(5);
        list.add(10);
        list.add(10);

        assertTrue(list.contains(10));
        assertFalse(list.contains(100));
    }

    @Test
    void testSingleElement() {
        MyLinkedList list = new MyLinkedList();

        list.add(42);

        assertEquals(42, list.get(0));
        assertEquals(42, list.remove(0));
        assertEquals(0, list.size());
    }

    @Test
    void testInvalidIndexes() {
        MyLinkedList list = new MyLinkedList();

        assertThrows(IndexOutOfBoundsException.class,
                () -> list.get(0));

        assertThrows(IndexOutOfBoundsException.class,
                () -> list.remove(0));

        assertThrows(IndexOutOfBoundsException.class,
                () -> list.add(-1, 10));

        assertThrows(IndexOutOfBoundsException.class,
                () -> list.add(1, 10));
    }

    @Test
    void testRandomCorrectness() {
        MyLinkedList list = new MyLinkedList();
        ArrayList<Integer> expected = new ArrayList<>();
        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt();

            list.add(value);
            expected.add(value);
        }

        assertEquals(expected.size(), list.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).intValue(), list.get(i));
        }
    }
}