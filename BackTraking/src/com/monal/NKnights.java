package com.monal;

/*
 *
 * We have been given n knights and n*n chess board. We ought to place all the knights on the chess board
 * such that no two knights attack each other. The knights attack each other if they are placed in L shape
 * from each other. We have to find the number of ways to place the knight on the chess board and also print
 * all the possible ways to place the knights.
 */

import java.util.ArrayList;
import java.util.List;

public class NKnights {

  public static void main(String[] args) {
    int n = 2; // Example board size, can be changed to any value
    List<char[][]> solutions = nKnights(n);
    System.out.println(
        "Number of ways to place "
            + n
            + " knights on a "
            + n
            + "x"
            + n
            + " board: "
            + solutions.size());
    printSolutions(solutions);
  }

  static char[][] initializeBoard(int size) {
    char[][] board = new char[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        board[i][j] = '_';
      }
    }
    return board;
  }

  static char[][] copyBoard(char[][] board) {
    char[][] copy = new char[board.length][board.length];
    for (int i = 0; i < board.length; i++) {
      System.arraycopy(board[i], 0, copy[i], 0, board.length);
    }
    return copy;
  }

  static List<char[][]> nKnights(int boardSize) {
    char[][] board = initializeBoard(boardSize);
    List<char[][]> solutions = new ArrayList<>();
    nKnightsUtil(board, 0, 0, boardSize, solutions);
    return solutions;
  }

  static void nKnightsUtil(
      char[][] board, int row, int col, int knightsRemaining, List<char[][]> solutions) {
    // Base case: all knights are placed
    if (knightsRemaining == 0) {
      solutions.add(copyBoard(board));
      return;
    }

    // Out of bounds check
    if (row >= board.length) {
      return;
    }

    // Move to next row if we are past the last column
    if (col >= board.length) {
      nKnightsUtil(board, row + 1, 0, knightsRemaining, solutions);
      return;
    }

    // Try placing knight at (row, col)
    if (safeToPlaceKnight(board, row, col)) {
      board[row][col] = '♞';
      nKnightsUtil(board, row, col + 1, knightsRemaining - 1, solutions);
      board[row][col] = '_';
    }

    // Try without placing knight at (row, col)
    nKnightsUtil(board, row, col + 1, knightsRemaining, solutions);
  }

  static boolean safeToPlaceKnight(char[][] board, int row, int col) {
    // For a knight to be placed safely, it should not have any other knight in its 8 possible
    // positions which are (row-2, col-1), (row-2, col+1), (row-1, col-2), (row-1, col+2),
    // (row+1, col-2), (row+1, col+2), (row+2, col-1), (row+2, col+1)

    int[][] directions = {
      {-2, -1}, {-2, +1}, {-1, -2}, {-1, +2},
      {+1, -2}, {+1, +2}, {+2, -1}, {+2, +1}
    };

    for (int[] direction : directions) {
      int newRow = row + direction[0];
      int newCol = col + direction[1];
      if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board.length) {
        if (board[newRow][newCol] == '♞') {
          return false;
        }
      }
    }
    return true;
  }

  static void printBoard(char[][] board) {
    for (char[] row : board) {
      for (char cell : row) {
        System.out.print(cell + " ");
      }
      System.out.println();
    }
  }

  static void printSolutions(List<char[][]> solutions) {
    for (char[][] solution : solutions) {
      printBoard(solution);
      System.out.println();
    }
  }
}
