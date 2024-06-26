package com.monal;

/*
public class Basics02 {

  public static void main(String[] args) {
    // BinarySearch with recursion
    int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int target, start, end;
    target = 5;
    start = 0;
    end = array.length - 1;
    int result = binarySearch(array, target, start, end);
    if (result == -1) {
      System.out.println("Element not found");
    } else {
      System.out.println("Element found at positon " + (result + 1) + " in the array");
    }
  }

  public static int binarySearch(int[] arr, int target, int start, int end) {
    if (start > end) {
      return -1; // target not found
    }
    int mid = start + (end - start) / 2;
    if (arr[mid] == target) {
      return mid;
    }
    if (arr[mid] < target) {
      return binarySearch(arr, target, mid + 1, end);
    } else {
      return binarySearch(arr, target, start, mid - 1);
    }
  }
} */

// Memoization is an optimization technique used primarily to speed up computer programs by storing
public class Basics02 {
  public static void main(String[] args) {
    // BinarySearch with recursion and memoization

    int[] array = {11, 34, 55, 66, 88, 97, 151, 222, 566, 1111};
    int target, start, end;
    target = 97;
    start = 0;
    end = array.length - 1;
    int[] memo = new int[array.length];
    int result = binarySearch(array, target, start, end, memo);
    if (result == -1) {
      System.out.println("Element not found");
    } else {
      System.out.println("Element found at positon " + (result + 1) + " in the array");
    }
  }

  public static int binarySearch(int[] arr, int target, int start, int end, int[] memo) {
    if (start > end) {
      return -1; // Base case: element not found
    }

    int mid = start + (end - start) / 2;

    // Check memoization table
    if (memo[mid] != 0 && memo[mid] == target) {
      return mid; // Return memoized result if found
    }

    // Perform binary search
    if (arr[mid] == target) {
      memo[mid] = target; // Memoize the result
      return mid; // Element found
    } else if (arr[mid] < target) {
      return binarySearch(arr, target, mid + 1, end, memo); // Search right half
    } else {
      return binarySearch(arr, target, start, mid - 1, memo); // Search left half
    }
  }
}
