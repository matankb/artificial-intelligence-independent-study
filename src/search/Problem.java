package search;

import java.util.ArrayList;

public interface Problem {

    public State getInitialState();
    public boolean goalTest(State state);
    public ArrayList<Action> getActions(Node n);
    public State result(State parentState, Action action);
    public int stepCost(State parentState, Action action);

}
