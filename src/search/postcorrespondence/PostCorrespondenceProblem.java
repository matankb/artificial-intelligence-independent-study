package search.postcorrespondence;

import search.Action;
import search.Node;
import search.Problem;
import search.State;

import java.util.ArrayList;

public class PostCorrespondenceProblem implements Problem {

    private String[][] dominoes;

    public PostCorrespondenceProblem(String[][] dominoes) {
        super();
        this.dominoes = dominoes;
    }

    @Override
    public State getInitialState() {
        return new PostCorrespondenceState(new ArrayList<String[]>());
    }

    @Override
    public boolean goalTest(State s) {
        PostCorrespondenceState state = (PostCorrespondenceState) s;
        String[] strings = state.getFullStrings();
        return state.getState().size() > 0 && strings[0].equals(strings[1]);
    }

    @Override
    public ArrayList<Action> getActions(Node n) {
        ArrayList<Action> actions = new ArrayList<>();
        for (String[] domino: dominoes) {
            PostCorrespondenceAction action = new PostCorrespondenceAction(domino);
            PostCorrespondenceState nextState = (PostCorrespondenceState) result(n.getState(), action);
            String[] strings = nextState.getFullStrings();
            if (strings[0].startsWith(strings[1]) || strings[1].startsWith(strings[0])) {
                actions.add(action);
            }
        }
        return actions;
    }

    @Override
    public State result(State parentState, Action action) {
        PostCorrespondenceState state = (PostCorrespondenceState) parentState;
        ArrayList<String[]> newDominoes = new ArrayList<String[]>(state.getState());
        newDominoes.add(((PostCorrespondenceAction) action).getDomino());
        return new PostCorrespondenceState(newDominoes);
    }

    @Override
    public int stepCost(State parentState, Action action) {
        return 0;
    }

}
