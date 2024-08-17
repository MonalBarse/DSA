package com.monal;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Nqueens {
  public static void main(String[] args) {
    System.out.println("N-Queens Problem");
    System.out.println("==============================");
    List<List<String>> results = new Nqueens().solveNQueens(4);
    for (List<String> result : results) {
      for (String row : result) {
        System.out.println(row);
      }
      System.out.println();
    }
  }

  private int n; // board size
  private List<List<String>> builder; // for storing the results
  private String[] template;
  private String[] board; // for storing the board

  // solveNQueens is a method that uses the AbstractList class to create a list of lists of strings
  public List<List<String>> solveNQueens(int n) {

    // An abstract list is a list that is not backed by a data store. It is a list that is generated
    // on the fly.
    return new AbstractList<List<String>>() {
      private List<List<String>> cache = null; // first we set the cache to null

      @Override // then we override the get method to our own implementation
      public List<String> get(int index) {
        if (cache == null) cache = solveNQueens1(n);
        return cache.get(index);
      } // our implementation of the get method is that if the cache is null we set the cache to the

      // result of the solveNQueens1 method and then we return the cache at the index

      @Override
      public int size() {
        if (cache == null) cache = solveNQueens1(n);
        return cache.size();
      }
      // we also override the size method to return the size of the cache
    };
  }

  public List<List<String>> solveNQueens1(int n) {
    this.n = n;
    this.builder = new ArrayList<>();
    this.template = new String[n];
    // template is an array of strings where each string represents a row configuration with a queen
    // at a specific position.
    this.board = new String[n];

    char[] line = new char[n]; // line will be for the horizontal line of the board
    Arrays.fill(line, '_'); // we fill the line with underscores

    for (int i = 0; i < n; i++) {
      line[i] = 'Q';
      template[i] = String.valueOf(line);
      line[i] = '_';
    }
    enumerateRow(0, 0L, 0L, 0L);
    return builder;
  }

  private void enumerateRow(int i, long shadow0, long shadow1, long shadow2) {
    long shadowSum = shadow0 | shadow1 | shadow2;
    for (int j = 0; j < n; j++) {
      long cell = 1L << j;
      if ((shadowSum & cell) == 0L) {
        board[i] = template[j];
        if (i == n - 1) builder.add(new ArrayList<>(Arrays.asList(board)));
        else enumerateRow(i + 1, (shadow0 | cell) << 1, shadow1 | cell, (shadow2 | cell) >> 1);
      }
    }
  }
}
/* package com.monal;

import java.util.ArrayList;
import java.util.List;

// 1. N-Queens Problem -
/ *
 * The N Queen is the problem of placing N chess queens on an N×N chessboard so that no two queens
 * attack each other, for which we have the following constraints:
 * 1. No two queens share the same row.
 * 2. No two queens share the same column.
 * 3. No two queens share the same diagonal.
 *
 * Given an integer n, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.
 * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.
 *
 * Examole 1:
 *  Input: n = 4
 *  Output: [[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
 *  Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above
 * Example 2:
 *  Input: n = 1
 *  Output: [["Q"]]
 * /

public class Nqueens {
  public static void main(String[] args) {
    // 1. N-Queens Problem -
    System.out.println("N-Queens Problem");

    List<List<String>> results = solveNQueens(4);
    for (List<String> result : results) {
      for (String row : result) {
        System.out.println(row);
      }
      System.out.println();
    }
  }

  public static List<List<String>> solveNQueens(int n) {
    List<List<String>> results = new ArrayList<>();
    boolean[] cols = new boolean[n];
    boolean[] diag1 = new boolean[2 * n - 1];
    boolean[] diag2 = new boolean[2 * n - 1];
    int[] board = new int[n];
    solve(0, n, board, cols, diag1, diag2, results);
    return results;
  }

  private static void solve(
      int row,
      int n,
      int[] board,
      boolean[] cols,
      boolean[] diag1,
      boolean[] diag2,
      List<List<String>> results) {
    if (row == n) {
      results.add(constructBoard(board, n));
      return;
    }
    for (int col = 0; col < n; col++) {
      int d1 = row - col + n - 1;
      int d2 = row + col;
      if (cols[col] || diag1[d1] || diag2[d2]) {
        continue;
      }
      board[row] = col;
      cols[col] = true;
      diag1[d1] = true;
      diag2[d2] = true;
      solve(row + 1, n, board, cols, diag1, diag2, results);
      cols[col] = false;
      diag1[d1] = false;
      diag2[d2] = false;
    }
  }

  private static List<String> constructBoard(int[] board, int n) {
    List<String> result = new ArrayList<>();
    for (int i : board) {
      char[] row = new char[n];
      for (int j = 0; j < n; j++) {
        row[j] = (j == i) ? '♛' : '_';
      }
      result.add(new String(row));
    }
    return result;
  }
}
*/
