package search;

import java.util.ArrayList;
import java.util.LinkedList;

public class Searcher {

    private Problem problem;

    public Searcher(Problem problem) {
        this.problem = problem;
    }

    public Node breadthSearch() throws SearchFailure {
        Node node = new Node(this.problem.getInitialState(), null, null, 0);
        if (problem.goalTest(node.getState())) {
            return node;
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
                        return child;
                    }
                    frontier.add(child);
                }
            }
        }
    }

    public Node depthSearch() throws SearchFailure {
        Node node = new Node(this.problem.getInitialState(), null, null, 0);
        if (problem.goalTest(node.getState())) {
            return node;
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
                        return child;
                    }
                    frontier.addFirst(child);
                }
            }
        }
    }

    public Node depthLimitedSearch(int limit) throws SearchFailure, SearchCutoff {
        Node node = new Node(this.problem.getInitialState(), null, null, 0);
        return recursiveDepthLimitedSearch(node, limit);
    }

    private Node recursiveDepthLimitedSearch(Node node, int limit) throws SearchFailure, SearchCutoff {
        if (problem.goalTest(node.getState())) {
            return node;
        } else if (limit == 0) {
            throw new SearchCutoff();
        } else {
            boolean cutoffOccurred = false;
            ArrayList<Action> actions = this.problem.getActions(node);
            for (Action action: actions) {
                Node child = this.childNode(node, action);
                try {
                    return recursiveDepthLimitedSearch(child, limit - 1);
                } catch (SearchCutoff cutoff) {
                    cutoffOccurred = true;
                } catch (SearchFailure failure) {}
            }
            if (cutoffOccurred) {
                throw new SearchCutoff();
            } else {
                throw new SearchFailure();
            }
        }
    }

    public Node iterativeDeepeningSearch(int maxDepth) throws SearchFailure {
       for (int i = 0; i < maxDepth; i++) {
           try {
               return depthLimitedSearch(i);
           } catch (SearchCutoff | SearchFailure e) {}  // do not report failure until iteration completes
        }
        throw new SearchFailure();
    }

    private Node childNode(Node parent, Action action) {
        return new Node(this.problem.result(parent.getState(), action), parent, action, parent.getPathCost() + problem.stepCost(parent.getState(), action));
    }

}
