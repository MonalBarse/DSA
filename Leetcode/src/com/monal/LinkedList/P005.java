package com.monal.LinkedList;

public class P005 {
  // Delete a Node from a DLL
  public DoublyNode deleteNode(DoublyNode head, DoublyNode del) {
    if (head == null || del == null) return head;
    if (head == del) head = head.next;
    if (del.next != null) del.next.prev = del.prev;
    if (del.prev != null) del.prev.next = del.next;
    return head;
  }

  // Insert a node at a given position in a DLL
  public DoublyNode insertAtPosition(DoublyNode head, int x, int pos) {
    DoublyNode newNode = new DoublyNode(x);
    if (pos == 1) {
      newNode.next = head;
      if (head != null) head.prev = newNode;
      return newNode;
    }

    DoublyNode curr = head;
    for (int i = 1; i < pos - 1 && curr != null; i++) curr = curr.next;
    if (curr == null) return head;
    newNode.next = curr.next;
    newNode.prev = curr;
    if (curr.next != null) curr.next.prev = newNode;
    curr.next = newNode;
    return head;
  }

  // Reverse a DLL
  public DoublyNode reverseDLL(DoublyNode head) {
    if(head == null || head.next == null) return head;

    DoublyNode prev = null;
    DoublyNode curr = head;

    while (curr != null) {
      DoublyNode nxt = curr.next;
      curr.next = prev;
      curr.prev = nxt;
      prev = curr;
      curr = nxt;
    }

    return prev;
  }
}
