package com.monal.BST;

/* Insert A given node in a BST
You are given the root node of a binary search tree (BST) and a value to insert into the tree.
Return the root node of the BST after the insertion. It is guaranteed that the new value
does not exist in the original BST. Notice that there may exist multiple valid ways for
the insertion, as long as the tree remains a BST after insertion. You can return any of them.
Example 1:
  Input: root = [4,2,7,1,3], val = 5
  Output: [4,2,7,1,3,5] or [5,3,7,1,2,4]

Example 2:
  Input: root = [40,20,60,10,30,50,70], val = 25
  Output: [40,20,60,10,30,50,70,null,null,25]
*/
public class P003 {
  public TreeNode insertIntoBST(TreeNode root, int val) {
    if (root == null)
      return new TreeNode(val);

    if (val < root.val) {
      root.left = insertIntoBST(root, val);
    } else {
      root.right = insertIntoBST(root, val);
    }
    return root;
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
    P003 p003 = new P003();
    P003.TreeNode root = p003.new TreeNode(4);
    root.left = p003.new TreeNode(2);
    root.right = p003.new TreeNode(7);
    root.left.left = p003.new TreeNode(1);
    root.left.right = p003.new TreeNode(3);

    int valToInsert = 5;
    P003.TreeNode updatedRoot = p003.insertIntoBST(root, valToInsert);

    // Print the updated BST (in-order traversal)
    printInOrder(updatedRoot);
  }

  private static void printInOrder(P003.TreeNode node) {
    if (node == null) {
      return;
    }
    printInOrder(node.left);
    System.out.print(node.val + " ");
    printInOrder(node.right);
  }
}
