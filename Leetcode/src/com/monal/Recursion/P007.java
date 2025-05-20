package com.monal.Recursion;

/*
 * Ques
 * Given an m x n grid of characters board and a string word, return true if word exists in the grid.
 * The word can be constructed from letters of sequentially adjacent cells,
 * where adjacent cells are horizontally or vertically neighboring.
 * The same letter cell may not be used more than once.
 *
 * Example 1:
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
 * Output: false
 * Explanation: The word "ABCCED" does not exist in the given board.
 *
 * Example 2:
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
 * Output: true
 * Explanation: The word "SEE" exists in the given board.
 *
 */
public class P007 {
  class Solution {
    /*
     * The idea is to use DFS to explore all possible paths in the grid.
     * We will start from each cell and try to match the word character by
     * character. If we find a match, we will continue to the next character and
     * mark the cell as visited. If we reach the end of the word, we return true.
     * If we cannot find a match, we backtrack and unmark the cell.
     */
    public boolean exist(char[][] board, String word) {
      int rows = board.length;
      int cols = board[0].length;

      // Try starting DFS from every position where the first letter matches
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
          if (board[i][j] == word.charAt(0)) {// linear search for starting point
            // we got starting pt, now we can start searching for the word
            // we pass starting pt and the word, and an array to keep track of visited cells
            if (dfs(board, word, 0, i, j, new boolean[rows][cols])) {
              return true;
            }
          }
        }
      }
      return false;
    }

    private boolean dfs(char[][] board, String word, int index, int row, int col, boolean[][] visited) {
      // Base case: If the entire word is found
      if (index == word.length()) {
        return true;
      }

      // Boundary checks and visited check
      if (row < 0 || col < 0 || row >= board.length || col >= board[0].length || visited[row][col]
          || board[row][col] != word.charAt(index)) {
        return false;
      }

      // Mark cell as visited
      visited[row][col] = true;

      // Explore all 4 directions
      boolean found = dfs(board, word, index + 1, row + 1, col, visited) || // Down
          dfs(board, word, index + 1, row - 1, col, visited) || // Up
          dfs(board, word, index + 1, row, col + 1, visited) || // Right
          dfs(board, word, index + 1, row, col - 1, visited); // Left

      // Backtrack
      visited[row][col] = false;

      return found;
    }
  }

  public static void main(String[] args) {

    P007 p007 = new P007();
    Solution solution = p007.new Solution();
    char[][] board = { { 'A', 'B', 'C', 'E' }, { 'S', 'F', 'C', 'S' }, { 'A', 'D', 'E', 'E' } };
    System.out.println(solution.exist(board, "ABCCED")); // true
    System.out.println(solution.exist(board, "SEE")); // true
    System.out.println(solution.exist(board, "ABCB")); // false

  }
}
