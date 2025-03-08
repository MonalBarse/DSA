package com.monal.polymorphism;

import java.util.Scanner;

// Compile-time polymorphism (Method Overloading)
class Calculator {
  // Overloaded methods
  public int add(int a, int b) {
    return a + b;
  }

  public double add(double a, double b) {
    return a + b;
  }

  public int add(int a, int b, int c) {
    return a + b + c;
  }
}

// Runtime polymorphism (Method Overriding)
class Shape {
  public void draw() {
    System.out.println("Drawing a generic shape.");
  }
}

// Interface for demonstrating interface-based polymorphism
interface Drawable {
  void draw();
}

// Circle class extending Shape and implementing Drawable
class Circle extends Shape implements Drawable {
  @Override
  public void draw() {
    System.out.println("Drawing a Circle: ⭕");
  }
}

// Rectangle class extending Shape and implementing Drawable
class Rectangle extends Shape implements Drawable {
  @Override
  public void draw() {
    System.out.println("Drawing a Rectangle: ⧠");
  }
}

// Triangle class extending Shape and implementing Drawable
class Triangle extends Shape implements Drawable {
  @Override
  public void draw() {
    System.out.println("Drawing a Triangle: △");
  }
}

public class Polymorphism {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("=== Demonstrating Polymorphism in Java ===");

    // 1. Compile-time (Static) Polymorphism - Method Overloading
    System.out.println("\n[1] Compile-time Polymorphism: Method Overloading");
    Calculator calc = new Calculator();
    System.out.println("Sum of 5 and 10: " + calc.add(5, 10));
    System.out.println("Sum of 5.5 and 10.5: " + calc.add(5.5, 10.5));
    System.out.println("Sum of 5, 10, and 15: " + calc.add(5, 10, 15));

    // 2. Runtime (Dynamic) Polymorphism - Method Overriding
    System.out.println("\n[2] Runtime Polymorphism: Method Overriding");
    Shape genericShape = new Shape();
    Shape circle = new Circle();
    Shape rectangle = new Rectangle();
    Shape triangle = new Triangle();

    // Calls the overridden methods based on the object type
    genericShape.draw();
    circle.draw();
    rectangle.draw();
    triangle.draw();

    // 3. Polymorphic Array - Using Interface for Multiple Shapes
    System.out.println("\n[3] Interface-based Polymorphism: Using Drawable Interface");
    Drawable[] drawables = { new Circle(), new Rectangle(), new Triangle() };

    // Using polymorphism to call the appropriate draw method
    for (Drawable d : drawables) {
      d.draw();
    }

    // 4. Using the instanceof operator
    System.out.println("\n[4] Using instanceof Operator to Identify Objects");
    for (Drawable d : drawables) {
      if (d instanceof Circle) {
        System.out.println("This is a Circle.");
      } else if (d instanceof Rectangle) {
        System.out.println("This is a Rectangle.");
      } else if (d instanceof Triangle) {
        System.out.println("This is a Triangle.");
      }
    }

    // 5. Interactive Polymorphism Example - User Chooses a Shape to Draw
    System.out.println("\n[5] Interactive Shape Drawing");
    System.out.println("Choose a shape to draw: \n1. Circle \n2. Rectangle \n3. Triangle");
    int choice = scanner.nextInt();

    Shape selectedShape;
    switch (choice) {
      case 1:
        selectedShape = new Circle();
        break;
      case 2:
        selectedShape = new Rectangle();
        break;
      case 3:
        selectedShape = new Triangle();
        break;
      default:
        System.out.println("Invalid choice! Defaulting to a generic shape.");
        selectedShape = new Shape();
    }
    selectedShape.draw(); // Calls the correct overridden method based on the user's choice

    System.out.println("\n=== Polymorphism Demonstration Complete! ===");

    scanner.close();
  }
}
