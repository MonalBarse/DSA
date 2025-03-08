#!/bin/bash
# Check if a filename was provided
if [ -z "$1" ]; then
  echo "Usage: $0 <JavaClassName>"
  exit 1
fi
# Directory paths
BIN_DIR="out"
# Create the bin directory if it doesn't exist
mkdir -p $BIN_DIR
# Remove .class extension if provided in the argument
CLASS_NAME=$(basename "$1" .class)
# Find the class file
CLASS_FILE=$(find $BIN_DIR/com/monal -name "${CLASS_NAME}.class")
# Check if the class file was found
if [ -z "$CLASS_FILE" ]; then
  echo "Error: Class file for ${CLASS_NAME} not found. Make sure you've compiled it first."
  exit 1
fi
# Get the package path and construct the fully qualified class name
PACKAGE_PATH=$(dirname ${CLASS_FILE#$BIN_DIR/})
FULLY_QUALIFIED_NAME="${PACKAGE_PATH}/${CLASS_NAME}"
FULLY_QUALIFIED_NAME=$(echo "$FULLY_QUALIFIED_NAME" | tr '/' '.')
# Run the Java program
echo "Running: $FULLY_QUALIFIED_NAME"
java -cp $BIN_DIR $FULLY_QUALIFIED_NAME
# Capture the exit status of the Java program
JAVA_EXIT_STATUS=$?
# Check if the Java program ran successfully
if [ $JAVA_EXIT_STATUS -eq 0 ]; then
    echo "Program ran successfully."
else
    echo "Program encountered an error."
fi
