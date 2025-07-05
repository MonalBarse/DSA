package com.monal.SlidingWindow;

import java.util.*;

/**
 * COMPLETE SLIDING WINDOW
 * 1. Core concepts
 * 3. Intermediate challenges
 * 4. Advanced problems
 * 5. Template patterns
 *
 * SLIDING WINDOW PATTERN OVERVIEW:
 * - Used for problems involving contiguous subarrays/substrings
 * - Two main types: Fixed size window and Variable size window
 * - Key insight: Instead of checking all subarrays (O(n²)), we slide a window
 * (O(n))
 */
public class SlidingWindow {

    // ======================== CORE CONCEPTS ========================

    /**
     * FIXED SIZE SLIDING WINDOW TEMPLATE
     *
     * Pattern Recognition:
     * - "Find something in subarray/substring of size K"
     * - "Maximum/minimum in every window of size K"
     *
     * Template:
     * 1. Calculate result for first window
     * 2. Slide window: remove left element, add right element
     * 3. Update result for each window
     */

    /**
     * VARIABLE SIZE SLIDING WINDOW TEMPLATE
     *
     * Pattern Recognition:
     * - "Longest subarray with condition X"
     * - "Shortest subarray with condition Y"
     * - "Count of subarrays with condition Z"
     *
     * Template:
     * 1. Expand window by moving right pointer
     * 2. When condition violated, shrink from left
     * 3. Track optimal result during valid windows
     */

    // ======================== BASIC PROBLEMS ========================

    /**
     * PROBLEM 1: Maximum Sum of Subarray of Size K (EASIEST - START HERE)
     *
     * THOUGHT PROCESS:
     * - Instead of calculating sum of every K-sized subarray (O(n*k))
     * - Calculate first window sum, then slide: subtract left, add right (O(n))
     *
     * PATTERN: Fixed Size Window
     */
    public static int maxSumSubarrayK(int[] arr, int k) {
        if (arr.length < k)
            return -1;

        // Step 1: Calculate sum of first window
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }

        int maxSum = windowSum;

        // Step 2: Slide the window
        for (int i = k; i < arr.length; i++) {
            // Remove leftmost element of previous window, add rightmost of current
            windowSum = windowSum - arr[i - k] + arr[i];
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }

    /**
     * PROBLEM 2: First Negative Number in Every Window of Size K
     *
     * THOUGHT PROCESS:
     * - We need to track first negative number in each window
     * - Use deque to store indices of negative numbers
     * - Remove indices that are out of current window
     *
     * PATTERN: Fixed Size Window + Deque for tracking
     */
    public static int[] firstNegativeInWindow(int[] arr, int k) {
        int n = arr.length;
        int[] result = new int[n - k + 1]; // n - k + 1 becuase we can have n-k+1 windows
        Deque<Integer> deque = new ArrayDeque<>(); // Store indices of negative numbers

        // Process first window
        for (int i = 0; i < k; i++) {
            if (arr[i] < 0) {
                deque.offer(i);
            }
        }

        // First window result
        result[0] = deque.isEmpty() ? 0 : arr[deque.peekFirst()];

        // Process remaining windows
        for (int i = k; i < n; i++) {
            // Remove indices that are out of current window
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // Add current element if negative
            if (arr[i] < 0) {
                deque.offer(i);
            }

            // Store result for current window
            result[i - k + 1] = deque.isEmpty() ? 0 : arr[deque.peekFirst()];
        }

        return result;
    }

    /**
     * PROBLEM 3: Count Occurrences of Anagrams in String
     * The problem involves counting how many times an anagram of a given pattern
     * appears in a text. for eg. text = "forxxorfxdofr" and pattern = "for"
     * we can find 3 occurrences of "for" as an anagram: ["for", "rof", "ofr"]
     *
     * THOUGHT PROCESS:
     * - Fixed window size = length of pattern
     * - Use frequency map to track character counts
     * - When all characters match required frequency, we found an anagram
     *
     * PATTERN: Fixed Size Window + Frequency Counting
     */

    public static int countAnagrams(String text, String pattern) {
        if (text.length() < pattern.length())
            return 0;

        int k = pattern.length(); // window of k characters
        Map<Character, Integer> patternMap = new HashMap<>();

        // Build frequency map for pattern
        for (char c : pattern.toCharArray()) {
            patternMap.put(c, patternMap.getOrDefault(c, 0) + 1);
        }

        // Sliding window map to track current window character counts
        Map<Character, Integer> windowMap = new HashMap<>();
        int count = 0;

        // Process first window
        for (int i = 0; i < k; i++) {
            char c = text.charAt(i);
            windowMap.put(c, windowMap.getOrDefault(c, 0) + 1);
        }

        if (windowMap.equals(patternMap))
            count++; // .equals checks if both maps have same keys and values

        // Slide the window
        for (int i = k; i < text.length(); i++) {
            // Add new character
            char newChar = text.charAt(i);
            windowMap.put(newChar, windowMap.getOrDefault(newChar, 0) + 1);

            // Remove old character
            char oldChar = text.charAt(i - k);
            // Only decrement if oldChar exists in the windowMap
            windowMap.put(oldChar, windowMap.get(oldChar) - 1);
            if (windowMap.get(oldChar) == 0) {
                windowMap.remove(oldChar);
            }

            // Check if current window is anagram
            if (windowMap.equals(patternMap))
                count++;
        }

        return count;
    }

    // ======================== INTERMEDIATE PROBLEMS ========================

    /**
     * PROBLEM 4: Longest Substring with K Unique Characters
     * - You have been given a string and an integer. You have to find the length of
     * the longest
     * substring that contains k unique characters.
     *
     * THOUGHT PROCESS:
     * - Variable size window problem
     * - Expand window while unique chars <= k
     * - When unique chars > k, shrink from left
     * - Track maximum length when exactly k unique chars
     *
     * PATTERN: Variable Size Window + HashMap
     */
    public static int longestSubstringKUnique(String s, int k) {
        if (s.length() == 0 || k == 0) return 0;

        Map<Character, Integer> charCount = new HashMap<>();
        int start = 0, maxLength = 0;

        for (int end = 0; end < s.length(); end++) {
            // Expand window
            char rightChar = s.charAt(end);
            charCount.put(rightChar, charCount.getOrDefault(rightChar, 0) + 1);
            // Shrink window if more than k unique characters
            while (charCount.size() > k) {
                char leftChar = s.charAt(start);
                charCount.put(leftChar, charCount.get(leftChar) - 1);
                if (charCount.get(leftChar) == 0) charCount.remove(leftChar);
                start++;
            }
            // Update result when exactly k unique characters
            if (charCount.size() == k) {
                maxLength = Math.max(maxLength, end - start + 1);
            }
        }
        return maxLength;
    }

    /*
     * PROBLEM 4.1 : Longest Substring with At Least K Repeating Characters
     * - Given a string s and an integer k, find the length of the longest substring
     * that contains at least k repeating characters.
     * * THOUGHT PROCESS:
     * - Variable size window problem
     * - Expand window while all characters have at least k frequency
     * - When any character's frequency drops below k, shrink from left
     */
    public int longestSubStringAtLeastKRepeatingChar(String s, int k) {
        int n = s.length();
        int result = 0;
        // we try every possible number of distinct chars in the window
        for (int t = 1; t <= 26; t++) {
            int[] count = new int[26];
            int start = 0, end = 0;
            int distinct = 0; // how many unique chars in window
            int atLeastK = 0; // how many chars reach >= k
            while (end < n) {
                // expand window by s.charAt(end)
                if (count[s.charAt(end) - 'a']++ == 0)
                    distinct++;
                if (count[s.charAt(end) - 'a'] == k)
                    atLeastK++;
                end++;
                // shrink while too many distinct
                while (distinct > t) {
                    if (count[s.charAt(start) - 'a']-- == k)
                        atLeastK--;
                    if (count[s.charAt(start) - 'a'] == 0)
                        distinct--;
                    start++;
                }
                // check if window is “good”
                if (distinct == t && atLeastK == t) {
                    result = Math.max(result, end - start);
                }
            }
        }
        return result;
    }

    /**
     * PROBLEM 5: Longest Substring Without Repeating Characters
     *
     * THOUGHT PROCESS:
     * - Variable size window
     * - Expand window and track characters seen
     * - When duplicate found, shrink window from left until no duplicate
     * - Track maximum length throughout
     *
     * PATTERN: Variable Size Window + Set/Map for uniqueness
     */
    public static int longestSubstringWithoutRepeating(String s) {
        if (s.length() == 0)
            return 0;

        Set<Character> seen = new HashSet<>();
        int left = 0, maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);

            // Shrink window until no duplicate
            while (seen.contains(rightChar)) {
                seen.remove(s.charAt(left));
                left++;
            }

            // Add current character and update result
            seen.add(rightChar);
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    /*
     * PROBLEM 5.1: Longes Substring with At Most K distinct characters
     * Given a string s and an integer k, find the length of the longest substring
     * that contains at most k distinct characters.
     *
     * THOUGHT PROCESS:
     * - Variable size window problem
     * - Expand window while unique characters <= k
     * - When unique characters > k, shrink from left
     * - Track maximum length when at most k unique characters
     */

    public static int longestSubstringAtMostKDistinct(String s, int k) {
        if (s.length() < k)
            return 0;
        Map<Character, Integer> charCount = new HashMap<>();
        int start = 0, maxLength = 0;

        for (int end = 0; end < s.length(); end++) {
            // Expand window
            char rightChar = s.charAt(end);
            charCount.put(rightChar, charCount.getOrDefault(rightChar, 0) + 1);

            // Shrink window if more than k unique characters
            while (charCount.size() > k) {
                char leftChar = s.charAt(start);
                charCount.put(leftChar, charCount.get(leftChar) - 1);
                if (charCount.get(leftChar) == 0) {
                    charCount.remove(leftChar);
                }
                start++;
            }
            // Update result when at most k unique characters
            maxLength = Math.max(maxLength, end - start + 1);
        }

        return maxLength;
    }

    /**
     * PROBLEM 6.1: Minimum Window Substring (HARD)
     * Given two strings s and t of lengths m and n respectively, return the minimum
     * window substring of s such that every character in t (including duplicates)
     * is included in the window. If there is no such substring, return the empty
     * string "".
     *
     * THOUGHT PROCESS:
     * - Find smallest window in string s that contains all characters of string t
     * - Use two frequency maps: one for target, one for current window
     * - Expand window until all characters matched
     * - Then shrink window while maintaining all characters
     *
     * PATTERN: Variable Size Window + Complex Condition Tracking
     */

    public static String minWindowSubstring(String s, String t) {
        if (s.length() < t.length())
            return "";

        // for t, build frequency map of characters
        Map<Character, Integer> targetMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetMap.put(c, targetMap.getOrDefault(c, 0) + 1);
        }

        // window map is just to track characters in current window
        Map<Character, Integer> windowMap = new HashMap<>();
        int left = 0, minLength = Integer.MAX_VALUE;
        int minStart = 0, formed = 0;
        int required = targetMap.size(); // Number of unique characters in t

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            windowMap.put(rightChar, windowMap.getOrDefault(rightChar, 0) + 1);

            // Check if current character's frequency matches target frequency
            if (targetMap.containsKey(rightChar) &&
                    windowMap.get(rightChar).intValue() == targetMap.get(rightChar).intValue()) {
                formed++;
            }

            while (left <= right && formed == required) {
                // if current window is valid, check if it's the smallest
                if (right - left + 1 < minLength) {
                    // Update minimum length and start index
                    minLength = right - left + 1;
                    minStart = left;
                }

                // Shrink window from left as we need to scan for all characters in t
                char leftChar = s.charAt(left);
                windowMap.put(leftChar, windowMap.get(leftChar) - 1);

                // if left character's frequency goes below target frequency,
                // we need to reduce formed count
                if (targetMap.containsKey(leftChar) &&
                        windowMap.get(leftChar) < targetMap.get(leftChar)) {
                    formed--;
                }
                left++;
            }
        }

        return minLength == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLength);
    }

    /**
     * PROBLEM 6.2 MINIMUM WINDOW SUBSEQUENCE
     * You are given two strings ‘s’ and ‘t’. your task is to find the
     * minimum (contiguous) substring ‘w’ of ‘s’, such that ‘t’ is a subsequence of
     * ‘w'.
     * A subsequence is a sequence that can be derived from another sequence by
     * removing zero or more elements, without changing the order. While, a
     * substring is a contiguous part of a string.
     */

    public static String minWindowSubsequence(String s, String t) {
        int minLen = Integer.MAX_VALUE;
        int startIndex = -1;
        int sLen = s.length(), tLen = t.length();

        // for loop is to iterate through each character in s
        // and try to find the minimum window that contains t as a subsequence
        for (int i = 0; i < sLen; i++) {
            if (s.charAt(i) != t.charAt(0))
                continue; // skip if current char doesn't match t's first char

            // Start after first match of t's first character
            int sPtr = i;
            int tPtr = 0;

            // Move both pointers to match subsequence
            while (sPtr < sLen && tPtr < tLen) {
                if (s.charAt(sPtr) == t.charAt(tPtr))
                    tPtr++;
                sPtr++;
            } // and if it did match the whole t i.e there exist a subsequence
              // then we can check if the current window is smaller than the previous one
            if (tPtr == tLen) {
                // Backtrack to find the start of the window
                int end = sPtr - 1;
                tPtr--; // go back to last matched character in t
                while (tPtr >= 0) {
                    if (s.charAt(sPtr - 1) == t.charAt(tPtr))
                        tPtr--;
                    sPtr--;
                }
                int windowStart = sPtr;
                if (end - windowStart + 1 < minLen) {
                    minLen = end - windowStart + 1;
                    startIndex = windowStart;
                }
            }
        }

        return startIndex == -1 ? "" : s.substring(startIndex, startIndex + minLen);
    }

    /*
     * PROBLEM 7.1: Number of Subarray with at Most K Distinct elements(MEDIUM)
     * THOUGHT PROCESS:
     * - Count subarrays with at most K distinct integers
     * - Use sliding window to maintain count of distinct integers
     * - Expand window until distinct count exceeds K
     * - When it exceeds, shrink from left until valid again
     */
    public static int subArrayWithAtMostK(int k, int[] arr) {
        if (k <= 0 || arr.length == 0)
            return 0;
        Map<Integer, Integer> countMap = new HashMap<>();
        int start = 0, result = 0;
        for (int end = 0; end < arr.length; end++) {
            countMap.put(arr[end], countMap.getOrDefault(arr[end], 0) + 1);

            // Shrink window until we have at most k distinct integers
            while (countMap.size() > k) {
                countMap.put(arr[start], countMap.get(arr[start]) - 1);
                if (countMap.get(arr[start]) == 0) {
                    countMap.remove(arr[start]);
                }
                start++;
            }
            // the result will be the number of subarrays ending at end since
            // all subarrays starting from start to end are valid
            result += end - start + 1;
        }
        return result;
    }
    /*
     * PROBLEM 7.2: Number of Subarray with at Least K Distinct elements(MEDIUM)
     * THOUGHT PROCESS:
     * - Count total subarrays that can be formed
     * - Subtract subarrays with at most K-1 distinct integers
     * - Use sliding window to count subarrays with at most K distinct integers
     * - Result = Total subarrays - Subarrays with at most K-1 distinct integers
     * - Total subarrays = n * (n + 1) / 2 (derived from combinatorial counting)
     * - Subarrays with at most K distinct integers = subArrayWithAtMostK(k, arr)
     * - Subarrays with at most K-1 distinct integers = subArrayWithAtMostK(k - 1,
     * arr)
     * - Result = Total subarrays - Subarrays with at most K-1 distinct integers
     */

    public static int subArrayWithAtLeastK(int k, int[] arr) {
        if (k <= 0 || arr.length == 0)
            return 0;

        // Total subarrays = n * (n + 1) / 2
        int n = arr.length;
        int totalSubarrays = n * (n + 1) / 2;

        // Subarrays with at most K-1 distinct integers
        int atMostKMinus1 = subArrayWithAtMostK(k - 1, arr);

        // Result = Total subarrays - Subarrays with at most K-1 distinct integers
        return totalSubarrays - atMostKMinus1;
    }

    /**
     * PROBLEM 7.3: Subarrays with K unique Integers (MEDIUM HARD)
     *
     * THOUGHT PROCESS:
     * - Direct counting is complex
     * - Key insight: subarrays with exactly K = subarrays with at most K -
     * subarrays with at most K-1
     * - Implement helper function for "at most K different integers"
     *
     * PATTERN: Variable Size Window + Mathematical Insight
     */
    public static int subarraysWithKDistinct(int[] nums, int k) {
        return atMostK(nums, k) - atMostK(nums, k - 1);
    }

    private static int atMostK(int[] nums, int k) {
        if (k == 0)
            return 0;

        Map<Integer, Integer> count = new HashMap<>();
        int left = 0, result = 0;

        for (int right = 0; right < nums.length; right++) {
            count.put(nums[right], count.getOrDefault(nums[right], 0) + 1);

            while (count.size() > k) {
                count.put(nums[left], count.get(nums[left]) - 1);
                if (count.get(nums[left]) == 0) {
                    count.remove(nums[left]);
                }
                left++;
            }

            // All subarrays ending at 'right' with at most k distinct elements
            result += right - left + 1;
        }
        return result;
    }

    // ======================== ADVANCED PROBLEMS ========================

    /**
     * PROBLEM 8: Sliding Window Maximum (Medium)
     *
     * THOUGHT PROCESS:
     * - For each window, we need maximum element
     * - Naive approach: scan each window = O(n*k)
     * - Better: Use deque to maintain potential maximums in decreasing order
     * - Front of deque always has index of maximum element in current window
     *
     * PATTERN: Fixed Size Window + Monotonic Deque
     */
    public static int[] slidingWindowMaximum(int[] nums, int k) {
        if (nums.length == 0 || k == 0)
            return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // Store indices

        // First k elements
        for (int i = 0; i < k; i++) {
            // Remove elements smaller than current from deque
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }

        // Store maximum for first window
        result[0] = nums[deque.peekFirst()];
        // Slide the window
        for (int i = k; i < n; i++) {
            // Remove indices that are out of current window
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }

            // Remove elements smaller than current from deque
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            // Store maximum for current window
            result[i - k + 1] = nums[deque.peekFirst()];
        }
        return result;

    }

    /**
     * PROBLEM 9: Longest Repeating Character Replacement (HARD)
     *
     * THOUGHT PROCESS:
     * - We can replace at most k characters
     * - In any valid window: window_size - max_frequency <= k
     * This is becuase we can replace all other characters with the most frequent
     * character
     * for example in "AABABBA" with k = 2, window "AABABBA" has max freq of B = 3,
     * A = 4
     *
     * - Use sliding window to find longest such window
     * - Track frequency of each character in current window
     *
     * PATTERN: Variable Size Window + Frequency Optimization
     */
    public static int characterReplacement(String s, int k) {
        Map<Character, Integer> count = new HashMap<>();
        int left = 0, maxFreq = 0, maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            count.put(rightChar, count.getOrDefault(rightChar, 0) + 1);
            maxFreq = Math.max(maxFreq, count.get(rightChar));

            // If window is invalid, shrink it
            while (right - left + 1 - maxFreq > k) {
                char leftChar = s.charAt(left);
                count.put(leftChar, count.get(leftChar) - 1);
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    /**
     * PROBLEM 10: Maximum Points from Cards
     * You can pick k cards from either end of the array. Find maximum sum.
     */
    public static int maxScore(int[] cardPoints, int k) {
        int n = cardPoints.length;
        int totalSum = 0;

        // Calculate total sum
        for (int point : cardPoints) {
            totalSum += point;
        }

        // If we take all cards
        if (k == n)
            return totalSum;

        // Find minimum sum of subarray of size (n-k)
        // The remaining cards after picking k cards optimally
        int windowSize = n - k;
        int windowSum = 0;

        // Calculate first window sum
        for (int i = 0; i < windowSize; i++) {
            windowSum += cardPoints[i];
        }

        int minWindowSum = windowSum;

        // Slide the window
        for (int i = windowSize; i < n; i++) {
            windowSum = windowSum - cardPoints[i - windowSize] + cardPoints[i];
            minWindowSum = Math.min(minWindowSum, windowSum);
        }

        return totalSum - minWindowSum;
    }

    /*
     * PROBLEM 11. Card Points Maximum Score (LeetCode 1423)
     * Given an integer array cardPoints and an integer k, return the maximum score
     * you can obtain by
     * choosing k cards from either the beginning or the end of the array.
     * You can only choose cards from the beginning or the end of the array.
     * You have to choose exactly k cards.
     *
     * THOUGHT PROCESS:
     */

    public int cardMaxScore(int[] cardPoints, int k) {
        int n = cardPoints.length, sum = 0, left = 0, total = 0;
        for (int num : cardPoints)
            total += num;

        for (int i = 0; i < n - k; i++)
            sum += cardPoints[i];
        int minWindowSum = sum;

        for (int right = n - k; right < n; right++) {
            sum += cardPoints[right]; // add right
            sum -= cardPoints[left++]; // remvoe left
            minWindowSum = Math.min(minWindowSum, sum); // update res
        }

        // - minWindowSum form total = max score
        return total - minWindowSum;
    }

    // ======================== TEST CASES ========================

    public static void main(String[] args) {
        // Test basic problems
        System.out.println("=== BASIC PROBLEMS TESTS ===");

        int[] arr1 = { 2, 1, 5, 1, 3, 2 };
        System.out.println("Max sum subarray of size 3: " + maxSumSubarrayK(arr1, 3)); // Expected: 9

        int[] arr2 = { -8, 2, 3, -6, 10 };
        int[] negatives = firstNegativeInWindow(arr2, 2);
        System.out.print("First negative in each window of size 2: ");
        System.out.println(Arrays.toString(negatives)); // Expected: [-8, 0, -6, -6]

        System.out.println("Anagram count: " + countAnagrams("forxxorfxdofr", "for")); // Expected: 3

        // Test intermediate problems
        System.out.println("\n=== INTERMEDIATE PROBLEMS TESTS ===");

        System.out.println("Longest substring with 2 unique chars: " +
                longestSubstringKUnique("aabbcc", 2)); // Expected: 4

        System.out.println("Longest substring without repeating: " +
                longestSubstringWithoutRepeating("abcabcbb")); // Expected: 3

        System.out.println("Minimum window substring: " +
                minWindowSubstring("ADOBECODEBANC", "ABC")); // Expected: "BANC"

        // Test advanced problems
        System.out.println("\n=== ADVANCED PROBLEMS TESTS ===");

        int[] arr3 = { 1, 3, -1, -3, 5, 3, 6, 7 };
        int[] maxWindows = slidingWindowMaximum(arr3, 3);
        System.out.println("Sliding window maximum: " + Arrays.toString(maxWindows));

        int[] arr4 = { 1, 2, 1, 2, 3 };
        System.out.println("Subarrays with exactly 2 distinct: " +
                subarraysWithKDistinct(arr4, 2)); // Expected: 7

        System.out.println("Character replacement: " +
                characterReplacement("ABAB", 2)); // Expected: 4
    }
}

/*
 * ======================== KEY TAKEAWAYS ========================
 *
 * 1. PATTERN RECOGNITION:
 * - Fixed size window: "size K", "every window"
 * - Variable size window: "longest/shortest", "maximum/minimum"
 *
 * 2. COMMON TECHNIQUES:
 * - Two pointers (left, right)
 * - HashMap/Set for tracking
 * - Deque for maintaining order
 * - Mathematical insights (at most K technique)
 *
 * 3. TIME COMPLEXITY:
 * - Usually O(n) instead of O(n²) or O(n³)
 * - Each element added and removed at most once
 *
 * 4. SPACE COMPLEXITY:
 * - Usually O(k) for tracking window contents
 * - Sometimes O(1) for simple cases
 *
 * 5. DEBUGGING TIPS:
 * - Print window contents at each step
 * - Verify window expansion/contraction logic
 * - Check boundary conditions (empty array, k=0, k>n)
 *
 * Remember: The key to mastering sliding window is recognizing the pattern
 * and knowing which template to apply!
 */

// ======================== REUSABLE TEMPLATES ========================
/*
 * // TEMPLATE 1: Fixed Size Window
 * public static void fixedSizeWindowTemplate(int[] arr, int k) {
 * // Process first window
 * // ... initialize window result ...
 *
 * // Slide window
 * for (int i = k; i < arr.length; i++) {
 * // Remove arr[i-k], add arr[i]
 * // Update result
 * }
 * }
 *
 * // TEMPLATE 2: Variable Size Window (Maximum)
 *
 * public static int variableWindowMaxTemplate(int[] arr, String condition) {
 * int left = 0, maxResult = 0;
 * // ... initialize tracking variables ...
 *
 * for (int right = 0; right < arr.length; right++) {
 * // Add arr[right] to window
 *
 * // Shrink window while condition violated
 * while ( condition violated ) {
 * // Remove arr[left] from window
 * left++;
 * }
 *
 * // Update result
 * maxResult = Math.max(maxResult, right - left + 1);
 * }
 *
 * return maxResult;
 * }
 *
 *
 * // TEMPLATE 3: Variable Size Window (Minimum)
 *
 * public static int variableWindowMinTemplate(int[] arr, String condition) {
 * int left = 0, minResult = Integer.MAX_VALUE;
 * // ... initialize tracking variables ...
 *
 * for (int right = 0; right < arr.length; right++) {
 * // Add arr[right] to window
 *
 * // Shrink window while condition satisfied
 * while (condition satisfied) {
 * minResult = Math.min(minResult, right - left + 1);
 * // Remove arr[left] from window
 * left++;
 * }
 * }
 *
 * return minResult == Integer.MAX_VALUE ? -1 : minResult;
 * }
 *
 * // TEMPLATE 4: Count Valid Windows - i.e for eg
 * "Count of subarrays with sum <= target"
 * public static int countValidWindows(int[] arr, String condition) {
 * int left = 0, count = 0;
 * for (int right = 0; right < arr.length; right++) {
 * // Add arr[right]
 * while (condition violated) {
 * // Remove arr[left]
 * left++;
 * }
 * // All windows ending at 'right' are valid
 * count += (right - left + 1);
 * }
 * return count;
 * }
 *
 * // Template 5: Sliding Window with Deque
 *
 * public static void dequeBasedSlidingWindow(int[] arr, int k) {
 * Deque<Integer> deque = new ArrayDeque<>(); // stores indices
 *
 * for (int i = 0; i < arr.length; i++) {
 * // 1. Remove indices outside current window
 * while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
 * deque.pollFirst();
 * }
 *
 * // 2. Maintain monotonic property
 * while (!deque.isEmpty() && shouldRemove(arr, deque.peekLast(), i)) {
 * deque.pollLast();
 * }
 *
 * // 3. Add current index
 * deque.offerLast(i);
 *
 * // 4. Process window when ready
 * if (i >= k - 1) {
 * // deque.peekFirst() gives index of optimal element
 * processWindow(arr, deque.peekFirst(), i);
 * }
 * }
 * }
 */

/*
 * Most sliding window problems reduce to one of two flavors:
 * 👉 You're either dealing with a **fixed-size window** or a **variable-size
 * window**.
 * Understanding *which flavor you’re in* is your first and most important
 * insight.
 *
 *
 * ### 🔹 Fixed Size Window: "Every window of size K…"
 *
 * These questions are usually worded like:
 *
 * "Find the maximum/minimum in **every** window of size `k`"
 * "First negative number in **each** window of size `k`"
 * "Average of **every** subarray of size `k`"
 *
 * You're being asked to **slide a window of size `k` across the array** and
 * compute something at every step.
 *
 * 📌 In this case:
 *
 * You use a **`for` loop for `right`**, and calculate `left = right - k + 1`
 * You **only move forward** — no shrinking needed
 * You **reset or update your data structure** as the window moves
 *
 * 💡 These problems often use:
 *
 * A `Deque` when **you need to maintain order**, such as "first" or "maximum"
 * Simple math or prefix sums for efficient calculations
 *
 * ---
 *
 * ### 🔸 Variable Size Window: "Longest/Shortest subarray that…"
 *
 * These problems are about **stretching or shrinking the window** based on some
 * condition. Examples:
 *
 * "Longest substring with at most K distinct characters"
 * "Smallest subarray with sum ≥ target"
 * "Longest substring with all same characters after at most K replacements"
 *
 * Here, the **window size is not fixed** — it grows or shrinks to satisfy a
 * condition.
 *
 * 📌 In this case:
 *
 * Use **two pointers**: `left` and `right`
 * Expand the window with `right++`
 * Shrink the window from the `left` when the condition is violated
 * At each step, check whether the window is valid, and update result
 * accordingly
 *
 * 💡 These problems often use:
 *
 * `HashMap` or `int[]` for character or number frequencies
 * A running count or sum
 * Smart checks like `(window length - max frequency)` to determine validity
 *
 * ---
 *
 * ### 🔁 Two Pointers: The Core Engine
 *
 * The `left` and `right` pointers are your **sliding frame**. Together they
 * define your current candidate window.
 *
 * In **fixed-size**, you mostly **control `right`** and calculate `left`
 * indirectly.
 *
 * In **variable-size**, both `left` and `right` are dynamic:
 *
 * `right` keeps moving forward (expanding the window)
 * `left` moves forward **only when** your window becomes invalid
 *
 * Why does this work so well?
 *
 * Because this setup ensures **O(n)** processing — every element is added and
 * removed at most once.
 *
 * ---
 *
 * ### 🧮 Frequency Tracking: HashMap, Set, or Array?
 *
 * Here’s where sliding windows come alive with extra tools:
 *
 * Use a **HashMap\<Character, Integer>** when:
 *
 * The character set is unknown or variable (like Unicode)
 * You need to track frequency (`char → count`)
 * You want to shrink the window based on distinct character count
 *
 * Use a **Set** when:
 *
 * You only care about *presence*, not frequency (e.g., no duplicates)
 *
 * Use a **`int[26]` or `int[128]`** frequency array when:
 *
 * The character set is fixed (A-Z, a-z, ASCII)
 * You want speed and constant-time operations
 * You want to avoid map overhead in high-performance scenarios
 *
 * ---
 *
 * ### 🧰 Deque: Your Tool for Ordered Sliding Window
 *
 * Not all windows are happy with just frequency.
 *
 * In some cases, the *order* matters. Like:
 *
 * "First negative number in the window"
 * "Maximum in the window"
 *
 * In these, a **Deque** is your best friend. It lets you:
 *
 * Add to both ends
 * Remove from both ends
 *
 * So you can:
 *
 * Keep the “most important” elements (like max, min, or first negative) at the
 * front
 * Discard irrelevant ones from the back as you move forward
 *
 * You’re essentially maintaining a **monotonic queue** — one that remains
 * sorted by importance.
 *
 * ---
 *
 * ### ✨ Mathematical Insights: "At Most K" Trick
 *
 * Sometimes, rather than tracking exact counts, you can **transform the
 * question**.
 *
 * For example:
 *
 * Count of subarrays with **exactly K** distinct elements
 * → `atMost(K) - atMost(K - 1)`
 *
 * Count of subarrays with **at least K** distinct elements
 * → `totalSubarrays - atMost(K - 1)`
 *
 * These techniques work because **prefix sums** of counts give you powerful
 * tools for slicing the data differently.
 *
 * So instead of brute-forcing, you break a hard problem into two easier ones
 * and subtract their results.
 *
 * This is **mathematical laziness at its best** — you reduce logic, not
 * precision.
 *
 * ---
 *
 * ## 🎯 Final Mental Model
 *
 * Think of it like this:
 *
 * Sliding window = You are managing a **stretchy window** that walks through
 * the array.
 * You use **pointers** to control the boundaries.
 * You use **maps/arrays/deques** to track what’s inside the window.
 * You use **conditions** to shrink or hold the window.
 * You apply **math or count tricks** to convert indirect problems into direct
 * ones.
 *
 * If you can learn to “see” the shape of the problem (fixed-size,
 * variable-size, tracking max/min, uniqueness, frequency…), then you’ll
 * instinctively know:
 *
 * What tools you need
 * How to use them
 * And how to avoid brute force
 *
 */