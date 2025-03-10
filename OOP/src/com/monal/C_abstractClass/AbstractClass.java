package com.monal.C_abstractClass;

// Introduction: Demonstrating Abstract Classes and Interfaces in Java

// Abstract Class Example -
// Abstract classes are used to provide a common definition of a base class that multiple derived classes can share.
// They are different from interfaces as they can have concrete methods along with abstract methods.
// Abstract classes cannot be instantiated, but they can be subclassed.
// Interfaces are different from abstract classes as they provide a way to achieve multiple inheritance in Java.
// Interface in detail in next section.

abstract class Animal {

    public Animal(String species) {
        System.out.println("This is a " + species + "\n[INFO] This is from abstract class constructor.");
    }

    // Abstract method (to be implemented by subclasses
    abstract void makeSound();

    // Concrete method
    void sleep() {
        System.out.println("\n[INFO] This method is implemented in the abstract class.");
        System.out.println("This animal is sleeping...\n");
    }

    // Static method in an abstract class (valid, but cannot be abstract)
    static void info() {
        System.out.println("\n[INFO] Static method inside an abstract class. Can be called without an object.");
        System.out.println("Animals have different sounds.\n");
    }
}

// Concrete subclass implementing abstract method
class Dog extends Animal {

    // Constructor

    public Dog() {
        // Calling constructor of superclass
        super("Dog");
        System.out.println("\n[INFO] Dog class extending Animal abstract class.");
    }

    @Override
    void makeSound() {
        System.out.println("\n[INFO] Overriding abstract method in subclass Dog.");
        System.out.println("Dog: Woof Woof!\n");
    }
}

class Cat extends Animal {
    public Cat() {
        super("Cat");
        System.out.println("\n[INFO] Cat class extending Animal abstract class.");
    }

    @Override
    void makeSound() {
        System.out.println("\n[INFO] Overriding abstract method in subclass Cat.");
        System.out.println("Cat: Meow Meow!\n");
    }
}

// Interfaces Example
interface Vehicle {
    void start(); // Abstract method (implicitly public and abstract)
}

// Implementing Interface in different classes
class Car implements Vehicle {
    public Car() {
        System.out.println("\n[INFO] Car class implementing Vehicle interface.");
    }

    @Override
    public void start() {
        System.out.println("\n[INFO] Implementing start() method in Car class.");
        System.out.println("Car is starting with a key ignition.\n");
    }
}

class Bike implements Vehicle {
    @Override
    public void start() {
        System.out.println("\n[INFO] Implementing start() method in Bike class.");
        System.out.println("Bike is starting with a self-start button.\n");
    }
}

// Demonstrating multiple inheritance using interfaces
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

class Duck implements Flyable, Swimmable {

    public Duck(String name) {
        System.out.println("\n[INFO] Duck class implementing multiple interfaces: Flyable and Swimmable.");
        System.out.println("Duck name: " + name);
    }

    @Override
    public void fly() {
        System.out.println("\n[INFO] Implementing fly() method from Flyable interface.");
        System.out.println("Duck is flying with flapping wings.\n");
    }

    @Override
    public void swim() {
        System.out.println("\n[INFO] Implementing swim() method from Swimmable interface.");
        System.out.println("Duck is swimming gracefully in water.\n");
    }
}

// Main class demonstrating all concepts step by step
public class AbstractClass {
    public static void main(String[] args) {
        System.out.println("\n===== Abstract Class Demonstration =====");
        Dog dog = new Dog();
        Cat cat = new Cat();
        dog.makeSound();
        cat.makeSound();
        dog.sleep();
        Animal.info(); // Calling static method from abstract class

        System.out.println("\n===== Interface Demonstration =====");
        Vehicle car = new Car();
        Vehicle bike = new Bike();
        car.start();
        bike.start();

        System.out.println("\n===== Multiple Inheritance using Interfaces =====");
        Duck duck = new Duck("Donald");
        duck.fly();
        duck.swim();
    }
}
