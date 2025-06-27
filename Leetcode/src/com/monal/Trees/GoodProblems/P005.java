package com.monal.Trees.GoodProblems;

import com.monal.Trees.TreeNode;
/* Given the root of a complete binary tree, return the number of the nodes in the tree.

According to Wikipedia, every level, except possibly the last, is completely filled in a complete binary tree, and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.

Design an algorithm that runs in less than O(n) time complexity. */

public class P005 {
  public int countNodes(TreeNode root) {
    if (root == null)
      return 0;

    int leftHeight = getLeftHeight(root);
    int rightHeight = getRightHeight(root);

    if (leftHeight == rightHeight) {
      return (int)Math.pow(2, rightHeight) -1;
    }

    // Otherwise, recurse on left and right subtrees
    return 1 + countNodes(root.left) + countNodes(root.right);
  }

  private int getLeftHeight(TreeNode node) {
    int height = 0;
    while (node != null) {
      height++;
      node = node.left;
    }
    return height;
  }

  private int getRightHeight(TreeNode node) {
    int height = 0;
    while (node != null) {
      height++;
      // This is The main point as in a complete binary tree if we goto left we will always have a left child
      // but if we go to right we may not have a right child and we will determine the height of the tree
      // based on the right child.
      node = node.right;
    }
    return height;
  }

}
