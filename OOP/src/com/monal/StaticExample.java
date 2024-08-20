package com.monal;

public class StaticExample {

  // Static variable: Shared by all instances of the class
  // Initialized when the class is loaded into memory
  static int instanceCount = 0;

  // Non-static (instance) variable: Unique to each instance of the class
  String name;

  public static void main(String[] args) {
    // Accessing a static method without creating an instance
    StaticExample.displayInstanceCount();

    // Creating instances of the StaticAdvancedExample class
    StaticExample obj1 = new StaticExample("Object1");
    StaticExample obj2 = new StaticExample("Object2");

    // Accessing instance methods
    obj1.greet();
    obj2.greet();

    // Accessing a static method again
    StaticExample.displayInstanceCount();

    // Working with the static inner class
    StaticInnerClass staticInner = new StaticInnerClass();
    staticInner.showMessage();

    // Working with the non-static inner class
    StaticExample.NonStaticInnerClass nonStaticInner = obj1.new NonStaticInnerClass();
    nonStaticInner.showMessage();

    // Working with the Singleton class
    Singleton singleton1 = Singleton.getInstance();
    Singleton singleton2 = Singleton.getInstance();
    System.out.println("Are both singleton instances the same? " + (singleton1 == singleton2));
  }

  // Static block: Runs once when the class is loaded, before any instance is created
  static {
    System.out.println("Static block executed. Class StaticAdvancedExample is loaded.");
    // Initialize the static variable if needed
    instanceCount = 0;
  }

  // Constructor: Called when a new object is created
  StaticExample(String name) {
    this.name = name; // 'this' keyword refers to the current instance's variable
    instanceCount++; // Increment the shared static variable
    System.out.println(name + " created. Instance count: " + instanceCount);
  }

  // Static method: Can be called without creating an instance of the class
  static void displayInstanceCount() {
    System.out.println("Total instances created: " + instanceCount);

    // Trying to access non-static variables or methods directly will cause an error
    // System.out.println(name); // ERROR: Cannot access non-static variable 'name' from a static
    // context

    // To access non-static variables, you need an instance of the class
    StaticExample example = new StaticExample("Temporary");
    System.out.println("Accessing non-static variable from static method: " + example.name);
  }

  // Non-static method: Requires an instance of the class to be called
  void greet() {
    System.out.println("Hello, " + name + "!");
  }

  // Static inner class: Can be instantiated without an outer class instance
  static class StaticInnerClass {
    void showMessage() {
      System.out.println("This is a static inner class.");
    }
  }

  // Non-static inner class: Requires an instance of the outer class to be instantiated
  class NonStaticInnerClass {
    void showMessage() {
      System.out.println("This is a non-static inner class. Outer class name: " + name);
    }
  }

  // Singleton class: Ensures only one instance of the class is created
  static class Singleton {
    // Private static variable of the only instance of the class
    private static Singleton instance;

    // Private constructor to prevent instantiation from other classes
    private Singleton() {
      System.out.println("Singleton instance created.");
    }

    // Static method to provide a global access point to the instance
    public static Singleton getInstance() {
      if (instance == null) {
        instance = new Singleton();
      }
      return instance;
    }
  }
}
