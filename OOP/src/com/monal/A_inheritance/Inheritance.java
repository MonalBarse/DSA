/*
 * Inheritance -
 *  - Inheritance is a mechanism in which one class acquires the properties and behavior of another class.
 *  - It represents an "is-a" relationship between classes, i.e a subclass is a type of superclass.
 */

// Important points about inheritance:

// 1. Java supports single inheritance for classes (a class can only extend one class)
// 2. Java supports multiple inheritance of types through interfaces
// 3. The 'extends' keyword is used for class inheritance
//    'implements' for interface implementation
// 4. Inheritance promotes code reuse and establishes an "is-a" relationship
// 5. Method overriding allows a subclass to provide a specific
//    implementation of a method from its superclass
// 6. The 'super' keyword is used to call the superclass constructor or methods
// 7. Inheritance is a fundamental concept in object-oriented programming, enabling polymorphism

package com.monal.A_inheritance;

import java.util.Scanner;

public class Inheritance {

  // Abstract class Animal - it cannot be instantiated, only extended
  abstract static class Animal {
    protected String name; // Protected access - allows subclasses to access directly
    protected int energy;

    // Constructor - initializes the name and energy of the animal
    public Animal(String name, int energy) {
      this.name = name;
      this.energy = energy;
    }

    // Abstract method - It is a method without a body, the definition is ought to
    // be provided by the subclass
    // If not provided (and the class is not abstract), it will result in a
    // compilation error
    public abstract void makeSound();

    // Concrete method - has a body and is inherited by all subclasses
    public void eat(String food) {
      energy += 10;
      System.out.println(name + " devours the " + food + "! Energy increased to " + energy + ".");
    }

    public void displayEnergy() {
      System.out.println(name + "'s current energy: " + energy);
    }
  }

  // Swimmer interface - defines the swim method, if a class implements Swimmer.
  interface Swimmer {
    void swim();
  }

  // Dog class - this extends the Animal class which means it inherits all the
  // properties and methods of the Animal class
  // This is Inheritance (Single Inheritance)

  // It 'implements' the Swimmer interface which means it must provide an
  // implementation for the swim method
  // This is Interface Implementation (Multiple Inheritance)

  static class Dog extends Animal implements Swimmer {
    // Constructor
    public Dog(String name, int energy) {
      super(name, energy);
    }

    // Method overriding - provides a specific implementation of the makeSound
    // method from the superclass
    // This is Polymorphism
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

  // Cat class - Inherits from Animal
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

  // Bird class - Inherits from Animal
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

  // Fish class - Hierarchical inheritance, also implements Swimmer
  static class Fish extends Animal implements Swimmer {
    public Fish(String name, int energy) {
      super(name, energy);
    }

    @Override
    public void makeSound() {
      System.out.println(name + " remains silent... but bubbles appear!");
    }

    @Override
    public void swim() {
      if (energy >= 5) {
        energy -= 5;
        System.out.println(name + " glides through the water effortlessly! Energy decreased to " + energy + ".");
      } else {
        System.out.println(name + " is too tired to swim. Need more food!");
      }
    }
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Asking the user for pet names
    System.out.print("Enter a name for your Dog: ");
    String dogName = scanner.nextLine();
    System.out.print("Enter a name for your Cat: ");
    String catName = scanner.nextLine();
    System.out.print("Enter a name for your Bird: ");
    String birdName = scanner.nextLine();
    System.out.print("Enter a name for your Fish: ");
    String fishName = scanner.nextLine();

    // Creating pet objects with user-defined names
    Dog dog = new Dog(dogName, 10);
    Cat cat = new Cat(catName, 10);
    Bird bird = new Bird(birdName, 10);
    Fish fish = new Fish(fishName, 10);

    System.out.println("\nWelcome to the Enhanced Pet Adventure Game!");
    System.out.println(
        "Your pets are: " + dog.name + " (Dog), " + cat.name + " (Cat), " + bird.name + " (Bird), and " + fish.name
            + " (Fish).");
    System.out.println("Choose their actions and keep them happy and energetic!\n");

    boolean playing = true;
    while (playing) {
      System.out.println("\nChoose an action:");
      System.out.println("1. Feed " + dog.name);
      System.out.println("2. Feed " + cat.name);
      System.out.println("3. Feed " + bird.name);
      System.out.println("4. Feed " + fish.name);
      System.out.println("5. " + dog.name + " fetches the ball");
      System.out.println("6. " + cat.name + " plays with the laser");
      System.out.println("7. " + bird.name + " flies");
      System.out.println("8. Make " + dog.name + " bark");
      System.out.println("9. Make " + cat.name + " meow");
      System.out.println("10. Make " + bird.name + " chirp");
      System.out.println("11. " + dog.name + " goes for a swim");
      System.out.println("12. " + fish.name + " swims");
      System.out.println("13. Check " + dog.name + "'s energy");
      System.out.println("14. Check " + cat.name + "'s energy");
      System.out.println("15. Check " + bird.name + "'s energy");
      System.out.println("16. Check " + fish.name + "'s energy");
      System.out.println("17. Check if " + fish.name + " is an Animal (instanceof)");
      System.out.println("18. Exit game");

      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline

      switch (choice) {
        case 1:
          System.out.print("Enter the food for " + dog.name + ": ");
          dog.eat(scanner.nextLine());
          break;
        case 2:
          System.out.print("Enter the food for " + cat.name + ": ");
          cat.eat(scanner.nextLine());
          break;
        case 3:
          System.out.print("Enter the food for " + bird.name + ": ");
          bird.eat(scanner.nextLine());
          break;
        case 4:
          System.out.print("Enter the food for " + fish.name + ": ");
          fish.eat(scanner.nextLine());
          break;
        case 5:
          dog.fetch();
          break;
        case 6:
          cat.playWithLaser();
          break;
        case 7:
          bird.fly();
          break;
        case 8:
          dog.makeSound();
          break;
        case 9:
          cat.makeSound();
          break;
        case 10:
          bird.makeSound();
          break;
        case 11:
          dog.swim();
          break;
        case 12:
          fish.swim();
          break;
        case 13:
          dog.displayEnergy();
          break;
        case 14:
          cat.displayEnergy();
          break;
        case 15:
          bird.displayEnergy();
          break;
        case 16:
          fish.displayEnergy();
          break;
        case 17:
          if (fish instanceof Animal) {
            System.out.println(fish.name + " is an Animal!");
          } else {
            System.out.println(fish.name + " is NOT an Animal!");
          }
          break;
        case 18:
          System.out.println("Thanks for playing! Goodbye!");
          playing = false;
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
    scanner.close();
  }
}

// import java.util.Scanner;

// public class Inheritance {

// // Animal class - defines the common properties and methods of all animals
// static class Animal {
// String name;
// int energy;

// public Animal(String name, int energy) {
// this.name = name;
// this.energy = energy;
// }

// public void eat(String food) {
// energy += 10;
// System.out.println(name + " devours the " + food + "! Energy increased to " +
// energy + ".");
// }

// public void makeSound() {
// System.out.println(name + " makes a sound.");
// }

// public void displayEnergy() {
// System.out.println(name + "'s current energy: " + energy);
// }
// }

// // Swimmer interface - defines the swim method
// interface Swimmer {
// void swim();
// }

// // Dog class - this extends the Animal class which means it inherits all the
// properties and methods of the Animal class
// // This is Inheritance (Single Inheritance)

// // It 'implements' the Swimmer interface which means it must provide an
// implementation for the swim method
// // This is Interface Implementation (Multiple Inheritance)
// static class Dog extends Animal implements Swimmer {
// public Dog(String name, int energy) {
// super(name, energy);
// }

// // Method overriding - provides a specific implementation of the makeSound
// method from the superclass
// // This is Polymorphism
// @Override
// public void makeSound() {
// System.out.println(name + " barks loudly! Woof! Woof!");
// }

// public void fetch() {
// if (energy >= 5) {
// energy -= 5;
// System.out.println(name + " fetches the ball happily! Energy decreased to " +
// energy + ".");
// } else {
// System.out.println(name + " is too tired to fetch. Need more food!");
// }
// }

// // We did not provide a implementation in Animal class for swim, but still we
// are using @Override annotation
// // because if we don't use Override annotation, it will be considered as a
// new method in Dog class
// // and it will not be considered as an implementation of the swim method from
// the Swimmer interface
// @Override
// public void swim() {
// if (energy >= 6) {
// energy -= 6;
// System.out.println(name + " splashes in the water! Energy decreased to " +
// energy + ".");
// } else {
// System.out.println(name + " is too tired to swim. Need more food!");
// }
// }
// }

// static class Cat extends Animal {
// public Cat(String name, int energy) {
// super(name, energy);
// }

// @Override
// public void makeSound() {
// System.out.println(name + " meows softly. Purr...");
// }

// public void playWithLaser() {
// if (energy >= 3) {
// energy -= 3;
// System.out.println(name + " chases the laser beam! Energy decreased to " +
// energy + ".");
// } else {
// System.out.println(name + " is too tired to play with the laser. Need more
// food!");
// }
// }
// }

// static class Bird extends Animal {
// public Bird(String name, int energy) {
// super(name, energy);
// }

// @Override
// public void makeSound() {
// System.out.println(name + " chirps melodiously!");
// }

// public void fly() {
// if (energy >= 7) {
// energy -= 7;
// System.out.println(name + " soars through the sky! Energy decreased to " +
// energy + ".");
// } else {
// System.out.println(name + " is too tired to fly. Need more food!");
// }
// }
// }

// public static void main(String[] args) {
// Scanner scanner = new Scanner(System.in);
// Dog dog = new Dog("Buddy", 10);
// Cat cat = new Cat("Whiskers", 10);
// Bird bird = new Bird("Tweety", 10);

// System.out.println("Welcome to the Pet Adventure Game!");
// System.out.println(
// "You have three pets: a dog named Buddy, a cat named Whiskers, and a bird
// named Tweety.");
// System.out.println("Choose their actions and keep them happy and
// energetic!\n");

// boolean playing = true;
// while (playing) {
// System.out.println("\nChoose an action:");
// System.out.println("1. Feed Buddy");
// System.out.println("2. Feed Whiskers");
// System.out.println("3. Feed Tweety");
// System.out.println("4. Buddy fetches the ball");
// System.out.println("5. Whiskers plays with the laser");
// System.out.println("6. Tweety flies");
// System.out.println("7. Make Buddy bark");
// System.out.println("8. Make Whiskers meow");
// System.out.println("9. Make Tweety chirp");
// System.out.println("10. Buddy goes for a swim");
// System.out.println("11. Check Buddy's energy");
// System.out.println("12. Check Whiskers' energy");
// System.out.println("13. Check Tweety's energy");
// System.out.println("14. Exit game");

// int choice = scanner.nextInt();
// scanner.nextLine(); // Consume newline

// switch (choice) {
// case 1:
// System.out.print("Enter the food for Buddy: ");
// String dogFood = scanner.nextLine();
// dog.eat(dogFood);
// break;
// case 2:
// System.out.print("Enter the food for Whiskers: ");
// String catFood = scanner.nextLine();
// cat.eat(catFood);
// break;
// case 3:
// System.out.print("Enter the food for Tweety: ");
// String birdFood = scanner.nextLine();
// bird.eat(birdFood);
// break;
// case 4:
// dog.fetch();
// break;
// case 5:
// cat.playWithLaser();
// break;
// case 6:
// bird.fly();
// break;
// case 7:
// dog.makeSound();
// break;
// case 8:
// cat.makeSound();
// break;
// case 9:
// bird.makeSound();
// break;
// case 10:
// dog.swim();
// break;
// case 11:
// dog.displayEnergy();
// break;
// case 12:
// cat.displayEnergy();
// break;
// case 13:
// bird.displayEnergy();
// break;
// case 14:
// System.out.println("Thanks for playing! Goodbye!");
// playing = false;
// break;
// default:
// System.out.println("Invalid choice. Please try again.");
// }
// }
// scanner.close();
// }
// }