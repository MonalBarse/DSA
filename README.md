# INDEX

1. BasicMaths/
2. BitManipulation/
3. Recursion/
   ....(will be updated)

## Structure

- `src/`: Contains all the Java source files.
- `bin/`: Contains the compiled Java class files.

```bash

    git clone https://github.com/MonalBarse/DSA
```

## Bash Scripts

### compile.sh

A script to compile all Java files in the `src/` directory and place the compiled `.class` files in the `bin/` directory.

### run.sh

A script to compile the Java files using `compile.sh` and then run a specified Java class.

## Add a ./compile.sh file to each directory

- for eg. in Recusion directory, add a compile.sh file

```bash

    #!/bin/bash

    # Directory paths
    SRC_DIR="src"
    BIN_DIR="bin"

    # Create the bin directory if it doesn't exist
    mkdir -p $BIN_DIR

    # Compile all .java files in src/ and place .class files into bin/
    javac -d $BIN_DIR $SRC_DIR/com/monal/*.java

    # Check if compilation was successful
    if [ $? -eq 0 ]; then
    echo "Compilation successful."
    else
    echo "Compilation failed."
    fi


```

- Create a run.sh file in the same directory

```bash


#!/bin/bash

# Compile Java files using compile.sh
./compile.sh

# Check if compilation was successful
if [ $? -eq 0 ]; then
    # Navigate to the bin/ directory
    cd ../bin

    # Run the specified Java class
    java com.monal.$1

    # Navigate back to the original directory
    cd -
else
    echo "Compilation failed. Please fix errors before running."
fi

```

- Make sure both compile.sh and run.sh have executable permissions (chmod +x compile.sh run.sh).

## How to Use

1. **Compile Java Files**:

   ```sh
   ./scripts/compile.sh
   ```

2. **Run a Java Class**:
   ```sh
   ./scripts/run.sh <ClassName>
   ```
