package com.monal.Trees.Traversals;

import java.util.*;

public class Traversals04 {

  // Solved using recursion
  public boolean isSymmetric(TreeNode root) {
    if (root == null)
      return true;
    return isMirror(root.left, root.right);
  }

  private boolean isMirror(TreeNode left, TreeNode right) {
    if (left == null && right == null)
      return true;
    if (left == null || right == null)
      return false;
    return (left.val == right.val) && isMirror(left.left, right.right) &&
        isMirror(left.right, right.left);
  }

  // Solved using iteration
  public boolean isSymmetricIterative(TreeNode root) {
    if (root == null)
      return true;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root.left);
    queue.offer(root.right);

    while (!queue.isEmpty()) {
      TreeNode left = queue.poll();
      TreeNode right = queue.poll();

      if (left == null && right == null)
        continue;
      if (left == null || right == null || left.val != right.val)
        return false;

      queue.offer(left.left);
      queue.offer(right.right);
      queue.offer(left.right);
      queue.offer(right.left);
    }
    return true;

  }

  // ------------------------------------------------------ //
  // ------------------ Path to the node ------------------ //

  public static List<Integer> pathToNode(TreeNode root, int target) {
    List<Integer> path = new ArrayList<>();

    if (findPath(root, target, path)) {
      return path; // If path found, return it
    } else {
      return new ArrayList<>(); // If not found, return empty list
    }
  }

  // Which traversal is this? = This is backtracking so a DFS traversal
  private static boolean findPath(TreeNode node, int target, List<Integer> path) {
    if (node == null)
      return false;

      // MAKE CHOICE
    path.add(node.val); // Add current node to path
    if (node.val == target)
      return true; // If target found, return

      // EXPLORE - If viable we will return true
    if (findPath(node.left, target, path) || findPath(node.right, target, path)) {
      return true; // If target found in either subtree, return
    }

     // IF IT DID NOT WORK OUT, BACKTRACK
    path.remove(path.size() - 1);
    return false;
  }

  public static void main(String[] args) {
    Traversals04 traversals = new Traversals04();

    // Example usage for isSymmetric
    TreeNode root1 = new TreeNode(1, new TreeNode(2), new TreeNode(2));
    System.out.println(traversals.isSymmetric(root1)); // true

    // Example usage for isSymmetricIterative
    TreeNode root1_1 = new TreeNode(1, new TreeNode(2, new TreeNode(3), null),
        new TreeNode(2, null, new TreeNode(3)));
    System.out.println(traversals.isSymmetricIterative(root1_1)); // false

    TreeNode root2 = new TreeNode(1, new TreeNode(2, new TreeNode(3), null),
        new TreeNode(2, null, new TreeNode(3)));
    System.out.println(traversals.isSymmetricIterative(root2)); // false

    // Example usage for pathToNode
    TreeNode root3 = new TreeNode(1, new TreeNode(2), new TreeNode(3));
    System.out.println(pathToNode(root3, 3)); // [1, 3]
  }
  public static class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
      this.val = val;
      this.left = this.right = null;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }
}
