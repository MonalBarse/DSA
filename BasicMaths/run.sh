
#!/bin/bash

# Check if a class name was provided
if [ -z "$1" ]; then
  echo "Usage: $0 <MainClassName>"
  exit 1
fi

# Directory paths
BIN_DIR="bin"

# Run the specified Java class from the bin directory
java -cp $BIN_DIR com.monal.$1

# Capture the exit status of the Java program
JAVA_EXIT_STATUS=$?

# Check if the Java program ran successfully
if [ $JAVA_EXIT_STATUS -eq 0 ]; then
    echo "Program ran successfully."
else
    echo "Program encountered an error."
fi




# To run a problem1.java file, execute the following command:
# ./run.sh problem1
# This will first compile using the compile.sh script and then run this class using the java command.

