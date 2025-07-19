package com.monal;

import java.util.*;

/**
 * COMPREHENSIVE GREEDY PROBLEMS LEARNING RESOURCE
 * ================================================
 *
 * WHAT IS A GREEDY ALGORITHM?
 * - A greedy algorithm makes the locally optimal choice at each step
 * - It hopes to find a global optimum by making local optimal choices
 * - Key insight: "Take what looks best right now"
 *
 * WHEN TO USE GREEDY APPROACH?
 * 1. Greedy Choice Property: A globally optimal solution can be reached by
 * making locally optimal choices
 * 2. Optimal Substructure: An optimal solution contains optimal solutions to
 * subproblems
 *
 * GREEDY VS DYNAMIC PROGRAMMING:
 * - Greedy: Makes one choice at a time, never reconsiders
 * - DP: Considers all possible choices, may reconsider previous decisions
 *
 * COMMON GREEDY PATTERNS:
 * 1. Sorting + Greedy Selection
 * 2. Priority Queue/Heap based selection
 * 3. Interval scheduling
 * 4. Exchange argument proof technique
 */

public class Basics {

    // =================================================================
    // PATTERN 1: ACTIVITY/INTERVAL SELECTION PROBLEMS
    // =================================================================

    /**
     * ACTIVITY SELECTION PROBLEM (Classic Greedy)
     * Problem: Given start and end times of activities, select maximum number of
     * non-overlapping activities
     *
     * GREEDY INSIGHT: Always pick the activity that finishes earliest
     * WHY? It leaves maximum room for future activities
     *
     * PROOF TECHNIQUE: Exchange Argument
     * - If optimal solution doesn't include earliest finishing activity, we can
     * replace it
     * - This gives us at least as good solution, proving greedy choice is optimal
     */
    public static class ActivitySelection {
        static class Activity {
            int start, end;

            Activity(int s, int e) {
                start = s;
                end = e;
            }
        }

        public static int maxActivities(int[] start, int[] end) {
            int n = start.length;
            Activity[] activities = new Activity[n];

            // Create activity objects
            for (int i = 0; i < n; i++) {
                activities[i] = new Activity(start[i], end[i]);
            }

            // KEY INSIGHT: Sort by end time (greedy choice)
            Arrays.sort(activities, (a, b) -> a.end - b.start);

            int count = 1; // First activity is always selected
            int lastEnd = activities[0].end;

            // Greedy selection: pick activity if it doesn't overlap with last selected
            for (int i = 1; i < n; i++) {
                if (activities[i].start >= lastEnd) {
                    count++;
                    lastEnd = activities[i].end;
                }
            }

            return count;
        }
    }

    // =================================================================
    // PATTERN 2: SORTING + GREEDY SELECTION
    // =================================================================

    /**
     * FRACTIONAL KNAPSACK PROBLEM
     * Problem: Given weights, values, and capacity, maximize value (fractions
     * allowed)
     *
     * GREEDY INSIGHT: Sort by value/weight ratio, take highest ratio items first
     * WHY? We want maximum value per unit weight
     *
     * DIFFERENCE FROM 0/1 KNAPSACK:
     * - 0/1 Knapsack: Items can't be broken (requires DP)
     * - Fractional: Items can be broken (greedy works)
     */
    public static class FractionalKnapsack {
        static class Item {
            int weight, value;
            double ratio;

            Item(int w, int v) {
                weight = w;
                value = v;
                ratio = (double) v / w; // Value per unit weight
            }
        }

        public static double fractionalKnapsack(int capacity, int[] weights, int[] values) {
            int n = weights.length;
            Item[] items = new Item[n];

            // Create items with ratio calculation
            for (int i = 0; i < n; i++) {
                items[i] = new Item(weights[i], values[i]);
            }

            // GREEDY CHOICE: Sort by value/weight ratio in descending order
            Arrays.sort(items, (a, b) -> Double.compare(b.ratio, a.ratio));

            double totalValue = 0;
            int remainingCapacity = capacity;

            for (Item item : items) {
                if (remainingCapacity >= item.weight) {
                    // Take the whole item
                    totalValue += item.value;
                    remainingCapacity -= item.weight;
                } else {
                    // Take fraction of the item
                    totalValue += item.ratio * remainingCapacity;
                    break; // Knapsack is full
                }
            }

            return totalValue;
        }
    }

    // =================================================================
    // PATTERN 3: COIN CHANGE (GREEDY APPROACH)
    // =================================================================

    /**
     * COIN CHANGE - GREEDY APPROACH
     * Problem: Find minimum number of coins to make a given amount
     *
     * IMPORTANT: Greedy works only for canonical coin systems (like US coins)
     * For arbitrary coin systems, use Dynamic Programming
     *
     * GREEDY INSIGHT: Always use the largest coin possible
     * WHY? Using larger coins reduces the total number of coins needed
     *
     * CANONICAL COIN SYSTEM: A coin system where greedy algorithm gives optimal
     * solution
     * Examples: {1, 5, 10, 25}, {1, 2, 5, 10, 20, 50, 100}
     */
    public static class CoinChange {
        public static int minCoins(int[] coins, int amount) {
            // PREREQUISITE: coins array should be sorted in descending order
            Arrays.sort(coins);
            reverseArray(coins);

            int count = 0;

            // Greedy approach: use largest coin possible
            for (int coin : coins) {
                if (amount >= coin) {
                    int numCoins = amount / coin;
                    count += numCoins;
                    amount -= numCoins * coin;
                }

                if (amount == 0)
                    break;
            }

            return amount == 0 ? count : -1; // Return -1 if exact change not possible
        }

        private static void reverseArray(int[] arr) {
            int left = 0, right = arr.length - 1;
            while (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
        }
    }

    // =================================================================
    // PATTERN 4: SCHEDULING PROBLEMS
    // =================================================================

    /**
     * JOB SEQUENCING PROBLEM
     * Problem: Given jobs with deadlines and profits, maximize profit
     *
     * GREEDY INSIGHT: Sort jobs by profit (descending), schedule highest profit
     * jobs first
     * WHY? We want to maximize total profit within deadline constraints
     *
     * TECHNIQUE: Use Union-Find or boolean array to track available time slots
     */
    public static class JobSequencing {
        static class Job {
            int id, deadline, profit;

            Job(int i, int d, int p) {
                id = i;
                deadline = d;
                profit = p;
            }
        }

        public static int[] jobScheduling(Job[] jobs, int n) {
            // GREEDY CHOICE: Sort by profit in descending order
            Arrays.sort(jobs, (a, b) -> b.profit - a.profit);

            // Find maximum deadline to determine time slots
            int maxDeadline = 0;
            for (Job job : jobs) {
                maxDeadline = Math.max(maxDeadline, job.deadline);
            }

            // Track available time slots
            boolean[] timeSlot = new boolean[maxDeadline + 1];
            int jobCount = 0, totalProfit = 0;

            // Schedule jobs greedily
            for (Job job : jobs) {
                // Find latest available slot before deadline
                for (int j = job.deadline; j > 0; j--) {
                    if (!timeSlot[j]) {
                        timeSlot[j] = true;
                        jobCount++;
                        totalProfit += job.profit;
                        break;
                    }
                }
            }

            return new int[] { jobCount, totalProfit };
        }
    }

    // =================================================================
    // PATTERN 5: HEAP/PRIORITY QUEUE BASED GREEDY
    // =================================================================

    /**
     * HUFFMAN CODING (Using Priority Queue)
     * Problem: Build optimal prefix-free binary code
     *
     * GREEDY INSIGHT: Always merge two nodes with minimum frequency
     * WHY? This minimizes the total encoding length
     *
     * DATA STRUCTURE: Min-heap to always get minimum frequency nodes
     */
    public static class HuffmanCoding {
        static class Node implements Comparable<Node> {
            int freq;
            char ch;
            Node left, right;

            Node(int f, char c) {
                freq = f;
                ch = c;
            }

            Node(int f, Node l, Node r) {
                freq = f;
                left = l;
                right = r;
            }

            public int compareTo(Node other) {
                return this.freq - other.freq;
            }
        }

        public static Node buildHuffmanTree(char[] chars, int[] freqs) {
            // GREEDY STRUCTURE: Min-heap to always get minimum frequency nodes
            PriorityQueue<Node> heap = new PriorityQueue<>();

            // Add all characters as leaf nodes
            for (int i = 0; i < chars.length; i++) {
                heap.offer(new Node(freqs[i], chars[i]));
            }

            // Build tree using greedy approach
            while (heap.size() > 1) {
                // GREEDY CHOICE: Take two minimum frequency nodes
                Node left = heap.poll();
                Node right = heap.poll();

                // Create internal node with combined frequency
                Node merged = new Node(left.freq + right.freq, left, right);
                heap.offer(merged);
            }

            return heap.poll(); // Root of Huffman tree
        }
    }

    // =================================================================
    // PATTERN 6: ARRAY MANIPULATION GREEDY
    // =================================================================

    /**
     * CANDY DISTRIBUTION PROBLEM
     * Problem: Give minimum candies to children such that:
     * 1. Each child gets at least one candy
     * 2. Children with higher rating get more candies than neighbors
     *
     * GREEDY INSIGHT: Two-pass approach
     * - Left-to-right: Handle left neighbor constraint
     * - Right-to-left: Handle right neighbor constraint
     *
     * WHY TWO PASSES? Single pass can't handle both constraints simultaneously
     */
    public static class CandyDistribution {
        public static int candy(int[] ratings) {
            int n = ratings.length;
            int[] candies = new int[n];

            // Initialize: everyone gets at least 1 candy
            Arrays.fill(candies, 1);

            // PASS 1: Left to right (handle left neighbor)
            for (int i = 1; i < n; i++) {
                if (ratings[i] > ratings[i - 1]) {
                    candies[i] = candies[i - 1] + 1;
                }
            }

            // PASS 2: Right to left (handle right neighbor)
            for (int i = n - 2; i >= 0; i--) {
                if (ratings[i] > ratings[i + 1]) {
                    candies[i] = Math.max(candies[i], candies[i + 1] + 1);
                }
            }

            // Calculate total candies
            int total = 0;
            for (int candy : candies) {
                total += candy;
            }

            return total;
        }
    }

    // =================================================================
    // PATTERN 7: JUMP GAME PROBLEMS
    // =================================================================

    /**
     * JUMP GAME I
     * Problem: Determine if you can reach the last index
     *
     * GREEDY INSIGHT: Track the farthest reachable index
     * WHY? If current index > farthest reachable, we can't proceed
     */
    public static class JumpGame1 {
        public static boolean canJump(int[] nums) {
            int farthest = 0;

            for (int i = 0; i < nums.length; i++) {
                // If current index is beyond farthest reachable, return false
                if (i > farthest)
                    return false;

                // Update farthest reachable index
                farthest = Math.max(farthest, i + nums[i]);

                // Early termination: if we can reach the end
                if (farthest >= nums.length - 1)
                    return true;
            }

            return false;
        }
    }

    /**
     * JUMP GAME II
     * Problem: Find minimum number of jumps to reach the end
     *
     * GREEDY INSIGHT: For each jump, choose the position that can reach farthest
     * WHY? This minimizes the number of jumps needed
     *
     * TECHNIQUE: Track current jump's end and farthest reachable in next jump
     */
    public static class JumpGame2 {
        public static int jump(int[] nums) {
            int jumps = 0;
            int currentEnd = 0; // End of current jump
            int farthest = 0; // Farthest reachable in next jump

            // Don't iterate through last index (we want to reach it, not jump from it)
            for (int i = 0; i < nums.length - 1; i++) {
                // Update farthest reachable in next jump
                farthest = Math.max(farthest, i + nums[i]);

                // If we've reached the end of current jump, make another jump
                if (i == currentEnd) {
                    jumps++;
                    currentEnd = farthest;
                }
            }

            return jumps;
        }
    }

    // =================================================================
    // ADDITIONAL PROBLEMS SECTION
    // =================================================================

    /**
     * ADDITIONAL PROBLEM 1: ASSIGN COOKIES
     * Problem: Assign cookies to children to maximize satisfaction
     *
     * GREEDY INSIGHT: Sort both arrays, assign smallest cookie that satisfies each
     * child
     */
    public static class AssignCookies {
        public static int findContentChildren(int[] g, int[] s) {
            Arrays.sort(g); // Children's greed factors
            Arrays.sort(s); // Cookie sizes

            int child = 0, cookie = 0;

            while (child < g.length && cookie < s.length) {
                if (s[cookie] >= g[child]) {
                    child++; // Child is satisfied
                }
                cookie++;
            }

            return child;
        }
    }

    /**
     * ADDITIONAL PROBLEM 2: LEMONADE CHANGE
     * Problem: Determine if you can provide correct change for all customers
     *
     * GREEDY INSIGHT: Always use largest denominations first when giving change
     */
    public static class LemonadeChange {
        public static boolean lemonadeChange(int[] bills) {
            int five = 0, ten = 0;

            for (int bill : bills) {
                if (bill == 5) {
                    five++;
                } else if (bill == 10) {
                    if (five > 0) {
                        five--;
                        ten++;
                    } else {
                        return false;
                    }
                } else { // bill == 20
                    if (ten > 0 && five > 0) {
                        ten--;
                        five--;
                    } else if (five >= 3) {
                        five -= 3;
                    } else {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    /**
     * ADDITIONAL PROBLEM 3: N MEETINGS IN ONE ROOM
     * Problem: Find maximum number of meetings that can be held in one room
     *
     * GREEDY INSIGHT: Same as activity selection - sort by end time
     */
    public static class NMeetings {
        static class Meeting {
            int start, end, index;

            Meeting(int s, int e, int i) {
                start = s;
                end = e;
                index = i;
            }
        }

        public static int maxMeetings(int[] start, int[] end) {
            int n = start.length;
            Meeting[] meetings = new Meeting[n];

            for (int i = 0; i < n; i++) {
                meetings[i] = new Meeting(start[i], end[i], i);
            }

            Arrays.sort(meetings, (a, b) -> a.end - b.end);

            int count = 1;
            int lastEnd = meetings[0].end;

            for (int i = 1; i < n; i++) {
                if (meetings[i].start > lastEnd) {
                    count++;
                    lastEnd = meetings[i].end;
                }
            }

            return count;
        }
    }

    /**
     * ADDITIONAL PROBLEM 4: MINIMUM PLATFORMS
     * Problem: Find minimum number of platforms needed for trains
     *
     * GREEDY INSIGHT: Sort arrival and departure times separately
     */
    public static class MinimumPlatforms {
        public static int findPlatform(int[] arr, int[] dep) {
            Arrays.sort(arr);
            Arrays.sort(dep);

            int platforms = 1, result = 1;
            int i = 1, j = 0;

            while (i < arr.length && j < dep.length) {
                if (arr[i] <= dep[j]) {
                    platforms++;
                    i++;
                } else {
                    platforms--;
                    j++;
                }
                result = Math.max(result, platforms);
            }

            return result;
        }
    }

    /**
     * ADDITIONAL PROBLEM 5: MERGE INTERVALS
     * Problem: Merge overlapping intervals
     *
     * GREEDY INSIGHT: Sort by start time, then merge overlapping intervals
     */
    public static class MergeIntervals {
        public static int[][] merge(int[][] intervals) {
            if (intervals.length <= 1)
                return intervals;

            Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

            List<int[]> merged = new ArrayList<>();
            int[] current = intervals[0];

            for (int i = 1; i < intervals.length; i++) {
                if (intervals[i][0] <= current[1]) {
                    current[1] = Math.max(current[1], intervals[i][1]);
                } else {
                    merged.add(current);
                    current = intervals[i];
                }
            }

            merged.add(current);
            return merged.toArray(new int[merged.size()][]);
        }
    }

    /**
     * ADDITIONAL PROBLEM 6: NON-OVERLAPPING INTERVALS
     * Problem: Find minimum number of intervals to remove to make rest
     * non-overlapping
     *
     * GREEDY INSIGHT: Sort by end time, keep intervals that end earliest
     */
    public static class NonOverlappingIntervals {
        public static int eraseOverlapIntervals(int[][] intervals) {
            if (intervals.length <= 1)
                return 0;

            Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

            int count = 0;
            int end = intervals[0][1];

            for (int i = 1; i < intervals.length; i++) {
                if (intervals[i][0] < end) {
                    count++;
                } else {
                    end = intervals[i][1];
                }
            }

            return count;
        }
    }

    /**
     * ADDITIONAL PROBLEM 7: VALID PARENTHESIS CHECKER
     * Problem: Check if parentheses are balanced
     *
     * GREEDY INSIGHT: Use counter for balance, never let it go negative
     */
    public static class ValidParentheses {
        public static boolean isValid(String s) {
            int balance = 0;

            for (char c : s.toCharArray()) {
                if (c == '(') {
                    balance++;
                } else if (c == ')') {
                    balance--;
                    if (balance < 0)
                        return false;
                }
            }

            return balance == 0;
        }
    }

    // =================================================================
    // COMMON GREEDY PROBLEM PATTERNS & TIPS
    // =================================================================

    /**
     * GREEDY PROBLEM IDENTIFICATION CHECKLIST:
     *
     * 1. OPTIMIZATION PROBLEMS: Find maximum/minimum of something
     * 2. SORTING HELPS: If sorting the input leads to obvious greedy choice
     * 3. EXCHANGE ARGUMENT: Can prove greedy choice is optimal
     * 4. NO FUTURE DEPENDENCE: Current choice doesn't affect future optimal choices
     *
     * COMMON MISTAKES TO AVOID:
     *
     * 1. ASSUMING GREEDY WORKS: Always verify with examples
     * 2. WRONG SORTING CRITERIA: Choose the right parameter to sort by
     * 3. EDGE CASES: Empty arrays, single elements, all same values
     * 4. INTEGER OVERFLOW: Be careful with large numbers
     *
     * PROOF TECHNIQUES:
     *
     * 1. EXCHANGE ARGUMENT: Show that any optimal solution can be modified to
     * include greedy choice
     * 2. INDUCTION: Prove greedy choice works for subproblems
     * 3. CONTRADICTION: Assume greedy doesn't work and derive contradiction
     *
     * WHEN GREEDY DOESN'T WORK:
     *
     * 1. 0/1 KNAPSACK: Items can't be broken (use DP)
     * 2. LONGEST COMMON SUBSEQUENCE: Need to consider all possibilities (use DP)
     * 3. COIN CHANGE (non-canonical): Greedy may give suboptimal solution (use DP)
     *
     * DEBUGGING TIPS:
     *
     * 1. TRACE THROUGH EXAMPLES: Manually verify greedy choices
     * 2. CHECK CORNER CASES: Empty input, single element, all same values
     * 3. VERIFY OPTIMALITY: Use exchange argument or find counterexample
     * 4. TIME COMPLEXITY: Most greedy algorithms are O(n log n) due to sorting
     */

    // =================================================================
    // MAIN METHOD FOR TESTING
    // =================================================================

    public static void main(String[] args) {
        // Test Activity Selection
        int[] start = { 1, 3, 0, 5, 8, 5 };
        int[] end = { 2, 4, 6, 7, 9, 9 };
        System.out.println("Activity Selection: " + ActivitySelection.maxActivities(start, end));

        // Test Fractional Knapsack
        int[] weights = { 10, 20, 30 };
        int[] values = { 60, 100, 120 };
        System.out.println("Fractional Knapsack: " + FractionalKnapsack.fractionalKnapsack(50, weights, values));

        // Test Coin Change
        int[] coins = { 1, 5, 10, 25 };
        System.out.println("Coin Change: " + CoinChange.minCoins(coins, 30));

        // Test Jump Game
        int[] nums = { 2, 3, 1, 1, 4 };
        System.out.println("Jump Game 1: " + JumpGame1.canJump(nums));
        System.out.println("Jump Game 2: " + JumpGame2.jump(nums));

        // Test Candy Distribution
        int[] ratings = { 1, 0, 2 };
        System.out.println("Candy Distribution: " + CandyDistribution.candy(ratings));

        // Test Assign Cookies
        int[] children = { 1, 2, 3 };
        int[] cookies = { 1, 1 };
        System.out.println("Assign Cookies: " + AssignCookies.findContentChildren(children, cookies));

        // Test Lemonade Change
        int[] bills = { 5, 5, 5, 10, 20 };
        System.out.println("Lemonade Change: " + LemonadeChange.lemonadeChange(bills));

        System.out.println("\n=== ALL TESTS COMPLETED ===");
        System.out.println("Study the code above to understand different greedy patterns!");
    }
}

/**
 * FINAL STUDY TIPS FOR INTERVIEWS:
 *
 * 1. PRACTICE PATTERN RECOGNITION: Learn to identify when greedy approach works
 * 2. MASTER PROOF TECHNIQUES: Exchange argument is most common
 * 3. HANDLE EDGE CASES: Empty arrays, single elements, duplicates
 * 4. TIME COMPLEXITY ANALYSIS: Usually O(n log n) due to sorting
 * 5. SPACE COMPLEXITY: Usually O(1) or O(n) for auxiliary arrays
 * 6. PRACTICE VARIATIONS: Same pattern applies to many problems
 * 7. KNOW WHEN NOT TO USE: Some problems require DP, not greedy
 * 8. CODE CLEANLY: Use meaningful variable names and comments
 * 9. TEST THOROUGHLY: Always verify with examples
 * 10. COMMUNICATE CLEARLY: Explain your greedy choice and why it works
 */