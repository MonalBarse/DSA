package com.monal.Recursion;

import java.util.*;

/*
  The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
  Given an integer n, return all distinct solutions to the n-queens puzzle.
  You may return the answer in any order. Each solution contains a distinct board
  configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.

  Example 1:
    Input: n = 4
    Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
    Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above
  Example 2:
    Input: n = 1
    Output: [["Q"]]
  Constraints:
    1 <= n <= 9
 */
public class P008 {

  class Solution {
    public List<List<String>> solveNQueens(int n) {
      List<List<String>> res = new ArrayList<>();
      char[][] board = new char[n][n];
      for (int i = 0; i < n; i++) {
        Arrays.fill(board[i], '.');
      }
      put_queens(board, res, 0, n);
      return res;
    }

    private void put_queens(char[][] board, List<List<String>> res, int row, int n) {
      // Base case : if we reach at the end row length then we have found one possible
      if (row == n) {
        res.add(constructBoard(board));
        return;
      }

      // in this 'row' for every column check if its okay to put queen
      for (int col = 0; col < n; col++) {
        if (isValid(row, col, board)) {
          board[row][col] = 'Q';
          // further put for next row recursively after this (dfs)
          put_queens(board, res, row + 1, n);

          // then backtract
          board[row][col] = '.';
        }
      }
    }

    private boolean isValid(int row, int col, char[][] board) {
      int n = board.length;
      // Check vertical column
      for (int i = 0; i < row; i++)
        if (board[i][col] == 'Q')
          return false;

      // Check upper-left diagonal
      for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--)
        if (board[i][j] == 'Q')
          return false;

      // Check upper-right diagonal
      for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++)
        if (board[i][j] == 'Q')
          return false;

      return true;

    }

    private List<String> constructBoard(char[][] board) {
      // String str = new String(charArray);
      List<String> res = new ArrayList<>();
      for (char[] charArray : board) {
        String str = new String(charArray);
        res.add(str);
      }
      return res;

    }
  }
}
