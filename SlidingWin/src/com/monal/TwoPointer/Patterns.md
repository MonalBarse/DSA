# Fast-Slow Pointer Pattern

### **Category 1: Different Speed Movement**

```java
// Classic: Fast moves 2, slow moves 1
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
```

**Applications:**
- Cycle detection in linked list
- Finding middle of linked list
- Finding cycle start point
- Palindrome linked list check

### **Category 2: Conditional Movement**

```java
// Fast moves always, slow moves conditionally
while (fast < arr.length) {
    if (condition) {
        arr[slow] = arr[fast];
        slow++;
    }
    fast++;
}
```

**Applications:**
- **Remove duplicates from sorted array**
- **Move zeros to end**
- **Remove specific elements**
- **Partition array**

### **Category 3: Gap-Based Movement**

```java
// Maintain fixed gap between pointers
while (fast < arr.length) {
    if (fast - slow >= k) {
        // Process window of size k
        slow++;
    }
    fast++;
}
```

**Applications:**
- Remove nth node from end
- Find kth element from end
- Window-based problems

### **Category 4: Meeting Point Problems**

```java
// Both move at same speed until they meet
while (pointer1 != pointer2) {
    pointer1 = pointer1.next != null ? pointer1.next : headB;
    pointer2 = pointer2.next != null ? pointer2.next : headA;
}
```

**Applications:**
- Intersection of two linked lists
- Finding common elements

## **All Applications Categorized:**

### **Linked List Problems:**
1. **Cycle Detection** - Floyd's algorithm
2. **Find Middle** - slow at middle when fast at end
3. **Cycle Start** - mathematical property
4. **Palindrome Check** - find middle + reverse
5. **Remove Nth from End** - gap of n
6. **Intersection Point** - path switching technique

### **Array Problems:**
7. **Remove Duplicates** - slow tracks unique position
8. **Move Zeros to End** - slow tracks non-zero position
9. **Remove Element** - slow tracks valid elements
10. **Partition Array** - slow tracks partition boundary
11. **Segregate Even/Odd** - slow tracks even position
12. **Dutch National Flag** - multiple slow pointers

### **String Problems:**
13. **Remove Duplicates from String**
14. **Valid Palindrome** - ignore non-alphanumeric
15. **Compress String** - slow writes, fast reads

### **Advanced Applications:**
16. **Tortoise and Hare in Arrays** - find duplicate number
17. **Happy Number** - cycle detection in number sequence
18. **Rearrange Array** - in-place modifications

## **Core Templates:**

### **Template 1: In-Place Modification**
```java
public static int twoPointerInPlace(int[] arr, Condition condition) {
    int slow = 0;
    for (int fast = 0; fast < arr.length; fast++) {
        if (condition.test(arr[fast])) {
            arr[slow] = arr[fast];
            slow++;
        }
    }
    return slow; // new length
}
```

### **Template 2: Linked List Cycle**
```java
public static boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}
```

### **Template 3: Gap Maintenance**
```java
public static ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode slow = head, fast = head;
    // Create gap of n
    for (int i = 0; i < n; i++) {
        fast = fast.next;
    }
    // Move both until fast reaches end
    while (fast.next != null) {
        slow = slow.next;
        fast = fast.next;
    }
    slow.next = slow.next.next;
    return head;
}
```

## **When to Use Each:**
- **Different speeds**: When you need to find middle, detect cycles, or mathematical relationships
- **Conditional movement**: When you need in-place array modifications
- **Gap-based**: When you need to maintain distance between elements
- **Meeting point**: When you need to find intersections or common points
