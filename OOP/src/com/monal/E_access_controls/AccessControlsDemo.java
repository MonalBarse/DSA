package com.monal.E_access_controls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;
import java.util.Random;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.UnknownHostException;
import java.net.MalformedURLException;
import java.net.URI;

/**
 * Comprehensive demonstration of Java Access Controls and Core Package APIs
 * This class provides detailed examples of access modifiers, inheritance,
 * and the most important classes from core Java packages.
 */
public class AccessControlsDemo {
    public static void main(String[] args) {
        System.out.println("\n========== COMPREHENSIVE JAVA ACCESS CONTROLS & PACKAGES TUTORIAL ==========\n");

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            displayMenu();
            System.out.print("Enter choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        demonstrateAccessModifiers();
                        break;
                    case 2:
                        demonstrateInheritanceAndProtected();
                        break;
                    case 3:
                        demonstrateJavaLangPackage(scanner);
                        break;
                    case 4:
                        demonstrateJavaUtilPackage();
                        break;
                    case 5:
                        demonstrateJavaIOPackage();
                        break;
                    case 6:
                        demonstrateJavaNetPackage();
                        break;
                    case 7:
                        demonstrateObjectClassMethods();
                        break;
                    case 8:
                        exit = true;
                        System.out.println("Exiting application. Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear the scanner buffer
            }

            if (!exit) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n===== JAVA ACCESS CONTROLS & PACKAGES MENU =====");
        System.out.println("1. Access Modifiers Explained");
        System.out.println("2. Inheritance & Protected Access");
        System.out.println("3. java.lang Package Tour");
        System.out.println("4. java.util Package Exploration");
        System.out.println("5. java.io Package Demonstration");
        System.out.println("6. java.net Package Examples");
        System.out.println("7. Object Class Methods Deep Dive");
        System.out.println("8. Exit");
    }

    /**
     * SECTION 1: ACCESS MODIFIERS
     * Demonstrates the four Java access modifiers: private, default
     * (package-private),
     * protected, and public with practical examples.
     */
    private static void demonstrateAccessModifiers() {
        System.out.println("\n===== 1. ACCESS MODIFIERS =====");

        // Create instances to demonstrate access
        System.out.println("\n[INFO] Creating objects to demonstrate access levels:");
        ModifierExample modObj = new ModifierExample();
        modObj.display();

        // Demonstrate direct access based on modifiers
        System.out.println("\n[INFO] Demonstrating direct access from main method:");
        // Cannot access: System.out.println(modObj.privateVar); // Compilation error
        System.out.println("Default variable (package access): " + modObj.defaultVar);
        System.out.println("Protected variable: " + modObj.protectedVar);
        System.out.println("Public variable: " + modObj.publicVar);

        // Demonstrate encapsulation with getters and setters
        System.out.println("\n[INFO] Demonstrating encapsulation with EncapsulatedClass:");
        EncapsulatedClass encObj = new EncapsulatedClass();
        encObj.setId(1001);
        encObj.setName("Java Expert");
        System.out.println(
                "Accessing private variables through getters: ID=" + encObj.getId() + ", Name=" + encObj.getName());

        // Practical example of access modifiers in a class hierarchy
        System.out.println("\n[INFO] Access modifiers in class hierarchy:");
        BankAccount account = new BankAccount("123456", 1000.0);
        account.deposit(500.0);
        account.withdraw(200.0);
        account.displayBalance();

        // Cannot access these methods directly
        // account.updateBalance(100); // private method
        // account.validateAccountNumber("123"); // protected method

        System.out.println("\n[INFO] Access modifier summary:");
        System.out.println("- private: Accessible only within the class");
        System.out.println("- default (no modifier): Accessible within the package");
        System.out.println("- protected: Accessible within package and by subclasses");
        System.out.println("- public: Accessible from anywhere");
    }

    /**
     * SECTION 2: INHERITANCE AND PROTECTED ACCESS
     * Explains how protected members can be accessed through inheritance
     * and demonstrates the concept with concrete examples.
     */
    private static void demonstrateInheritanceAndProtected() {
        System.out.println("\n===== 2. INHERITANCE & PROTECTED ACCESS =====");

        // Basic inheritance with protected access
        System.out.println("\n[INFO] Basic inheritance with protected access:");
        Child childObj = new Child();
        childObj.display();

        // More complex inheritance hierarchy
        System.out.println("\n[INFO] Inheritance hierarchy demonstration:");
        System.out.println("Creating a SavingsAccount (subclass of BankAccount):");
        SavingsAccount savingsAccount = new SavingsAccount("SA12345", 5000.0, 0.05);
        savingsAccount.deposit(1000.0);
        savingsAccount.addInterest();
        savingsAccount.displayBalance();

        System.out.println("\nCreating a CheckingAccount (another subclass of BankAccount):");
        CheckingAccount checkingAccount = new CheckingAccount("CA67890", 3000.0, 500.0);
        checkingAccount.withdraw(3200.0);
        checkingAccount.displayBalance();

        // Abstract class and methods
        System.out.println("\n[INFO] Abstract classes and protected methods:");
        Shape circle = new Circle(5.0);
        Shape rectangle = new Rectangle(4.0, 6.0);

        System.out.println("Circle area: " + circle.calculateArea());
        System.out.println("Rectangle area: " + rectangle.calculateArea());

        // Interface implementation
        System.out.println("\n[INFO] Interfaces with default and public methods:");
        Printable document = new PrintableDocument("Important Contract");
        document.print();
        document.displayInfo();

        System.out.println("\n[INFO] Protected access summary:");
        System.out.println("- Protected members are accessible in the same package");
        System.out.println("- Protected members are also accessible in subclasses");
        System.out.println("- Protected is ideal for methods that should be overridden");
        System.out.println("- Abstract classes often use protected for implementation details");
    }

    /**
     * SECTION 3: JAVA.LANG PACKAGE
     * Explores the essential classes and utilities in the java.lang package,
     * which is automatically imported into all Java programs.
     */
    private static void demonstrateJavaLangPackage(Scanner scanner) {
        System.out.println("\n===== 3. JAVA.LANG PACKAGE =====");
        System.out.println("[INFO] java.lang is automatically imported in every Java program");

        // String manipulation
        System.out.println("\n[INFO] String class capabilities:");
        System.out.print("Enter a string: ");
        String userString = scanner.nextLine();

        System.out.println("Original string: \"" + userString + "\"");
        System.out.println("Length: " + userString.length());
        System.out.println("Uppercase: " + userString.toUpperCase());
        System.out.println("Lowercase: " + userString.toLowerCase());

        if (userString.length() > 0) {
            System.out.println("First character: " + userString.charAt(0));
            System.out.println("Last character: " + userString.charAt(userString.length() - 1));
        }

        if (userString.contains(" ")) {
            String[] words = userString.split(" ");
            System.out.println("Word count: " + words.length);
            System.out.print("Words: ");
            for (String word : words) {
                System.out.print("\"" + word + "\" ");
            }
            System.out.println();
        }

        // StringBuffer vs StringBuilder
        System.out.println("\n[INFO] StringBuffer (thread-safe) vs StringBuilder (faster):");
        StringBuffer buffer = new StringBuffer("StringBuffer: ");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            buffer.append("a");
        }
        long bufferTime = System.currentTimeMillis() - startTime;

        StringBuilder builder = new StringBuilder("StringBuilder: ");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            builder.append("a");
        }
        long builderTime = System.currentTimeMillis() - startTime;

        System.out.println("StringBuffer time: " + bufferTime + "ms");
        System.out.println("StringBuilder time: " + builderTime + "ms");
        System.out.println("StringBuilder is typically faster for single-threaded operations");

        // Wrapper classes
        System.out.println("\n[INFO] Wrapper Classes for primitive types:");
        Integer intObj = Integer.valueOf(42);
        Double doubleObj = Double.valueOf(3.14159);
        Boolean boolObj = Boolean.valueOf(true);

        System.out.println("Integer object: " + intObj + ", primitive: " + intObj.intValue());
        System.out.println("Double object: " + doubleObj + ", primitive: " + doubleObj.doubleValue());
        System.out.println("Boolean object: " + boolObj + ", primitive: " + boolObj.booleanValue());

        // AutoBoxing and Unboxing
        System.out.println("\n[INFO] AutoBoxing and Unboxing:");
        Integer autoBoxed = 100; // Autoboxing int to Integer
        int unboxed = autoBoxed; // Unboxing Integer to int
        System.out.println("Autoboxed: " + autoBoxed + ", Unboxed: " + unboxed);

        // Math class
        System.out.println("\n[INFO] Math class utilities:");
        System.out.println("PI: " + Math.PI);
        System.out.println("E: " + Math.E);
        System.out.println("Square root of 16: " + Math.sqrt(16));
        System.out.println("Absolute value of -7.5: " + Math.abs(-7.5));
        System.out.println("Random number (0-1): " + Math.random());
        System.out.println("Maximum of 10 and 20: " + Math.max(10, 20));
        System.out.println("Round 3.75: " + Math.round(3.75));

        // System class
        System.out.println("\n[INFO] System class capabilities:");
        System.out.println("Current time: " + System.currentTimeMillis() + " ms since epoch");
        System.out.println("JVM properties:");
        System.out.println("  Java version: " + System.getProperty("java.version"));
        System.out.println("  OS name: " + System.getProperty("os.name"));
        System.out.println("  User home directory: " + System.getProperty("user.home"));

        // Thread class
        System.out.println("\n[INFO] Thread class basics:");
        Thread currentThread = Thread.currentThread();
        System.out.println("Current thread: " + currentThread.getName());
        System.out.println("Thread priority: " + currentThread.getPriority());
        System.out.println("Thread group: " + currentThread.getThreadGroup().getName());
    }

    /**
     * SECTION 4: JAVA.UTIL PACKAGE
     * Demonstrates the collection framework and utility classes from java.util
     */
    private static void demonstrateJavaUtilPackage() {
        System.out.println("\n===== 4. JAVA.UTIL PACKAGE =====");

        // ArrayList
        System.out.println("\n[INFO] ArrayList - Dynamic array implementation:");
        ArrayList<String> arrayList = new ArrayList<>();
        System.out.println("Adding elements to ArrayList...");
        arrayList.add("Java");
        arrayList.add("Python");
        arrayList.add("JavaScript");
        System.out.println("ArrayList content: " + arrayList);
        System.out.println("ArrayList size: " + arrayList.size());
        System.out.println("Element at index 1: " + arrayList.get(1));
        arrayList.remove("Python");
        System.out.println("After removing 'Python': " + arrayList);

        // HashMap
        System.out.println("\n[INFO] HashMap - Key-value pairs implementation:");
        Map<Integer, String> hashMap = new HashMap<>();
        System.out.println("Adding key-value pairs to HashMap...");
        hashMap.put(1, "One");
        hashMap.put(2, "Two");
        hashMap.put(3, "Three");
        System.out.println("HashMap content: " + hashMap);
        System.out.println("Value for key 2: " + hashMap.get(2));
        System.out.println("Contains key 4? " + hashMap.containsKey(4));
        System.out.println("Contains value 'One'? " + hashMap.containsValue("One"));
        hashMap.remove(1);
        System.out.println("After removing key 1: " + hashMap);

        // Date and Calendar
        System.out.println("\n[INFO] Date and Calendar classes:");
        Date today = new Date();
        System.out.println("Current date and time: " + today);

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        System.out.println("Current calendar time: " + calendar.getTime());
        System.out.println("Current year: " + calendar.get(java.util.Calendar.YEAR));
        System.out.println("Current month: " + (calendar.get(java.util.Calendar.MONTH) + 1)); // Month is 0-based
        System.out.println("Current day: " + calendar.get(java.util.Calendar.DAY_OF_MONTH));

        // Random
        System.out.println("\n[INFO] Random class for generating pseudo-random numbers:");
        Random random = new Random();
        System.out.println("Random integer: " + random.nextInt());
        System.out.println("Random integer (0-99): " + random.nextInt(100));
        System.out.println("Random double (0.0-1.0): " + random.nextDouble());
        System.out.println("Random boolean: " + random.nextBoolean());

        // Arrays utility
        System.out.println("\n[INFO] Arrays utility class:");
        int[] numbers = { 5, 2, 9, 1, 5, 6 };
        System.out.print("Original array: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();

        java.util.Arrays.sort(numbers);
        System.out.print("Sorted array: ");
        for (int num : numbers) {
            System.out.print(num + " ");
        }
        System.out.println();

        int searchIndex = java.util.Arrays.binarySearch(numbers, 5);
        System.out.println("Binary search for 5, found at index: " + searchIndex);

        int[] numbersCopy = java.util.Arrays.copyOf(numbers, numbers.length);
        System.out.print("Copied array: ");
        for (int num : numbersCopy) {
            System.out.print(num + " ");
        }
        System.out.println();

        // Scanner (we're already using it in the main method)
        System.out.println("\n[INFO] Scanner class for parsing input:");
        System.out.println("Scanner is being used throughout this program to parse your input");
        System.out.println("It can parse primitive types and strings from various sources");
    }

    /**
     * SECTION 5: JAVA.IO PACKAGE
     * Demonstrates file I/O operations using the java.io package
     */
    private static void demonstrateJavaIOPackage() {
        System.out.println("\n===== 5. JAVA.IO PACKAGE =====");

        // File class
        System.out.println("\n[INFO] File class for file system operations:");
        File demoDir = new File("java_demo_dir");
        if (!demoDir.exists()) {
            boolean created = demoDir.mkdir();
            System.out.println("Directory created: " + created);
        } else {
            System.out.println("Directory already exists");
        }

        File textFile = new File(demoDir, "sample.txt");
        System.out.println("File path: " + textFile.getAbsolutePath());
        System.out.println("File exists: " + textFile.exists());

        // FileWriter and FileReader
        System.out.println("\n[INFO] FileWriter and FileReader for character streams:");
        try {
            // Writing to file
            FileWriter writer = new FileWriter(textFile);
            writer.write("Hello, Java IO!\n");
            writer.write("This file demonstrates basic file operations.\n");
            writer.write("Line 3: FileWriter and FileReader are for character data.\n");
            System.out.println("Content written to file successfully");
            writer.close();

            // Reading from file
            FileReader reader = new FileReader(textFile);
            char[] buffer = new char[100];
            int charsRead = reader.read(buffer);
            System.out.println("\nRead " + charsRead + " characters:");
            System.out.println(new String(buffer, 0, charsRead));
            reader.close();
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }

        // BufferedWriter and BufferedReader
        System.out.println("\n[INFO] BufferedWriter and BufferedReader for efficient I/O:");
        try {
            // Buffered writing
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter(textFile));
            buffWriter.write("Line 1: Buffered streams improve efficiency.\n");
            buffWriter.write("Line 2: They reduce system calls by buffering data.\n");
            buffWriter.write("Line 3: This results in better performance.\n");
            buffWriter.close();
            System.out.println("Content written with BufferedWriter");

            // Buffered reading (line by line)
            BufferedReader buffReader = new BufferedReader(new FileReader(textFile));
            String line;
            System.out.println("\nReading file line by line:");
            while ((line = buffReader.readLine()) != null) {
                System.out.println(line);
            }
            buffReader.close();
        } catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }

        // Exception handling
        System.out.println("\n[INFO] Exception handling with try-with-resources:");
        File nonExistentFile = new File("nonexistent.txt");

        try (@SuppressWarnings("unused")
        FileReader reader = new FileReader(nonExistentFile)) {
            System.out.println("This won't execute because the file doesn't exist");
        } catch (IOException e) {
            System.out.println("Expected exception: " + e.getMessage());
            System.out.println("try-with-resources ensures that resources are closed");
        }

        // File operations
        System.out.println("\n[INFO] Additional File operations:");
        System.out.println("File size: " + textFile.length() + " bytes");
        System.out.println("Can read: " + textFile.canRead());
        System.out.println("Can write: " + textFile.canWrite());
        System.out.println("Is directory: " + textFile.isDirectory());
        System.out.println("Is file: " + textFile.isFile());
        System.out.println("Last modified: " + new Date(textFile.lastModified()));
    }

    /**
     * SECTION 6: JAVA.NET PACKAGE
     * Demonstrates network operations using the java.net package
     */
    private static void demonstrateJavaNetPackage() {
        System.out.println("\n===== 6. JAVA.NET PACKAGE =====");

        // InetAddress - Getting IP addresses
        System.out.println("\n[INFO] InetAddress for IP address operations:");
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Local Host Name: " + localhost.getHostName());
            System.out.println("Local IP Address: " + localhost.getHostAddress());

            System.out.println("\nLooking up google.com:");
            InetAddress[] addresses = InetAddress.getAllByName("https://google.com");
            for (InetAddress address : addresses) {
                System.out.println("Host Name: " + address.getHostName());
                System.out.println("IP Address: " + address.getHostAddress());
            }
        } catch (UnknownHostException e) {
            System.out.println("Host lookup failed: " + e.getMessage());
        }

        // URL class
        System.out.println("\n[INFO] URL class for handling URLs:");
        try {
            URL url = URI.create(
                    "https://www.google.com/search?q=Images&sca_esv=9cb432d0935eb54f&sxsrf=AHTn8zrLu5IXUHKdyAqtca7BfbEYb6Pz9A%3A1741601528388&ei=-LrOZ4ayF9mhseMPsIq9iAE&ved=0ahUKEwiGjsKjo_-LAxXZUGwGHTBFDxEQ4dUDCBA&uact=5&oq=Images&gs_lp=Egxnd3Mtd2l6LXNlcnAiBkltYWdlczIKECMYgAQYJxiKBTIIEAAYgAQYsQMyCBAAGIAEGLEDMgoQABiABBhDGIoFMgUQABiABDIIEAAYgAQYsQMyCBAAGIAEGLEDMggQABiABBixAzIIEAAYgAQYsQMyChAAGIAEGEMYigVIvA5QuQRYug1wAngBkAEAmAGZAaABrAWqAQMwLjW4AQPIAQD4AQGYAgegAt8FwgIKEAAYsAMY1gQYR8ICDRAAGIAEGLADGEMYigXCAhMQLhiABBiwAxhDGMgDGIoF2AEBwgIWEC4YgAQYsAMYQxjUAhjIAxiKBdgBAcICDhAAGIAEGLEDGIMBGIoFwgILEAAYgAQYsQMYgwHCAgsQLhiABBixAxiDAcICDhAuGIAEGLEDGIMBGIoFwgIREC4YgAQYsQMY0QMYgwEYxwHCAg0QABiABBixAxhDGIoFwgILEAAYgAQYkgMYigWYAwCIBgGQBgy6BgQIARgIkgcDMi41oAfNJQ&sclient=gws-wiz-serp")
                    .toURL();
            System.out.println("Protocol: " + url.getProtocol());
            System.out.println("Host: " + url.getHost());
            System.out.println("Port: " + url.getPort());
            System.out.println("Path: " + url.getPath());
            System.out.println("Query: " + url.getQuery());
            System.out.println("Reference: " + url.getRef());
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL: " + e.getMessage());
        }

        // URLConnection basics
        System.out.println("\n[INFO] URLConnection for connecting to a URL:");
        try {
            @SuppressWarnings("deprecated")
            URL url = URI.create("https://google.com").toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // We're not going to execute the connection to avoid unexpected network calls
            System.out.println("Connection created to: " + url);
            System.out.println("Request method: " + connection.getRequestMethod());
            System.out.println("Connection timeout: " + connection.getConnectTimeout() + "ms");
            System.out.println("Note: Not executing the connection to avoid network calls");

        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }

        // Socket basics (conceptual explanation)
        System.out.println("\n[INFO] Socket programming concepts:");
        System.out.println("1. Socket - Endpoint for sending or receiving data");
        System.out.println("2. ServerSocket - Waits for client socket connections");
        System.out.println("3. Basic client-server model:");
        System.out.println("   - Server creates a ServerSocket to listen on a port");
        System.out.println("   - Client creates a Socket to connect to server's address & port");
        System.out.println("   - Server accepts connection, creating a new Socket for communication");
        System.out.println("   - Both ends use input/output streams to exchange data");
    }

    /**
     * SECTION 7: OBJECT CLASS METHODS
     * Deep dive into the methods inherited from the Object class
     */
    private static void demonstrateObjectClassMethods() {
        System.out.println("\n===== 7. OBJECT CLASS METHODS =====");
        System.out.println("[INFO] Every Java class inherits from Object class");

        // Create objects for demonstration
        Person person1 = new Person("John", 30);
        Person person2 = new Person("Jane", 25);
        Person person3 = new Person("John", 30);

        // toString() method
        System.out.println("\n[INFO] toString() method:");
        System.out.println("Default Object.toString(): " + new Object().toString());
        System.out.println("Person1.toString(): " + person1.toString());
        System.out.println("Direct printing using toString() implicitly: " + person2);

        // equals() method
        System.out.println("\n[INFO] equals() method:");
        System.out.println("person1.equals(person2): " + person1.equals(person2));
        System.out.println("person1.equals(person3): " + person1.equals(person3));
        System.out.println("person1 == person3: " + (person1 == person3)); // Reference comparison

        // hashCode() method
        System.out.println("\n[INFO] hashCode() method:");
        System.out.println("person1.hashCode(): " + person1.hashCode());
        System.out.println("person2.hashCode(): " + person2.hashCode());
        System.out.println("person3.hashCode(): " + person3.hashCode());
        System.out.println("Note: equal objects must have equal hash codes");

        // getClass() method
        System.out.println("\n[INFO] getClass() method:");
        System.out.println("person1.getClass(): " + person1.getClass());
        System.out.println("Class name: " + person1.getClass().getName());
        System.out.println("Simple class name: " + person1.getClass().getSimpleName());
        System.out.println("Is instance of Person: " + (person1 instanceof Person));

        // clone() method
        System.out.println("\n[INFO] clone() method:");
        System.out.println("Objects must implement Cloneable interface to support cloning");
        try {
            CloneablePerson originalPerson = new CloneablePerson("Alice", 28);
            CloneablePerson clonedPerson = (CloneablePerson) originalPerson.clone();

            System.out.println("Original: " + originalPerson);
            System.out.println("Clone: " + clonedPerson);
            System.out.println("Are they equal? " + originalPerson.equals(clonedPerson));
            System.out.println("Same reference? " + (originalPerson == clonedPerson));
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning not supported");
        }

        // finalize() method
        System.out.println("\n[INFO] finalize() method:");
        System.out.println("Called by garbage collector before object destruction");
        System.out.println("Deprecated since Java 9 - not recommended for resource cleanup");
        System.out.println("Better alternatives: try-with-resources, explicit close methods");

        // wait(), notify(), notifyAll() methods
        System.out.println("\n[INFO] wait(), notify(), notifyAll() methods:");
        System.out.println("Used for thread synchronization");
        System.out.println("wait() - Makes thread wait until another thread calls notify()");
        System.out.println("notify() - Wakes up a single thread waiting on the object");
        System.out.println("notifyAll() - Wakes up all threads waiting on the object");
    }
}

/**
 * Classes for Access Modifiers Demonstration
 */
class ModifierExample {
    private int privateVar = 10; // Only accessible within this class
    int defaultVar = 20; // Accessible within the same package
    protected int protectedVar = 30; // Accessible within package and subclasses
    public int publicVar = 40; // Accessible from anywhere

    public void display() {
        System.out.println("Private Variable: " + privateVar);
        System.out.println("Default Variable: " + defaultVar);
        System.out.println("Protected Variable: " + protectedVar);
        System.out.println("Public Variable: " + publicVar);

        // Private method can be called within the same class
        privateMethod();
    }

    private void privateMethod() {
        System.out.println("This is a private method");
    }

    void defaultMethod() {
        System.out.println("This is a package-private (default) method");
    }

    protected void protectedMethod() {
        System.out.println("This is a protected method");
    }

    public void publicMethod() {
        System.out.println("This is a public method");
    }
}

class EncapsulatedClass {
    private int id;
    private String name;

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // Public methods for external access
    public void deposit(double amount) {
        if (amount > 0) {
            updateBalance(amount);
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            updateBalance(-amount);
            System.out.println("Withdrawn: $" + amount);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds");
        }
    }

    public void displayBalance() {
        System.out.println("Account: " + getFormattedAccountNumber() + ", Balance: $" + balance);
    }

    // Private method - internal implementation detail
    private void updateBalance(double amount) {
        this.balance += amount;
    }

    // Protected method - available to subclasses
    protected boolean validateAccountNumber(String accountNumber) {
        // Simple validation - real implementation would be more complex
        return accountNumber != null && accountNumber.length() > 5;
    }

    // Helper method for formatting
    private String getFormattedAccountNumber() {
        // Format to show only last 4 digits
        if (accountNumber.length() > 4) {
            return "****" + accountNumber.substring(accountNumber.length() - 4);
        }
        return accountNumber;
    }
}

/**
 * Classes for Inheritance Demonstration
 */
class Parent {
    protected int protectedVar = 100;

    protected void displayParent() {
        System.out.println("Protected variable in Parent: " + protectedVar);
    }
}

class Child extends Parent {
    private int childVar = 200;

    public void display() {
        // Can access protected member from parent
        System.out.println("Accessing Parent's protected variable: " + protectedVar);
        displayParent(); // Can call protected method
        System.out.println("Child's private variable: " + childVar);
    }
}

// Bank account inheritance hierarchy
class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(String accountNumber, double initialBalance, double interestRate) {
        super(accountNumber, initialBalance);
        this.interestRate = interestRate;
    }

    public void addInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
        System.out.println("Interest added: $" + interest);
    }

    // Method to get balance - needed because balance is private in parent
    private double getBalance() {
        // We'll use a workaround since we can't directly access private members
        // In a real application, we would have a protected getBalance() in the parent
        double currentBalance = 0;
        // This is just a simulation to show the concept
        try {
            // Create a temporary zero-balance account to get the current balance
            deposit(0); // This will display the current balance
            // The actual value would be retrieved from the parent in a real implementation
        } catch (Exception e) {
            System.out.println("Error retrieving balance: " + e.getMessage());
        }
        return currentBalance;
    }
}

class CheckingAccount extends BankAccount {
    @SuppressWarnings("unused")
    private double overdraftLimit;

    public CheckingAccount(String accountNumber, double initialBalance, double overdraftLimit) {
        super(accountNumber, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        // Access validation from parent class
        if (validateAccountNumber(getAccountNumber())) {
            // Override to allow overdraft up to the limit
            // We can't directly access balance in parent, so we use a workaround
            if (amount > 0) {
                super.withdraw(amount); // Try normal withdrawal first
                // The actual implementation would be more sophisticated in a real application
            } else {
                System.out.println("Invalid withdrawal amount");
            }
        } else {
            System.out.println("Invalid account number");
        }
    }

    // We need this method to access the account number for validation
    private String getAccountNumber() {
        return ""; // In a real app, we would have access to this via a protected getter
    }
}

/**
 * Abstract class and Interface examples
 */
abstract class Shape {
    protected String name;

    public Shape(String name) {
        this.name = name;
    }

    // Abstract method - must be implemented by subclasses
    public abstract double calculateArea();

    // Concrete method - inherited by all subclasses
    public void displayName() {
        System.out.println("Shape: " + name);
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        super("Circle");
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        super("Rectangle");
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }
}

interface Printable {
    void print(); // Abstract method - public by default

    // Default method - introduced in Java 8
    default void displayInfo() {
        System.out.println("This is a printable item");
    }
}

class PrintableDocument implements Printable {
    private String documentName;

    public PrintableDocument(String documentName) {
        this.documentName = documentName;
    }

    @Override
    public void print() {
        System.out.println("Printing document: " + documentName);
    }

    @Override
    public void displayInfo() {
        System.out.println("Document name: " + documentName);
    }
}

/**
 * Classes for Object method demonstration
 */
class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Override toString() for better string representation
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }

    // Override equals() for value comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Person person = (Person) obj;
        return age == person.age &&
                (name == null ? person.name == null : name.equals(person.name));
    }

    // Override hashCode() to be consistent with equals()
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
}

class CloneablePerson implements Cloneable {
    private String name;
    private int age;

    public CloneablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Override clone() to enable object cloning
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // Override toString() for better string representation
    @Override
    public String toString() {
        return "CloneablePerson{name='" + name + "', age=" + age + "}";
    }

    // Override equals() for value comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        CloneablePerson person = (CloneablePerson) obj;
        return age == person.age &&
                (name == null ? person.name == null : name.equals(person.name));
    }

    // Override hashCode() to be consistent with equals()
    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }
}