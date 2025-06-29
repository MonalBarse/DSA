package com.monal.BST;

/*
You are given the root of a binary search tree (BST), where the values of exactly two nodes of the tree were swapped by mistake. Recover the tree without changing its structure.
*/
public class P011 {

  // Global variables to track the two swapped nodes
  TreeNode first = null; // first node that is out of order
  TreeNode second = null; // second node that is out of order
  TreeNode prev = null; // previous node in the inorder traversal

  public void recoverTree(TreeNode root) {
    // Reset global variables
    first = null;
    second = null;
    prev = null;

    // Perform inorder traversal to find the two swapped nodes
    inorderTraversal(root);

    // Swap the values of the two nodes
    if (first != null && second != null) {
      int temp = first.val;
      first.val = second.val;
      second.val = temp;
    }
  }

  private void inorderTraversal(TreeNode node) {
    if (node == null)
      return;

    // Traverse left subtree
    inorderTraversal(node.left);

    // Process current node
    if (prev != null && prev.val > node.val) {
      // Found a violation of BST property
      if (first == null) {
        // First violation found
        first = prev;
        second = node;
      } else {
        // Second violation found
        second = node;
      }
    }
    prev = node;

    // Traverse right subtree
    inorderTraversal(node.right);
  }

  // Helper method to print the tree (inorder)
  private void printInorder(TreeNode node) {
    if (node == null)
      return;
    printInorder(node.left);
    System.out.print(node.val + " ");
    printInorder(node.right);
  }

  public static void main(String[] args) {
    P011 solution = new P011();

    // Create the tree: 3,1,4,null,null,2,null
    // 3
    // / \
    // 1 4
    // /
    // 2
    TreeNode root = solution.new TreeNode(3);
    root.left = solution.new TreeNode(1);
    root.right = solution.new TreeNode(4);
    root.right.left = solution.new TreeNode(2);

    System.out.print("Before recovery: ");
    solution.printInorder(root);
    System.out.println();

    solution.recoverTree(root);

    System.out.print("After recovery: ");
    solution.printInorder(root);
    System.out.println();
  }

  public class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int x) {
      val = x;
      left = null;
      right = null;
    }
  }

  public class Solution {

    public void recoverTree(TreeNode root) {
      TreeNode first = null, second = null, prev = null;
      TreeNode current = root;

      // Morris Traversal - O(1) space inorder traversal
      while (current != null) {
        if (current.left == null) {
          // Process current node
          if (prev != null && prev.val > current.val) {
            if (first == null) {
              first = prev;
              second = current;
            } else {
              second = current;
            }
          }
          prev = current;
          current = current.right;
        } else {
          // Find inorder predecessor
          TreeNode predecessor = current.left;
          while (predecessor.right != null && predecessor.right != current) {
            predecessor = predecessor.right;
          }

          if (predecessor.right == null) {
            // Create threaded link
            predecessor.right = current;
            current = current.left;
          } else {
            // Remove threaded link and process current node
            predecessor.right = null;

            if (prev != null && prev.val > current.val) {
              if (first == null) {
                first = prev;
                second = current;
              } else {
                second = current;
              }
            }
            prev = current;
            current = current.right;
          }
        }
      }

      // Swap the values of the two nodes
      if (first != null && second != null) {
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
      }
    }
  }
}