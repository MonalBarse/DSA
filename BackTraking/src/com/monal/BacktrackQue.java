package com.monal;

import java.util.ArrayList;
import java.util.List;

public class BacktrackQue {
  public static void main(String[] args) {
    System.out.println("Backtracking Question Practice");
    System.out.println("==============================");
    // 1. N-Queens Problem -
    System.out.println("1. N-Queens Problem -");
    nQueens(4);
  }

  // 1. N-Queens Problem -
  /*
   * The N Queen is the problem of placing N chess queens on an N×N chessboard so that no two queens
   * attack each other, for which we have the following constraints:
   * 1. No two queens share the same row.
   * 2. No two queens share the same column.
   * 3. No two queens share the same diagonal.
   */

  static char[][] initializeBoard(int size) {
    char[][] board = new char[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        board[i][j] = '_';
      }
    }
    return board;
  }

  // Each time we find a valid solution, we need to store a snapshot of the board at that
  // moment. The copyBoard function creates a deep copy of the current board state, ensuring
  // that each solution in our list is a distinct configuration.
  static char[][] copyBoard(char[][] board) {
    char[][] copy = new char[board.length][board.length];
    for (int i = 0; i < board.length; i++) {
      System.arraycopy(board[i], 0, copy[i], 0, board.length);
      // arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
    }
    return copy;
  }

  static void nQueens(int boardOSize) {
    char[][] board = initializeBoard(boardOSize);
    List<char[][]> solutions = new ArrayList<>();

    if (boardOSize >= 1 && boardOSize <= 3) {
      System.out.println("Solution Exists");
      board[0][0] = '♛';
      solutions.add(board);
    } else {
      nQueensUtil(board, 0, boardOSize, solutions);
    }

    if (solutions.isEmpty()) {
      System.out.println("No solutions exist");
    } else {
      System.out.println("Number of solutions: " + solutions.size());
      for (int i = 0; i < solutions.size(); i++) {
        System.out.println("Solution " + (i + 1) + ":");
        printBoard(solutions.get(i));
        System.out.println();
      }
    }
  }

  static void nQueensUtil(char[][] board, int col, int boardOSize, List<char[][]> solutions) {
    if (col >= boardOSize) {
      solutions.add(copyBoard(board));
      return;
    }

    for (int i = 0; i < boardOSize; i++) {
      if (isSafe(board, i, col, boardOSize)) {
        board[i][col] = '♛';
        nQueensUtil(board, col + 1, boardOSize, solutions);
        board[i][col] = '_';
      }
    }
  }

  static boolean isSafe(char[][] board, int row, int col, int boardOSize) {
    int i, j;
    // Check this row on the left side
    for (i = 0; i < col; i++) {
      if (board[row][i] == '♛') {
        return false;
      }
    }
    // Check the upper diagonal on the left side
    for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
      if (board[i][j] == '♛') {
        return false;
      }
    }
    // Check the lower diagonal on the left side
    for (i = row, j = col; i < boardOSize && j >= 0; i++, j--) {
      if (board[i][j] == '♛') {
        return false;
      }
    }
    return true;
    /* Example of a safe board configuration:

    \ * * *
    * \ * *
    - - p *
    * / * *

    */
  }

  static void printBoard(char[][] board) {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        System.out.print(board[i][j] + " ");
      }
      System.out.println();
    }
  }

  /*

  static void nQueens(int boardOSize) {
    int[][] board = new int[boardOSize][boardOSize];
    boolean[] cols = new boolean[boardOSize];
    boolean[] majDiags = new boolean[2 * boardOSize - 1];
    boolean[] minDiags = new boolean[2 * boardOSize - 1];

    if (nQueensUtil(board, 0, boardOSize, cols, majDiags, minDiags)) {
      System.out.println("Solution Exists");
      printBoard(board);
    } else {
      System.out.println("Solution does not exist");
    }
  }

  static boolean nQueensUtil(
      int[][] board,
      int col,
      int boardOSize,
      boolean[] cols,
      boolean[] majDiags,
      boolean[] minDiags) {
    if (col >= boardOSize) {
      return true;
    }
    for (int row = 0; row < boardOSize; row++) {
      if (isSafe(row, col, boardOSize, cols, majDiags, minDiags)) {
        board[row][col] = 1;
        cols[col] = true;
        majDiags[row - col + boardOSize - 1] = true;
        minDiags[row + col] = true;

        if (nQueensUtil(board, col + 1, boardOSize, cols, majDiags, minDiags)) {
          return true;
        }

        // Backtrack
        board[row][col] = 0;
        cols[col] = false;
        majDiags[row - col + boardOSize - 1] = false;
        minDiags[row + col] = false;
      }
    }
    return false;
  }

  static boolean isSafe(
      int row, int col, int boardOSize, boolean[] cols, boolean[] majDiags, boolean[] minDiags) {
    return !cols[col] && !majDiags[row - col + boardOSize - 1] && !minDiags[row + col];
  }

  static void printBoard(int[][] board) {
    for (int[] row : board) {
      for (int cell : row) {
        System.out.print(cell + " ");
      }
      System.out.println();
    }
  }
  */
}
