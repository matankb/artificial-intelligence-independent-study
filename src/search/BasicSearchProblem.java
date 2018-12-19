package search;

// generic interface for Problems
// based on book pseudocode
// used for Chapter 3 basic searches
public interface BasicSearchProblem extends SearchProblem {

    // returns true if the state satisfies the goal
    boolean goalTest(State state);
    int stepCost(State parentState, Action action);

}
