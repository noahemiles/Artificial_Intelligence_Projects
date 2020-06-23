
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GameBoard {

    private int[][] gameBoard = null;
    private String[] boardLabels = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private List<StringTuple> moveLog = new ArrayList<>();
    private int size = 8;
    private int first;
    private int currentMove;
    private int numOfTurns = 0;
    private int timeLimitPerMove = 20;
    String computerCurrentPos;
    String playerCurrentPos;

    private int computer = 1;
    private int opponent = 2;

    //default constructor
    public GameBoard() {
        gameBoard = new int[size][size];
    }

    //copy constructor
    public GameBoard(GameBoard copy) {
        gameBoard = new int[size][size];
        for (int i = 0; i < gameBoard.length; i++) {
            gameBoard[i] = copy.gameBoard[i].clone();
        }
        this.computerCurrentPos = copy.computerCurrentPos;
    }

    //printer methods
    //prints the current board's state with the move log
    public void printBoard() {
        Boolean endOfBoard = false;
        System.out.println("  1  2  3  4  5  6  7  8\tComputer vs. Opponent");
        for (int i = 0; i < this.size; i++) {
            System.out.print(boardLabels[i]);
            endOfBoard = false;
            for (int j = 0; j < this.size; j++) {
                if (j == this.size - 1) {
                    endOfBoard = true;
                }
                switch (gameBoard[i][j]) {
                    case 0:
                        System.out.print(" - ");
                        if (endOfBoard && moveLog.size() > i) {
                            System.out.format("\t%4d. %s%5s", (i + 1), moveLog.get(i).getValue(0), moveLog.get(i).getValue(1));
                        }
                        break;
                    case 1:
                        System.out.print(" X ");
                        if (endOfBoard && moveLog.size() > i) {
                            System.out.format("\t%4d. %s%5s", (i + 1), moveLog.get(i).getValue(0), moveLog.get(i).getValue(1));
                        }
                        break;
                    case 2:
                        System.out.print(" O ");
                        if (endOfBoard && moveLog.size() > i) {
                            System.out.format("\t%4d. %s%5s", (i + 1), moveLog.get(i).getValue(0), moveLog.get(i).getValue(1));
                        }
                        break;
                    case 3:
                        System.out.print(" # ");
                        if (endOfBoard && moveLog.size() > i) {
                            System.out.format("\t%4d. %s%5s", (i + 1), moveLog.get(i).getValue(0), moveLog.get(i).getValue(1));
                        }
                        break;
                    default:
                        System.out.print(" ! ");
                        System.err.println("Invalid Value on Board");
                        break;
                }
            }
            System.out.println();
        }

        if (moveLog.size() > 8) {
            for (int i = 9; i < moveLog.size(); i++) {
                System.out.format("\t\t\t\t%4d. %s%5s\n", (i), moveLog.get(i).getValue(0), moveLog.get(i).getValue(1));
            }
        }
    }

    //prints the winner of the game and exits the program
    void printWinner() {
        if ((currentMove % 2) + 1 == 1) {
            System.out.println("Computer Wins!");
        } else {
            System.out.println("Opponent Wins!");
        }
        System.exit(0);
    }

    //movement methods
    //checks if the given coordinate is a move and doesn't violate any game  rules
    public boolean isValidMove(String coordinate) {
        int[] srcXY;
        int[] dstXY;
        if (numOfTurns == 1) {
            srcXY = getXY.apply(moveLog.get(moveLog.size() - 1).getValue(currentMove - 1));
            dstXY = getXY.apply(coordinate);
        } else {
            if (first == 1) {
                srcXY = getXY.apply(moveLog.get(moveLog.size() - currentMove).getValue(currentMove - 1));
                dstXY = getXY.apply(coordinate);

            } else {
                if (currentMove == 1) {
                    srcXY = getXY.apply(moveLog.get(moveLog.size() - (currentMove + 1)).getValue(currentMove - 1));
                    dstXY = getXY.apply(coordinate);
                } else {
                    srcXY = getXY.apply(moveLog.get((moveLog.size() - currentMove) + 1).getValue(currentMove - 1));
                    dstXY = getXY.apply(coordinate);
                }
            }
        }
        int[] srcXYandDirection = new int[4];

        srcXYandDirection[0] = srcXY[0];
        srcXYandDirection[1] = srcXY[1];
        boolean valid = true;
        Direction dstDirection = getDirection(srcXY, dstXY);

        switch (dstDirection) {
            case N:
                srcXYandDirection[2] = 0;
                srcXYandDirection[3] = Math.abs(srcXY[0] - dstXY[0]);
                break;
            case NW:
                srcXYandDirection[2] = 1;
                srcXYandDirection[3] = Math.abs(srcXY[0] - dstXY[0]);
                break;
            case NE:
                srcXYandDirection[2] = 2;
                srcXYandDirection[3] = Math.abs(srcXY[0] - dstXY[0]);
                break;
            case S:
                srcXYandDirection[2] = 3;
                srcXYandDirection[3] = Math.abs(srcXY[0] - dstXY[0]);
                break;
            case SW:
                srcXYandDirection[2] = 4;
                srcXYandDirection[3] = Math.abs(srcXY[0] - dstXY[0]);
                break;
            case SE:
                srcXYandDirection[2] = 5;
                srcXYandDirection[3] = Math.abs(srcXY[0] - dstXY[0]);
                break;
            case E:
                srcXYandDirection[2] = 6;
                srcXYandDirection[3] = Math.abs(srcXY[1] - dstXY[1]);
                break;
            case W:
                srcXYandDirection[2] = 7;
                srcXYandDirection[3] = Math.abs(srcXY[1] - dstXY[1]);
                break;
            case INVALID:
                return false;
        }
        if (numberOfMoves.apply(srcXYandDirection) == -1) {
            valid = false;
        }

        return valid;
    }

    //checks if the given coordinate is formatted properly and exists on the board
    public boolean isValidCoord(String coordinate) {
        //validate input
        //must be format XX
        if (coordinate.length() != 2) {
            return false;
        }

        //numeric values of coordinates
        int[] xy = getXY.apply(coordinate);
        int letterCoord = xy[0];
        int numCoord = xy[1];

        //must be A-H  and 1-8
        if ((letterCoord < 0) || (letterCoord > 7) || (numCoord < 0) || (numCoord > 7)) {
            return false;
            //must be empty space
        } else if (gameBoard[letterCoord][numCoord] != 0) {
            return false;
        }

        return true;
    }

    //makes the move based on the given coordinate
    public boolean makeMove(String coordinate) {

        //check if input is correct syntax
        if (!isValidCoord(coordinate)) {
            return false;
        }
        //check if valid move
        if (!isValidMove(coordinate)) {
            return false;
        }

        int[] xy = getXY.apply(coordinate);
        int letterCoord = xy[0];
        int numCoord = xy[1];

        if (first == 1) {
            if (currentMove == first) {
                computerCurrentPos = coordinate;
                moveLog.add(new StringTuple(computerCurrentPos, currentMove));
            } else {
                playerCurrentPos = coordinate;
                StringTuple temp = moveLog.remove(moveLog.size() - 1);
                moveLog.add(new StringTuple(temp.getValue(0), playerCurrentPos));
            }
        } else {
            if (currentMove == first) {
                playerCurrentPos = coordinate;
                moveLog.add(new StringTuple(playerCurrentPos, currentMove));
            } else {
                computerCurrentPos = coordinate;
                StringTuple temp = moveLog.remove(moveLog.size() - 1);
                moveLog.add(new StringTuple(computerCurrentPos, temp.getValue(1)));
            }
        }

        gameBoard[letterCoord][numCoord] = currentMove;
        setPrevious();
        numOfTurns++;

        return true;
    }

    //helper class to store moves for movelog
    private class StringTuple {

        private String str0 = "";
        private String str1 = "";

        public StringTuple() {
            this("", "", first);
        }

        public StringTuple(String s1, int turn) {
            this(s1, "  ", turn);
        }

        public StringTuple(String s1, String s2, int turn) {
            if (turn == computer) {
                str0 = s1;
                str1 = s2;
            } else {
                str0 = s2;
                str1 = s1;
            }
        }

        public StringTuple(String s1, String s2) {
            str0 = s1;
            str1 = s2;
        }

        private String getValue(int index) {
            switch (index) {
                case 0:
                    return str0;
                case 1:
                    return str1;
                default:
                    return "";
            }
        }

        @Override
        public String toString() {
            return "(" + str0 + "," + str1 + ")";
        }
    }

    //Getters and Setters 
    //Sets the board's first turn based on who goes first
    public void setFirst(int turn) {
        first = turn;
        currentMove = first;
        gameBoard[0][0] = turn;
        gameBoard[size - 1][size - 1] = (turn % 2) + 1;
        if (turn == 1) {
            computerCurrentPos = "A1";
            playerCurrentPos = "H8";
        } else {
            computerCurrentPos = "H8";
            playerCurrentPos = "A1";
        }
        moveLog.add(new StringTuple(computerCurrentPos, playerCurrentPos));
        numOfTurns++;
    }

    //Sets the time limit per move
    public void setTime(int timeLimitPerMove) {
        this.timeLimitPerMove = timeLimitPerMove;
    }

    //Ability to set and unset moves given source coordinate and destination coordinate
    public void setMove(int[] action, int[] src, boolean unset) {
        if (unset) {
            this.computerCurrentPos = getString.apply(action);
            this.gameBoard[src[0]][src[1]] = 0;
            this.gameBoard[action[0]][action[1]] = 1;
        } else {
            this.computerCurrentPos = getString.apply(action);
            this.gameBoard[src[0]][src[1]] = 3;
            this.gameBoard[action[0]][action[1]] = 1;
        }
    }

    //Returns a list of possible moves of the current board given a coordinate 
    public List<int[]> getSuccessors(String src) {
        int counter = 1;
        int[] xy = getXY.apply(src);
        int srcR = xy[0];
        int srcC = xy[1];
        List<int[]> successors = new ArrayList<>();
        boolean N = true, NW = true, NE = true, S = true, SW = true, SE = true, E = true, W = true;
        while (NE || SE || NW || SE || N || S || E || W) {
            int indexR = srcR - counter;
            if (N && indexR >= 0 && gameBoard[indexR][srcC] == 0) {
                successors.add(new int[]{indexR, srcC});
            } else {
                N = false;
            }
            if (NW && indexR >= 0 && srcC - counter >= 0 && gameBoard[indexR][srcC - counter] == 0) {
                successors.add(new int[]{indexR, srcC - counter});
            } else {
                NW = false;
            }
            if (NE && indexR >= 0 && srcC + counter < 8 && gameBoard[indexR][srcC + counter] == 0) {
                successors.add(new int[]{indexR, srcC + counter});
            } else {
                NE = false;
            }
            indexR = srcR + counter;
            if (S && indexR < 8 && gameBoard[indexR][srcC] == 0) {
                successors.add(new int[]{indexR, srcC});
            } else {
                S = false;
            }
            if (SW && indexR < 8 && srcC - counter >= 0 && gameBoard[indexR][srcC - counter] == 0) {
                successors.add(new int[]{indexR, srcC - counter});
            } else {
                SW = false;
            }
            if (SE && indexR < 8 && srcC + counter < 8 && gameBoard[indexR][srcC + counter] == 0) {
                successors.add(new int[]{indexR, srcC + counter});
            } else {
                SE = false;
            }
            int indexC = srcC + counter;
            if (E && indexC < 8 && gameBoard[srcR][indexC] == 0) {
                successors.add(new int[]{srcR, indexC});
            } else {
                E = false;
            }
            indexC = srcC - counter;
            if (W && indexC >= 0 && gameBoard[srcR][indexC] == 0) {
                successors.add(new int[]{srcR, indexC});
            } else {
                W = false;
            }
            counter++;
        }
        if (counter == 0) {
            printWinner();
        }
        return successors;
    }

    //Directions a player can move
    enum Direction {
        N,
        NE,
        E,
        SE,
        S,
        SW,
        W,
        NW,
        INVALID
    }

    //Given a source and destination coordinate, returns the direction of the move
    public Direction getDirection(int[] srcXY, int[] dstXY) {
        int srcR = srcXY[0];
        int srcC = srcXY[1];
        int dstR = dstXY[0];
        int dstC = dstXY[1];
        //src and dst are on same column
        if (srcC == dstC) {
            if (srcR < dstR) {
                return Direction.S;
            } else {
                return Direction.N;
            }
        }
        //src and dst are on same row
        if (srcR == dstR) {
            if (srcC < dstC) {
                return Direction.E;
            } else {
                return Direction.W;
            }
        }
        //src and dst are on same diagonal
        if (Math.abs(srcC - dstC) == Math.abs(srcR - dstR)) {
            if (srcC - dstC < 0 && srcR - dstR < 0) {
                return Direction.SE;
            }
            if (srcC - dstC > 0 && srcR - dstR < 0) {
                return Direction.SW;
            }
            if (srcC - dstC < 0 && srcR - dstR > 0) {
                return Direction.NE;
            }
            if (srcC - dstC > 0 && srcR - dstR > 0) {
                return Direction.NW;
            }
        }
        return Direction.INVALID;
    }

    //Returns the coordinate of the desired player
    public String getPosition(int currentMove) {
        if (currentMove == 1) {
            return computerCurrentPos;
        } else {
            return playerCurrentPos;
        }
    }

    //returns whos turn it is
    public int getCurrentMove() {
        return currentMove;
    }

    //returns the time a player has to make their move
    public int getTimeLimitPerMove() {
        return timeLimitPerMove;
    }

    //sets the previous move spot to 3 (#) and changed the current move to the next player
    public void setPrevious() {
        int letterCoord;
        int numCoord;
        if (moveLog.size() > 0) {
            StringTuple prevMoves = moveLog.get(moveLog.size() - 2);
            if (currentMove == 1) {
                letterCoord = prevMoves.getValue(0).charAt(0) - 'A';
                numCoord = Integer.parseInt(Character.toString(prevMoves.getValue(0).charAt(1))) - 1;
                gameBoard[letterCoord][numCoord] = 3;
            } else {
                letterCoord = prevMoves.getValue(1).charAt(0) - 'A';
                numCoord = Integer.parseInt(Character.toString(prevMoves.getValue(1).charAt(1))) - 1;
                gameBoard[letterCoord][numCoord] = 3;
            }
            currentMove = (currentMove % 2) + 1;
        }
    }

    //Heuristics
    //Converts int[] coordinates to string coordinates
    Function<int[], String> getString = (coord) -> {
        return String.valueOf((char) (coord[0] + 65)) + Integer.toString(coord[1] + 1);
    };

    //Converts string coordinates to int[] coordinates
    Function<String, int[]> getXY = (coord) -> {
        return new int[]{(coord.charAt(0) - 'A'), (Integer.parseInt(Character.toString(coord.charAt(1))) - 1)};

    };

    //returns total number of moves given coordinate
    Function<int[], Integer> numberOfMoves = (src) -> {
        int srcR = src[0];
        int srcC = src[1];
        int direction = -1, distanceFromSrc = 8;
        int countN = 0, countNE = 0, countE = 0, countSE = 0, countS = 0, countSW = 0, countW = 0, countNW = 0;
        boolean N = true, NW = true, NE = true, S = true, SW = true, SE = true, E = true, W = true;
        int counter = 1;

        //If checking only a single direction
        if (src.length == 4) {
            direction = src[2];
            distanceFromSrc = src[3];
        }
        while (NE || SE || NW || SE || N || S || E || W) {
            if (src.length == 4) {
                if (counter > distanceFromSrc) {
                    return 0;
                }
            }
            int indexR = srcR - counter;
            if (N && indexR >= 0 && gameBoard[indexR][srcC] == 0) {
                countN++;
            } else {
                try {
                    if (direction == 0 && gameBoard[indexR][srcC] != 0) {
                        return -1;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
                N = false;
            }
            if (NW && indexR >= 0 && srcC - counter >= 0 && gameBoard[indexR][srcC - counter] == 0) {
                countNW++;
            } else {
                try {
                    if (direction == 1 && gameBoard[indexR][srcC] != 0) {
                        return -1;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
                NW = false;
            }
            if (NE && indexR >= 0 && srcC + counter < 8 && gameBoard[indexR][srcC + counter] == 0) {
                countNE++;
            } else {
                try {
                    if (direction == 2 && gameBoard[indexR][srcC] != 0) {
                        return -1;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
                NE = false;
            }
            indexR = srcR + counter;
            if (S && indexR < 8 && gameBoard[indexR][srcC] == 0) {
                countS++;
            } else {
                try {
                    if (direction == 3 && gameBoard[indexR][srcC] != 0) {
                        return -1;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
                S = false;
            }
            if (SW && indexR < 8 && srcC - counter >= 0 && gameBoard[indexR][srcC - counter] == 0) {
                countSW++;
            } else {
                try {
                    if (direction == 4 && gameBoard[indexR][srcC] != 0) {
                        return -1;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
                SW = false;
            }
            if (SE && indexR < 8 && srcC + counter < 8 && gameBoard[indexR][srcC + counter] == 0) {
                countSE++;
            } else {
                try {
                    if (direction == 5 && gameBoard[indexR][srcC] != 0) {
                        return -1;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
                SE = false;
            }
            int indexC = srcC + counter;
            if (E && indexC < 8 && gameBoard[srcR][indexC] == 0) {
                countE++;
            } else {
                try {
                    if (direction == 6 && gameBoard[indexR][srcC] != 0) {
                        return -1;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {

                }
                E = false;
            }
            indexC = srcC - counter;
            if (W && indexC >= 0 && gameBoard[srcR][indexC] == 0) {
                countW++;
            } else {
                try {
                    if (direction == 7 && gameBoard[indexR][srcC] != 0) {
                        return -1;
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {

                }
                W = false;
            }
            counter++;
        }
        return countN + countNE + countNW + countS + countSE + countSW + countE + countW;
    };
}
