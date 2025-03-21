package com.monal.Array;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/pascals-triangle/
/*
Given an integer numRows, return the first numRows of Pascal's triangle.
In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:

          1
        1   1
      1   2   1
    1   3   3   1
  1   4   6   4   1
1   5  10  10   5   1

Example 1:
  Input: numRows = 5
  Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
Example 2:
  Input: numRows = 1
  Output: [[1]]
Constraints:
  1 <= numRows <= 30
 *
 */
public class P011 {
  // First THought approach

  // class Solution {
  // public List<List<Integer>> generate(int k) {
  // List<List<Integer>> result = new ArrayList<>();

  // for (int i = 0; i < k; i++) {
  // ArrayList<Integer> new_tempList = new ArrayList<>();
  // new_tempList.add(1); // first
  // // Add the middle elements (sum of adjacent elements)
  // if(i>0){
  // List<Integer> temp_list = result.get(i-1);
  // for (int j = 0; j < temp_list.size() - 1; j++) {
  // new_tempList.add(temp_list.get(j) + temp_list.get(j + 1));
  // }
  // new_tempList.add(1); // last
  // }
  // result.add(new_tempList);
  // }

  // return result;

  // }
  // }

  /*
   * Each number in Pascal's Triangle can be computed using the binomial
   * coefficient formula:
   * C(n, k) = n! / (k!(n-k)!) ; n = row index, k = column index;
   *
   * // Instead of using previous rows, we can directly compute each value in a
   * row
   * // Instead of computing full factorials (which are slow), we can use this
   * iterative approach
   *
   * C(n, k) = C(n, k-1) × (n - k + 1) / k
   * This formula builds up each element from the previous one.
   * It avoids computing large factorials, making it fast and efficient.
   */

  // OPTIMIZED : using Combinatorics
  class Solution {
    public List<List<Integer>> generate(int numRows) {
      List<List<Integer>> result = new ArrayList<>();
      for (int n = 0; n < numRows; n++) {
        List<Integer> row = new ArrayList<>();
        long value = 1; // C(n, 0) is always 1
        row.add((int) value);
        for (int k = 1; k <= n; k++) {
          value = value * (n - k + 1) / k; // Using optimized combinatorics formula
          row.add((int) value);
        }
        result.add(row);
      }
      return result;
    }
  }

  public static void main(String[] args) {
    P011 p011 = new P011();
    Solution solution = p011.new Solution();
    int k = 5;
    System.out.println(solution.generate(k)); // Output: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]

    int k2 = 9;
    System.out.println(solution.generate(k2)); // Output:
                                               // [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1],[1,5,10,10,5,1],[1,6,15,20,15,6,1],[1,7,21,35,35,21,7,1],[1,8,28,56,70,56,28,8,1]]
  }

}
