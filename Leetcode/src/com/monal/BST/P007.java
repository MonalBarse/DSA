package com.monal.BST;

/*
Construct a BST from a preorder traversal & Find Inorder Successor & Predecessor in BST
 */
public class P007 {
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

  public TreeNode bstFromPreorder(int[] preorder) {
    int index[] = { 0 }; // Use an array to keep track of the current index in preorder
    return build1(preorder, index, Integer.MAX_VALUE, Integer.MIN_VALUE);
  }

  public TreeNode bstFromPostorder(int[] postorder) {
    int index[] = { postorder.length - 1 }; // Use an array to keep track of the current index in postorder
    return build2(postorder, index, Integer.MAX_VALUE, Integer.MIN_VALUE);
  }

  private TreeNode build1(int[] preorder, int[] index, int max, int min) {
    if (preorder.length == index[0] || preorder[index[0]] > max || preorder[index[0]] < min)
      return null;
    TreeNode root = new TreeNode(preorder[index[0]++]);
    root.left = build1(preorder, index, root.val, min);
    root.right = build1(preorder, index, max, root.val);
    return root;
  }

  private TreeNode build2(int[] postorder, int[] index, int max, int min) {
    if (index[0] < 0 || postorder[index[0]] > max || postorder[index[0]] < min)
      return null;
    TreeNode root = new TreeNode(postorder[index[0]--]);
    root.right = build2(postorder, index, max, root.val);
    root.left = build2(postorder, index, root.val, min);
    return root;
  }

  public int inorderSuccessor(TreeNode root, TreeNode p) {
    if (root == null || p == null)
      return -1;
    TreeNode successor = null;
    while (root != null) {
      if (p.val < root.val) {
        successor = root; // Potential successor
        root = root.left; // Move left
      } else {
        root = root.right; // Move right
      }
    }
    return successor != null ? successor.val : -1; // Return the value of the successor or -1 if not found
  }

  public int inorderPredecessor(TreeNode root, TreeNode p) {
    if (root == null || p == null)
      return -1;
    TreeNode predecessor = null;
    while (root != null) {
      if (p.val < root.val) {
        root = root.left; // Move left
      } else {
        predecessor = root; // Potential predecessor
        root = root.right; // Move right
      }
    }
    return predecessor != null ? predecessor.val : -1; // Return the value of the predecessor or -1 if not found
  }


  public static void main(String[] args) {
    P007 solution = new P007();
    int[] preorder = { 8, 5, 1, 7, 10, 12 };
    TreeNode rootFromPreorder = solution.bstFromPreorder(preorder);
    System.out.println("BST from Preorder: " + rootFromPreorder.val);

    int[] postorder = { 1, 7, 5, 12, 10, 8 };
    TreeNode rootFromPostorder = solution.bstFromPostorder(postorder);
    System.out.println("BST from Postorder: " + rootFromPostorder.val);

    TreeNode p = new P007().new TreeNode(5);
    int successor = solution.inorderSuccessor(rootFromPreorder, p);
    System.out.println("Inorder Successor of " + p.val + ": " + successor);

    int predecessor = solution.inorderPredecessor(rootFromPreorder, p);
    System.out.println("Inorder Predecessor of " + p.val + ": " + predecessor);
  }

}
