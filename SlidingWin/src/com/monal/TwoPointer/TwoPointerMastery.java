package com.monal.TwoPointer;

import java.util.*;

/**
 * COMPLETE TWO POINTER GUIDE - FROM BASICS TO MASTERY
 *
 * This file contains everything you need to master two pointer technique:
 * 1. Core concepts and patterns
 * 2. Basic problems with detailed explanations
 * 3. Intermediate challenges
 * 4. Advanced problems
 * 5. Template patterns you can reuse
 *
 * TWO POINTER PATTERN OVERVIEW:
 * - Used to solve problems involving pairs, triplets, or subarrays
 * - Reduces time complexity from O(n²) or O(n³) to O(n) or O(n²)
 * - Main types: Opposite Direction, Same Direction, Fast-Slow
 */
public class TwoPointerMastery {

    // ======================== CORE CONCEPTS ========================

    /**
     * OPPOSITE DIRECTION (COLLISION) PATTERN
     *
     * Pattern Recognition:
     * - "Two Sum in sorted array"
     * - "Palindrome checking"
     * - "Container with most water"
     * - "3Sum, 4Sum problems"
     *
     * Template:
     * left = 0, right = n-1
     * while left < right:
     *     if condition met: return/process
     *     else if sum < target: left++
     *     else: right--
     */

    /**
     * SAME DIRECTION (FAST-SLOW) PATTERN
     *
     * Pattern Recognition:
     * - "Remove duplicates"
     * - "Move zeros to end"
     * - "Partition array"
     * - "Merge sorted arrays"
     *
     * Template:
     * slow = 0
     * for fast in range(n):
     *     if condition:
     *         process(slow, fast)
     *         slow++
     */

    /**
     * FAST-SLOW POINTER (FLOYD'S CYCLE) PATTERN
     *
     * Pattern Recognition:
     * - "Detect cycle in linked list"
     * - "Find middle of linked list"
     * - "Happy number problem"
     *
     * Template:
     * slow = start, fast = start
     * while fast != null && fast.next != null:
     *     slow = slow.next
     *     fast = fast.next.next
     *     if slow == fast: cycle detected
     */

    // ======================== BASIC PROBLEMS ========================

    /**
     * PROBLEM 1: Two Sum in Sorted Array (EASIEST - START HERE)
     *
     * THOUGHT PROCESS:
     * - Array is sorted, so we can use opposite direction pointers
     * - If sum < target, we need larger numbers (move left pointer right)
     * - If sum > target, we need smaller numbers (move right pointer left)
     * - If sum == target, we found our answer
     *
     * PATTERN: Opposite Direction
     * TIME: O(n), SPACE: O(1)
     */
    public static int[] twoSumSorted(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;

        while (left < right) {
            int sum = numbers[left] + numbers[right];

            if (sum == target) {
                return new int[]{left + 1, right + 1}; // 1-indexed
            } else if (sum < target) {
                left++; // Need larger sum
            } else {
                right--; // Need smaller sum
            }
        }

        return new int[]{-1, -1}; // No solution found
    }
    /**
     * PROBLEM 1.1: Two Sum in Unsorted Array
     * THOUGHT PROCESS:
     *  - Use a HashMap to store indices of elements
     *  - For each element, check if target - element exists in map
     *  - If found, return indices
     *  - If not found, add element to map
     */
    public static int[] twoSumUnsorted(int [] arr, int target){
        Map<Integer, Integer> map = new HashMap<>();
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            int rem = target - arr[i];
            if(map.containsKey(rem)) return new int[]{map.get(rem), i};
            else map.put(arr[i], i);
        }
        return new int[]{-1, -1}; // No solution found
    }
    // NOTE: To Go with the pattern of Two Pointer, we can sort the array first
    // and then apply the twoSumSorted method, but in this case it would not be optimal
    // but if it was a 3Sum problem, we could sort the array as in 3sum this above method
    // will not work and sorting the array will help us to use the two pointer technique
    // which will be faster and optimal than using a HashMap.
    // since map will take O(n) time and space, and sorting will take O(nlogn) time
    // and then we can use the two pointer technique to find the pairs in O(n) time
    // Total in both cases for 2sum will: 1. HashMap: O(n) + O(n) = O(n), 2. Sorting + Two Pointer: O(nlogn) + O(n) = O(nlogn)
    // O(N) is better than O(NlogN) so we will use the HashMap approach for 2Sum in Unsorted Array

    /**
     * PROBLEM 2: Valid Palindrome
     *
     * THOUGHT PROCESS:
     * - Compare characters from both ends moving inward
     * - Skip non-alphanumeric characters
     * - Convert to lowercase for comparison
     * - If any mismatch found, not a palindrome
     *
     * PATTERN: Opposite Direction
     * TIME: O(n), SPACE: O(1)
     */
    public static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;

        while (left < right) {
            // Skip non-alphanumeric characters from left
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            // Skip non-alphanumeric characters from right
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            // Compare characters (case-insensitive)
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    /**
     * PROBLEM 3: Remove Duplicates from Sorted Array
     *
     * THOUGHT PROCESS:
     * - Use slow pointer to track position for next unique element
     * - Use fast pointer to scan through array
     * - When fast finds new unique element, place it at slow position
     * - Slow pointer gives us the length of unique elements
     *
     * PATTERN: Same Direction (Slow-Fast)
     * TIME: O(n), SPACE: O(1)
     */
    public static int removeDuplicates(int[] arr) {
        if (arr.length == 0) return 0;

        int slow = 1; // Position for next unique element

        for (int fast = 1; fast < arr.length; fast++) {
            if(arr[fast] == arr[fast - 1]) {
                continue; // Skip duplicates
            }
            // Found new unique element
            arr[slow] = arr[fast];
            slow++;

        }

        return slow; // Length of unique elements
    }

    /**
     * PROBLEM 4: Move Zeros to End
     *
     * THOUGHT PROCESS:
     * - Use slow pointer to track position for next non-zero element
     * - Use fast pointer to find non-zero elements
     * - When fast finds non-zero, swap with slow position
     * - This naturally moves zeros to the end
     *
     * PATTERN: Same Direction (Slow-Fast)
     * TIME: O(n), SPACE: O(1)
     */
    public static void moveZeroes(int[] nums) {
        int slow = 0; // Position for next non-zero element

        // Move all non-zero elements to front
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != 0) {
                // Swap only if pointers are different (optimization)
                if (slow != fast) {
                    int temp = nums[slow];
                    nums[slow] = nums[fast];
                    nums[fast] = temp;
                }
                slow++;
            }
        }
    }

    // ======================== INTERMEDIATE PROBLEMS ========================

    /**
     * PROBLEM 5: Container With Most Water
     * COntainer with most water (LeetCode 11)
     *
     *
     * THOUGHT PROCESS:
     * - Water amount = min(height[left], height[right]) * (right - left)
     * - To maximize water, we need to maximize both height and width
     * - Always move pointer with smaller height (keep the taller one)
     * IMP -
     * - Why? Moving taller pointer can only decrease width without height benefit
     *
     * PATTERN: Opposite Direction with Greedy Choice
     * TIME: O(n), SPACE: O(1)
     */
    public static int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int maxWater = 0;

        while (left < right) {
            int width = right - left;
            int waterHeight = Math.min(height[left], height[right]);
            int currentWater = width * waterHeight;

            maxWater = Math.max(maxWater, currentWater);

            // Move pointer with smaller height
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxWater;
    }

    /**
     * PROBLEM 6: 3Sum (Find all unique triplets that sum to zero)
     *
     * THOUGHT PROCESS:
     * - Fix first element, then use 2Sum on remaining array
     * - Sort array first to use two pointers for 2Sum
     * - Skip duplicates to avoid duplicate triplets
     * - For each fixed element, find pairs that sum to -element
     *
     * PATTERN: Fixed Element + Two Pointer
     * TIME: O(n²), SPACE: O(1) excluding result
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length < 3) return result;

        Arrays.sort(nums); // Sort for two pointer approach

        // first loop is to fix the first element
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates for first element
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int target = -nums[i]; // We want nums[j] + nums[k] = target
            int left = i + 1, right = nums.length - 1;

            while (left < right) {
                int sum = nums[left] + nums[right];

                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // Skip duplicates for second element
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    // Skip duplicates for third element
                    while (left < right && nums[right] == nums[right - 1]) right--;

                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }

        return result;
    }
    // if we have used a hashmap to store the pairs, the time complexity would be O(n²) and space complexity would be O(n)
    // but here we have used the two pointer technique after sorting the array
    // which gives us a time complexity of O(n²) and space complexity of O(1) excluding the result list

    /**
     * PROBLEM 7: Sort Colors (Dutch National Flag)
     *
     * THOUGHT PROCESS:
     * - We have 3 colors: 0, 1, 2
     * - Use 3 pointers: low (for 0s), mid (current), high (for 2s)
     * - Invariant: [0...low-1] all 0s, [low...mid-1] all 1s, [high+1...n-1] all 2s
     * - Process elements at mid: if 0 swap with low, if 2 swap with high
     *
     * PATTERN: Three Pointers (Partitioning)
     * TIME: O(n), SPACE: O(1)
     */
    public static void sortColors(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;

        while (mid <= high) {
            switch (nums[mid]) {
                case 0 -> {
                    // Swap with low and move both pointers
                    swap(nums, low, mid);
                    low++;
                    mid++;
                }
                case 1 -> {
                    // Correct position, just move mid
                    mid++;
                }
                case 2 -> {
                    // Swap with high, don't move mid (need to check swapped element)
                    swap(nums, mid, high);
                    high--;
                }
                default -> {
                    // Handle unexpected values if necessary
                    mid++;
                }
            }
        }
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * PROBLEM 8: Trapping Rain Water
     *
     * THOUGHT PROCESS:
     * - Water at position i = min(maxLeft[i], maxRight[i]) - height[i]
     * - Use two pointers to avoid extra space for maxLeft/maxRight arrays
     * - Move pointer with smaller max (water level determined by smaller side)
     * - Keep track of current max from both sides
     *
     * PATTERN: Opposite Direction with State Tracking
     * TIME: O(n), SPACE: O(1)
     */
    public static int trap(int[] height) {
        if (height.length == 0) return 0;

        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int totalWater = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                // Process left side
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    totalWater += leftMax - height[left];
                }
                left++;
            } else {
                // Process right side
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    totalWater += rightMax - height[right];
                }
                right--;
            }
        }

        return totalWater;
    }

    // ======================== ADVANCED PROBLEMS ========================

    /**
     * PROBLEM 9: 4Sum (Find all unique quadruplets that sum to target)
     *
     * THOUGHT PROCESS:
     * - Extension of 3Sum: fix two elements, then use 2Sum
     * - Sort array and use nested loops with two pointers
     * - Skip duplicates at all levels to avoid duplicate quadruplets
     * - Use early termination optimizations
     *
     * PATTERN: Fixed Elements + Two Pointer
     * TIME: O(n³), SPACE: O(1) excluding result
     */
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.length < 4) return result;

        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 3; i++) {
            // Skip duplicates for first element
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            // Early termination: if smallest possible sum > target
            if ((long)nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) break;

            // Early termination: if largest possible sum < target
            if ((long)nums[i] + nums[nums.length - 3] + nums[nums.length - 2] + nums[nums.length - 1] < target) continue;

            for (int j = i + 1; j < nums.length - 2; j++) {
                // Skip duplicates for second element
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                int left = j + 1, right = nums.length - 1;
                long twoSumTarget = (long)target - nums[i] - nums[j];

                while (left < right) {
                    long sum = nums[left] + nums[right];

                    if (sum == twoSumTarget) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));

                        // Skip duplicates
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;

                        left++;
                        right--;
                    } else if (sum < twoSumTarget) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }

        return result;
    }

    /**
     * PROBLEM 10: Linked List Cycle Detection (Floyd's Cycle Detection)
     *
     * THOUGHT PROCESS:
     * - Use fast and slow pointers (tortoise and hare)
     * - Fast moves 2 steps, slow moves 1 step
     * - If there's a cycle, fast will eventually meet slow
     * - If no cycle, fast will reach null
     *
     * PATTERN: Fast-Slow Pointers
     * TIME: O(n), SPACE: O(1)
     */
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode slow = head;
        ListNode fast = head.next;

        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false; // No cycle
            }
            slow = slow.next;
            fast = fast.next.next;
        }

        return true; // Cycle detected
    }

    /**
     * PROBLEM 11: Find Cycle Start in Linked List
     *
     * THOUGHT PROCESS:
     * - First detect cycle using Floyd's algorithm
     * - Then find cycle start: move one pointer to head, keep other at meeting point
     * - Move both one step at a time until they meet - that's the cycle start
     * - Mathematical proof: distance from head to cycle start = distance from meeting point to cycle start
     *
     * PATTERN: Fast-Slow + Mathematical Insight
     * TIME: O(n), SPACE: O(1)
     */
    public static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null;

        // Phase 1: Detect if cycle exists
        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
        }

        // No cycle found
        if (fast == null || fast.next == null) return null;

        // Phase 2: Find cycle start
        slow = head; // Reset slow to head
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow; // Cycle start
    }

    /**
     * PROBLEM 12: Minimum Window Substring (Using Two Pointers)
     *
     * THOUGHT PROCESS:
     * - Use expanding window to include all characters of t
     * - Use contracting window to minimize size while maintaining all characters
     * - Track character frequencies and valid window condition
     *
     * PATTERN: Same Direction (Expanding-Contracting Window)
     * TIME: O(|s| + |t|), SPACE: O(|s| + |t|)
     */
    public static String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";

        Map<Character, Integer> need = new HashMap<>();
        Map<Character, Integer> window = new HashMap<>();

        // Build need map
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = 0;
        int valid = 0; // Number of characters that satisfy the requirement
        int start = 0, len = Integer.MAX_VALUE;

        while (right < s.length()) {
            // Expand window
            char c = s.charAt(right);
            right++;

            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    valid++;
                }
            }

            // Contract window
            while (valid == need.size()) {
                // Update minimum window
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }

                char d = s.charAt(left);
                left++;

                if (need.containsKey(d)) {
                    if (window.get(d).equals(need.get(d))) {
                        valid--;
                    }
                    window.put(d, window.get(d) - 1);
                }
            }
        }

        return len == Integer.MAX_VALUE ? "" : s.substring(start, start + len);
    }

    // ======================== PRACTICE PROBLEMS ========================

    /**
     * PROBLEM 13: Squares of Sorted Array
     * Given sorted array (can have negatives), return squares in sorted order
     */
    public static int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        int left = 0, right = n - 1;
        int index = n - 1; // Fill result from right to left

        while (left <= right) {
            int leftSquare = nums[left] * nums[left];
            int rightSquare = nums[right] * nums[right];

            if (leftSquare > rightSquare) {
                result[index] = leftSquare;
                left++;
            } else {
                result[index] = rightSquare;
                right--;
            }
            index--;
        }

        return result;
    }

    /**
     * PROBLEM 14: Partition Array by Pivot
     * Partition array so elements < pivot come first, then pivot, then > pivot
     */
    public static int[] pivotArray(int[] nums, int pivot) {
        int[] result = new int[nums.length];
        int index = 0;

        // First pass: elements less than pivot
        for (int num : nums) {
            if (num < pivot) {
                result[index++] = num;
            }
        }

        // Second pass: elements equal to pivot
        for (int num : nums) {
            if (num == pivot) {
                result[index++] = num;
            }
        }

        // Third pass: elements greater than pivot
        for (int num : nums) {
            if (num > pivot) {
                result[index++] = num;
            }
        }

        return result;
    }


    // ======================== TEST CASES ========================

    public static void main(String[] args) {
        // Test basic problems
        System.out.println("=== BASIC PROBLEMS TESTS ===");

        int[] nums1 = {2, 7, 11, 15};
        System.out.println("Two Sum: " + Arrays.toString(twoSumSorted(nums1, 9))); // [1, 2]

        System.out.println("Is Palindrome: " + isPalindrome("A man, a plan, a canal: Panama")); // true

        int[] nums2 = {1, 1, 2, 2, 3, 3, 3};
        System.out.println("Remove duplicates length: " + removeDuplicates(nums2)); // 3

        int[] nums3 = {0, 1, 0, 3, 12};
        moveZeroes(nums3);
        System.out.println("After moving zeros: " + Arrays.toString(nums3)); // [1, 3, 12, 0, 0]

        // Test intermediate problems
        System.out.println("\n=== INTERMEDIATE PROBLEMS TESTS ===");

        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println("Max water area: " + maxArea(heights)); // 49

        int[] nums4 = {-1, 0, 1, 2, -1, -4};
        System.out.println("3Sum: " + threeSum(nums4)); // [[-1, -1, 2], [-1, 0, 1]]

        int[] colors = {2, 0, 2, 1, 1, 0};
        sortColors(colors);
        System.out.println("Sorted colors: " + Arrays.toString(colors)); // [0, 0, 1, 1, 2, 2]

        int[] trapArray = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("Trapped water: " + trap(trapArray)); // 6

        // Test advanced problems
        System.out.println("\n=== ADVANCED PROBLEMS TESTS ===");

        int[] nums5 = {1, 0, -1, 0, -2, 2};
        System.out.println("4Sum: " + fourSum(nums5, 0)); // Various quadruplets

        System.out.println("Min window: " + minWindow("ADOBECODEBANC", "ABC")); // "BANC"

        // Test practice problems
        System.out.println("\n=== PRACTICE PROBLEMS TESTS ===");

        int[] nums6 = {-4, -1, 0, 3, 10};
        System.out.println("Sorted squares: " + Arrays.toString(sortedSquares(nums6))); // [0, 1, 9, 16, 100]

        int[] nums7 = {9, 12, 5, 10, 14, 3, 10};
        System.out.println("Pivot array: " + Arrays.toString(pivotArray(nums7, 10))); // [9, 5, 3, 10, 10, 12, 14]
    }
}

/*
======================== KEY TAKEAWAYS ========================

1. PATTERN RECOGNITION:
   - Opposite Direction: "Two Sum", "Palindrome", "Container problems"
   - Same Direction: "Remove/Move elements", "Partitioning"
   - Fast-Slow: "Cycle detection", "Find middle"

2. COMMON TECHNIQUES:
   - Sort first for opposite direction problems
   - Maintain invariants in same direction problems
   - Use mathematical insights for cycle problems

3. TIME COMPLEXITY IMPROVEMENTS:
   - Two Sum: O(n²) → O(n) with sorting
   - 3Sum: O(n³) → O(n²) with two pointers
   - Cycle detection: O(n) space → O(1) space

4. SPACE OPTIMIZATION:
   - Most two pointer solutions use O(1) extra space
   - Avoid creating extra arrays when possible

5. DEBUGGING TIPS:
   - Draw the array and pointer positions
   - Verify loop invariants
   - Check boundary conditions
   - Test with sorted and unsorted arrays

======================== WHEN TO USE EACH PATTERN ========================

OPPOSITE DIRECTION:
- Array is sorted or can be sorted
- Looking for pairs/triplets with specific sum
- Palindrome-like properties
- Optimization problems (max area, etc.)

SAME DIRECTION:
- Modifying array in-place
- Partitioning or filtering
- Maintaining relative order
- Remove/move elements

FAST-SLOW:
- Linked list problems
- Cycle detection
- Finding middle element
- Problems with "meeting point" concept

======================== PRACTICE STRATEGY ========================

1. Master basic patterns first (Problems 1-4)
2. Understand the "why" behind pointer movements
3. Practice recognizing which pattern to use
4. Time yourself on implementation
5. Try variations of each problem
6. Focus on edge cases and boundary conditions

Remember: Two pointers is about reducing nested loops by using
the sorted property or maintaining specific invariants!
*/

/*
 // ======================== REUSABLE TEMPLATES ========================

    // * TEMPLATE 1: Opposite Direction (Collision)
    public static void oppositeDirectionTemplate(int[] arr, int target) {
        int left = 0, right = arr.length - 1;

        while (left < right) {
            int sum = arr[left] + arr[right];

            if (sum == target) {
                // Process found pair
                // return or add to result
            } else if (sum < target) {
                left++; // Need larger sum
            } else {
                right--; // Need smaller sum
            }
        }
    }

    //  * TEMPLATE 2: Same Direction (Fast-Slow)
    public static void sameDirectionTemplate(int[] arr) {
        int slow = 0;

        for (int fast = 0; fast < arr.length; fast++) {
            if (condition to place element at slow position ) {
                arr[slow] = arr[fast];
                slow++;
            }
        }

        // slow now points to the length of valid elements
    }

    //  * TEMPLATE 3: Fast-Slow Pointers (Cycle Detection)
    public static boolean cycleDetectionTemplate(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true; // Cycle detected
            }
        }

        return false; // No cycle
    }

    //  * TEMPLATE 4: Expanding-Contracting Window
    public static String windowTemplate(String s, String condition) {
        int left = 0, right = 0;
        // ... initialize tracking variables ...

        while (right < s.length()) {
            // Expand window
            char c = s.charAt(right);
            right++;
            // ... update window state ...

            // Contract window while condition met
            while ( window condition satisfied ) {
                // ... update result ...

                char d = s.charAt(left);
                left++;
                // ... update window state ...
            }
        }

        return "result";
    }
 */