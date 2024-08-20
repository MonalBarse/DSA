#!/bin/bash

# Directory paths
SRC_DIR="src"
BIN_DIR="out"

# Create the bin directory if it doesn't exist
mkdir -p $BIN_DIR

# Compile all .java files in src/ and place .class files into out/
javac -d $BIN_DIR $SRC_DIR/com/monal/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
  echo "Compilation successful."
else
  echo "Compilation failed."
fi

