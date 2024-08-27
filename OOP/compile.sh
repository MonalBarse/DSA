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

# Find the Java file
JAVA_FILE=$(find $SRC_DIR/com/monal -name "$1.java")

# Check if the file was found
if [ -z "$JAVA_FILE" ]; then
  echo "Error: File $1.java not found in any subfolder of $SRC_DIR/com/monal."
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
else
  echo "Compilation failed."
fi
