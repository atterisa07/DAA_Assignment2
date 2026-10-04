import org.junit.jupiter.api.Test;
import structures.DynamicArray;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DynamicArrayTest {

    @Test
    void testAddAndGet() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
        assertEquals(3, array.size());
    }

    @Test
    void testAddAtIndex() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(30);
        array.add(1, 20);

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void testAddFirstAndLast() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);

        array.add(0, 5);
        array.add(array.size(), 30);

        assertEquals(5, array.get(0));
        assertEquals(30, array.get(array.size() - 1));
    }

    @Test
    void testRemove() {
        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(20, array.remove(1));
        assertEquals(30, array.get(1));
        assertEquals(2, array.size());
    }

    @Test
    void testContainsDuplicates() {
        DynamicArray array = new DynamicArray();

        array.add(5);
        array.add(10);
        array.add(10);

        assertTrue(array.contains(10));
        assertFalse(array.contains(100));
    }

    @Test
    void testSingleElement() {
        DynamicArray array = new DynamicArray();

        array.add(42);

        assertEquals(42, array.get(0));
        assertEquals(42, array.remove(0));
        assertEquals(0, array.size());
    }

    @Test
    void testInvalidIndexes() {
        DynamicArray array = new DynamicArray();

        assertThrows(IndexOutOfBoundsException.class,
                () -> array.get(0));

        assertThrows(IndexOutOfBoundsException.class,
                () -> array.remove(0));

        assertThrows(IndexOutOfBoundsException.class,
                () -> array.add(-1, 10));

        assertThrows(IndexOutOfBoundsException.class,
                () -> array.add(1, 10));
    }

    @Test
    void testGrowth() {
        DynamicArray array = new DynamicArray();

        for (int i = 0; i < 100; i++) {
            array.add(i);
        }

        assertEquals(100, array.size());
        assertEquals(0, array.get(0));
        assertEquals(99, array.get(99));
    }

    @Test
    void testRandomCorrectness() {
        DynamicArray array = new DynamicArray();
        ArrayList<Integer> expected = new ArrayList<>();
        Random random = new Random(42);

        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt();

            array.add(value);
            expected.add(value);
        }

        assertEquals(expected.size(), array.size());

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).intValue(), array.get(i));
        }
    }
}