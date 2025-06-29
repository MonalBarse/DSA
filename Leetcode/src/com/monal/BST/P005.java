package com.monal.BST;

/*
Solving for kth smallest elemnt in BST.
j
*/
public class P005 {
  public static class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
      val = x;
      left = null;
      right = null;
    }
  }

  public int kthSmallest(TreeNode root, int k) {
    if (root == null || k <= 0)
      return -1;

    return kthSmallestHelper(root, k, new int[1]);
  }

  private int kthSmallestHelper(TreeNode node, int k, int[] count) {
    if (node == null)
      return -1;
    // Search in left subtree
    int left = kthSmallestHelper(node.left, k, count);
    if (left != -1)
      return left; // Found in left subtree

    // Process current node
    count[0]++;
    if (count[0] == k)
      return node.val; // Found kth smallest
    if (count[0] > k)
      return -1; // If count exceeds k, return -1

    // Search in right subtree
    return kthSmallestHelper(node.right, k, count);
  }

  public int kthSmallestMorris(TreeNode root, int k) {
    if (root == null || k <= 0) {
      return -1; // Invalid input
    }
    TreeNode current = root;
    int count = 0;

    while (current != null) {
      if (current.left == null) {
        // Process current node
        count++;
        if (count == k) {
          return current.val;
        }
        current = current.right;
      } else {
        // Find predecessor
        TreeNode predecessor = current.left;
        while (predecessor.right != null && predecessor.right != current) {
          predecessor = predecessor.right;
        }

        if (predecessor.right == null) {
          predecessor.right = current;
          current = current.left;
        } else {
          predecessor.right = null;
          // Process current node
          count++;
          if (count == k) {
            return current.val;
          }
          current = current.right;
        }
      }
    }

    return -1; // k is larger than number of nodes
  }

  public static void main(String[] args) {
    P005 p005 = new P005();
    TreeNode root = new TreeNode(5);
    root.left = new TreeNode(3);
    root.right = new TreeNode(8);
    root.left.left = new TreeNode(2);
    root.left.right = new TreeNode(4);
    root.right.right = new TreeNode(9);

    int k = 3; // Find the 3rd smallest element
    int result = p005.kthSmallest(root, k);
    System.out.println("The " + k + "rd smallest element is: " + result);

    // Using Morris Traversal
    int morrisResult = p005.kthSmallestMorris(root, k);
    System.out.println("The " + k + "rd smallest element using Morris Traversal is: " + morrisResult);

    // using normal inorder traversal
    int[] count = { 0 };
    int inorderResult = p005.kthSmallestHelper(root, k, count);
    System.out.println("The " + k + "rd smallest element using inorder traversal is: " + inorderResult);
  }
}
