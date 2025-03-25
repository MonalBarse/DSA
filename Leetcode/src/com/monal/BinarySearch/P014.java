package com.monal.BinarySearch;

/* Given two sorted arrays a[] and b[] and an element k, the task is to find the element that would be at the kth position of the combined sorted array.

Examples :
  Input: a[] = [2, 3, 6, 7, 9], b[] = [1, 4, 8, 10], k = 5
  Output: 6
  Explanation: The final combined sorted array would be [1, 2, 3, 4, 6, 7, 8, 9, 10]. The 5th element of this array is 6.

  Input: a[] = [100, 112, 256, 349, 770], b[] = [72, 86, 113, 119, 265, 445, 892], k = 7
  Output: 256
  Explanation: Combined sorted array is [72, 86, 100, 112, 113, 119, 256, 265, 349, 445, 770, 892]. The 7th element of this array is 256.

Constraints:
  1 <= a.size(), b.size() <= 106
  1 <= k <= a.size() + b.size()
  0 <= a[i], b[i] < 108
*/
public class P014 {
  class Solution {
    public int kthElement(int a[], int b[], int k) {
      int n = a.length, m = b.length;

      // Ensure a[] is the smaller array to optimize binary search
      if (n > m)
        return kthElement(b, a, k);

      // Binary search on the smaller array
      int start = Math.max(0, k - m), end = Math.min(n, k);

      while (start <= end) {
        int midA = start + (end - start) / 2; // Number of elements from a[]
        int midB = k - midA; // The remaining k elements must come from b[]

        /*
         * WE are trying to partition the two arrays such that:
         * leftA= [ first X (midA) elements from a ] | A_Right = [ remaining elements in
         * a ]
         * B_Left = [ first (K - midA) elements from b ] | B_Right = [remaining elements
         * in b
         */

        // Compute left and right partitions
        int leftA = (midA > 0) ? a[midA - 1] : Integer.MIN_VALUE;
        int leftB = (midB > 0) ? b[midB - 1] : Integer.MIN_VALUE;
        int rightA = (midA < n) ? a[midA] : Integer.MAX_VALUE;
        int rightB = (midB < m) ? b[midB] : Integer.MAX_VALUE;

        // Check if partition is valid
        if (leftA <= rightB && leftB <= rightA) {
          return Math.max(leftA, leftB);
        } else if (leftA > rightB) {
          end = midA - 1; // the X we guessed is wrong and should be smaller
        } else {
          start = midA + 1; // the X we guessed is wrong and should be larger
        }
      }

      return -1; // never reached
    }

  }

  public static void main(String[] args) {

  }
}
