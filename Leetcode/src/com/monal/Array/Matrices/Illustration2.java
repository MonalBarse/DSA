
/*
 * Illustration 2: Matrices
 *    - How to multiply two matrices
 *    - How to find the determinant of a matrix
 *    - How to find matrix exponentiation
 *    - Prefix sum in a matrix
 *    - Difference array in a matrix
 *    - Rotate a matrix (any degree)
 *    - How to find Maximum sum matrix (Kadane's algorithm)
 *    - Food fill algorithm (BFS/DFS)
 *    - Shortest path in a matrix (Dijkstra's algorithm)
 */

package com.monal.Array.Matrices;

import java.util.*;

public class Illustration2 {

  /*
   * Matrix multiplication is a binary operation that produces a single matrix
   * from two input matrices. For two matrices A (n × m) and B (m × p), their
   * product C (n × p) is defined by:
   * C[i][j] = Σ A[i][k] × B[k][j] where k goes from 0 to m-1.
   * The code implements this by using three nested loops to compute each element
   * of the result matrix.
   */
  /**
   * Matrix Multiplication Algorithm:
   * - Multiply two matrices A and B where A is (n x m) and B is (m x p).
   * - Iterate through rows of A and columns of B to compute the dot product.
   * - Store the result in a new matrix of size (n x p).
   */
  public static int[][] multiplyMatrices(int[][] A, int[][] B) {
    int n = A.length, m = A[0].length, p = B[0].length;
    int[][] result = new int[n][p];

    // check if matrix multiplication is possible
    if (A[0].length != B.length) {
      return new int[0][0];
    }

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < p; j++) {
        for (int k = 0; k < m; k++) {
          result[i][j] += A[i][k] * B[k][j];
        }
      }
    }
    return result;
  }

  /**
   * Determinant of a Matrix
   * The determinant is a scalar value that can be computed from the elements of a
   * square matrix. It describes certain properties of the matrix and the linear
   * transformation it represents. For smaller matrices:
   *
   * 1×1 matrix: det(A) = A[0][0]
   * 2×2 matrix: det(A) = A[0][0]×A[1][1] - A[0][1]×A[1][0]
   * Larger matrices: Use cofactor expansion.
   *
   * The code implements this recursively by using cofactor expansion along the
   * first row.
   *
   * Determinant of a Matrix Algorithm:
   * - If the matrix is 1x1, return the only element.
   * - If 2x2, compute directly using ad - bc formula.
   * - For larger matrices, recursively compute determinant using cofactor
   * expansion.
   */
  public static int determinant(int[][] matrix, int n) {
    if (n == 1)
      return matrix[0][0];
    if (n == 2)
      return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

    int det = 0;
    for (int i = 0; i < n; i++) {
      int[][] subMatrix = getCofactor(matrix, 0, i, n);
      det += (i % 2 == 0 ? 1 : -1) * matrix[0][i] * determinant(subMatrix, n - 1);
    }
    return det;
  }

  private static int[][] getCofactor(int[][] matrix, int row, int col, int n) {
    int[][] subMatrix = new int[n - 1][n - 1];
    int r = 0, c;
    for (int i = 0; i < n; i++) {
      if (i == row)
        continue;
      c = 0;
      for (int j = 0; j < n; j++) {
        if (j == col)
          continue;
        subMatrix[r][c++] = matrix[i][j];
      }
      r++;
    }
    return subMatrix;
  }

  /**
   * Matrix Exponentiation
   * Matrix exponentiation is raising a matrix to a power. For a square matrix A
   * and a positive integer n, A^n means multiplying A by itself n times. The code
   * implements this efficiently using binary exponentiation, which reduces the
   * number of matrix multiplications from O(n) to O(log n).
   *
   * Matrix Exponentiation Algorithm:
   * - Uses binary exponentiation to efficiently compute power of a matrix.
   * - Start with identity matrix and repeatedly square base matrix.
   */
  public static int[][] matrixExponentiation(int[][] base, int exp) {
    int n = base.length;
    int[][] result = new int[n][n];
    for (int i = 0; i < n; i++)
      result[i][i] = 1;

    while (exp > 0) {
      if (exp % 2 == 1)
        result = multiplyMatrices(result, base);
      base = multiplyMatrices(base, base);
      exp /= 2;
    }
    return result;
  }

  /**
   * Prefix Sum in a Matrix
   * A prefix sum matrix stores the sum of all elements in the submatrix from
   * (0,0) to (i,j) at position (i,j). This allows for O(1) queries of the sum of
   * any rectangular submatrix. The code implements this by computing cumulative
   * sums along both rows and columns.
   * All these algorithms collectively provide powerful tools for working with
   * matrices in various applications, from graphics processing to dynamic
   * programming.
   *
   * Prefix Sum in a Matrix Algorithm:
   * - Computes cumulative sums to allow O(1) submatrix sum queries.
   * - Each cell accumulates sum from top and left neighbors.
   */
  public static int[][] prefixSum(int[][] matrix) {
    int n = matrix.length, m = matrix[0].length;
    int[][] prefix = new int[n][m];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        prefix[i][j] = matrix[i][j];
        if (i > 0)
          prefix[i][j] += prefix[i - 1][j];
        if (j > 0)
          prefix[i][j] += prefix[i][j - 1];
        if (i > 0 && j > 0)
          prefix[i][j] -= prefix[i - 1][j - 1];
      }
    }
    return prefix;
  }

  // ----------------------------------------------------------- //
  /*
   * A difference array in a matrix is a technique used for efficient range
   * updates. Instead of updating each cell individually in a submatrix (which
   * would be O(n²) operation), you can update only the corners of the submatrix
   * in the difference array. This allows for O(1) updates and O(n²)
   * reconstruction of the original matrix after all updates.
   */

  /**
   * Difference Array in a Matrix Algorithm:
   * - Creates a difference array to support efficient range updates.
   * - Each update to a submatrix requires only 4 operations.
   * - After updates, the original matrix can be reconstructed.
   */
  public static int[][] createDifferenceArray(int[][] matrix) {
    int n = matrix.length, m = matrix[0].length;
    int[][] diff = new int[n + 1][m + 1];

    // Compute difference array
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        // Add value at current cell
        diff[i][j] += matrix[i][j];
        // Subtract from right neighbor
        diff[i][j + 1] -= matrix[i][j];
        // Subtract from bottom neighbor
        diff[i + 1][j] -= matrix[i][j];
        // Add to diagonal neighbor
        diff[i + 1][j + 1] += matrix[i][j];
      }
    }
    return diff;
  }

  /**
   * Updates a submatrix in the difference array.
   * (r1,c1) is the top-left corner, (r2,c2) is the bottom-right corner.
   */
  public static void updateSubmatrix(int[][] diff, int r1, int c1, int r2, int c2, int val) {
    diff[r1][c1] += val;
    diff[r1][c2 + 1] -= val;
    diff[r2 + 1][c1] -= val;
    diff[r2 + 1][c2 + 1] += val;
  }

  /**
   * Reconstructs the original matrix from the difference array after updates.
   */
  public static int[][] reconstructMatrix(int[][] diff, int n, int m) {
    int[][] result = new int[n][m];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        if (i == 0 && j == 0) {
          result[i][j] = diff[i][j];
        } else if (i == 0) {
          result[i][j] = result[i][j - 1] + diff[i][j];
        } else if (j == 0) {
          result[i][j] = result[i - 1][j] + diff[i][j];
        } else {
          result[i][j] = result[i - 1][j] + result[i][j - 1] - result[i - 1][j - 1] + diff[i][j];
        }
      }
    }
    return result;
  }

  // ----------------------------------------------------------- //
  /**
   * Rotate a Matrix
   * Matrix rotation involves transforming a matrix by rotating its elements
   * around the center. For a 90-degree clockwise rotation, this involves first
   * transposing the matrix (swapping rows with columns) and then reversing each
   * row. For other angles, similar transformations or multiple 90-degree
   * rotations can be used.
   *
   * Algorithm:
   * Rotate a Matrix Algorithm:
   * - Rotates a square matrix by the specified angle (90, 180, 270, 360 degrees).
   * - Uses combinations of transpose and row/column reversals.
   */
  public static int[][] rotateMatrix(int[][] matrix, int degrees) {
    int n = matrix.length;
    int[][] result = new int[n][n];

    // Normalize degrees to 0, 90, 180, or 270
    degrees = degrees % 360;
    if (degrees < 0)
      degrees += 360;

    // Copy the matrix for any operation
    for (int i = 0; i < n; i++) {
      result[i] = matrix[i].clone();
    }

    // Perform rotation based on degrees
    switch (degrees) {
      case 0:
        return result; // No rotation
      case 90:
        result = transpose(matrix);
        reverseRows(result);
        return result;
      case 180:
        reverseRows(result);
        reverseColumns(result);
        return result;
      case 270:
        result = transpose(matrix);
        reverseColumns(result);
        return result;
      default:
        return matrix; // Invalid degree, return original
    }
  }

  private static int[][] transpose(int[][] matrix) {
    int n = matrix.length;
    int[][] result = new int[n][n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        result[j][i] = matrix[i][j];
      }
    }
    return result;
  }

  private static void reverseRows(int[][] matrix) {
    int n = matrix.length;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n / 2; j++) {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[i][n - j - 1];
        matrix[i][n - j - 1] = temp;
      }
    }
  }

  private static void reverseColumns(int[][] matrix) {
    int n = matrix.length;

    for (int i = 0; i < n / 2; i++) {
      for (int j = 0; j < n; j++) {
        int temp = matrix[i][j];
        matrix[i][j] = matrix[n - i - 1][j];
        matrix[n - i - 1][j] = temp;
      }
    }
  }

  // ----------------------------------------------------------- //
  /*
   * Maximum Sum Matrix (Kadane's Algorithm)
   * Kadane's algorithm, originally for 1D arrays, can be extended to find the
   * maximum sum submatrix in a 2D array. This involves applying Kadane's
   * algorithm for all possible column ranges and finding the maximum sum.
   */

  /**
   * Maximum Sum Matrix Algorithm (Kadane's algorithm extension):
   * - Finds the submatrix with the maximum sum.
   * - Extends Kadane's algorithm to 2D by fixing the left and right boundaries.
   * - Returns the sum and coordinates of the maximum submatrix.
   */
  public static int[] maxSumSubmatrix(int[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;

    int maxSum = Integer.MIN_VALUE;
    int[] result = new int[5]; // [maxSum, top, left, bottom, right]

    // Fix the left boundary
    for (int left = 0; left < cols; left++) {
      int[] temp = new int[rows];

      // Fix the right boundary
      for (int right = left; right < cols; right++) {
        // Add the values in the current column to temp array
        for (int i = 0; i < rows; i++) {
          temp[i] += matrix[i][right];
        }

        // Apply Kadane's algorithm on temp array
        int currentSum = 0;
        int maxSumSoFar = Integer.MIN_VALUE;
        int start = 0;
        int end = -1;

        for (int i = 0; i < rows; i++) {
          currentSum += temp[i];

          if (currentSum > maxSumSoFar) {
            maxSumSoFar = currentSum;
            end = i;
          }

          if (currentSum < 0) {
            currentSum = 0;
            start = i + 1;
          }
        }

        if (maxSumSoFar > maxSum) {
          maxSum = maxSumSoFar;
          result[0] = maxSum;
          result[1] = start; // top
          result[2] = left; // left
          result[3] = end; // bottom
          result[4] = right; // right
        }
      }
    }

    return result;
  }

  // ----------------------------------------------------------- //

  /*
   * Flood Fill Algorithm
   * Flood fill is used to fill connected regions in a matrix. Starting from a
   * cell, it replaces the color of that cell and all its adjacent cells that have
   * the same original color. This can be implemented using either BFS or DFS.
   */
  /**
   * Flood Fill Algorithm:
   * - Changes connected cells of the same color to a new color.
   * - Implements both BFS and DFS approaches.
   */
  public static int[][] floodFillBFS(int[][] image, int sr, int sc, int newColor) {
    int rows = image.length;
    int cols = image[0].length;
    int startColor = image[sr][sc];

    if (startColor == newColor)
      return image;

    Queue<int[]> queue = new LinkedList<>();
    queue.add(new int[] { sr, sc });
    image[sr][sc] = newColor;

    int[] dx = { -1, 1, 0, 0 }; // up, down, left, right
    int[] dy = { 0, 0, -1, 1 };

    while (!queue.isEmpty()) {
      int[] cell = queue.poll();
      int r = cell[0];
      int c = cell[1];

      for (int i = 0; i < 4; i++) {
        int nr = r + dx[i];
        int nc = c + dy[i];

        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && image[nr][nc] == startColor) {
          image[nr][nc] = newColor;
          queue.add(new int[] { nr, nc });
        }
      }
    }

    return image;
  }

  public static int[][] floodFillDFS(int[][] image, int sr, int sc, int newColor) {
    int startColor = image[sr][sc];
    if (startColor == newColor)
      return image;

    dfs(image, sr, sc, startColor, newColor);
    return image;
  }

  private static void dfs(int[][] image, int r, int c, int startColor, int newColor) {
    int rows = image.length;
    int cols = image[0].length;

    if (r < 0 || r >= rows || c < 0 || c >= cols || image[r][c] != startColor) {
      return;
    }

    image[r][c] = newColor;

    // Recursively fill adjacent cells
    dfs(image, r + 1, c, startColor, newColor); // down
    dfs(image, r - 1, c, startColor, newColor); // up
    dfs(image, r, c + 1, startColor, newColor); // right
    dfs(image, r, c - 1, startColor, newColor); // left
  }

  // ----------------------------------------------------------- //

  /*
   * Shortest Path in a Matrix (Dijkstra's Algorithm)
   * Dijkstra's algorithm finds the shortest path from a source cell to all other
   * cells in a weighted graph. In a matrix, each cell can be considered a node
   * with edges to adjacent cells.
   */
  /**
   * Shortest Path in a Matrix Algorithm (Dijkstra's):
   * - Finds the shortest path from source to destination in a weighted matrix.
   * - Each cell represents a node with weight as the value in the matrix.
   * - Returns the shortest distance or -1 if no path exists.
   */
  public static int shortestPath(int[][] grid, int[] start, int[] destination) {
    int rows = grid.length;
    int cols = grid[0].length;

    // Directions: up, right, down, left
    int[] dx = { -1, 0, 1, 0 };
    int[] dy = { 0, 1, 0, -1 };

    // Distance matrix
    int[][] distance = new int[rows][cols];
    for (int[] row : distance) {
      Arrays.fill(row, Integer.MAX_VALUE);
    }

    // Priority queue to store cells to be visited
    // [row, column, distance]
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);

    // Start with the source cell
    pq.offer(new int[] { start[0], start[1], 0 });
    distance[start[0]][start[1]] = 0;

    while (!pq.isEmpty()) {
      int[] current = pq.poll();
      int r = current[0];
      int c = current[1];
      int dist = current[2];

      // If we've reached the destination
      if (r == destination[0] && c == destination[1]) {
        return dist;
      }

      // If this path is longer than a previously found path
      if (dist > distance[r][c]) {
        continue;
      }

      // Check all four directions
      for (int i = 0; i < 4; i++) {
        int nr = r + dx[i];
        int nc = c + dy[i];

        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] != -1) {
          int newDist = dist + grid[nr][nc]; // Add the weight of the new cell

          if (newDist < distance[nr][nc]) {
            distance[nr][nc] = newDist;
            pq.offer(new int[] { nr, nc, newDist });
          }
        }
      }
    }

    // If we can't reach the destination
    return -1;
  }

  // ----------------------------------------------------------- //
  public static void main(String[] args) {
    System.out.println("Matrix Operations Demonstration");
    System.out.println("===============================");

    // Define sample matrices
    int[][] matrixA = {
        { 1, 2, 3 },
        { 4, 5, 6 },
        { 7, 8, 9 }
    };

    int[][] matrixB = {
        { 9, 8, 7 },
        { 6, 5, 4 },
        { 3, 2, 1 }
    };

    // Matrix Multiplication
    System.out.println("\n1. Matrix Multiplication");
    System.out.println("Matrix A:");
    printMatrix(matrixA);
    System.out.println("Matrix B:");
    printMatrix(matrixB);
    System.out.println("A * B:");
    int[][] resultMult = multiplyMatrices(matrixA, matrixB);
    printMatrix(resultMult);

    // Determinant
    System.out.println("\n2. Determinant");
    System.out.println("Det(A) = " + determinant(matrixA, matrixA.length));

    // Matrix Exponentiation
    System.out.println("\n3. Matrix Exponentiation");
    System.out.println("A^2:");
    int[][] resultExp = matrixExponentiation(matrixA, 2);
    printMatrix(resultExp);

    // Prefix Sum
    System.out.println("\n4. Prefix Sum");
    System.out.println("Original Matrix A:");
    printMatrix(matrixA);
    System.out.println("Prefix Sum Matrix:");
    int[][] prefixSumMatrix = prefixSum(matrixA);
    printMatrix(prefixSumMatrix);

    // Difference Array
    System.out.println("\n5. Difference Array");
    System.out.println("Original Matrix A:");
    printMatrix(matrixA);
    System.out.println("Difference Array:");
    int[][] diffArray = createDifferenceArray(matrixA);
    printMatrix(diffArray);
    System.out.println("Update submatrix (0,0) to (1,1) with value 5:");
    updateSubmatrix(diffArray, 0, 0, 1, 1, 5);
    System.out.println("Reconstructed Matrix after update:");
    int[][] reconstructed = reconstructMatrix(diffArray, matrixA.length, matrixA[0].length);
    printMatrix(reconstructed);

    // Matrix Rotation
    System.out.println("\n6. Matrix Rotation");
    System.out.println("Original Matrix A:");
    printMatrix(matrixA);
    System.out.println("Rotated 90 degrees:");
    int[][] rotated90 = rotateMatrix(matrixA, 90);
    printMatrix(rotated90);
    System.out.println("Rotated 180 degrees:");
    int[][] rotated180 = rotateMatrix(matrixA, 180);
    printMatrix(rotated180);

    // Maximum Sum Submatrix
    System.out.println("\n7. Maximum Sum Submatrix");
    int[][] kadaneMatrix = {
        { 1, 2, -1, -4, -20 },
        { -8, -3, 4, 2, 1 },
        { 3, 8, 10, 1, 3 },
        { -4, -1, 1, 7, -6 }
    };
    System.out.println("Matrix for Kadane's algorithm:");
    printMatrix(kadaneMatrix);
    int[] maxSumResult = maxSumSubmatrix(kadaneMatrix);
    System.out.println("Maximum sum: " + maxSumResult[0]);
    System.out.println("Submatrix coordinates: Top: " + maxSumResult[1] +
        ", Left: " + maxSumResult[2] +
        ", Bottom: " + maxSumResult[3] +
        ", Right: " + maxSumResult[4]);

    // Flood Fill
    System.out.println("\n8. Flood Fill");
    int[][] image = {
        { 1, 1, 1 },
        { 1, 1, 0 },
        { 1, 0, 1 }
    };
    System.out.println("Original Image:");
    printMatrix(image);
    System.out.println("After BFS Flood Fill (starting at (1,1), new color 2):");
    int[][] filledBFS = floodFillBFS(deepCopy(image), 1, 1, 2);
    printMatrix(filledBFS);
    System.out.println("After DFS Flood Fill (starting at (1,1), new color 3):");
    int[][] filledDFS = floodFillDFS(deepCopy(image), 1, 1, 3);
    printMatrix(filledDFS);

    // Shortest Path
    System.out.println("\n9. Shortest Path");
    int[][] grid = {
        { 1, 3, 1 },
        { 1, 5, 1 },
        { 4, 2, 1 }
    };
    System.out.println("Grid (values represent weights):");
    printMatrix(grid);
    int shortestDist = shortestPath(grid, new int[] { 0, 0 }, new int[] { 2, 2 });
    System.out.println("Shortest path from (0,0) to (2,2): " + shortestDist);
  }

  // Helper method to print a matrix
  private static void printMatrix(int[][] matrix) {
    for (int[] row : matrix) {
      System.out.print("[");
      for (int i = 0; i < row.length; i++) {
        System.out.print(row[i]);
        if (i < row.length - 1) {
          System.out.print(", ");
        }
      }
      System.out.println("]");
    }
  }

  // Helper method to create a deep copy of a matrix
  private static int[][] deepCopy(int[][] original) {
    int[][] copy = new int[original.length][];
    for (int i = 0; i < original.length; i++) {
      copy[i] = original[i].clone();
    }
    return copy;
  }
}
