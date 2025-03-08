package com.monal.access_controls;

import java.util.Scanner;

// Class demonstrating access modifiers
class ModifierExample {
    private int privateVar = 10; // Only accessible within this class
    int defaultVar = 20; // Accessible within the same package
    protected int protectedVar = 30; // Accessible within the same package & subclasses
    public int publicVar = 40; // Accessible from anywhere

    public void display() {
        System.out.println(privateVar);
        System.out.println("Default Variable: " + defaultVar);
        System.out.println("Protected Variable: " + protectedVar);
        System.out.println("Public Variable: " + publicVar);
    }
}

// Class demonstrating protected access through inheritance
class Parent {
    protected void show() {
        System.out.println("Protected method from Parent class");
    }
}

class Child extends Parent {
    void display() {
        show(); // Allowed as Child is a subclass
    }
}

// Main class demonstrating multiple concepts interactively
public class Access {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Access Modifiers");
            System.out.println("2. Protected Access via Inheritance");
            System.out.println("3. java.lang Package");
            System.out.println("4. java.util Package");
            System.out.println("5. java.io Package");
            System.out.println("6. java.net Package");
            System.out.println("7. Object Class Methods");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    ModifierExample obj = new ModifierExample();
                    obj.display();
                    break;
                case 2:
                    Child childObj = new Child();
                    childObj.display();
                    break;
                case 3:
                    System.out.print("Enter a string: ");
                    String str = scanner.nextLine();
                    System.out.println("Uppercase: " + str.toUpperCase());
                    break;
                case 4:
                    java.util.ArrayList<String> list = new java.util.ArrayList<>();
                    list.add("Java");
                    list.add("Programming");
                    System.out.println("ArrayList: " + list);
                    break;
                case 5:
                    try {
                        java.io.File file = new java.io.File("test.txt");
                        if (file.createNewFile()) {
                            System.out.println("File created: " + file.getName());
                        } else {
                            System.out.println("File already exists.");
                        }
                    } catch (java.io.IOException e) {
                        System.out.println("An error occurred.");
                    }
                    break;
                case 6:
                    try {
                        java.net.InetAddress address = java.net.InetAddress.getLocalHost();
                        System.out.println("IP Address: " + address.getHostAddress());
                    } catch (java.net.UnknownHostException e) {
                        System.out.println("Unable to get IP address.");
                    }
                    break;
                case 7:
                    Object objInstance = new Object();
                    System.out.println("hashCode: " + objInstance.hashCode());
                    System.out.println("Class Name: " + objInstance.getClass().getName());
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}