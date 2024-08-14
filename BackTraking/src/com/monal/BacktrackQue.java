package com.monal;

public class BacktrackQue {
  public static void main(String[] args) {
    System.out.println("Backtracking Question Practice");
    System.out.println("==============================");
    // 1. N-Queens Problem -
    System.out.println("1. N-Queens Problem -");
    nQueens(5);
  }

  // 1. N-Queens Problem -
  /*
   * The N Queen is the problem of placing N chess queens on an N×N chessboard so that no two queens
   * attack each other, for which we have the following constraints:
   * 1. No two queens share the same row.
   * 2. No two queens share the same column.
   * 3. No two queens share the same diagonal.
   */

  static void nQueens(int boardOSize) {
    int[][] board = new int[boardOSize][boardOSize];
    if (nQueensUtil(board, 0, boardOSize)) {
      System.out.println("Solution Exists");
      printBoard(board);
    } else {
      System.out.println("Solution does not exist");
    }
  }

  static boolean nQueensUtil(int[][] board, int col, int boardOSize) {
    if (col >= boardOSize) {
      return true;
    }
    for (int i = 0; i < boardOSize; i++) {
      if (isSafe(board, i, col, boardOSize)) {
        board[i][col] = 1;
        if (nQueensUtil(board, col + 1, boardOSize)) {
          return true;
        }
        board[i][col] = 0;
      }
    }
    return false;
  }

  static boolean isSafe(int[][] board, int row, int col, int boardOSize) {
    int i, j;
    for (i = 0; i < col; i++) {
      if (board[row][i] == 1) {
        return false;
      }
    }
    for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
      if (board[i][j] == 1) {
        return false;
      }
    }
    for (i = row, j = col; i < boardOSize && j >= 0; i++, j--) {
      if (board[i][j] == 1) {
        return false;
      }
    }
    return true;
  }

  static void printBoard(int[][] board) {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        System.out.print(board[i][j] + " ");
      }
      System.out.println();
    }
  }
}
