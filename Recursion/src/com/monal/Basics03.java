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

      // Move elements of arr[0..i-1], that are greater than key, to one position ahead
      // of their current position
      while (j >= 0 && arr[j] > key) {
        arr[j + 1] = arr[j];
        j = j - 1;
      }
      arr[j + 1] = key;
    }
  }

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

  // ---------------------------------------- //

  public static void printArray(int[] arr) {
    for (int value : arr) {
      System.out.print(value + " ");
    }
    System.out.println();
  }
}
