package search.slide;

import search.State;

import java.util.Arrays;

public class SlideState extends State {

    private int[][] state;

    SlideState(int[][] state) {
        this.state = state;
    }

    int[][] getState() {
        return state;
    }

    public boolean equals(Object otherState) {
        if (!(otherState instanceof  SlideState)) {
            return false;
        }
        return Arrays.deepEquals(((SlideState) otherState).getState(), state);
    }
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            str.append("[");
            for (int j = 0; j < 3; j++) {
                str.append(state[i][j]).append(", ");
            }
            str.append("]\n");
        }
        return str.toString();
    }

}
