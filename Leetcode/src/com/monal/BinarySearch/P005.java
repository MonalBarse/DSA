package com.monal.BinarySearch;

/*
 🔹 Advanced Problem 2: Median of Two Sorted Arrays
    Problem Statement:
      You are given two sorted arrays, nums1[] and nums2[], of sizes n and m.
      Find the median of the two sorted arrays in O(log(min(n, m))) time complexity.

    Example 1:
      Input: nums1 = [1, 3], nums2 = [2]
      Output: 2.0
      Explanation: The median is 2.0

    Example 2:
      Input: nums1 = [1, 2], nums2 = [3, 4]
      Output: 2.5
      Explanation: The median is (2 + 3) / 2 = 2.5

    Example 3:
      Input: nums1 = [2,32,45,67,89], nums2 = [1,3,5,7,9,12,15,18,40,78]
      Output: 15
      Explanation the array becomes [1, 2, 3, 5, 7, 9, 12, 15, 18, 32, 40, 45, 67, 78, 89] middle element is 15
 */

/*
 * Approach:
  1️. Understanding the Problem
    If we merge the two arrays, the median is the middle element (or average of two middle elements if length is even).
    But merging takes O(n + m) time ❌.
    Instead, we use Binary Search to partition the arrays optimally.

  2. Binary Search on Partitioning
    We divide the two arrays into left and right halves such that:
    Left half has smaller elements
    Right half has larger elements
    Median is found from the left and right boundary elements

  3. Conditions for partitioning
    We partition the smaller array (nums1) at index mid1 and the larger array (nums2) at mid2, where:
    mid1 + mid2 = (n + m + 1) / 2 (mid1 will be n/2, mid2 will be m/2 if n+m is odd and if n+m is even, mid1 will be n/2, mid2 will be m/2 + 1)

    For the partition to be valid:
      leftMax1 <= rightMin2 (left max of nums1 ≤ right min of nums2)
      leftMax2 <= rightMin1 (left max of nums2 ≤ right min of nums1)
    Once these conditions hold, we compute the median.

 */
public class P005 {

  private double findMedian(int[] nums1, int[] nums2, int n, int m) {
    // swap the arrays, we want nums1 to be the smaller array
    if (n > m)
      return findMedian(nums2, nums1, m, n);

    int start = 0, end = n;
    // Step 1: Apply Binary Search on the smaller array
    while (start <= end) {
      int mid1 = start + (end - start) / 2;
      int mid2 = (n + m + 1) / 2 - mid1;

      // Step 2: Compute the left and right boundary elements
      int leftMax1 = (mid1 == 0) ? Integer.MIN_VALUE : nums1[mid1 - 1];
      int rightMin1 = (mid1 == n) ? Integer.MAX_VALUE : nums1[mid1];

      int leftMax2 = (mid2 == 0) ? Integer.MIN_VALUE : nums2[mid2 - 1];
      int rightMin2 = (mid2 == m) ? Integer.MAX_VALUE : nums2[mid2];

      // Step 3: Check if the partition is valid
      if (leftMax1 <= rightMin2 && leftMax2 <= rightMin1) {
        // Step 4: Compute the median
        if ((n + m) % 2 == 0) {
          return (Math.max(leftMax1, leftMax2) + Math.min(rightMin1, rightMin2)) / 2.0;
        } else {
          return Math.max(leftMax1, leftMax2);
        }
      } else if (leftMax1 > rightMin2) {
        end = mid1 - 1;
      } else {
        start = mid1 + 1;
      }
    }

    return -1;
  }

  public static void main(String[] args) {
    P005 obj = new P005();
    int[] nums1 = { 1, 4, 13 };
    int[] nums2 = { 2, 3, 5, 7, 88 };
    int n = nums1.length, m = nums2.length;
    System.out.println(obj.findMedian(nums1, nums2, n, m));

    int[] nums3 = { 1, 2, 35, 66, 78, 199 };
    int[] nums4 = { 3, 4, 5, 7, 9, 17, 18, 20, 79, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 210 };
    int n1 = nums3.length, m1 = nums4.length;
    System.out.println(obj.findMedian(nums3, nums4, n1, m1));

    int[] nums5 = { 2, 32, 45, 67, 89, 112, 123, 134, 145, 156, 167, 178, 189 };
    int[] nums6 = { 1, 3, 5, 7, 9, 12, 15, 18, 40, 78, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 210 };
    int n2 = nums5.length, m2 = nums6.length;
    System.out.println(obj.findMedian(nums5, nums6, n2, m2));

  }
}
