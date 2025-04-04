
package com.monal.Recursion;

import java.util.*;
/*
Write a program to solve a Sudoku puzzle by filling the empty cells.

A sudoku solution must satisfy all of the following rules:
Each of the digits 1-9 must occur exactly once in each row.
Each of the digits 1-9 must occur exactly once in each column.
Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
The '.' character indicates empty cells.

Input: board =
[
  ["5","3",".",".","7",".",".",".","."],
  ["6",".",".","1","9","5",".",".","."],
  [".","9","8",".",".",".",".","6","."],
  ["8",".",".",".","6",".",".",".","3"],
  ["4",".",".","8",".","3",".",".","1"],
  ["7",".",".",".","2",".",".",".","6"],
  [".","6",".",".",".",".","2","8","."],
  [".",".",".","4","1","9",".",".","5"],
  [".",".",".",".","8",".",".","7","9"]
]
Output: [
  ["5","3","4","6","7","8","9","1","2"],
  ["6","7","2","1","9","5","3","4","8"],
  ["1","9","8","3","4","2","5","6","7"],
  ["8","5","9","7","6","1","4","2","3"],
  ["4","2","6","8","5","3","7","9","1"],
  ["7","1","3","9","2","4","8","5","6"],
  ["9","6","1","5","3","7","2","8","4"],
  ["2","8","7","4","1","9","6","3","5"],
  ["3","4","5","2","8","6","1","7","9"]
]

*/

/*
The Sudoku solver:
We will use backtracking to fill empty cells while ensuring all Sudoku rules are satisfied.
Steps:
  Find the First Empty Cell (.): Start from the top-left and look for an empty cell.
  Try Placing a Number (1-9):
    Check if it is valid (i.e., not in the same row, column, or 3x3 sub-grid).
    Recursively Solve the Rest of the Board:
      If successful, return true (board is solved).
      If placing a number leads to an invalid board, backtrack by removing it and trying the next number.

Base Case:
If all cells are filled, return true (solution found).
*/
public class P011 {
  /**
   * Solution class implementing the Sudoku solver
   */
  class Solution1 {
    public void solveSudoku(char[][] board) {
      solveBacktrack(board);
    }

    /**
     * @param board The current state of the Sudoku board
     * @return true if a solution is found, false otherwise
     */
    private boolean solveBacktrack(char[][] board) {
      // Iterate through each cell in the board
      for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
          // If we find an empty cell
          if (board[row][col] == '.') {
            // Try placing each digit 1-9
            for (char num = '1'; num <= '9'; num++) {
              // Check if placing this digit is valid
              if (isValidPlacement(board, row, col, num)) {
                // Place the digit
                board[row][col] = num;

                // Recursively try to solve the rest of the board
                if (solveBacktrack(board)) {
                  return true; // Solution found
                }

                // If placing this digit doesn't lead to a solution,
                // backtrack by removing it
                board[row][col] = '.';
              }
            }
            // If no digit can be placed, backtrack
            return false;
          }
        }
      }
      // If we've gone through the entire board without finding empty cells,
      // the Sudoku is solved
      return true;
    }

    /**
     * Checks if placing a digit in a specific position is valid
     *
     * @param row   The row index (0-8)
     * @param col   The column index (0-8)
     * @param num   The digit to place ('1'-'9')
     * @param board The current state of the board
     * @return true if placement is valid, false otherwise
     */
    private boolean isValidPlacement(char[][] board, int row, int col, char num) {
      // Check row constraint
      for (int i = 0; i < 9; i++) {
        if (board[row][i] == num) {
          return false; // Digit already exists in this row
        }
      }

      // Check column constraint
      for (int i = 0; i < 9; i++) {
        if (board[i][col] == num) {
          return false; // Digit already exists in this column
        }
      }

      // Check 3x3 subgrid constraint
      // Find the starting indices of the 3x3 subgrid
      int subgridRowStart = (row / 3) * 3;
      int subgridColStart = (col / 3) * 3;

      // Check each cell in the 3x3 subgrid
      for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
          if (board[subgridRowStart + i][subgridColStart + j] == num) {
            return false; // Digit already exists in this 3x3 subgrid
          }
        }
      }

      // If all constraints are satisfied, the placement is valid
      return true;
    }
  }

  /**
   * Optimized Sudoku Solver using Most Constrained Variable (MRV) heuristic
   * This implementation is faster because it prioritizes cells with the fewest
   * valid options
   */
  class Solution2 {
    // Flag to track if we've found a solution
    boolean solved = false;

    /**
     * @param board The 9x9 Sudoku board with empty cells represented as '.'
     */
    public void solveSudoku(char[][] board) {
      // Create a temporary board to store the solution
      char[][] temp = new char[9][9];

      // Start the recursive backtracking process
      solve(board, temp);

      // Copy the solution back to the original board
      for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
          board[i][j] = temp[i][j];
        }
      }
    }

    /**
     * Find the empty cell with the fewest valid number options (MRV heuristic)
     * This is the key optimization that makes this solution faster
     *
     * @param board The current state of the board
     * @return Array with the row and column of the chosen cell, or {-1, -1} if no
     *         empty cells
     */
    public static int[] getEmptyCell(char[][] board) {
      int[] cell = { -1, -1 };
      int minOptions = 10; // Start with a value larger than possible (9)

      // Iterate through the board to find empty cells
      for (int i = 0; i < 9; i++) {
        for (int j = 0; j < 9; j++) {
          if (board[i][j] == '.') {
            // Count how many valid numbers can be placed in this cell
            int count = countValidNumbers(board, i, j);

            // If this cell has fewer options than our current minimum
            if (minOptions > count) {
              minOptions = count;
              cell[0] = i;
              cell[1] = j;
            }
          }
        }
      }
      return cell;
    }

    /**
     * Count the number of valid digits that can be placed in the given cell
     *
     * @param board The current state of the board
     * @param row   The row index of the cell
     * @param col   The column index of the cell
     * @return Number of valid digits (1-9) that can be placed in the cell
     */
    public static int countValidNumbers(char[][] board, int row, int col) {
      // Track which numbers are already used in row, column, or subgrid
      boolean[] used = new boolean[10]; // 0 index is ignored

      // Check row and column constraints
      for (int i = 0; i < 9; i++) {
        // Mark digits in the same row as used
        if (board[row][i] != '.') {
          used[board[row][i] - '0'] = true;
        }

        // Mark digits in the same column as used
        if (board[i][col] != '.') {
          used[board[i][col] - '0'] = true;
        }
      }

      // Check 3x3 subgrid constraint
      int boxRow = (row / 3) * 3;
      int boxCol = (col / 3) * 3;
      for (int i = boxRow; i < boxRow + 3; i++) {
        for (int j = boxCol; j < boxCol + 3; j++) {
          if (board[i][j] != '.') {
            used[board[i][j] - '0'] = true;
          }
        }
      }

      // Count how many digits are still valid
      int count = 0;
      for (int i = 1; i <= 9; i++) {
        if (!used[i])
          count++;
      }

      return count;
    }

    /**
     * Recursive backtracking method to solve the Sudoku
     *
     * @param board The current state of the board
     * @param temp  The board to store the solution when found
     */
    private void solve(char[][] board, char[][] temp) {
      // If solution already found, stop recursion
      if (solved == true) {
        return;
      }

      // Get the most constrained empty cell (fewest valid options)
      int[] cell = getEmptyCell(board);
      int freeRow = cell[0];
      int freeCol = cell[1];

      // If no empty cells found and not solved yet, we've found a solution
      if (freeRow == -1 && freeCol == -1 && solved == false) {
        // Copy the solved board to the temp board
        for (int i = 0; i < 9; i++) {
          for (int j = 0; j < 9; j++) {
            temp[i][j] = board[i][j];
          }
        }
        solved = true;
        return;
      }

      // Try different numbers in the selected cell
      for (char num = '1'; num <= '9'; num++) {
        if (isValidPlacement(board, freeRow, freeCol, num)) {
          // Place the number and continue solving
          board[freeRow][freeCol] = num;
          solve(board, temp);

          // Backtrack if needed
          board[freeRow][freeCol] = '.';
        }
      }
    }

    /**
     * Check if placing a digit in a specific position is valid
     *
     * @param board The current state of the board
     * @param row   The row index
     * @param col   The column index
     * @param num   The digit to place
     * @return true if the placement is valid, false otherwise
     */
    private boolean isValidPlacement(char[][] board, int row, int col, char num) {
      // Check row and column constraints
      for (int i = 0; i < board.length; i++) {
        // Check if number exists in the same row
        if (board[i][col] == num) {
          return false;
        }

        // Check if number exists in the same column
        if (board[row][i] == num) {
          return false;
        }
      }

      // Check 3x3 subgrid constraint
      int boxRow = (row / 3) * 3;
      int boxCol = (col / 3) * 3;
      for (int i = boxRow; i < boxRow + 3; i++) {
        for (int j = boxCol; j < boxCol + 3; j++) {
          if (board[i][j] == num) {
            return false;
          }
        }
      }

      // Placement is valid
      return true;
    }
  }

  // ----------------------------------------------------------------------- //
  public static void main(String[] args) {
    char[][] board = {
        { '5', '3', '.', '.', '7', '.', '.', '.', '.' },
        { '6', '.', '.', '1', '9', '5', '.', '.', '.' },
        { '.', '9', '8', '.', '.', '.', '.', '6', '.' },
        { '8', '.', '.', '.', '6', '.', '.', '.', '3' },
        { '4', '.', '.', '8', '.', '3', '.', '.', '1' },
        { '7', '.', '.', '.', '2', '.', '.', '.', '6' },
        { '.', '6', '.', '.', '.', '.', '2', '8', '.' },
        { '.', '.', '.', '4', '1', '9', '.', '.', '5' },
        { '.', '.', '.', '.', '8', '.', '.', '7', '9' }
    };

    // Create an instance of P011 to access the Solution inner class
    P011 p011 = new P011();
    Solution1 solution = p011.new Solution1();

    // Solve the Sudoku
    solution.solveSudoku(board);

    // Print the solved Sudoku
    System.out.println("Solved Sudoku:");
    for (char[] row : board) {
      System.out.println(Arrays.toString(row));
    }
  }
}