package com.monal.Trees.GoodProblems;
/* Maximum Sum BST in a Binary Tree
 * Given a binary tree root, return the maximum sum of all keys of any sub-tree which is also a Binary Search Tree (BST).
 */

public class P009 {
  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  /*
   * For each node, you call isBST() which traverses the entire subtree
   * You do this for every node in the tree
   * This results in redundant traversals
   */
  public class Solution1 {

    public int maxSumBST(TreeNode root) {
      int[] result = new int[1]; // Use an array to keep track of the maximum sum
      if (root == null)
        return 0;
      maxSumBSTHelper(root, result);
      return result[0];
    }

    private int maxSumBSTHelper(TreeNode node, int[] result) {
      if (node == null)
        return 0;
      // Check if the current subtree is a BST
      if (isBST(node, Integer.MIN_VALUE, Integer.MAX_VALUE)) {
        // If it is a BST, calculate the sum of the subtree
        int leftSum = maxSumBSTHelper(node.left, result);
        int rightSum = maxSumBSTHelper(node.right, result);
        int currentSum = leftSum + rightSum + node.val;
        // Update the maximum sum found so far
        result[0] = Math.max(result[0], currentSum);
        return currentSum; // Return the sum of the current subtree
      } else {
        // if the current subtree is not a BST, we return 0
        maxSumBSTHelper(node.left, result);
        maxSumBSTHelper(node.right, result);
        return 0; // Not a BST, so we return 0
      }
    }

    private boolean isBST(TreeNode node, int min, int max) {
      if (node == null)
        return true;
      if (node.val <= min || node.val >= max)
        return false;
      return isBST(node.left, min, node.val) && isBST(node.right, node.val, max);
    }

  }

  public class Solution2 {

    // OPTIMIZED -
    // Use a single traversal to check if the subtree is a BST and calculate the sum
    private int maxSum = 0;

    public int maxSumBST(TreeNode root) {
      helper(root);
      return maxSum;
    }

    private int[] helper(TreeNode node) {
      if (node == null)
        return new int[] { 1, 0, Integer.MAX_VALUE, Integer.MIN_VALUE };
      // isBST, sum, min, max

      int[] left = helper(node.left);
      int[] right = helper(node.right);

      boolean isBST = left[0] == 1 && right[0] == 1 &&
          node.val > left[3] && node.val < right[2];

      if (isBST) {
        int sum = left[1] + right[1] + node.val;
        maxSum = Math.max(maxSum, sum);
        return new int[] {
            1, // is BST
            sum, // total sum
            Math.min(node.val, left[2]), // min
            Math.max(node.val, right[3]) // max
        };
      } else {
        return new int[] { 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE };
      }
    }
  }

  // =========================================================== //
  public static void main(String[] args) {
    P009 p009 = new P009();
    Solution1 solution1 = p009.new Solution1();
    Solution2 solution2 = p009.new Solution2();

    // Example usage
    TreeNode root = p009.new TreeNode(10);
    root.left = p009.new TreeNode(5);
    root.right = p009.new TreeNode(15);
    root.left.left = p009.new TreeNode(1);
    root.left.right = p009.new TreeNode(8);
    root.right.right = p009.new TreeNode(7);

    System.out.println("Max Sum BST (Solution 1): " + solution1.maxSumBST(root));
    System.out.println("Max Sum BST (Solution 2): " + solution2.maxSumBST(root));
  }
}
