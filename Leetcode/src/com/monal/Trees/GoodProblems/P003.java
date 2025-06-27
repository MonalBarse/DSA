package com.monal.Trees.GoodProblems;

import com.monal.Trees.TreeNode;
import java.util.*;

// Width of Binary Tree (Maximum Width of Binary Tree)
/*
Given the root of a binary tree, return the maximum width of the given tree.
The maximum width of a tree is the maximum width among all levels.
The width of one level is defined as the length between the end-nodes
(the leftmost and rightmost non-null nodes), where the null nodes
between the end-nodes that would be present in a complete binary
tree extending down to that level are also counted into the length calculation.

Example 1:
  Input: root = [1,3,2,5,3,null,9]
  Output: 4
  Explanation: The maximum width exists in the third level with length 4 (5,3,null,9).

Example 2:
  Input: root = [1,3,2,5,null,null,9,6,null,7]
  Output: 7
  Explanation: The maximum width exists in the fourth level with length 7 (6,null,null,null,null,null,7).

Example 3:
  Input: root = [1,3,2,5]
  Output: 2
  Explanation: The maximum width exists in the second level with length 2 (3,2).

*/

public class P003 {
  public int widthOfBinaryTree(TreeNode root) {
    if (root == null)
      return 0;

    // Queue to store pairs of (node, index)
    Queue<Pair> queue = new LinkedList<>();
    queue.offer(new Pair(root, 0));

    int maxWidth = 1;

    while (!queue.isEmpty()) {
      int size = queue.size();
      int leftmostIndex = queue.peek().index; // First node's index in this level
      int rightmostIndex = leftmostIndex; // Will be updated to last node's index

      // In all bfs this loop is for processing the current level
      for (int i = 0; i < size; i++) {
        Pair current = queue.poll();
        TreeNode node = current.node;
        int index = current.index;

        // Update rightmost index
        rightmostIndex = index;
        // Add children to the queue with their respective indices
        if (node.left != null) {
          queue.offer(new Pair(node.left, current.leftIndex()));
        }
        if (node.right != null) {
          queue.offer(new Pair(node.right, current.rightIndex()));
        }
      }
      // Calculate width of current level
      int currentWidth = rightmostIndex - leftmostIndex + 1;
      maxWidth = Math.max(maxWidth, currentWidth);
    }

    return maxWidth;
  }

  // Helper class to store node and its index
  class Pair {
    TreeNode node;
    int index;

    Pair(TreeNode node, int index) {
      this.node = node;
      this.index = index;
    }

    // method for next left Index and right Index
    int leftIndex() {
      return 2 * index + 1;
    }
    int rightIndex() {
      return 2 * index + 2;
    }
  }
}
