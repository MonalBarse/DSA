package com.monal.BST;

/*
* DELETING NODE From BST
*/

public class P004 {
  public TreeNode deleteNode(TreeNode root, int val) {
    if (root == null)
      return null;

    // If the value to be deleted is smaller than the root's value,
    // then it lies in the left subtree.
    if (val < root.val) {
      root.left = deleteNode(root.left, val);
    } else if (val > root.val) {
      // If the value to be deleted is greater than the root's value,
      // then it lies in the right subtree.
      root.right = deleteNode(root.right, val);
    } else {
      // Node with only one child or no child
      if (root.left == null) {
        return root.right;
      } else if (root.right == null) {
        return root.left;
      }

      // Node with two children: Get inorder successor (smallest in the right
      TreeNode minNode = findMin(root.right);
      root.val = minNode.val; // Copy the inorder successor's value to this node
      root.right = deleteNode(root.right, minNode.val); // Delete the inorder successor
    }
    return root; // Return the (possibly unchanged) node pointer
  }

  private TreeNode findMin(TreeNode node) {
    while (node.left != null) {
      node = node.left;
    }
    return node;
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
  }

  public static void main(String[] args) {
    P004 p004 = new P004();
    P004.TreeNode root = p004.new TreeNode(5);
    root.left = p004.new TreeNode(3);
    root.right = p004.new TreeNode(8);
    root.left.left = p004.new TreeNode(2);
    root.left.right = p004.new TreeNode(4);
    root.right.right = p004.new TreeNode(9);

    int valToDelete = 3;
    P004.TreeNode updatedRoot = p004.deleteNode(root, valToDelete);

    // Print the updated BST (in-order traversal)
    printInOrder(updatedRoot);
  }

  private static void printInOrder(P004.TreeNode node) {
    if (node == null) {
      return;
    }
    printInOrder(node.left);
    System.out.print(node.val + " ");
    printInOrder(node.right);
  }
}
