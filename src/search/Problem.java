package search;

import java.util.ArrayList;

abstract public class Problem {

    abstract public State getInitialState();
    abstract public boolean goalTest(State state);
    abstract public ArrayList<Action> getActions(Node n);
    abstract public State result(State parentState, Action action);
    abstract public int stepCost(State parentState, Action action);

}
