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

    /* // Cyclic sort

    int[] arr4 = {3, 5, 2, 1, 4};

    System.out.println("Array before sorting:");
    printArray(arr4);

    cyclicSort(arr4);
    System.out.println("Array after Cyclic Sort:");
    printArray(arr4);

    System.out.println("------------------------"); */
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

    // Start from the second element (index 1)
    for (int i = 1; i < n; ++i) {
      // Select the element to be inserted
      int key = arr[i];
      int j = i - 1;

      // Move elements of arr[0..i-1] that are greater than key to one position ahead of their
      // current position
      while (j >= 0 && arr[j] > key) {
        arr[j + 1] = arr[j];
        j = j - 1;
      }

      // Place the key in its correct position
      arr[j + 1] = key;
    }
  }

  // Example walkthrough:
  // Let arr = [33, 32, 31, 3]

  // 1st iteration (i = 1):
  //   key = 32, j = 0
  //   33 > 32, so shift 33 to the right
  //   arr becomes [33, 33, 31, 3]
  //   Place key (32) at j+1
  //   arr becomes [32, 33, 31, 3]

  // 2nd iteration (i = 2):
  //   key = 31, j = 1
  //   33 > 31, so shift 33 to the right
  //   32 > 31, so shift 32 to the right
  //   arr becomes [32, 33, 33, 3]
  //   Place key (31) at j+1
  //   arr becomes [31, 32, 33, 3]

  // 3rd iteration (i = 3):
  //   key = 3, j = 2
  //   33 > 3, so shift 33 to the right
  //   32 > 3, so shift 32 to the right
  //   31 > 3, so shift 31 to the right
  //   arr becomes [31, 32, 33, 33]
  //   Place key (3) at j+1
  //   arr becomes [3, 31, 32, 33]

  // Final sorted array: [3, 31, 32, 33]

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

  /* public static void cyclicSort(int[] arr) {
    int i = 0;
    while (i < arr.length) {
      int correctIndex = arr[i] - 1;
      if (arr[i] != arr[correctIndex]) {
        swap(arr, i, correctIndex);
      } else {
        i++;
      }
    }
  } */

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

// To remember what each sort does, you can think of it like this:
// Bubble sort: It bubbles the largest element to the end of the array in each pass.
// Insertion sort: It inserts the current element in its correct position in the sorted part of the
// array.
// Selection sort: It selects the minimum element from the unsorted part and places it at the
// beginning of the array.
// Cyclic sort: It places each element in its correct position in the array by swapping elements.

/*
 *
 * Bubble sort : we compare each element with its adjacent element and swap if its greater than the adjacent element
 * Insertion sort : We take an element
 *
 *
 */
