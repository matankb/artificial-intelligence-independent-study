package search;

// generic interface for Problems
// based on book pseudocode
// used for Chapter 3 basic searches
public interface BasicSearchProblem<StateType extends State, ActionType extends Action> extends SearchProblem<StateType, ActionType> {

    // returns true if the state satisfies the goal
    boolean goalTest(StateType state);
    int stepCost(StateType parentState, ActionType action);

}
