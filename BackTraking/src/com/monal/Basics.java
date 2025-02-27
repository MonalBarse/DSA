package com.monal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Basics {

  // ======================= Problem 1 ======================= //
  static int problem_one(int r, int c) {
    // Q1 size 3 x 3 matrix
    // [ s * * ]
    // [ * * * ]
    // [ * * e ]

    if (r == 1 || c == 1) {
      return 1;
    }
    return problem_one(r - 1, c) + problem_one(r, c - 1);
  }

  // ======================= Problem 2 ======================= //
  static List<String> problem_two(int r, int c) {
    // Return all the paths to reach the end of the matrix
    List<String> result = new ArrayList<>();
    problem_two_helper(r, c, "", result);
    return result;
  }

  static void problem_two_helper(int r, int c, String path, List<String> result) {
    if (r == 1 && c == 1) {
      result.add(path);
      return;
    }
    if (r > 1) {
      problem_two_helper(r - 1, c, path + "⬇", result);
    }
    if (c > 1) {
      problem_two_helper(r, c - 1, path + "➡", result);
    }
  }

  static List<String> problem_twoDiagonal(int r, int c, String path) {
    // in this one you can move diagonal as well
    if (r == 1 && c == 1) {
      List<String> res = new ArrayList<>();
      res.add(path);
      return res;
    }
    List<String> result = new ArrayList<>();

    if (r > 1) {
      result.addAll(problem_twoDiagonal(r - 1, c, path + "↓"));
    }
    if (c > 1) {
      result.addAll(problem_twoDiagonal(r, c - 1, path + "➙"));
    }
    if (r > 1 && c > 1) {
      result.addAll(problem_twoDiagonal(r - 1, c - 1, path + "➘"));
    }
    return result;
  }

  // ======================= Problem 3 ======================= //
  /*
   * Maze with obstacles => ( 0 - can go through | 1 - obstacle)
   * [ s * * * * ]
   * [ * * * * * ]
   * [ * * × * * ]
   * [ * × * * * ]
   * [ * * * * e ]
   * Given a 2D matrix with obstacles, path (represent with arrows) to reach the
   * end (you cannot go through the obstacles)
   */

  static int[][] maze1 = {
      { 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0 },
      { 0, 0, 1, 0, 0 },
      { 0, 1, 0, 0, 0 },
      { 0, 0, 0, 0, 0 }
  };

  static List<String> problem_three(int r, int c) {
    List<String> result = new ArrayList<>();
    problem_three_helper(r, c, "", result);
    return result;
  }

  static void problem_three_helper(int r, int c, String path, List<String> result) {
    if (r == maze1.length - 1 && c == maze1[0].length - 1) {
      result.add(path);
      return;
    }
    if (maze1[r][c] == 1)
      return;
    if (r < maze1.length - 1) {
      problem_three_helper(r + 1, c, path + "⬇", result);
    }
    if (c < maze1[0].length - 1) {
      problem_three_helper(r, c + 1, path + "➡", result);
    }
  }

  // ====================== Backtracking ===================== //

  // ======================= Problem 3 ======================= //
  // In this its similar to Problem 2 with obstacles but you can move up as well
  // (⬆)
  static int[][] maze2 = {
      { 0, 0, 0 },
      { 0, 1, 0 },
      { 0, 0, 0 }
  };

  static List<String> problem_three_allpath(int r, int c) {
    List<String> result = new ArrayList<>();
    prob3_helper(r, c, "", result);
    return result;
  }

  static void prob3_helper(int r, int c, String path, List<String> result) {
    // base case if we reach the end
    if (r == maze2.length - 1 && c == maze2[0].length - 1) {
      result.add(path);
      return;
    }
    // if we hit the obstacle
    if (maze2[r][c] == 1)
      return;
    // mark the current cell as visited if it is 0 make it 1 and
    // if it is 1 we don't need to do anything
    int temp = maze2[r][c];
    maze2[r][c] = 1;

    // move down
    if (r < maze2.length - 1) {
      prob3_helper(r + 1, c, path + "⬇", result);
    }
    // move right
    if (c < maze2[0].length - 1) {
      prob3_helper(r, c + 1, path + "➡", result);
    }
    // move up
    if (r > 0) {
      prob3_helper(r - 1, c, path + "⬆", result);
    }
    // move left
    if (c > 0) {
      prob3_helper(r, c - 1, path + "⬅", result);
    }
    // backtrack
    maze2[r][c] = temp; // set it back to the original value
  }

  // Using memoization for problem 3

  static int[][] maze = {
      { 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0 },
      { 0, 0, 1, 0, 0 },
      { 0, 1, 0, 0, 0 },
      { 0, 0, 0, 0, 0 }
  };

  static int[][] dp;

  public static int findPaths(int r, int c) {
    // If out of bounds or at an obstacle, return 0 paths
    if (r < 0 || c < 0 || r >= maze.length || c >= maze[0].length || maze[r][c] == 1)
      return 0;

    // If at the goal, return 1
    if (r == maze.length - 1 && c == maze[0].length - 1)
      return 1;

    // If already computed, return stored result
    if (dp[r][c] != -1)
      return dp[r][c];

    // Compute paths by moving only down and right
    int down = findPaths(r + 1, c);
    int right = findPaths(r, c + 1);

    // Store the result in DP table
    dp[r][c] = down + right;

    return dp[r][c];
  }

  public static void main(String[] args) {
    // System.out.println(problem_one(3, 3));
    System.out.println(problem_two(3, 3));
    // System.out.println(problem_twoDiagonal(3, 3, ""));
    // System.out.println(problem_three(0, 0));
    // System.out.println(problem_three_allpath(0, 0));
    // Problem 3 with DP

    dp = new int[maze.length][maze[0].length];

    for (int[] row : dp)
      Arrays.fill(row, -1); // Initialize DP table with -1 (uncomputed)

    int totalPaths = findPaths(0, 0); // Start from (0,0), the top-left corner
    System.out.println("Total unique paths: " + totalPaths);
  }

}
