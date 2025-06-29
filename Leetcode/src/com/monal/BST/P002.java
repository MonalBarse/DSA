package com.monal.BST;

/* Cealing in a BST
  Return a minimum number which is just greater than or equal to the target.
  If no such number exists, return -1.
*/

public class P002 {
  public static int cealingInBST(TreeNode root, int target) {
    if (root == null) {
      return -1; // If the tree is empty, return -1
    }
    return cealingInBSTHelper(root, target, Integer.MAX_VALUE);
  }

  private static int cealingInBSTHelper(TreeNode node, int target, int minCeiling) {
    if (node == null)
      return minCeiling == Integer.MAX_VALUE ? -1 : minCeiling;

    if (node.val == target)
      return node.val; // Found exact match
    if (node.val < target) {
      // If current node's value is less than target, search in the right subtree
      return cealingInBSTHelper(node.right, target, minCeiling);
    } else {
      // If current node's value is greater than or equal to target,
      // it could be a potential ceiling, so check left subtree
      minCeiling = Math.min(minCeiling, node.val);
      return cealingInBSTHelper(node.left, target, minCeiling);
    }
  }

  private static int floorInBST(TreeNode root, int target) {
    if (root == null)
      return -1;
    return floorInBSTHelp(root, target, Integer.MIN_VALUE);
  }

  private static int floorInBSTHelp(TreeNode node, int target, int maxFloor) {
    if (node == null)
      return maxFloor == Integer.MIN_VALUE ? -1 : maxFloor;

    if (node.val == target)
      return node.val; // Found exact match
    if (node.val > target) {
      // If current node's value is greater than target, search in the left subtree
      return floorInBSTHelp(node.left, target, maxFloor);
    } else {
      // If current node's value is less than or equal to target,
      // it could be a potential floor, so check right subtree
      maxFloor = Math.max(maxFloor, node.val);
      return floorInBSTHelp(node.right, target, maxFloor);
    }
  }

  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
    }
  }

  public static void main(String[] args) {
    P002 p = new P002();
    TreeNode root = p.new TreeNode(88);
    root.left = p.new TreeNode(44);
    root.right = p.new TreeNode(99);
    root.left.left = p.new TreeNode(22);
    root.left.right = p.new TreeNode(66);
    root.right.left = p.new TreeNode(77);
    root.right.right = p.new TreeNode(100);
    root.left.left.left = p.new TreeNode(11);
    root.left.left.right = p.new TreeNode(33);
    root.left.right.left = p.new TreeNode(55);
    root.left.right.right = p.new TreeNode(77);
    root.right.left.left = p.new TreeNode(66);
    root.right.left.right = p.new TreeNode(89);
    root.right.right.left = p.new TreeNode(90);
    root.right.right.right = p.new TreeNode(101);


    int target = 6;
    int result = cealingInBST(root, target);
    System.out.println("Ceiling of " + target + " in BST: " + result); // Output: 7

    int floorResult = floorInBST(root, target);
    System.out.println("Floor of " + target + " in BST: " + floorResult); // Output: 5
  }

}
