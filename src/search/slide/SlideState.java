package search.slide;

import search.State;

import java.util.Arrays;

public class SlideState implements State {

    private int[][] state;

    SlideState(int[][] state) {
        this.state = state;
    }

    int[][] getState() {
        return state;
    }

    public boolean equals(Object otherState) {
        return (otherState instanceof SlideState) &&
                Arrays.deepEquals(((SlideState) otherState).getState(), state);
    }
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            str.append("[");
            for (int j = 0; j < 4; j++) {
                str.append(state[i][j]);
                if (j < 3) {
                    str.append(", ");
                }
            }
            str.append("]");
            if (i < 3) {
                str.append("\n");
            }
        }
        return str.toString();
    }

}
