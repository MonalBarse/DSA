package CodeForces.src.com.contestOne;

import java.util.*;

class Solution {
    private static final int MOD = 1000000007;

    public int beautySum(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> gcdCount = new HashMap<>();

        // For each starting position
        for (int i = 0; i < n; i++) {
            // Single element subsequence
            gcdCount.put(nums[i], gcdCount.getOrDefault(nums[i], 0) + 1);

            // Map to store GCD -> count for subsequences ending at current position
            Map<Integer, Integer> currentGcds = new HashMap<>();
            currentGcds.put(nums[i], 1);

            // Extend subsequences from previous positions
            for (int j = i + 1; j < n; j++) {
                if (nums[j] <= nums[j-1]) {
                    // Can't extend any subsequence, but can start new single element
                    currentGcds.clear();
                    currentGcds.put(nums[j], 1);
                } else {
                    // Can extend existing subsequences and start new one
                    Map<Integer, Integer> nextGcds = new HashMap<>();

                    // Add single element subsequence
                    nextGcds.put(nums[j], 1);

                    // Extend existing subsequences
                    for (Map.Entry<Integer, Integer> entry : currentGcds.entrySet()) {
                        int gcd = entry.getKey();
                        int count = entry.getValue();
                        int newGcd = gcd(gcd, nums[j]);
                        nextGcds.put(newGcd, nextGcds.getOrDefault(newGcd, 0) + count);
                    }

                    currentGcds = nextGcds;
                }

                // Add counts to global map
                for (Map.Entry<Integer, Integer> entry : currentGcds.entrySet()) {
                    int gcd = entry.getKey();
                    int count = entry.getValue();
                    gcdCount.put(gcd, gcdCount.getOrDefault(gcd, 0) + count);
                }
            }
        }

        // Calculate total beauty
        long totalBeauty = 0;
        for (Map.Entry<Integer, Integer> entry : gcdCount.entrySet()) {
            int gcd = entry.getKey();
            int count = entry.getValue();
            totalBeauty = (totalBeauty + (long) gcd * count) % MOD;
        }

        return (int) totalBeauty;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        System.out.println(s.beautySum(new int[]{1, 2, 3})); // Expected: 10
        System.out.println(s.beautySum(new int[]{4, 6}));    // Expected: 12
    }
}