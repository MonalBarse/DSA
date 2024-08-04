package com.monal;

public class Patterns {

  public static void main(String[] args) {
    int[] arr = {64, 34, 25, 12, 22, 11, 90};
    pattern1(5, 0);
    bubbleSort(arr, arr.length - 1, 0);
    for (int i : arr) {
      System.out.print(i + " ");
    }

    // mergeSort
    int[] arr2 = {64, 34, 25, 12, 22, 11, 90};
    mergeSort(arr2, 0, arr2.length - 1);
    System.out.println();
    for (int i : arr2) {
      System.out.print(i + " ");
    }
  }

  public static void pattern1(int r, int c) {
    /* Pattern using recursion

    * * * * *
    * * * *
    * * *
    * *
    *

    */
    if (r == 0) {
      return;
    }
    if (c < r) {
      System.out.print("* ");
      pattern1(r, c + 1);
    } else {
      System.out.println();
      pattern1(r - 1, 0);
    }
  }

  public static void bubbleSort(int[] arr, int r, int c) {
    // Base case: if the size of the unsorted part is 0, return
    if (r == 0) {
      return;
    }

    // If c is within bounds, perform the bubble operation
    if (c < r) {
      if (arr[c] > arr[c + 1]) {
        // Swap arr[c] and arr[c + 1]
        int temp = arr[c];
        arr[c] = arr[c + 1];
        arr[c + 1] = temp;
      }
      // Continue to the next element in the current pass
      bubbleSort(arr, r, c + 1);
    } else {
      // Move to the next pass, reducing the size of the unsorted part
      bubbleSort(arr, r - 1, 0);
    }
  }

  // Merge Sort

  public static void mergeSort(int[] arr, int start, int end) {
    if (start < end) {
      int mid = (end - start) / 2 + start;

      // Sort first and second halves
      mergeSort(arr, start, mid);
      mergeSort(arr, mid + 1, end);

      // Merge the sorted halves
      merge(arr, start, mid, end);
    }
  }

  public static void merge(int[] arr, int start, int mid, int end) {
    // Sizes of the two subarrays to be merged
    int n1 = mid - start + 1;
    int n2 = end - mid;

    // Temporary arrays to hold the subarrays
    int[] leftArray = new int[n1];
    int[] rightArray = new int[n2];

    // Copy data to temporary arrays
    for (int i = 0; i < n1; i++) {
      leftArray[i] = arr[start + i];
    }
    for (int j = 0; j < n2; j++) {
      rightArray[j] = arr[mid + 1 + j];
    }

    // Initial indices of the subarrays
    int i = 0, j = 0;

    // Initial index of the merged array
    int k = start;

    // Merge the temporary arrays back into arr[start..end]
    while (i < n1 && j < n2) {
      if (leftArray[i] <= rightArray[j]) {
        arr[k] = leftArray[i];
        i++;
      } else {
        arr[k] = rightArray[j];
        j++;
      }
      k++;
    }

    // Copy remaining elements of leftArray[] if any
    while (i < n1) {
      arr[k] = leftArray[i];
      i++;
      k++;
    }

    // Copy remaining elements of rightArray[] if any
    while (j < n2) {
      arr[k] = rightArray[j];
      j++;
      k++;
    }
  }

  public static void printArray(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + " ");
    }
    System.out.println();
  }
}
