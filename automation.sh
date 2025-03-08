#!/bin/bash
DSA_ROOT="$HOME/Desktop/MAIN/DSA"
SCRIPT_NAME="automation.sh"

# Color codes for better readability
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Function to compile Java file (replaces compile.sh)
compile_java_file() {
    local topic_dir="$1"
    local java_file_name="$2"

    cd "$topic_dir" || return

    # Directory paths
    SRC_DIR="src"
    BIN_DIR="out"

    # Create the bin directory if it doesn't exist
    mkdir -p $BIN_DIR

    # Remove .java extension if provided in the argument
    FILENAME=$(basename "$java_file_name" .java)

    # Find the Java file (search in any subfolder under src/com/monal)
    JAVA_FILE=$(find $SRC_DIR/com/monal -name "${FILENAME}.java")

    # Check if the file was found
    if [ -z "$JAVA_FILE" ]; then
        echo "Error: File ${FILENAME}.java not found in any subfolder of $SRC_DIR/com/monal."
        return 1
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

        # Extract the main class name from package path and filename
        MAIN_CLASS="${PACKAGE_PATH}.${FILENAME}"
        MAIN_CLASS=$(echo "$MAIN_CLASS" | tr '/' '.')

        # Ask if user wants to run the compiled program
        echo "Run the program? (y/n)"
        read -r RUN_OPTION
        if [ "$RUN_OPTION" = "y" ]; then
            java -cp $BIN_DIR $MAIN_CLASS
            echo ""  # Add a blank line for better separation
        fi

        return 0
    else
        echo "Compilation failed."
        return 1
    fi
}

# Function to run compiled Java class (replaces run.sh)
run_java_class() {
    local topic_dir="$1"
    local class_name="$2"

    cd "$topic_dir" || return

    # Directory paths
    BIN_DIR="out"

    # Create the bin directory if it doesn't exist
    mkdir -p $BIN_DIR

    # Remove .class extension if provided in the argument
    CLASS_NAME=$(basename "$class_name" .class)

    # Find the class file
    CLASS_FILE=$(find $BIN_DIR/com/monal -name "${CLASS_NAME}.class")

    # Check if the class file was found
    if [ -z "$CLASS_FILE" ]; then
        echo "Error: Class file for ${CLASS_NAME} not found. Make sure you've compiled it first."
        return 1
    fi

    # Get the package path and construct the fully qualified class name
    PACKAGE_PATH=$(dirname ${CLASS_FILE#$BIN_DIR/})
    FULLY_QUALIFIED_NAME="${PACKAGE_PATH}.${CLASS_NAME}"
    FULLY_QUALIFIED_NAME=$(echo "$FULLY_QUALIFIED_NAME" | tr '/' '.')

    # Run the Java program
    echo "Running: $FULLY_QUALIFIED_NAME"
    java -cp $BIN_DIR $FULLY_QUALIFIED_NAME

    # Capture the exit status of the Java program
    JAVA_EXIT_STATUS=$?

    # Check if the Java program ran successfully
    if [ $JAVA_EXIT_STATUS -eq 0 ]; then
        echo "Program ran successfully."
        return 0
    else
        echo "Program encountered an error."
        return 1
    fi
}

# Function to detect preferred editor
detect_editor() {
    # Check what editors are currently running
    if pgrep -l "code" > /dev/null; then
        echo "vscode"
    elif pgrep -l "intellij" > /dev/null || pgrep -l "idea" > /dev/null; then
        echo "intellij"
    elif pgrep -l "eclipse" > /dev/null; then
        echo "eclipse"
    elif pgrep -l "gedit" > /dev/null; then
        echo "gedit"
    elif pgrep -l "sublime_text" > /dev/null; then
        echo "sublime"
    elif pgrep -l "atom" > /dev/null; then
        echo "atom"
    # If no running editors, check for installed editors
    elif command -v code &> /dev/null; then
        echo "vscode"
    elif command -v intellij-idea-community &> /dev/null || command -v idea &> /dev/null; then
        echo "intellij"
    elif command -v eclipse &> /dev/null; then
        echo "eclipse"
    elif command -v gedit &> /dev/null; then
        echo "gedit"
    elif command -v sublime_text &> /dev/null; then
        echo "sublime"
    elif command -v atom &> /dev/null; then
        echo "atom"
    elif command -v nano &> /dev/null; then
        echo "nano"
    elif command -v vim &> /dev/null; then
        echo "vim"
    else
        echo "default"
    fi
}

# Function to open file in detected editor
open_in_editor() {
    local file_path="$1"
    local editor_type=$(detect_editor)

    case $editor_type in
        "vscode")
            code "$file_path"
            ;;
        "intellij")
            if command -v idea &> /dev/null; then
                idea "$file_path"
            else
                intellij-idea-community "$file_path"
            fi
            ;;
        "eclipse")
            eclipse "$file_path"
            ;;
        "gedit")
            gedit "$file_path" &
            ;;
        "sublime")
            sublime_text "$file_path"
            ;;
        "atom")
            atom "$file_path"
            ;;
        "nano")
            nano "$file_path"
            ;;
        "vim")
            vim "$file_path"
            ;;
        *)
            xdg-open "$file_path" 2>/dev/null || open "$file_path" 2>/dev/null
            ;;
    esac

    echo -e "Opened file in ${GREEN}$(echo $editor_type | tr '[:lower:]' '[:upper:]')${NC}"
}

# Function to create a new project
create_project() {
    echo -e "${BLUE}Creating a new DSA topic folder${NC}"
    read -p "Enter topic name (e.g., OOP, Recursion): " topic_name

    # Create the main project directory
    topic_dir="$DSA_ROOT/$topic_name"
    if [ -d "$topic_dir" ]; then
        echo -e "${YELLOW}Warning:${NC} Topic '$topic_name' already exists!"
        read -p "Do you want to continue? (y/n): " continue_choice
        if [[ $continue_choice != "y" && $continue_choice != "Y" ]]; then
            echo "Operation cancelled."
            return
        fi
    else
        mkdir -p "$topic_dir"
        echo -e "${GREEN}Created${NC} topic directory: $topic_dir"
    fi

    # Create standard directories
    mkdir -p "$topic_dir/src/com/monal"
    mkdir -p "$topic_dir/out"

    echo -e "${GREEN}Created${NC} standard directory structure"

    # Ask for subfolders inside src/com/monal
    echo -e "${BLUE}Do you want to add subfolders inside src/com/monal?${NC}"
    read -p "Add subfolders? (y/n): " add_subfolders

    if [[ $add_subfolders == "y" || $add_subfolders == "Y" ]]; then
        read -p "Enter subfolder name: " subfolder_name
        mkdir -p "$topic_dir/src/com/monal/$subfolder_name"
        src_path="$topic_dir/src/com/monal/$subfolder_name"
        package_path="com.monal.$subfolder_name"
    else
        src_path="$topic_dir/src/com/monal"
        package_path="com.monal"
    fi

    # Create Java file
    read -p "Enter Java file name (without .java extension): " java_file_name
    java_file_path="$src_path/${java_file_name}.java"

    # Create the Java file with package declaration
    cat > "$java_file_path" << EOF
package $package_path;

public class $java_file_name {
    public static void main(String[] args) {
        System.out.println("Hello from $java_file_name!");
    }
}
EOF

    echo -e "${GREEN}Created${NC} $java_file_path"

    # Open the Java file with the detected editor
    open_in_editor "$java_file_path"

    echo -e "${GREEN}Project setup complete!${NC}"
}

# Function to add a subtopic to an existing topic
add_subtopic() {
    echo -e "${BLUE}Adding a subtopic to an existing topic${NC}"

    # List available topics
    echo -e "${GREEN}Available topics:${NC}"
    topics=$(get_valid_topics)

    if [ -z "$topics" ]; then
        echo -e "${RED}No existing topics found!${NC}"
        return
    fi

    echo "$topics"

    # Get topic choice
    read -p "Enter topic name: " topic_name
    topic_dir="$DSA_ROOT/$topic_name"

    if [ ! -d "$topic_dir" ]; then
        echo -e "${RED}Error:${NC} Topic '$topic_name' does not exist!"
        return
    fi

    # Ask for the subtopic name
    read -p "Enter subtopic name : " subtopic_name

    # Create the subfolder inside src/com/monal
    subtopic_dir="$topic_dir/src/com/monal/$subtopic_name"
    mkdir -p "$subtopic_dir"

    echo -e "${GREEN}Created${NC} subtopic directory: $subtopic_dir"

    # Ask to create a Java file
    read -p "Do you want to add a Java file to this subtopic? (y/n): " add_java_file
    if [[ $add_java_file == "y" || $add_java_file == "Y" ]]; then
        read -p "Enter Java file name (without .java extension): " java_file_name
        java_file_path="$subtopic_dir/${java_file_name}.java"

        # Create the Java file with package declaration
        package_path="com.monal.$subtopic_name"
        cat > "$java_file_path" << EOF
package $package_path;

public class $java_file_name {
    public static void main(String[] args) {
        System.out.println("Hello from $java_file_name in subtopic $subtopic_name!");
    }
}
EOF
        echo -e "${GREEN}Created${NC} $java_file_path"

        # Open in editor
        open_in_editor "$java_file_path"
    fi
}

# Function to filter valid topic directories (those with Java files)
get_valid_topics() {
    find "$DSA_ROOT" -maxdepth 1 -type d | while read dir; do
        # Skip hidden directories and the DSA root itself
        if [[ "$(basename "$dir")" != "." && "$(basename "$dir")" != ".." && "$(basename "$dir")" != "$(basename "$DSA_ROOT")" && ! "$(basename "$dir")" =~ ^\. ]]; then
            # Check if the directory contains any Java files
            if find "$dir" -name "*.java" -print -quit | grep -q .; then
                basename "$dir"
            fi
        fi
    done
}

# Function to help with compilation
compile_file() {
    echo -e "${BLUE}Compile a Java file${NC}"

    # List all valid topic folders
    echo -e "${GREEN}Available topics:${NC}"
    topics=$(get_valid_topics)

    # Check if any topics were found
    if [ -z "$topics" ]; then
        echo -e "${RED}No topics with Java files found!${NC}"
        return
    fi

    # Display topics
    echo "$topics"

    # Get topic choice
    read -p "Enter topic name: " topic_name
    topic_dir="$DSA_ROOT/$topic_name"

    if [ ! -d "$topic_dir" ]; then
        echo -e "${RED}Error:${NC} Topic '$topic_name' does not exist!"
        return
    fi

    # Create out directory if it doesn't exist
    mkdir -p "$topic_dir/out"

    # List Java files in the topic directory with their full paths relative to src/com/monal
    echo -e "${GREEN}Java files in this topic:${NC}"
    java_files=$(find "$topic_dir/src" -name "*.java")

    # Check if any Java files were found
    if [ -z "$java_files" ]; then
        echo -e "${RED}No Java files found in this topic!${NC}"
        return
    fi

    # Display Java files with their packages
    echo "$java_files" | while read file; do
        rel_path=${file#$topic_dir/src/}
        package_path=$(dirname "$rel_path")
        file_name=$(basename "$file" .java)
        echo -e "${BLUE}${package_path}${NC} - ${file_name}"
    done

    read -p "Enter Java file name (without .java extension): " java_file_name

    # Check if the file is a directory
    if [ -d "$topic_dir/src/com/monal/$java_file_name" ]; then
        echo -e "${RED}Error:${NC} '$java_file_name' is a directory, not a Java file!"

        # List Java files in that directory
        echo -e "${YELLOW}Java files in $java_file_name directory:${NC}"
        find "$topic_dir/src/com/monal/$java_file_name" -name "*.java" | while read file; do
            echo "- $(basename "$file" .java)"
        done

        read -p "Enter Java file name from the list above: " java_file_name
    fi

    # Run the compilation function
    compile_java_file "$topic_dir" "$java_file_name"
}

# Function to run a compiled class
run_class() {
    echo -e "${BLUE}Run a compiled Java class${NC}"

    # List all valid topic folders
    echo -e "${GREEN}Available topics:${NC}"
    topics=$(get_valid_topics)

    # Check if any topics were found
    if [ -z "$topics" ]; then
        echo -e "${RED}No topics with Java files found!${NC}"
        return
    fi

    # Display topics
    echo "$topics"

    # Get topic choice
    read -p "Enter topic name: " topic_name
    topic_dir="$DSA_ROOT/$topic_name"

    if [ ! -d "$topic_dir" ]; then
        echo -e "${RED}Error:${NC} Topic '$topic_name' does not exist!"
        return
    fi

    # Create out directory if it doesn't exist
    mkdir -p "$topic_dir/out"

    # List class files in the topic directory with their packages
    echo -e "${GREEN}Compiled classes in this topic:${NC}"
    class_files=$(find "$topic_dir/out" -name "*.class" 2>/dev/null)

    # Check if any class files were found
    if [ -z "$class_files" ]; then
        echo -e "${YELLOW}No compiled classes found. Let's compile a file first.${NC}"
        compile_file
        return
    fi

    # Display class files with their packages
    echo "$class_files" | while read file; do
        rel_path=${file#$topic_dir/out/}
        package_path=$(dirname "$rel_path")
        class_name=$(basename "$file" .class)
        echo -e "${BLUE}${package_path}${NC} - ${class_name}"
    done

    read -p "Enter class name (without .class extension): " class_name

    # Run the class using our function
    run_java_class "$topic_dir" "$class_name"
}

# Quick compilation and run in one step
quick_compile_run() {
    echo -e "${BLUE}Quick compile and run${NC}"

    # List all valid topic folders
    echo -e "${GREEN}Available topics:${NC}"
    topics=$(get_valid_topics)

    # Check if any topics were found
    if [ -z "$topics" ]; then
        echo -e "${RED}No topics with Java files found!${NC}"
        return
    fi

    # Display topics
    echo "$topics"

    # Get topic choice
    read -p "Enter topic name: " topic_name
    topic_dir="$DSA_ROOT/$topic_name"

    if [ ! -d "$topic_dir" ]; then
        echo -e "${RED}Error:${NC} Topic '$topic_name' does not exist!"
        return
    fi

    # Create out directory if it doesn't exist
    mkdir -p "$topic_dir/out"

    # List Java files in the topic directory with their paths
    echo -e "${GREEN}Java files in this topic:${NC}"
    java_files=$(find "$topic_dir/src" -name "*.java")

    # Check if any Java files were found
    if [ -z "$java_files" ]; then
        echo -e "${RED}No Java files found in this topic!${NC}"
        return
    fi

    # Display Java files with their packages
    echo "$java_files" | while read file; do
        rel_path=${file#$topic_dir/src/}
        package_path=$(dirname "$rel_path")
        file_name=$(basename "$file" .java)
        echo -e "${BLUE}${package_path}${NC} - ${file_name}"
    done

    read -p "Enter Java file name (without .java extension): " java_file_name

    # Check if the file is a directory
    if [ -d "$topic_dir/src/com/monal/$java_file_name" ]; then
        echo -e "${RED}Error:${NC} '$java_file_name' is a directory, not a Java file!"

        # List Java files in that directory
        echo -e "${YELLOW}Java files in $java_file_name directory:${NC}"
        find "$topic_dir/src/com/monal/$java_file_name" -name "*.java" | while read file; do
            echo "- $(basename "$file" .java)"
        done

        read -p "Enter Java file name from the list above: " java_file_name
    fi

    # Compile the file using our function, which includes option to run
    compile_java_file "$topic_dir" "$java_file_name"
}

list_topics() {
    echo -e "${BLUE}Listing all DSA topics and their Java files${NC}"

    # Get all valid topics
    topics=$(get_valid_topics)

    # Check if any topics were found
    if [ -z "$topics" ]; then
        echo -e "${RED}No topics with Java files found!${NC}"
    else
        echo "$topics" | while read topic; do
            topic_dir="$DSA_ROOT/$topic"
            echo -e "${GREEN}Topic:${NC} $topic"

            # Find all Java files and group by package
            echo "  Files by package:"
            find "$topic_dir/src" -name "*.java" 2>/dev/null | sort | while read file; do
                rel_path=${file#$topic_dir/src/}
                package_path=$(dirname "$rel_path")
                file_name=$(basename "$file" .java)
                echo -e "    ${BLUE}${package_path}${NC} - ${file_name}"
            done
            echo ""
        done
    fi

    # Pause before returning to menu
    read -p "Press Enter to return to the main menu..."
}

main_menu() {
    if [[ $1 != "noclear" ]]; then
        clear
    fi

    echo -e "${BLUE}DSA Project Automation Tool${NC}"
    echo "------------------------"
    echo "1. Create new topic folder"
    echo "2. Add subtopic to existing topic"
    echo "3. Compile a Java file"
    echo "4. Run a compiled class"
    echo "5. Quick compile and run"
    echo "6. List all topics and files"
    echo "7. Exit"
    echo

    read -p "Enter your choice (1-7): " choice

    case $choice in
        1) create_project ;;
        2) add_subtopic ;;
        3) compile_file ;;
        4) run_class ;;
        5) quick_compile_run ;;
        6) list_topics ;;
        7) echo "Exiting..."; exit 0 ;;
        *) echo -e "${YELLOW}Invalid choice.${NC}" ;;
    esac

    read -p "Press Enter to return to the main menu..."
    main_menu
}

# Start the main menu
main_menu