package search.searcher;

import search.*;

import java.util.ArrayList;

public class AdversarialSearcher<Problem extends AdversarialSearchProblem<StateType, ActionType, PlayerType>, StateType extends State, ActionType extends Action, PlayerType extends Player> extends Searcher<Problem> {

    private final PlayerType player;

    public AdversarialSearcher(Problem problem, PlayerType player) {
        super(problem);
        this.player = player;
    }

    /* CHAPTER 5 */

    public Solution miniMaxSearch(StateType state) {
        ArrayList<ActionType> actions = problem.getActions(state);
        double maxUtility = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        for (ActionType action: actions) {
            double utility = this.minValue(this.problem.result(state, action));
            if (utility > maxUtility) {
                maxUtility = utility;
                bestAction = action;
            }
        }
        return new Solution(bestAction);
    }
    private double maxValue(StateType state) {
        if (problem.terminalTest(state)) {
            return problem.utility(state, this.player);
        }
        double value = Double.NEGATIVE_INFINITY;
        ArrayList<ActionType> actions = problem.getActions(state);
        for (ActionType action: actions) {
            StateType result = this.problem.result(state, action);
            value = Math.max(value, minValue(result));
        }
        return value;
    }
    private double minValue(StateType state) {
        if (problem.terminalTest(state)) {
            return problem.utility(state, this.player);
        }
        double value = Double.POSITIVE_INFINITY;
        ArrayList<ActionType> actions = problem.getActions(state);
        for (ActionType action: actions) {
            StateType result = problem.result(state, action);
            value = Math.min(value, maxValue(result));
        }
        return value;
    }

    public Solution alphaBetaSearch(StateType state) {
        ArrayList<ActionType> actions = problem.getActions(state);
        double maxUtility = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        for (ActionType action: actions) {
            double utility = this.alphaBetaMinValue(this.problem.result(state, action), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            if (utility > maxUtility) {
                maxUtility = utility;
                bestAction = action;
            }
        }
        return new Solution(bestAction);
    }
    private double alphaBetaMaxValue(StateType state, double alpha, double beta) {
        if (problem.terminalTest(state)) {
            return problem.utility(state, this.player);
        }
        double value = Double.NEGATIVE_INFINITY;
        ArrayList<ActionType> actions = problem.getActions(state);
        for (ActionType action: actions) {
            StateType result = this.problem.result(state, action);
            value = Math.max(value, alphaBetaMinValue(result, alpha, beta));
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value;
    }
    private double alphaBetaMinValue(StateType state, double alpha, double beta) {
        if (problem.terminalTest(state)) {
            return problem.utility(state, this.player);
        }
        double value = Double.POSITIVE_INFINITY;
        ArrayList<ActionType> actions = problem.getActions(state);
        for (ActionType action: actions) {
            StateType result = this.problem.result(state, action);
            value = Math.min(value, alphaBetaMaxValue(result, alpha, beta));
            if (value <= alpha) {
                return value;
            }
            beta = Math.min(beta, value);
        }
        return value;
    }

    public Solution minMaxCutoffSearch(StateType state, double maxDepth) {
        ArrayList<ActionType> actions = problem.getActions(state);
        double maxUtility = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        for (ActionType action: actions) {
            StateType result = this.problem.result(state, action);
            double utility = this.minValueCutoff(result, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, maxDepth);
            if (utility > maxUtility) {
                maxUtility = utility;
                bestAction = action;
            }
        }
        return new Solution(bestAction);
    }
    private double maxValueCutoff(StateType state, double alpha, double beta, double depth) {
        if (cutoffTest(state, depth)) {
            return problem.eval(state, this.player);
        }
        double value = Double.NEGATIVE_INFINITY;
        ArrayList<ActionType> actions = problem.getActions(state);
        for (ActionType action: actions) {
            StateType result = this.problem.result(state, action);
            value = Math.max(value, minValueCutoff(result, alpha, beta, depth - 1));
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value;
    }
    private double minValueCutoff(StateType state, double alpha, double beta, double depth) {
        if (cutoffTest(state, depth)) {
            return problem.eval(state, this.player);
        }
        double value = Double.POSITIVE_INFINITY;
        ArrayList<ActionType> actions = problem.getActions(state);
        for (ActionType action: actions) {
            StateType result = this.problem.result(state, action);
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

    public Solution generalMinimaxSearch(StateType state) {
        ArrayList<ActionType> actions = problem.getActions(state);
        double max = Double.NEGATIVE_INFINITY;
        Action bestAction = null;
        for (ActionType action: actions) {
            double worstUtility = worstCaseUtility(problem.result(state, action));
            if (worstUtility > max) {
                max = worstUtility;
                bestAction = action;
            }
        }
        return new Solution(bestAction);
    }
    private double worstCaseUtility(StateType state) {
        if (problem.terminalTest(state)) {
            return problem.utility(state, this.player);
        }
        double min = Double.POSITIVE_INFINITY;
        ArrayList<ActionType> actions = problem.getActions(state);
        for (ActionType action: actions) {
            StateType result = problem.result(state, action);
            min = Math.min(min, worstCaseUtility(result));
        }
        return min;
    }


}
