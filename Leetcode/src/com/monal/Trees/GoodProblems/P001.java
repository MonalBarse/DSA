package com.monal.Trees.GoodProblems;

import com.monal.Trees.TreeNode;

// https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/description/
/*
 * Lowest Common Ancestor of a Binary Tree
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between
 * two nodes p and q as the lowest node in T that has both p and q as descendants
 * (where we allow a node to be a descendant of itself).”
 */
public class P001 {
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null)
      return null;
    return findLCA(root, p, q);
  }

  private TreeNode findLCA(TreeNode node, TreeNode p, TreeNode q) {
    if (node == null || node == p || node == q) {
      return node;
    }

    TreeNode left = findLCA(node.left, p, q);
    TreeNode right = findLCA(node.right, p, q);

    if (left != null && right != null) {
      return node; // If both left and right of the node are not null, then this is the LCA
    }

    // If only one of the sides is !null, return that side
    return right != null ? right : left;
  }
}