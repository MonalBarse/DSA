package com.monal.BST;

import java.util.*;

/*
  Implement the BSTIterator class that represents an iterator over the
  in-order traversal of a binary search tree (BST):

  BSTIterator(TreeNode root) Initializes an object of the BSTIterator class.
  The root of the BST is given as part of the constructor. The pointer should be
  initialized to a non-existent number smaller than any element in the BST.
  boolean hasNext() Returns true if there exists a number in the traversal to
  the right of the pointer, otherwise returns false.
  int next() Moves the pointer to the right, then returns the number at the pointer.
  Notice that by initializing the pointer to a non-existent smallest number, the first
  call to next() will return the smallest element in the BST.
  You may assume that next() calls will always be valid. That is, there will be at
  least a next number in the in-order traversal when next() is called.
*/
public class P008 {

  class BSTIterator {

    private final Stack<TreeNode> stack = new Stack<>();

    public BSTIterator(TreeNode root) {
      pushAllLeft(root);
    }

    public boolean hasNext() {
      return !stack.isEmpty();
    }

    public int next() {
      TreeNode node = stack.pop();
      pushAllLeft(node.right);
      return node.val;
    }

    private void pushAllLeft(TreeNode node) {
      while (node != null) {
        stack.push(node);
        node = node.left;
      }
    }

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
    P008 bstIterator = new P008();
    TreeNode root = bstIterator.new TreeNode(7);
    root.left = bstIterator.new TreeNode(3);
    root.right = bstIterator.new TreeNode(15);
    root.right.left = bstIterator.new TreeNode(9);
    root.right.right = bstIterator.new TreeNode(20);
    BSTIterator iterator = bstIterator.new BSTIterator(root);
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
    // Output should be the in-order traversal: 3, 7, 9, 15, 20
  }

}
