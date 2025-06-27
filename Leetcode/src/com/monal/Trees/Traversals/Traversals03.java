package com.monal.Trees.Traversals;

import java.util.*;

public class Traversals03 {
  /*
   * Vertical Order Traversal of a Binary Tree
   * In this traversal, nodes are visited in vertical order.
   * Nodes are grouped by their vertical distance from the root.
   */
  public class VerticalOrderTraversal {
    public List<List<Integer>> verticalTraversal(TreeNode root) {
      // We create a map to hold - x coordinate (vertical distance) as key
      // and a list of nodes at that vertical distance as value.
      TreeMap<Integer, List<Touple>> map = new TreeMap<>();
      Queue<Touple> q = new LinkedList<>();
      q.offer(new Touple(root, 0, 0));

      while (!q.isEmpty()) {
        Touple p = q.poll();
        TreeNode node = p.node;
        int x = p.x, y = p.y;

        map.putIfAbsent(x, new ArrayList<>());
        map.get(x).add(p); // Add the current node to the list at its vertical distance
        if (node.left != null)
          q.offer(new Touple(node.left, x - 1, y + 1)); // Traverse left
        if (node.right != null)
          q.offer(new Touple(node.right, x + 1, y + 1)); // Traverse right
      }

      List<List<Integer>> result = new ArrayList<>();
      // Prepare the result from the map
      // Each key in the map corresponds to a vertical distance
      // Each value is a list of nodes at that distance
      for (List<Touple> column : map.values()) {
        // Sort by y coordinate, then by node value
        column.sort((a, b) -> a.y == b.y ? a.node.val - b.node.val : a.y - b.y);
        List<Integer> col = new ArrayList<>();
        for (Touple p : column)
          col.add(p.node.val);
        result.add(col);
      }

      return result;
    }

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

    public class Touple {
      TreeNode node;
      int x, y;

      Touple(TreeNode n, int x, int y) {
        this.node = n;
        this.x = x;
        this.y = y;
      }
    }
  }

  public static void main(String[] args) {
    // Example usage
    Traversals03 traversal = new Traversals03();
    VerticalOrderTraversal verticalOrder = traversal.new VerticalOrderTraversal();

    VerticalOrderTraversal.TreeNode root = new VerticalOrderTraversal.TreeNode(1);
    root.left = new VerticalOrderTraversal.TreeNode(2);
    root.right = new VerticalOrderTraversal.TreeNode(3);
    root.left.left = new VerticalOrderTraversal.TreeNode(4);
    root.left.right = new VerticalOrderTraversal.TreeNode(5);
    root.right.right = new VerticalOrderTraversal.TreeNode(6);

    List<List<Integer>> result = verticalOrder.verticalTraversal(root);
    System.out.println(result); // Output: [[4], [2], [1, 5, 3], [6]]
  }
}