import java.util.Arrays;
import java.util.Random;

/**
 * Empirical Observation of Time Complexity
 * Includes Assignment 1 and Assignment 2.
 */
public class TimeComplexityObservation {

    // ==========================================
    // Assignment 1 Algorithms
    // ==========================================
    
    // Linear Scan (Array Sum) -> Expected O(n)
    public static long linearScanSum(int[] arr) {
        long sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }

    // Merge Sort -> Expected O(n log n)
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
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // ==========================================
    // Assignment 2 Algorithms (Sorting)
    // ==========================================

    // Insertion Sort -> Expected O(n^2)
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    // Bubble Sort -> Expected O(n^2)
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }
            // If no two elements were swapped by inner loop, then break
            if (!swapped) break;
        }
    }

    // Quick Sort -> Expected O(n log n) average
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    // ==========================================
    // Test Framework
    // ==========================================
    public static void main(String[] args) {
        // Reduced max size slightly because Bubble Sort O(n^2) gets very slow at 160k
        int[] inputSizes = {10000, 20000, 40000, 80000}; 
        Random random = new Random();

        System.out.println("==========================================================================================");
        System.out.println("ASSIGNMENT 1: Linear Scan vs Merge Sort vs Insertion Sort");
        System.out.println("==========================================================================================");
        System.out.printf("%-12s | %-20s | %-20s | %-20s%n", "Input Size", "Linear Scan O(n)", "Merge Sort O(n log n)", "Insertion Sort O(n^2)");
        System.out.println("------------------------------------------------------------------------------------------");

        for (int size : inputSizes) {
            int[] originalArray = generateRandomArray(size, random);
            int[] arrForScan = Arrays.copyOf(originalArray, size);
            int[] arrForMerge = Arrays.copyOf(originalArray, size);
            int[] arrForInsertion = Arrays.copyOf(originalArray, size);

            double timeScan = measureTime(() -> linearScanSum(arrForScan));
            double timeMerge = measureTime(() -> mergeSort(arrForMerge, 0, arrForMerge.length - 1));
            double timeInsertion = measureTime(() -> insertionSort(arrForInsertion));

            System.out.printf("%-12d | %-17.3f ms | %-17.3f ms | %-17.3f ms%n", size, timeScan, timeMerge, timeInsertion);
        }

        System.out.println("\n==========================================================================================");
        System.out.println("ASSIGNMENT 2: Insertion Sort vs Bubble Sort vs Quick Sort");
        System.out.println("==========================================================================================");
        System.out.printf("%-12s | %-21s | %-21s | %-21s%n", "Input Size", "Insertion Sort O(n^2)", "Bubble Sort O(n^2)", "Quick Sort O(n log n)");
        System.out.println("------------------------------------------------------------------------------------------");

        for (int size : inputSizes) {
            int[] originalArray = generateRandomArray(size, random);
            int[] arrForInsertion2 = Arrays.copyOf(originalArray, size);
            int[] arrForBubble = Arrays.copyOf(originalArray, size);
            int[] arrForQuick = Arrays.copyOf(originalArray, size);

            double timeInsertion2 = measureTime(() -> insertionSort(arrForInsertion2));
            double timeBubble = measureTime(() -> bubbleSort(arrForBubble));
            double timeQuick = measureTime(() -> quickSort(arrForQuick, 0, arrForQuick.length - 1));

            System.out.printf("%-12d | %-18.3f ms | %-18.3f ms | %-18.3f ms%n", size, timeInsertion2, timeBubble, timeQuick);
        }
    }

    // Helper method to generate random arrays
    private static int[] generateRandomArray(int size, Random random) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(10000);
        }
        return arr;
    }

    // Helper method to measure execution time
    private static double measureTime(Runnable method) {
        long start = System.nanoTime();
        method.run();
        long end = System.nanoTime();
        return (end - start) / 1_000_000.0; // Return in milliseconds
    }
}