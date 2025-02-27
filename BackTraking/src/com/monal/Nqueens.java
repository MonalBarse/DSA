package com.monal;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Nqueens {
  private int boardSize;
  private List<List<String>> solutions;
  private String[] queenPositions;
  private String[] currentBoard;

  public List<List<String>> solveNQueens(int n) {
    // Using AbstractList for lazy evaluation
    // Solutions are only calculated when needed
    // This improves performance when the caller only needs a subset of solutions
    return new AbstractList<List<String>>() {
      // memoization
      private List<List<String>> cachedSolutions = null;

      // Overriding get() and size() methods of AbstractList
      // so when the main functions calls get() or size() it will call these methods
      @Override
      public List<String> get(int index) {
        if (cachedSolutions == null)
          cachedSolutions = calculateAllSolutions(n);
        return cachedSolutions.get(index);
      }

      @Override
      public int size() {
        if (cachedSolutions == null)
          cachedSolutions = calculateAllSolutions(n);
        return cachedSolutions.size();
      }
    };
  }

  public List<List<String>> calculateAllSolutions(int n) {
    this.boardSize = n;
    this.solutions = new ArrayList<>();
    this.queenPositions = new String[n];
    this.currentBoard = new String[n];

    char[] emptyRow = new char[n];
    Arrays.fill(emptyRow, '.');

    // Pre-compute all possible row configurations with a queen
    // This is an optimization to avoid string manipulations during backtracking
    for (int i = 0; i < n; i++) {
      emptyRow[i] = 'Q';
      queenPositions[i] = String.valueOf(emptyRow);
      emptyRow[i] = '.';
    }

    // Start the backtracking algorithm from the first row with empty attack masks
    placeQueensInRow(0, 0L, 0L, 0L);
    return solutions;
  }

  public static void main(String[] args) {
    // Test with different board sizes
    int[] testSizes = { 4 };

    for (int n : testSizes) {
      System.out.println("Solving N-Queens for n = " + n);

      Nqueens solver = new Nqueens();
      List<List<String>> solutions = solver.solveNQueens(n);

      System.out.println("Found " + solutions.size() + " solutions:");

      // Print first 3 solutions at most to avoid too much output
      int solutionsToPrint = Math.min(solutions.size(), 3);
      for (int i = 0; i < solutionsToPrint; i++) {
        System.out.println("\nSolution " + (i + 1) + ":");
        printBoard(solutions.get(i));
      }

      if (solutions.size() > 3) {
        System.out.println("\n... and " + (solutions.size() - 3) + " more solutions");
      }

      System.out.println("\n---------------------------------\n");
    }
  }

  private static void printBoard(List<String> board) {
    for (String row : board) {
      System.out.println(row);
    }
  }

  /**
   * Recursive backtracking function that uses bit manipulation for efficiency
   * - Each position's "under attack" status is tracked using bits
   * - The three attack masks represent:
   * 1. leftDiagonalMask: Queens attacking diagonally from top-left
   * 2. columnMask: Queens attacking vertically
   * 3. rightDiagonalMask: Queens attacking diagonally from top-right
   *
   * The bit shift operations (<<1 and >>1) propagate these attack patterns down
   * the board
   * for the next row, which is much faster than checking each position manually.
   */
  private void placeQueensInRow(int row, long leftDiagonalMask, long columnMask, long rightDiagonalMask) {
    // Combine all three attack masks to get all attacked positions in current row
    long attackedPositions = leftDiagonalMask | columnMask | rightDiagonalMask;

    for (int col = 0; col < boardSize; col++) {
      long positionBit = 1L << col; // Binary representation of current column position

      // If position is not under attack (bit is 0)
      if ((attackedPositions & positionBit) == 0L) {
        // Place queen at this position
        currentBoard[row] = queenPositions[col];

        if (row == boardSize - 1) {
          // Base case: found complete solution (all queens placed)
          solutions.add(new ArrayList<>(Arrays.asList(currentBoard)));
        } else {
          // Recursive case: try placing queens in next row
          // For next row, update attack patterns:
          // 1. Left diagonal mask: shift left to move attack pattern down+right
          // 2. Column mask: keep columns the same
          // 3. Right diagonal mask: shift right to move attack pattern down+left
          placeQueensInRow(
              row + 1,
              (leftDiagonalMask | positionBit) << 1,
              columnMask | positionBit,
              (rightDiagonalMask | positionBit) >> 1);
          // Backtracking happens implicitly: when we return from recursive call,
          // we try the next column position without keeping previous placement
        }
      }
    }
  }
}

/*
 * package com.monal;
 *
 * import java.util.ArrayList;
 * import java.util.List;
 *
 * // 1. N-Queens Problem -
 * / *
 * The N Queen is the problem of placing N chess queens on an N×N chessboard so
 * that no two queens
 * attack each other, for which we have the following constraints:
 * 1. No two queens share the same row.
 * 2. No two queens share the same column.
 * 3. No two queens share the same diagonal.
 *
 * Given an integer n, return all distinct solutions to the n-queens puzzle. You
 * may return the answer in any order.
 * Each solution contains a distinct board configuration of the n-queens'
 * placement, where 'Q' and '.' both indicate a queen and an empty space,
 * respectively.
 *
 * Examole 1:
 * Input: n = 4
 * Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 * Explanation: There exist two distinct solutions to the 4-queens puzzle as
 * shown above
 * Example 2:
 * Input: n = 1
 * Output: [["Q"]]
 * /
 *
 * public class Nqueens {
 * public static void main(String[] args) {
 * // 1. N-Queens Problem -
 * System.out.println("N-Queens Problem");
 *
 * List<List<String>> results = solveNQueens(4);
 * for (List<String> result : results) {
 * for (String row : result) {
 * System.out.println(row);
 * }
 * System.out.println();
 * }
 * }
 *
 * public static List<List<String>> solveNQueens(int n) {
 * List<List<String>> results = new ArrayList<>();
 * boolean[] cols = new boolean[n];
 * boolean[] diag1 = new boolean[2 * n - 1];
 * boolean[] diag2 = new boolean[2 * n - 1];
 * int[] board = new int[n];
 * solve(0, n, board, cols, diag1, diag2, results);
 * return results;
 * }
 *
 * private static void solve(
 * int row,
 * int n,
 * int[] board,
 * boolean[] cols,
 * boolean[] diag1,
 * boolean[] diag2,
 * List<List<String>> results) {
 * if (row == n) {
 * results.add(constructBoard(board, n));
 * return;
 * }
 * for (int col = 0; col < n; col++) {
 * int d1 = row - col + n - 1;
 * int d2 = row + col;
 * if (cols[col] || diag1[d1] || diag2[d2]) {
 * continue;
 * }
 * board[row] = col;
 * cols[col] = true;
 * diag1[d1] = true;
 * diag2[d2] = true;
 * solve(row + 1, n, board, cols, diag1, diag2, results);
 * cols[col] = false;
 * diag1[d1] = false;
 * diag2[d2] = false;
 * }
 * }
 *
 * private static List<String> constructBoard(int[] board, int n) {
 * List<String> result = new ArrayList<>();
 * for (int i : board) {
 * char[] row = new char[n];
 * for (int j = 0; j < n; j++) {
 * row[j] = (j == i) ? '♛' : '_';
 * }
 * result.add(new String(row));
 * }
 * return result;
 * }
 * }
 */
/*
 *
 * import java.util.*;
 * 
 * public class NQueens {
 * public static List<List<String>> solveNQueens(int n) {
 * List<List<String>> results = new ArrayList<>();
 * char[][] board = new char[n][n];
 * 
 * // Initialize board with '.'
 * for (char[] row : board) Arrays.fill(row, '.');
 * 
 * solve(0, board, results, n);
 * return results;
 * }
 * 
 * private static void solve(int row, char[][] board, List<List<String>>
 * results, int n) {
 * // Base case: If all queens are placed, store the board configuration
 * if (row == n) {
 * results.add(constructBoard(board));
 * return;
 * }
 * 
 * // Try placing the queen in each column
 * for (int col = 0; col < n; col++) {
 * if (isSafe(board, row, col, n)) {
 * board[row][col] = 'Q'; // Place the queen
 * solve(row + 1, board, results, n); // Recur for the next row
 * board[row][col] = '.'; // Backtrack (remove the queen)
 * }
 * }
 * }
 * 
 * private static boolean isSafe(char[][] board, int row, int col, int n) {
 * // Check column for another queen
 * for (int i = 0; i < row; i++) {
 * if (board[i][col] == 'Q') return false;
 * }
 * 
 * // Check left diagonal
 * for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
 * if (board[i][j] == 'Q') return false;
 * }
 * 
 * // Check right diagonal
 * for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
 * if (board[i][j] == 'Q') return false;
 * }
 * 
 * return true;
 * }
 * 
 * private static List<String> constructBoard(char[][] board) {
 * List<String> result = new ArrayList<>();
 * for (char[] row : board) {
 * result.add(new String(row));
 * }
 * return result;
 * }
 * 
 * public static void main(String[] args) {
 * List<List<String>> solutions = solveNQueens(4);
 * for (List<String> board : solutions) {
 * for (String row : board) {
 * System.out.println(row);
 * }
 * System.out.println();
 * }
 * }
 * }
 * 
 */
