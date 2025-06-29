package com.monal.BST;

import java.util.*;

/*
 *
 * Two Sum in BST
 * Given a binary search tree (BST) and a target value, determine if there
 * are two distinct nodes in the BST such that their values add up to the target value.
 */
public class P010 {

  class Solution {
    public boolean findTarget(TreeNode root, int k) {
      Set<Integer> seen = new HashSet<>();
      return dfs(root, k, seen);
    }

    private boolean dfs(TreeNode node, int k, Set<Integer> seen) {
      if (node == null)
        return false;
      if (seen.contains(k - node.val))
        return true;
      seen.add(node.val);
      return dfs(node.left, k, seen) || dfs(node.right, k, seen);
    }

  }

  class Soltuion2 {
    public boolean findTarget(TreeNode root, int k) {
      List<Integer> inorder = new ArrayList<>();
      inOrder(root, inorder);

      // Two pointer technique
      int left = 0, right = inorder.size() - 1;
      while (left < right) {
        int sum = inorder.get(left) + inorder.get(right);
        if (sum == k)
          return true;
        else if (sum < k)
          left++;
        else
          right--;
      }
      return false;
    }

    private void inOrder(TreeNode node, List<Integer> list) {
      if (node == null)
        return;
      inOrder(node.left, list);
      list.add(node.val);
      inOrder(node.right, list);
    }

  }

  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
      this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }
}
