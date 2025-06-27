package com.monal.Trees.Traversals;

import java.util.*;

public class Traversals01 {

  // ======================== TREE TRAVERSALS ========================

  /*
   * TREE TRAVERSALS: The key to understanding trees! Think of traversals as ways
   * to "visit" every
   * node in the tree in a specific order.
   *
   * Common traversal types: -
   * - Preorder (DFS)
   * - Inorder (DFS)
   * - Postorder (DFS)
   * - Level Order (BFS)
   */

  public static List<Integer> preorderTraversal(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    preorderHelper(root, result);
    return result;
  }

  private static void preorderHelper(TreeNode node, List<Integer> result) {
    if (node == null)
      return;

    result.add(node.val);
    // process left
    preorderHelper(node.left, result);
    // process right
    preorderHelper(node.right, result);
    // Note: No need to return anything, we are collecting results in the list
  }

  /*
   * INORDER TRAVERSAL: Left -> Root -> Right
   * Use case: BST gives sorted order, expression trees give infix notation
   * Think: "Process current node BETWEEN its children"
   */
  public static void inorderTraversal(TreeNode root) {
    if (root == null)
      return;

    inorderTraversal(root.left); // First left subtree
    System.out.print(root.val + " "); // Then current node
    inorderTraversal(root.right); // Then right subtree
  }

  /**
   * POSTORDER TRAVERSAL: Left -> Right -> Root Use case: Deleting tree,
   * calculating size/height,
   * expression evaluation Think: "Process current node AFTER its children"
   */
  public static void postorderTraversal(TreeNode root) {
    if (root == null)
      return;
    postorderTraversal(root.left); // First left subtree
    postorderTraversal(root.right); // Then right subtree
    System.out.print(root.val + " "); // Finally current no```
  }

  /**
   * LEVEL ORDER TRAVERSAL (BFS): Level by level, left to right Use case: Printing
   * tree level by
   * level, finding shortest path in unweighted tree This is your BFS knowledge
   * applied to trees!
   */
  public static List<List<Integer>> levelOrderTraversal(TreeNode root) {
    if (root == null)
      return new ArrayList<>();
    List<List<Integer>> result = new ArrayList<>();
    Queue<TreeNode> q = new ArrayDeque<>();
    q.offer(root);

    while (!q.isEmpty()) {
      int levelSize = q.size();
      List<Integer> currentLevel = new ArrayList<>();
      for (int i = 0; i < levelSize; i++) {
        TreeNode node = q.poll();
        currentLevel.add(node.val);
        if (node.left != null)
          q.offer(node.left);
        if (node.right != null)
          q.offer(node.right);
      }
      result.add(currentLevel);
    }
    return result;
  }

  public static class AllTraversals {
    List<Integer> preorder;
    List<Integer> inorder;
    List<Integer> postorder;
  }

  public static AllTraversals allTraversalsInOneTraversal(TreeNode root) {
    AllTraversals traversals = new AllTraversals();
    traversals.preorder = new ArrayList<>();
    traversals.inorder = new ArrayList<>();
    traversals.postorder = new ArrayList<>();
    allTraversalsHelper(root, traversals);
    return traversals;
  }

  private static void allTraversalsHelper(TreeNode node, AllTraversals traversals) {
    if (node == null)
      return;

    // Preorder - process current node first
    traversals.preorder.add(node.val);

    // Recursively process left subtree
    allTraversalsHelper(node.left, traversals);

    // Inorder - process current node after left subtree
    traversals.inorder.add(node.val);

    // Recursively process right subtree
    allTraversalsHelper(node.right, traversals);

    // Postorder - process current node after both subtrees
    traversals.postorder.add(node.val);
  }


  static class TreeNode {
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
