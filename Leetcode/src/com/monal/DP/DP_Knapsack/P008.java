package com.monal.DP.DP_Knapsack;

/*
You are given an integer array `nums` with length `n`.
The **cost** of a subarray `nums[l..r]`, where `0 <= l <= r < n`, is defined as:
`cost(l, r) = nums[l] - nums[l + 1] + ... + nums[r] * (−1)r − l`
Your task is to **split** `nums` into subarrays such that the **total** **cost** of the subarrays is **maximized**, ensuring each element belongs to **exactly one** subarray.
Formally, if `nums` is split into `k` subarrays, where `k > 1`, at indices `i1, i2, ..., ik − 1`, where `0 <= i1 < i2 < ... < ik - 1 < n - 1`, then the total cost will be:
`cost(0, i1) + cost(i1 + 1, i2) + ... + cost(ik − 1 + 1, n − 1)`
Return an integer denoting the *maximum total cost* of the subarrays after splitting the array optimally.
**Note:** If `nums` is not split into subarrays, i.e. `k = 1`, the total cost is simply `cost(0, n - 1)`.
 
**Example 1:**
**Input:** nums = [1,-2,3,4]
**Output:** 10
**Explanation:**
One way to maximize the total cost is by splitting `[1, -2, 3, 4]` into subarrays `[1, -2, 3]` and `[4]`. The total cost will be `(1 + 2 + 3) + 4 = 10`.
**Example 2:**
**Input:** nums = [1,-1,1,-1]
**Output:** 4
**Explanation:**
One way to maximize the total cost is by splitting `[1, -1, 1, -1]` into subarrays `[1, -1]` and `[1, -1]`. The total cost will be `(1 + 1) + (1 + 1) = 4`.
**Example 3:**
**Input:** nums = [0]
**Output:** 0
**Explanation:**
We cannot split the array further, so the answer is 0.
**Example 4:**
**Input:** nums = [1,-1]
**Output:** 2
**Explanation:**
Selecting the whole array gives a total cost of `1 + 1 = 2`, which is the maximum.
*/
public class P008 {

  class Solution {
    private int[] arr;
    private int n;
    private Long[][] memo; // [index][lastSign] where lastSign: 0=positive, 1=negative

    public long maximumTotalCost(int[] nums) {
      this.arr = nums;
      this.n = nums.length;
      this.memo = new Long[n][2];
      return help(0, 0); // Start from index 0 with last sign as positive
    }

    private long help(int i, int lastSign) {
      if (i == n) return 0;
      if (memo[i][lastSign] != null) return memo[i][lastSign];

      long res = Long.MIN_VALUE;
      // Take current element with positive sign
      res = Math.max(res, arr[i] + help(i + 1, 0));

      // Take current element with negative sign (only if last was positive)
      if (lastSign == 0 && i > 0) res = Math.max(res, -arr[i] + help(i + 1, 1));

      return memo[i][lastSign] = res;
    }
  }

  class Solution_opt {
    public long maximumTotalCost(int[] nums) {
      int n = nums.length;

      long prevPos = nums[0]; // dp[i-1][0]
      long prevNeg = Long.MIN_VALUE; // dp[i-1][1]

      for (int i = 1; i < n; i++) {
        long newPos = Math.max(prevPos, prevNeg) + nums[i]; // add nums[i] positively
        long newNeg = prevPos - nums[i]; // flip nums[i], only if prev was pos

        prevPos = newPos;
        prevNeg = newNeg;
      }

      return Math.max(prevPos, prevNeg);
    }
  }

}
