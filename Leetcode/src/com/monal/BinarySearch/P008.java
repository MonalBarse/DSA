package com.monal.BinarySearch;

/*
You are given an integer array bloomDay, an integer m and an integer k.
You want to make m bouquets. To make a bouquet, you need to use k adjacent flowers from the garden.
The garden consists of n flowers, the ith flower will bloom in the bloomDay[i] and then can be used in exactly one bouquet.
Return the minimum number of days you need to wait to be able to make m bouquets from the garden. If it is impossible to make m bouquets return -1.

Example 1:
  Input: bloomDay = [1,10,3,10,2], m = 3, k = 1
  Output: 3
  Explanation: Let us see what happened in the first three days. x means flower bloomed and _ means flower did not bloom in the garden.
  We need 3 bouquets each should contain 1 flower.
  After day 1: [x, _, _, _, _]   // we can only make one bouquet.
  After day 2: [x, _, _, _, x]   // we can only make two bouquets.
  After day 3: [x, _, x, _, x]   // we can make 3 bouquets. The answer is 3.

Example 2:
  Input: bloomDay = [1,10,3,10,2], m = 3, k = 2
  Output: -1
  Explanation: We need 3 bouquets each has 2 flowers, that means we need 6 flowers. We only have 5 flowers so it is impossible to get the needed bouquets and we return -1.

Example 3:
  Input: bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3
  Output: 12
  Explanation: We need 2 bouquets each should have 3 flowers.  Here is the garden after the 7 and 12 days:
  After day 7: [x, x, x, x, _, x, x]
  We can make one bouquet of the first three flowers that bloomed. We cannot make another bouquet from the last three flowers that bloomed because they are not adjacent.
  After day 12: [x, x, x, x, x, x, x]
  It is obvious that we can make two bouquets in different ways.

Constraints:
  bloomDay.length == n
  1 <= n <= 105
  1 <= bloomDay[i] <= 109
  1 <= m <= 106
  1 <= k <= n

*/
public class P008 {

  class Solution {
    public int minDays(int[] bloomDay, int m, int k) {
      int n = bloomDay.length;

      // Early check: if we need more flowers than available, return -1
      if ((long) m * k > n) {
        return -1;
      }

      // Define the search space
      int start = Integer.MAX_VALUE; // Find minimum bloom day
      int end = Integer.MIN_VALUE; // Find maximum bloom day

      for (int day : bloomDay) {
        start = Math.min(start, day);
        end = Math.max(end, day);
      }

      // Binary search on the days
      while (start <= end) {
        int mid = start + (end - start) / 2;

        if (canMakeBouquets(bloomDay, m, k, mid)) {
          // If we can make enough bouquets, try fewer days
          end = mid - 1;
        } else {
          // If we can't make enough bouquets, try more days
          start = mid + 1;
        }
      }

      return start;
    }

    private boolean canMakeBouquets(int[] bloomDay, int bouquets, int flowersPerBouquet, int day) {
      int bouquetCount = 0;
      int consecutiveFlowers = 0;

      for (int bloom : bloomDay) {
        // If flower has bloomed by 'day'
        if (bloom <= day) {
          consecutiveFlowers++;
          // Check if we can form a bouquet
          if (consecutiveFlowers == flowersPerBouquet) {
            bouquetCount++;
            consecutiveFlowers = 0;

            // Early exit if we've made enough bouquets
            if (bouquetCount >= bouquets) {
              return true;
            }
          }
        } else {
          // Reset count if we encounter a flower that hasn't bloomed
          consecutiveFlowers = 0;
        }
      }

      return bouquetCount >= bouquets;
    }
  }

  public static void main(String[] args) {
    P008 a = new P008();
    Solution obj = a.new Solution();

    int[] bloomDay = { 1, 10, 3, 10, 2 };
    int m = 3;
    int k = 1;
    System.out.println(obj.minDays(bloomDay, m, k));

    int[] bloomDay1 = { 1, 10, 6, 7, 8, 9, 7, 5, 5, 5, 5, 5, 5, 53, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };
    int m1 = 5;
    int k1 = 5;
    System.out.println(obj.minDays(bloomDay1, m1, k1));

  }
}
