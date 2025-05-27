package com.monal.Stacks_Queues;

import java.util.*;

/**
 * COMPLETE STACKS AND QUEUES TUTORIAL
 * =================================
 *
 * This comprehensive tutorial covers everything you need to know about
 * Stacks and Queues in Java for your DSA journey!
 *
 * Topics Covered:
 * 1. Stack Fundamentals (LIFO - Last In First Out)
 * 2. Queue Fundamentals (FIFO - First In First Out)
 * 3. Implementation using Arrays and LinkedList
 * 4. Built-in Java Collections
 * 5. Common Operations and Edge Cases
 * 6. Real-world Applications
 * 7. Practice Problems and Examples
 * 8. Performance Analysis
 */
public class Basics {

    // ============================================================================
    // STACK IMPLEMENTATION
    // ============================================================================

    /**
     * CUSTOM STACK IMPLEMENTATION USING ARRAY
     * Stack follows LIFO (Last In First Out) principle
     * Think of it like a stack of plates - you add/remove from the top only
     */
    static class ArrayStack<T> {
        final Object[] arr;
        private int top; // Points to the topmost element
        final int capacity;

        // Constructor
        public ArrayStack(int size) {
            this.capacity = size;
            this.arr = new Object[capacity];
            this.top = -1; // -1 indicates empty stack
        }

        // PUSH: Add element to top of stack
        public void push(T item) {
            if (isFull()) {
                throw new RuntimeException("Stack Overflow! Cannot push " + item);
            }
            arr[++top] = item;
            System.out.println("✅ Pushed: " + item + " | Stack size: " + size());
        }

        // POP: Remove and return top element
        @SuppressWarnings("unchecked")
        public T pop() {
            if (isEmpty()) {
                throw new RuntimeException("Stack Underflow! Cannot pop from empty stack");
            }
            T item = (T) arr[top];
            arr[top--] = null; // Help GC and decrement top
            System.out.println("❌ Popped: " + item + " | Stack size: " + size());
            return item;
        }

        // PEEK/TOP: View top element without removing it
        @SuppressWarnings("unchecked")
        public T peek() {
            if (isEmpty()) {
                throw new RuntimeException("Stack is empty! Cannot peek");
            }
            return (T) arr[top];
        }

        // Check if stack is empty
        public boolean isEmpty() {
            return top == -1;
        }

        // Check if stack is full
        public boolean isFull() {
            return top == capacity - 1;
        }

        // Get current size
        public int size() {
            return top + 1;
        }

        // Display stack contents
        public void display() {
            if (isEmpty()) {
                System.out.println("📚 Stack is empty!");
                return;
            }
            System.out.print("📚 Stack (top to bottom): ");
            for (int i = top; i >= 0; i--) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        }
    }

    // ============================================================================
    // QUEUE IMPLEMENTATION
    // ============================================================================

    /**
     * CUSTOM QUEUE IMPLEMENTATION USING ARRAY (CIRCULAR QUEUE)
     * Queue follows FIFO (First In First Out) principle
     * Think of it like a line at a restaurant - first person in line gets served
     * first
     */
    static class ArrayQueue<T> {
        final Object[] arr;
        private int front; // Points to first element
        private int rear; // Points to last element
        private int size; // Current number of elements
        final int capacity; // Maximum capacity

        // Constructor
        public ArrayQueue(int capacity) {
            this.capacity = capacity;
            this.arr = new Object[capacity];
            this.front = 0;
            this.rear = -1;
            this.size = 0;
        }

        // ENQUEUE: Add element to rear of queue
        public void enqueue(T item) {
            if (isFull()) {
                throw new RuntimeException("Queue Overflow! Cannot enqueue " + item);
            }
            rear = (rear + 1) % capacity; // Circular increment
            arr[rear] = item;
            size++;
            System.out.println("➡️ Enqueued: " + item + " | Queue size: " + size);
        }

        // DEQUEUE: Remove and return front element
        @SuppressWarnings("unchecked")
        public T dequeue() {
            if (isEmpty()) {
                throw new RuntimeException("Queue Underflow! Cannot dequeue from empty queue");
            }
            T item = (T) arr[front];
            arr[front] = null; // Help GC
            front = (front + 1) % capacity; // Circular increment
            size--;
            System.out.println("⬅️ Dequeued: " + item + " | Queue size: " + size);
            return item;
        }

        // FRONT: View front element without removing it
        @SuppressWarnings("unchecked")
        public T front() {
            if (isEmpty()) {
                throw new RuntimeException("Queue is empty! Cannot view front");
            }
            return (T) arr[front];
        }

        // REAR: View rear element without removing it
        @SuppressWarnings("unchecked")
        public T rear() {
            if (isEmpty()) {
                throw new RuntimeException("Queue is empty! Cannot view rear");
            }
            return (T) arr[rear];
        }

        // Check if queue is empty
        public boolean isEmpty() {
            return size == 0;
        }

        // Check if queue is full
        public boolean isFull() {
            return size == capacity;
        }

        // Get current size
        public int size() {
            return size;
        }

        // Display queue contents
        public void display() {
            if (isEmpty()) {
                System.out.println("🚶 Queue is empty!");
                return;
            }
            System.out.print("🚶 Queue (front to rear): ");
            for (int i = 0; i < size; i++) {
                int index = (front + i) % capacity;
                System.out.print(arr[index] + " ");
            }
            System.out.println();
        }
    }

    // ============================================================================
    // BUILT-IN JAVA COLLECTIONS
    // ============================================================================

    /**
     * Demonstrates Java's built-in Stack and Queue implementations
     */
    public static void demonstrateBuiltInCollections() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🏗️  BUILT-IN JAVA STACK & QUEUE COLLECTIONS");
        System.out.println("=".repeat(60));

        // 1. JAVA STACK CLASS
        System.out.println("\n📚 Java Stack Class:");
        Stack<String> javaStack = new Stack<>();
        javaStack.push("Bottom");
        javaStack.push("Middle");
        javaStack.push("Top");
        System.out.println("Stack: " + javaStack);
        System.out.println("Peek: " + javaStack.peek());
        System.out.println("Pop: " + javaStack.pop());
        System.out.println("After pop: " + javaStack);

        // 2. DEQUE AS STACK (Recommended approach)
        System.out.println("\n📚 ArrayDeque as Stack (Recommended):");
        Deque<String> stackDeque = new ArrayDeque<>();
        // ITcan be pushed and popped on both ends
        stackDeque.offerFirst("A"); // Same as push()
        stackDeque.offerFirst("B");
        stackDeque.offerFirst("C");
        System.out.println("Deque Stack: " + stackDeque);
        stackDeque.pollFirst(); // Same as pop()
        System.out.println("After poll: " + stackDeque);
        stackDeque.pollFirst(); // GIVES : // B
        System.out.println("After another poll: " + stackDeque);
        stackDeque.pollFirst(); // GIVES : // A
        System.out.println("After another poll: " + stackDeque);
        stackDeque.pollFirst(); // GIVES : // EMPTY
        System.out.println("After another poll: " + stackDeque);

        // Can be polled and offered from Last end as well
        stackDeque.offerLast("X"); // Same as push()
        stackDeque.offerLast("Y");
        stackDeque.offerLast("Z");
        System.out.println("Deque Stack (after offering at last): " + stackDeque);
        stackDeque.pollLast(); // Same as pop()
        System.out.println("After poll from last: " + stackDeque);
        stackDeque.pollLast(); // GIVES : // Y
        System.out.println("After another poll from last: " + stackDeque);
        stackDeque.pollLast(); // GIVES : // X
        System.out.println("After another poll from last: " + stackDeque);
        stackDeque.pollLast(); // GIVES : // EMPTY

        // 3. QUEUE INTERFACE IMPLEMENTATIONS
        System.out.println("\n🚶 Queue Implementations:");

        // LinkedList as Queue
        Queue<Integer> linkedQueue = new LinkedList<>();
        linkedQueue.offer(10); // Same as add()
        linkedQueue.offer(20);
        linkedQueue.offer(30);
        System.out.println("LinkedList Queue: " + linkedQueue);
        System.out.println("Poll: " + linkedQueue.poll()); // Same as remove()

        // ArrayDeque as Queue (Most efficient)
        Queue<Integer> arrayQueue = new ArrayDeque<>();
        arrayQueue.offer(100);
        arrayQueue.offer(200);
        arrayQueue.offer(300);
        System.out.println("ArrayDeque Queue: " + arrayQueue);
        System.out.println("Peek: " + arrayQueue.peek());

        // PriorityQueue (Heap-based)
        Queue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(30);
        priorityQueue.offer(10);
        priorityQueue.offer(20);
        System.out.println("PriorityQueue: " + priorityQueue);
        System.out.println("Poll (min): " + priorityQueue.poll());
    }

    // ============================================================================
    // REAL-WORLD APPLICATIONS
    // ============================================================================

    /**
     * STACK APPLICATIONS
     */

    // 1. Balanced Parentheses Checker
    public static boolean isBalanced(String expression) {
        Stack<Character> stack = new Stack<>();

        for (char ch : expression.toCharArray()) {
            // Push opening brackets
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            }
            // Check closing brackets
            else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty())
                    return false;

                char top = stack.pop();
                if ((ch == ')' && top != '(') ||
                        (ch == ']' && top != '[') ||
                        (ch == '}' && top != '{')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    // 2. Infix to Postfix Conversion
    public static String infixToPostfix(String infix) {
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();

        for (char ch : infix.toCharArray()) {
            if (Character.isLetterOrDigit(ch)) {
                result.append(ch);
            } else if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                stack.pop(); // Remove '('
            } else {
                while (!stack.isEmpty() && precedence(ch) <= precedence(stack.peek())) {
                    result.append(stack.pop());
                }
                stack.push(ch);
            }
        }

        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }

    private static int precedence(char ch) {
        return switch (ch) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }

    // 3. Function Call Stack Simulation
    public static void simulateRecursion(int n) {
        Stack<String> callStack = new Stack<>();
        System.out.println("\n🔄 Simulating Factorial(" + n + ") Call Stack:");

        // Simulate function calls
        for (int i = n; i >= 1; i--) {
            callStack.push("factorial(" + i + ")");
            System.out.println("📞 Calling: factorial(" + i + ") | Stack: " + callStack);
        }

        // Simulate returns
        int result = 1;
        while (!callStack.isEmpty()) {
            String call = callStack.pop();
            int num = Integer.parseInt(call.substring(10, call.length() - 1));
            result *= num;
            System.out.println("🔙 Returning from " + call + " = " + result + " | Stack: " + callStack);
        }
    }

    /**
     * QUEUE APPLICATIONS
     */

    // 1. BFS (Breadth-First Search) Simulation
    public static void simulateBFS() {
        System.out.println("\n🌐 BFS Traversal Simulation:");
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        // Simple graph: A -> B, C; B -> D; C -> E
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("A", Arrays.asList("B", "C"));
        graph.put("B", Arrays.asList("D"));
        graph.put("C", Arrays.asList("E"));
        graph.put("D", Arrays.asList());
        graph.put("E", Arrays.asList());

        queue.offer("A");
        visited.add("A");

        while (!queue.isEmpty()) {
            String node = queue.poll();
            System.out.println("🔍 Visiting: " + node + " | Queue: " + queue);

            for (String neighbor : graph.get(node)) {
                if (!visited.contains(neighbor)) {
                    queue.offer(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }

    // 2. Process Scheduling Simulation
    public static void simulateProcessScheduling() {
        System.out.println("\n⚙️ CPU Process Scheduling (Round Robin):");

        class Process {
            String name;
            int burstTime;

            Process(String name, int burstTime) {
                this.name = name;
                this.burstTime = burstTime;
            }

            @Override
            public String toString() {
                return name + "(" + burstTime + ")";
            }
        }

        Queue<Process> processQueue = new LinkedList<>();
        processQueue.offer(new Process("P1", 8));
        processQueue.offer(new Process("P2", 4));
        processQueue.offer(new Process("P3", 6));

        int timeQuantum = 3;
        int currentTime = 0;

        while (!processQueue.isEmpty()) {
            Process current = processQueue.poll();
            System.out.println("⏰ Time " + currentTime + ": Executing " + current.name);

            if (current.burstTime <= timeQuantum) {
                currentTime += current.burstTime;
                System.out.println("✅ " + current.name + " completed at time " + currentTime);
            } else {
                current.burstTime -= timeQuantum;
                currentTime += timeQuantum;
                processQueue.offer(current);
                System.out.println("⏸️ " + current.name + " preempted, remaining: " + current.burstTime);
            }
            System.out.println("📋 Process Queue: " + processQueue);
        }
    }

    // ============================================================================
    // GAME SECTION
    // ============================================================================

    /**
     * INTERACTIVE STACK GAME
     * Students can play this to understand stack operations better
     */
    public static void playStackGame() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎮 STACK TOWER GAME");
        System.out.println("=".repeat(60));
        System.out.println("Build a tower by stacking blocks! Can you build it correctly?");

        ArrayStack<String> tower = new ArrayStack<>(5);
        String[] blocks = { "🟥 Foundation", "🟨 Floor 1", "🟦 Floor 2", "🟩 Floor 3", "🟪 Roof" };

        System.out.println("\n📦 Available blocks (must be placed in order):");
        for (int i = 0; i < blocks.length; i++) {
            System.out.println((i + 1) + ". " + blocks[i]);
        }

        // Simulate building the tower
        for (String block : blocks) {
            try {
                tower.push(block);
                tower.display();
                System.out.println("Height: " + tower.size() + "/5");
                Thread.sleep(1000); // Pause for effect
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\n🏆 Tower completed! Now let's demolish it safely...");

        // Demolish the tower
        while (!tower.isEmpty()) {
            try {
                tower.pop();
                tower.display();
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("🎉 Game completed! You understand LIFO principle!");
    }

    /**
     * INTERACTIVE QUEUE GAME
     * Restaurant service simulation
     */
    public static void playQueueGame() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🍽️ RESTAURANT SERVICE GAME");
        System.out.println("=".repeat(60));
        System.out.println("Serve customers in the order they arrived!");

        ArrayQueue<String> customerQueue = new ArrayQueue<>(5);
        String[] customers = { "👨 John", "👩 Alice", "👨‍💼 Bob", "👩‍🦳 Mary", "👦 Tommy" };

        // Customers arrive
        System.out.println("\n📍 Customers arriving...");
        for (String customer : customers) {
            try {
                customerQueue.enqueue(customer);
                customerQueue.display();
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\n🍳 Starting service (FIFO order)...");

        // Serve customers
        while (!customerQueue.isEmpty()) {
            try {
                String customer = customerQueue.front();
                System.out.println("🍽️ Serving: " + customer);
                customerQueue.dequeue();
                customerQueue.display();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("🎉 All customers served! You understand FIFO principle!");
    }

    // ============================================================================
    // PERFORMANCE COMPARISON
    // ============================================================================

    public static void performanceAnalysis() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("⚡ PERFORMANCE ANALYSIS");
        System.out.println("=".repeat(60));

        System.out.println("\n📊 Time Complexity Comparison:");
        System.out.println("┌─────────────────┬──────────┬──────────┬──────────┐");
        System.out.println("│ Operation       │ Stack    │ Queue    │ Note     │");
        System.out.println("├─────────────────┼──────────┼──────────┼──────────┤");
        System.out.println("│ Push/Enqueue    │ O(1)     │ O(1)     │ Constant │");
        System.out.println("│ Pop/Dequeue     │ O(1)     │ O(1)     │ Constant │");
        System.out.println("│ Peek/Front      │ O(1)     │ O(1)     │ Constant │");
        System.out.println("│ Search          │ O(n)     │ O(n)     │ Linear   │");
        System.out.println("│ Size/IsEmpty    │ O(1)     │ O(1)     │ Constant │");
        System.out.println("└─────────────────┴──────────┴──────────┴──────────┘");

        System.out.println("\n💾 Space Complexity:");
        System.out.println("• Array Implementation: O(n) - Fixed size");
        System.out.println("• LinkedList Implementation: O(n) - Dynamic size");

        System.out.println("\n🏆 Best Implementations in Java:");
        System.out.println("• Stack: ArrayDeque (avoid legacy Stack class)");
        System.out.println("• Queue: ArrayDeque (most efficient for general use)");
        System.out.println("• Priority Queue: PriorityQueue (heap-based)");
    }

    // ============================================================================
    // MAIN METHOD
    // ============================================================================

    public static void main(String[] args) {
        System.out.println("🎓 WELCOME TO STACKS & QUEUES MASTERCLASS!");
        System.out.println("==========================================");

        try {
            // 1. Custom Stack Demo
            System.out.println("\n" + "=".repeat(60));
            System.out.println("📚 CUSTOM STACK DEMONSTRATION");
            System.out.println("=".repeat(60));

            ArrayStack<String> stack = new ArrayStack<>(3);
            stack.display();
            stack.push("First");
            stack.push("Second");
            stack.push("Third");
            stack.display();

            System.out.println("👁️ Peek: " + stack.peek());
            stack.pop();
            stack.pop();
            stack.display();

            // 2. Custom Queue Demo
            System.out.println("\n" + "=".repeat(60));
            System.out.println("🚶 CUSTOM QUEUE DEMONSTRATION");
            System.out.println("=".repeat(60));

            ArrayQueue<Integer> queue = new ArrayQueue<>(4);
            queue.display();
            queue.enqueue(10);
            queue.enqueue(20);
            queue.enqueue(30);
            queue.display();

            System.out.println("👁️ Front: " + queue.front());
            System.out.println("👁️ Rear: " + queue.rear());
            queue.dequeue();
            queue.enqueue(40);
            queue.display();

            // 3. Built-in Collections
            demonstrateBuiltInCollections();

            // 4. Real-world Applications
            System.out.println("\n" + "=".repeat(60));
            System.out.println("🌍 REAL-WORLD APPLICATIONS");
            System.out.println("=".repeat(60));

            // Stack Applications
            System.out.println("\n📚 STACK APPLICATIONS:");

            System.out.println("\n1️⃣ Balanced Parentheses:");
            String expr1 = "{[()]}";
            String expr2 = "{[(])}";
            System.out.println(expr1 + " is " + (isBalanced(expr1) ? "✅ Balanced" : "❌ Not Balanced"));
            System.out.println(expr2 + " is " + (isBalanced(expr2) ? "✅ Balanced" : "❌ Not Balanced"));

            System.out.println("\n2️⃣ Infix to Postfix:");
            String infix = "a+b*c";
            System.out.println("Infix: " + infix);
            System.out.println("Postfix: " + infixToPostfix(infix));

            simulateRecursion(4);

            // Queue Applications
            System.out.println("\n🚶 QUEUE APPLICATIONS:");
            simulateBFS();
            simulateProcessScheduling();

            // 5. Interactive Games
            playStackGame();
            playQueueGame();

            // 6. Performance Analysis
            performanceAnalysis();

            // 7. Final Tips
            System.out.println("\n" + "=".repeat(60));
            System.out.println("💡 KEY TAKEAWAYS FOR DSA INTERVIEWS");
            System.out.println("=".repeat(60));
            System.out.println("✅ Stack: Use for LIFO operations, recursion, expression evaluation");
            System.out.println("✅ Queue: Use for FIFO operations, BFS, process scheduling");
            System.out.println("✅ Always check for empty/full conditions before operations");
            System.out.println("✅ Use ArrayDeque for both Stack and Queue in Java");
            System.out.println("✅ Know when to use each: DFS→Stack, BFS→Queue");
            System.out.println("✅ Practice: Balanced parentheses, next greater element, sliding window");

            System.out.println("\n🎉 Congratulations! You've mastered Stacks and Queues!");

        } catch (Exception e) {
            System.err.println("❌ Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}