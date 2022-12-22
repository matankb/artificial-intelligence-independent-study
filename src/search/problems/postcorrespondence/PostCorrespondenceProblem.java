package search.problems.postcorrespondence;

import search.*;

import java.util.ArrayList;

public class PostCorrespondenceProblem implements BasicSearchProblem<PostCorrespondenceState, PostCorrespondenceAction> {

    private String[][] dominoes;

    public PostCorrespondenceProblem(String[][] dominoes) {
        super();
        this.dominoes = dominoes;
    }

    @Override
    public PostCorrespondenceState getInitialState() {
        return new PostCorrespondenceState(new String[]{"", ""}); // empty dominoes
    }

    @Override
    public boolean goalTest(PostCorrespondenceState s) {
        String[] strings = ((PostCorrespondenceState) s).getState();
        return !strings[0].isEmpty() && strings[0].equals(strings[1]);
    }

    @Override
    public ArrayList<PostCorrespondenceAction> getActions(PostCorrespondenceState state) {
        ArrayList<PostCorrespondenceAction> actions = new ArrayList<>();
        for (String[] domino: dominoes) {
            String[] newDominoes = addDomino(((PostCorrespondenceState) state).getState(), domino);
            if (newDominoes[1].startsWith(newDominoes[0]) || newDominoes[0].startsWith(newDominoes[1])) {
                PostCorrespondenceAction action = new PostCorrespondenceAction(domino);
                actions.add(action);
            }
        }
        return actions;
    }

    @Override
    public PostCorrespondenceState result(PostCorrespondenceState state, PostCorrespondenceAction action) {
        String[] addedDomino = action.getDomino();
        String[] newDominoes = addDomino(state.getState(), addedDomino);
        return new PostCorrespondenceState(newDominoes);
    }

    @Override
    public int stepCost(PostCorrespondenceState parentState, PostCorrespondenceAction action) {
        return 0;
    }

    private String[] addDomino(String[] dominoes, String[] newDominoes) {
        return new String[]{
            dominoes[0] + newDominoes[0],
            dominoes[1] + newDominoes[1],
        };
    }

}
