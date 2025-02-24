// BINARY SEARCH WITH RECURSION
package com.monal;

import java.util.ArrayList;

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
    System.out.println("------------------------");
    // Linear search
    int target2 = 88;

    // linearSearch(array, target2, 0);
    // if (list.isEmpty()) {
    //   System.out.println("Element not found");
    // } else {
    //   System.out.println("Element found at index " + list + " in the array");
    // }
    int ans = rotatedBinarySearch(array, target2, 0, array.length - 1);
    if (ans == -1) {
      System.out.println("Element not found");
    } else {
      System.out.println("Element found at index " + ans + " in the array");
    }
  }

  /*
   * Apply memoization only when the function has overlapping subproblems and the time complexity of the function is high
   * and not to apply it when the function has a low time complexity as the overhead of memoization will be more than the
   * time saved by memoization
   *
   * General steps in Memoised Functions:
   *
   * Pass a memoization Object to the function (here its an array)
   * Check if the result is already memoized and if it is return the result
   * If the result is not memoized, calculate the result and store it in the memoization object
   */
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

  // Solve for a rotated array using binary search with recursion

  public static int rotatedBinarySearch(int[] arr, int target, int start, int end) {
    if (start > end) {
      return -1;
    }

    int mid = start + (end - start) / 2;

    if (arr[mid] == target) {
      return mid;
    }

    if (arr[start] <= arr[mid]) {
      // The left half is sorted
      if (target >= arr[start] && target < arr[mid]) {
        return rotatedBinarySearch(arr, target, start, mid - 1);
      } else {
        return rotatedBinarySearch(arr, target, mid + 1, end);
      }
    } else {
      // The right half is sorted
      if (target > arr[mid] && target <= arr[end]) {
        return rotatedBinarySearch(arr, target, mid + 1, end);
      } else {
        return rotatedBinarySearch(arr, target, start, mid - 1);
      }
    }
  }

  // Not optimized as its creating objects at each recursive call but this is just for learning
  public static ArrayList<Integer> linearSearch(int[] arr, int target, int index) {
    // solve using recursion
    ArrayList<Integer> list = new ArrayList<Integer>(); // Create an ArrayList object

    if (index == arr.length) {
      return list;
    }
    if (arr[index] == target) {
      list.add(index);
    }
    ArrayList<Integer> temp = linearSearch(arr, target, index + 1);
    list.addAll(temp);

    return list;
  }

  // Simple LinerSearch
  public static int linearSearch1(int[] arr, int target, int index) {
    return (index == arr.length)
        ? -1
        : (arr[index] == target ? index : linearSearch1(arr, target, index + 1));
  }
}
