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
        return new PostCorrespondenceState(new String[]{"", ""}); // empty dominoes
    }

    @Override
    public boolean goalTest(State s) {
        String[] strings = ((PostCorrespondenceState) s).getState();
        return !strings[0].isEmpty() && strings[0].equals(strings[1]);
    }

    @Override
    public ArrayList<Action> getActions(Node n) {
        ArrayList<Action> actions = new ArrayList<>();
        for (String[] domino: dominoes) {
            String[] newDominoes = addDomino(((PostCorrespondenceState) n.getState()).getState(), domino);
            if (newDominoes[1].startsWith(newDominoes[0]) || newDominoes[0].startsWith(newDominoes[1])) {
                PostCorrespondenceAction action = new PostCorrespondenceAction(domino);
                actions.add(action);
            }
        }
        return actions;
    }

    @Override
    public State result(State parentState, Action action) {
        PostCorrespondenceState state = (PostCorrespondenceState) parentState;
        String[] addedDomino = ((PostCorrespondenceAction) action).getDomino();
        String[] newDominoes = addDomino(state.getState(), addedDomino);
        return new PostCorrespondenceState(newDominoes);
    }

    @Override
    public int stepCost(State parentState, Action action) {
        return 0;
    }

    private String[] addDomino(String[] dominoes, String[] newDominoes) {
        return new String[]{
            dominoes[0] + newDominoes[0],
            dominoes[1] + newDominoes[1],
        };
    }

}
