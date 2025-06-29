package com.monal.BST;

import java.util.*;

/*
 * Merge two BSTs into a single BST.
 */
public class P009 {

  public TreeNode mergeTwoBSTs(TreeNode root1, TreeNode root2) {
    List<Integer> list1 = new ArrayList<>();
    List<Integer> list2 = new ArrayList<>();
    inorder(root1, list1);
    inorder(root2, list2);

    List<Integer> merged = mergeSortedLists(list1, list2);
    return sortedListToBST(merged, 0, merged.size() - 1);
  }

  private void inorder(TreeNode root, List<Integer> list) {
    if (root == null)
      return;
    inorder(root.left, list);
    list.add(root.val);
    inorder(root.right, list);
  }

  private List<Integer> mergeSortedLists(List<Integer> a, List<Integer> b) {
    List<Integer> result = new ArrayList<>();
    int i = 0, j = 0;
    while (i < a.size() && j < b.size()) {
      if (a.get(i) < b.get(j))
        result.add(a.get(i++));
      else
        result.add(b.get(j++));
    }
    while (i < a.size())
      result.add(a.get(i++));
    while (j < b.size())
      result.add(b.get(j++));
    return result;
  }

  private TreeNode sortedListToBST(List<Integer> list, int start, int end) {
    if (start > end)
      return null;
    int mid = start + (end - start) / 2;
    TreeNode root = new TreeNode(list.get(mid));
    root.left = sortedListToBST(list, start, mid - 1);
    root.right = sortedListToBST(list, mid + 1, end);
    return root;
  }

  public class TreeNode {
    int val;
    TreeNode left, right;

    TreeNode(int x) {
      val = x;
    }
  }

  public static void main(String[] args) {
    P009 p = new P009();
    TreeNode root1 = p.new TreeNode(2);
    root1.left = p.new TreeNode(1);
    root1.right = p.new TreeNode(3);

    TreeNode root2 = p.new TreeNode(5);
    root2.left = p.new TreeNode(4);
    root2.right = p.new TreeNode(6);

    TreeNode mergedRoot = p.mergeTwoBSTs(root1, root2);
    p.printTree(mergedRoot); // Should print the merged BST in sorted order
    System.out.println();

  }

  private void printTree(TreeNode root) {
    if (root == null) {
      System.out.print("null ");
      return;
    }
    System.out.print(root.val + " ");
    printTree(root.left);
    printTree(root.right);
  }
}
