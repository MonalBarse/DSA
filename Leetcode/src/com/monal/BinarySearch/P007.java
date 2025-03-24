package com.monal.BinarySearch;

/*
Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas. The guards have gone and will come back in h hours.
Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses some pile of bananas and eats k bananas from that pile. If the pile has less than k bananas, she eats all of them instead and will not eat any more bananas during this hour.
Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.
Return the minimum integer k such that she can eat all the bananas within h hours.

Example 1:
  Input: piles = [3,6,7,11], h = 8
  Output: 4
  Explaination: In first hour, 3 banans, in second 4, in third 2, in fourth 4 then 3, in sixith 4, in seventh 4, in eighth 3
Example 2:
  Input: piles = [30,11,23,4,20], h = 5
  Output: 30
Example 3:
  Input: piles = [30,11,23,4,20], h = 6
  Output: 23
Constraints:
  1 <= piles.length <= 104
  piles.length <= h <= 109
  1 <= piles[i] <= 109
 */
public class P007 {
  // Classic example of BS on answers
  public static int minEatingSpeed(int[] piles, int h) {
    // Koko has `h` hours to eat all the bananas
    // Define the search space - the answer lies between lowest speed (1) and
    // highest speed (max(piles))(when h = piles.length)

    int start = 1, end = 0;
    int result = 0;
    for (int pile : piles) {
      end = Math.max(end, pile);
    }

    // BS on teh space
    while (start <= end) {
      int mid = start + (end - start) / 2;
      if (isValid(piles, h, mid)) {
        // If the current speed can eat all the bananas in h hours then we
        // should store the speed and move to the left side to minimize the speed
        result = mid;
        end = mid - 1;
      } else {
        start = mid + 1;
      }
    }
    return result;
  }

  private static boolean isValid(int[] piles, int h, int maxSpeed) {
    int hoursNeeded = 0;

    for (int pile : piles) {
      hoursNeeded = hoursNeeded + pile / maxSpeed;
      if (pile % maxSpeed != 0) {
        hoursNeeded++;
      }

      if (hoursNeeded > h) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    int[] piles1 = { 3, 6, 7, 11 };
    int h1 = 8;
    System.out.println(minEatingSpeed(piles1, h1));

    int[] piles2 = { 30, 11, 23, 4, 20 };
    int h2 = 5;
    System.out.println(minEatingSpeed(piles2, h2));

    int[] piles3 = { 30, 11, 23, 4, 20 };
    int h3 = 6;
    System.out.println(minEatingSpeed(piles3, h3));
  }

}
