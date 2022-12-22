package search.problems.postcorrespondence;

import search.State;

public class PostCorrespondenceState implements State {

    private String[] state;

    PostCorrespondenceState(String[] state) {
        this.state = state;
    }

    String[] getState() {
        return state;
    }
    public String toString() {
        return state[0] + " " + state[1];
    }

}
