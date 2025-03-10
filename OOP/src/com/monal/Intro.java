package com.monal;

public class Intro {

  public static void main(String[] args) {
    System.out.println("Intro to Object-Oriented Programming in Java");

    // A class is a blueprint for creating objects. It defines properties (fields)
    // and behaviors
    // (methods) that the objects created from the class will have. Classes do not
    // consume memory
    // until an object (instance) of the class is created.

    /*
     * class ClassName {
     * Fields (attributes)
     * Methods (behaviors)
     * }
     */

    // An object is an instance of a class. When a class is defined, no memory is
    // allocated until an
    // object is created.
    // In Java, objects are created using dynamic memory allocation, which means
    // memory is allocated
    // at runtime when the object is created using the new keyword. This allows for
    // flexibility as
    // memory is allocated only when needed, and the size can be determined at
    // runtime.
    Dog myDog = new Dog("Woofie"); // Memory is allocated for myDog at runtime
    // Dog myDog2 = new Dog("Max", 5);
    myDog.bark(); // Output: Woofie is barking
  }

  static class Dog {
    // Fields
    String name;
    String breed;
    int age;

    // Method
    void bark() {
      System.out.println(name + " is barking");
    }

    // A constructor is a special method used to initialize objects. It is called
    // when an object of
    // a class is created. It has the same name as the class and no return type.
    // Constructors can be overloaded, allowing different ways to initialize an
    // object.
    Dog(String name) {
      this.name = name; // 'this.name' refers to the instance variable, 'name' is the parameter
    }

    // Constructor overloading allows a class to have more than one constructor with
    // different
    // parameter lists.
    // Constructor with two parameters
    Dog(String name, int age) {
      this.name = name;
      this.age = age;
    }
  }

  /*
   * final Keyword
   * The final keyword can be used with classes, methods, and variables.
   * - final class: Cannot be subclassed.
   * - final method: Cannot be overridden by subclasses.
   * - final variable: Its value cannot be changed once assigned (constant).
   * Example:
   * final int MAX_AGE = 10;
   */
  final int MAX_AGE = 10;

  /*
   * Garbage Collection
   * Garbage Collection in Java is the process of automatically freeing memory by
   * destroying objects
   * that are no longer in use. This helps in preventing memory leaks and
   * optimizes memory usage.
   * The Java Virtual Machine (JVM) handles garbage collection automatically.
   * While you can suggest garbage collection by calling System.gc(), it's not
   * guaranteed to happen immediately.
   */
}
