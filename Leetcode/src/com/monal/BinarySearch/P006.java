package com.monal.BinarySearch;

/*
  You are given an array weights[] of N packages, where weights[i] is the weight of the i-th package.
  A ship can carry these packages, but it has a weight capacity limit.
  You must ship all packages in exactly D days while maintaining the order of packages.

  Find the minimum ship capacity required to ship all packages within D days.

  Example 1:
    Input: weights = [1,2,3,4,5,6,7,8,9,10], D = 5
    Output: 15
    Explanation:
    - Ship packages in **5 days** with minimum capacity **15**:
      - Day 1: [1,2,3,4,5] (Total = 15)
      - Day 2: [6,7] (Total = 13)
      - Day 3: [8] (Total = 8)
      - Day 4: [9] (Total = 9)
      - Day 5: [10] (Total = 10)

  Example 2:
    Input : weights = [3,4,5,7,11,15,20] , D = 3
    Output : 35
    Explanation:
    - Ship packages in **3 days** with minimum capacity **35**:
      - Day 1 : [3,4,5] (Total = 12)
      - Day 2 : [7,11] (Total = 18)
      - Day 3 : [15,20] (Total = 35)

*/
public class P006 {

  private int minCapacity(int[] weights, int days) {
    int start = 0;
    int end = 0;
    int result = 0;

    // Define the search Space, start should be the maximum weight and end should be
    // the sum of all weights
    for (int weight : weights) {
      start = Math.max(start, weight);
      end += weight;
    }

    // Apply Binary Search on the search space
    while (start <= end) {
      int mid = start + (end - start) / 2;

      if (isValid(weights, days, mid)) {
        // If the current capacity is valid, then we store the result and move to the
        // left side as we need to minimize the capacity.
        result = mid;
        end = mid - 1;
      } else {
        start = mid + 1;
      }
    }

    return result;
  }

  private boolean isValid(int[] weights, int days, int capacity) {
    int currentCapacity = 0;
    int currentDays = 1;

    for (int weight : weights) {
      currentCapacity += weight;

      if (currentCapacity > capacity) {
        currentDays++;
        currentCapacity = weight;
      }

      if (currentDays > days) {
        return false;
      }
    }

    return true;
  }

  public static void main(String[] args) {
    P006 obj = new P006();

    int[] weights = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    int days = 5;
    System.out.println(obj.minCapacity(weights, days));

    int[] weights2 = { 3, 4, 5, 7, 11, 15, 20 };
    int days2 = 3;
    System.out.println(obj.minCapacity(weights2, days2));

    int weights3[] = { 1, 2, 3, 1, 1 };
    int days3 = 4;
    System.out.println(obj.minCapacity(weights3, days3));

  }

}
