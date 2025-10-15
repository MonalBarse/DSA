package com.monal.DP.DP_LIS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
You are given a 2D array of integers envelopes where envelopes[i] = [wi, hi] represents the width and the height of an envelope.
One envelope can fit into another if and only if both the width and height of one envelope are greater than the other envelope's width and height.
Return the maximum number of envelopes you can Russian doll (i.e., put one inside the other).
Note: You cannot rotate an envelope.

Example 1:
  Input: envelopes = [[5,4],[6,4],[6,7],[2,3]]
  Output: 3
  Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
Example 2:
  Input: envelopes = [[1,1],[1,1],[1,1]]
  Output: 1

Constraints72. Edit Distance:
  1 <= envelopes.length <= 105
  envelopes[i].length == 2
  1 <= wi, hi <= 105

*/
public class P008 {
  class Solution {
    public int maxEnvelopes(int[][] envelopes) {
      // Sort by width ascending, height descending for same width
      Arrays.sort(envelopes, (a, b) -> {
        if (a[0] == b[0])
          return b[1] - a[1]; // Same width: sort by height descending
        return a[0] - b[0]; // Different width: sort by width ascending
      });
      // Why height descending for same width?
      // Because if you sort by height ascending for same width, you can't pick both
      // [6,4] and [6,7]
      // So you need to sort by height descending for same width.

      // Find LIS on heights using binary search (O(n log n))
      return lengthOfLIS(envelopes);
    }

    private int lengthOfLIS(int[][] envelopes) {
      List<Integer> tails = new ArrayList<>();

      for (int[] envelope : envelopes) {
        int height = envelope[1];
        // Binary search for the position to insert/replace
        int pos = binarySearch(tails, height);
        if (pos == tails.size())
          tails.add(height); // Extend the sequence
        else
          tails.set(pos, height); // Replace to maintain smallest possible tail
      }
      return tails.size();
    }

    private int binarySearch(List<Integer> tails, int target) {
      int left = 0, right = tails.size();
      while (left < right) {
        int mid = left + (right - left) / 2;
        if (tails.get(mid) < target)
          left = mid + 1;
        else
          right = mid;
      }
      return left;
    }
  }

}

/*
 * How to approach thinking about this problem?
 * - When you first see this problem, you might think about sorting by width and
 * then using LIS on heights.
 * - But this doesn't work because you can't pick both [6,7] and [6,4]
 * - So you need to sort by width and then use LIS on heights, but you need to
 * sort by height descending for same width.
 * - Then you need to think about how to handle the case where you can't pick
 * both [6,7] and [6,4]
 * - How do we find the longest increasing subsequence?
 */
