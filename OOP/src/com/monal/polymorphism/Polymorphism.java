package com.monal;

public class Polymorphism {
  public static void main(String[] args) {
    System.out.println("Demonstrating Polymorphism in Java");

    // Compile-time (static) polymorphism
    Calculator calc = new Calculator();
    System.out.println("Method Overloading:");
    System.out.println("Sum of 5 and 10: " + calc.add(5, 10));
    System.out.println("Sum of 5.5 and 10.5: " + calc.add(5.5, 10.5));
    System.out.println("Sum of 5, 10, and 15: " + calc.add(5, 10, 15));

    // Runtime (dynamic) polymorphism
    System.out.println("\nMethod Overriding:");
    Shape shape = new Shape();
    Shape circle = new Circle();
    Shape rectangle = new Rectangle();

    shape.draw(); // Calls Shape's draw method
    circle.draw(); // Calls Circle's draw method
    rectangle.draw(); // Calls Rectangle's draw method

    // Polymorphic array
    System.out.println("\nPolymorphic Array:");
    Drawable[] drawables = {new Circle(), new Rectangle(), new Triangle()};
    for (Drawable d : drawables) {
      d.draw(); // Calls the draw method of each shape through the Drawable interface
    }

    // Instanceof operator
    System.out.println("\nInstanceof Operator:");
    for (Drawable d : drawables) {
      if (d instanceof Circle) {
        System.out.println("This is a Circle");
      } else if (d instanceof Rectangle) {
        System.out.println("This is a Rectangle");
      } else if (d instanceof Triangle) {
        System.out.println("This is a Triangle");
      }
    }
  }
}

// Compile-time polymorphism (Method Overloading)
class Calculator {
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
    System.out.println("Drawing a shape");
  }
}

// Circle class implementing Drawable
class Circle extends Shape implements Drawable {
  @Override
  public void draw() {
    System.out.println("Drawing a circle");
  }
}

// Rectangle class implementing Drawable
class Rectangle extends Shape implements Drawable {
  @Override
  public void draw() {
    System.out.println("Drawing a rectangle");
  }
}

// Triangle class implementing Drawable
class Triangle extends Shape implements Drawable {
  @Override
  public void draw() {
    System.out.println("Drawing a triangle");
  }
}

// Interface for demonstrating interface polymorphism
interface Drawable {
  void draw();
}
// Note: Circle, Rectangle, and Triangle classes should also implement Drawable
// But Java doesn't support multiple class inheritance, so we're assuming they implement it
