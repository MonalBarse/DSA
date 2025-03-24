package com.monal.BinarySearch;
/*
  Problem Statement:
    Given a sorted array arr and a target x, find the first occurrence of x in the array.
    If x is not found, return -1.
*/

/*
  Problem Statement:
    Search a 2D Matrix
      Given an m x n integer matrix with the following properties:
      - Integers in each row are sorted in ascending order from left to right.
      - The first integer of each row is greater than the last integer of the previous row.

      This matrix is called as row major sorted matrix.
*/

/*
  Problem Statement:
    Search a 2D Matrix
      Given an m x n integer matrix with the following properties:
      - Integers in each row are sorted in ascending order from left to right.
      - Integers in each column are sorted in ascending order from top to bottom.

      This matrix is called row wise and column wise sorted matrix.
*/

public class P001 {

  // Problem 1: Find First Occurrence of a Number in a Sorted Array
  public static int firstOccurrence(int[] arr, int target) {
    int left = 0, right = arr.length - 1;
    int result = -1; // Store potential first occurrence

    while (left <= right) {
      int mid = left + (right - left) / 2;

      if (arr[mid] == target) {
        result = mid; // Store index, but continue searching left
        right = mid - 1;
      } else if (arr[mid] < target) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return result; // Return first occurrence index
  }

  // Problem 2: Search a 2D Matrix
  public static boolean bs2DMatrix(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
      return false;
    int rows = matrix.length;
    int cols = matrix[0].length;
    int start = 0, end = rows * cols - 1;

    while (start <= end) {
      int mid = start + (end - start) / 2;
      // IMPORTANT
      int mid_element = matrix[mid / cols][mid % cols]; // Convert 1D index to 2D index
      if (mid_element == target) {
        return true;
      } else if (mid_element < target) {
        start = mid + 1;
      } else {
        end = mid - 1;
      }
    }
    return false;
  }

  // Probelm 3: Search in 2D Matrix which is individually row wise and
  // column wise sorted
  public static int[] rowAndColumnWiseSorted(int[][] matrix, int target) {

    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return new int[] { -1, -1 };
    }
    int rows = matrix.length;
    int cols = matrix[0].length;

    // Start from top right corner - row = 0, col = cols - 1
    // If the target is greater than the current element, move down
    // If the target is less than the current element, move left
    int rowPointer = 0;
    int colPointer = cols - 1;

    while (rowPointer < rows && colPointer >= 0) {
      if (matrix[rowPointer][colPointer] == target) {
        return new int[] { rowPointer, colPointer };
      } else if (matrix[rowPointer][colPointer] < target) {
        // Move Down
        rowPointer++;
      } else {
        // Move Left
        colPointer--;
      }
    }

    return new int[] { -1, -1 }; // Target not found

  }

  public static void main(String[] args) {
    int[] arr = { 1, 3, 5, 5, 5, 8, 10 };
    int target = 5;

    int result = firstOccurrence(arr, target);
    System.out.println("First occurrence at index: " + result);

    int[][] matrix = { { 1, 3, 5, 7 }, { 10, 11, 16, 20 }, { 23, 30, 34, 60 }, { 63, 67, 69, 90 } };
    int target2 = 34;
    System.out.println("Target " + target2 + " found in 2D Matrix: " + bs2DMatrix(matrix, target2));
  }

  // Binary Search BASICS
  public static boolean binarySearch(int[] arr, int target) {
    int start = 0, end = arr.length - 1;
    while (start <= end) {
      int mid = start + (end - start) / 2;
      if (arr[mid] == target) {
        return true;
      } else if (arr[mid] < target) {
        start = mid + 1;
      } else {
        end = mid - 1;
      }
    }
    return false;
  }

}
