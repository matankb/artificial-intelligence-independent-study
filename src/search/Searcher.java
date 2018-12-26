package search;

import java.util.ArrayList;
import java.util.LinkedList;

// A generic Searcher class which accepts a generic SearchProblem
// Can run breadth-first search, depth-first search, and recursive depth-limited search on a BasicSearchProblem
// Can run Minimax search on an AdversarialSearchProblem
// most of the search algorithms come from the book's pseudocode
public class Searcher {

    private SearchProblem problem;
    private Player player;

    public Searcher(SearchProblem problem) {
        this.problem = problem;
    }
    public Searcher(SearchProblem problem, Player player) {
        this.problem = problem;
        this.player = player;
    }

    /* CHAPTER 3 */

    public Solution breadthSearch() throws SearchFailure {
        BasicSearchProblem problem = (BasicSearchProblem) this.problem;
        Node node = new Node(problem.getInitialState(), null, null, 0);
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
            ArrayList<Action> actions = problem.getActions(node.getState());
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
        BasicSearchProblem problem = (BasicSearchProblem) this.problem;
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
            ArrayList<Action> actions = problem.getActions(node.getState());
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

    public Solution depthLimitedSearch(int limit) throws SearchFailure, SearchCutoff {
        Node node = new Node(this.problem.getInitialState(), null, null, 0);
        return recursiveDepthLimitedSearch(node, limit);
    }

    private Solution recursiveDepthLimitedSearch(Node node, int limit) throws SearchFailure, SearchCutoff {
        BasicSearchProblem problem = (BasicSearchProblem) this.problem;
        if (problem.goalTest(node.getState())) {
            return new Solution(node, 0);
        } else if (limit == 0) {
            throw new SearchCutoff(0);
        } else {
            int statesGenerated = 0;
            boolean cutoffOccurred = false;
            ArrayList<Action> actions = problem.getActions(node.getState());
            for (Action action: actions) {
                Node child = this.childNode(node, action);
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

    private Node childNode(Node parent, Action action) {
        BasicSearchProblem problem = (BasicSearchProblem) this.problem;
        State result = this.problem.result(parent.getState(), action);
        return new Node(result, parent, action, parent.getPathCost() + problem.stepCost(parent.getState(), action));
    }

    /* CHAPTER 5 */

    public Solution miniMaxSearch(State state) {
        AdversarialSearchProblem problem = (AdversarialSearchProblem) this.problem;
        ArrayList<Action> actions = problem.getActions(state);
        double maxUtility = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        for (Action action: actions) {
            double utility = this.minValue(this.problem.result(state, action));
            if (utility > maxUtility) {
                maxUtility = utility;
                bestAction = action;
            }
        }
        return new Solution(bestAction);
    }
    private double maxValue(State state) {
        AdversarialSearchProblem problem = (AdversarialSearchProblem) this.problem;
        if (problem.terminalTest(state)) {
            return problem.utility(state, this.player);
        }
        double value = Double.NEGATIVE_INFINITY;
        ArrayList<Action> actions = problem.getActions(state);
        for (Action action: actions) {
            State result = this.problem.result(state, action);
            value = Math.max(value, minValue(result));
        }
        return value;
    }
    private double minValue(State state) {
        AdversarialSearchProblem problem = (AdversarialSearchProblem) this.problem;
        if (problem.terminalTest(state)) {
            return problem.utility(state, this.player);
        }
        double value = Double.POSITIVE_INFINITY;
        ArrayList<Action> actions = problem.getActions(state);
        for (Action action: actions) {
            State result = problem.result(state, action);
            value = Math.min(value, maxValue(result));
        }
        return value;
    }

    public Solution alphaBetaSearch(State state) {
        AdversarialSearchProblem problem = (AdversarialSearchProblem) this.problem;
        ArrayList<Action> actions = problem.getActions(state);
        double maxUtility = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        for (Action action: actions) {
            double utility = this.alphaBetaMinValue(this.problem.result(state, action), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            if (utility > maxUtility) {
                maxUtility = utility;
                bestAction = action;
            }
        }
        return new Solution(bestAction);
    }
    private double alphaBetaMaxValue(State state, double alpha, double beta) {
        AdversarialSearchProblem problem = (AdversarialSearchProblem) this.problem;
        if (problem.terminalTest(state)) {
            return problem.utility(state, this.player);
        }
        double value = Double.NEGATIVE_INFINITY;
        ArrayList<Action> actions = problem.getActions(state);
        for (Action action: actions) {
            State result = this.problem.result(state, action);
            value = Math.max(value, alphaBetaMinValue(result, alpha, beta));
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value;
    }
    private double alphaBetaMinValue(State state, double alpha, double beta) {
        AdversarialSearchProblem problem = (AdversarialSearchProblem) this.problem;
        if (problem.terminalTest(state)) {
            return problem.utility(state, this.player);
        }
        double value = Double.POSITIVE_INFINITY;
        ArrayList<Action> actions = problem.getActions(state);
        for (Action action: actions) {
            State result = this.problem.result(state, action);
            value = Math.min(value, alphaBetaMaxValue(result, alpha, beta));
            if (value <= alpha) {
                return value;
            }
            beta = Math.min(beta, value);
        }
        return value;
    }

    public Solution minMaxCutoffSearch(State state, double maxDepth) {
        AdversarialSearchProblem problem = (AdversarialSearchProblem) this.problem;
        ArrayList<Action> actions = problem.getActions(state);
        double maxUtility = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        for (Action action: actions) {
            State result = this.problem.result(state, action);
            double utility = this.minValueCutoff(result, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, maxDepth);
            if (utility > maxUtility) {
                maxUtility = utility;
                bestAction = action;
            }
        }
        return new Solution(bestAction);
    }
    private double maxValueCutoff(State state, double alpha, double beta, double depth) {
        AdversarialSearchProblem problem = (AdversarialSearchProblem) this.problem;
        if (cutoffTest(state, depth)) {
            return problem.eval(state, this.player);
        }
        double value = Double.NEGATIVE_INFINITY;
        ArrayList<Action> actions = problem.getActions(state);
        for (Action action: actions) {
            State result = this.problem.result(state, action);
            value = Math.max(value, minValueCutoff(result, alpha, beta, depth - 1));
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value;
    }
    private double minValueCutoff(State state, double alpha, double beta, double depth) {
        AdversarialSearchProblem problem = (AdversarialSearchProblem) this.problem;
        if (cutoffTest(state, depth)) {
            return problem.eval(state, this.player);
        }
        double value = Double.POSITIVE_INFINITY;
        ArrayList<Action> actions = problem.getActions(state);
        for (Action action: actions) {
            State result = this.problem.result(state, action);
            value = Math.min(value, maxValueCutoff(result, alpha, beta, depth - 1));
            if (value <= alpha) {
                return value;
            }
            beta = Math.min(beta, value);
        }
        return value;
    }
    private boolean cutoffTest(State state, double depth) {
        return depth == 0;
    }

    public Solution generalMinimaxSearch(State state) {
        AdversarialSearchProblem problem = (AdversarialSearchProblem) this.problem;
        ArrayList<Action> actions = problem.getActions(state);
        double max = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        for (Action action: actions) {
            double worstUtility = worstCaseUtility(problem.result(state, action));
            if (worstUtility > max) {
                max = worstUtility;
                bestAction = action;
            }
        }
        return new Solution(bestAction);
    }
    private double worstCaseUtility(State state) {
        AdversarialSearchProblem problem = (AdversarialSearchProblem) this.problem;
        if (problem.terminalTest(state)) {
            return problem.utility(state, this.player);
        }
        double min = Double.POSITIVE_INFINITY;
        ArrayList<Action> actions = problem.getActions(state);
        for (Action action: actions) {
            State result = problem.result(state, action);
            min = Math.min(min, worstCaseUtility(result));
        }
        return min;
    }

}
