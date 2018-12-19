package search;

import java.util.ArrayList;

public interface AdversarialSearchProblem extends SearchProblem {
    ArrayList<Action> getActions(State state);
    // returns true if game state is over
    boolean terminalTest(State state);
    // state must be terminal
    double utility(State state, Player p);
}
