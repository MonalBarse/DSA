#!/bin/bash
# Check if a filename was provided
if [ -z "$1" ]; then
  echo "Usage: $0 <JavaFileName>"
  exit 1
fi
# Directory paths
SRC_DIR="src"
BIN_DIR="out"
# Create the bin directory if it doesn't exist
mkdir -p $BIN_DIR
# Remove .java extension if provided in the argument
FILENAME=$(basename "$1" .java)
# Find the Java file (search in any subfolder under src/com/monal)
JAVA_FILE=$(find $SRC_DIR/com/monal -name "${FILENAME}.java")
# Check if the file was found
if [ -z "$JAVA_FILE" ]; then
  echo "Error: File ${FILENAME}.java not found in any subfolder of $SRC_DIR/com/monal."
  exit 1
fi
# Get the package path
PACKAGE_PATH=$(dirname ${JAVA_FILE#$SRC_DIR/})
# Create the package directory in the output folder
mkdir -p "$BIN_DIR/$PACKAGE_PATH"
# Compile the Java file
javac -d $BIN_DIR $JAVA_FILE
# Check if compilation was successful
if [ $? -eq 0 ]; then
  echo "Compilation successful. Class file saved in $BIN_DIR/$PACKAGE_PATH"
  # Extract the main class name (assuming it matches the filename)
  MAIN_CLASS="com.monal.${FILENAME}"
  # Ask if user wants to run the compiled program
  echo "Run the program? (y/n)"
  read -r RUN_OPTION
  if [ "$RUN_OPTION" = "y" ]; then
    java -cp $BIN_DIR $MAIN_CLASS
    echo ""  # Add a blank line for better separation

  fi
else
  echo "Compilation failed."
fi
