package com.monal.BST;

import java.util.*;

/*
Given the root of a binary search tree, return a balanced binary search tree with the same node values. If there is more than one answer, return any of them.

A binary search tree is balanced if the depth of the two subtrees of every node never differs by more than 1.

Example 1:
  Input: root = [1,null,2,null,3,null,4,null,null]
  Output: [2,1,3,null,null,null,4]
  Explanation: This is not the only correct answer, [3,1,4,null,2] is also correct.

Example 2:
  Input: root = [2,1,3]
  Output: [2,1,3]

Constraints:

The number of nodes in the tree is in the range [1, 104].
1 <= Node.val <= 105
*/
public class P012 {

  class Solution {
    private List<Integer> inorder; // store values instead of nodes

    public TreeNode balanceBST(TreeNode root) {
      this.inorder = new ArrayList<>();
      traversal(root);
      return build(0, inorder.size() - 1);
    }

    // build balanced BST from sorted array
    private TreeNode build(int start, int end) {
      if (start > end)
        return null;
      int mid = start + (end - start) / 2;

      TreeNode root = new TreeNode(inorder.get(mid));
      root.left = build(start, mid - 1);
      root.right = build(mid + 1, end);
      return root;
    }

    // inorder traversal collects values in sorted order
    private void traversal(TreeNode node) {
      if (node == null)
        return;
      traversal(node.left);
      inorder.add(node.val);
      traversal(node.right);
    }
  }

}
