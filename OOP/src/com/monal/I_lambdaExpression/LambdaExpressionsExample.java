package com.monal.I_lambdaExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Lambda Expressions in Java:
 * Lambda expressions are a new and important feature included in Java 8.
 * In simple terms, they can be considered as anonymous functions that allow you to write code more concisely.
 * for example, you can use lambda expressions to replace anonymous classes in Java.
 * Lambda expressions are used primarily to define inline implementation of a functional interface, i.e., an interface with a single method only.
 * Lambda expressions are also used to define implementation of abstract method of functional interface.
 * e.g -
 *      public interface MyInterface {
 *              void myMethod();
 *     }
 *     MyInterface myInterface = () -> System.out.println("My Method Implementation");
 *     myInterface.myMethod();
 *
 *  In the above example, we have defined a functional interface MyInterface with a single abstract method myMethod.
 *  We have then created an object of MyInterface using a lambda expression to define the implementation of myMethod.
 *  Finally, we have called the myMethod method using the object of MyInterface.
 */
public class LambdaExpressionsExample {
        public static void main(String[] args) {
                System.out.println("\n========== COMPREHENSIVE LAMBDA EXPRESSIONS TUTORIAL ==========\n");

                // ===== 1. BASIC LAMBDA SYNTAX =====
                System.out.println("\n===== 1. BASIC LAMBDA SYNTAX =====");

                // Traditional anonymous class
                Runnable traditionalRunnable = new Runnable() {
                        @Override
                        public void run() {
                                System.out.println("[INFO] Traditional anonymous class implementation");
                        }
                };
                traditionalRunnable.run();

                // Equivalent lambda expression
                Runnable lambdaRunnable = () -> System.out.println("[INFO] Lambda expression implementation");
                lambdaRunnable.run();

                // Multi-line lambda with block
                Runnable multiLineRunnable = () -> {
                        System.out.println("[INFO] This is a multi-line lambda");
                        System.out.println("[INFO] With multiple statements");
                };
                multiLineRunnable.run();

                // ===== 2. FUNCTIONAL INTERFACES =====
                System.out.println("\n===== 2. FUNCTIONAL INTERFACES =====");

                // Predicate - takes one argument and returns boolean
                System.out.println("\n[INFO] Predicate<T> - test if condition is true");
                Predicate<String> isEmpty = str -> str.isEmpty();
                System.out.println("Is empty string empty? " + isEmpty.test(""));
                System.out.println("Is 'Hello' empty? " + isEmpty.test("Hello"));

                // Consumer - accepts one argument but returns no result
                System.out.println("\n[INFO] Consumer<T> - performs operation on input");
                Consumer<String> printer = message -> System.out.println(message);
                printer.accept("[INFO] This message was printed using a Consumer");

                // Function - transforms one input into an output of possibly different type
                System.out.println("\n[INFO] Function<T,R> - transforms input to output");
                Function<String, Integer> lengthFunction = str -> str.length();
                System.out.println("Length of 'Hello': " + lengthFunction.apply("Hello"));

                // Supplier - takes no arguments but returns a result
                System.out.println("\n[INFO] Supplier<T> - provides a value");
                Supplier<Double> randomSupplier = () -> Math.random();
                System.out.println("Random value: " + randomSupplier.get());

                // UnaryOperator - specialization of Function where input and output are same
                // type
                System.out.println("\n[INFO] UnaryOperator<T> - transforms input to same type");
                UnaryOperator<String> toUpperCase = str -> str.toUpperCase();
                System.out.println("'hello' to uppercase: " + toUpperCase.apply("hello"));

                // BinaryOperator - takes two arguments of same type and returns result of same
                // type
                System.out.println("\n[INFO] BinaryOperator<T> - combines two inputs of same type");
                BinaryOperator<Integer> add = (a, b) -> a + b;
                System.out.println("5 + 3 = " + add.apply(5, 3));

                // BiFunction - takes two arguments of different types and returns a result
                System.out.println("\n[INFO] BiFunction<T,U,R> - transforms two inputs to output");
                BiFunction<String, Integer, String> repeat = (str, count) -> str.repeat(count);
                System.out.println("Repeat 'Hi' 3 times: " + repeat.apply("Hi", 3));

                // ===== 3. METHOD REFERENCES =====
                System.out.println("\n===== 3. METHOD REFERENCES =====");

                // Static method reference
                System.out.println("\n[INFO] Static method reference");
                Function<String, Integer> parseIntLambda = s -> Integer.parseInt(s);
                Function<String, Integer> parseIntRef = Integer::parseInt;
                System.out.println("Parse '123' (lambda): " + parseIntLambda.apply("123"));
                System.out.println("Parse '123' (method ref): " + parseIntRef.apply("123"));

                // Instance method reference of a particular object
                System.out.println("\n[INFO] Instance method reference of particular object");
                Consumer<String> printLambda = s -> System.out.println(s);
                Consumer<String> printRef = System.out::println;
                printLambda.accept("[INFO] Printed via lambda");
                printRef.accept("[INFO] Printed via method reference");

                // Instance method reference of an arbitrary object of a particular type
                System.out.println("\n[INFO] Instance method reference of arbitrary object");
                Function<String, String> toLowerLambda = s -> s.toLowerCase();
                Function<String, String> toLowerRef = String::toLowerCase;
                System.out.println("'HELLO' to lowercase (lambda): " + toLowerLambda.apply("HELLO"));
                System.out.println("'HELLO' to lowercase (method ref): " + toLowerRef.apply("HELLO"));

                // Constructor reference
                System.out.println("\n[INFO] Constructor reference");
                Supplier<List<String>> listSupplierLambda = () -> new ArrayList<>();
                Supplier<List<String>> listSupplierRef = ArrayList::new;
                List<String> list1 = listSupplierLambda.get();
                List<String> list2 = listSupplierRef.get();
                System.out.println("List created via lambda: " + list1);
                System.out.println("List created via constructor ref: " + list2);

                // ===== 4. LAMBDA WITH COLLECTIONS =====
                System.out.println("\n===== 4. LAMBDA WITH COLLECTIONS =====");

                List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eva");

                // Sorting with lambda
                System.out.println("\n[INFO] Sorting with lambda");
                Collections.sort(names, (s1, s2) -> s1.compareToIgnoreCase(s2));
                System.out.println("Sorted alphabetically: " + names);

                // Sorting with method reference
                System.out.println("\n[INFO] Sorting with method reference");
                Collections.sort(names, String::compareToIgnoreCase);
                System.out.println("Sorted alphabetically (method ref): " + names);

                // Sorting by length
                System.out.println("\n[INFO] Sorting by length");
                Collections.sort(names, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
                System.out.println("Sorted by length: " + names);

                // Sorting by length (using Comparator factory methods)
                System.out.println("\n[INFO] Sorting using Comparator factory methods");
                Collections.sort(names, Comparator.comparingInt(String::length));
                System.out.println("Sorted by length (comparator factory): " + names);

                // ===== 5. STREAM API BASICS =====
                System.out.println("\n===== 5. STREAM API BASICS =====");

                List<String> fruits = Arrays.asList("apple", "banana", "cherry", "date", "elderberry", "fig");

                // Filtering with stream
                System.out.println("\n[INFO] Filtering with stream");
                List<String> longFruits = fruits.stream()
                                .filter(f -> f.length() > 5)
                                .collect(Collectors.toList());
                System.out.println("Fruits with more than 5 letters: " + longFruits);

                // Mapping with stream
                System.out.println("\n[INFO] Mapping with stream");
                List<Integer> fruitLengths = fruits.stream()
                                .map(String::length)
                                .collect(Collectors.toList());
                System.out.println("Lengths of fruits: " + fruitLengths);

                // ForEach with stream
                System.out.println("\n[INFO] ForEach with stream");
                System.out.println("Fruits in uppercase:");
                fruits.stream()
                                .map(String::toUpperCase)
                                .forEach(System.out::println);

                // ===== 6. STREAM OPERATIONS =====
                System.out.println("\n===== 6. STREAM OPERATIONS =====");

                List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

                // Filter - keep only elements that match a condition
                System.out.println("\n[INFO] Filter operation");
                List<Integer> evenNumbers = numbers.stream()
                                .filter(n -> n % 2 == 0)
                                .collect(Collectors.toList());
                System.out.println("Even numbers: " + evenNumbers);

                // Map - transform each element
                System.out.println("\n[INFO] Map operation");
                List<Integer> squaredNumbers = numbers.stream()
                                .map(n -> n * n)
                                .collect(Collectors.toList());
                System.out.println("Squared numbers: " + squaredNumbers);

                // FlatMap - flatten nested collections
                System.out.println("\n[INFO] FlatMap operation");
                List<List<Integer>> nestedLists = Arrays.asList(
                                Arrays.asList(1, 2),
                                Arrays.asList(3, 4),
                                Arrays.asList(5, 6));

                List<Integer> flattenedList = nestedLists.stream()
                                .flatMap(list -> list.stream())
                                .collect(Collectors.toList());
                System.out.println("Flattened list: " + flattenedList);

                // Reduce - combine elements into a single result
                System.out.println("\n[INFO] Reduce operation");
                int sum = numbers.stream()
                                .reduce(0, (a, b) -> a + b);
                System.out.println("Sum of numbers: " + sum);

                // Method reference with reduce
                int product = numbers.stream()
                                .reduce(1, (a, b) -> a * b);
                System.out.println("Product of numbers: " + product);

                // Collect - gather elements into a collection
                System.out.println("\n[INFO] Collect operation");
                Map<Boolean, List<Integer>> partitioned = numbers.stream()
                                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
                System.out.println("Partitioned numbers: " + partitioned);

                // Grouping
                Map<Integer, List<Integer>> grouped = numbers.stream()
                                .collect(Collectors.groupingBy(n -> n % 3));
                System.out.println("Grouped by remainder when divided by 3: " + grouped);

                // ===== 7. STREAM CREATION =====
                System.out.println("\n===== 7. STREAM CREATION =====");

                // Create from collection
                System.out.println("\n[INFO] Stream from collection");
                Stream<String> fruitStream = fruits.stream();
                System.out.println("First fruit: " + fruitStream.findFirst().orElse("None"));

                // Create from values
                System.out.println("\n[INFO] Stream from values");
                Stream<Integer> valueStream = Stream.of(1, 2, 3, 4, 5);
                System.out.println("Values stream sum: " + valueStream.mapToInt(Integer::intValue).sum());

                // Create from array
                System.out.println("\n[INFO] Stream from array");
                String[] fruitArray = { "grape", "kiwi", "lemon" };
                Stream<String> arrayStream = Arrays.stream(fruitArray);
                System.out.println("Fruits uppercase: " +
                                arrayStream.map(String::toUpperCase).collect(Collectors.joining(", ")));

                // Create infinite stream
                System.out.println("\n[INFO] Infinite stream with limit");
                Stream<Integer> infiniteStream = Stream.iterate(1, n -> n + 1);
                System.out.println("First 5 natural numbers: " +
                                infiniteStream.limit(5).collect(Collectors.toList()));

                // Generate random numbers
                System.out.println("\n[INFO] Generate stream");
                Stream<Double> randomStream = Stream.generate(Math::random);
                System.out.println("5 random numbers: " +
                                randomStream.limit(5).map(n -> String.format("%.4f", n))
                                                .collect(Collectors.joining(", ")));

                // ===== 8. INTERMEDIATE VS TERMINAL OPERATIONS =====
                System.out.println("\n===== 8. INTERMEDIATE VS TERMINAL OPERATIONS =====");
                System.out.println("\n[INFO] Demonstrating lazy evaluation");

                Stream<String> nameStream = names.stream()
                                .filter(n -> {
                                        System.out.println("Filtering: " + n);
                                        return n.length() > 3;
                                })
                                .map(n -> {
                                        System.out.println("Mapping: " + n);
                                        return n.toUpperCase();
                                });

                // Nothing is executed yet because no terminal operation
                System.out.println("[INFO] Stream created but no terminal operation yet");

                // Terminal operation triggers execution
                System.out.println("\n[INFO] Executing terminal operation");
                List<String> result = nameStream.collect(Collectors.toList());
                System.out.println("Result: " + result);

                // ===== 9. ADVANCED STREAM OPERATIONS =====
                System.out.println("\n===== 9. ADVANCED STREAM OPERATIONS =====");

                // Distinct - remove duplicates
                System.out.println("\n[INFO] Distinct operation");
                List<Integer> duplicateNumbers = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 5, 5);
                List<Integer> uniqueNumbers = duplicateNumbers.stream()
                                .distinct()
                                .collect(Collectors.toList());
                System.out.println("Unique numbers: " + uniqueNumbers);

                // Sorted - sort elements
                System.out.println("\n[INFO] Sorted operation");
                List<String> sortedFruits = fruits.stream()
                                .sorted()
                                .collect(Collectors.toList());
                System.out.println("Sorted fruits: " + sortedFruits);

                // Custom sort
                List<String> lengthSortedFruits = fruits.stream()
                                .sorted(Comparator.comparing(String::length).thenComparing(String::compareTo))
                                .collect(Collectors.toList());
                System.out.println("Fruits sorted by length then alphabetically: " + lengthSortedFruits);

                // Limit & Skip
                System.out.println("\n[INFO] Limit and Skip operations");
                List<Integer> firstThreeNumbers = numbers.stream()
                                .limit(3)
                                .collect(Collectors.toList());
                System.out.println("First three numbers: " + firstThreeNumbers);

                List<Integer> afterThreeNumbers = numbers.stream()
                                .skip(3)
                                .collect(Collectors.toList());
                System.out.println("Numbers after skipping first three: " + afterThreeNumbers);

                // ===== 10. ADDITIONAL TERMINAL OPERATIONS =====
                System.out.println("\n===== 10. ADDITIONAL TERMINAL OPERATIONS =====");

                // Count
                System.out.println("\n[INFO] Count operation");
                long count = numbers.stream()
                                .filter(n -> n > 5)
                                .count();
                System.out.println("Count of numbers > 5: " + count);

                // Any/All/None Match
                System.out.println("\n[INFO] Match operations");
                boolean anyMatch = numbers.stream().anyMatch(n -> n % 7 == 0);
                boolean allMatch = numbers.stream().allMatch(n -> n > 0);
                boolean noneMatch = numbers.stream().noneMatch(n -> n < 0);

                System.out.println("Any number divisible by 7? " + anyMatch);
                System.out.println("All numbers positive? " + allMatch);
                System.out.println("No negative numbers? " + noneMatch);

                // FindFirst and FindAny
                System.out.println("\n[INFO] Find operations");
                Optional<Integer> firstEven = numbers.stream()
                                .filter(n -> n % 2 == 0)
                                .findFirst();
                System.out.println("First even number: " + firstEven.orElse(-1));

                Optional<String> anyLongFruit = fruits.stream()
                                .filter(f -> f.length() > 6)
                                .findAny();
                System.out.println("Any fruit with length > 6: " + anyLongFruit.orElse("None found"));

                // Min and Max
                System.out.println("\n[INFO] Min and Max operations");
                Optional<Integer> min = numbers.stream()
                                .min(Integer::compare);
                Optional<Integer> max = numbers.stream()
                                .max(Integer::compare);
                System.out.println("Minimum number: " + min.orElse(0));
                System.out.println("Maximum number: " + max.orElse(0));

                // Custom comparator with min/max
                Optional<String> shortestFruit = fruits.stream()
                                .min(Comparator.comparing(String::length));
                Optional<String> longestFruit = fruits.stream()
                                .max(Comparator.comparing(String::length));
                System.out.println("Shortest fruit: " + shortestFruit.orElse("None"));
                System.out.println("Longest fruit: " + longestFruit.orElse("None"));

                // ===== 11. COLLECTORS IN DEPTH =====
                System.out.println("\n===== 11. COLLECTORS IN DEPTH =====");

                // Joining strings
                System.out.println("\n[INFO] Joining collector");
                String fruitString = fruits.stream()
                                .collect(Collectors.joining(", ", "Fruits: [", "]"));
                System.out.println(fruitString);

                // Averaging and summarizing
                System.out.println("\n[INFO] Averaging and summarizing collectors");
                Double averageLength = fruits.stream()
                                .collect(Collectors.averagingInt(String::length));
                System.out.println("Average fruit name length: " + averageLength);

                // // Statistics
                // System.out.println("\n[INFO] Statistics collectors");
                // IntSummaryStatistics lengthStats = fruits.stream()
                // .collect(Collectors.summarizingInt(String::length));
                // System.out.println("Fruit name length statistics: " + lengthStats);

                // Grouping and partitioning
                System.out.println("\n[INFO] Advanced grouping collectors");
                Map<Integer, List<String>> fruitsByLength = fruits.stream()
                                .collect(Collectors.groupingBy(String::length));
                System.out.println("Fruits grouped by length: " + fruitsByLength);

                // Downstream collectors
                Map<Integer, Long> fruitCountByLength = fruits.stream()
                                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
                System.out.println("Count of fruits by length: " + fruitCountByLength);

                // Partitioning - special case of grouping with boolean key
                Map<Boolean, List<String>> startsWithA = fruits.stream()
                                .collect(Collectors.partitioningBy(f -> f.startsWith("a")));
                System.out.println("Fruits partitioned by starting with 'a': " + startsWithA);

                // ===== 12. PARALLEL STREAMS =====
                System.out.println("\n===== 12. PARALLEL STREAMS =====");

                // Creating parallel stream
                System.out.println("\n[INFO] Creating parallel streams");
                List<Integer> largeNumbers = new ArrayList<>();
                for (int i = 0; i < 1000; i++) {
                        largeNumbers.add(i);
                }

                // Measuring performance
                System.out.println("\n[INFO] Comparing sequential vs parallel performance");

                long start = System.nanoTime();
                long sequentialSum = largeNumbers.stream()
                                .filter(n -> n % 2 == 0)
                                .mapToLong(n -> (long) n * n)
                                .sum();
                long end = System.nanoTime();
                System.out.println("Sequential sum: " + sequentialSum);
                System.out.println("Sequential time: " + (end - start) / 1_000_000 + " ms");

                start = System.nanoTime();
                long parallelSum = largeNumbers.parallelStream()
                                .filter(n -> n % 2 == 0)
                                .mapToLong(n -> (long) n * n)
                                .sum();
                end = System.nanoTime();
                System.out.println("Parallel sum: " + parallelSum);
                System.out.println("Parallel time: " + (end - start) / 1_000_000 + " ms");

                // Caution with parallel streams
                System.out.println("\n[INFO] Caution with parallel streams");
                System.out.println("- They may not always be faster due to overhead");
                System.out.println("- Order is not guaranteed unless explicitly preserved");
                System.out.println("- Shared mutable state can cause issues");

                // ===== 13. LAMBDA BEST PRACTICES =====
                System.out.println("\n===== 13. LAMBDA BEST PRACTICES =====");

                System.out.println("\n[INFO] Lambda best practices:");
                System.out.println("1. Keep lambdas short and readable");
                System.out.println("2. Use method references when possible");
                System.out.println("3. Avoid side effects in stream operations");
                System.out.println("4. Consider readability vs conciseness");
                System.out.println("5. Be careful with parallel streams");
                System.out.println("6. Favor strong typing with specialized interfaces");

                System.out.println("\n========== END OF LAMBDA EXPRESSIONS TUTORIAL ==========");
        }
}
