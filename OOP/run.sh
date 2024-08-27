#!/bin/bash

# Check if a filename was provided
if [ -z "$1" ]; then
  echo "Usage: $0 <JavaFileName>"
  exit 1
fi

# Directory paths
BIN_DIR="out"

# Find the class file
CLASS_FILE=$(find $BIN_DIR/com/monal -name "$1.class")

# Check if the class file was found
if [ -z "$CLASS_FILE" ]; then
  echo "Error: Class file for $1 not found. Make sure you've compiled it first."
  exit 1
fi

# Get the package name
PACKAGE_NAME=$(dirname ${CLASS_FILE#$BIN_DIR/} | tr '/' '.')

# Run the Java program
java -cp $BIN_DIR $PACKAGE_NAME.$1

# Capture the exit status of the Java program
JAVA_EXIT_STATUS=$?

# Check if the Java program ran successfully
if [ $JAVA_EXIT_STATUS -eq 0 ]; then
    echo "Program ran successfully."
else
    echo "Program encountered an error."
fi
