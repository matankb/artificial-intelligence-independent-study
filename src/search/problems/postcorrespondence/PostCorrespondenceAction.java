package search.problems.postcorrespondence;

import search.Action;

public class PostCorrespondenceAction implements Action {

    private String[] domino;

    PostCorrespondenceAction(String[] domino) {
        this.domino = domino;
    }
    String[] getDomino() {
        return domino;
    }

    @Override
    public String toString() {
        return "Add domino (" + domino[0] + ", " + domino[1] + ")";
    }
}
