package com.monal;

import java.util.*;

public class Problems {

    /*
     * Problem 1: Assign Cookies
     * Description: You have g children and s cookies. Each child i has a greed
     * factor g[i],
     * and each cookie j has a size s[j]. A child can be satisfied if s[j] >= g[i].
     * Find maximum number of children that can be satisfied.
     *
     * Example: greed = [1,2,3], cookies = [1,1] → Output: 1
     *
     * Intuition: To maximize satisfied children, we should give smallest possible
     * cookie to each child. Sort both arrays and use two pointers.
     *
     * Approach:
     * 1. Sort both greed and cookies arrays
     * 2. Use two pointers to match cookies with children
     * 3. If current cookie can satisfy current child, move both pointers
     * 4. Otherwise, move only cookie pointer to find bigger cookie
     */
    public static class Problem_1 {
        public int findContentChildren(int[] g, int[] s) {
            Arrays.sort(g); // Sort greed factors
            Arrays.sort(s); // Sort cookie sizes

            int child = 0, cookie = 0;

            while (child < g.length && cookie < s.length) {
                if (s[cookie] >= g[child]) {
                    child++; // Child is satisfied
                }
                cookie++; // Move to next cookie
            }

            return child;
        }
    }

    /*
     * Problem 2: Fractional Knapsack Problem
     * Description: Given items with weights and values, and a knapsack capacity,
     * maximize value by taking fractions of items if needed.
     *
     * Example: capacity = 50, items = [{value:60, weight:10}, {value:100,
     * weight:20}, {value:120, weight:30}]
     * → Output: 240.0
     *
     * Intuition: Take items with highest value-to-weight ratio first.
     * We can take fractions, so greedy approach works optimally.
     *
     * Approach:
     * 1. Calculate value-to-weight ratio for each item
     * 2. Sort items by ratio in descending order
     * 3. Take items greedily until capacity is full
     * 4. Take fraction of last item if needed
     */
    public static class Problem_2 {
        static class Item {
            int value, weight;

            Item(int value, int weight) {
                this.value = value;
                this.weight = weight;
            }
        }

        public double fractionalKnapsack(int capacity, Item[] items) {
            // Sort by value/weight ratio in descending order
            Arrays.sort(items, (a, b) -> Double.compare(
                    (double) b.value / b.weight, (double) a.value / a.weight));

            double totalValue = 0.0;
            int currentWeight = 0;

            for (Item item : items) {
                if (currentWeight + item.weight <= capacity) {
                    // Take whole item
                    currentWeight += item.weight;
                    totalValue += item.value;
                } else {
                    // Take fraction of item
                    int remainingCapacity = capacity - currentWeight;
                    totalValue += item.value * ((double) remainingCapacity / item.weight);
                    break;
                }
            }

            return totalValue;
        }
    }

    /*
     * Problem 3: Greedy algorithm to find minimum number of coins
     * Description: Given coin denominations and a target amount, find minimum coins
     * needed.
     * Assumes infinite supply of each coin and denominations form a canonical
     * system.
     *
     * Example: coins = [1, 2, 5, 10, 20, 50, 100, 500, 1000], amount = 93
     * → Output: 3 (50 + 20 + 20 + 2 + 1 = 93, but optimal is 50 + 20 + 20 + 2 + 1)
     *
     * Intuition: Start with largest denomination and take as many as possible,
     * then move to next smaller denomination.
     *
     * Approach:
     * 1. Sort coins in descending order
     * 2. For each coin, take maximum possible count
     * 3. Reduce amount and continue with next coin
     */
    public static class Problem_3 {
        public int minCoins(int[] coins, int amount) {
            Arrays.sort(coins);
            // Reverse to get descending order
            for (int i = 0; i < coins.length / 2; i++) {
                int temp = coins[i];
                coins[i] = coins[coins.length - 1 - i];
                coins[coins.length - 1 - i] = temp;
            }

            int count = 0;
            for (int coin : coins) {
                if (amount >= coin) {
                    count += amount / coin;
                    amount = amount % coin;
                }
            }

            return amount == 0 ? count : -1; // -1 if not possible
        }
    }

    /*
     * Problem 4: Lemonade Change
     * Description: Each customer pays with $5, $10, or $20 bill for $5 lemonade.
     * Return true if you can provide correct change for all customers.
     *
     * Example: bills = [5,5,5,10,20] → Output: true
     *
     * Intuition: Keep track of $5 and $10 bills. For $20, prefer giving $10+$5 over
     * three $5s
     * because $10 bills are less flexible.
     *
     * Approach:
     * 1. Count $5 and $10 bills
     * 2. For each customer, calculate required change
     * 3. Give change optimally (prefer $10 over $5 when possible)
     */
    public static class Problem_4 {
        public boolean lemonadeChange(int[] bills) {
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

    /*
     * Problem 5: Valid Parenthesis Checker
     * Description: Given string with '(', ')', '{', '}', '[', ']', check if valid.
     *
     * Example: s = "()[]{}" → Output: true
     *
     * Intuition: Use stack to match opening and closing brackets.
     *
     * Approach:
     * 1. Use stack to store opening brackets
     * 2. For closing bracket, check if it matches top of stack
     * 3. Stack should be empty at end
     */
    public static class Problem_5 {
        public boolean isValid(String s) {
            Stack<Character> stack = new Stack<>();

            for (char c : s.toCharArray()) {
                if (c == '(' || c == '{' || c == '[') {
                    stack.push(c);
                } else {
                    if (stack.isEmpty())
                        return false;

                    char top = stack.pop();
                    if ((c == ')' && top != '(') ||
                            (c == '}' && top != '{') ||
                            (c == ']' && top != '[')) {
                        return false;
                    }
                }
            }

            return stack.isEmpty();
        }
    }

    /*
     * Problem 6: N meetings in one room
     * Description: Given start and end times of meetings, find maximum meetings
     * possible.
     *
     * Example: start = [1,3,0,5,8,5], end = [2,4,6,7,9,9] → Output: 4
     *
     * Intuition: Select meetings that end earliest to leave room for more meetings.
     *
     * Approach:
     * 1. Create meeting objects with start/end times
     * 2. Sort by end time
     * 3. Greedily select meetings that don't overlap
     */
    public static class Problem_6 {
        static class Meeting {
            int start, end, index;

            Meeting(int start, int end, int index) {
                this.start = start;
                this.end = end;
                this.index = index;
            }
        }

        public int maxMeetings(int[] start, int[] end) {
            int n = start.length;
            Meeting[] meetings = new Meeting[n];

            for (int i = 0; i < n; i++) {
                meetings[i] = new Meeting(start[i], end[i], i);
            }

            // Sort by end time
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

    /*
     * Problem 7: Jump Game
     * Description: Given array where each element represents max jump length,
     * determine if you can reach the last index.
     *
     * Example: nums = [2,3,1,1,4] → Output: true
     *
     * Intuition: Keep track of farthest reachable position.
     *
     * Approach:
     * 1. Iterate through array
     * 2. Update farthest reachable position
     * 3. If current position > farthest, return false
     */
    public static class Problem_7 {
        public boolean canJump(int[] nums) {
            int farthest = 0;

            for (int i = 0; i < nums.length; i++) {
                if (i > farthest) {
                    return false;
                }
                farthest = Math.max(farthest, i + nums[i]);
            }

            return true;
        }
    }

    /*
     * Problem 8: Jump Game 2
     * Description: Find minimum jumps to reach last index.
     *
     * Example: nums = [2,3,1,1,4] → Output: 2
     *
     * Intuition: Use BFS-like approach. For each jump, find farthest reachable
     * position.
     *
     * Approach:
     * 1. Track current jump's reach and next jump's reach
     * 2. When current reach is exhausted, increment jumps
     * 3. Update reaches accordingly
     */
    public static class Problem_8 {
        public int jump(int[] nums) {
            int jumps = 0;
            int currentEnd = 0;
            int farthest = 0;

            for (int i = 0; i < nums.length - 1; i++) {
                farthest = Math.max(farthest, i + nums[i]);

                if (i == currentEnd) {
                    jumps++;
                    currentEnd = farthest;
                }
            }

            return jumps;
        }
    }

    /*
     * Problem 9: Minimum number of platforms required for a railway
     * Description: Given arrival and departure times, find minimum platforms
     * needed.
     *
     * Example: arrival = [900, 940, 950, 1100, 1500, 1800]
     * departure = [910, 1200, 1120, 1130, 1900, 2000] → Output: 3
     *
     * Intuition: At any time, platforms needed = trains arrived - trains departed
     *
     * Approach:
     * 1. Sort arrival and departure times
     * 2. Use two pointers to simulate time
     * 3. Track current and maximum platforms needed
     */
    public static class Problem_9 {
        public int findPlatform(int[] arr, int[] dep) {
            Arrays.sort(arr);
            Arrays.sort(dep);

            int platforms = 0;
            int maxPlatforms = 0;
            int i = 0, j = 0;

            while (i < arr.length && j < dep.length) {
                if (arr[i] <= dep[j]) {
                    platforms++;
                    i++;
                } else {
                    platforms--;
                    j++;
                }
                maxPlatforms = Math.max(maxPlatforms, platforms);
            }

            return maxPlatforms;
        }
    }

    /*
     * Problem 10: Job sequencing Problem
     * Description: Given jobs with deadlines and profits, maximize profit.
     *
     * Example: jobs = [{id:1, deadline:2, profit:100}, {id:2, deadline:1,
     * profit:19}]
     * → Output: 100
     *
     * Intuition: Sort jobs by profit (descending) and assign to latest possible
     * slot.
     *
     * Approach:
     * 1. Sort jobs by profit in descending order
     * 2. Create time slots array
     * 3. For each job, assign to latest available slot before deadline
     */
    public static class Problem_10 {
        static class Job {
            int id, deadline, profit;

            Job(int id, int deadline, int profit) {
                this.id = id;
                this.deadline = deadline;
                this.profit = profit;
            }
        }

        public int jobScheduling(Job[] jobs) {
            // Sort by profit in descending order
            Arrays.sort(jobs, (a, b) -> b.profit - a.profit);

            int maxDeadline = 0;
            for (Job job : jobs) {
                maxDeadline = Math.max(maxDeadline, job.deadline);
            }

            boolean[] slot = new boolean[maxDeadline + 1];
            int totalProfit = 0;

            for (Job job : jobs) {
                // Find latest available slot
                for (int i = job.deadline; i > 0; i--) {
                    if (!slot[i]) {
                        slot[i] = true;
                        totalProfit += job.profit;
                        break;
                    }
                }
            }

            return totalProfit;
        }
    }

    /*
     * Problem 11: Candy
     * Description: Children in line, each has rating. Give candies such that:
     * 1. Each child gets at least one candy
     * 2. Children with higher rating get more candies than neighbors
     *
     * Example: ratings = [1,0,2] → Output: 5 (candies = [2,1,2])
     *
     * Intuition: Two passes - left to right for left neighbors, right to left for
     * right neighbors.
     *
     * Approach:
     * 1. Initialize all candies to 1
     * 2. Left to right: if rating[i] > rating[i-1], candy[i] = candy[i-1] + 1
     * 3. Right to left: if rating[i] > rating[i+1], candy[i] = max(candy[i],
     * candy[i+1] + 1)
     */
    public static class Problem_11 {
        public int candy(int[] ratings) {
            int n = ratings.length;
            int[] candies = new int[n];
            Arrays.fill(candies, 1);

            // Left to right pass
            for (int i = 1; i < n; i++) {
                if (ratings[i] > ratings[i - 1]) {
                    candies[i] = candies[i - 1] + 1;
                }
            }

            // Right to left pass
            for (int i = n - 2; i >= 0; i--) {
                if (ratings[i] > ratings[i + 1]) {
                    candies[i] = Math.max(candies[i], candies[i + 1] + 1);
                }
            }

            int total = 0;
            for (int candy : candies) {
                total += candy;
            }

            return total;
        }
    }

    /*
     * Problem 12: Program for Shortest Job First (SJF) CPU Scheduling
     * Description: Given processes with burst times, schedule to minimize average
     * waiting time.
     *
     * Example: processes = [{pid:1, burst:6}, {pid:2, burst:8}, {pid:3, burst:7}]
     * → Order: P1, P3, P2
     *
     * Intuition: Process with shortest burst time first minimizes total waiting
     * time.
     *
     * Approach:
     * 1. Sort processes by burst time
     * 2. Calculate waiting time for each process
     * 3. Calculate average waiting time
     */
    public static class Problem_12 {
        static class Process {
            int pid, burstTime, waitingTime;

            Process(int pid, int burstTime) {
                this.pid = pid;
                this.burstTime = burstTime;
            }
        }

        public double shortestJobFirst(Process[] processes) {
            // Sort by burst time
            Arrays.sort(processes, (a, b) -> a.burstTime - b.burstTime);

            int totalWaitingTime = 0;
            processes[0].waitingTime = 0;

            for (int i = 1; i < processes.length; i++) {
                processes[i].waitingTime = processes[i - 1].waitingTime + processes[i - 1].burstTime;
                totalWaitingTime += processes[i].waitingTime;
            }

            return (double) totalWaitingTime / processes.length;
        }
    }

    /*
     * Problem 13: Program for Least Recently Used (LRU) Page Replacement Algorithm
     * Description: Implement LRU cache with get and put operations.
     *
     * Example: capacity = 2
     * put(1, 1), put(2, 2), get(1), put(3, 3), get(2) → returns -1
     *
     * Intuition: Use doubly linked list + hashmap for O(1) operations.
     *
     * Approach:
     * 1. Hashmap for O(1) key lookup
     * 2. Doubly linked list for O(1) insertion/deletion
     * 3. Always add to head, remove from tail
     */
    public static class Problem_13 {
        class Node {
            int key, value;
            Node prev, next;

            Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        class LRUCache {
            private Map<Integer, Node> cache;
            private Node head, tail;
            private int capacity;

            public LRUCache(int capacity) {
                this.capacity = capacity;
                cache = new HashMap<>();
                head = new Node(-1, -1);
                tail = new Node(-1, -1);
                head.next = tail;
                tail.prev = head;
            }

            public int get(int key) {
                if (cache.containsKey(key)) {
                    Node node = cache.get(key);
                    moveToHead(node);
                    return node.value;
                }
                return -1;
            }

            public void put(int key, int value) {
                if (cache.containsKey(key)) {
                    Node node = cache.get(key);
                    node.value = value;
                    moveToHead(node);
                } else {
                    if (cache.size() == capacity) {
                        Node last = tail.prev;
                        deleteNode(last);
                        cache.remove(last.key);
                    }

                    Node newNode = new Node(key, value);
                    cache.put(key, newNode);
                    addToHead(newNode);
                }
            }

            private void addToHead(Node node) {
                node.prev = head;
                node.next = head.next;
                head.next.prev = node;
                head.next = node;
            }

            private void deleteNode(Node node) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }

            private void moveToHead(Node node) {
                deleteNode(node);
                addToHead(node);
            }
        }
    }

    /*
     * Problem 14: Insert Interval
     * Description: Given non-overlapping intervals and new interval, insert and
     * merge.
     *
     * Example: intervals = [[1,3],[6,9]], newInterval = [2,5] → Output:
     * [[1,5],[6,9]]
     *
     * Intuition: Three parts - before overlap, merge overlapping, after overlap.
     *
     * Approach:
     * 1. Add all intervals that end before new interval starts
     * 2. Merge all overlapping intervals
     * 3. Add remaining intervals
     */
    public static class Problem_14 {
        public int[][] insert(int[][] intervals, int[] newInterval) {
            List<int[]> result = new ArrayList<>();
            int i = 0;

            // Add all intervals that end before newInterval starts
            while (i < intervals.length && intervals[i][1] < newInterval[0]) {
                result.add(intervals[i]);
                i++;
            }

            // Merge overlapping intervals
            while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
                newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
                newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
                i++;
            }
            result.add(newInterval);

            // Add remaining intervals
            while (i < intervals.length) {
                result.add(intervals[i]);
                i++;
            }

            return result.toArray(new int[result.size()][]);
        }
    }

    /*
     * Problem 15: Merge Intervals
     * Description: Given collection of intervals, merge overlapping intervals.
     *
     * Example: intervals = [[1,3],[2,6],[8,10],[15,18]] → Output:
     * [[1,6],[8,10],[15,18]]
     *
     * Intuition: Sort by start time, then merge overlapping intervals.
     *
     * Approach:
     * 1. Sort intervals by start time
     * 2. Iterate and merge overlapping intervals
     * 3. Add non-overlapping intervals to result
     */
    public static class Problem_15 {
        public int[][] merge(int[][] intervals) {
            if (intervals.length <= 1)
                return intervals;

            Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
            List<int[]> result = new ArrayList<>();

            int[] current = intervals[0];
            for (int i = 1; i < intervals.length; i++) {
                if (intervals[i][0] <= current[1]) {
                    // Overlapping intervals, merge them
                    current[1] = Math.max(current[1], intervals[i][1]);
                } else {
                    // Non-overlapping interval
                    result.add(current);
                    current = intervals[i];
                }
            }
            result.add(current);

            return result.toArray(new int[result.size()][]);
        }
    }

    /*
     * Problem 16: Non-overlapping Intervals
     * Description: Given intervals, find minimum removals to make non-overlapping.
     *
     * Example: intervals = [[1,2],[2,3],[3,4],[1,3]] → Output: 1
     *
     * Intuition: Keep intervals that end earliest (similar to activity selection).
     *
     * Approach:
     * 1. Sort by end time
     * 2. Greedily select non-overlapping intervals
     * 3. Count removals = total - selected
     */
    public static class Problem_16 {
        public int eraseOverlapIntervals(int[][] intervals) {
            if (intervals.length <= 1)
                return 0;

            Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

            int count = 1; // First interval is always selected
            int end = intervals[0][1];

            for (int i = 1; i < intervals.length; i++) {
                if (intervals[i][0] >= end) {
                    count++;
                    end = intervals[i][1];
                }
            }

            return intervals.length - count;
        }
    }

    // Test methods for demonstration
    public static void main(String[] args) {
        // Example usage for Problem 1
        Problem_1 p1 = new Problem_1();
        System.out.println("Problem 1 - Assign Cookies: " +
                p1.findContentChildren(new int[] { 1, 2, 3 }, new int[] { 1, 1 })); // Output: 1

        // Example usage for Problem 2
        Problem_2 p2 = new Problem_2();
        Problem_2.Item[] items = {
                new Problem_2.Item(60, 10),
                new Problem_2.Item(100, 20),
                new Problem_2.Item(120, 30)
        };
        System.out.println("Problem 2 - Fractional Knapsack: " +
                p2.fractionalKnapsack(50, items)); // Output: 240.0

        // Add more test cases as needed...
    }
}