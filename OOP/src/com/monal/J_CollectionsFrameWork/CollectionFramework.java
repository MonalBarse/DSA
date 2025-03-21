package com.monal.J_CollectionsFrameWork;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
    Here are the main collection frameworks in Java:

    1. List
        - ArrayList
        - LinkedList
        - Vector
        - Stack

    2. Set
        - HashSet
        - LinkedHashSet
        - TreeSet

    3. Queue
        - PriorityQueue
        - ArrayDeque
        - LinkedList (also implements Queue)

    4. Map (technically not part of Collection interface, but part of Collections Framework)
        - HashMap
        - LinkedHashMap
        - TreeMap
        - Hashtable
        - Properties

    5. Legacy collections (older implementations)
        - Vector
        - Stack
        - Hashtable
        - Properties
        - Dictionary
        - Enumeration (interface)

    These collections are organized under the java.util package and follow a hierarchy with interfaces and implementations.
    Most of these collections also have concurrent versions in the java.util.concurrent package like ConcurrentHashMap, CopyOnWriteArrayList, etc.
    _______________________________________________________________________________________________________________________________

    In addition to the core collection interfaces we have covered the following topics in this tutorial:

    a. Collections Utility Class - It is a utility class that provides static methods for common collection operations like sorting
        and searching. It also provides methods to create immutable collections, synchronized collections, etc.

    b. Concurrent Collections - These are thread-safe collections that are designed to be used in a multi-threaded environment.
        Examples include ConcurrentHashMap, CopyOnWriteArrayList, etc.

    c. Streams with Collections - Java 8 introduced the Stream API that allows us to perform functional-style operations on collections.
        We can use streams to filter, map, reduce, and perform other operations on collections.

    d. Collections with Custom Objects - We have demonstrated how to use custom objects with collections. We have used a custom class
        called Person and demonstrated how to sort, search, and perform other operations on collections of custom objects.
 */

@SuppressWarnings("unused")
public class CollectionFramework {
    public static void main(String[] args) {
        System.out.println("\n========== COMPREHENSIVE JAVA COLLECTIONS FRAMEWORK  ==========\n");

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            displayMenu();
            System.out.print("Enter choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        demonstrateListInterface();
                        break;
                    case 2:
                        demonstrateSetInterface();
                        break;
                    case 3:
                        demonstrateQueueAndDequeInterfaces();
                        break;
                    case 4:
                        demonstrateMapInterface();
                        break;
                    case 5:
                        demonstrateUtilityClasses();
                        break;
                    case 6:
                        demonstrateConcurrentCollections();
                        break;
                    case 7:
                        demonstrateStreamsWithCollections();
                        break;
                    case 8:
                        demonstrateCustomObjects();
                        break;
                    case 9:
                        exit = true;
                        System.out.println("Exiting application. Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear the scanner buffer
            }

            if (!exit) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n===== JAVA COLLECTIONS FRAMEWORK MENU =====");
        System.out.println("1. List Interface & Implementations");
        System.out.println("2. Set Interface & Implementations");
        System.out.println("3. Queue & Deque Interfaces");
        System.out.println("4. Map Interface & Implementations");
        System.out.println("5. Collections Utility Class");
        System.out.println("6. Concurrent Collections");
        System.out.println("7. Streams with Collections");
        System.out.println("8. Collections with Custom Objects");
        System.out.println("9. Exit");
    }

    // Simple enum for day of week for EnumSet/EnumMap examples
    private enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    /*
     * SECTION 1: LIST INTERFACE
     * Demonstrates the List interface and its implementations
     * including ArrayList, LinkedList, and Vector
     */

    private static void demonstrateListInterface() {
        System.out.println("\n===== 1. LIST INTERFACE & IMPLEMENTATIONS =====");
        System.out.println("[INFO] List is an ordered collection that allows duplicates");

        // ArrayList demonstration
        System.out.println("\n[INFO] ArrayList - Dynamic array implementation:");
        List<String> arrayList = new ArrayList<>();

        System.out.println("Adding elements to ArrayList...");
        arrayList.add("Java");
        arrayList.add("Python");
        arrayList.add("JavaScript");
        arrayList.add("Python"); // Duplicates allowed

        System.out.println("ArrayList content: " + arrayList);
        System.out.println("ArrayList size: " + arrayList.size());
        System.out.println("Element at index 1: " + arrayList.get(1));
        System.out.println("Index of 'Python' (first occurrence): " + arrayList.indexOf("Python"));
        System.out.println("Last index of 'Python': " + arrayList.lastIndexOf("Python"));

        System.out.println("\nIterating through ArrayList with enhanced for loop:");
        for (String language : arrayList) {
            System.out.println("  - " + language);
        }

        System.out.println("\nIterating through ArrayList with Iterator:");
        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            System.out.println("  - " + iterator.next());
        }

        System.out.println("\nModifying ArrayList...");
        arrayList.set(1, "Kotlin"); // Replace element at index 1
        arrayList.remove("JavaScript"); // Remove by object
        arrayList.remove(0); // Remove by index
        System.out.println("After modifications: " + arrayList);

        // LinkedList demonstration
        System.out.println("\n[INFO] LinkedList - Doubly-linked list implementation:");
        LinkedList<String> linkedList = new LinkedList<>();

        System.out.println("Adding elements to LinkedList...");
        linkedList.add("Apple");
        linkedList.add("Banana");
        linkedList.add("Cherry");

        // LinkedList specific methods
        linkedList.addFirst("Avocado"); // Add at the beginning
        linkedList.addLast("Durian"); // Add at the end
        System.out.println("LinkedList content: " + linkedList);

        System.out.println("First element: " + linkedList.getFirst());
        System.out.println("Last element: " + linkedList.getLast());

        linkedList.removeFirst();
        linkedList.removeLast();
        System.out.println("After removing first and last: " + linkedList);

        // Vector demonstration
        System.out.println("\n[INFO] Vector - Thread-safe dynamic array (legacy):");
        Vector<Integer> vector = new Vector<>();

        System.out.println("Adding elements to Vector...");
        vector.add(10);
        vector.add(20);
        vector.add(30);

        System.out.println("Vector content: " + vector);
        System.out.println("Vector capacity: " + vector.capacity());
        System.out.println("Vector size: " + vector.size());

        // Stack demonstration (extends Vector)
        System.out.println("\n[INFO] Stack - LIFO (Last-In-First-Out) data structure (legacy):");
        Stack<String> stack = new Stack<>();

        System.out.println("Pushing elements onto Stack...");
        stack.push("Bottom");
        stack.push("Middle");
        stack.push("Top");

        System.out.println("Stack content: " + stack);
        System.out.println("Peek (view top element): " + stack.peek());
        System.out.println("Pop (remove and return top): " + stack.pop());
        System.out.println("Stack after pop: " + stack);

        // Performance and use cases
        System.out.println("\n[INFO] List Implementation Comparison:");
        System.out.println("ArrayList:");
        System.out.println("  - Fast random access (get/set by index)");
        System.out.println("  - Efficient iteration");
        System.out.println("  - Inefficient insertion/deletion in the middle");
        System.out.println("  - Good for most use cases where random access is common");

        System.out.println("LinkedList:");
        System.out.println("  - Efficient insertion/deletion anywhere in the list");
        System.out.println("  - Less efficient random access");
        System.out.println("  - More memory overhead per element");
        System.out.println("  - Good for frequent insertions/deletions, especially at ends");

        System.out.println("Vector/Stack:");
        System.out.println("  - Thread-safe (synchronized methods)");
        System.out.println("  - Less efficient due to synchronization overhead");
        System.out.println("  - Generally replaced by ArrayList and more modern alternatives");
        System.out.println("  - Use Stack only when LIFO behavior specifically needed");
    }

    /**
     * SECTION 2: SET INTERFACE
     * Demonstrates the Set interface and its implementations
     * including HashSet, LinkedHashSet, and TreeSet
     */
    private static void demonstrateSetInterface() {
        System.out.println("\n===== 2. SET INTERFACE & IMPLEMENTATIONS =====");
        System.out.println("[INFO] Set is a collection that contains no duplicate elements");

        // HashSet demonstration
        System.out.println("\n[INFO] HashSet - Hash table implementation, no guaranteed order:");
        Set<String> hashSet = new HashSet<>();

        System.out.println("Adding elements to HashSet...");
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Cherry");
        hashSet.add("Apple"); // Duplicate - will not be added

        System.out.println("HashSet content: " + hashSet);
        System.out.println("HashSet size: " + hashSet.size());
        System.out.println("Contains 'Banana'? " + hashSet.contains("Banana"));

        System.out.println("\nIterating through HashSet:");
        for (String fruit : hashSet) {
            System.out.println("  - " + fruit);
        }

        hashSet.remove("Banana");
        System.out.println("After removing 'Banana': " + hashSet);

        // LinkedHashSet demonstration
        System.out.println("\n[INFO] LinkedHashSet - Maintains insertion order:");
        Set<String> linkedHashSet = new LinkedHashSet<>();

        System.out.println("Adding elements to LinkedHashSet...");
        linkedHashSet.add("Red");
        linkedHashSet.add("Green");
        linkedHashSet.add("Blue");
        linkedHashSet.add("Yellow");

        System.out.println("LinkedHashSet content: " + linkedHashSet);
        System.out.println("Elements appear in insertion order");

        // TreeSet demonstration
        System.out.println("\n[INFO] TreeSet - Sorted set based on a tree structure:");
        TreeSet<Integer> treeSet = new TreeSet<>();

        System.out.println("Adding elements to TreeSet...");
        treeSet.add(5);
        treeSet.add(1);
        treeSet.add(10);
        treeSet.add(3);
        treeSet.add(7);

        System.out.println("TreeSet content: " + treeSet);
        System.out.println("Elements are automatically sorted");

        // TreeSet navigation methods
        System.out.println("\nTreeSet navigation methods:");
        System.out.println("First element: " + treeSet.first());
        System.out.println("Last element: " + treeSet.last());
        System.out.println("Element lower than 5: " + treeSet.lower(5)); // Greatest element < 5
        System.out.println("Element higher than 5: " + treeSet.higher(5)); // Smallest element > 5
        System.out.println("Element floor of 4: " + treeSet.floor(4)); // Greatest element <= 4
        System.out.println("Element ceiling of 4: " + treeSet.ceiling(4)); // Smallest element >= 4

        System.out.println("\nSubSet view from 3 to 7 (inclusive): " + treeSet.subSet(3, true, 7, true));
        System.out.println("HeadSet view (elements < 5): " + treeSet.headSet(5, false));
        System.out.println("TailSet view (elements >= 5): " + treeSet.tailSet(5, true));

        // EnumSet demonstration
        System.out.println("\n[INFO] EnumSet - Specialized Set for enum types:");
        EnumSet<DayOfWeek> weekdays = EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.FRIDAY);
        EnumSet<DayOfWeek> weekend = EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

        System.out.println("Weekdays: " + weekdays);
        System.out.println("Weekend: " + weekend);

        EnumSet<DayOfWeek> allDays = EnumSet.allOf(DayOfWeek.class);
        System.out.println("All days: " + allDays);

        // Set operations
        System.out.println("\n[INFO] Set operations demonstration:");
        Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        Set<Integer> set2 = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));

        System.out.println("Set1: " + set1);
        System.out.println("Set2: " + set2);

        // Union
        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println("Union: " + union);

        // Intersection
        Set<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("Intersection: " + intersection);

        // Difference (set1 - set2)
        Set<Integer> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        System.out.println("Difference (set1 - set2): " + difference);

        // Symmetric difference (elements in either set, but not in both)
        Set<Integer> symmetricDifference = new HashSet<>(set1);
        symmetricDifference.addAll(set2);

        Set<Integer> tempIntersection = new HashSet<>(set1);
        tempIntersection.retainAll(set2);

        symmetricDifference.removeAll(tempIntersection);
        System.out.println("Symmetric Difference: " + symmetricDifference);

        // Performance and use cases
        System.out.println("\n[INFO] Set Implementation Comparison:");
        System.out.println("HashSet:");
        System.out.println("  - Fastest performance for add/remove/contains operations (O(1))");
        System.out.println("  - No guaranteed element order");
        System.out.println("  - Best for most use cases when order doesn't matter");

        System.out.println("LinkedHashSet:");
        System.out.println("  - Slightly slower than HashSet");
        System.out.println("  - Maintains insertion order");
        System.out.println("  - Use when iteration order matters");

        System.out.println("TreeSet:");
        System.out.println("  - Slower performance (O(log n))");
        System.out.println("  - Elements stored in sorted order");
        System.out.println("  - Provides range view operations");
        System.out.println("  - Use when sorting or range operations needed");

        System.out.println("EnumSet:");
        System.out.println("  - Extremely memory-efficient for enum types");
        System.out.println("  - Very fast bit vector implementation");
        System.out.println("  - Always use for enum-based sets");
    }

    /**
     * SECTION 3: QUEUE AND DEQUE INTERFACES
     * Demonstrates the Queue and Deque interfaces and their implementations
     */
    private static void demonstrateQueueAndDequeInterfaces() {
        System.out.println("\n===== 3. QUEUE & DEQUE INTERFACES =====");
        System.out.println("[INFO] Queue represents a collection for holding elements prior to processing");
        System.out.println("[INFO] Deque (double-ended queue) supports insertion and removal at both ends");

        // Queue interface with LinkedList implementation
        System.out.println("\n[INFO] Queue implementation with LinkedList:");
        Queue<String> queue = new LinkedList<>();

        System.out.println("Adding elements to Queue...");
        queue.offer("First"); // Preferred method to add elements (vs. add())
        queue.offer("Second");
        queue.offer("Third");

        System.out.println("Queue content: " + queue);
        System.out.println("Peek (view head element): " + queue.peek());

        System.out.println("\nRemoving elements from Queue...");
        System.out.println("Poll (remove and return head): " + queue.poll());
        System.out.println("Queue after poll: " + queue);

        // PriorityQueue demonstration
        System.out.println("\n[INFO] PriorityQueue - Elements processed according to priority:");
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        System.out.println("Adding elements to PriorityQueue...");
        priorityQueue.offer(10);
        priorityQueue.offer(5);
        priorityQueue.offer(20);
        priorityQueue.offer(1);

        System.out.println("PriorityQueue content: " + priorityQueue);
        System.out.println("Note: toString() doesn't guarantee to show the queue in priority order");

        System.out.println("\nRemoving elements from PriorityQueue...");
        while (!priorityQueue.isEmpty()) {
            System.out.println("Polling element: " + priorityQueue.poll());
        }

        // Custom priority with Comparator
        System.out.println("\n[INFO] PriorityQueue with custom comparator (descending order):");
        PriorityQueue<Integer> descendingPQ = new PriorityQueue<>(Comparator.reverseOrder());

        descendingPQ.addAll(Arrays.asList(10, 5, 20, 1));
        System.out.println("Removing elements from descending PriorityQueue...");
        while (!descendingPQ.isEmpty()) {
            System.out.println("Polling element: " + descendingPQ.poll());
        }

        // Deque interface with ArrayDeque implementation
        System.out.println("\n[INFO] Deque implementation with ArrayDeque:");
        Deque<String> deque = new ArrayDeque<>();

        System.out.println("Adding elements to Deque...");
        deque.offerFirst("First"); // Add to front
        deque.offerLast("Last"); // Add to end
        deque.offerFirst("Very First");
        deque.offerLast("Very Last");

        System.out.println("Deque content: " + deque);
        System.out.println("First element: " + deque.peekFirst());
        System.out.println("Last element: " + deque.peekLast());

        System.out.println("\nRemoving elements from Deque...");
        System.out.println("Poll first: " + deque.pollFirst());
        System.out.println("Poll last: " + deque.pollLast());
        System.out.println("Deque after polling: " + deque);

        // Using Deque as a Stack
        System.out.println("\n[INFO] Using ArrayDeque as a Stack (preferred over Stack class):");
        Deque<Integer> stack = new ArrayDeque<>();

        System.out.println("Pushing elements to stack...");
        stack.push(1); // push adds to front (same as offerFirst)
        stack.push(2);
        stack.push(3);

        System.out.println("Stack content: " + stack);
        System.out.println("Peek top element: " + stack.peek());

        System.out.println("\nPopping elements from stack...");
        while (!stack.isEmpty()) {
            System.out.println("Pop: " + stack.pop()); // pop removes from front
        }

        // BlockingQueue interface introduction
        System.out.println("\n[INFO] BlockingQueue interface overview (from java.util.concurrent):");
        System.out.println("  - Used for producer-consumer patterns");
        System.out.println("  - Blocks or times out when operations can't be satisfied immediately");
        System.out.println("  - Implementations: ArrayBlockingQueue, LinkedBlockingQueue, PriorityBlockingQueue");
        System.out.println("  - Methods: put(), take(), offer(E, time, unit), poll(time, unit)");

        // Performance and use cases
        System.out.println("\n[INFO] Queue/Deque Implementation Comparison:");
        System.out.println("LinkedList (as Queue/Deque):");
        System.out.println("  - Doubly-linked list implementation");
        System.out.println("  - Good general-purpose implementation");
        System.out.println("  - More memory overhead");

        System.out.println("ArrayDeque:");
        System.out.println("  - Resizable array implementation");
        System.out.println("  - More memory-efficient than LinkedList");
        System.out.println("  - Faster than Stack for stack operations");
        System.out.println("  - No capacity restrictions (resizes as needed)");
        System.out.println("  - Not thread-safe");

        System.out.println("PriorityQueue:");
        System.out.println("  - Based on priority heap");
        System.out.println("  - O(log n) time for insertion and removal");
        System.out.println("  - O(1) time to retrieve the highest-priority element");
        System.out.println("  - Use when processing order matters");
    }

    /**
     * SECTION 4: MAP INTERFACE
     * Demonstrates the Map interface and its implementations
     * including HashMap, LinkedHashMap, TreeMap, and others
     */
    private static void demonstrateMapInterface() {
        System.out.println("\n===== 4. MAP INTERFACE & IMPLEMENTATIONS =====");
        System.out.println("[INFO] Map maps keys to values, without duplicate keys");

        // HashMap demonstration
        System.out.println("\n[INFO] HashMap - Hash table implementation, no guaranteed order:");
        Map<String, Integer> hashMap = new HashMap<>();

        System.out.println("Adding key-value pairs to HashMap...");
        hashMap.put("One", 1);
        hashMap.put("Two", 2);
        hashMap.put("Three", 3);
        hashMap.put("One", 11); // Replaces previous value for key "One"

        System.out.println("HashMap content: " + hashMap);
        System.out.println("Size: " + hashMap.size());
        System.out.println("Value for key 'Two': " + hashMap.get("Two"));
        System.out.println("Contains key 'Four'? " + hashMap.containsKey("Four"));
        System.out.println("Contains value 3? " + hashMap.containsValue(3));

        // Default value with getOrDefault
        System.out.println("Value for key 'Four' (getOrDefault): " + hashMap.getOrDefault("Four", 0));

        // Iterating through HashMap
        System.out.println("\nIterating through HashMap:");

        System.out.println("Using keySet():");
        for (String key : hashMap.keySet()) {
            System.out.println("  Key: " + key + ", Value: " + hashMap.get(key));
        }

        System.out.println("\nUsing entrySet():");
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println("  " + entry.getKey() + " = " + entry.getValue());
        }

        System.out.println("\nUsing forEach() (Java 8+):");
        hashMap.forEach((key, value) -> System.out.println("  " + key + " => " + value));

        // LinkedHashMap demonstration
        System.out.println("\n[INFO] LinkedHashMap - Maintains key insertion order:");
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();

        System.out.println("Adding elements to LinkedHashMap...");
        linkedHashMap.put("CPU", "Intel Core i7");
        linkedHashMap.put("RAM", "16GB DDR4");
        linkedHashMap.put("Storage", "512GB SSD");
        linkedHashMap.put("GPU", "NVIDIA RTX 3080");

        System.out.println("LinkedHashMap contents (maintains insertion order):");
        linkedHashMap.forEach((key, value) -> System.out.println("  " + key + ": " + value));

        // LRU cache with LinkedHashMap
        System.out.println("\n[INFO] LinkedHashMap as LRU Cache (access-order):");
        LinkedHashMap<Integer, String> lruCache = new LinkedHashMap<>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                return size() > 3; // Max cache size of 3
            }
        };

        System.out.println("Adding entries to LRU cache...");
        lruCache.put(1, "One");
        lruCache.put(2, "Two");
        lruCache.put(3, "Three");
        System.out.println("Initial cache: " + lruCache);

        // Access an entry (moves to end in access order)
        System.out.println("Accessing entry with key 1: " + lruCache.get(1));
        System.out.println("Cache after access: " + lruCache);

        // Add new entry, should evict eldest by access time (key 2)
        lruCache.put(4, "Four");
        System.out.println("Cache after adding new entry: " + lruCache);

        // TreeMap demonstration
        System.out.println("\n[INFO] TreeMap - Keys sorted in natural order:");
        TreeMap<Integer, String> treeMap = new TreeMap<>();

        System.out.println("Adding elements to TreeMap...");
        treeMap.put(5, "Five");
        treeMap.put(3, "Three");
        treeMap.put(1, "One");
        treeMap.put(4, "Four");
        treeMap.put(2, "Two");

        System.out.println("TreeMap content (keys are sorted): " + treeMap);

        // TreeMap navigation methods
        System.out.println("\nTreeMap navigation methods:");
        System.out.println("First key: " + treeMap.firstKey());
        System.out.println("Last key: " + treeMap.lastKey());
        System.out.println("Lower key than 3: " + treeMap.lowerKey(3));
        System.out.println("Higher key than 3: " + treeMap.higherKey(3));
        System.out.println("Floor key of 2.5: " + treeMap.floorKey((int) 2.5));
        System.out.println("Ceiling key of 2.5: " + treeMap.ceilingKey((int) 2.5));

        System.out.println("\nSubMap from 2 to 4 (inclusive): " + treeMap.subMap(2, true, 4, true));
        System.out.println("HeadMap up to 3 (inclusive): " + treeMap.headMap(3, true));
        System.out.println("TailMap from 3 (inclusive): " + treeMap.tailMap(3, true));

        // EnumMap demonstration
        System.out.println("\n[INFO] EnumMap - Specialized Map for enum keys:");
        EnumMap<DayOfWeek, String> scheduleMap = new EnumMap<>(DayOfWeek.class);

        scheduleMap.put(DayOfWeek.MONDAY, "Work");
        scheduleMap.put(DayOfWeek.TUESDAY, "Gym");
        scheduleMap.put(DayOfWeek.WEDNESDAY, "Meeting");
        scheduleMap.put(DayOfWeek.THURSDAY, "Work from home");
        scheduleMap.put(DayOfWeek.FRIDAY, "Social event");

        System.out.println("EnumMap content:");
        scheduleMap.forEach((day, activity) -> System.out.println("  " + day + ": " + activity));

        // WeakHashMap introduction
        System.out.println("\n[INFO] WeakHashMap - Map with weak references as keys:");
        System.out.println("  - Keys held by weak references");
        System.out.println("  - Entries removed when keys no longer referenced");
        System.out.println("  - Useful for implementing memory-sensitive caches");

        // IdentityHashMap introduction
        System.out.println("\n[INFO] IdentityHashMap introduction:");
        System.out.println("  - Uses reference equality (==) instead of object equality (equals())");
        System.out.println("  - Useful for topology-preserving object graph transformations");

        // Hashtable introduction (legacy)
        System.out.println("\n[INFO] Hashtable - Thread-safe legacy implementation:");
        System.out.println("  - Thread-safe (synchronized methods)");
        System.out.println("  - Does not allow null keys or values");
        System.out.println("  - Largely replaced by ConcurrentHashMap for thread-safety");

        // Performance and use cases
        System.out.println("\n[INFO] Map Implementation Comparison:");
        System.out.println("HashMap:");
        System.out.println("  - Fast for most operations: O(1) average case");
        System.out.println("  - No guaranteed order");
        System.out.println("  - Allows one null key and multiple null values");
        System.out.println("  - Best for general-purpose use");

        System.out.println("LinkedHashMap:");
        System.out.println("  - Slightly slower than HashMap");
        System.out.println("  - Maintains insertion order (or access order if specified)");
        System.out.println("  - Good for ordered iterations and LRU caches");

        System.out.println("TreeMap:");
        System.out.println("  - Slower: O(log n) for most operations");
        System.out.println("  - Keys stored in sorted order");
        System.out.println("  - Provides range-view methods");
        System.out.println("  - Use when sorting or range operations needed");

        System.out.println("EnumMap:");
        System.out.println("  - Very efficient for enum keys");
        System.out.println("  - Array-based implementation");
        System.out.println("  - Always use for enum-based maps");
    }

    /**
     * SECTION 5: COLLECTIONS UTILITY CLASS
     * Demonstrates static methods in Collections class for various operations
     */
    private static void demonstrateUtilityClasses() {
        System.out.println("\n===== 5. COLLECTIONS UTILITY CLASS =====");
        System.out.println("[INFO] Collections class provides static methods for collection operations");

        // Creating a sample list
        List<Integer> numbers = new ArrayList<>(Arrays.asList(4, 1, 8, 3, 6, 2, 9, 5, 7));
        System.out.println("Original list: " + numbers);

        // Sorting
        System.out.println("\n[INFO] Sorting operations:");
        Collections.sort(numbers);
        System.out.println("After Collections.sort(): " + numbers);

        Collections.reverse(numbers);
        System.out.println("After Collections.reverse(): " + numbers);

        Collections.shuffle(numbers);
        System.out.println("After Collections.shuffle(): " + numbers);

        // Custom sorting with Comparator
        List<String> fruits = Arrays.asList("apple", "Banana", "cherry", "Date", "elderberry");
        System.out.println("\nOriginal fruits list: " + fruits);

        // Case-insensitive sorting
        Collections.sort(fruits, String.CASE_INSENSITIVE_ORDER);
        System.out.println("After case-insensitive sort: " + fruits);

        // Reverse ordering
        Collections.sort(fruits, Collections.reverseOrder());
        System.out.println("After reverse order sort: " + fruits);

        // Binary search
        System.out.println("\n[INFO] Binary search operations:");
        List<Integer> sortedNumbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        System.out.println("Sorted list: " + sortedNumbers);

        int index = Collections.binarySearch(sortedNumbers, 6);
        System.out.println("Index of 6 using binarySearch: " + index);

        index = Collections.binarySearch(sortedNumbers, 10);
        System.out.println("Index of 10 (not in list): " + index); // Returns -(insertion point) - 1

        // Min and Max
        System.out.println("\n[INFO] Min and Max operations:");
        System.out.println("Min value: " + Collections.min(numbers));
        System.out.println("Max value: " + Collections.max(numbers));

        // Custom min/max with Comparator (by string length)
        Comparator<String> lengthComparator = Comparator.comparingInt(String::length);
        System.out.println("Shortest string (by length): " + Collections.min(fruits, lengthComparator));
        System.out.println("Longest string (by length): " + Collections.max(fruits, lengthComparator));

        // Frequency and disjoint
        System.out.println("\n[INFO] Frequency and disjoint operations:");
        List<String> letters = Arrays.asList("a", "b", "c", "a", "b", "a");
        System.out.println("Letters list: " + letters);
        System.out.println("Frequency of 'a': " + Collections.frequency(letters, "a"));

        List<String> set1 = Arrays.asList("a", "b", "c");
        List<String> set2 = Arrays.asList("d", "e", "f");
        System.out.println("set1: " + set1 + ", set2: " + set2);
        System.out.println("Are set1 and set2 disjoint? " + Collections.disjoint(set1, set2));

        // Fill and copy
        System.out.println("\n[INFO] Fill and copy operations:");
        List<String> filledList = new ArrayList<>(Arrays.asList("one", "two", "three", "four", "five"));
        System.out.println("Before fill: " + filledList);
        Collections.fill(filledList, "filled");
        System.out.println("After fill: " + filledList);

        List<Integer> source = Arrays.asList(1, 2, 3);
        List<Integer> dest = Arrays.asList(0, 0, 0, 0, 0);
        System.out.println("Source: " + source + ", Destination before copy: " + dest);
        Collections.copy(dest, source);
        System.out.println("Destination after copy: " + dest);

        // Rotate
        List<Integer> toRotate = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("\nBefore rotation: " + toRotate);
        Collections.rotate(toRotate, 2);
        System.out.println("After rotating right by 2: " + toRotate);
        Collections.rotate(toRotate, -2);
        System.out.println("After rotating left by 2: " + toRotate);

        // Swap
        Collections.swap(toRotate, 0, 4);
        System.out.println("After swapping first and last elements: " + toRotate);

        // Immutable collections
        System.out.println("\n[INFO] Unmodifiable wrappers:");
        List<String> modifiableList = new ArrayList<>(Arrays.asList("one", "two", "three"));
        List<String> unmodifiableList = Collections.unmodifiableList(modifiableList);
        System.out.println("Unmodifiable list: " + unmodifiableList);
        System.out.println("Attempting to modify unmodifiable list throws UnsupportedOperationException");

        // Synchronized wrappers
        System.out.println("\n[INFO] Synchronized wrappers:");
        List<String> syncList = Collections.synchronizedList(new ArrayList<>());
        System.out.println("Created synchronized list wrapper (thread-safe)");
        System.out.println("Similarly: synchronizedSet(), synchronizedMap(), etc.");

        // Empty collections
        System.out.println("\n[INFO] Empty collections:");
        List<Object> emptyList = Collections.emptyList();
        Set<Object> emptySet = Collections.emptySet();
        Map<Object, Object> emptyMap = Collections.emptyMap();
        System.out.println("Empty immutable instances useful as return values");

        // Singleton collections
        System.out.println("\n[INFO] Singleton collections:");
        Set<String> singletonSet = Collections.singleton("only element");
        List<String> singletonList = Collections.singletonList("only element");
        Map<String, Integer> singletonMap = Collections.singletonMap("key", 1);
        System.out.println("Singleton set: " + singletonSet);
        System.out.println("Singleton list: " + singletonList);
        System.out.println("Singleton map: " + singletonMap);

        // Arrays utility class overview
        System.out.println("\n[INFO] Arrays utility class overview:");
        System.out.println("Arrays.asList(): Fixed-size list backed by array");
        System.out.println("Arrays.sort(): Sort arrays (primitive or object)");
        System.out.println("Arrays.binarySearch(): Binary search in sorted arrays");
        System.out.println("Arrays.equals(), deepEquals(): Array comparison");
        System.out.println("Arrays.fill(): Fill array with value");
        System.out.println("Arrays.copyOf(), copyOfRange(): Create copies");
    }

    /**
     * SECTION 6: CONCURRENT COLLECTIONS
     * Demonstrates concurrent collection classes from java.util.concurrent
     */
    private static void demonstrateConcurrentCollections() {
        System.out.println("\n===== 6. CONCURRENT COLLECTIONS =====");
        System.out.println("[INFO] java.util.concurrent package provides thread-safe collections");

        // ConcurrentHashMap
        System.out.println("\n[INFO] ConcurrentHashMap - High-concurrency thread-safe map:");
        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("One", 1);
        concurrentMap.put("Two", 2);
        concurrentMap.put("Three", 3);

        System.out.println("ConcurrentHashMap content: " + concurrentMap);

        // Atomic updates
        concurrentMap.replace("One", 1, 11);
        System.out.println("After replace: " + concurrentMap);

        // computeIfAbsent/computeIfPresent
        concurrentMap.computeIfAbsent("Four", k -> 4);
        concurrentMap.computeIfPresent("Two", (k, v) -> v * 10);
        System.out.println("After compute operations: " + concurrentMap);

        // Concurrent operations (just demonstration, not showing results)
        System.out.println("\n[INFO] ConcurrentHashMap features:");
        System.out.println("  - Permits concurrent reads");
        System.out.println("  - Permits a configurable number of concurrent writes");
        System.out.println("  - No ConcurrentModificationException during iteration");
        System.out.println("  - Provides atomic operations: putIfAbsent, remove, replace");
        System.out.println("  - Provides aggregation operations: forEach, reduce, search");

        // CopyOnWriteArrayList
        System.out.println("\n[INFO] CopyOnWriteArrayList - Thread-safe variant of ArrayList:");
        CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();
        cowList.addAll(Arrays.asList("One", "Two", "Three"));

        System.out.println("CopyOnWriteArrayList content: " + cowList);
        System.out.println("Features:");
        System.out.println("  - Creates a fresh copy on each modification");
        System.out.println("  - Iterator doesn't reflect modifications after creation");
        System.out.println("  - No ConcurrentModificationException");
        System.out.println("  - Best for lists that are read often but seldom modified");

        // CopyOnWriteArraySet
        System.out.println("\n[INFO] CopyOnWriteArraySet - Set backed by CopyOnWriteArrayList");
        CopyOnWriteArraySet<String> cowSet = new CopyOnWriteArraySet<>();
        cowSet.addAll(Arrays.asList("A", "B", "C"));
        System.out.println("CopyOnWriteArraySet content: " + cowSet);

        // Concurrent Queue implementations
        System.out.println("\n[INFO] Concurrent Queue implementations:");

        // ConcurrentLinkedQueue
        ConcurrentLinkedQueue<Integer> concurrentQueue = new ConcurrentLinkedQueue<>();
        concurrentQueue.offer(1);
        concurrentQueue.offer(2);
        concurrentQueue.offer(3);
        System.out.println("ConcurrentLinkedQueue: " + concurrentQueue);
        System.out.println("Poll: " + concurrentQueue.poll());
        System.out.println("After poll: " + concurrentQueue);

        // BlockingQueue implementations
        System.out.println("\n[INFO] BlockingQueue implementations:");

        // ArrayBlockingQueue - bounded blocking queue
        ArrayBlockingQueue<Integer> arrayQueue = new ArrayBlockingQueue<>(3);
        arrayQueue.offer(1);
        arrayQueue.offer(2);
        arrayQueue.offer(3);
        System.out.println("ArrayBlockingQueue (bounded, capacity 3): " + arrayQueue);
        System.out.println("Offer when full: " + arrayQueue.offer(4)); // Returns false

        // LinkedBlockingQueue - optionally bounded blocking queue
        LinkedBlockingQueue<Integer> linkedQueue = new LinkedBlockingQueue<>(); // Unbounded
        System.out.println("\nLinkedBlockingQueue (unbounded by default)");

        // DelayQueue - delay before elements become available
        System.out.println("\n[INFO] DelayQueue - elements not available until delay expires");

        // SynchronousQueue - handoff queue with no capacity
        SynchronousQueue<Integer> syncQueue = new SynchronousQueue<>();
        System.out.println("\nSynchronousQueue - no capacity, requires producer and consumer");

        // PriorityBlockingQueue - priority queue that blocks
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue<>();
        System.out.println("\nPriorityBlockingQueue - blocking priority queue");

        // TransferQueue implementation
        System.out.println("\n[INFO] TransferQueue - allows producer to wait for consumer:");
        LinkedTransferQueue<Integer> transferQueue = new LinkedTransferQueue<>();
        System.out.println("LinkedTransferQueue: supports tryTransfer() and transfer() methods");

        // BlockingDeque implementation
        System.out.println("\n[INFO] BlockingDeque - blocking deque interface:");
        LinkedBlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<>();
        System.out.println("LinkedBlockingDeque: thread-safe double-ended blocking queue");

        // ConcurrentSkipListMap/Set
        System.out.println("\n[INFO] ConcurrentSkipListMap/Set - concurrent sorted collections:");
        ConcurrentSkipListMap<Integer, String> skipListMap = new ConcurrentSkipListMap<>();
        skipListMap.put(3, "Three");
        skipListMap.put(1, "One");
        skipListMap.put(2, "Two");
        System.out.println("ConcurrentSkipListMap (concurrent sorted map): " + skipListMap);

        ConcurrentSkipListSet<Integer> skipListSet = new ConcurrentSkipListSet<>();
        skipListSet.addAll(Arrays.asList(3, 1, 4, 2));
        System.out.println("ConcurrentSkipListSet (concurrent sorted set): " + skipListSet);

        // Summary
        System.out.println("\n[INFO] When to use concurrent collections:");
        System.out.println("1. ConcurrentHashMap: High-concurrency, thread-safe alternative to HashMap/Hashtable");
        System.out.println("2. CopyOnWriteArrayList/Set: For collections rarely modified but often iterated");
        System.out.println("3. ConcurrentLinkedQueue: For high-throughput, non-blocking queue operations");
        System.out.println("4. BlockingQueue implementations: For producer-consumer patterns");
        System.out.println("5. ConcurrentSkipListMap/Set: For concurrent access to sorted collections");
    }

    /**
     * SECTION 7: STREAMS WITH COLLECTIONS
     * Demonstrates Java 8+ Stream API used with collections
     */
    private static void demonstrateStreamsWithCollections() {
        System.out.println("\n===== 7. STREAMS WITH COLLECTIONS =====");
        System.out.println("[INFO] Stream API provides functional-style operations on collections");

        List<String> names = Arrays.asList("John", "Alice", "Bob", "Charlie", "David", "Eve", "Frank");
        System.out.println("Original list: " + names);

        // Creating streams
        System.out.println("\n[INFO] Creating streams from collections:");
        System.out.println("From Collection.stream(): collection.stream()");
        System.out.println("From Arrays.stream(): Arrays.stream(array)");
        System.out.println("From Stream.of(): Stream.of(element1, element2, ...)");

        // Filtering
        System.out.println("\n[INFO] Stream filtering operations:");
        List<String> filtered = names.stream()
                .filter(name -> name.length() > 4)
                .collect(Collectors.toList());
        System.out.println("Names longer than 4 chars: " + filtered);

        // Mapping
        System.out.println("\n[INFO] Stream mapping operations:");
        List<Integer> nameLengths = names.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println("Name lengths: " + nameLengths);

        List<String> upperCaseNames = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("Uppercase names: " + upperCaseNames);

        // FlatMap example
        System.out.println("\n[INFO] Stream flatMap operation:");
        List<List<Integer>> listOfLists = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9));

        List<Integer> flattenedList = listOfLists.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        System.out.println("Flattened list: " + flattenedList);

        // Sorting
        System.out.println("\n[INFO] Stream sorting operations:");
        List<String> sortedNames = names.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Alphabetically sorted: " + sortedNames);

        List<String> sortedByLength = names.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        System.out.println("Sorted by length: " + sortedByLength);

        // Distinct
        System.out.println("\n[INFO] Stream distinct operation:");
        List<Integer> numbersWithDuplicates = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
        List<Integer> distinctNumbers = numbersWithDuplicates.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Original numbers: " + numbersWithDuplicates);
        System.out.println("Distinct numbers: " + distinctNumbers);

        // Limit and skip
        System.out.println("\n[INFO] Stream limit and skip operations:");
        List<String> limited = names.stream()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("First 3 names: " + limited);

        List<String> skipped = names.stream()
                .skip(4)
                .collect(Collectors.toList());
        System.out.println("Names after skipping 4: " + skipped);

        // Peek (for debugging)
        System.out.println("\n[INFO] Stream peek operation (for debugging):");
        List<String> peekExample = names.stream()
                .filter(n -> n.length() > 3)
                .peek(n -> System.out.println("Filtered: " + n))
                .map(String::toUpperCase)
                .peek(n -> System.out.println("Mapped: " + n))
                .collect(Collectors.toList());
        System.out.println("Final result: " + peekExample);

        // Terminal operations
        System.out.println("\n[INFO] Stream terminal operations:");

        // forEach
        System.out.println("forEach example:");
        names.stream()
                .forEach(name -> System.out.print(name + " "));
        System.out.println();

        // count
        long count = names.stream()
                .filter(name -> name.startsWith("A"))
                .count();
        System.out.println("Count of names starting with 'A': " + count);

        // anyMatch, allMatch, noneMatch
        boolean anyStartsWithJ = names.stream().anyMatch(name -> name.startsWith("J"));
        boolean allLongerThan2 = names.stream().allMatch(name -> name.length() > 2);
        boolean noneStartWithZ = names.stream().noneMatch(name -> name.startsWith("Z"));

        System.out.println("Any name starts with 'J': " + anyStartsWithJ);
        System.out.println("All names longer than 2 chars: " + allLongerThan2);
        System.out.println("No name starts with 'Z': " + noneStartWithZ);

        // findFirst and findAny
        String firstLongName = names.stream()
                .filter(name -> name.length() > 4)
                .findFirst()
                .orElse("None found");
        System.out.println("First name longer than 4 chars: " + firstLongName);

        // reduce
        System.out.println("\n[INFO] Stream reduce operation:");

        // Sum of numbers
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream()
                .reduce(0, Integer::sum);
        System.out.println("Sum of numbers: " + sum);

        // Concatenate strings
        String concatenated = names.stream()
                .reduce("", (s1, s2) -> s1 + s2 + ", ");
        System.out.println("Concatenated names: " + concatenated);

        // min and max
        String shortest = names.stream()
                .min(Comparator.comparingInt(String::length))
                .orElse("None");
        String longest = names.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("None");

        System.out.println("Shortest name: " + shortest);
        System.out.println("Longest name: " + longest);

        // Collectors
        System.out.println("\n[INFO] Collectors utility class:");

        // toList, toSet, toMap
        List<String> toListExample = names.stream().collect(Collectors.toList());
        Set<String> toSetExample = names.stream().collect(Collectors.toSet());
        Map<String, Integer> toMapExample = names.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        String::length));

        System.out.println("toList: " + toListExample);
        System.out.println("toSet: " + toSetExample);
        System.out.println("toMap (name -> length): " + toMapExample);

        // joining
        String joined = names.stream().collect(Collectors.joining(", ", "[", "]"));
        System.out.println("Joined with commas: " + joined);

        // counting
        Long totalCount = names.stream().collect(Collectors.counting());
        System.out.println("Total count: " + totalCount);

        // summarizing
        IntSummaryStatistics stats = names.stream()
                .collect(Collectors.summarizingInt(String::length));
        System.out.println("Name length statistics: " + stats);

        // groupingBy
        Map<Integer, List<String>> groupedByLength = names.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println("Names grouped by length: " + groupedByLength);

        // partitioningBy
        Map<Boolean, List<String>> partitioned = names.stream()
                .collect(Collectors.partitioningBy(name -> name.length() > 4));
        System.out.println("Names partitioned by length > 4: " + partitioned);

        // Parallel streams
        System.out.println("\n[INFO] Parallel streams:");
        long startTime = System.nanoTime();
        long count1 = names.stream()
                .map(String::toUpperCase)
                .count();
        long endTime = System.nanoTime();
        System.out.println("Sequential stream time: " + (endTime - startTime) + " ns");

        startTime = System.nanoTime();
        long count2 = names.parallelStream()
                .map(String::toUpperCase)
                .count();
        endTime = System.nanoTime();
        System.out.println("Parallel stream time: " + (endTime - startTime) + " ns");
        System.out.println("Note: Parallel is not always faster for small collections");

        // Summary
        System.out.println("\n[INFO] Stream operations summary:");
        System.out.println("1. Intermediate Operations: filter, map, flatMap, sorted, distinct, limit, skip");
        System.out.println("2. Terminal Operations: forEach, collect, count, reduce, min, max, findFirst, findAny");
        System.out.println("3. Short-circuiting Operations: anyMatch, allMatch, noneMatch, findFirst, findAny");
    }

    /**
     * SECTION 8: CUSTOM OBJECTS IN COLLECTIONS
     * Demonstrates using custom objects in collections with proper equals, hashCode
     * and Comparable implementations
     */
    private static void demonstrateCustomObjects() {
        System.out.println("\n===== 8. COLLECTIONS WITH CUSTOM OBJECTS =====");
        System.out.println("[INFO] Using custom objects in collections requires proper implementation");

        // Create some Person objects
        Person p1 = new Person("Alice", 28);
        Person p2 = new Person("Bob", 32);
        Person p3 = new Person("Charlie", 25);
        Person p4 = new Person("Alice", 28); // Same as p1

        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);
        System.out.println("p3: " + p3);
        System.out.println("p4: " + p4 + " (same data as p1)");

        // Testing equals and hashCode
        System.out.println("\n[INFO] Testing equals() and hashCode():");
        System.out.println("p1.equals(p4): " + p1.equals(p4));
        System.out.println("p1.hashCode() == p4.hashCode(): " + (p1.hashCode() == p4.hashCode()));

        // Using in Lists
        System.out.println("\n[INFO] Using custom objects in List:");
        List<Person> personList = new ArrayList<>();
        personList.add(p1);
        personList.add(p2);
        personList.add(p3);
        personList.add(p4); // Duplicate by value

        System.out.println("Person list (allows duplicates): " + personList);
        System.out.println("personList.contains(p4): " + personList.contains(p4));
        System.out.println("personList.indexOf(p4): " + personList.indexOf(p4)); // Returns index of first equal object

        // Using in Sets
        System.out.println("\n[INFO] Using custom objects in Set:");
        Set<Person> personSet = new HashSet<>();
        personSet.add(p1);
        personSet.add(p2);
        personSet.add(p3);
        personSet.add(p4); // Not added because equals() shows it's a duplicate

        System.out.println("Person set (no duplicates): " + personSet);
        System.out.println("personSet.size(): " + personSet.size() + " (p4 not added as it equals p1)");
        System.out.println("personSet.contains(p4): " + personSet.contains(p4));

        // Using in Maps as keys
        System.out.println("\n[INFO] Using custom objects as Map keys:");
        Map<Person, String> personMap = new HashMap<>();
        personMap.put(p1, "Person One");
        personMap.put(p2, "Person Two");
        personMap.put(p3, "Person Three");
        personMap.put(p4, "Person Four"); // Overwrites p1's value because they're "equal"

        System.out.println("Person map size: " + personMap.size() + " (p4 replaced p1's value)");
        System.out.println("Value for p1: " + personMap.get(p1));

        // Sorting custom objects with Comparable
        System.out.println("\n[INFO] Sorting with Comparable (natural ordering):");
        List<Person> sortedPersonList = new ArrayList<>(Arrays.asList(p1, p2, p3));
        Collections.sort(sortedPersonList); // Uses Person's compareTo method
        System.out.println("Sorted by age (natural order): " + sortedPersonList);

        // Sorting with Comparator
        System.out.println("\n[INFO] Sorting with Comparator (custom ordering):");
        Collections.sort(sortedPersonList, Comparator.comparing(Person::getName));
        System.out.println("Sorted by name: " + sortedPersonList);

        // Reverse order
        Collections.sort(sortedPersonList, Comparator.comparing(Person::getAge).reversed());
        System.out.println("Sorted by age descending: " + sortedPersonList);

        // Multiple criteria
        Comparator<Person> nameAgeSorter = Comparator
                .comparing(Person::getName)
                .thenComparing(Person::getAge);
        Collections.sort(sortedPersonList, nameAgeSorter);
        System.out.println("Sorted by name, then age: " + sortedPersonList);

        // TreeSet with custom objects
        System.out.println("\n[INFO] Using custom objects in TreeSet:");
        TreeSet<Person> personTreeSet = new TreeSet<>(); // Uses natural ordering
        personTreeSet.addAll(Arrays.asList(p1, p2, p3, p4));
        System.out.println("TreeSet (sorted by age): " + personTreeSet);

        // TreeSet with custom comparator
        TreeSet<Person> nameOrderedSet = new TreeSet<>(Comparator.comparing(Person::getName));
        nameOrderedSet.addAll(Arrays.asList(p1, p2, p3, p4));
        System.out.println("TreeSet (sorted by name): " + nameOrderedSet);

        // Immutable objects best practices
        System.out.println("\n[INFO] Best practices for custom objects in collections:");
        System.out.println("1. Make objects immutable when possible");
        System.out.println("2. Always override both equals() and hashCode()");
        System.out.println("3. Make equals()/hashCode() based on immutable fields");
        System.out.println("4. Implement Comparable for natural ordering");
        System.out.println("5. Use separate Comparator for different sorting needs");
        System.out.println("6. Be careful with mutable objects as Set elements or Map keys");
    }

    /**
     * Person class to demonstrate custom objects in collections
     */
    private static class Person implements Comparable<Person> {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Person person = (Person) o;
            return age == person.age && Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age);
        }

        @Override
        public String toString() {
            return name + " (" + age + ")";
        }

        @Override
        public int compareTo(Person other) {
            // Natural ordering by age
            return Integer.compare(this.age, other.age);
        }
    }

}
