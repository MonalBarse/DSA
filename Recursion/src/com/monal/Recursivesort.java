// For Merge and Quick sort

package com.monal;

import java.util.Arrays;

public class Recursivesort {
  public static void main(String[] args) {

    // ==================================================//

    System.out.println("----------Merge Sort-------------");
    // mergeSort
    int[] arr2 = {64, 34, 25, 12, 22, 11, 90};
    System.out.println("Array before sorting:");
    printArray(arr2);
    mergeSort(arr2, 0, arr2.length - 1);
    System.out.println("Array after Merge Sort:");
    printArray(arr2);
    // ==================================================//
    System.out.println("----------Quick Sort--------------");

    // Quick sort
    int[] arr = {64, 34, 25, 12, 22, 11, 90};
    System.out.println("Array before sorting:");
    printArray(arr);
    quickSort(arr, 0, arr.length - 1);
    System.out.println("Array after Quick Sort:");
    printArray(arr);
  }

  // ------------------------------------------------------------------------------------//
  // Merge sort
  public static void mergeSort(int[] arr, int start, int end) {
    if (start < end) {
      int mid = (end - start) / 2 + start;
      mergeSort(arr, start, mid);
      mergeSort(arr, mid + 1, end);
      merge(arr, start, mid, end);
    }
  }

  public static void merge(int arr[], int start, int mid, int end) {
    int length_1 = mid - start + 1; // Length of the first half is mid - start + 1 (0 indexed)
    int length_2 = end - mid;

    // lets create two temporary arrays to store the two halves then we will merge them
    int[] temp_1 = new int[length_1];
    int[] temp_2 = new int[length_2];

    // Copy data to temp arrays
    temp_1 = Arrays.copyOfRange(arr, start, mid + 1);
    temp_2 = Arrays.copyOfRange(arr, mid + 1, end + 1);

    // Initialize the pointers for the two halves
    int i = 0, j = 0, k = start;

    // Merge the two halves
    // Compare the elements of the two halves and put the smaller element in the original array
    while (i < length_1 && j < length_2) {
      if (temp_1[i] <= temp_2[j]) {
        arr[k] = temp_1[i];
        i++;
      } else {
        arr[k] = temp_2[j];
        j++;
      }
      k++;
    }

    // Copy the remaining elements of the two halves
    // If there are any remaining elements in the first half
    while (i < length_1) {
      arr[k] = temp_1[i];
      i++;
      k++;
    }
    // If there are any remaining elements in the second half
    while (j < length_2) {
      arr[k] = temp_2[j];
      j++;
      k++;
    }

    System.out.println("Array after merging: ");
    printArray(arr);
  }

  // ------------------------------------------------------------------------------------//
  // Quick sort

  // Pivot Selection : Using the last element as the pivot can lead to worst-case O(n^2) complexity
  // for already sorted or reverse sorted arrays.
  public static void quickSort(int[] arr, int start, int end) {
    while (start < end) {
      // Use insertion sort for small arrays (10 elements or less)
      if (end - start < 10) {
        insertionSort(arr, start, end);
        return;
      }

      int pivot = medianOfThree(arr, start, start + (end - start) / 2, end);

      // Here the partition function is called to place the pivot element at its correct position
      pivot = partition(arr, start, end, pivot);

      if (pivot - start < end - pivot) {
        quickSort(arr, start, pivot - 1);
        start = pivot + 1;
      } else {
        quickSort(arr, pivot + 1, end);
        end = pivot - 1;
      }
    }
  }

  private static int partition(int[] arr, int start, int end, int pivotIndex) {
    int pivotValue = arr[pivotIndex];
    swap(arr, pivotIndex, end);
    int storeIndex = start;
    for (int i = start; i < end; i++) {
      if (arr[i] < pivotValue) {
        swap(arr, i, storeIndex);
        storeIndex++;
      }
    }
    swap(arr, storeIndex, end);
    return storeIndex;
  }

  private static int medianOfThree(int[] arr, int a, int b, int c) {

    if (arr[a] < arr[b]) {
      if (arr[b] < arr[c]) return b;
      if (arr[a] < arr[c]) return c;
      return a;
    } else {
      if (arr[a] < arr[c]) return a;
      if (arr[b] < arr[c]) return c;
      return b;
    }
  }

  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  private static void insertionSort(int[] arr, int start, int end) {
    for (int i = start + 1; i <= end; i++) {
      int key = arr[i];
      int j = i - 1;
      while (j >= start && arr[j] > key) {
        arr[j + 1] = arr[j];
        j--;
      }
      arr[j + 1] = key;
    }
  }

  public static void printArray(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i] + " ");
    }
    System.out.println();
  }
}
