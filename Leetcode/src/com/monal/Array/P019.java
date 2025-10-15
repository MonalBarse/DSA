/*
You are given two integer arrays nums1 and nums2, both of length n. 
Your task is to count the number of pairs of indices (i, j) that satisfy the following conditions:
    i < j (the first index must be strictly less than the second index)
    nums1[i] + nums1[j] > nums2[i] + nums2[j] (the sum of elements from nums1 at 
    positions i and j must be greater than the sum of elements from nums2 at the same positions)

The key insight is that we need to find pairs where the sum from the first array exceeds 
the sum from the second array at the same index positions.

For example, if we have:
    nums1 = [2, 1, 2, 1]
    nums2 = [1, 2, 1, 2]

We would check all pairs (i, j) where i < j:
    For pair (0, 1): Check if 2 + 1 > 1 + 2 (which is 3 > 3, false)
    For pair (0, 2): Check if 2 + 2 > 1 + 1 (which is 4 > 2, true)
    And so on...

The solution transforms this problem by rearranging the inequality to 
(nums1[i] - nums2[i]) + (nums1[j] - nums2[j]) > 0. 
This allows us to create a new array where each 
element is the difference between corresponding elements of nums1 and nums2, then find pairs in this 
new array whose sum is positive. The sorting and two-pointer technique efficiently counts all 
valid pairs without checking every possible combination. */

import java.util.Arrays;

public class P019.java {
    public class Solution{
      public long countPairs(int[] nums1, int[] nums2) {
          int n = nums1.length;
          int[] diff = new int[n];
           
          for (int i = 0; i < n; i++) diff[i] = nums1[i] - nums2[i];
          Arrays.sort(diff);

          long count = 0;
          int left = 0, right = n - 1;
          
          while (left < right) {
              if (diff[left] + diff[right] > 0) {
                  count += (right - left); 
                  right--;
              } 
              else left++;
              
          }
          
          return count;
      }
    }
}
