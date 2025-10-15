package com.monal.LinkedList;

public class DoublyNode {
  public int val;
  public DoublyNode next;
  public DoublyNode prev;

  public DoublyNode() {
  }

  public DoublyNode(int val) {
    this.val = val;
  }

  public DoublyNode(int val, DoublyNode next, DoublyNode prev) {
    this.val = val;
    this.next = next;
    this.prev = prev;
  }

  @Override
  public String toString() {
    return String.valueOf(val);
  }
}
