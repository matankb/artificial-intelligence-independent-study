package search;

import java.util.ArrayList;
import java.util.LinkedList;

public class Searcher {

    private Problem problem;

    public Searcher(Problem problem) {
        this.problem = problem;
    }

    public Solution breadthSearch() throws SearchFailure {
        Node node = new Node(this.problem.getInitialState(), null, null, 0);
        if (problem.goalTest(node.getState())) {
            return new Solution(node);
        }
        LinkedList<Node> frontier = new LinkedList<Node>();
        frontier.add(node);
        LinkedList<State> explored = new LinkedList<State>();
        while (true) {
            if (frontier.isEmpty()) {
                throw new SearchFailure();
            }
            node = frontier.pop();
            explored.add(node.getState());
            ArrayList<Action> actions = problem.getActions(node);
            for (Action action: actions) {
                Node child = this.childNode(node, action);
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
        Node node = new Node(this.problem.getInitialState(), null, null, 0);
        if (problem.goalTest(node.getState())) {
            return new Solution(node);
        }
        LinkedList<Node> frontier = new LinkedList<Node>();
        frontier.add(node);
        LinkedList<State> explored = new LinkedList<State>();
        while (true) {
            if (frontier.isEmpty()) {
                throw new SearchFailure();
            }
            node = frontier.removeLast();
            explored.add(node.getState());
            ArrayList<Action> actions = problem.getActions(node);
            for (Action action: actions) {
                Node child = this.childNode(node, action);
                if (!frontier.contains((child)) && !explored.contains(child.getState())) {
                    if (problem.goalTest(child.getState())) {
                        return new Solution(child);
                    }
                    frontier.addFirst(child);
                }
            }
        }
    }

    public Solution depthLimitedSearch(int limit, int level) throws SearchFailure, SearchCutoff {
        Node node = new Node(this.problem.getInitialState(), null, null, 0);
        return recursiveDepthLimitedSearch(node, limit, level);
    }

    private Solution recursiveDepthLimitedSearch(Node node, int limit, int level) throws SearchFailure, SearchCutoff {
        if (problem.goalTest(node.getState())) {
            return new Solution(node, 0);
        } else if (limit == 0) {
            throw new SearchCutoff(0);
        } else {
            int statesGenerated = 0;
            boolean cutoffOccurred = false;
            ArrayList<Action> actions = this.problem.getActions(node);
            for (Action action: actions) {
                Node child = this.childNode(node, action);
                statesGenerated++;
                try {
                    Solution solution = recursiveDepthLimitedSearch(child, limit - 1, level);
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
               solution = depthLimitedSearch(i, i);
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

    private Node childNode(Node parent, Action action) {
        return new Node(this.problem.result(parent.getState(), action), parent, action, parent.getPathCost() + problem.stepCost(parent.getState(), action));
    }

}
