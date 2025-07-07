package com.monal.Stacks_Queues;

import java.util.*;

/**
 * LRU CACHE (Least Recently Used):
 * - Eviction policy: Remove the least recently used item when cache is full
 * - Operations: get() and put() both should be O(1)
 * - Implementation: HashMap + Doubly Linked List
 * - HashMap provides O(1) access, DLL provides O(1) insertion/deletion
 * - Most recently used items are at head, least recently used at tail
 *
 * LFU CACHE (Least Frequently Used):
 * - Eviction policy: Remove the least frequently used item when cache is full
 * - If tie in frequency, remove the least recently used among them
 * - Operations: get() and put() both should be O(1)
 * - Implementation: HashMap + HashMap of frequency + Doubly Linked Lists
 * - Track frequency of each key and maintain separate DLL for each frequency
 */

public class P018 {

    // ==================== PROBLEM 1: LRU Cache Implementation ====================

    /**
     * LRU Cache - Design and implement a data structure for LRU cache
     * Time Complexity: O(1) for both get and put operations
     * Space Complexity: O(capacity)
     */
    static class LRUCache {
        // Node class for doubly linked list
        class Node {
            int key, value;
            Node prev, next;

            Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }

        private HashMap<Integer, Node> cache;
        private int capacity;
        private Node head, tail;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<>();

            // Create dummy head and tail nodes
            head = new Node(0, 0);
            tail = new Node(0, 0);
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            Node node = cache.get(key);
            if (node == null) {
                return -1;
            }

            // Move the accessed node to head (most recently used)
            moveToHead(node);
            return node.value;
        }

        public void put(int key, int value) {
            Node node = cache.get(key);

            if (node == null) {
                Node newNode = new Node(key, value);

                if (cache.size() >= capacity) {
                    // Remove least recently used node (tail.prev)
                    Node tail_prev = removeTail();
                    cache.remove(tail_prev.key);
                }

                cache.put(key, newNode);
                addToHead(newNode);
            } else {
                // Update existing node
                node.value = value;
                moveToHead(node);
            }
        }

        private void addToHead(Node node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void moveToHead(Node node) {
            removeNode(node);
            addToHead(node);
        }

        private Node removeTail() {
            Node lastNode = tail.prev;
            removeNode(lastNode);
            return lastNode;
        }
    }

    // ==================== PROBLEM 2: LFU Cache Implementation ====================

    /**
     * LFU Cache - Design and implement a data structure for LFU cache
     * Time Complexity: O(1) for both get and put operations
     * Space Complexity: O(capacity)
     */
    static class LFUCache {
        class Node {
            int key, value, freq;
            Node prev, next;

            Node(int key, int value) {
                this.key = key;
                this.value = value;
                this.freq = 1;
            }
        }

        class DLList {
            Node head, tail;
            int size;

            public DLList() {
                head = new Node(0, 0);
                tail = new Node(0, 0);
                head.next = tail;
                tail.prev = head;
                size = 0;
            }

            void addToHead(Node node) {
                node.prev = head;
                node.next = head.next;
                head.next.prev = node;
                head.next = node;
                size++;
            }

            void removeNode(Node node) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                size--;
            }

            Node removeTail() {
                Node lastNode = tail.prev;
                removeNode(lastNode);
                return lastNode;
            }
        }

        private int capacity, size, minFreq;
        private HashMap<Integer, Node> nodeMap;
        private HashMap<Integer, DLList> freqMap;

        public LFUCache(int capacity) {
            this.capacity = capacity;
            this.size = 0;
            this.minFreq = 0;
            this.nodeMap = new HashMap<>();
            this.freqMap = new HashMap<>();
        }

        public int get(int key) {
            Node node = nodeMap.get(key);
            if (node == null) {
                return -1;
            }

            updateNode(node);
            return node.value;
        }

        public void put(int key, int value) {
            if (capacity == 0) return;

            if (nodeMap.containsKey(key)) {
                Node node = nodeMap.get(key);
                node.value = value;
                updateNode(node);
            } else {
                size++;
                if (size > capacity) {
                    // Remove LFU node
                    DLList minFreqList = freqMap.get(minFreq);
                    Node deleteNode = minFreqList.removeTail();
                    nodeMap.remove(deleteNode.key);
                    size--;
                }

                // Add new node
                minFreq = 1;
                Node newNode = new Node(key, value);
                nodeMap.put(key, newNode);
                freqMap.computeIfAbsent(1, k -> new DLList()).addToHead(newNode);
            }
        }

        private void updateNode(Node node) {
            int currFreq = node.freq;
            DLList currList = freqMap.get(currFreq);
            currList.removeNode(node);

            // If current list becomes empty and it was the min frequency
            if (currFreq == minFreq && currList.size == 0) {
                minFreq++;
            }

            node.freq++;
            freqMap.computeIfAbsent(node.freq, k -> new DLList()).addToHead(node);
        }
    }

    // ==================== PROBLEM 3: Design Browser History ====================

    /**
     * Design a browser history system that supports:
     * - visit(url): Visit a url from current page
     * - back(steps): Go back in history by steps
     * - forward(steps): Go forward in history by steps
     *
     * This can be solved using a list/array or doubly linked list
     */
    static class BrowserHistory {
        private List<String> history;
        private int currentIndex;

        public BrowserHistory(String homepage) {
            history = new ArrayList<>();
            history.add(homepage);
            currentIndex = 0;
        }

        public void visit(String url) {
            // Remove all forward history
            while (history.size() > currentIndex + 1) {
                history.remove(history.size() - 1);
            }

            history.add(url);
            currentIndex++;
        }

        public String back(int steps) {
            currentIndex = Math.max(0, currentIndex - steps);
            return history.get(currentIndex);
        }

        public String forward(int steps) {
            currentIndex = Math.min(history.size() - 1, currentIndex + steps);
            return history.get(currentIndex);
        }
    }

    // ==================== PROBLEM 4: Design Hit Counter ====================

    /**
     * Design a hit counter which counts the number of hits received in the past 5 minutes.
     * - hit(timestamp): Record a hit at given timestamp
     * - getHits(timestamp): Return number of hits in past 5 minutes
     */
    static class HitCounter {
        private Queue<Integer> hits;

        public HitCounter() {
            hits = new LinkedList<>();
        }

        public void hit(int timestamp) {
            hits.offer(timestamp);
        }

        public int getHits(int timestamp) {
            // Remove hits older than 5 minutes (300 seconds)
            while (!hits.isEmpty() && hits.peek() <= timestamp - 300) {
                hits.poll();
            }
            return hits.size();
        }
    }

    // ==================== PROBLEM 5: All O(1) Data Structure ====================

    /**
     * Design a data structure that supports:
     * - inc(key): Increment count of key by 1
     * - dec(key): Decrement count of key by 1
     * - getMaxKey(): Return key with maximum count
     * - getMinKey(): Return key with minimum count
     * All operations should be O(1)
     */
    static class AllOne {
        class Node {
            int count;
            Set<String> keys;
            Node prev, next;

            Node(int count) {
                this.count = count;
                this.keys = new HashSet<>();
            }
        }

        private HashMap<String, Node> keyToNode;
        private Node head, tail;

        public AllOne() {
            keyToNode = new HashMap<>();
            head = new Node(0);
            tail = new Node(0);
            head.next = tail;
            tail.prev = head;
        }

        public void inc(String key) {
            if (keyToNode.containsKey(key)) {
                Node node = keyToNode.get(key);
                node.keys.remove(key);

                Node nextNode = node.next;
                if (nextNode.count != node.count + 1) {
                    nextNode = new Node(node.count + 1);
                    insertAfter(node, nextNode);
                }

                nextNode.keys.add(key);
                keyToNode.put(key, nextNode);

                if (node.keys.isEmpty()) {
                    removeNode(node);
                }
            } else {
                Node firstNode = head.next;
                if (firstNode.count != 1) {
                    firstNode = new Node(1);
                    insertAfter(head, firstNode);
                }

                firstNode.keys.add(key);
                keyToNode.put(key, firstNode);
            }
        }

        public void dec(String key) {
            if (!keyToNode.containsKey(key)) return;

            Node node = keyToNode.get(key);
            node.keys.remove(key);

            if (node.count == 1) {
                keyToNode.remove(key);
            } else {
                Node prevNode = node.prev;
                if (prevNode.count != node.count - 1) {
                    prevNode = new Node(node.count - 1);
                    insertAfter(node.prev, prevNode);
                }

                prevNode.keys.add(key);
                keyToNode.put(key, prevNode);
            }

            if (node.keys.isEmpty()) {
                removeNode(node);
            }
        }

        public String getMaxKey() {
            if (tail.prev == head) return "";
            return tail.prev.keys.iterator().next();
        }

        public String getMinKey() {
            if (head.next == tail) return "";
            return head.next.keys.iterator().next();
        }

        private void insertAfter(Node prev, Node node) {
            node.next = prev.next;
            node.prev = prev;
            prev.next.prev = node;
            prev.next = node;
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    // ==================== TEST METHODS ====================

    public static void testLRUCache() {
        System.out.println("=== Testing LRU Cache ===");
        LRUCache lru = new LRUCache(2);

        lru.put(1, 1);
        lru.put(2, 2);
        System.out.println("get(1): " + lru.get(1)); // returns 1
        lru.put(3, 3); // evicts key 2
        System.out.println("get(2): " + lru.get(2)); // returns -1 (not found)
        lru.put(4, 4); // evicts key 1
        System.out.println("get(1): " + lru.get(1)); // returns -1 (not found)
        System.out.println("get(3): " + lru.get(3)); // returns 3
        System.out.println("get(4): " + lru.get(4)); // returns 4
    }

    public static void testLFUCache() {
        System.out.println("\n=== Testing LFU Cache ===");
        LFUCache lfu = new LFUCache(2);

        lfu.put(1, 1);
        lfu.put(2, 2);
        System.out.println("get(1): " + lfu.get(1)); // returns 1
        lfu.put(3, 3); // evicts key 2
        System.out.println("get(2): " + lfu.get(2)); // returns -1 (not found)
        System.out.println("get(3): " + lfu.get(3)); // returns 3
        lfu.put(4, 4); // evicts key 1
        System.out.println("get(1): " + lfu.get(1)); // returns -1 (not found)
        System.out.println("get(3): " + lfu.get(3)); // returns 3
        System.out.println("get(4): " + lfu.get(4)); // returns 4
    }

    public static void testBrowserHistory() {
        System.out.println("\n=== Testing Browser History ===");
        BrowserHistory browser = new BrowserHistory("leetcode.com");

        browser.visit("google.com");
        browser.visit("facebook.com");
        browser.visit("youtube.com");
        System.out.println("back(1): " + browser.back(1)); // facebook.com
        System.out.println("back(1): " + browser.back(1)); // google.com
        System.out.println("forward(1): " + browser.forward(1)); // facebook.com
        browser.visit("linkedin.com");
        System.out.println("forward(2): " + browser.forward(2)); // linkedin.com
        System.out.println("back(2): " + browser.back(2)); // google.com
        System.out.println("back(7): " + browser.back(7)); // leetcode.com
    }

    public static void main(String[] args) {
        testLRUCache();
        testLFUCache();
        testBrowserHistory();
    }
}