package search;

// This wrapper supports multiple types of solutions,
// which allows for separation of searching and printing
public class Solution {

    private Node node;
    private Action action;
    private int statesGenerated; // used for DLS search
    private int[] statesGeneratedPerLevel; // used for recursive DLS search

    public Solution(Node node) {
        this.node = node;
    }
    public Solution(Action action) {
        this.action = action;
    }
    public Solution(Node node, int statesGenerated) {
        this.node = node;
        this.statesGenerated = statesGenerated;
    }
    public Solution(Node node, int[] statesGeneratedPerLevel) {
        this.node = node;
        this.statesGeneratedPerLevel = statesGeneratedPerLevel;
    }

    public Node getNode() { return node; }
    public Action getAction() { return action; }

    public int getStatesGenerated() { return statesGenerated; }
    public int[] getStatesGeneratedPerLevel() { return statesGeneratedPerLevel; }
}
