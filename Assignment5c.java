package DAY1;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Assignment5c {

	final static int n = 10000000;
	private static long startTime;
	private static int base = 10;

	public static void main(String args[]) throws IOException {
		Integer[] arr = new Integer[n]; // Array for mean quick sort
		Integer[] arrCopy = new Integer[n]; // Array for randomized quick sort

		System.out.println("***Compare Run time complexity***");

		String folderpath = args[0]; // Read from command prompt
		File trainingpath = new File(folderpath + "/rev.txt"); // Enter the
																// name of the
																// file
		BufferedReader br = null;
		String line = null;
		String[] data = null;
		try { // Read from file the data
			br = new BufferedReader(new FileReader(trainingpath));
			while ((line = br.readLine()) != null) {
				data = line.split(",");
			}
		} catch (FileNotFoundException e) // Handle File not Found error
		{
			System.out
					.println("Please make sure the directory file is actually there.");
		}

		for (int i = 0; i < n; i++) { // Store value from file to array
			arr[i] = Integer.parseInt(data[i]);
			arrCopy[i] = arr[i];
		}

		// Quick Sort for deterministic
		System.out.println("Quick Sort by Deterministic");
		StartTimer();
		quickSort(arr);
		StopTimer();
		// Quick Sort by randomized
		System.out.println("Quick Sort by random");
		StartTimer();
		QuickSort(arrCopy, 0, n - 1);
		StopTimer();

	}

	// Stop the timer
	private static void StopTimer() {
		long stopTime = System.currentTimeMillis();
		System.out.println("Total time : " + (stopTime - startTime) + " msec.");

	}

	// Start the timer
	private static void StartTimer() {
		startTime = System.currentTimeMillis();
	}

	// Quick sort for deterministic
	public static void quickSort(Integer[] arr) {
		recQuickSort(arr, 0, arr.length - 1);
	}

	// Deterministic quicksort
	public static <T extends Comparable<? super T>> void recQuickSort(T[] arr,
			int left, int right) {
		
		if (left + base > right) {
			InsertionSort(arr, left, right);
			return;
		} else {
			T median = medianOf3(arr, left, right);
			int partition = partitionIt(arr, left, right, median);
			recQuickSort(arr, left, partition - 1);
			recQuickSort(arr, partition + 1, right);
		}
	}

	// Take median of 3
	public static <T extends Comparable<? super T>> T medianOf3(T[] arr,
			int left, int right) {
		int center = (left + right) / 2;

		if (arr[left].compareTo(arr[center]) > 0)
			swap(arr, left, center);

		if (arr[left].compareTo(arr[right]) > 0)
			swap(arr, left, right);

		if (arr[center].compareTo(arr[right]) > 0)
			swap(arr, center, right);

		swap(arr, center, right - 1);
		return arr[right - 1];
	}

	// Perform swapping
	public static <T extends Comparable<? super T>> void swap(T[] arr,
			int dex1, int dex2) {
		T temp = arr[dex1];
		arr[dex1] = arr[dex2];
		arr[dex2] = temp;
	}

	// Perform partition on deterministic quicksort
	public static <T extends Comparable<? super T>> int partitionIt(T[] arr,
			int left, int right, T pivot) {
		int leftPtr = left;
		int rightPtr = right - 1;

		while (true) {
			while (arr[++leftPtr].compareTo(pivot) < 0)
				;
			while (arr[--rightPtr].compareTo(pivot) > 0)
				;
			if (leftPtr >= rightPtr)
				break;
			else
				swap(arr, leftPtr, rightPtr);
		}
		swap(arr, leftPtr, right - 1);
		return leftPtr;
	}

	// QuickSort for randomization
	public static <T extends Comparable<? super T>> void QuickSort(T[] A,
			int p, int r) {
		int n = r - p + 1;

		if (n < base) {
			InsertionSort(A, p, r);
			return;
		} else {
			int q = Partition(A, p, r);
			if (q > p)
				QuickSort(A, p, q - 1);
			if (q < r - 1)
				QuickSort(A, q + 1, r);
		}
	}

	// Perform partition on randomized quick sort
	public static <T extends Comparable<? super T>> int Partition(T[] A, int p,
			int r) {
		Random random = new Random();
		int i = Math.abs(random.nextInt(r - p) + p);

		swap(A, i, r);
		T pivot = A[r];
		i = p - 1;

		for (int j = p; j <= r - 1; j++) {
			if (A[j].compareTo(pivot) <= 0) {
				i++;
				swap(A, i, j);
			}
		}
		swap(A, i + 1, r);
		return i + 1;
	}

	// Perform Insertion SOrt for base
	public static <T extends Comparable<? super T>> void InsertionSort(T[] A,
			int p, int r) {
		for (int i = p; i <= r; i++) {
			for (int j = i; j > p; j--) {
				if (A[j].compareTo(A[j - 1]) < 0) {
					T t = A[j];
					A[j] = A[j - 1];
					A[j - 1] = t;
				} else
					break;
			}
		}
	}
}
