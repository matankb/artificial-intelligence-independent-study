package search;

public class Node {

    private final State state;
    private final Node parent;
    private final Action action;
    private final int pathCost;

    public Node(State state, Node parent, Action action, int pathCost) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.pathCost = pathCost;
    }

    public State getState() {
        return state;
    }
    public Node getParent() {
        return parent;
    }
    public Action getAction() {
        return action;
    }
    public int getPathCost() {
        return pathCost;
    }

}
