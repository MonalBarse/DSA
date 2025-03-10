package com.monal.F_customArraylist;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A custom implementation of ArrayList that demonstrates the principles behind
 * Java's ArrayList implementation.
 *
 * @param <E> the type of elements in this list
 */
public class CustomArrayList<E> implements Iterable<E> {
    // Default initial capacity
    private static final int DEFAULT_CAPACITY = 10;

    // Shared empty array instance for empty instances
    private static final Object[] EMPTY_ELEMENTDATA = {};

    // The array buffer into which the elements are stored
    private Object[] elementData;

    // The size of the ArrayList (number of elements it contains)
    private int size;

    // modCount is used to keep track of the number of times the list has been
    // structurally modified.
    // This is used to detect concurrent modification during iteration.
    private int modCount = 0;

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public CustomArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity is
     *                                  negative
     */
    public CustomArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    // Returns the number of elements in this list.
    public int size() {
        return size;
    }

    // Returns true if this list contains no elements.
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element element to be appended to this list
     * @return true (as specified by the Collection interface)
     */
    public boolean add(E element) {
        // Ensure capacity
        ensureCapacityInternal(size + 1);

        // Add element and increment size
        elementData[size++] = element;
        modCount++;
        return true;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void add(int index, E element) {
        // Check if index is valid
        rangeCheckForAdd(index);

        // Ensure capacity
        ensureCapacityInternal(size + 1);

        // Shift elements
        System.arraycopy(elementData, index, elementData, index + 1, size - index);

        // Insert element
        elementData[index] = element;
        size++;
        modCount++;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        // Check if index is valid
        rangeCheck(index);

        return (E) elementData[index];
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        // Check if index is valid
        rangeCheck(index);

        E oldValue = (E) elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        // Check if index is valid
        rangeCheck(index);

        modCount++;
        E oldValue = (E) elementData[index];

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }

        // Clear reference to help GC
        elementData[--size] = null;

        return oldValue;
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it
     * is present.
     * If the list does not contain the element, it is unchanged.
     *
     * @param o element to be removed from this list, if present
     * @return true if this list contained the specified element
     */
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++) {
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
            }
        } else {
            for (int index = 0; index < size; index++) {
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Private remove method that skips bounds checking and does not return the
     * removed value.
     */
    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
    }

    /**
     * Removes all elements from this list. The list will be empty after this call
     * returns.
     */
    public void clear() {
        modCount++;

        // Clear references to help GC
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }

        size = 0;
    }

    /**
     * Returns true if this list contains the specified element.
     *
     * @param o element whose presence in this list is to be tested
     * @return true if this list contains the specified element
     */
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * Returns the index of the first occurrence of the specified element in this
     * list,
     * or -1 if this list does not contain the element.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in this
     *         list,
     *         or -1 if this list does not contain the element
     */
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element in this
     * list,
     * or -1 if this list does not contain the element.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in this
     *         list,
     *         or -1 if this list does not contain the element
     */
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Trims the capacity of this ArrayList instance to be the list's current size.
     * An application can use this operation to minimize the storage of an ArrayList
     * instance.
     */
    public void trimToSize() {
        modCount++;
        if (size < elementData.length) {
            elementData = (size == 0)
                    ? EMPTY_ELEMENTDATA
                    : Arrays.copyOf(elementData, size);
        }
    }

    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void ensureCapacityInternal(int minCapacity) {
        // Calculate how much to grow
        int oldCapacity = elementData.length;

        // If we need more space
        if (minCapacity > oldCapacity) {
            // Define new capacity using growth formula
            int newCapacity = oldCapacity + (oldCapacity >> 1); // 50% growth

            // If our formula doesn't give enough capacity
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }

            // Create a new array and copy elements
            elementData = Arrays.copyOf(elementData, newCapacity);

            System.out.println("[INFO] Resizing from " + oldCapacity + " to " + newCapacity);
        }
    }

    /**
     * Checks if the given index is in range. If not, throws appropriate exception.
     */
    private void rangeCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Similar to rangeCheck, but used for add operation, so allows index == size
     */
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /*
     * Returns an array containing all of the elements in this list in proper
     * sequence.
     */
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    // Returns an iterator over the elements in this list in proper sequence
    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    /**
     * Private iterator implementation for CustomArrayList
     */
    private class ArrayListIterator implements Iterator<E> {
        private int cursor = 0;
        private int expectedModCount = modCount;
        private int lastRet = -1; // index of last element returned; -1 if no such

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            // Check for concurrent modification
            checkForComodification();

            int i = cursor;
            if (i >= size) {
                throw new NoSuchElementException();
            }

            Object[] data = CustomArrayList.this.elementData;
            if (i >= data.length) {
                throw new ConcurrentModificationException();
            }

            cursor = i + 1;
            return (E) data[lastRet = i];
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }

            // Check for concurrent modification
            checkForComodification();

            try {
                CustomArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        private void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    // Override toString to provide a readable representation
    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(elementData[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    // Main method for demonstration
    public static void main(String[] args) {
        System.out.println("\n========== CUSTOM ARRAYLIST IMPLEMENTATION DEMO ==========\n");

        // Create a new CustomArrayList
        System.out.println("Creating new CustomArrayList...");
        CustomArrayList<String> list = new CustomArrayList<>();

        // Test add method
        System.out.println("\n===== ADDING ELEMENTS =====");
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        System.out.println("List after adding elements: " + list);
        System.out.println("Size: " + list.size());

        // Test get method
        System.out.println("\n===== GETTING ELEMENTS =====");
        System.out.println("Element at index 1: " + list.get(1));

        // Test set method
        System.out.println("\n===== SETTING ELEMENTS =====");
        String old = list.set(1, "Blueberry");
        System.out.println("Previous element at index 1: " + old);
        System.out.println("List after setting element: " + list);

        // Test indexed add
        System.out.println("\n===== INSERTING ELEMENTS =====");
        list.add(1, "Apricot");
        System.out.println("List after inserting at index 1: " + list);

        // Test remove by index
        System.out.println("\n===== REMOVING BY INDEX =====");
        String removed = list.remove(2);
        System.out.println("Removed element: " + removed);
        System.out.println("List after removing index 2: " + list);

        // Test remove by object
        System.out.println("\n===== REMOVING BY OBJECT =====");
        boolean wasRemoved = list.remove("Apple");
        System.out.println("Was 'Apple' removed? " + wasRemoved);
        System.out.println("List after removing 'Apple': " + list);

        // Test contains
        System.out.println("\n===== CHECKING CONTAINS =====");
        System.out.println("Does list contain 'Cherry'? " + list.contains("Cherry"));
        System.out.println("Does list contain 'Apple'? " + list.contains("Apple"));

        // Test indexOf
        System.out.println("\n===== FINDING INDEX =====");
        System.out.println("Index of 'Cherry': " + list.indexOf("Cherry"));
        System.out.println("Index of 'Dragon Fruit': " + list.indexOf("Dragon Fruit"));

        // Test auto-resizing
        System.out.println("\n===== AUTO-RESIZING =====");
        System.out.println("Adding many elements to trigger resize...");
        for (int i = 0; i < 15; i++) {
            list.add("Item " + i);
        }
        System.out.println("Size after adding many elements: " + list.size());

        // Test iterator
        System.out.println("\n===== ITERATOR USAGE =====");
        System.out.println("Iterating through elements:");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            System.out.print(item + " ");
        }
        System.out.println();

        // Test clear
        System.out.println("\n===== CLEARING LIST =====");
        list.clear();
        System.out.println("List after clearing: " + list);
        System.out.println("Is list empty? " + list.isEmpty());

        // Demonstrate fail-fast iterator
        System.out.println("\n===== FAIL-FAST ITERATOR DEMO =====");
        list.add("Element 1");
        list.add("Element 2");
        list.add("Element 3");

        try {
            System.out.println("Attempting to modify list during iteration...");
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                String value = it.next();
                System.out.println("Value: " + value);

                if (value.equals("Element 2")) {
                    // This will trigger ConcurrentModificationException
                    list.add("Element 4");
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Caught ConcurrentModificationException as expected when modifying during iteration");
        }

        // Demonstrate proper removal during iteration
        System.out.println("\n===== PROPER REMOVAL DURING ITERATION =====");
        System.out.println("Before removal: " + list);

        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String value = it.next();
            if (value.equals("Element 2")) {
                it.remove(); // Safe way to remove during iteration
            }
        }

        System.out.println("After removal: " + list);
    }
}

class ConcurrentModificationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConcurrentModificationException() {
        super();
    }

    public ConcurrentModificationException(String message) {
        super(message);
    }
}
