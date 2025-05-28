package com.monal.Stacks_Queues;

/*
Design your implementation of the circular queue. The circular queue is a linear data structure in which the operations are performed based on FIFO (First In First Out) principle, and the last position is connected back to the first position to make a circle. It is also called "Ring Buffer".

One of the benefits of the circular queue is that we can make use of the spaces in front of the queue. In a normal queue, once the queue becomes full, we cannot insert the next element even if there is a space in front of the queue. But using the circular queue, we can use the space to store new values.

Implement the MyCircularQueue class:

MyCircularQueue(k) Initializes the object with the size of the queue to be k.
int Front() Gets the front item from the queue. If the queue is empty, return -1.
int Rear() Gets the last item from the queue. If the queue is empty, return -1.
boolean enQueue(int value) Inserts an element into the circular queue. Return true if the operation is successful.
boolean deQueue() Deletes an element from the circular queue. Return true if the operation is successful.
boolean isEmpty() Checks whether the circular queue is empty or not.
boolean isFull() Checks whether the circular queue is full or not.
You must solve the problem without using the built-in queue data structure in your programming language.



Example 1:

Input
["MyCircularQueue", "enQueue", "enQueue", "enQueue", "enQueue", "Rear", "isFull", "deQueue", "enQueue", "Rear"]
[[3], [1], [2], [3], [4], [], [], [], [4], []]
Output
[null, true, true, true, false, 3, true, true, true, 4]

Explanation
MyCircularQueue myCircularQueue = new MyCircularQueue(3);
myCircularQueue.enQueue(1); // return True
myCircularQueue.enQueue(2); // return True
myCircularQueue.enQueue(3); // return True
myCircularQueue.enQueue(4); // return False
myCircularQueue.Rear();     // return 3
myCircularQueue.isFull();   // return True
myCircularQueue.deQueue();  // return True
myCircularQueue.enQueue(4); // return True
myCircularQueue.Rear();     // return 4
*/
public class P006 {

  class MyCircularQueue {
    final int[] queue; // Array to store the elements of the circular queue
    final int size; // Maximum size of the circular queue
    private int front; // Index of the front element
    private int rear; // Index of the rear element
    private int count; // Current number of elements in the queue

    public MyCircularQueue(int k) {
      // Initialize the circular queue with a given size k
      this.size = k;
      this.queue = new int[k];
      this.front = 0;
      this.rear = -1;
      this.count = 0;
    }

    public boolean enQueue(int value) {
      if (isFull()) {
        return false; // Queue is full, cannot enqueue
      }
      rear = (rear + 1) % size; // Circular increment
      queue[rear] = value; // Insert the value at the rear
      count++; // Increment the count of elements
      return true;

    }

    public boolean deQueue() {

      if (isEmpty()) {
        return false; // Queue is empty, cannot dequeue
      }
      front = (front + 1) % size; // Circular increment
      count--; // Decrement the count of elements
      return true; // Dequeue successful
    }

    public int Front() {
      if (isEmpty()) {
        return -1; // Queue is empty, return -1
      }
      return queue[front]; // Return the front element

    }

    public int Rear() {

      if (isEmpty()) {
        return -1; // Queue is empty, return -1
      }
      return queue[rear]; // Return the rear element
    }

    public boolean isEmpty() {

      return count == 0; // Check if the queue is empty
    }

    public boolean isFull() {
      return count == size; // Check if the queue is full

    }

  }

  public static void main(String[] args) {
    MyCircularQueue myCircularQueue = new P006().new MyCircularQueue(3);
    System.out.println(myCircularQueue.enQueue(1)); // return True
    System.out.println(myCircularQueue.enQueue(2)); // return True
    System.out.println(myCircularQueue.enQueue(3)); // return True
    System.out.println(myCircularQueue.enQueue(4)); // return False
    System.out.println(myCircularQueue.Rear()); // return 3
    System.out.println(myCircularQueue.isFull()); // return True
    System.out.println(myCircularQueue.deQueue()); // return True
    System.out.println(myCircularQueue.enQueue(4)); // return True
    System.out.println(myCircularQueue.Rear()); // return 4
  }
}
