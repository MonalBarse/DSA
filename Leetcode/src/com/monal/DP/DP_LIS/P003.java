package com.monal.DP.DP_LIS;

import java.util.*;

/*
368. Largest Divisible Subset
Given a set of distinct positive integers nums, return the largest subset answer such that every pair (answer[i], answer[j]) of elements in this subset satisfies:
answer[i] % answer[j] == 0, or
answer[j] % answer[i] == 0
If there are multiple solutions, return any of them.
The solution set must be returned in any order.
Example 1:
  Input: nums = [1,2,3]
  Output: [1,2]
  Explanation: [1,3] is also accepted.
Example 2:
  Input: nums = [1,2,4,8]
  Output: [1,2,4,8]
*/
public class P003 {
    class Solution {
        public List<Integer> largestDivisibleSubset(int[] arr) {
            Arrays.sort(arr); // Sort to ensure divisibility works left to r
            // does not affect answer since it's asking for subset not subseq
            int n = arr.length;
            int[] curr = new int[n]; // dp[i] = size of largest subset ending at i
            int[] prev = new int[n]; // prev[i] = previous index in path
            Arrays.fill(curr, 1);
            Arrays.fill(prev, -1);
            int maxIndex = 0;
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    if (arr[i] % arr[j] == 0 && curr[j] + 1 > curr[i]) {
                        curr[i] = curr[j] + 1;

                        prev[i] = j;
                    }
                }
                if (curr[i] > curr[maxIndex]) {
                    maxIndex = i;
                }
            }
            // Reconstruct the path
            List<Integer> result = new ArrayList<>();
            while (maxIndex != -1) {
                result.add(arr[maxIndex]);
                maxIndex = prev[maxIndex];
            }

            Collections.reverse(result);
            return result;
        }
    }

}
