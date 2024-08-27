package com.monal;

import java.util.Scanner;

public class Inheritance {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Dog dog = new Dog("Buddy", 10);
    Cat cat = new Cat("Whiskers", 10);
    Bird bird = new Bird("Tweety", 10);

    System.out.println("Welcome to the Pet Adventure Game!");
    System.out.println(
        "You have three pets: a dog named Buddy, a cat named Whiskers, and a bird named Tweety.");
    System.out.println("Choose their actions and keep them happy and energetic!\n");

    boolean playing = true;
    while (playing) {
      System.out.println("\nChoose an action:");
      System.out.println("1. Feed Buddy");
      System.out.println("2. Feed Whiskers");
      System.out.println("3. Feed Tweety");
      System.out.println("4. Buddy fetches the ball");
      System.out.println("5. Whiskers plays with the laser");
      System.out.println("6. Tweety flies");
      System.out.println("7. Make Buddy bark");
      System.out.println("8. Make Whiskers meow");
      System.out.println("9. Make Tweety chirp");
      System.out.println("10. Buddy goes for a swim");
      System.out.println("11. Check Buddy's energy");
      System.out.println("12. Check Whiskers' energy");
      System.out.println("13. Check Tweety's energy");
      System.out.println("14. Exit game");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline

      switch (choice) {
        case 1:
          System.out.print("Enter the food for Buddy: ");
          String dogFood = scanner.nextLine();
          dog.eat(dogFood);
          break;
        case 2:
          System.out.print("Enter the food for Whiskers: ");
          String catFood = scanner.nextLine();
          cat.eat(catFood);
          break;
        case 3:
          System.out.print("Enter the food for Tweety: ");
          String birdFood = scanner.nextLine();
          bird.eat(birdFood);
          break;
        case 4:
          dog.fetch();
          break;
        case 5:
          cat.playWithLaser();
          break;
        case 6:
          bird.fly();
          break;
        case 7:
          dog.makeSound();
          break;
        case 8:
          cat.makeSound();
          break;
        case 9:
          bird.makeSound();
          break;
        case 10:
          dog.swim();
          break;
        case 11:
          dog.displayEnergy();
          break;
        case 12:
          cat.displayEnergy();
          break;
        case 13:
          bird.displayEnergy();
          break;
        case 14:
          System.out.println("Thanks for playing! Goodbye!");
          playing = false;
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
    scanner.close();
  }

  static class Animal {
    String name;
    int energy;

    public Animal(String name, int energy) {
      this.name = name;
      this.energy = energy;
    }

    public void eat(String food) {
      energy += 10;
      System.out.println(name + " devours the " + food + "! Energy increased to " + energy + ".");
    }

    public void makeSound() {
      System.out.println(name + " makes a sound.");
    }

    public void displayEnergy() {
      System.out.println(name + "'s current energy: " + energy);
    }
  }

  static class Dog extends Animal implements Swimmer {
    public Dog(String name, int energy) {
      super(name, energy);
    }

    @Override
    public void makeSound() {
      System.out.println(name + " barks loudly! Woof! Woof!");
    }

    public void fetch() {
      if (energy >= 5) {
        energy -= 5;
        System.out.println(name + " fetches the ball happily! Energy decreased to " + energy + ".");
      } else {
        System.out.println(name + " is too tired to fetch. Need more food!");
      }
    }

    @Override
    public void swim() {
      if (energy >= 6) {
        energy -= 6;
        System.out.println(name + " splashes in the water! Energy decreased to " + energy + ".");
      } else {
        System.out.println(name + " is too tired to swim. Need more food!");
      }
    }
  }

  static class Cat extends Animal {
    public Cat(String name, int energy) {
      super(name, energy);
    }

    @Override
    public void makeSound() {
      System.out.println(name + " meows softly. Purr...");
    }

    public void playWithLaser() {
      if (energy >= 3) {
        energy -= 3;
        System.out.println(name + " chases the laser beam! Energy decreased to " + energy + ".");
      } else {
        System.out.println(name + " is too tired to play with the laser. Need more food!");
      }
    }
  }

  static class Bird extends Animal {
    public Bird(String name, int energy) {
      super(name, energy);
    }

    @Override
    public void makeSound() {
      System.out.println(name + " chirps melodiously!");
    }

    public void fly() {
      if (energy >= 7) {
        energy -= 7;
        System.out.println(name + " soars through the sky! Energy decreased to " + energy + ".");
      } else {
        System.out.println(name + " is too tired to fly. Need more food!");
      }
    }
  }

  interface Swimmer {
    void swim();
  }
}
  // Important points about inheritance:
  // 1. Java supports single inheritance for classes (a class can only extend one class)
  // 2. Java supports multiple inheritance of types through interfaces
  // 3. The 'extends' keyword is used for class inheritance, 'implements' for interface
  // implementation
  // 4. Inheritance promotes code reuse and establishes an "is-a" relationship
  // 5. Method overriding allows a subclass to provide a specific implementation of a method from
  // its
  // superclass
  // 6. The 'super' keyword is used to call the superclass constructor or methods
  // 7. Inheritance is a fundamental concept in object-oriented programming, enabling polymorphism
