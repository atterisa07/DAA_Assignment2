package benchmark;

import metrics.Metrics;
import structures.DynamicArray;
import structures.MyLinkedList;
import structures.MinHeap;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {
            100, 1000, 10000, 100000
    };

    private static final int RUNS = 5;

    public static void main(String[] args) throws Exception {

        File directory = new File("results");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        PrintWriter writer =
                new PrintWriter("results/results.csv");

        writer.println(
                "workload,variant,structure,n,time_ms,steps,moves,comparisons"
        );

        for (int n : SIZES) {

            System.out.println("Running n = " + n);

            runW1(writer, n);
            runW2(writer, n);
            runW3(writer, n, "head");
            runW3(writer, n, "middle");
            runW4(writer, n);
        }

        writer.close();

        System.out.println();
        System.out.println("Benchmark finished.");
        System.out.println("Results saved to results/results.csv");
    }

    // =========================
    // W1 - RANDOM ACCESS
    // =========================

    private static void runW1(PrintWriter writer, int n) {

        double[] arrayTimes = new double[RUNS];
        double[] listTimes = new double[RUNS];

        long arraySteps = 0;
        long arrayMoves = 0;
        long arrayComparisons = 0;

        long listSteps = 0;
        long listMoves = 0;
        long listComparisons = 0;

        // run = 0 is warm-up
        for (int run = 0; run <= RUNS; run++) {

            Random random = new Random(42);

            DynamicArray array = new DynamicArray();
            MyLinkedList list = new MyLinkedList();

            for (int i = 0; i < n; i++) {
                int value = random.nextInt();

                array.add(value);
                list.add(value);
            }

            int[] indexes = new int[10000];

            for (int i = 0; i < indexes.length; i++) {
                indexes[i] = random.nextInt(n);
            }

            // DynamicArray
            array.resetMetrics();

            long start = System.nanoTime();

            for (int index : indexes) {
                array.get(index);
            }

            long end = System.nanoTime();

            if (run > 0) {
                arrayTimes[run - 1] =
                        (end - start) / 1_000_000.0;

                Metrics m = array.getMetrics();

                arraySteps = m.getSteps();
                arrayMoves = m.getMoves();
                arrayComparisons = m.getComparisons();
            }

            // MyLinkedList
            list.resetMetrics();

            start = System.nanoTime();

            for (int index : indexes) {
                list.get(index);
            }

            end = System.nanoTime();

            if (run > 0) {
                listTimes[run - 1] =
                        (end - start) / 1_000_000.0;

                Metrics m = list.getMetrics();

                listSteps = m.getSteps();
                listMoves = m.getMoves();
                listComparisons = m.getComparisons();
            }
        }

        write(
                writer,
                "W1",
                "-",
                "DynamicArray",
                n,
                median(arrayTimes),
                arraySteps,
                arrayMoves,
                arrayComparisons
        );

        write(
                writer,
                "W1",
                "-",
                "MyLinkedList",
                n,
                median(listTimes),
                listSteps,
                listMoves,
                listComparisons
        );
    }

    // =========================
    // W2 - SEARCH
    // =========================

    private static void runW2(PrintWriter writer, int n) {

        double[] arrayTimes = new double[RUNS];
        double[] listTimes = new double[RUNS];

        long arraySteps = 0;
        long arrayMoves = 0;
        long arrayComparisons = 0;

        long listSteps = 0;
        long listMoves = 0;
        long listComparisons = 0;

        for (int run = 0; run <= RUNS; run++) {

            Random random = new Random(42);

            DynamicArray array = new DynamicArray();
            MyLinkedList list = new MyLinkedList();

            int[] values = new int[n];

            for (int i = 0; i < n; i++) {
                values[i] = random.nextInt();

                array.add(values[i]);
                list.add(values[i]);
            }

            int[] queries = new int[1000];

            // 500 present values
            for (int i = 0; i < 500; i++) {
                queries[i] =
                        values[random.nextInt(n)];
            }

            // 500 absent values
            for (int i = 500; i < 1000; i++) {

                int candidate;

                do {
                    candidate = random.nextInt();
                } while (contains(values, candidate));

                queries[i] = candidate;
            }

            // DynamicArray
            array.resetMetrics();

            long start = System.nanoTime();

            for (int query : queries) {
                array.contains(query);
            }

            long end = System.nanoTime();

            if (run > 0) {
                arrayTimes[run - 1] =
                        (end - start) / 1_000_000.0;

                Metrics m = array.getMetrics();

                arraySteps = m.getSteps();
                arrayMoves = m.getMoves();
                arrayComparisons = m.getComparisons();
            }

            // MyLinkedList
            list.resetMetrics();

            start = System.nanoTime();

            for (int query : queries) {
                list.contains(query);
            }

            end = System.nanoTime();

            if (run > 0) {
                listTimes[run - 1] =
                        (end - start) / 1_000_000.0;

                Metrics m = list.getMetrics();

                listSteps = m.getSteps();
                listMoves = m.getMoves();
                listComparisons = m.getComparisons();
            }
        }

        write(
                writer,
                "W2",
                "-",
                "DynamicArray",
                n,
                median(arrayTimes),
                arraySteps,
                arrayMoves,
                arrayComparisons
        );

        write(
                writer,
                "W2",
                "-",
                "MyLinkedList",
                n,
                median(listTimes),
                listSteps,
                listMoves,
                listComparisons
        );
    }

    // =========================
    // W3 - INSERT AND REMOVE
    // =========================

    private static void runW3(
            PrintWriter writer,
            int n,
            String variant) {

        double[] arrayTimes = new double[RUNS];
        double[] listTimes = new double[RUNS];

        long arraySteps = 0;
        long arrayMoves = 0;
        long arrayComparisons = 0;

        long listSteps = 0;
        long listMoves = 0;
        long listComparisons = 0;

        for (int run = 0; run <= RUNS; run++) {

            DynamicArray array = new DynamicArray();
            MyLinkedList list = new MyLinkedList();

            for (int i = 0; i < n; i++) {
                array.add(i);
                list.add(i);
            }

            // DynamicArray
            array.resetMetrics();

            long start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {

                int index;

                if (variant.equals("head")) {
                    index = 0;
                } else {
                    index = array.size() / 2;
                }

                array.add(index, i);
            }

            for (int i = 0; i < 1000; i++) {

                int index;

                if (variant.equals("head")) {
                    index = 0;
                } else {
                    index = array.size() / 2;
                }

                array.remove(index);
            }

            long end = System.nanoTime();

            if (run > 0) {
                arrayTimes[run - 1] =
                        (end - start) / 1_000_000.0;

                Metrics m = array.getMetrics();

                arraySteps = m.getSteps();
                arrayMoves = m.getMoves();
                arrayComparisons = m.getComparisons();
            }

            // MyLinkedList
            list.resetMetrics();

            start = System.nanoTime();

            for (int i = 0; i < 1000; i++) {

                int index;

                if (variant.equals("head")) {
                    index = 0;
                } else {
                    index = list.size() / 2;
                }

                list.add(index, i);
            }

            for (int i = 0; i < 1000; i++) {

                int index;

                if (variant.equals("head")) {
                    index = 0;
                } else {
                    index = list.size() / 2;
                }

                list.remove(index);
            }

            end = System.nanoTime();

            if (run > 0) {
                listTimes[run - 1] =
                        (end - start) / 1_000_000.0;

                Metrics m = list.getMetrics();

                listSteps = m.getSteps();
                listMoves = m.getMoves();
                listComparisons = m.getComparisons();
            }
        }

        write(
                writer,
                "W3",
                variant,
                "DynamicArray",
                n,
                median(arrayTimes),
                arraySteps,
                arrayMoves,
                arrayComparisons
        );

        write(
                writer,
                "W3",
                variant,
                "MyLinkedList",
                n,
                median(listTimes),
                listSteps,
                listMoves,
                listComparisons
        );
    }

    // =========================
    // W4 - PRIORITY PROCESSING
    // =========================

    private static void runW4(
            PrintWriter writer,
            int n) {

        double[] times = new double[RUNS];

        long steps = 0;
        long moves = 0;
        long comparisons = 0;

        for (int run = 0; run <= RUNS; run++) {

            Random random = new Random(42);

            MinHeap heap = new MinHeap();

            heap.resetMetrics();

            long start = System.nanoTime();

            for (int i = 0; i < n; i++) {
                heap.insert(random.nextInt());
            }

            int previous = Integer.MIN_VALUE;

            while (heap.size() > 0) {

                int current = heap.extractMin();

                if (current < previous) {
                    throw new IllegalStateException(
                            "Heap output is not sorted"
                    );
                }

                previous = current;
            }

            long end = System.nanoTime();

            if (run > 0) {
                times[run - 1] =
                        (end - start) / 1_000_000.0;

                Metrics m = heap.getMetrics();

                steps = m.getSteps();
                moves = m.getMoves();
                comparisons = m.getComparisons();
            }
        }

        write(
                writer,
                "W4",
                "-",
                "MinHeap",
                n,
                median(times),
                steps,
                moves,
                comparisons
        );
    }

    // =========================
    // HELPERS
    // =========================

    private static boolean contains(
            int[] array,
            int value) {

        for (int x : array) {
            if (x == value) {
                return true;
            }
        }

        return false;
    }

    private static double median(double[] values) {

        double[] copy = values.clone();

        Arrays.sort(copy);

        return copy[copy.length / 2];
    }

    private static void write(
            PrintWriter writer,
            String workload,
            String variant,
            String structure,
            int n,
            double time,
            long steps,
            long moves,
            long comparisons) {

        writer.printf(
                Locale.US,
                "%s,%s,%s,%d,%.6f,%d,%d,%d%n",
                workload,
                variant,
                structure,
                n,
                time,
                steps,
                moves,
                comparisons
        );
    }
}