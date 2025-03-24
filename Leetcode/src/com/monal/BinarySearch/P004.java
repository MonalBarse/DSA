package com.monal.BinarySearch;

/*
 Advanced Problem 1: Minimize the Maximum Load (Binary Search + Greedy)
 Problem Statement:

  You are given an array work[] of N positive integers, representing tasks of different workloads.
  You need to assign these tasks to K workers, such that:
   - Each worker gets a contiguous subarray of tasks.
   - The maximum workload among all workers is minimized.
   - Return the minimum possible maximum workload that any worker has to handle.

  Example 1:
    Input: nums = {10, 20, 30, 40}, K = 2
    Output: 60
    Explanation:
      We can split as **[10, 20, 30] [40]**, so the maximum workload is **60**.


 */
public class P004 {

  private int minMaxWorkload(int[] work, int k) {
    int minMaxWorkload = 0;
    int start = 0;
    int end = 0;

    // define the search space, start should be the maximum workload and end should
    // be the sum of all workloads (1 worker does all tasks)
    for (int workload : work) {
      start = Math.max(start, workload);
      end += workload;
    }

    // apply binary search on the search space
    while (start <= end) {
      int mid = start + (end - start) / 2;

      if (isValid(work, k, mid)) {
        // If the current workload is valid, then we store the result and move to the
        // left side as we need to minimize the maximum workload among all workers.
        minMaxWorkload = mid;
        end = mid - 1;
      } else {
        start = mid + 1;
      }
    }

    return minMaxWorkload;

  }

  private boolean isValid(int[] work, int k, int desiredWorkload) {
    int workers = 1; // in search space, we are considering the current workload as the maximum
    // workload that any worker has to handle.

    int currentWorkload = 0;

    for (int workload : work) {
      if (currentWorkload + workload > desiredWorkload) {
        workers++;
        currentWorkload = workload;
        if (workers > k)
          return false;
      } else {
        currentWorkload += workload;
      }
    }
    return true;

  }

  public static void main(String[] args) {
    /**
     * Approach 1: Binary Search + Greedy
     *
     * @1 Define the Search Space
     *
     * The minimum possible maximum workload is max(nums) (if one worker gets the
     * hardest task).
     * The maximum possible workload is sum(nums) (if one worker does all tasks).
     * So, our search space is [max(nums), sum(nums)].
     *
     * @2 Checking Feasibility (Greedy)
     *
     * Given a mid workload, try to assign workers greedily.
     * If we can assign all tasks within K workers, reduce mid (try smaller loads).
     * If we cannot, increase mid (try larger loads)
     */

    P004 obj = new P004();

    int work1[] = { 10, 20, 30, 40 };
    int k1 = 2;
    System.out.println(obj.minMaxWorkload(work1, k1)); // 60

    int work2[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
    int k2 = 4;
    System.out.println(obj.minMaxWorkload(work2, k2)); // 15

    int work3[] = { 1, 17, 22, 47, 68, 72, 80, 99, 109, 113 };
    int k3 = 6;
    System.out.println(obj.minMaxWorkload(work3, k3)); // 140
  }

}
