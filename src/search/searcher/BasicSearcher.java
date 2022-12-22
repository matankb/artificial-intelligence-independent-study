package search.searcher;

import search.*;
import search.exception.SearchCutoff;
import search.exception.SearchFailure;

import java.util.ArrayList;
import java.util.LinkedList;

public class BasicSearcher<Problem extends BasicSearchProblem<StateType, ActionType>, StateType extends State, ActionType extends Action> extends Searcher<Problem> {

    public BasicSearcher(Problem problem) {
        super(problem);
    }

    public Solution breadthSearch() throws SearchFailure, InvalidProblem {
        Node<StateType> node = new Node<StateType>(problem.getInitialState(), null, null, 0);
        if (problem.goalTest(node.getState())) {
            return new Solution(node);
        }
        LinkedList<Node<StateType>> frontier = new LinkedList<Node<StateType>>();
        frontier.add(node);
        LinkedList<State> explored = new LinkedList<State>();
        while (true) {
            if (frontier.isEmpty()) {
                throw new SearchFailure();
            }
            node = frontier.pop();
            explored.add(node.getState());
            ArrayList<ActionType> actions = problem.getActions(node.getState());
            for (ActionType action: actions) {
                Node<StateType> child = this.childNode(node, action);
                if (!frontier.contains((child)) && !explored.contains(child.getState())) {
                    if (problem.goalTest(child.getState())) {
                        return new Solution(child);
                    }
                    frontier.add(child);
                }
            }
        }
    }

    public Solution depthSearch() throws SearchFailure {
        Node<StateType> node = new Node<StateType>(this.problem.getInitialState(), null, null, 0);
        if (problem.goalTest(node.getState())) {
            return new Solution(node);
        }
        LinkedList<Node<StateType>> frontier = new LinkedList<Node<StateType>>();
        frontier.add(node);
        LinkedList<State> explored = new LinkedList<State>();
        while (true) {
            if (frontier.isEmpty()) {
                throw new SearchFailure();
            }
            node = frontier.removeLast();
            explored.add(node.getState());
            ArrayList<ActionType> actions = problem.getActions(node.getState());
            for (ActionType action: actions) {
                Node<StateType> child = this.childNode(node, action);
                if (!frontier.contains((child)) && !explored.contains(child.getState())) {
                    if (problem.goalTest(child.getState())) {
                        return new Solution(child);
                    }
                    frontier.addFirst(child);
                }
            }
        }
    }

    public Solution depthLimitedSearch(int limit) throws SearchFailure, SearchCutoff {
        Node<StateType> node = new Node<StateType>(this.problem.getInitialState(), null, null, 0);
        return recursiveDepthLimitedSearch(node, limit);
    }

    private Solution recursiveDepthLimitedSearch(Node<StateType> node, int limit) throws SearchFailure, SearchCutoff {
        if (problem.goalTest(node.getState())) {
            return new Solution(node, 0);
        } else if (limit == 0) {
            throw new SearchCutoff(0);
        } else {
            int statesGenerated = 0;
            boolean cutoffOccurred = false;
            ArrayList<ActionType> actions = problem.getActions(node.getState());
            for (ActionType action: actions) {
                Node<StateType> child = this.childNode(node, action);
                statesGenerated++;
                try {
                    Solution solution = recursiveDepthLimitedSearch(child, limit - 1);
                    return new Solution(solution.getNode(), solution.getStatesGenerated() + statesGenerated);
                } catch (SearchCutoff cutoff) {
                    cutoffOccurred = true;
                    statesGenerated += cutoff.getStatesGenerated();
                } catch (SearchFailure failure) {
                    statesGenerated += failure.getStatesGenerated();
                }
            }
            if (cutoffOccurred) {
                throw new SearchCutoff(statesGenerated);
            } else {
                throw new SearchFailure(statesGenerated);
            }
        }
    }

    public Solution iterativeDeepeningSearch(int maxDepth) throws SearchFailure, SearchCutoff {
        boolean cutoffOccurred = false;
        int[] statesGenerated = new int[maxDepth];
        Solution solution = null;
        for (int i = 0; i < maxDepth; i++) {
            try {
                solution = depthLimitedSearch(i);
                statesGenerated[i] = solution.getStatesGenerated();
                break; // solution found - break out of loop
            } catch (SearchCutoff e) {
                statesGenerated[i] = e.getStatesGenerated();
                if (i + 1 == maxDepth) {
                    cutoffOccurred = true; // report searchCutoff only if last iteration reached cutoff
                }
            }  catch (SearchFailure e) { // do not report failure until iteration completes
                statesGenerated[i] = e.getStatesGenerated();
            }
        }

        if (solution != null) {
            return new Solution(solution.getNode(), statesGenerated);
        }
        if (cutoffOccurred) {
            throw new SearchCutoff();
        }
        throw new SearchFailure();
    }

    private Node<StateType> childNode(Node<StateType> parent, ActionType action) {
        StateType result = this.problem.result(parent.getState(), action);
        return new Node<StateType>(result, parent, action, parent.getPathCost() + problem.stepCost(parent.getState(), action));
    }

}
