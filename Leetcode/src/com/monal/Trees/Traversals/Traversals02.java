package com.monal.Trees.Traversals;

import java.util.*;

public class Traversals02 {

  /*
   * LEVEL ORDER with LEVEL SEPARATION - (Uses logic similar to BFS)
   * Returns list of lists, each inner list represents one level
   */
  public static List<List<Integer>> levelOrderWithLevels(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null)
      return result;

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      int levelSize = queue.size(); // KEY: Current level's node count
      List<Integer> currentLevel = new ArrayList<>();

      // Process all nodes at current level
      for (int i = 0; i < levelSize; i++) {
        TreeNode node = queue.poll();
        currentLevel.add(node.val);

        if (node.left != null)
          queue.offer(node.left);
        if (node.right != null)
          queue.offer(node.right);
      }
      result.add(currentLevel);
    }
    return result;
  }

  /*
   * ZigZag Level Order Traversal
   * Traverse tree level by level, alternating direction at each level
   * Use case: Printing tree in a zigzag pattern
   * Approach: Use a queue for BFS and a flag to alternate direction
   * Time Complexity: O(n) where n is number of nodes
   * Space Complexity: O(n) for queue storage
   */

  public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null)
      return result;

    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);
    boolean leftToRight = true; // Flag to alternate direction

    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      List<Integer> currentLevel = new ArrayList<>();

      for (int i = 0; i < levelSize; i++) {
        TreeNode node = queue.poll();
        if (leftToRight) {
          currentLevel.add(node.val); // Add normally
        } else {
          currentLevel.add(0, node.val); // Add to front for reverse order
        }
        if (node.left != null)
          queue.offer(node.left);
        if (node.right != null)
          queue.offer(node.right);
      }
      result.add(currentLevel);
      leftToRight = !leftToRight; // Toggle direction for next level
    }
    return result;
  }

  /*
   * Boundary Traversal of Binary Tree
   * Traverse the boundary of a binary tree in anticlockwise direction
   * Includes left boundary, leaves, and right boundary (in reverse)
   *
   * Time Complexity: O(n) where n is number of nodes
   * Space Complexity: O(h) for recursion stack where h is height of tree
   */

  public class BoundaryTraversal {
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
      List<Integer> boundary = new ArrayList<>();
      if (root == null) {
        return boundary;
      }
      if (!isLeaf(root))
        boundary.add(root.val); // Add root if not a leaf
      addLeftBoundary(root.left, boundary);
      addLeaves(root, boundary);
      addRightBoundaryInReverse(root.right, boundary);
      return boundary;
    }

    private boolean isLeaf(TreeNode node) {
      if (node == null)
        return false;

      return node.left == null && node.right == null;
    }

    private void addLeftBoundary(TreeNode node, List<Integer> boundary) {
      if (node == null || isLeaf(node)) {
        return;
      }
      boundary.add(node.val); // Add current node to boundary
      if (node.left != null) {
        addLeftBoundary(node.left, boundary); // Go left
      } else {
        addLeftBoundary(node.right, boundary); // If no left only then go right
      }
    }

    private void addLeaves(TreeNode node, List<Integer> boundary) {
      if (node == null)
        return;
      if (isLeaf(node))
        boundary.add(node.val);
      else {
        addLeaves(node.left, boundary); // Traverse left subtree
        addLeaves(node.right, boundary); // Traverse right subtree
      }
    }

    private void addRightBoundaryInReverse(TreeNode node, List<Integer> boundary) {
      Stack<Integer> stack = new Stack<>();
      while (node != null) {
        if (!isLeaf(node)) {
          stack.push(node.val); // Push to stack if not a leaf
        }
        if (node.right != null) {
          node = node.right;
        } else {
          node = node.left;
        }
      }
      // Pop from stack to add right boundary in reverse order
      while (!stack.isEmpty()) {
        boundary.add(stack.pop());
      }
      // This ensures right boundary is added in reverse order
    }
  }

  // ==================================TreeNode=====================================
  // //
  public static class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int val) {
      this.val = val;
      this.left = this.right = null;
    }

    @SuppressWarnings("unused")
    TreeNode(int val, TreeNode left, TreeNode right) {
      this.val = val;
      this.left = left;
      this.right = right;
    }
  }

  public static void main(String[] args) {
    // Example usage
    TreeNode root = new TreeNode(1);
    root.left = new TreeNode(2);
    root.right = new TreeNode(3);
    root.left.left = new TreeNode(4);
    root.left.right = new TreeNode(5);
    root.right.right = new TreeNode(6);

    List<List<Integer>> levelOrderResult = levelOrderWithLevels(root);
    System.out.println("Level Order with Levels: " + levelOrderResult);

    Traversals02 traversals = new Traversals02();
    List<List<Integer>> zigzagResult = traversals.zigzagLevelOrder(root);
    System.out.println("Zigzag Level Order: " + zigzagResult);

    BoundaryTraversal boundaryTraversal = traversals.new BoundaryTraversal();
    List<Integer> boundaryResult = boundaryTraversal.boundaryOfBinaryTree(root);
    System.out.println("Boundary Traversal: " + boundaryResult);
  }
}
