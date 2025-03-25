package com.monal.BinarySearch;

/*
A peak element in a 2D grid is an element that is strictly greater than all of its adjacent neighbors to the left, right, top, and bottom.
Given a 0-indexed m x n matrix mat where no two adjacent cells are equal, find any peak element mat[i][j] and return the length 2 array [i,j].
You may assume that the entire matrix is surrounded by an outer perimeter with the value -1 in each cell.
You must write an algorithm that runs in O(m log(n)) or O(n log(m)) time.

Example 1:
  Input: mat = [[1,4],[3,2]]
  Output: [0,1]
  Explanation: Both 3 and 4 are peak elements so [1,0] and [0,1] are both acceptable answers.

Example 2:
  Input: mat = [[10,20,15],[21,30,14],[7,16,32]]
  Output: [1,1]
  Explanation: Both 30 and 32 are peak elements so [1,1] and [2,2] are both acceptable answers.


Constraints:
  m == mat.length
  n == mat[i].length
  1 <= m, n <= 500
  1 <= mat[i][j] <= 105

  Approach
    1. Pick a middle column.
    2. Find the max element in that column (this is our "peak candidate").
    3. Compare it with its left and right neighbors:
    4. If it's greater than both, return it.
    5. If left neighbor is greater, search the left half.
    6. If right neighbor is greater, search the right half.
*/
public class P016 {
  class Solution {
    public int[] findPeakGrid(int[][] mat) {
      int cols = mat[0].length, rows = mat.length;
      // Step 1: Define the search space
      // our search space are the columns
      int start = 0, end = cols - 1;

      while (start <= end) {

        // Pick the middle column
        int mid = start + (end - start) / 2;
        int maxRow = 0;

        // Find the max element in the middle column
        for (int i = 0; i < rows; i++) {
          if (mat[i][mid] > mat[maxRow][mid]) {
            maxRow = i;
          }
        }

        // Compare the peak candidate with its neighbors
        boolean isPeak = true;

        if (mid > 0 && mat[maxRow][mid - 1] > mat[maxRow][mid]) {
          isPeak = false;
          end = mid - 1;
        } else if (mid < cols - 1 && mat[maxRow][mid + 1] > mat[maxRow][mid]) {
          isPeak = false;
          start = mid + 1;
        }
        if (isPeak)
          return new int[] { maxRow, mid };
      }
      return new int[] { -1, -1 };
    }
  }

  public static void main(String[] args) {
    P016 p016 = new P016();
    P016.Solution solution = p016.new Solution();
    int[][] mat1 = { { 1, 4 }, { 3, 2 } };
    int[][] mat2 = { { 10, 20, 15 }, { 21, 30, 14 }, { 7, 16, 32 } };
    System.out.println(solution.findPeakGrid(mat1)); // [0,1]
    System.out.println(solution.findPeakGrid(mat2)); // [1,1]
  }
}
