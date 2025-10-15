package com.monal.DP.DP_Knapsack;

import java.util.*;

public class P009 {
  class Solution_Memo {
    private Map<Integer, Integer> memo;
    private int[][] clips;
    private int targetTime;

    public int videoStitching(int[][] clips, int time) {
      this.clips = clips;
      this.targetTime = time;
      this.memo = new HashMap<>(); // map (time -> min clips to cover it)

      int result = help(0);
      return result >= Integer.MAX_VALUE ? -1 : result;
    }

    private int help(int currentTime) {
      if (currentTime >= targetTime) return 0;
      if (memo.containsKey(currentTime)) return memo.get(currentTime);

      int minClips = Integer.MAX_VALUE;

      // Try each clip that can cover the current time
      for (int[] clip : clips) {
        int start = clip[0], end = clip[1];
        // clip can cover current time if:
        // 1. It starts at or before current time
        // 2. It extends beyond current time
        if (start <= currentTime && end > currentTime) {
          int subResult = help(end);
          if (subResult != Integer.MAX_VALUE) minClips = Math.min(minClips, 1 + subResult);
        }
      }
      memo.put(currentTime, minClips);
      return minClips;
    }
  }

  class Solution {
    public int videoStitching_(int[][] clips, int time) {
      int dp[] = new int[time + 1]; // dp[i] = min req Clips to go from 0 to i time
      Arrays.fill(dp, Integer.MAX_VALUE);
      dp[0] = 0;

      for (int i = 1; i <= time; i++) {
        for (int[] clip : clips) {
          // If this clip can help cover position i
          // (clip starts at or before i and extends to at least i)
          // We can use this clip to extend coverage from position start
          int sT = clip[0], eT = clip[1];
          if (sT <= i && eT >= i && dp[sT] != Integer.MAX_VALUE)
            dp[i] = Math.min(dp[i], dp[sT] + 1);
        }
      }

      return dp[time] == Integer.MAX_VALUE ? -1 : dp[time];
    }

    // OPTIMIZED
    public int videoStitching(int[][] clips, int time) {
      // Sort clips by start time for better processing
      Arrays.sort(clips, (a, b) -> a[0] - b[0]);

      int[] dp = new int[time + 1];// dp[i] = minimum clips to cover [0, i]
      Arrays.fill(dp, Integer.MAX_VALUE);

      dp[0] = 0;
      for (int i = 1; i <= time; i++) {
        // For position i, check all clips that end at or after i
        for (int[] clip : clips) {
          int start = clip[0], end = clip[1];
          // If clip starts after position i, it can't help cover i
          if (start > i) break;
          // If clip can cover position i
          if (end >= i && dp[start] != Integer.MAX_VALUE) dp[i] = Math.min(dp[i], dp[start] + 1);
        }
      }

      return dp[time] == Integer.MAX_VALUE ? -1 : dp[time];
    }
  }
}
