
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;

public class AStarSearch {

    private HashSet<Node> exploredNodes;
    private PriorityQueue<Node> frontier;

    public double search(EightPuzzleGame problem, Function<Integer[], Integer> heuristic, boolean exploredSet) {
        double startTime = System.nanoTime();
        frontier = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return (heuristic.apply(o1.state) + o1.getCurrentCost()) - (heuristic.apply(o2.state) + o2.getCurrentCost());
            }
        });
        if (exploredSet) {
            exploredNodes = new HashSet<>();
        }
        Node initialState = new Node(problem.getCurrentState());

        frontier.add(initialState);
        if (exploredSet) {
            exploredNodes.add(initialState);
        }
        List<Integer[]> childrenStates;
        Node currentState = null;
        Node child;
        int searchCost = 1;
        boolean found = false;
        while (!frontier.isEmpty() && !found) {
            //set current node to first of frontier
            currentState = frontier.peek();
            //goal test
            if (problem.isGoalState(currentState.state)) {
                found = true;
                break;
            }
            frontier.remove();
            //add children of current state to queue
            childrenStates = problem.swapSpaceList(problem.getSpaceIndex(currentState.state), currentState.state);
            for (int i = 0; i < childrenStates.size(); i++) {
                //create nodes for children
                child = new Node(childrenStates.get(i));
                searchCost++;
                //set child parent to current state
                child.setParent(currentState);
                //set child cost to parents + 1
                child.setCurrentCost(child.parent.getCurrentCost() + 1);
                //put child on frontier
                if (!exploredSet) {
                    frontier.add(child);
                } else {
                    if (!exploredNodes.contains(child)) {
                        frontier.add(child);
                    }
                }
            }
        }
        double timeElapsed = System.nanoTime() - startTime;
        if (found) {
            problem.setDepth(currentState.currentCost);
            problem.setSearchCost(searchCost);
            try {
                do {
                    problem.addGoalPath(currentState.state);
                    currentState = currentState.getParent();
                } while (currentState.parent != null);
            } catch (Exception e) {
            }
            problem.addGoalPath(initialState.state);
        }
        return timeElapsed / 1000000;
    }

    private static class Node {

        private Integer[] state;
        private Node parent;
        private int currentCost;
        public Node(Integer[] node) {
            this.state = node;
            if (this.parent != null) {
                this.currentCost = this.parent.currentCost++;
            } else {
                this.currentCost = 0;
            }
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public Node getParent() {
            return parent;
        }

        public int getCurrentCost() {
            return currentCost;
        }
        public void setCurrentCost(int cost) {
            currentCost = cost;
        }

        public void printNode() {
            for (int i = 0; i < this.state.length; i++) {
                if ((i + 1) % 3 == 0) {
                    System.out.println(this.state[i]);
                } else {
                    System.out.print(this.state[i]);
                }
            }
        }
    }
}
