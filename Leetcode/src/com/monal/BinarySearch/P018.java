package com.monal.BinarySearch;

public class P018 {
class Solution {
    private int p1 = 0, p2 = 0;

    // Get the smaller value between nums1[p1] and nums2[p2] and move the pointer forwards.


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length, m = nums2.length;
        if ((n + m) % 2 == 0) {
            for (int i = 0; i < (n + m) / 2 - 1; ++i) {int tmp = getMin(nums1, nums2);}

            return (double) (getMin(nums1, nums2) + getMin(nums1, nums2)) / 2;
        } else {
            for (int i = 0; i < (n + m) / 2; ++i) {
                int tmp = getMin(nums1, nums2);
            }
            return getMin(nums1, nums2);
        }
    }

    private int getMin(int[] nums1, int[] nums2) {
        if (p1 < nums1.length && p2 < nums2.length) return nums1[p1] < nums2[p2] ? nums1[p1++] : nums2[p2++];
        else if (p1 < nums1.length) return nums1[p1++];
        else if (p2 < nums2.length) return nums2[p2++];
        return -1;
    }
}
class Solution1 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // ensure nums1 is the smaller array
        if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);

        int n = nums1.length, m = nums2.length;
        int start = 0, end = n;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            int i = mid;
            int j = ((n + m + 1) / 2 ) - i;
            // n + m array -> [...i...|...j...]

            // Elements around the cuts (use +/- infinity for out-of-bounds)
            int A_left_max  = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int A_right_min = (i == n) ? Integer.MAX_VALUE : nums1[i];
            int B_left_max  = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int B_right_min = (j == m) ? Integer.MAX_VALUE : nums2[j];

            // Check partition correctness
            if (A_left_max <= B_right_min && B_left_max <= A_right_min) {
                // correct partition found
                if ((n + m) % 2 == 1) {
                    return (double) Math.max(A_left_max, B_left_max);
                } else {
                    int leftMax = Math.max(A_left_max, B_left_max);
                    int rightMin = Math.min(A_right_min, B_right_min);
                    return (leftMax + rightMin) / 2.0;
                }
            } else if (A_left_max > B_right_min) {
                // i is too big; move left
                end = i - 1;
            } else {
                // B_left_max > A_right_min, i is too small; move right
                start = i + 1;
            }
        }

        // Should never reach here if inputs are valid
        throw new IllegalArgumentException("Input arrays are not sorted or invalid.");
    }
}

}
