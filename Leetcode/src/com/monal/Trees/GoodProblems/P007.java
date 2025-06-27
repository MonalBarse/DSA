package com.monal.Trees.GoodProblems;

import java.util.*;

/* Create a utility function that serializes and deserializes a binary tree. */
public class P007 {

  // ================ Serialization ================ //
  public String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    serializeHelper(root, sb);
    return sb.toString();
  }

  private void serializeHelper(TreeNode node, StringBuilder sb) {
    if (node == null) {
      sb.append("null,");
      return;
    }

    sb.append(node.val).append(",");
    serializeHelper(node.left, sb);
    serializeHelper(node.right, sb);
  }

  // ================ Deserialization ================ //
  public TreeNode deserialize(String data) {
    if (data == null || data.isEmpty()) {
      return null;
    }

    String[] nodes = data.split(",");
    Queue<String> queue = new LinkedList<>(Arrays.asList(nodes));
    return deserializeHelper(queue);
  }

  private TreeNode deserializeHelper(Queue<String> queue) {
    if (queue.isEmpty()) {
      return null;
    }

    String val = queue.poll();
    if (val.equals("null")) {
      return null;
    }

    TreeNode node = new TreeNode(Integer.parseInt(val));
    node.left = deserializeHelper(queue);
    node.right = deserializeHelper(queue);
    return node;
  }

  // ================ Helper method for testing ================ //
  public void printInorder(TreeNode root) {
    if (root == null) {
      return;
    }
    printInorder(root.left);
    System.out.print(root.val + " ");
    printInorder(root.right);
  }

  // ================ Example Usage ================ //
  public static void main(String[] args) {
    P007 serializer = new P007();

    // Example 1: Simple tree
    TreeNode root1 = serializer.new TreeNode(1);
    root1.left = serializer.new TreeNode(2);
    root1.right = serializer.new TreeNode(3);
    root1.left.left = serializer.new TreeNode(4);
    root1.left.right = serializer.new TreeNode(5);

    System.out.println("=== Example 1 ===");
    System.out.print("Original tree (inorder): ");
    serializer.printInorder(root1);
    System.out.println();

    String serialized1 = serializer.serialize(root1);
    System.out.println("Serialized: " + serialized1);

    TreeNode deserialized1 = serializer.deserialize(serialized1);
    System.out.print("Deserialized tree (inorder): ");
    serializer.printInorder(deserialized1);
    System.out.println();

    // Example 2: More complex tree
    TreeNode root2 = serializer.new TreeNode(1);
    root2.left = serializer.new TreeNode(2);
    root2.right = serializer.new TreeNode(3);
    root2.left.left = serializer.new TreeNode(4);
    root2.left.right = serializer.new TreeNode(5);
    root2.right.left = serializer.new TreeNode(6);
    root2.right.right = serializer.new TreeNode(7);
    root2.left.left.left = serializer.new TreeNode(8);
    root2.left.left.right = serializer.new TreeNode(9);
    root2.right.left.left = serializer.new TreeNode(10);
    root2.right.left.right = serializer.new TreeNode(11);

    System.out.println("\n=== Example 2 ===");
    System.out.print("Original tree (inorder): ");
    serializer.printInorder(root2);
    System.out.println();

    String serialized2 = serializer.serialize(root2);
    System.out.println("Serialized: " + serialized2);

    TreeNode deserialized2 = serializer.deserialize(serialized2);
    System.out.print("Deserialized tree (inorder): ");
    serializer.printInorder(deserialized2);
    System.out.println();

    // Example 3: Edge cases
    System.out.println("\n=== Edge Cases ===");

    // Empty tree
    String emptyTree = serializer.serialize(null);
    System.out.println("Empty tree serialized: " + emptyTree);
    TreeNode emptyDeserialized = serializer.deserialize(emptyTree);
    System.out.println("Empty tree deserialized: " + (emptyDeserialized == null ? "null" : "not null"));

    // Single node tree
    TreeNode singleNode = serializer.new TreeNode(42);
    String singleSerialized = serializer.serialize(singleNode);
    System.out.println("Single node serialized: " + singleSerialized);
    TreeNode singleDeserialized = serializer.deserialize(singleSerialized);
    System.out.println("Single node deserialized value: " + singleDeserialized.val);
  }

  public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
      left = null;
      right = null;
    }

    @SuppressWarnings("unused")
    TreeNode(int x, TreeNode left, TreeNode right) {
      val = x;
      this.left = left;
      this.right = right;
    }
  }
}