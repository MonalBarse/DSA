package com.monal;

public class Basics03 {
  public static void main(String[] args) {
    // Bubble sort
    int[] arr1 = {64, 34, 25, 12, 22, 11, 90};

    System.out.println("Array before sorting:");
    printArray(arr1);

    bubbleSort(arr1);

    System.out.println("Array after Bubble Sort:");
    printArray(arr1);
    System.out.println("------------------------");
    // ---------------------------------------- //
    // Insertion sort

    int[] arr2 = {64, 34, 25, 12, 22, 11, 90};

    System.out.println("Array before sorting:");
    printArray(arr2);

    insertionSort(arr2);

    System.out.println("Array after Insertion Sort:");
    printArray(arr2);

    System.out.println("------------------------");
    // ---------------------------------------- //
    // Selection sort

    int[] arr3 = {64, 34, 25, 12, 22, 11, 90};

    System.out.println("Array before sorting:");
    printArray(arr3);

    selectionSort(arr3);

    System.out.println("Array after Selection Sort:");
    printArray(arr3);

    System.out.println("------------------------");
  }

  // Bubble sort algorithm
  public static void bubbleSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
      for (int j = 0; j < n - i - 1; j++) {
        if (arr[j] > arr[j + 1]) {
          // swap arr[j+1] and arr[j]
          int temp = arr[j];
          arr[j] = arr[j + 1];
          arr[j + 1] = temp;
        }
      }
    }
  }

  // ---------------------------------------- //

  // Insertion sort algorithm
  public static void insertionSort(int[] arr) {
    int n = arr.length;
    for (int i = 1; i < n; ++i) {
      int key = arr[i];
      int j = i - 1;

      // We will move elements of arr[0..i-1] that are greater than key to one position ahead of
      // their current position
      while (j >= 0 && arr[j] > key) {
        arr[j + 1] = arr[j];
        j = j - 1;
      }
      arr[j + 1] = key;
    }
  }

  // Let the arr = [33 32 31 3]

  // 1st iteration: key = 32, j = 0 Since 33 > 32, shift 33 to the right. arr = [33 33 31 3] and key
  // = 32 -> arr = [32 33 31 3]

  // 2nd iteration: key = 31, j = 1 Since 33 > 31, shift 33 to the right. Since 32 > 31, shift 32 to
  // the right. -> arr = [31, 32, 33, 3]

  // 3rd iteration: key = 3, j = 2 Since 33 > 3, shift 33 to the right. Since 32 > 3, shift 32 to
  // the right. Since 31 > 3, shift 31 to the right. -> arr = [3, 31, 32, 33]

  // Final array: [3, 31, 32, 33]

  // ---------------------------------------- //

  // Selection sort algorithm
  public static void selectionSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
      // Find the minimum element in unsorted array
      int minIndex = i;
      for (int j = i + 1; j < n; j++) {
        if (arr[j] < arr[minIndex]) {
          minIndex = j;
        }
      }

      // Swap the found minimum element with the first element of the unsorted array
      int temp = arr[minIndex];
      arr[minIndex] = arr[i];
      arr[i] = temp;
    }
  }

  // In selection sort we get minimum element and iterate through the array and swap with other
  // minimum we find in the array

  // ---------------------------------------- //

  public static void printArray(int[] arr) {
    for (int value : arr) {
      System.out.print(value + " ");
    }
    System.out.println();
  }
}
