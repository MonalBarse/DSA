package com.monal.G_comparingObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class demonstrates various ways to compare objects in Java:
 * - Comparable interface
 * - Comparator interface
 * - Natural ordering
 * - Custom ordering
 * - Method references
 * - Multiple field comparison
 * - equals() and hashCode() methods
 */
public class ComparingObjectsExample {

  public static void main(String[] args) {
    System.out.println("\n========== COMPREHENSIVE OBJECT COMPARISON IN JAVA ==========\n");

    // ===== 1. COMPARABLE INTERFACE =====
    System.out.println("\n===== 1. COMPARABLE INTERFACE =====");
    System.out.println("[INFO] Using Comparable for natural ordering");

    List<Person> people = new ArrayList<>();
    people.add(new Person("John", "Smith", 30));
    people.add(new Person("Alice", "Johnson", 25));
    people.add(new Person("Bob", "Williams", 35));
    people.add(new Person("Emma", "Brown", 28));

    System.out.println("\nBefore sorting:");
    printList(people);

    // Sort using natural ordering (Person implements Comparable)
    Collections.sort(people);

    System.out.println("\nAfter sorting by age (natural ordering):");
    printList(people);

    // ===== 2. COMPARATOR INTERFACE =====
    System.out.println("\n===== 2. COMPARATOR INTERFACE =====");
    System.out.println("[INFO] Using Comparator for custom ordering");

    // Sort by first name using Comparator
    Collections.sort(people, new FirstNameComparator());

    System.out.println("\nAfter sorting by first name (using FirstNameComparator):");
    printList(people);

    // Sort by last name using anonymous Comparator
    Collections.sort(people, new Comparator<Person>() {
      @Override
      public int compare(Person p1, Person p2) {
        return p1.getLastName().compareTo(p2.getLastName());
      }
    });

    System.out.println("\nAfter sorting by last name (using anonymous Comparator):");
    printList(people);

    // ===== 3. COMPARATOR WITH LAMBDA EXPRESSIONS =====
    System.out.println("\n===== 3. COMPARATOR WITH LAMBDA EXPRESSIONS =====");
    System.out.println("[INFO] Using lambda expressions for Comparators");

    // Sort by age using lambda
    Collections.sort(people, (p1, p2) -> Integer.compare(p1.getAge(), p2.getAge()));

    System.out.println("\nAfter sorting by age (using lambda):");
    printList(people);

    // ===== 4. COMPARATOR METHODS & CHAINING =====
    System.out.println("\n===== 4. COMPARATOR METHODS & CHAINING =====");
    System.out.println("[INFO] Using Comparator utility methods and chaining");

    // Sort by first name then last name using comparing and thenComparing
    Collections.sort(people, Comparator.comparing(Person::getFirstName)
        .thenComparing(Person::getLastName));

    System.out.println("\nAfter sorting by first name then last name (using Comparator methods):");
    printList(people);

    // Sort by age in reverse order
    Collections.sort(people, Comparator.comparing(Person::getAge).reversed());

    System.out.println("\nAfter sorting by age in reverse order:");
    printList(people);

    // ===== 5. COMPARABLE IN COLLECTIONS =====
    System.out.println("\n===== 5. COMPARABLE IN COLLECTIONS =====");
    System.out.println("[INFO] Using Comparable with TreeSet for automatic sorting");

    Set<Person> personSet = new TreeSet<>();
    personSet.addAll(people);

    System.out.println("\nPeople in TreeSet (automatically sorted by natural ordering):");
    for (Person p : personSet) {
      System.out.println(p);
    }

    // ===== 6. COMPARATOR IN COLLECTIONS =====
    System.out.println("\n===== 6. COMPARATOR IN COLLECTIONS =====");
    System.out.println("[INFO] Using Comparator with TreeSet for custom sorting");

    Set<Person> personSetByName = new TreeSet<>(Comparator.comparing(Person::getFirstName));
    personSetByName.addAll(people);

    System.out.println("\nPeople in TreeSet (sorted by first name):");
    for (Person p : personSetByName) {
      System.out.println(p);
    }

    // ===== 7. COMPARING PRIMITIVES =====
    System.out.println("\n===== 7. COMPARING PRIMITIVES =====");
    System.out.println("[INFO] Using Integer.compare and Double.compare for primitives");

    int compare1 = Integer.compare(10, 20);
    System.out.println("Integer.compare(10, 20): " + compare1);

    int compare2 = Double.compare(3.14, 2.71);
    System.out.println("Double.compare(3.14, 2.71): " + compare2);

    // ===== 8. NULL HANDLING =====
    System.out.println("\n===== 8. NULL HANDLING =====");
    System.out.println("[INFO] Using Comparator.nullsFirst and nullsLast");

    List<Person> peopleWithNull = new ArrayList<>(people);
    peopleWithNull.add(null);

    // Handle nulls with nullsFirst
    Comparator<Person> nullSafeComparator = Comparator.nullsFirst(Comparator.naturalOrder());
    Collections.sort(peopleWithNull, nullSafeComparator);

    System.out.println("\nAfter sorting with nulls first:");
    for (Person p : peopleWithNull) {
      System.out.println(p);
    }

    // ===== 9. EQUALS AND HASHCODE =====
    System.out.println("\n===== 9. EQUALS AND HASHCODE =====");
    System.out.println("[INFO] Proper implementation of equals() and hashCode()");

    Person person1 = new Person("John", "Smith", 30);
    Person person2 = new Person("John", "Smith", 30);
    Person person3 = new Person("Jane", "Smith", 30);

    System.out.println("person1.equals(person2): " + person1.equals(person2));
    System.out.println("person1.equals(person3): " + person1.equals(person3));
    System.out.println("person1.hashCode(): " + person1.hashCode());
    System.out.println("person2.hashCode(): " + person2.hashCode());

    // ===== 10. ARRAYS COMPARISON =====
    System.out.println("\n===== 10. ARRAYS COMPARISON =====");
    System.out.println("[INFO] Using Arrays.equals and Arrays.compare");

    int[] array1 = { 1, 2, 3 };
    int[] array2 = { 1, 2, 3 };
    int[] array3 = { 1, 2, 4 };

    System.out.println("Arrays.equals(array1, array2): " + Arrays.equals(array1, array2));
    System.out.println("Arrays.equals(array1, array3): " + Arrays.equals(array1, array3));
    System.out.println("Arrays.compare(array1, array3): " + Arrays.compare(array1, array3));

    // ===== 11. DEEP COMPARISON =====
    System.out.println("\n===== 11. DEEP COMPARISON =====");
    System.out.println("[INFO] Using Arrays.deepEquals for multi-dimensional arrays");

    int[][] matrix1 = { { 1, 2 }, { 3, 4 } };
    int[][] matrix2 = { { 1, 2 }, { 3, 4 } };
    int[][] matrix3 = { { 1, 2 }, { 3, 5 } };

    System.out.println("Arrays.equals(matrix1, matrix2): " + Arrays.equals(matrix1, matrix2)); // Will be false!
    System.out.println("Arrays.deepEquals(matrix1, matrix2): " + Arrays.deepEquals(matrix1, matrix2));
    System.out.println("Arrays.deepEquals(matrix1, matrix3): " + Arrays.deepEquals(matrix1, matrix3));

    // ===== 12. USING OBJECTS CLASS FOR COMPARISON =====
    System.out.println("\n===== 12. USING OBJECTS CLASS FOR COMPARISON =====");
    System.out.println("[INFO] Using Objects.equals and Objects.hash");

    String s1 = "Hello";
    String s2 = "Hello";
    String s3 = null;

    System.out.println("Objects.equals(s1, s2): " + Objects.equals(s1, s2));
    System.out.println("Objects.equals(s1, s3): " + Objects.equals(s1, s3));
    System.out.println("Objects.equals(s3, s3): " + Objects.equals(s3, s3));

    // ===== 13. COMPARETO VS EQUALS =====
    System.out.println("\n===== 13. COMPARETO VS EQUALS =====");
    System.out.println("[INFO] Understanding difference between compareTo() and equals()");

    Person person4 = new Person("John", "Smith", 31); // Same name but different age

    System.out.println("person1.equals(person4): " + person1.equals(person4)); // false because ages differ
    System.out.println("person1.compareTo(person4): " + person1.compareTo(person4)); // -1 because 30 < 31

    // Using PersonNameOnly for equality based on name but comparison based on age
    PersonNameOnly pno1 = new PersonNameOnly("John", "Smith", 30);
    PersonNameOnly pno2 = new PersonNameOnly("John", "Smith", 31);

    System.out.println("pno1.equals(pno2): " + pno1.equals(pno2)); // true because names match
    System.out.println("pno1.compareTo(pno2): " + pno1.compareTo(pno2)); // -1 because 30 < 31
  }

  private static void printList(List<Person> list) {
    for (Person p : list) {
      System.out.println(p);
    }
  }
}

/**
 * Person class implements Comparable for natural ordering by age
 */
class Person implements Comparable<Person> {
  private String firstName;
  private String lastName;
  private int age;

  public Person(String firstName, String lastName, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public int getAge() {
    return age;
  }

  @Override
  public String toString() {
    return firstName + " " + lastName + " (" + age + ")";
  }

  // Natural ordering based on age
  @Override
  public int compareTo(Person other) {
    return Integer.compare(this.age, other.age);
  }

  // Proper equals method
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    Person other = (Person) obj;

    return age == other.age &&
        Objects.equals(firstName, other.firstName) &&
        Objects.equals(lastName, other.lastName);
  }

  // Proper hashCode method (always override with equals)
  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, age);
  }
}

/**
 * A custom comparator for sorting Person objects by first name
 */
class FirstNameComparator implements Comparator<Person> {
  @Override
  public int compare(Person p1, Person p2) {
    return p1.getFirstName().compareTo(p2.getFirstName());
  }
}

/**
 * A class that demonstrates how equals and compareTo can have different
 * behavior
 * This class considers two persons equal if they have the same name,
 * but sorts them by age (natural ordering)
 */
class PersonNameOnly implements Comparable<PersonNameOnly> {
  private String firstName;
  private String lastName;
  private int age;

  public PersonNameOnly(String firstName, String lastName, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public int getAge() {
    return age;
  }

  @Override
  public String toString() {
    return firstName + " " + lastName + " (" + age + ")";
  }

  // Natural ordering based on age
  @Override
  public int compareTo(PersonNameOnly other) {
    return Integer.compare(this.age, other.age);
  }

  // Equality based only on first and last name
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;

    PersonNameOnly other = (PersonNameOnly) obj;

    return Objects.equals(firstName, other.firstName) &&
        Objects.equals(lastName, other.lastName);
    // Note: age is intentionally excluded for demonstration purposes
  }

  // HashCode consistent with equals
  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName);
    // Note: age is intentionally excluded to match equals method
  }
}