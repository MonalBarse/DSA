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
    System.out.println("================================");
    System.out.println("2. Sudoko Solver -");

    // 2. Sudoko Solver -
    int[][] board = {
      {5, 3, 0, 0, 7, 0, 0, 0, 0},
      {6, 0, 0, 1, 9, 5, 0, 0, 0},
      {0, 9, 8, 0, 0, 0, 0, 6, 0},
      {8, 0, 0, 0, 6, 0, 0, 0, 3},
      {4, 0, 0, 8, 0, 3, 0, 0, 1},
      {7, 0, 0, 0, 2, 0, 0, 0, 6},
      {0, 6, 0, 0, 0, 0, 2, 8, 0},
      {0, 0, 0, 4, 1, 9, 0, 0, 5},
      {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };
    printBoard(board);

    int[][] solvedBoard = solveSudoku(board);
    printBoard(solvedBoard);
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

  // 2. Sudoko Solver ------------------------------------------------------------------
  public static int[][] solveSudoku(int[][] board) {
    if (sudokuUtil(board, 0, 0)) {
      return board;
    } else {
      return null; // No solution exists
    }
  }

  private static boolean sudokuUtil(int[][] board, int row, int col) {
    // If we have reached the end of the board
    if (row == board.length) {
      return true;
    }

    // Move to the next row if we are past the last column
    if (col == board.length) {
      return sudokuUtil(board, row + 1, 0);
    }

    // Assuming the empty cell is denoted by 0
    if (board[row][col] == 0) {
      for (int number = 1; number <= 9; number++) {
        if (isSafe(board, row, col, number)) {
          board[row][col] = number;
          if (sudokuUtil(board, row, col + 1)) {
            return true;
          }
          board[row][col] = 0; // Backtrack
        }
      }
      return false; // No valid number found, trigger backtracking
    } else {
      return sudokuUtil(board, row, col + 1);
    }
  }

  private static boolean isSafe(int[][] board, int row, int col, int number) {
    // Check the row and column for the number
    for (int i = 0; i < board.length; i++) {
      if (board[row][i] == number || board[i][col] == number) {
        return false;
      }
    }

    // Check the 3x3 box for the number
    int boxSize = (int) Math.sqrt(board.length);
    int boxRowStart = row - row % boxSize;
    int boxColStart = col - col % boxSize;

    for (int i = boxRowStart; i < boxRowStart + boxSize; i++) {
      for (int j = boxColStart; j < boxColStart + boxSize; j++) {
        if (board[i][j] == number) {
          return false;
        }
      }
    }
    return true;
  }

  public static void printBoard(int[][] board) {
    if (board == null) {
      System.out.println("No solution exists");
      return;
    }

    System.out.println("Sudoku Board:");
    for (int[] row : board) {
      for (int cell : row) {
        System.out.print(cell + " ");
      }
      System.out.println();
    }
  }
}
