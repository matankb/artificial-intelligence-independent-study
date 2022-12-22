package search;

import java.util.ArrayList;

public interface SearchProblem<StateType extends State, ActionType extends Action> {
    StateType getInitialState();
    // all possible actions that could be applied to n
    ArrayList<ActionType> getActions(StateType n);
    // the resulting state after the action is applied to parentState
    StateType result(StateType parentState, ActionType action);
}