
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class UI {

    Scanner kb = new Scanner(System.in);
    private static GameBoard board = new GameBoard();
    private static MiniMaxAlgorithmWithAlphaBetaPruning search = new MiniMaxAlgorithmWithAlphaBetaPruning();

    public static void main(String[] args) {
        UI ui = new UI();
        //sets the time per move
        board.setTime(ui.timeLimitPerMoveMenu());
        //sets who goes first
        board.setFirst(ui.whoGoesFirstMenu());
        board.printBoard();

        //while moves are able to be made
        for (int i = 0; i < 31; i++) {
            moveSequence(ui);
        }
    }

    //move sequence of alternating player moves
    public static void moveSequence(UI ui) {
        ui.moveMenu();
        board.printBoard();
        ui.moveMenu();
        board.printBoard();
    }

    //prompts user to enter time limit in seconds and return that value
    public int timeLimitPerMoveMenu() {
        boolean valid = false;
        int timeLimit = 0;
        while (!valid) {
            try {
                System.out.print("Enter time limit (seconds): ");
                timeLimit = kb.nextInt();
                valid = timeLimit >= 0;
            } catch (Exception e) {
                System.out.println("Invalid time input");
            }
            kb.nextLine();
        }
        return timeLimit;
    }

    //prompts user to enter who goes first
    public int whoGoesFirstMenu() {
        System.out.print("Who goes first? (Computer [C] or Opponent [O]): ");
        boolean done = false;
        int first = -1, computer = 1, opponent = 2;
        String input = "";
        while (!done) {
            input = kb.nextLine().toUpperCase();
            switch (input) {
                case "C":
                    first = computer;
                    done = true;
                    break;
                case "O":
                    first = opponent;
                    done = true;
                    break;
                default:
                    System.out.print("Enter either C or O: ");
                    break;
            }
        }
        return first;
    }

    //TimerTask to control the time user takes to enter a move
    public TimerTask makeTimerTask() {
        String noRes = "";
        TimerTask makeMove = new TimerTask() {
            public void run() {
                if (noRes.equals("")) {
                    System.out.println("\nOut of time.\nGame Over.");
                    board.printWinner();
                }
            }
        };
        return makeMove;
    }

    //Starts timer and a player makes their move
    public void moveMenu() {
        String input;
        long playerStartTime = System.nanoTime();
        boolean valid = false;
        if (board.getCurrentMove() == 1) {
            input = printPlayerMove();
            board.makeMove(input);
            System.out.println(input);
        } else {
            TimerTask makeMove = makeTimerTask();
            Timer moveTimer = new Timer();
            moveTimer.schedule(makeMove, board.getTimeLimitPerMove() * 1000);
            input = printPlayerMove();
            if (input.length() == 2) {
                if (Character.isDigit(input.charAt(1))) {
                    valid = board.makeMove(input);
                }
            }
            while (!valid) {
                if ((System.nanoTime() - playerStartTime) / 1000000000 > board.getTimeLimitPerMove()) {
                    board.printWinner();
                }
                System.out.println("Invalid Coordinate (Format: [A-H][1-8])");
                board.printBoard();
                input = printPlayerMove();
                if (input.length() == 2) {
                    if (Character.isDigit(input.charAt(1))) {
                        valid = board.makeMove(input);
                    }
                }
            }
            moveTimer.cancel();
        }
        //if the player doesn't have any moves, end game
        if (board.numberOfMoves.apply(board.getXY.apply(board.getPosition(board.getCurrentMove()))) == 0) {
            board.printWinner();
        }
    }

    //print method for handling the prompt for entering a move
    public String printPlayerMove() {
        String input = "";
        long startTime = System.nanoTime();
        if (board.getCurrentMove() == 1) {
            System.out.print("\nComputer's move is: ");
            
            //Iterative deepening for the computer
            for (int i = 3; i < Double.POSITIVE_INFINITY; i++) {
                if ((System.nanoTime() - startTime) / 1000000000 < board.getTimeLimitPerMove()) {
                    try {
                        input = search.alphaBetaSearch(board, i, startTime);
                    } catch (OutOfTimeException ex) {
                        return input;
                    }
                } else {
                    return input;
                }

            }
        } else {
            System.out.print("\nEnter opponent's move: ");
            input = kb.nextLine().toUpperCase();

        }
        return input;
    }
}
