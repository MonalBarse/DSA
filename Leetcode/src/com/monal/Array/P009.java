package com.monal.Array;
//

/* https://leetcode.com/problems/set-matrix-zeroes/ - The solution must be of O(1) space complexity.
 Given an m x n integer matrix matrix, if an element is 0, set its entire row and column to 0's.
You must do it in place.

Example 1:
  Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
  Output: [[1,0,1],[0,0,0],[1,0,1]]
Example 2:
  Input: matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
  Output: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
Example 3"
  Input: matrix = [[61,2,4,26,7],[3,0,5,2,3],[1,3,61,5,0],[10,1,52,3,4],[21,2,3,4,45],[1,23,3,4,5]]
  Output matrix = [[61,0,4,26,0],[0,0,0,0,0],[0,0,0,0,0],[10,0,52,3,0],[21,0,3,4,0],[1,0,3,4,0]]

  Constraints:
    m == matrix.length
    n == matrix[0].length
    1 <= m, n <= 200
   -231 <= matrix[i][j] <= 231 - 1

*/
public class P009 {

  class Solution {
    public void setZeroes(int[][] matrix) {
      // 3x3 ---> zero location - (1,1) i.e -> 1 arr -> all zero, 0th , 1st, 2nd (i.e
      // all array's) array's 1st element should be zero

      // 3x4 ---> zero location - (0,0) (0,3) i.e -> 0 arr -> all zero, 0th, 1st, 2nd
      // i.e all array's 0th and 3rd element should be zero

      // 5x6 ---> zero location - (1,1) (2,5) i.e -> 1 arr & 2 arr -> all zero,
      // all array's 1st and 5th element should be zero
      int m = matrix.length, n = matrix[0].length;
      boolean firstRowZero = false, firstColZero = false;

      // Step 1: Determine if first row and first column need to be zero
      for (int i = 0; i < m; i++) {
        if (matrix[i][0] == 0) {
          firstColZero = true;
          break;
        }
      }
      for (int j = 0; j < n; j++) {
        if (matrix[0][j] == 0) {
          firstRowZero = true;
          break;
        }
      }

      // Step 2: Mark rows and columns using first row and first column
      for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
          if (matrix[i][j] == 0) {
            matrix[i][0] = 0;
            matrix[0][j] = 0;
          }
        }
      }

      // Step 3: Use marks to set entire row and column to zero
      for (int i = 1; i < m; i++) {
        if (matrix[i][0] == 0) {
          for (int j = 1; j < n; j++) {
            matrix[i][j] = 0;
          }
        }
      }
      for (int j = 1; j < n; j++) {
        if (matrix[0][j] == 0) {
          for (int i = 1; i < m; i++) {
            matrix[i][j] = 0;
          }
        }
      }

      // Step 4: Handle first row and first column separately
      if (firstRowZero) {
        for (int j = 0; j < n; j++) {
          matrix[0][j] = 0;
        }
      }
      if (firstColZero) {
        for (int i = 0; i < m; i++) {
          matrix[i][0] = 0;
        }
      }
    }
  }

  public static void main(String[] args) {

    Solution sol = new P009().new Solution();
    int[][] matrix = { { 1, 1, 1 }, { 1, 0, 1 }, { 1, 1, 1 } };
    sol.setZeroes(matrix);
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
    // exptected:
    System.out.println("Expected: [[1,0,1],[0,0,0],[1,0,1]]");

    int[][] matrix1 = { { 61, 2, 4, 26, 7 }, { 3, 0, 5, 2, 3 }, { 1, 3, 61, 5, 0 }, { 10, 1, 52, 3, 4 },
        { 21, 2, 3, 4, 45 }, { 1, 23, 3, 4, 5 } };
    sol.setZeroes(matrix1);
    for (int i = 0; i < matrix1.length; i++) {
      for (int j = 0; j < matrix1[0].length; j++) {
        System.out.print(matrix1[i][j] + " ");
      }
      System.out.println();
    }
    // exptected:
    System.out.println("Expected: [[61,0,4,26,0],[0,0,0,0,0],[0,0,0,0,0],[10,0,52,3,0],[21,0,3,4,0],[1,0,3,4,0]]");

  }

}
