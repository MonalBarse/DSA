package com.monal.BinarySearch;

/*  MINIMIZE MAXIMUM DISTANCE TO GAS STATION

Problem Statement:
  We have an array of gas stations sorted in ascending order.
  We are allowed to add k new gas stations between existing ones.
  The goal is to minimize the maximum distance between adjacent gas stations after adding k stations.

Example 1:
  Input: stations = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 9
  Output: 0
  Explanation: We can add a gas station at every integer point between 1 and 10. The maximum distance between adjacent gas stations is 0.

Example 2:
  Input stations = [23, 24, 36, 39, 46, 56, 57, 65, 84, 98], k = 1
  Output: 14
  Explanation: We can add a gas station at 60. The maximum distance between adjacent gas stations is 14.

Constraints:
  1 <= stations.length <= 10^4
  0 <= stations[i] <= 10^8
  0 <= k <= 10^4
 */
public class P012 {

  private double minMaxGasStation(int[] gasStations, int k) {
    int n = gasStations.length;
    // Step 1 is to define the search space
    // The search space will be between 0 (minimum) and (max station distance)
    double start = 0, end = 0;

    // Compute the maximum initial gap between two consecutive gas stations
    for (int i = 0; i < n - 1; i++) {
      end = Math.max(end, gasStations[i + 1] - gasStations[i]);
    }
    double precision = 1e-6;
    while (end - start > precision) {
      double mid = start + (end - start) / 2;
      if (canAddStations(gasStations, k, mid)) {
        end = mid; // Try to minimize the maximum distance
      } else {
        start = mid + precision;
      }
    }

    return start;
  }

  private boolean canAddStations(int[] gasStations, int k, double maxDistance) {
    int requiredStations = 0;

    for (int i = 0; i < gasStations.length - 1; i++) {
      double gap = gasStations[i + 1] - gasStations[i];
      requiredStations += (int) (gap / maxDistance);
      if (requiredStations > k)
        return false;
    }

    return requiredStations <= k;
  }

  public static void main(String[] args) {

    P012 p12 = new P012();

    int stations[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    int k = 9;
    System.out.println(p12.minMaxGasStation(stations, k)); // Output: 0

    int stations2[] = { 23, 24, 36, 39, 46, 56, 57, 65, 84, 98 };
    int k2 = 1;
    System.out.println(p12.minMaxGasStation(stations2, k2)); // Output: 14
  }
}
