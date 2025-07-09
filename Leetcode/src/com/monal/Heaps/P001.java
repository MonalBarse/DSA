package com.monal.Heaps;

import java.util.*;

/*
 621. Task Scheduler (https://leetcode.com/problems/task-scheduler/)
You are given an array of CPU tasks, each labeled with a letter from A to Z,
and a number n. Each CPU interval can be idle or allow the completion of one task.
Tasks can be completed in any order, but there's a constraint: there has to be a
gap of at least n intervals between two tasks with the same label.
Return the minimum number of CPU intervals required to complete all tasks.
Example 1:
    Input: tasks = ["A","A","A","B","B","B"], n = 2
    Output: 8
    Explanation: A possible sequence is: A -> B -> idle -> A -> B -> idle -> A -> B.
    After completing task A, you must wait two intervals before doing A again. The same applies to task B. In the 3rd interval, neither A nor B can be done, so you idle. By the 4th interval, you can do A again as 2 intervals have passed.
Example 2:
    Input: tasks = ["A","C","A","B","D","B"], n = 1
    Output: 6
    Explanation: A possible sequence is: A -> B -> C -> D -> A -> B.
With a cooling interval of 1, you can repeat a task after just one other task.
*/
public class P001 {
    public int leastInterval(char[] tasks, int n) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char task : tasks)
            freqMap.put(task, freqMap.getOrDefault(task, 0) + 1);
        // store the frequencies in a max heap
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.addAll(freqMap.values());

        Queue<int[]> cooling = new ArrayDeque<>(); // to track tasks in cooling state
        int timeRequired = 0;

        while (!maxHeap.isEmpty() || !cooling.isEmpty()) {
            timeRequired++;
            if (!maxHeap.isEmpty()) {
                int freq = maxHeap.poll() - 1;
                if (freq > 0)
                    cooling.offer(new int[] { freq, timeRequired + n });
            }
            if (!cooling.isEmpty() && cooling.peek()[1] == timeRequired)
                maxHeap.offer(cooling.poll()[0]);

        }
        return timeRequired;
    }

    public static void main(String[] args) {
        P001 solution = new P001();
        char[] tasks1 = { 'A', 'A', 'A', 'B', 'B', 'B' };
        int n1 = 2;
        System.out.println("Minimum intervals for tasks1: " + solution.leastInterval(tasks1, n1)); // Output: 8

        char[] tasks2 = { 'A', 'C', 'A', 'B', 'D', 'B' };
        int n2 = 1;
        System.out.println("Minimum intervals for tasks2: " + solution.leastInterval(tasks2, n2)); // Output: 6
    }
}
