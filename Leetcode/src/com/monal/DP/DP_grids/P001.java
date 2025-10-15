package com.monal.DP.DP_grids;

/*
An attendance record for a student can be represented as a string where each character signifies whether the student was absent, late, or present on that day. The record only contains the following three characters:

'A': Absent.
'L': Late.
'P': Present.
Any student is eligible for an attendance award if they meet both of the following criteria:

The student was absent ('A') for strictly fewer than 2 days total.
The student was never late ('L') for 3 or more consecutive days.
Given an integer n, return the number of possible attendance records of length n that make a student eligible for an attendance award. The answer may be very large, so return it modulo 109 + 7.


 */
public class P001 {
  class Solution {
    private static final int MOD = (int) 1e9 + 7;

    public int checkRecord(int n) {
      int[][][] dp = new int[n + 1][2][3]; // dp[i][absent][late]
      dp[0][0][0] = 1; // Base case: 0 days, 0 absent, 0 late → 1 way

      for (int i = 1; i <= n; i++) {
        for (int a = 0; a <= 1; a++) {
          for (int l = 0; l <= 2; l++) {
            int val = dp[i - 1][a][l];
            if (val == 0)
              continue;

            // Case 1: Present → reset late to 0, absent stays same
            dp[i][a][0] = (dp[i][a][0] + val) % MOD;

            // Case 2: Late → only if l < 2 → increase late count
            if (l < 2)
              dp[i][a][l + 1] = (dp[i][a][l + 1] + val) % MOD;

            // Case 3: Absent → only if a < 1 → increase absent count, reset late
            if (a < 1)
              dp[i][a + 1][0] = (dp[i][a + 1][0] + val) % MOD;
          }
        }
      }

      int res = 0;
      for (int a = 0; a <= 1; a++)
        for (int l = 0; l <= 2; l++)
          res = (res + dp[n][a][l]) % MOD;

      return res;
    }
  }

  class Solution_MEMO {
    private static final int MOD = (int) 1e9 + 7;

    public int checkRecord(int n) {
      // Memoization
      int[][][] memo = new int[n + 1][3][2]; // dp[n][last][absentCount]
      for (int i = 0; i <= n; i++)
        for (int j = 0; j < 3; j++)
          for (int k = 0; k < 2; k++)
            memo[i][j][k] = -1; // Initialize with -1

      return countRecords(n, 0, 0, memo);
    }

    private int countRecords(int n, int lates, int absentees, int[][][] memo) {
      if (n == 0)
        return 1;
      if (memo[n][lates][absentees] != -1)
        return memo[n][lates][absentees];
      int count = 0;

      // Case 1: Present
      count = (count + countRecords(n - 1, 0, absentees, memo)) % MOD;
      // Case 2: Late The student was never late ('L') for 3 or more consecutive days.
      if (lates < 2)
        count = (count + countRecords(n - 1, lates + 1, absentees, memo)) % MOD;
      // Case 3: Absent, only if < 1 & reset Lates
      if (absentees < 1) count = (count + countRecords(n - 1, 0, absentees + 1, memo)) % MOD;

      // count = (count + (countRecords(n - 1, 0, absentees, memo) +
      //     (lates < 2 ? countRecords(n - 1, lates + 1, absentees, memo) : 0) +
      //     (absentees < 1 ? countRecords(n - 1, 0, absentees + 1, memo) : 0))) % MOD;
      // count %= MOD; // Take modulo

      return memo[n][lates][absentees] = count;
    }
  }

}
