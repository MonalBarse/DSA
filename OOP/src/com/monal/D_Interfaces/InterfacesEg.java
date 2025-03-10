package com.monal.D_Interfaces;

//  * Includes basic usage, multiple inheritance, default methods, static methods,
//  * private methods, functional interfaces, marker interfaces, and more.

// ========== 1. Basic Interface Definition ==========
/*
 * An Interface is a contract that defines what a class can do, but not how it does it.
 * It contains only abstract methods, constants, default methods, static methods, and private methods.
 */
interface Vehicle {
    // Constants in interfaces are implicitly public, static, and final
    int MAX_SPEED = 200;

    // Abstract methods (implicitly public and abstract)
    void start();

    void stop();

    // Default method (added in Java 8) - provides implementation in the interface
    default void performSafety() {
        System.out.println("\n[INFO] Default method in Vehicle interface");
        // Using private methods for code organization
        checkBrakes();
        checkLights();
        System.out.println("[INFO] Safety check completed");
    }

    // Static method (added in Java 8) - called directly on the interface
    static void showVehicleInfo() {
        System.out.println("\n[INFO] Inside Static method in Vehicle interface");
        System.out.println("[INFO] Vehicles are transportation devices with a maximum speed of " + MAX_SPEED + " km/h");
    }

    // Private method (added in Java 9) - for internal use within the interface
    private void checkBrakes() {
        System.out.println("[INFO] Checking brakes");
    }

    private void checkLights() {
        System.out.println("[INFO] Checking lights");
    }
}

// ========== 2. Interface Inheritance ==========
// Interfaces can extend other interfaces
interface ElectricVehicle extends Vehicle {

    void charge();

    int getBatteryLevel();
}

// ========== 3. Marker Interface ==========
// A marker interface has no methods or constants - it simply "marks" a class
interface Autonomous {
    // No methods or constants - just marks a class as autonomous
    // This means that any class implementing this interface is autonomous vehicle
}

// ========== 4. Multiple Interfaces ==========
interface Engine {

    void turnOn();

    void turnOff();

    // Default method example
    default void engineSpecs() {
        System.out.println("\n[INFO] Default method in Engine interface");
        System.out.println("[INFO] This is a standard engine with 4 cylinders");
    }

    // Static method example
    static boolean isEcoFriendly(String engineType) {
        System.out.println("\n[INFO] Static method in Engine interface");
        return engineType.equals("electric") || engineType.equals("hybrid");
    }
}

interface MediaPlayer {

    void playMusic();

    void stopMusic();

}

// ========== 5. Default Method Conflict Resolution ==========
// This topic is - that if a class implements two interfaces with the same
// default method name, then the class must override the default method to
// resolve the conflict.
interface Radio {
    default void turnOn() {
        System.out.println("\n[INFO] Default method in Radio interface");
        System.out.println("[INFO] Radio turning on");
    }
}

interface TV {
    default void turnOn() {
        System.out.println("\n[INFO] Default method in TV interface");
        System.out.println("[INFO] TV turning on");
    }
}

// ========== 6. Nested Interface ==========
interface AdvancedFeatures {
    // Nested interface - shows hierarchy and organization
    interface GPS {
        void showLocation();
    }

    // Another nested interface
    interface AutoPilot {
        void engage();

        void disengage();
    }
}

// ========== 7. Functional Interface ==========
// A functional interface has exactly one abstract method
@FunctionalInterface
interface VehicleStarter {
    void start(Vehicle vehicle);

    // Default methods don't count against the single abstract method rule
    default void info() {
        System.out.println("[INFO] This is a vehicle starter");
    }
}

// ========== 8. Basic Implementation ==========
// First implementation of Vehicle interface
class Car implements Vehicle, Engine, MediaPlayer {
    @Override
    public void start() {
        System.out.println("\n[INFO] Implementing start() from Vehicle interface");
        System.out.println("[INFO] Car is starting using the key ignition");
    }

    @Override
    public void stop() {
        System.out.println("\n[INFO] Implementing stop() from Vehicle interface");
        System.out.println("[INFO] Car has stopped");
    }

    @Override
    public void turnOn() {
        System.out.println("\n[INFO] Implementing turnOn() from Engine interface");
        System.out.println("[INFO] Engine is turning on");
    }

    @Override
    public void turnOff() {
        System.out.println("\n[INFO] Implementing turnOff() from Engine interface");
        System.out.println("[INFO] Engine is turning off");
    }

    @Override
    public void playMusic() {
        System.out.println("\n[INFO] Implementing playMusic() from MediaPlayer interface");
        System.out.println("[INFO] Playing music on the car's media player");
    }

    @Override
    public void stopMusic() {
        System.out.println("\n[INFO] Implementing stopMusic() from MediaPlayer interface");
        System.out.println("[INFO] Music has been stopped");
    }
}

// ========== 9. Advanced Implementation ==========
// Class implementing multiple interfaces including a nested interface
class SmartCar extends Car implements ElectricVehicle, AdvancedFeatures.GPS, AdvancedFeatures.AutoPilot, Autonomous {
    private int batteryLevel = 80;

    @Override
    public void start() {
        System.out.println("\n[INFO] Overriding start() in SmartCar");
        System.out.println("[INFO] SmartCar starting with keyless ignition");
    }

    @Override
    public void charge() {
        System.out.println("\n[INFO] Implementing charge() from ElectricVehicle interface");
        System.out.println("[INFO] SmartCar is charging");
        batteryLevel = 100;
    }

    @Override
    public int getBatteryLevel() {
        System.out.println("\n[INFO] Implementing getBatteryLevel() from ElectricVehicle interface");
        System.out.println("[INFO] Current battery level: " + batteryLevel + "%");
        return batteryLevel;
    }

    @Override
    public void showLocation() {
        System.out.println("\n[INFO] Implementing showLocation() from nested GPS interface");
        System.out.println("[INFO] GPS is displaying the current location");
    }

    @Override
    public void engage() {
        System.out.println("\n[INFO] Implementing engage() from nested AutoPilot interface");
        System.out.println("[INFO] AutoPilot engaged");
    }

    @Override
    public void disengage() {
        System.out.println("\n[INFO] Implementing disengage() from nested AutoPilot interface");
        System.out.println("[INFO] AutoPilot disengaged");
    }
}

// ========== 10. Resolving Default Method Conflicts ==========
class Entertainment implements Radio, TV {
    // Must override the conflicting method
    @Override
    public void turnOn() {
        System.out.println("\n[INFO] Resolving default method conflict between Radio and TV interfaces");
        // Choose which interface's default method to call
        Radio.super.turnOn();
        // Or we could call TV's version
        // TV.super.turnOn();

        // Or we could provide completely different implementation
        System.out.println("[INFO] Entertainment system is now on");
    }
}

// ========== Main Class To Run The Program ==========
public class InterfacesEg {
    public static void main(String[] args) {
        System.out.println("\n========== COMPREHENSIVE JAVA INTERFACES TUTORIAL ==========\n");

        // ===== 1. BASIC INTERFACE USAGE =====
        System.out.println("\n===== 1. BASIC INTERFACE USAGE =====");
        Car myCar = new Car();
        myCar.start();
        myCar.stop();

        // ===== 2. MULTIPLE INTERFACES =====
        System.out.println("\n===== 2. MULTIPLE INTERFACES =====");
        System.out.println("\n[INFO] A single class can implement multiple interfaces");
        myCar.turnOn();
        myCar.playMusic();
        myCar.stopMusic();
        myCar.turnOff();

        // ===== 3. DEFAULT METHODS =====
        System.out.println("\n===== 3. DEFAULT METHODS =====");
        System.out.println("\n[INFO] Default methods provide implementation in interfaces");
        myCar.engineSpecs(); // Calling default method from Engine
        myCar.performSafety(); // Calling default method from Vehicle

        // ===== 4. STATIC METHODS =====
        System.out.println("\n===== 4. STATIC METHODS =====");
        System.out.println("\n[INFO] Static methods are called directly on the interface");
        Vehicle.showVehicleInfo();
        boolean isEco = Engine.isEcoFriendly("electric");
        System.out.println("[INFO] Is electric engine eco-friendly? " + isEco);

        // ===== 5. CONSTANTS IN INTERFACES =====
        System.out.println("\n===== 5. CONSTANTS IN INTERFACES =====");
        System.out.println("\n[INFO] Interfaces can define constants (public static final)");
        System.out.println("[INFO] Maximum speed defined in Vehicle interface: " + Vehicle.MAX_SPEED + " km/h");

        // ===== 6. INTERFACE INHERITANCE =====
        System.out.println("\n===== 6. INTERFACE INHERITANCE =====");
        System.out.println("\n[INFO] Interfaces can extend other interfaces");
        SmartCar mySmartCar = new SmartCar();
        mySmartCar.start(); // From Vehicle interface
        mySmartCar.charge(); // From ElectricVehicle interface that extends Vehicle
        mySmartCar.getBatteryLevel();

        // ===== 7. NESTED INTERFACES =====
        System.out.println("\n===== 7. NESTED INTERFACES =====");
        System.out.println("\n[INFO] Interfaces can be nested within other interfaces");
        mySmartCar.showLocation(); // From AdvancedFeatures.GPS
        mySmartCar.engage(); // From AdvancedFeatures.AutoPilot
        mySmartCar.disengage(); // From AdvancedFeatures.AutoPilot

        // ===== 8. MARKER INTERFACES =====
        System.out.println("\n===== 8. MARKER INTERFACES =====");
        System.out.println("\n[INFO] Marker interfaces have no methods but 'mark' a class");
        if (mySmartCar instanceof Autonomous) {
            System.out.println("[INFO] SmartCar is autonomous (detected via marker interface)");
        }

        // ===== 9. DEFAULT METHOD CONFLICT RESOLUTION =====
        System.out.println("\n===== 9. DEFAULT METHOD CONFLICT RESOLUTION =====");
        System.out
                .println("\n[INFO] Resolving conflicts when implementing multiple interfaces with same default method");
        Entertainment myEntertainment = new Entertainment();
        myEntertainment.turnOn();

        // ===== 10. POLYMORPHISM THROUGH INTERFACES =====
        System.out.println("\n===== 10. POLYMORPHISM THROUGH INTERFACES =====");
        System.out.println("\n[INFO] Interfaces enable polymorphism and loose coupling");

        // We can use the interface type as a reference
        Vehicle vehicle1 = new Car();
        Vehicle vehicle2 = new SmartCar();

        // Testing polymorphism
        testVehicle(vehicle1);
        testVehicle(vehicle2);

        // ===== 11. FUNCTIONAL INTERFACES AND LAMBDAS =====
        System.out.println("\n===== 11. FUNCTIONAL INTERFACES AND LAMBDAS =====");
        System.out.println("\n[INFO] Functional interfaces enable lambda expressions");

        // Using lambda expression with a functional interface
        VehicleStarter starter = (vehicle) -> {
            System.out.println("\n[INFO] Lambda expression implementing VehicleStarter functional interface");
            System.out.println("[INFO] Starting vehicle remotely");
            vehicle.start();
        };

        // Using the lambda
        starter.start(mySmartCar);

        // ===== 12. INTERFACE VS ABSTRACT CLASS =====
        System.out.println("\n===== 12. INTERFACE VS ABSTRACT CLASS =====");
        System.out.println("\n[INFO] Key differences between interfaces and abstract classes:");
        System.out.println("[INFO] 1. A class can implement multiple interfaces but extend only one abstract class");
        System.out
                .println("[INFO] 2. Abstract classes can have instance variables and constructors; interfaces cannot");
        System.out.println("[INFO] 3. Abstract classes can have concrete methods without the 'default' keyword");
        System.out.println("[INFO] 4. Use interfaces for unrelated classes to implement common behavior");
        System.out.println("[INFO] 5. Use abstract classes for closely related classes that share code");
    }

    // Demonstrating polymorphism through interfaces
    public static void testVehicle(Vehicle vehicle) {
        System.out.println("\n[INFO] Demonstrating polymorphism through interfaces");
        System.out.println("[INFO] Testing vehicle: " + vehicle.getClass().getSimpleName());
        vehicle.start(); // The specific implementation will be called based on the object type
    }
}