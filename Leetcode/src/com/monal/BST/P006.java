package com.monal.BST;
/* Given the root of a binary tree, determine if it is a valid binary search tree (BST).

A valid BST is defined as follows:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees. */

public class P006 {
  public boolean isValidBST(TreeNode root) {
    return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
  }

  private boolean isValidBSTHelper(TreeNode node, long min, long max) {
    if (node == null) {
      return true; // An empty tree is a valid BST
    }
    if (node.val <= min || node.val >= max) {
      return false; // Current node's value must be within the range
    }
    // Recursively check left and right subtrees with updated ranges
    return isValidBSTHelper(node.left, min, node.val) && isValidBSTHelper(node.right, node.val, max);
  }

  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
      left = null;
      right = null;
    }
  }

  public static void main(String[] args) {
    // Example usage
    TreeNode root = new TreeNode(2);
    root.left = new TreeNode(1);
    root.right = new TreeNode(3);

    P006 solution = new P006();
    boolean result = solution.isValidBST(root);
    System.out.println("Is the tree a valid BST? " + result); // Output: true

    // Test with an invalid BST
    root.right.left = new TreeNode(2);
    result = solution.isValidBST(root);
    System.out.println("Is the tree a valid BST? " + result); // Output: false
  }
}
