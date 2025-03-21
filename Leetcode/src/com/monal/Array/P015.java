package com.monal.Array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Subarray with given XOR
// https://www.interviewbit.com/problems/subarray-with-given-xor/

/*
 * Preriquisites:
 *  XOR is a bitwise operator if operated on two numbers, it returns 1 if the bits are different, else 0.
 * A ^ A = 0, A ^ 0 = A
 * 4 ^ 6 = 2 - 100 ^ 110 = 010  and 010 = 2
 */
/*
  Given an array of integers A and an integer B.
  Find the total number of subarrays having bitwise XOR of all elements equals to B.

  Example 1:
    Input 1: A = [4, 2, 2, 6, 4], B = 6
    Output: 4
    Explanation: The subarrays having XOR of their elements as 6 are:
      [4, 2], [6], [6, 4], [4]

  Example 2:
    Input 2: A = [5, 6, 7, 8, 9], B = 5
    Output: 2
    Explanation: The subarrays having XOR of their elements as 5 are:
      [5], [5, 6, 7, 8, 9]

*/
public class P015 {

  public class Solution {
    public int solve(ArrayList<Integer> A, int B) {
      Map<Integer, Integer> freq = new HashMap<>();
      int count = 0;
      int xor = 0;

      for (int num : A) {
        xor ^= num;
        if (xor == B) {
          count++;
        }
        if (freq.containsKey(xor ^ B)) {
          count += freq.get(xor ^ B);
        }
        freq.put(xor, freq.getOrDefault(xor, 0) + 1);
      }

      return count;
    }
  }

}
