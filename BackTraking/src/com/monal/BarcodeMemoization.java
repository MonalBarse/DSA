package com.monal;

import java.util.*;

public class BarcodeMemoization {


  static int n, m, x, y;
  static char[][] grid;
  static int[][] costWhite, costBlack; // Cost to repaint each column
  static int[][][] dp; // Memoization table
  static int INF = 1000000000;

  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      n = sc.nextInt();
      m = sc.nextInt();
      x = sc.nextInt();
      y = sc.nextInt();
      sc.nextLine(); // Consume newline

      grid = new char[n][m];
      for (int i = 0; i < n; i++) {
        grid[i] = sc.nextLine().toCharArray();
      }
    }
    // Step 1: Precompute costs
    costWhite = new int[m][2]; // Cost to make column white
    costBlack = new int[m][2]; // Cost to make column black

    for (int j = 0; j < m; j++) {
      int whiteCount = 0, blackCount = 0;
      for (int i = 0; i < n; i++) {
        if (grid[i][j] == '#') whiteCount++; // Need to repaint to white
        if (grid[i][j] == '.') blackCount++; // Need to repaint to black
      }
      costWhite[j][0] = whiteCount;
      costBlack[j][1] = blackCount;
    }

    // Step 2: Initialize DP table
    dp = new int[m][2][y + 1]; // dp[col][lastColor][stripeWidth]
    for (int[][] layer : dp) {
      for (int[] row : layer) {
        Arrays.fill(row, -1);
      }
    }

    // Step 3: Call Recursive Function
    int minRepaints = Math.min(solve(0, 0, 0), solve(0, 1, 0));
    System.out.println(minRepaints);
  }

  // Recursive function with memoization
  static int solve(int col, int lastColor, int stripeWidth) {
    // Base Case: If we reach the last column
    if (col == m) {
      return (stripeWidth >= x) ? 0 : INF; // If valid stripe size, return 0, else INF
    }

    // Check Memoization Table
    if (dp[col][lastColor][stripeWidth] != -1) {
      return dp[col][lastColor][stripeWidth];
    }

    int cost = (lastColor == 0) ? costWhite[col][0] : costBlack[col][1];
    int ans = INF;

    // Option 1: Extend current stripe (if within bounds)
    if (stripeWidth < y) {
      ans = Math.min(ans, cost + solve(col + 1, lastColor, stripeWidth + 1));
    }

    // Option 2: Start new stripe (only if old stripe is at least x)
    if (stripeWidth >= x) {
      int newCost = (lastColor == 0) ? costBlack[col][1] : costWhite[col][0];
      ans = Math.min(ans, newCost + solve(col + 1, 1 - lastColor, 1));
    }

    return dp[col][lastColor][stripeWidth] = ans; // Store result in DP table
  }
}
