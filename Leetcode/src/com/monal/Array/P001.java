
package com.monal.Array;

public class P001 {
    public static void main(String[] args) {
        Solution solution = new P001().new Solution();

        int[] arr1 = { 3, 4, 5, -1, 1, 2 }; // Rotated sorted -> true
        int[] arr2 = { 0, 1, 2, 3, 4, 5 }; // Already sorted -> true
        int[] arr3 = { 2, 0, 1, 3, 4, 5 }; // Not rotated sorted -> false
        int[] arr4 = { 1, 3, 2, 4, 5 }; // Not rotated sorted -> false

        System.out.println(solution.check(arr1)); // Expected: true
        System.out.println(solution.check(arr2)); // Expected: true
        System.out.println(solution.check(arr3)); // Expected: false
        System.out.println(solution.check(arr4)); // Expected: false
    }

    class Solution {
        public boolean check(int[] arr) {
            int count = 0;
            int n = arr.length;

            // Count the number of times the array is not in increasing order
            for (int i = 0; i < n; i++) {
                if (arr[i] > arr[(i + 1) % n]) {
                    count++;
                }
            }

            // If the array is rotated sorted, the count should be at most 1
            return count <= 1;
        }
    }
}
