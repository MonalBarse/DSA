#!/bin/bash
DSA_ROOT="$HOME/Desktop/DSA"
SCRIPT_NAME="automation.sh"

# Color codes for better readability
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Function to handle git operations
git_operations() {
    echo -e "${BLUE}Git Operations${NC}"

    # Check if git is installed
    if ! command -v git &> /dev/null; then
        echo -e "${RED}Error:${NC} Git is not installed or not in your PATH."
        return 1
    fi

    # Options for git operations
    echo "1. Commit and push all changes in DSA directory"
    echo "2. Commit and push changes in a specific topic"
    echo "3. Commit and push changes in a specific subtopic"
    echo "4. Return to main menu"

    read -p "Enter your choice (1-4): " git_choice

    case $git_choice in
        1)
            # Commit all changes in DSA root
            cd "$DSA_ROOT" || return

            # Check if this is a git repository
            if [ ! -d ".git" ]; then
                echo -e "${YELLOW}Warning:${NC} This is not a git repository."
                read -p "Do you want to initialize a git repository? (y/n): " init_choice
                if [[ $init_choice == "y" || $init_choice == "Y" ]]; then
                    git init
                    echo -e "${GREEN}Git repository initialized${NC}"
                else
                    return
                fi
            fi

            # Add all changes
            git add .

            # Get commit message
            read -p "Enter your commit message: " commit_message

            # Commit changes
            git commit -m "$commit_message"

            # Push changes
            echo -e "${BLUE}Pushing changes...${NC}"
            git push

            if [ $? -eq 0 ]; then
                echo -e "${GREEN}Successfully pushed changes!${NC}"
            else
                echo -e "${YELLOW}Note:${NC} If this is your first push, you might need to set the upstream branch."
                read -p "Do you want to set upstream and push? (y/n): " upstream_choice
                if [[ $upstream_choice == "y" || $upstream_choice == "Y" ]]; then
                    read -p "Enter remote name (usually 'origin'): " remote_name
                    read -p "Enter branch name (usually 'main' or 'master'): " branch_name
                    git push --set-upstream "$remote_name" "$branch_name"
                fi
            fi
            ;;

        2)
            # Commit changes in a specific topic
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

            # Change to topic directory
            cd "$topic_dir" || return

            # Check if parent directory is a git repository
            if [ ! -d "$DSA_ROOT/.git" ]; then
                echo -e "${YELLOW}Warning:${NC} DSA root is not a git repository."
                read -p "Do you want to initialize a git repository in DSA root? (y/n): " init_choice
                if [[ $init_choice == "y" || $init_choice == "Y" ]]; then
                    cd "$DSA_ROOT" || return
                    git init
                    echo -e "${GREEN}Git repository initialized in DSA root${NC}"
                    cd "$topic_dir" || return
                else
                    return
                fi
            fi

            # Add changes in this topic
            cd "$DSA_ROOT" || return
            git add "$topic_name"

            # Get commit message
            read -p "Enter your commit message: " commit_message

            # Commit changes
            git commit -m "$commit_message"

            # Push changes
            echo -e "${BLUE}Pushing changes...${NC}"
            git push

            if [ $? -eq 0 ]; then
                echo -e "${GREEN}Successfully pushed changes for topic '$topic_name'!${NC}"
            else
                echo -e "${YELLOW}Note:${NC} If this is your first push, you might need to set the upstream branch."
                read -p "Do you want to set upstream and push? (y/n): " upstream_choice
                if [[ $upstream_choice == "y" || $upstream_choice == "Y" ]]; then
                    read -p "Enter remote name (usually 'origin'): " remote_name
                    read -p "Enter branch name (usually 'main' or 'master'): " branch_name
                    git push --set-upstream "$remote_name" "$branch_name"
                fi
            fi
            ;;

        3)
            # Commit changes in a specific subtopic
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

            # List subtopics
            echo -e "${GREEN}Available subtopics in $topic_name:${NC}"
            subtopics=$(find "$topic_dir/src/com/monal" -type d -mindepth 1 -maxdepth 1 2>/dev/null | xargs -n1 basename 2>/dev/null)

            if [ -z "$subtopics" ]; then
                echo -e "${RED}No subtopics found in '$topic_name'!${NC}"
                return
            fi

            echo "$subtopics"

            # Get subtopic choice
            read -p "Enter subtopic name: " subtopic_name
            subtopic_dir="$topic_dir/src/com/monal/$subtopic_name"

            if [ ! -d "$subtopic_dir" ]; then
                echo -e "${RED}Error:${NC} Subtopic '$subtopic_name' does not exist!${NC}"
                return
            fi

            # Check if parent directory is a git repository
            if [ ! -d "$DSA_ROOT/.git" ]; then
                echo -e "${YELLOW}Warning:${NC} DSA root is not a git repository."
                read -p "Do you want to initialize a git repository in DSA root? (y/n): " init_choice
                if [[ $init_choice == "y" || $init_choice == "Y" ]]; then
                    cd "$DSA_ROOT" || return
                    git init
                    echo -e "${GREEN}Git repository initialized in DSA root${NC}"
                else
                    return
                fi
            fi

            # Add changes in this subtopic
            cd "$DSA_ROOT" || return
            git add "$topic_name/src/com/monal/$subtopic_name"

            # Get commit message
            read -p "Enter your commit message: " commit_message

            # Commit changes
            git commit -m "$commit_message"

            # Push changes
            echo -e "${BLUE}Pushing changes...${NC}"
            git push

            if [ $? -eq 0 ]; then
                echo -e "${GREEN}Successfully pushed changes for subtopic '$subtopic_name' in topic '$topic_name'!${NC}"
            else
                echo -e "${YELLOW}Note:${NC} If this is your first push, you might need to set the upstream branch."
                read -p "Do you want to set upstream and push? (y/n): " upstream_choice
                if [[ $upstream_choice == "y" || $upstream_choice == "Y" ]]; then
                    read -p "Enter remote name (usually 'origin'): " remote_name
                    read -p "Enter branch name (usually 'main' or 'master'): " branch_name
                    git push --set-upstream "$remote_name" "$branch_name"
                fi
            fi
            ;;

        4)
            # Return to main menu
            return
            ;;

        *)
            echo -e "${YELLOW}Invalid choice.${NC}"
            ;;
    esac
}
# Function to compile Java file (replaces compile.sh)
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

    # Find all Java files in the topic directory
    java_files=$(find "$topic_dir/src" -name "*.java")

    # Check if any Java files were found
    if [ -z "$java_files" ]; then
        echo -e "${RED}No Java files found in this topic!${NC}"
        return
    fi

    # Create a numbered list of Java files with their full package paths
    echo -e "${GREEN}Java files in this topic:${NC}"
    file_counter=1
    declare -a file_paths

    while IFS= read -r file; do
        rel_path=${file#$topic_dir/src/}
        package_path=$(dirname "$rel_path")
        file_name=$(basename "$file" .java)
        echo -e "$file_counter. ${BLUE}${package_path}${NC} - ${file_name}"
        file_paths[$file_counter]="$file"
        ((file_counter++))
    done <<< "$java_files"

    # Ask user to select file by number
    read -p "Enter the number of the Java file to compile: " file_number

    # Validate input
    if ! [[ "$file_number" =~ ^[0-9]+$ ]] || [ "$file_number" -lt 1 ] || [ "$file_number" -ge "$file_counter" ]; then
        echo -e "${RED}Error:${NC} Invalid selection!"
        return
    fi

    # Get the selected file path
    selected_file="${file_paths[$file_number]}"
    java_file_name=$(basename "$selected_file" .java)

    # Extract the subtopic path for more accurate file finding
    subtopic_path=${selected_file#$topic_dir/src/}
    subtopic_path=$(dirname "$subtopic_path")

    # Run the compilation function with the proper path context
    echo -e "${GREEN}Compiling${NC} $java_file_name from package $subtopic_path"

    # Change to the topic directory
    cd "$topic_dir" || return

    # Directory paths
    SRC_DIR="src"
    BIN_DIR="out"

    # Create the bin directory if it doesn't exist
    mkdir -p "$BIN_DIR"

    # Use the exact file path we found earlier
    JAVA_FILE=${selected_file#$topic_dir/}

    # Get the package path
    PACKAGE_PATH=$(dirname ${JAVA_FILE#$SRC_DIR/})

    # Create the package directory in the output folder
    mkdir -p "$BIN_DIR/$PACKAGE_PATH"

    # Compile the Java file
    javac -d "$BIN_DIR" "$JAVA_FILE"

    # Check if compilation was successful
    if [ $? -eq 0 ]; then
        echo "Compilation successful. Class file saved in $BIN_DIR/$PACKAGE_PATH"

        # Extract the main class name from package path and filename
        MAIN_CLASS="${PACKAGE_PATH}.${java_file_name}"
        MAIN_CLASS=$(echo "$MAIN_CLASS" | tr '/' '.')

        # Store these values for potential recompilation
        LAST_COMPILED_FILE="$JAVA_FILE"
        LAST_COMPILED_CLASS="$MAIN_CLASS"
        LAST_TOPIC_DIR="$topic_dir"

        # Ask if user wants to run the compiled program
        echo "Run the program? (y/n)"
        read -r RUN_OPTION
        if [ "$RUN_OPTION" = "y" ]; then
            java -cp "$BIN_DIR" "$MAIN_CLASS"
            echo ""  # Add a blank line for better separation

            # Ask if user wants to recompile and run again
            recompile_loop "$topic_dir" "$JAVA_FILE" "$MAIN_CLASS"
        fi

        return 0
    else
        echo "Compilation failed."
        return 1
    fi
}

# New function to handle recompile loop
recompile_loop() {
    local topic_dir="$1"
    local java_file="$2"
    local main_class="$3"

    while true; do
        echo -e "${GREEN}Options:${NC}"
        echo "1. Recompile and run"
        echo "2. Return to main menu"
        read -p "Enter your choice (1-2): " recompile_choice

        case $recompile_choice in
            1)
                # Recompile the same file
                echo -e "${GREEN}Recompiling${NC} $(basename "$java_file")..."
                cd "$topic_dir" || return

                # Directory paths
                BIN_DIR="out"

                # Compile the Java file
                javac -d "$BIN_DIR" "$java_file"

                # Check if compilation was successful
                if [ $? -eq 0 ]; then
                    echo "Compilation successful."

                    # Run the program automatically
                    echo -e "${BLUE}Running program...${NC}"
                    java -cp "$BIN_DIR" "$main_class"
                    echo ""  # Add a blank line for better separation
                else
                    echo "Compilation failed."
                fi
                ;;
            2)
                return 0
                ;;
            *)
                echo -e "${YELLOW}Invalid choice. Please enter 1 or 2.${NC}"
                ;;
        esac
    done
}

# Modified quick compile and run
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

    # Find all Java files in the topic directory
    java_files=$(find "$topic_dir/src" -name "*.java")

    # Check if any Java files were found
    if [ -z "$java_files" ]; then
        echo -e "${RED}No Java files found in this topic!${NC}"
        return
    fi

    # Create a numbered list of Java files with their full package paths
    echo -e "${GREEN}Java files in this topic:${NC}"
    file_counter=1
    declare -a file_paths

    while IFS= read -r file; do
        rel_path=${file#$topic_dir/src/}
        package_path=$(dirname "$rel_path")
        file_name=$(basename "$file" .java)
        echo -e "$file_counter. ${BLUE}${package_path}${NC} - ${file_name}"
        file_paths[$file_counter]="$file"
        ((file_counter++))
    done <<< "$java_files"

    # Ask user to select file by number
    read -p "Enter the number of the Java file to compile and run: " file_number

    # Validate input
    if ! [[ "$file_number" =~ ^[0-9]+$ ]] || [ "$file_number" -lt 1 ] || [ "$file_number" -ge "$file_counter" ]; then
        echo -e "${RED}Error:${NC} Invalid selection!"
        return
    fi

    # Get the selected file path
    selected_file="${file_paths[$file_number]}"
    java_file_name=$(basename "$selected_file" .java)

    # Extract the subtopic path for more accurate file finding
    subtopic_path=${selected_file#$topic_dir/src/}
    subtopic_path=$(dirname "$subtopic_path")

    # Run the compilation and execution directly
    echo -e "${GREEN}Compiling and running${NC} $java_file_name from package $subtopic_path"

    # Change to the topic directory
    cd "$topic_dir" || return

    # Directory paths
    SRC_DIR="src"
    BIN_DIR="out"

    # Create the bin directory if it doesn't exist
    mkdir -p "$BIN_DIR"

    # Use the exact file path we found earlier
    JAVA_FILE=${selected_file#$topic_dir/}

    # Get the package path
    PACKAGE_PATH=$(dirname ${JAVA_FILE#$SRC_DIR/})

    # Create the package directory in the output folder
    mkdir -p "$BIN_DIR/$PACKAGE_PATH"

    # Compile the Java file
    javac -d "$BIN_DIR" "$JAVA_FILE"

    # Check if compilation was successful
    if [ $? -eq 0 ]; then
        echo "Compilation successful. Class file saved in $BIN_DIR/$PACKAGE_PATH"

        # Extract the main class name from package path and filename
        MAIN_CLASS="${PACKAGE_PATH}.${java_file_name}"
        MAIN_CLASS=$(echo "$MAIN_CLASS" | tr '/' '.')

        # Store these values for potential recompilation
        LAST_COMPILED_FILE="$JAVA_FILE"
        LAST_COMPILED_CLASS="$MAIN_CLASS"
        LAST_TOPIC_DIR="$topic_dir"

        # Run the program automatically
        echo -e "${BLUE}Running program...${NC}"
        java -cp "$BIN_DIR" "$MAIN_CLASS"
        echo ""  # Add a blank line for better separation

        # Ask if user wants to recompile and run again
        recompile_loop "$topic_dir" "$JAVA_FILE" "$MAIN_CLASS"

        return 0
    else
        echo "Compilation failed."
        return 1
    fi
}

# Modified run_class function
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

    # Check for compiled classes
    class_files=$(find "$topic_dir/out" -name "*.class" 2>/dev/null)

    # Check if any class files were found
    if [ -z "$class_files" ]; then
        echo -e "${YELLOW}No compiled classes found. Let's compile a file first.${NC}"
        compile_file
        return
    fi

    # Create a numbered list of compiled classes with their full package paths
    echo -e "${GREEN}Compiled classes in this topic:${NC}"
    class_counter=1
    declare -a class_paths

    while IFS= read -r file; do
        rel_path=${file#$topic_dir/out/}
        package_path=$(dirname "$rel_path")
        class_name=$(basename "$file" .class)
        echo -e "$class_counter. ${BLUE}${package_path}${NC} - ${class_name}"
        class_paths[$class_counter]="$file"
        ((class_counter++))
    done <<< "$class_files"

    # Ask user to select class by number
    read -p "Enter the number of the class to run: " class_number

    # Validate input
    if ! [[ "$class_number" =~ ^[0-9]+$ ]] || [ "$class_number" -lt 1 ] || [ "$class_number" -ge "$class_counter" ]; then
        echo -e "${RED}Error:${NC} Invalid selection!"
        return
    fi

    # Get the selected class path
    selected_class="${class_paths[$class_number]}"
    class_name=$(basename "$selected_class" .class)

    # Extract the package path for running
    out_path=${selected_class#$topic_dir/out/}
    package_path=$(dirname "$out_path")

    # Run the Java program
    echo -e "${GREEN}Running${NC} $class_name from package $package_path"

    # Construct fully qualified class name
    FULLY_QUALIFIED_NAME="${package_path}.${class_name}"
    FULLY_QUALIFIED_NAME=$(echo "$FULLY_QUALIFIED_NAME" | tr '/' '.')

    # Run the program
    cd "$topic_dir" || return
    java -cp out "$FULLY_QUALIFIED_NAME"

    # Capture the exit status of the Java program
    JAVA_EXIT_STATUS=$?

    # Check if the Java program ran successfully
    if [ $JAVA_EXIT_STATUS -eq 0 ]; then
        echo "Program ran successfully."

        # Try to find the matching source file for potential recompilation
        SRC_DIR="src"
        SOURCE_FILE=$(find "$topic_dir/$SRC_DIR" -name "${class_name}.java")

        if [ -n "$SOURCE_FILE" ]; then
            JAVA_FILE=${SOURCE_FILE#$topic_dir/}

            # Ask if user wants to recompile and run again
            recompile_loop "$topic_dir" "$JAVA_FILE" "$FULLY_QUALIFIED_NAME"
        fi

        return 0
    else
        echo "Program encountered an error."
        return 1
    fi
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
    echo "7. Git operations"         # New option added here
    echo "8. Exit"                   # Changed from 7 to 8
    echo

    read -p "Enter your choice (1-8): " choice  # Changed from 1-7 to 1-8

    case $choice in
        1) create_project ;;
        2) add_subtopic ;;
        3) compile_file ;;
        4) run_class ;;
        5) quick_compile_run ;;
        6) list_topics ;;
        7) git_operations ;;         # New case added here
        8) echo "Exiting..."; exit 0 ;;  # Changed from 7 to 8
        *) echo -e "${YELLOW}Invalid choice.${NC}" ;;
    esac

    read -p "Press Enter to return to the main menu..."
    main_menu
}

# Start the main menu
main_menu