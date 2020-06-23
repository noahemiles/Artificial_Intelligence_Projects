
import java.util.List;

public class MiniMaxAlgorithmWithAlphaBetaPruning {

    //Takes current state of the gameboard, desired depth of searching, and the initial start time of the search and returns an action
    public String alphaBetaSearch(GameBoard state, int depth, long startTime) throws OutOfTimeException {
        ActionValueTuple value;
        if ((System.nanoTime() - startTime) / 1000000000 > state.getTimeLimitPerMove()) {
            throw (new OutOfTimeException("Out of Time"));
        }
        value = maxValue(state, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, depth, startTime);

        return value.getAction();
    }

    public ActionValueTuple maxValue(GameBoard state, double alpha, double beta, int depth, long startTime) throws OutOfTimeException {
        //if depth reached or terminal node found
        if (depth == 0 || terminal(state)) {
            return new ActionValueTuple(state.getPosition(1), utility(state));
        }

        Double value = Double.NEGATIVE_INFINITY;
        //generate possible moves
        List<int[]> SUCCESSORS = state.getSuccessors(state.getPosition(1));
        int[] action = new int[2];
        for (int i = 0; i < SUCCESSORS.size(); i++) {
            action = SUCCESSORS.get(i);
            int[] currentPos = state.getXY.apply(state.getPosition(1));
            //set
            state.setMove(action, currentPos, false);
            value = Math.max(value, minValue(state, alpha, beta, depth - 1, startTime).getValue());
            //unset
            state.setMove(currentPos, action, true);

            //check time
            if ((System.nanoTime() - startTime) / 1000000000 > state.getTimeLimitPerMove()) {
                return new ActionValueTuple(state.getString.apply(action), value);
            }
            //pruning
            if (value >= beta) {
                return new ActionValueTuple(state.getString.apply(action), value);
            } else {
                alpha = Math.max(alpha, value);
            }
        }
        return new ActionValueTuple(state.getString.apply(action), value);
    }

    public ActionValueTuple minValue(GameBoard state, double alpha, double beta, int depth, long startTime) throws OutOfTimeException {
        //if depth reached or terminal node found
        if (depth == 0 || terminal(state)) {
            return new ActionValueTuple(state.getPosition(1), utility(state));
        }

        Double value = Double.POSITIVE_INFINITY;
        //generate possible moves
        List<int[]> SUCCESSORS = state.getSuccessors(state.getPosition(1));
        int[] action = new int[2];
        for (int i = 0; i < SUCCESSORS.size(); i++) {
            action = SUCCESSORS.get(i);
            int[] currentPos = state.getXY.apply(state.getPosition(1));
            //set
            state.setMove(action, currentPos, false);
            value = Math.min(value, maxValue(state, alpha, beta, depth - 1, startTime).getValue());
            //unset
            state.setMove(currentPos, action, true);

            //check time
            if ((System.nanoTime() - startTime) / 1000000000 > state.getTimeLimitPerMove()) {
                return new ActionValueTuple(state.getString.apply(action), value);
            }
            //pruning
            if (value <= alpha) {
                return new ActionValueTuple(state.getString.apply(action), value);
            } else {
                beta = Math.min(beta, value);
            }
        }
        return new ActionValueTuple(state.getString.apply(action), value);
    }

    //utility of the current state of the computer
    public double utility(GameBoard state) {
        return state.numberOfMoves.apply(state.getXY.apply(state.getPosition(1)));
    }

    //checks if computer reached a terminal state
    public boolean terminal(GameBoard state) {
        return state.numberOfMoves.apply(state.getXY.apply(state.getPosition(1))) == 0;
    }

    //Helper class to store the action that goes with the value
    private class ActionValueTuple {
        String action;
        double value;

        public ActionValueTuple(String action, double value) {
            this.action = action;
            this.value = value;
        }

        public double getValue() {
            return this.value;
        }

        public String getAction() {
            return this.action;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
