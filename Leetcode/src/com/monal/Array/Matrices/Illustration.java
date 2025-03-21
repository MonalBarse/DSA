package com.monal.Array.Matrices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * This is an illustration on how to solve Matrix problems.
 * We will cover basics of Matrix and how to solve problems related to it.
 *    - How to traverse a matrix
 *    - How to traverse a matrix in spiral order
 *    - How to transpose a matrix
 *    - How to reverse a matrix
 *    - How to set matrix zeroes
 *    - How to search in an unsorted matrix
 *    - How to search in a sorted matrix (BS)
 *    - How to find the median of a row wise sorted matrix
 *    - How to find the kth smallest element in a row wise and column wise sorted matrix
 *    - How to find the saddle point in a matrix
 *    - How to find the largest square of 1's in a binary matrix
 */
public class Illustration {
  // 1. How to traverse a matrix
  public void traverseMatrix(int[][] matrix, String type) {
    if (type.equals("row")) {
      for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
          System.out.print(matrix[i][j] + " ");
        }
        System.out.println();
      }
    } else if (type.equals("column")) {
      for (int j = 0; j < matrix[0].length; j++) {
        for (int i = 0; i < matrix.length; i++) {
          System.out.print(matrix[i][j] + " ");
        }
        System.out.println();
      }
    }
  }

  // 2. How to traverse a matrix in spiral order
  public void traverseInSpiralOrder(int[][] matrix) {
    int top = 0, bottom = matrix.length - 1, left = 0, right = matrix[0].length - 1;
    while (top <= bottom && left <= right) {
      for (int i = left; i <= right; i++) {
        System.out.print(matrix[top][i] + " ");
      }
      top++;
      for (int i = top; i <= bottom; i++) {
        System.out.print(matrix[i][right] + " ");
      }
      right--;
      if (top <= bottom) {
        for (int i = right; i >= left; i--) {
          System.out.print(matrix[bottom][i] + " ");
        }
        bottom--;
      }
      if (left <= right) {
        for (int i = bottom; i >= top; i--) {
          System.out.print(matrix[i][left] + " ");
        }
        left++;
      }
    }
  }

  // 3. How to Transpose a matrix
  public void transposeMatrix(int[][] matrix) {
    int col = matrix[0].length - 1;
    int row = matrix.length - 1;
    for (int i = 0; i <= row; i++) {
      // j starts from i+1 to avoid swapping the same element twice
      for (int j = i + 1; j <= col; j++) {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[j][i];
        matrix[j][i] = temp;
      }
    }
  }

  // 4. How to reverse a matrix
  public void reverseMatrix(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols / 2; j++) {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[i][cols - j - 1];
        matrix[i][cols - j - 1] = temp;
      }
    }
  }

  // 5. How to set matrix zeroes (if encountered with 0 while traversing,
  // set that entire row and column to 0)
  public void setMatrixZeroes(int[][] matrix) {
    boolean firstRowZero = false, firstColZero = false;
    int m = matrix.length, n = matrix[0].length;
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

    for (int i = 1; i < m; i++) {
      for (int j = 1; j < n; j++) {
        if (matrix[i][j] == 0) {
          matrix[i][0] = 0;
          matrix[0][j] = 0;
        }
      }
    }

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

  // 5. How to search in an unsorted matrix
  public boolean searchInUnsortedMatrix(int[][] matrix, int target) {
    // traverse the whole matrix and check if target is present
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] == target) {
          return true;
        }
      }
    }
    return false;
  }

  // 7. How to find the median of a row wise sorted matrix
  public static int findMedian(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
    for (int i = 0; i < m; i++) {
      min = Math.min(min, matrix[i][0]);
      max = Math.max(max, matrix[i][n - 1]);
    }
    //
    int median = (m * n + 1) / 2; // median is the middle element in the sorted array

    while (min < max) {
      int mid = min + (max - min) / 2;
      int count = 0;
      for (int i = 0; i < m; i++) {
        count = count + binarySearch(matrix[i], mid);
      }
      if (count < median) {
        min = mid + 1;
      } else {
        max = mid;
      }
    }
    return min;
  }

  // 8. How to find the kth smallest element in a row wise and column wise sorted
  // matrix
  // Row and column wise sorted matrix is a matrix where each row and column is
  // sorted in ascending order
  // Kth smallest element is the kth element in the sorted matrix (not the value)

  public static int kthSmallest(int[][] matrix, int k) {
    int m = matrix.length, n = matrix[0].length; // m - rows, n - cols
    int start = matrix[0][0], end = matrix[m - 1][n - 1];
    while (start < end) {
      int mid = start + (end - start) / 2;
      int count = 0; // count for - how many elements are less than mid
      for (int i = 0; i < m; i++) {
        // search for the number of elements less than mid in each row
        count = count + binarySearch(matrix[i], mid);
      }
      // count < k, means that there are count elements less than mid
      // also therefore kth element is greater than mid
      // that means we need to search in the right half
      if (count < k) {
        start = mid + 1;
      } else {
        // if count >= k, means that there are count elements less than mid,
        // and kth elem is less than count thus we need to search in the left half
        end = mid;
      }
    }
    return start;
  }

  // 9. How to find the saddle point in a matrix
  // Saddle point is a point in the matrix where it is the minimum element in its
  // row and maximum element in its column
  // eg. matrix = [[5,1,8],[4,3,7],[6,2,9]]
  // saddle point = 3 (3 is the minimum element in its row (4,3,7)
  // and maximum element in its column (1,3,2))
  public List<List<Integer>> findSaddlePoints(int[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return new ArrayList<>();
    }
    int rows = matrix.length;
    int cols = matrix[0].length;
    List<List<Integer>> saddlePoint = new ArrayList<>();

    // for each cell in the matrix
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {

        // consider current element as saddle point
        boolean isMinInRow = true;
        boolean isMaxInCol = true;

        // check if the elem is minimum in its row
        for (int col = 0; col < cols; col++) {
          if (matrix[i][col] < matrix[i][j]) {
            isMinInRow = false;
            break;
          }
        }

        // check if the elem is maximum in its column
        for (int row = 0; row < rows; row++) {
          if (matrix[row][j] > matrix[i][j]) {
            isMaxInCol = false;
            break;
          }
        }

        // if the elem is minimum in its row and maximum in its column
        // then it is a saddle point
        if (isMinInRow && isMaxInCol) {
          List<Integer> point = Arrays.asList(i, j);
          saddlePoint.add(point);
        }
      }
    }
    return saddlePoint;
  }

  // 10. How to find the largest square of 1's in a binary matrix
  // revise
  public int maximalSquare(char[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return 0;
    }

    int rows = matrix.length;
    int cols = matrix[0].length;
    int[][] dp = new int[rows + 1][cols + 1];
    int maxSide = 0;

    for (int i = 1; i <= rows; i++) {
      for (int j = 1; j <= cols; j++) {
        if (matrix[i - 1][j - 1] == '1') {
          // The size of the square ending at position (i,j)
          // is determined by the minimum of the squares ending at
          // positions (i-1,j), (i,j-1), and (i-1,j-1), plus 1
          dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
          maxSide = Math.max(maxSide, dp[i][j]);
        }
      }
    }
    return maxSide * maxSide; // Return the area of the largest square
  }

  public static void main(String[] args) {
    System.out.println("MATRIX ILLUSTRATION");
  }

  // helper binary search function
  public static int binarySearch(int[] row, int target) {
    int start = 0, end = row.length - 1;
    while (start <= end) {
      int mid = start + (end - start) / 2;
      if (row[mid] == target) {
        return mid;
      } else if (row[mid] < target) {
        start = mid + 1;
      } else {
        end = mid - 1;
      }
    }
    return start;
  }
}
