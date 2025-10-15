package com.monal.DP.DP_Strings;

import java.util.*;

public class P007 {
  class Solution {
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
      if (n1 == 0) return 0;

      int n = s1.length(), m = s2.length();
      int s2_pos = 0; // Which character in s2 we're currently seeking
      int s2_count = 0; // How many full s2 strings we've successfully matched

      // This lets us detect when we're repeating the same matching pattern
      Map<Integer, int[]> memo = new HashMap<>();
      // tracking states in map of {s2_pos: [s1_iteration, s2_count]}

      for (int s1_idx = 0; s1_idx < n1; s1_idx++) {
        for (int j = 0; j < n; j++) {
          if (s1.charAt(j) == s2.charAt(s2_pos)) {
            s2_pos++;
            if (s2_pos == m) {
              s2_pos = 0;
              s2_count++;
            }
          }
        }

        // Check if we've been in this s2 position before after completing an s1
        // If yes, we've found a repeating cycle in our matching pattern
        if (memo.containsKey(s2_pos)) {
          int[] prev = memo.get(s2_pos);
          int prev_s1_idx = prev[0];
          int prev_s2_count = prev[1];

          // Calculate what happens in one complete cycle
          int s1_len_in_onecycle = s1_idx - prev_s1_idx;
          int s2_in_one_cycle = s2_count - prev_s2_count;

          int rem_len_in_s1 = n1 - s1_idx - 1;
          int rem_cycles = rem_len_in_s1 / s1_len_in_onecycle;

          // jump ahead by the number of complete cycles
          s2_count += rem_cycles * s2_in_one_cycle;
          s1_idx += rem_cycles * s1_len_in_onecycle;
        } else {
          // First time seeing this s2 position, record current state
          memo.put(s2_pos, new int[] { s1_idx, s2_count });
        }
      }

      // we have count of s2, we need to return str
      // s2_count / n2 will give us how many full s2 strings we can form
      if (n2 == 0) return 0; // Avoid division by zero if
      return s2_count / n2;
    }
  }
}
