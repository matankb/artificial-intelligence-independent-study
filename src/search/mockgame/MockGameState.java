package search.mockgame;

import search.State;

class MockGameState implements State {
    private int index;

    MockGameState(int index) {
        this.index = index;
    }

    int getIndex() {
        return index;
    }
}
