package com.monal.BinarySearch;
/*
  Problem Statement:
    Given a sorted array arr and a target x, find the first occurrence of x in the array.
    If x is not found, return -1.
*/

public class P001 {
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

  public static void main(String[] args) {
    int[] arr = { 1, 3, 5, 5, 5, 8, 10 };
    int target = 5;

    int result = firstOccurrence(arr, target);
    System.out.println("First occurrence at index: " + result);
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
