#!/bin/bash

# DSA Graph Problem Solver & Revision Tracker
# Author: Striver A-Z DSA Sheet Automation
# Usage: ./graph_solver.sh

# Configuration
PROBLEMS_FILE="graph_problems.txt"
PROGRESS_FILE="progress.json"
REVISION_FILE="revision_queue.json"
STATS_FILE="stats.json"

# Colors for better output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# Initialize files if they don't exist
init_files() {
    if [ ! -f "$PROBLEMS_FILE" ]; then
        cat > "$PROBLEMS_FILE" << 'EOF'
# Graph Problems List
# Format: subtopic|problem_name|difficulty
# Example: BFS/DFS|Number of Islands|Medium

# ===== STRIVER A-Z GRAPH PROBLEMS =====

# Section 1: BFS/DFS Problems (14 problems)
BFS/DFS|Number of provinces (leetcode)|Medium
BFS/DFS|Connected Components Problem in Matrix|Hard
BFS/DFS|Rotten Oranges|Medium
BFS/DFS|Flood fill|Hard
BFS/DFS|Cycle Detection in undirected Graph (bfs)|Hard
BFS/DFS|Cycle Detection in undirected Graph (dfs)|Hard
BFS/DFS|O/J Matrix (Bfs Problem)|Medium
BFS/DFS|Surrounded Regions (dfs)|Hard
BFS/DFS|Number of Enclaves (flood fill implementation – multisource)|Hard
BFS/DFS|Word ladder – 1|Hard
BFS/DFS|Word ladder – 2|Hard
BFS/DFS|Number of Distinct Islands (dfs multisource)|Hard
BFS/DFS|Bipartite Graph (DFS)|Medium
BFS/DFS|Cycle Detection in Directed Graph (DFS)|Hard

# Section 2: Topological Sort (7 problems)
Topo Sort|Topo Sort|Medium
Topo Sort|Kahn's Algorithm|Medium
Topo Sort|Cycle Detection in Directed Graph (BFS)|Medium
Topo Sort|Course Schedule - I|Hard
Topo Sort|Course Schedule - II|Hard
Topo Sort|Find eventual safe states|Hard
Topo Sort|Alien dictionary|Hard

# Section 3: Shortest Path (12 problems)
Shortest Path|Shortest Path in UG with unit weights|Medium
Shortest Path|Shortest Path in DAG|Medium
Shortest Path|Dijkstra's Algorithm|Medium
Shortest Path|Shortest path in a binary maze|Medium
Shortest Path|Path with minimum effort|Medium
Shortest Path|Cheapest flights within k stops|Hard
Shortest Path|Network Delay time|Medium
Shortest Path|Number of ways to arrive at destination|Medium
Shortest Path|Minimum steps to reach end from start by performing multiplication and mod operations with array elements|Hard
Shortest Path|Bellman Ford Algorithm|Hard
Shortest Path|Floyd Warshall Algorithm|Hard
Shortest Path|Find the city with the smallest number of neighbors in a threshold distance|Hard

# Section 4: Disjoint Sets/MST (11 problems)
DSU/MST|Minimum Spanning Tree|Medium
DSU/MST|Prim's Algorithm|Medium
DSU/MST|Disjoint Set Union by Rank|Medium
DSU/MST|Disjoint Set Union by Size|Medium
DSU/MST|Kruskal's Algorithm|Medium
DSU/MST|Number of operations to make network connected|Medium
DSU/MST|Most stones removed with same rows or columns|Medium
DSU/MST|Accounts merge|Hard
DSU/MST|Number of island II|Hard
DSU/MST|Making a Large Island|Hard
DSU/MST|Swim in rising water|Hard
EOF
        echo -e "${GREEN}Created $PROBLEMS_FILE with sample problems. Please add your problems there.${NC}"
    fi

    if [ ! -f "$PROGRESS_FILE" ]; then
        echo '{"solved": [], "current_index": 0, "total_solved": 0}' > "$PROGRESS_FILE"
    fi

    if [ ! -f "$REVISION_FILE" ]; then
        echo '{"revision_queue": [], "last_revision_check": 0}' > "$REVISION_FILE"
    fi

    if [ ! -f "$STATS_FILE" ]; then
        echo '{"daily_count": 0, "last_date": "", "difficulty_stats": {"Easy": 0, "Medium": 0, "Hard": 0}}' > "$STATS_FILE"
    fi
}

# Function to read problems from file
# read_problems() {
#     grep -v '^#' "$PROBLEMS_FILE" | grep -v '^$' | while IFS='|' read -r subtopic problem difficulty; do
#         echo "$subtopic|$problem|$difficulty"
#     done
# }
# Function to read problems from file
read_problems() {
    grep -v '^#' "$PROBLEMS_FILE" | grep -v '^$'
}


# Function to get current problem
# get_current_problem() {
#     local current_index=$(jq -r '.current_index' "$PROGRESS_FILE")
#     local problems=($(read_problems))

#     if [ $current_index -ge ${#problems[@]} ]; then
#         echo "COMPLETED"
#         return
#     fi

#     echo "${problems[$current_index]}"
# }

# Function to get current problem
get_current_problem() {
    local current_index=$(jq -r '.current_index' "$PROGRESS_FILE")
    local problem_line=$(read_problems | sed -n "$((current_index + 1))p")

    if [ -z "$problem_line" ]; then
        echo "COMPLETED"
        return
    fi

    echo "$problem_line"
}

# Function to check if revision is needed
check_revision_needed() {
    local total_solved=$(jq -r '.total_solved' "$PROGRESS_FILE")
    local last_revision_check=$(jq -r '.last_revision_check' "$REVISION_FILE")

    # Optimized revision triggers for 46 total problems
    # After every 7 problems: revise last 4 problems (section completion)
    if [ $((total_solved % 7)) -eq 0 ] && [ $total_solved -gt $last_revision_check ] && [ $total_solved -gt 0 ]; then
        echo "REVISE_SECTION"
        return
    fi

    # After every 14 problems: revise 6-8 problems from previous sections
    if [ $((total_solved % 14)) -eq 0 ] && [ $total_solved -gt $last_revision_check ] && [ $total_solved -gt 0 ]; then
        echo "REVISE_CROSS_SECTION"
        return
    fi

    # After every 23 problems: major revision of 10-12 mixed problems
    if [ $((total_solved % 23)) -eq 0 ] && [ $total_solved -gt $last_revision_check ] && [ $total_solved -gt 0 ]; then
        echo "REVISE_MAJOR"
        return
    fi

    # Final revision after completing all 46 problems
    if [ $total_solved -eq 46 ] && [ $total_solved -gt $last_revision_check ]; then
        echo "REVISE_FINAL"
        return
    fi

    echo "NO_REVISION"
}

# Function to get revision problems
get_revision_problems() {
    local revision_type=$1
    local solved_problems=$(jq -r '.solved[]' "$PROGRESS_FILE")
    local revision_list=()

    case $revision_type in
        "REVISE_SECTION")
            # Last 4 problems (section completion)
            revision_list=($(echo "$solved_problems" | tail -4 | cut -d'|' -f1-3))
            echo -e "${YELLOW}📚 SECTION COMPLETE: Revise last 4 problems${NC}"
            echo -e "${BLUE}💡 Focus on: Pattern recognition and implementation differences${NC}"
            ;;
        "REVISE_CROSS_SECTION")
            # 6-8 problems from previous sections (mix of difficulties)
            revision_list=($(echo "$solved_problems" | head -$(($(echo "$solved_problems" | wc -l) - 7)) | cut -d'|' -f1-3 | cut -d'|' -f1-3 | shuf | head-7))
            echo -e "${YELLOW}📚 CROSS-SECTION REVISION: 7 problems from previous sections${NC}"
            echo -e "${BLUE}💡 Focus on: Connecting concepts across different graph techniques${NC}"
            ;;
        "REVISE_MAJOR")
            # 10-12 mixed problems with emphasis on Hard problems
            local hard_problems=$(echo "$solved_problems" | grep "Hard" | cut -d'|' -f1-3 | shuf | head-6)
            local other_problems=$(echo "$solved_problems" | grep -v "Hard" | cut -d'|' -f1-3 | shuf | head-6)
            revision_list=($(echo -e "$hard_problems\n$other_problems" | head -10))
            echo -e "${YELLOW}📚 MAJOR REVISION: 10 mixed problems (emphasis on Hard)${NC}"
            echo -e "${BLUE}💡 Focus on: Time complexity optimization and edge cases${NC}"
            ;;
        "REVISE_FINAL")
            # Final comprehensive revision: 15-20 problems covering all sections
            local bfs_dfs=$(echo "$solved_problems" | grep "BFS/DFS" | cut -d'|' -f1-3 | shuf | head-4)
            local topo=$(echo "$solved_problems" | grep "Topo Sort" | cut -d'|' -f1-3 | shuf | head-3)
            local shortest=$(echo "$solved_problems" | grep "Shortest Path" | cut -d'|' -f1-3 | shuf | head-4)
            local mst=$(echo "$solved_problems" | grep "DSU/MST" | cut -d'|' -f1-3 | shuf | head-4)
            revision_list=($(echo -e "$bfs_dfs\n$topo\n$shortest\n$mst"))
            echo -e "${YELLOW}🎉 FINAL COMPREHENSIVE REVISION: 15 problems across all sections${NC}"
            echo -e "${BLUE}💡 Focus on: Complete mastery and interview readiness${NC}"
            ;;
    esac

    printf '%s\n' "${revision_list[@]}"
}

# Function to mark problem as solved
mark_solved() {
    local problem_info=$1
    local timestamp=$(date +%s)
    local today=$(date +%Y-%m-%d)

    # Update progress
    jq --arg prob "$problem_info" --arg ts "$timestamp" \
       '.solved += [$prob + "|" + $ts] | .current_index += 1 | .total_solved += 1' \
       "$PROGRESS_FILE" > tmp.json && mv tmp.json "$PROGRESS_FILE"

    # Update daily stats
    local difficulty=$(echo "$problem_info" | cut -d'|' -f3)
    jq --arg today "$today" --arg diff "$difficulty" \
       '.last_date = $today | .daily_count += 1 | .difficulty_stats[$diff] += 1' \
       "$STATS_FILE" > tmp.json && mv tmp.json "$STATS_FILE"
}

# Function to display statistics
show_stats() {
    local total_solved=$(jq -r '.total_solved' "$PROGRESS_FILE")
    local daily_count=$(jq -r '.daily_count' "$STATS_FILE")
    local easy_count=$(jq -r '.difficulty_stats.Easy' "$STATS_FILE")
    local medium_count=$(jq -r '.difficulty_stats.Medium' "$STATS_FILE")
    local hard_count=$(jq -r '.difficulty_stats.Hard' "$STATS_FILE")

    echo -e "${CYAN}📊 PROGRESS STATISTICS${NC}"
    echo -e "${GREEN}Total Problems Solved: $total_solved${NC}"
    echo -e "${BLUE}Today's Count: $daily_count${NC}"
    echo -e "${PURPLE}Difficulty Breakdown:${NC}"
    echo -e "  Easy: $easy_count | Medium: $medium_count | Hard: $hard_count"
    echo ""
}

# Function to display current problem
# Function to display current problem
show_current_problem() {
    local current_problem=$(get_current_problem)

    if [ "$current_problem" = "COMPLETED" ]; then
        echo -e "${GREEN}🎉 CONGRATULATIONS! You've completed all Graph problems!${NC}"
        echo -e "${CYAN}🏆 Ready for final comprehensive revision!${NC}"
        return
    fi

    # local subtopic=$(echo "$current_problem" | cut -d'|' -f1)
    # local problem=$(echo "$current_problem" | cut -d'|' -f2)
    # local difficulty=$(echo "$current_problem" | cut -d'|' -f3)
      IFS='|' read -r subtopic problem difficulty <<< "$current_problem"

    # Color code difficulty
    local diff_color=""
    case $difficulty in
        "Easy") diff_color=$GREEN ;;
        "Medium") diff_color=$YELLOW ;;
        "Hard") diff_color=$RED ;;
    esac

    echo -e "${CYAN}📋 CURRENT PROBLEM${NC}"
    echo -e "${PURPLE}Section: $subtopic${NC}"
    echo -e "${BLUE}Problem: $problem${NC}"
    echo -e "${diff_color}Difficulty: $difficulty${NC}"
    echo ""
}

# Function to show section progress
show_section_progress() {
    local total_solved=$(jq -r '.total_solved' "$PROGRESS_FILE")

    echo -e "${CYAN}📊 SECTION PROGRESS${NC}"

    # Calculate section progress
    if [ $total_solved -le 14 ]; then
        echo -e "${BLUE}Current Section: BFS/DFS ($(($total_solved))/14)${NC}"
        local progress=$((total_solved * 100 / 14))
        echo -e "${GREEN}Progress: $progress%${NC}"
    elif [ $total_solved -le 21 ]; then
        echo -e "${BLUE}Current Section: Topological Sort ($((total_solved - 14))/7)${NC}"
        local progress=$(((total_solved - 14) * 100 / 7))
        echo -e "${GREEN}Progress: $progress%${NC}"
    elif [ $total_solved -le 33 ]; then
        echo -e "${BLUE}Current Section: Shortest Path ($((total_solved - 21))/12)${NC}"
        local progress=$(((total_solved - 21) * 100 / 12))
        echo -e "${GREEN}Progress: $progress%${NC}"
    elif [ $total_solved -le 44 ]; then
        echo -e "${BLUE}Current Section: DSU/MST ($((total_solved - 33))/11)${NC}"
        local progress=$(((total_solved - 33) * 100 / 11))
        echo -e "${GREEN}Progress: $progress%${NC}"
    else
        echo -e "${GREEN}All sections completed! 🎉${NC}"
    fi

    echo -e "${PURPLE}Overall Progress: $total_solved/46 ($(($total_solved * 100 / 46))%)${NC}"
    echo ""
}

# Main function
# Main function with continuous loop
main() {
    init_files

    while true; do
        clear
        echo -e "${CYAN}🚀 STRIVER A-Z DSA GRAPH TRACKER${NC}"
        echo -e "${PURPLE}==================================${NC}"
        echo ""

        show_stats
        show_section_progress

        # Check for revision first
        local revision_needed=$(check_revision_needed)

        if [ "$revision_needed" != "NO_REVISION" ]; then
            echo -e "${RED}⚠️  REVISION REQUIRED BEFORE PROCEEDING!${NC}"
            echo ""

            local revision_problems=$(get_revision_problems "$revision_needed")
            local count=1

            echo "$revision_problems" | while read -r problem_line; do
                if [ -n "$problem_line" ]; then
                    # Extract just the problem info part (before timestamp)
                    local prob_info=$(echo "$problem_line" | cut -d'|' -f1-3)
                    IFS='|' read -r subtopic problem difficulty <<< "$prob_info"
                    echo -e "${YELLOW}$count. [$subtopic] $problem ($difficulty)${NC}"
                    count=$((count + 1))
                fi
            done

            echo ""
            echo -e "${CYAN}Complete these revision problems first.${NC}"

            # Update last revision check
            local total_solved=$(jq -r '.total_solved' "$PROGRESS_FILE")
            jq --arg total "$total_solved" '.last_revision_check = ($total | tonumber)' \
               "$REVISION_FILE" > tmp.json && mv tmp.json "$REVISION_FILE"

            echo ""
            read -p "Press Enter when revision is complete..."
            continue
        fi

        # Show current problem
        show_current_problem

        # Check if completed
        local current_problem=$(get_current_problem)
        if [ "$current_problem" = "COMPLETED" ]; then
            echo -e "${GREEN}🎊 All problems completed! Press Ctrl+C to exit${NC}"
            read -p "Press Enter to continue..."
            continue
        fi

        # Options menu
        echo -e "${YELLOW}Choose an option:${NC}"
        echo -e "${GREEN}1. Mark current problem as SOLVED${NC}"
        echo -e "${BLUE}2. Skip to next problem${NC}"
        echo -e "${PURPLE}3. Show detailed statistics${NC}"
        echo -e "${CYAN}4. Force revision mode${NC}"
        echo -e "${RED}5. Reset progress${NC}"
        echo -e "${NC}6. Exit${NC}"
        echo ""

        read -p "Enter your choice (1-6): " choice

        case $choice in
            1)
                mark_solved "$current_problem"
                echo -e "${GREEN}✅ Problem marked as solved!${NC}"
                sleep 2
                ;;
            2)
                jq '.current_index += 1' "$PROGRESS_FILE" > tmp.json && mv tmp.json "$PROGRESS_FILE"
                echo -e "${YELLOW}⏭️  Skipped to next problem${NC}"
                sleep 1
                ;;
            3)
                show_detailed_stats
                read -p "Press Enter to continue..."
                ;;
            4)
                echo -e "${YELLOW}Enter revision type:${NC}"
                echo "1. Section (last 4)"
                echo "2. Cross-section (7 mixed)"
                echo "3. Major (10 mixed)"
                echo "4. Final (15 comprehensive)"
                read -p "Choice: " rev_choice

                case $rev_choice in
                    1) get_revision_problems "REVISE_SECTION" ;;
                    2) get_revision_problems "REVISE_CROSS_SECTION" ;;
                    3) get_revision_problems "REVISE_MAJOR" ;;
                    4) get_revision_problems "REVISE_FINAL" ;;
                    *) echo "Invalid choice" ;;
                esac
                read -p "Press Enter to continue..."
                ;;
            5)
                read -p "Are you sure you want to reset ALL progress? (yes/no): " confirm
                if [ "$confirm" = "yes" ]; then
                    echo '{"solved": [], "current_index": 0, "total_solved": 0}' > "$PROGRESS_FILE"
                    echo '{"revision_queue": [], "last_revision_check": 0}' > "$REVISION_FILE"
                    echo '{"daily_count": 0, "last_date": "", "difficulty_stats": {"Easy": 0, "Medium": 0, "Hard": 0}}' > "$STATS_FILE"
                    echo -e "${RED}🔄 Progress reset!${NC}"
                    sleep 2
                fi
                ;;
            6)
                echo -e "${CYAN}Happy coding! 🚀${NC}"
                exit 0
                ;;
            *)
                echo -e "${RED}Invalid choice!${NC}"
                sleep 1
                ;;
        esac
    done
}

# Run main function
main