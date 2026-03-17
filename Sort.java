import java.util.Arrays;
import java.util.Random;

/**
 * Assignment 1: Empirical Observation of Time Complexity
 * This program implements Linear Scan, Merge Sort, and Insertion Sort,
 * and records their running time as the input size increases.
 */
public class Sort {

    // ==========================================
    // 1. Linear Scan (Array Sum) -> Expected O(n)
    // ==========================================
    public static long linearScanSum(int[] arr) {
        long sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }

    // ==========================================
    // 2. Insertion Sort -> Expected O(n^2)
    // ==========================================
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            // Move elements of arr[0..i-1] that are greater than key
            // to one position ahead of their current position
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    // ==========================================
    // 3. Merge Sort -> Expected O(n log n)
    // ==========================================
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }
        while (i < n1) {
            arr[k++] = L[i++];
        }
        while (j < n2) {
            arr[k++] = R[j++];
        }
    }

    // ==========================================
    // Test Framework
    // ==========================================
    public static void main(String[] args) {
        // Array sizes to test. Doubling the size helps clearly see the growth rate.
        int[] inputSizes = {10000, 20000, 40000, 80000, 160000};
        Random random = new Random();

        System.out.printf("%-12s | %-20s | %-20s | %-20s%n", "Input Size", "Linear Scan O(n)", "Merge Sort O(n log n)", "Insertion Sort O(n^2)");
        System.out.println("--------------------------------------------------------------------------------------");

        for (int size : inputSizes) {
            // 1. Generate random array
            int[] originalArray = new int[size];
            for (int i = 0; i < size; i++) {
                originalArray[i] = random.nextInt(10000);
            }

            // Create copies so each algorithm works on the exact same unsorted data
            int[] arrForScan = Arrays.copyOf(originalArray, size);
            int[] arrForMerge = Arrays.copyOf(originalArray, size);
            int[] arrForInsertion = Arrays.copyOf(originalArray, size);

            // 2. Measure Linear Scan O(n)
            long startScan = System.nanoTime();
            linearScanSum(arrForScan);
            long endScan = System.nanoTime();
            double timeScan = (endScan - startScan) / 1_000_000.0; // Convert to milliseconds

            // 3. Measure Merge Sort O(n log n)
            long startMerge = System.nanoTime();
            mergeSort(arrForMerge, 0, arrForMerge.length - 1);
            long endMerge = System.nanoTime();
            double timeMerge = (endMerge - startMerge) / 1_000_000.0;

            // 4. Measure Insertion Sort O(n^2)
            long startInsertion = System.nanoTime();
            insertionSort(arrForInsertion);
            long endInsertion = System.nanoTime();
            double timeInsertion = (endInsertion - startInsertion) / 1_000_000.0;

            // Print results
            System.out.printf("%-12d | %-17.3f ms | %-17.3f ms | %-17.3f ms%n", 
                              size, timeScan, timeMerge, timeInsertion);
        }
    }
}