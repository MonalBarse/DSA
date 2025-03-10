package com.monal.H_Generics;

/*
 * Generics in Java -
 *  - Generics were added to Java in JDK 5.0 to provide compile-time type safety for collections.
 *  - Generics allow us to create classes, interfaces, and methods that operate with a type parameter
 *    a type parameter is a placeholder for a specific type that will be provided when the class is used.
 *  - Generics help to reduce the number of typecasts and exceptions at runtime.
 *  - Generics are used to create classes that operate on a parameterized type.
 *  - The syntax for declaring a generic class is similar to a regular class, but with a type parameter.
 *    ( class Box<T> { ... } )
 *  - The type parameter can be used to declare variables, methods, and return types.
 */

// ========== 1. Basic Generics Concepts ==========
// A simple generic class with a single type parameter
class Box<T> {
  private T content;

  public Box(T content) {
    this.content = content;
  }

  public T getContent() {
    System.out.println("\n[INFO] Getting content from Box<" + content.getClass().getSimpleName() + ">");
    return content;
  }

  public void setContent(T content) {
    System.out.println("\n[INFO] Setting content in Box<" + content.getClass().getSimpleName() + ">");
    this.content = content;
  }
}

// ========== 2. Multiple Type Parameters ==========
// A generic class with two type parameters
class Pair<K, V> {
  private K key;
  private V value;

  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return key;
  }

  public V getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Pair { \n" +
        "key=" + key + ", \n" +
        "value=" + value + "\n" +
        "}";
  }
}

// ========== 3. Bounded Type Parameters ==========
// Using extends to limit what types can be used as type parameters
class NumberBox<T extends Number> {
  private T number;

  public NumberBox(T number) {
    this.number = number;
  }

  public T getNumber() {
    return number;
  }

  // Can use methods of Number since we know T extends Number
  public double getDoubleValue() {
    return number.doubleValue();
  }

  // Can compare with other NumberBox instances
  public boolean isGreaterThan(NumberBox<?> other) {
    return this.getDoubleValue() > other.getDoubleValue();
  }
}

// ========== 4. Wildcard Type Parameters ==========
class WildcardExample {
  // Using ? (unknown type) as a wildcard
  public static void printBoxContent(Box<?> box) {
    System.out.println("[INFO] Box contains: " + box.getContent());
  }

  // Upper bounded wildcard - accepts Box of any type that is a subclass of Number
  public static double sumOfNumberBox(Box<? extends Number> box) {
    return box.getContent().doubleValue();
  }

  // Lower bounded wildcard - accepts Box of Integer or any superclass of Integer
  public static void addInteger(Box<? super Integer> box, Integer value) {
    System.out.println("\n[INFO] Using lower bounded wildcard");
    box.setContent(value);
  }
}

// ========== 5. Generic Methods ==========
class GenericMethods {
  // A generic method that works with any type
  public static <T> void swap(T[] array, int i, int j) {
    System.out.println("\n[INFO] Swapping elements in array");
    T temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  // Generic method with bounded type parameter
  public static <T extends Comparable<T>> T findMax(T[] array) {
    if (array == null || array.length == 0) {
      return null;
    }

    T max = array[0];
    for (int i = 1; i < array.length; i++) {
      if (array[i].compareTo(max) > 0) {
        max = array[i];
      }
    }
    return max;
  }
}

// ========== 6. Type Erasure ==========
// Demonstrating how generics are implemented using Type Erasure
class TypeErasureExample {
  // The following methods would cause a compile error because they have the same
  // erasure due to type erasure (both become "process(Object)" at runtime)
  /*
   * public void process(List<String> stringList) {
   * // Process strings
   * }
   *
   * public void process(List<Integer> intList) {
   * // Process integers
   * }
   */

  // Instead, we need to use different method names or type parameters
  public void processStrings(Box<String> stringBox) {
    System.out.println("\n[INFO] Processing string box: " + stringBox.getContent());
  }

  public void processIntegers(Box<Integer> intBox) {
    System.out.println("\n[INFO] Processing integer box: " + intBox.getContent());
  }
}

// ========== 7. Generic Inheritance and Subtyping ==========
interface Source<T> {
  T getSource();
}

class StringSource implements Source<String> {
  @Override
  public String getSource() {
    return "String Source";
  }
}

class IntegerSource implements Source<Integer> {
  @Override
  public Integer getSource() {
    return 42;
  }
}

// ========== 8. Recursive Type Bounds ==========
// Using a type parameter as part of its own bound
interface Comparable<T> {
  int compareTo(T other);
}

class Person implements Comparable<Person> {
  private String name;
  private int age;

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  @Override
  public int compareTo(Person other) {
    return this.age - other.age;
  }

  @Override
  public String toString() {
    return name + " (" + age + ")";
  }
}

// Main Class to demonstrate all concepts
public class GenericsExample {
  public static void main(String[] args) {
    System.out.println("\n========== COMPREHENSIVE JAVA GENERICS TUTORIAL ==========\n");

    // ===== 1. BASIC GENERIC CLASS USAGE =====
    System.out.println("\n===== 1. BASIC GENERIC CLASS USAGE =====");
    Box<String> stringBox = new Box<>("Hello, Generics!");
    String content = stringBox.getContent();
    System.out.println("[INFO] String content: " + content);

    Box<Integer> intBox = new Box<>(123);
    Integer intContent = intBox.getContent();
    System.out.println("[INFO] Integer content: " + intContent);

    // ===== 2. DIAMOND OPERATOR (JAVA 7+) =====
    System.out.println("\n===== 2. DIAMOND OPERATOR =====");
    // Using diamond operator for type inference
    Box<Double> doubleBox = new Box<>(3.14);
    System.out.println("[INFO] Double content: " + doubleBox.getContent());

    // ===== 3. MULTIPLE TYPE PARAMETERS =====
    System.out.println("\n===== 3. MULTIPLE TYPE PARAMETERS =====");
    Pair<String, Integer> pair = new Pair<>("age", 30);
    System.out.println("[INFO] Pair key: " + pair.getKey() + ", value: " + pair.getValue());

    // ===== 4. BOUNDED TYPE PARAMETERS =====
    System.out.println("\n===== 4. BOUNDED TYPE PARAMETERS =====");
    NumberBox<Integer> intNumberBox = new NumberBox<>(10);
    NumberBox<Double> doubleNumberBox = new NumberBox<>(5.5);

    System.out.println("[INFO] intNumberBox value: " + intNumberBox.getNumber());
    System.out.println("[INFO] doubleNumberBox value: " + doubleNumberBox.getNumber());

    boolean isGreater = intNumberBox.isGreaterThan(doubleNumberBox);
    System.out.println("[INFO] Is intNumberBox greater than doubleNumberBox? " + isGreater);

    // The following would not compile:
    // NumberBox<String> stringNumberBox = new NumberBox<>("test"); // Error: String
    // is not a Number

    // ===== 5. WILDCARDS =====
    System.out.println("\n===== 5. WILDCARDS =====");
    // Unbounded wildcard
    WildcardExample.printBoxContent(stringBox);
    WildcardExample.printBoxContent(intBox);

    // Upper bounded wildcard
    double sum = WildcardExample.sumOfNumberBox(intBox);
    System.out.println("[INFO] Sum of NumberBox: " + sum);

    // Lower bounded wildcard
    Box<Number> numberBox = new Box<>(0);
    WildcardExample.addInteger(numberBox, 42);
    System.out.println("[INFO] After adding integer: " + numberBox.getContent());

    // ===== 6. GENERIC METHODS =====
    System.out.println("\n===== 6. GENERIC METHODS =====");
    Integer[] intArray = { 1, 2, 3, 4, 5 };
    System.out.println("[INFO] Before swap: " + intArray[0] + ", " + intArray[4]);
    GenericMethods.swap(intArray, 0, 4);
    System.out.println("[INFO] After swap: " + intArray[0] + ", " + intArray[4]);

    // Integer maxInt = GenericMethods.findMax(intArray);
    // System.out.println("[INFO] Maximum value in integer array: " + maxInt);

    // String[] strArray = { "apple", "banana", "cherry", "date", "elderberry" };
    // String maxStr = GenericMethods.findMax(strArray);
    // System.out.println("[INFO] Maximum value in string array: " + maxStr);

    // ===== 7. TYPE ERASURE =====
    System.out.println("\n===== 7. TYPE ERASURE =====");
    System.out.println("[INFO] At runtime, Box<String> and Box<Integer> are both just Box");
    TypeErasureExample example = new TypeErasureExample();
    example.processStrings(stringBox);
    example.processIntegers(intBox);

    // ===== 8. GENERIC INHERITANCE =====
    System.out.println("\n===== 8. GENERIC INHERITANCE =====");
    Source<String> stringSource = new StringSource();
    System.out.println("[INFO] String source: " + stringSource.getSource());

    Source<Integer> intSource = new IntegerSource();
    System.out.println("[INFO] Integer source: " + intSource.getSource());

    // ===== 9. RECURSIVE TYPE BOUNDS =====
    System.out.println("\n===== 9. RECURSIVE TYPE BOUNDS =====");
    Person[] people = {
        new Person("Alice", 30),
        new Person("Bob", 25),
        new Person("Charlie", 35)
    };

    Person oldest = GenericMethods.findMax(people);
    System.out.println("[INFO] Oldest person: " + oldest);

    // ===== 10. GENERICS LIMITATIONS =====
    System.out.println("\n===== 10. GENERICS LIMITATIONS =====");
    System.out.println("[INFO] 1. Cannot create instances of type parameters: new T() is illegal");
    System.out.println("[INFO] 2. Cannot create arrays of parameterized types: new List<String>[10] is illegal");
    System.out.println("[INFO] 3. Cannot use primitive types as type arguments: Box<int> is illegal");
    System.out.println("[INFO] 4. Cannot use static fields of type parameters: static T field is illegal");
    System.out
        .println("[INFO] 5. Cannot use instanceof with parameterized types: obj instanceof List<String> is illegal");
    System.out.println("[INFO] 6. Cannot create, catch, or throw objects of parameterized types");
    System.out.println(
        "[INFO] 7. Cannot overload methods where the formal parameter types would be erased to the same raw type");
  }
}