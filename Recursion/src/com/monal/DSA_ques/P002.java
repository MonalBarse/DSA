package com.monal.DSA_ques;

/*
You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
Merge all the linked-lists into one sorted linked-list and return it.
 */
public class P002 {
  /**
   * Definition for singly-linked list.
   * public class ListNode {
   * int val;
   * ListNode next;
   * ListNode() {}
   * ListNode(int val) { this.val = val; }
   * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
   * }
   */
  class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
      if (lists == null || lists.length == 0) {
        return null;
      }
      return helper_mergeKLists(lists, 0, lists.length - 1);
    }

    private ListNode helper_mergeKLists(ListNode[] lists, int left, int right) {
      if (left == right) {
        return lists[left];
      }
      int mid = left + (right - left) / 2;
      ListNode l1 = helper_mergeKLists(lists, left, mid);
      ListNode l2 = helper_mergeKLists(lists, mid + 1, right);
      return mergeTwoLists(l1, l2);
    }

    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
      if (l1 == null)
        return l2;
      if (l2 == null)
        return l1;

      if (l1.val < l2.val) {
        l1.next = mergeTwoLists(l1.next, l2);
        return l1;
      } else {
        l2.next = mergeTwoLists(l1, l2.next);
        return l2;
      }
    }

    // Helper class for ListNode
    public class ListNode {
      int val;
      ListNode next;

      ListNode() {
      }

      ListNode(int val) {
        this.val = val;
      }

      ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
      }
    }

  }
}