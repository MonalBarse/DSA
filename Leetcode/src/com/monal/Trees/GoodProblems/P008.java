package com.monal.Trees.GoodProblems;

/*
Flatten Binary Tree to Linked List
Given the root of a binary tree, flatten the tree into a "linked list":
The "linked list" should use the same TreeNode class where the right child pointer points to the next node in the list and the left child pointer is always null.
The "linked list" should be in the same order as a pre-order traversal of the binary tree.
*/
public class P008 {
  public void flatten(TreeNode root) {
    if (root == null)
      return;

    // Flatten left and right subtrees
    flatten(root.left);
    flatten(root.right);
    // Store the right subtree
    TreeNode rightSubtree = root.right;
    // Move the left subtree to the right
    root.right = root.left;
    root.left = null; // Set left child to null
    // Find the end of the new right subtree
    TreeNode current = root;
    while (current.right != null) {
      current = current.right;
    }
    // Attach the original right subtree
    current.right = rightSubtree;
  }

  public static void main(String[] args) {
    // Example usage

  }

  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
      left = null;
      right = null;
    }

    // Constructor for convenience
    TreeNode(int x, TreeNode left, TreeNode right) {
      this.val = x;
      this.left = left;
      this.right = right;
    }
  }
}
