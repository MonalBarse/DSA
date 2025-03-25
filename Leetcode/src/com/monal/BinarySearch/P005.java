package com.monal.BinarySearch;

/*
 🔹 Median of Two Sorted Arrays
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

  2.1 Why we did not do BS on answer?
    We cannot apply Binary Search on the answer because the median is not necessarily present in the arrays.
    We need to find the median from the two arrays.

  2.2 Binary Search on Partitioning
    We divide the two arrays into left and right halves such that:
      left half of nums1 + left half of nums2 = (n + m + 1) / 2


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

  private double findMedian(int[] nums1, int[] nums2) {
    int n = nums1.length, m = nums2.length;
    // swap the arrays, we want nums1 to be the smaller array
    if (n > m)
      return findMedian(nums2, nums1);

    int start = 0, end = n;
    // Step 1: Apply Binary Search on the smaller array
    while (start <= end) {
      int mid1 = start + (end - start) / 2;
      int mid2 = (n + m + 1) / 2 - mid1;

      // Step 2: Compute the left and right boundary elements
      int Aleft = (mid1 > 0) ? nums1[mid1 - 1] : Integer.MIN_VALUE;
      int Aright = (mid1 < m) ? nums1[mid1] : Integer.MAX_VALUE;

      int Bleft = (mid2 > 0) ? nums2[mid2 - 1] : Integer.MIN_VALUE;
      int Bright = (mid2 < n) ? nums2[mid2] : Integer.MAX_VALUE;

      // Step 3: Check if the partition is valid
      if (Aleft <= Bright && Bleft <= Aright) {
        // Step 4: Compute the median
        // if the total number of elements is even, return average of two middle elem
        // if its odd then its max of left partition
        if ((n + m) % 2 == 0) {
          return (Math.max(Aleft, Bleft) + Math.min(Aright, Bright)) / 2.0;
        } else {
          return Math.max(Aleft, Bleft);
        }
      } else if (Aleft > Bright) {
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
    System.out.println(obj.findMedian(nums1, nums2));

    int[] nums3 = { 1, 2, 35, 66, 78, 199 };
    int[] nums4 = { 3, 4, 5, 7, 9, 17, 18, 20, 79, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 210 };
    System.out.println(obj.findMedian(nums3, nums4));

    int[] nums5 = { 2, 32, 45, 67, 89, 112, 123, 134, 145, 156, 167, 178, 189 };
    int[] nums6 = { 1, 3, 5, 7, 9, 12, 15, 18, 40, 78, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 210 };
    System.out.println(obj.findMedian(nums5, nums6));

  }
}
