package search;

import java.util.ArrayList;

public interface AdversarialSearchProblem<StateType extends State, ActionType extends Action, PlayerType extends Player> extends SearchProblem<StateType, ActionType>  {
    ArrayList<ActionType> getActions(StateType state);
    // returns true if game state is over
    boolean terminalTest(StateType state);
    // state must be terminal
    double utility(StateType state, PlayerType p);
    // estimate utility value for non-terminal state
    double eval(StateType state, PlayerType p);
}
