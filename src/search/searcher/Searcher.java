package search.searcher;

import search.Action;
import search.SearchProblem;
import search.State;

import java.util.ArrayList;
import java.util.LinkedList;

// A generic Searcher class which accepts a generic SearchProblem
// Can run breadth-first search, depth-first search, and recursive depth-limited search on a BasicSearchProblem
// Can run Minimax search on an AdversarialSearchProblem
// most of the search algorithms come from the book's pseudocode
public class Searcher<Problem extends SearchProblem<? extends State, ? extends Action>> {

    protected final Problem problem;

    public Searcher(Problem problem) {
        this.problem = problem;
    }

}
