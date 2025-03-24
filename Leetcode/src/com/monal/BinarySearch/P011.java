package com.monal.BinarySearch;

/*  BOOk ALLOCATION PROBLEM -  https://www.geeksforgeeks.org/problems/allocate-minimum-number-of-pages0937/1?itm_source=geeksforgeeks&itm_medium=article&itm_campaign=practice_card

You are given an array arr[] of integers, where each element arr[i] represents the number of pages in the ith book. You also have an integer k representing the number of students. The task is to allocate books to each student such that:
  Each student receives atleast one book.
  Each student is assigned a contiguous sequence of books.
  No book is assigned to more than one student.

  The objective is to minimize the maximum number of pages assigned to any student. In other words, out of all possible allocations, find the arrangement where the student who receives the most pages still has the smallest possible maximum.
  Note: Return -1 if a valid assignment is not possible, and allotment should be in contiguous order (see the explanation for better understanding).

Example 1:
  Input: arr[] = [12, 34, 67, 90], k = 2
  Output: 113
  Explanation: Allocation can be done in following ways:
  [12] and [34, 67, 90] Maximum Pages = 191
  [12, 34] and [67, 90] Maximum Pages = 157
  [12, 34, 67] and [90] Maximum Pages = 113.
  Therefore, the minimum of these cases is 113, which is selected as the output.

Example 2:
  Input: arr[] = [15, 17, 20], k = 5
  Output: -1
  Explanation: Allocation can not be done.

Example 3:
  Input: arr[] = [22, 23, 67], k = 1
  Output: 112
*/

public class P011 {
  class Solution {
    public static int findPages(int[] arr, int k) {
      int n = arr.length;

      // Edge case: If there are fewer books than students
      if (n < k) {
        return -1; // Cannot allocate books to all students
      }

      // Step 1: Define the search space
      // The search space is the range of pages in the books
      // The min number of pages (start) is the max number of pages in the array
      // The max number of pages (end) is the sum of all pages in the array
      int start = Integer.MIN_VALUE; // Max pages in a single book
      int end = 0; // Sum of all pages

      for (int page : arr) {
        start = Math.max(start, page);
        end += page;
      }

      // Step 2: Binary Search on the search space
      int result = -1;
      while (start <= end) {
        int mid = start + (end - start) / 2;

        // Check if we can allocate books with mid pages
        if (canAllocateBooks(arr, n, k, mid)) {
          result = mid;
          end = mid - 1; // Try to minimize the max pages
        } else {
          start = mid + 1;
        }
      }

      return result;
    }

    private static boolean canAllocateBooks(int[] arr, int n, int k, int maxPages) {
      int students = 1;
      int currentPages = 0;

      for (int i = 0; i < n; i++) {
        // If a single book has more pages than maxPages, we can't allocate
        if (arr[i] > maxPages) {
          return false;
        }

        // If adding this book exceeds maxPages, allocate to next student
        if (currentPages + arr[i] > maxPages) {
          students++;
          currentPages = arr[i];

          // If we need more students than available, return false
          if (students > k) {
            return false;
          }
        } else {
          currentPages += arr[i];
        }
      }

      return true; // Valid allocation found
    }
  }

  public static void main(String[] args) {
    P011.Solution solution = new P011().new Solution();
    int[] arr1 = { 12, 34, 67, 90 };
    int k1 = 2;
    System.out.println(solution.findPages(arr1, k1)); // Output: 113

    int[] arr2 = { 15, 17, 20 };
    int k2 = 5;
    System.out.println(solution.findPages(arr2, k2)); // Output: -1

    int[] arr3 = { 22, 23, 67 };
    int k3 = 1;
    System.out.println(solution.findPages(arr3, k3)); // Output: 112

  }
}
