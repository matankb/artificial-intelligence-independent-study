package search;

public class Node<StateType extends State> {

    private final StateType state;
    private final Node<StateType> parent;
    private final Action action;
    private final int pathCost;

    public Node(StateType state, Node<StateType> parent, Action action, int pathCost) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.pathCost = pathCost;
    }

    public StateType getState() {
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
