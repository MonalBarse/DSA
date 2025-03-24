package com.monal.Array.Matrices;

public class BinarySearchInMatrix {

  // How to search in a sorted matrix (Binary Search)

  /*
   * A matrix can be of 4 types:
   * 1. Row wise sorted matrix
   * 2. Column wise sorted matrix
   * 3. Fully sorted matrix
   * - Row wise and Column wise sorted matrix ( rows, cols are indi sorted)
   * - Row major sorted (will form a sorted 1D array if flattened)
   * 4. Sorted matrix (not row wise or column wise sorted)
   * - Each row is sorted in ascending order from left to right
   * - Each column is sorted in ascending order from top to bottom
   */
  public static int[] rowWiseSorted(int[][] matrix, int target) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    for (int row = 0; row < rows; row++) {
      int left = 0;
      int right = cols - 1;

      while (left <= right) {
        int mid = left + (right - left) / 2;

        if (matrix[row][mid] == target) {
          return new int[] { row, mid };
        } else if (matrix[row][mid] < target) {
          left = mid + 1;
        } else {
          right = mid - 1;
        }
      }
    }

    return new int[] { -1, -1 }; // Target not found
  }

  // Column wise sorted matrix
  public static int[] columnWiseSorted(int[][] matrix, int target) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    for (int col = 0; col < cols; col++) {
      int left = 0;
      int right = rows - 1;

      while (left <= right) {
        int mid = left + (right - left) / 2;

        if (matrix[mid][col] == target) {
          return new int[] { mid, col };
        } else if (matrix[mid][col] < target) {
          left = mid + 1;
        } else {
          right = mid - 1;
        }
      }
    }

    return new int[] { -1, -1 }; // Target not found
  }

  // row major - entire 2D array is sorted as if it were a 1D array (laid out row
  // by row)
  public static int[] rowMajorSorted(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return new int[] { -1, -1 };
    }

    int rows = matrix.length;
    int cols = matrix[0].length;
    int left = 0;
    int right = rows * cols - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;

      // Convert 1D index to 2D coordinates
      int row = mid / cols;
      int col = mid % cols;

      if (matrix[row][col] == target) {
        return new int[] { row, col };
      } else if (matrix[row][col] < target) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return new int[] { -1, -1 }; // Target not found
  }

  // Row wise and Column wise sorted matrix
  public static int[] rowAndColumnSorted(int[][] matrix, int target) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
      return new int[] { -1, -1 };
    }

    int rows = matrix.length;
    int cols = matrix[0].length;

    // Start from top right corner - row = 0, col = cols - 1
    // If the target is greater than the current element, move down
    // If the target is less than the current element, move left
    int row = 0;
    int col = cols - 1;

    while (row < rows && col >= 0) {
      if (matrix[row][col] == target) {
        return new int[] { row, col };
      } else if (matrix[row][col] < target) {
        // Move Down
        row++;
      } else {
        // Move Left
        col--;
      }
    }

    return new int[] { -1, -1 }; // Target not found
  }

  // Sorted matrix (not row wise or column wise sorted but
  // each row & column is sorted)
  public class SortedMatrixBinary {
    public static int[] search(int[][] matrix, int target) {
      if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
        return new int[] { -1, -1 };
      }

      return searchHelper(matrix, target, 0, matrix.length - 1, 0, matrix[0].length - 1);
    }

    private static int[] searchHelper(int[][] matrix, int target,
        int rowStart, int rowEnd,
        int colStart, int colEnd) {
      // Base case: search space is empty
      if (rowStart > rowEnd || colStart > colEnd) {
        return new int[] { -1, -1 };
      }

      // Base case: single cell
      if (rowStart == rowEnd && colStart == colEnd) {
        if (matrix[rowStart][colStart] == target) {
          return new int[] { rowStart, colStart };
        } else {
          return new int[] { -1, -1 };
        }
      }

      int rowMid = rowStart + (rowEnd - rowStart) / 2;
      int colMid = colStart + (colEnd - colStart) / 2;

      if (matrix[rowMid][colMid] == target) {
        return new int[] { rowMid, colMid };
      }

      int[] result = new int[] { -1, -1 };

      // If target is smaller, search in top-left, top-right, and bottom-left
      // quadrants
      if (matrix[rowMid][colMid] > target) {
        // Top-left quadrant
        result = searchHelper(matrix, target, rowStart, rowMid, colStart, colMid);
        if (result[0] != -1)
          return result;

        // Top-right quadrant
        result = searchHelper(matrix, target, rowStart, rowMid, colMid + 1, colEnd);
        if (result[0] != -1)
          return result;

        // Bottom-left quadrant
        result = searchHelper(matrix, target, rowMid + 1, rowEnd, colStart, colMid);
      } else {
        // If target is larger, search in bottom-right, top-right, and bottom-left
        // quadrants
        // Bottom-right quadrant
        result = searchHelper(matrix, target, rowMid + 1, rowEnd, colMid + 1, colEnd);
        if (result[0] != -1)
          return result;

        // Top-right quadrant
        result = searchHelper(matrix, target, rowStart, rowMid, colMid + 1, colEnd);
        if (result[0] != -1)
          return result;

        // Bottom-left quadrant
        result = searchHelper(matrix, target, rowMid + 1, rowEnd, colStart, colMid);
      }

      return result;
    }
  }

  public static void main(String[] args) {

    int[][] rowWiseSortedMatrix = {
        { 1, 3, 5, 7 },
        { 11, 14, 16, 20 },
        { 10, 30, 34, 60 }
    };

    int[][] columnWiseSortedMatrix = {
        { 1, 5, 4, 11 },
        { 2, 6, 8, 12 },
        { 3, 7, 9, 16 },
        { 10, 13, 14, 17 }
    };

    // Row-major sorted
    int[][] rowMajorSortedMatrix = {
        { 1, 2, 3, 4 },
        { 5, 6, 7, 8 },
        { 9, 10, 11, 12 }
    };

    // Row-wise and column-wise sorted
    int[][] sortedMatrix = {
        { 10, 20, 30, 40 },
        { 15, 25, 35, 45 },
        { 27, 29, 37, 48 },
        { 32, 33, 39, 50 }
    };

    int target = 29;

    System.out.println(
        "Row-wise sorted matrix: " + java.util.Arrays.deepToString(rowWiseSortedMatrix) + " Target: " + target);
    int[] result = rowWiseSorted(rowWiseSortedMatrix, target);
    System.out.println("Row-wise sorted matrix: " + result[0] + " " + result[1]);

    System.out.println(
        "Column-wise sorted matrix: " + java.util.Arrays.deepToString(columnWiseSortedMatrix) + " Target: " + target);
    result = columnWiseSorted(columnWiseSortedMatrix, target);
    System.out.println("Column-wise sorted matrix: " + result[0] + " " + result[1]);

    System.out.println(
        "Row-major sorted matrix: " + java.util.Arrays.deepToString(rowMajorSortedMatrix) + " Target: " + target);
    result = rowMajorSorted(rowMajorSortedMatrix, target);
    System.out.println("Row-major sorted matrix: " + result[0] + " " + result[1]);

    System.out.println("Row-wise and column-wise sorted matrix: " + java.util.Arrays.deepToString(sortedMatrix)
        + " Target: " + target);
    result = rowAndColumnSorted(sortedMatrix, target);
    System.out.println("Row-wise and column-wise sorted matrix: " + result[0] + " " + result[1]);

    // Test for specially sorted matrix
    System.out
        .println("Specially sorted matrix: " + java.util.Arrays.deepToString(sortedMatrix) + " Target: " + target);
    result = SortedMatrixBinary.search(sortedMatrix, target);
    System.out.println("Specially sorted matrix: " + result[0] + " " + result[1]);

  }
}
