package search.postcorrespondence;

import search.Action;

public class PostCorrespondenceAction extends Action {

    private String[] domino;

    public PostCorrespondenceAction(String[] domino) {
        this.domino = domino;
    }

    public String[] getDomino() {
        return domino;
    }

    @Override
    public String toString() {
        return "Add domino (" + domino[0] + ", " + domino[1] + ")";
    }
}
