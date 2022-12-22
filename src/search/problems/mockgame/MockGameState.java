package search.problems.mockgame;

import search.State;

public class MockGameState implements State {
    private int index;

    public MockGameState(int index) {
        this.index = index;
    }

    int getIndex() {
        return index;
    }
}
