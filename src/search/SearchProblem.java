package search;

import java.util.ArrayList;

public interface SearchProblem {
    State getInitialState();
    // all possible actions that could be applied to n
    ArrayList<Action> getActions(State n);
    // the resulting state after the action is applied to parentState
    State result(State parentState, Action action);
}