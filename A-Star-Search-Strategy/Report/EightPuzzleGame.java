
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EightPuzzleGame {

    private Integer[] goalState;
    private Integer[] currentState;
    private List<Integer[]> goalPath;
    private int depth;
    private int searchCost;

    public EightPuzzleGame() {
        goalState = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        goalPath = new ArrayList<>();
    }

    public EightPuzzleGame(Integer[] state) {
        goalPath = new ArrayList<>();
        goalState = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        int i = 0;
        for (Integer value : state) {
            currentState[i++] = value;
        }
    }

    public boolean isGoalState(Integer[] currentState) {
        return Arrays.equals(currentState, goalState);
    }

    public int countInversions(Integer[] state) {
        int inversions = 0;
        for (int i = 0; i < state.length - 1; i++) {
            for (int j = i + 1; j < state.length; j++) {
                if (state[i] > state[j] && state[i] != 0 && state[j] != 0) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    public List<Integer[]> generateArray(Integer[] spaceIndex, Integer[] current) {
        Integer[] newChild;
        List<Integer[]> childrenArrays = new ArrayList<>();
        //spaceIndex = array of where zero values should be
        for (int i = 0; i < spaceIndex.length; i++) {
            boolean done = false;
            newChild = new Integer[9];
            //copy current to child
            for (int k = 0; k < newChild.length; k++) {
                newChild[k] = current[k];
            }
            for (int j = 0; j < newChild.length && !done; j++) {
                if (newChild[j] == 0) {
                    newChild[j] = newChild[spaceIndex[i]];
                    newChild[spaceIndex[i]] = 0;
                    done = true;
                }
            }
            childrenArrays.add(newChild);
        }
        return childrenArrays;
    }

    public List<Integer[]> swapSpaceList(int spaceLocation, Integer[] current) {
        //space location containing space loaction of neighbors
        HashMap<Integer, Integer[]> spaceMap = new HashMap<>();

        spaceMap.put(0, new Integer[]{1, 3});
        spaceMap.put(1, new Integer[]{0, 2, 4});
        spaceMap.put(2, new Integer[]{1, 5});
        spaceMap.put(3, new Integer[]{0, 4, 6});
        spaceMap.put(4, new Integer[]{1, 3, 5, 7});
        spaceMap.put(5, new Integer[]{2, 4, 8});
        spaceMap.put(6, new Integer[]{3, 7});
        spaceMap.put(7, new Integer[]{4, 6, 8});
        spaceMap.put(8, new Integer[]{5, 7});

        return generateArray(spaceMap.get(spaceLocation), current);
    }

    public int getSpaceIndex(Integer[] state) {
        int spaceIndex = -1;
        for (int i = 0; i < state.length; i++) {
            if (state[i] == 0) {
                spaceIndex = i;
            }
        }
        return spaceIndex;
    }

    public boolean isSolvable(Integer[] state) {
        boolean solvable = false;
        if (countInversions(state) % 2 == 0) {
            solvable = true;
        }
        return solvable;
    }

    public void generateRandomPuzzle() {
        Integer[] temp = goalState.clone();
        Collections.shuffle(Arrays.asList(temp));
        while (!isSolvable(temp)) {
            Collections.shuffle(Arrays.asList(temp));
        }
        this.currentState = temp;
    }

    public void specificPuzzle(Integer[] puzzle) {
        this.currentState = puzzle;
    }

    public Integer[] getGoalState() {
        return goalState;
    }

    public Integer[] getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Integer[] state) {
        currentState = state.clone();
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getSearchCost() {
        return searchCost;
    }

    public void setSearchCost(int cost) {
        searchCost = cost;
    }

    public void addGoalPath(Integer[] path) {
        goalPath.add(path);
    }
    
    public void printGoalPathToConsole(){
        for(int i = 0; i < goalPath.size(); i++){
            for (int j = 0; j < 9; j++){
                System.out.print(goalPath.get(i)[j]);
            }
            System.out.println("");
        }
    }

    public void printGoalPath(String searchType) throws IOException {

        BufferedWriter outputWriter = new BufferedWriter(new FileWriter("output.txt", true));
        ListIterator<Integer[]> it = goalPath.listIterator();
        outputWriter.write(String.valueOf(searchType) + "\n");
        while (it.hasNext()) {
            printNode1x8(it.next(), outputWriter);
        }

        try {
            outputWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(EightPuzzleGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void printNode3x3(Integer[] state,
            BufferedWriter outputWriter) throws IOException {
        for (int i = 0; i < state.length; i++) {
            if ((i + 1) % 3 == 0) {
                outputWriter.write(String.valueOf(state[i]));
            } else {
                outputWriter.write(state[i]);
            }
        }
        outputWriter.newLine();
        outputWriter.flush();
    }

    public void printNode1x8(Integer[] state,
            BufferedWriter outputWriter) throws IOException {

        for (int i = 0; i < state.length; i++) {
            outputWriter.write(String.valueOf(state[i]));
        }
        outputWriter.newLine();
        outputWriter.flush();
    }
}
