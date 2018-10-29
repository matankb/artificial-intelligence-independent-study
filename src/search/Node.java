package search;

public class Node {

    public State getState() {
        return state;
    }
    public int getPathCost() {
        return pathCost;
    }

    private State state;
    private Node parent;
    private Action action;
    private int pathCost;

    public Node(State state, Node parent, Action action, int pathCost) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.pathCost = pathCost;
    }

    public Node getParent() {
        return parent;
    }
    public Action getAction() {
        return action;
    }

}
