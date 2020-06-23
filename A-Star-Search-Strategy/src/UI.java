
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Function;

public class UI {

    public static void UI() throws FileNotFoundException, IOException {
        Function<Integer[], Integer> hammingHeuristic = (state) -> {
            int misplacedTiles = 0;
            int cost = 0;
            for (int i = 0; i < state.length; i++) {
                if (i != state[i] && state[i] != 0) {
                    misplacedTiles++;
                }
            }
            return misplacedTiles;
        };
        Function<Integer[], Integer> manhattanHeuristic = (state) -> {
            int misplacedTiles = 0;
            int cost = 0;
            int delta = 0;
            for (int i = 0; i < state.length - 1; i++) {
                if (i != state[i] && state[i] != 0) {
                    delta = Math.abs(i - state[i]);
                    misplacedTiles += (delta % 3) + (delta / 3);
                }
            }
            return misplacedTiles;
        };

        //menu
        Scanner inputScanner = new Scanner(System.in);
        EightPuzzleGame problem = printGoalMenu(inputScanner);
        initialStateMenu(problem, inputScanner);

//  }
        //for file input
//        Scanner fileScanner = new Scanner(new File("input.txt")).useDelimiter("(\\\\n)");
//    }

        /* converting file input into int array
        int it = 0;
        while (fileScanner.hasNext()) {
            System.out.println(it++);
            String tempStr = fileScanner.nextLine();
            String[] split = tempStr.split("");
            Integer[] temp = new Integer[9];
            for (int i = 0; i < 9; i++) {
                temp[i] = Integer.parseInt(split[i]);
            }

            EightPuzzleGame problem;
            
                problem = new EightPuzzleGame();
                problem.specificPuzzle(temp);
                
                
         */
//        for (int n = 0; n < 1; n++) {
        double elapsedTime = 0;
        System.out.println("::::Tree Search::::");
        System.out.println("::Hamming Heuristic::");
        AStarSearch treeAStarSearchHamming = new AStarSearch();
        elapsedTime = treeAStarSearchHamming.search(problem, hammingHeuristic, false);
            System.out.println("Depth: " + problem.getDepth());
            System.out.println("Time Elapsed: " + elapsedTime + "ms");
                System.out.println("Search Cost: " + problem.getSearchCost());
                problem.printGoalPathToConsole();
//        problem.printGoalPath("::::Tree Search::::\n::Hamming Heuristic::\nDepth: " + problem.getDepth() + "\nTime elapsed: " + elapsedTime + "ms\nSearch Cost: " + problem.getSearchCost() + "\n");
//        }

//        for (int n = 0;n < 1; n++) {
//            problem = new EightPuzzleGame();
//            problem.specificPuzzle(temp);
        elapsedTime = 0;
            System.out.println("::Manhattan  Hueristic::");
        AStarSearch treeAStarSearchManhattan = new AStarSearch();
        elapsedTime = treeAStarSearchManhattan.search(problem, manhattanHeuristic, false);
            System.out.println("Depth: " + problem.getDepth());
            System.out.println("Time Elapsed: " + elapsedTime + "ms");
                System.out.println("Search Cost: " + problem.getSearchCost());
                problem.printGoalPathToConsole();
//        problem.printGoalPath("::::Tree Search::::\n::Manhattan  Heuristic::\nDepth: " + problem.getDepth() + "\nTime elapsed: " + elapsedTime + "ms\nSearch Cost: " + problem.getSearchCost() + "\n");
//        }
//        for (int n = 0; n < 1; n++) {
//            problem = new EightPuzzleGame();
//            problem.specificPuzzle(temp);
        elapsedTime = 0;
            System.out.println("::::Graph Search::::");
            System.out.println("::Hamming Heuristic::");
        AStarSearch mapAStarSearchHamming = new AStarSearch();
        elapsedTime = mapAStarSearchHamming.search(problem, hammingHeuristic, true);
            System.out.println("Depth: " + problem.getDepth());
            System.out.println("Time Elapsed: " + elapsedTime + "ms");
                System.out.println("Search Cost: " + problem.getSearchCost());
                problem.printGoalPathToConsole();
//        problem.printGoalPath("::::Graph Search::::\n::Hamming Heuristic::\nDepth: " + problem.getDepth() + "\nTime elapsed: " + elapsedTime + "ms\nSearch Cost: " + problem.getSearchCost() + "\n");
//        }
//
//        for (int n = 0; n < 1; n++) {
//            problem = new EightPuzzleGame();
//            problem.specificPuzzle(temp);
        elapsedTime = 0;
            System.out.println("::Manhattan  Hueristic::");
        AStarSearch mapAStarSearchManhattan = new AStarSearch();
        elapsedTime = mapAStarSearchManhattan.search(problem, manhattanHeuristic, true);
            System.out.println("Depth: " + problem.getDepth());
            System.out.println("Time Elapsed: " + elapsedTime + "ms");
                System.out.println("Search Cost: " + problem.getSearchCost());
                problem.printGoalPathToConsole();
//        problem.printGoalPath("::::Graph Search::::\n::Manhattan  Heuristic::\nDepth: " + problem.getDepth() + "\nTime elapsed: " + elapsedTime + "ms\nSearch Cost: " + problem.getSearchCost() + "\n");
//        }
    }
//}

    public static EightPuzzleGame printGoalMenu(Scanner inputScanner) {
        System.out.println("\n::Eight Puzzle Game::");
        System.out.println("1: Default Goal State");
        System.out.println("0: Exit");
        return goalInputMenu(inputScanner.nextInt(), inputScanner);
    }

    public static EightPuzzleGame goalInputMenu(int userGoalInput, Scanner inputScanner) {
        while (true) {
            switch (userGoalInput) {
                case 1:
                    return new EightPuzzleGame();
                case 0:
                    System.out.println("Thanks for Playing");
                    System.exit(0);
                default:
                    System.out.println("Invalid Input");
                    printGoalMenu(inputScanner);
            }
        }
    }

    public static int printStateMenu(Scanner inputScanner) {
        System.out.println("\n::Eight Puzzle Game::");
        System.out.println("1: Generate Random Puzzle Initial State");
        System.out.println("2: Enter Specific Puzzle Initial State");
        System.out.println("3: Use Specific Puzzle Initial State : [3 4 1 7 0 2 8 6 5]");
        System.out.println("0: Exit");
        System.out.print("Choice: ");
        return inputScanner.nextInt();
    }

    public static void initialStateMenu(EightPuzzleGame eightPuzzleGame, Scanner menuScanner) {
        int userStateInput;
        userStateInput = printStateMenu(menuScanner);
        boolean done = false;
        while (!done) {
            switch (userStateInput) {
                case 1:
                    eightPuzzleGame.generateRandomPuzzle();
                    done = true;
                    break;
                case 2:
                    initialStateInput(eightPuzzleGame, menuScanner);
                    done = true;
                    break;
                case 3:
                    eightPuzzleGame.specificPuzzle(new Integer[]{3, 4, 1, 7, 0, 2, 8, 6, 5});
                    done = true;
                    break;
                case 0:
                    System.out.println("Thanks for Playing");
                    System.exit(0);
                default:
                    System.out.println("Invalid Input");
                    userStateInput = printStateMenu(menuScanner);
            }
        }
    }

    public static void initialStateInput(EightPuzzleGame problem, Scanner menuScanner) {
        Integer[] puzzleValues = new Integer[9];
        boolean first = true;
        while (first || !problem.isSolvable(puzzleValues)) {
            if (!first) {
                System.out.println("Invalid Initial State. \nEnter new Initial State: (0-8)");
            } else {
                System.out.println("Enter Initial State: (0-8) Seperated by single space");
                System.out.print("Input: ");
            }
            first = false;
            puzzleValues = new Integer[9];
            menuScanner.nextLine();
            String userInput = menuScanner.nextLine();
            String[] tokens = userInput.split(" ");

            int i = 0;
            for (String token : tokens) {
                puzzleValues[i++] = Integer.parseInt(token);
            }
        }
        problem.specificPuzzle(puzzleValues);
    }

    public static void main(String[] args) throws IOException {
        UI();
    }
}
