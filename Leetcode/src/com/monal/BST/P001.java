package com.monal.BST;

/*
Search in a Binary Search Tree
*/
public class P001 {
  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  public TreeNode searchBST(TreeNode root, int target) {
    if (root == null || root.val == target)
      return root;
    if (target < root.val) {
      return searchBST(root.left, target);
    } else {
      return searchBST(root.right, target);
    }
  }

  public static void main(String[] args) {
    P001 p = new P001();
    TreeNode root = p.new TreeNode(4);
    root.left = p.new TreeNode(2);
    root.right = p.new TreeNode(7);
    root.left.left = p.new TreeNode(1);
    root.left.right = p.new TreeNode(3);

    int target = 2;
    TreeNode result = p.searchBST(root, target);
    if (result != null) {
      System.out.println("Found node with value: " + result.val);
    } else {
      System.out.println("Node not found.");
    }
  }

}
