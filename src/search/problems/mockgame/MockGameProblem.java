package search.problems.mockgame;

import search.AdversarialSearchProblem;

import java.util.ArrayList;

/**
 * A simple mock "game" described by the demonstration in the Adversarial Search Project document
 * In which the player can move left or right during their turn
 *
 * The game contains the following nodes:
 *      0 (blue move, initial)
 *    /   \
 *   1     2 (red move)
 *  / \   / \
 * 3   4 5   6 (terminal)
 *
 * Terminal Nodes Utility: [blue, red]
 * 3: [1, 3]
 * 4: [4, 4]
 * 5: [3, 2]
 * 6: [3, 3]
 */
public class MockGameProblem implements AdversarialSearchProblem<MockGameState, MockGameAction, MockGamePlayer> {
    @Override
    public MockGameState getInitialState() {
        return new MockGameState(0);
    }

    @Override
    public ArrayList<MockGameAction> getActions(MockGameState state) {
        int index = state.getIndex();
        if (index >= 3) return new ArrayList<>();
        ArrayList<MockGameAction> actions = new ArrayList<>();
        actions.add(new MockGameAction(MockGameAction.Direction.RIGHT));
        actions.add(new MockGameAction(MockGameAction.Direction.LEFT));
        return actions;
    }

    @Override
    public MockGameState result(MockGameState parentState, MockGameAction action) {
        int index = parentState.getIndex();
        MockGameAction.Direction direction = action.getDirection();
        boolean isLeft = direction == MockGameAction.Direction.LEFT;
        switch (index) {
            case 0:
                if (isLeft) return new MockGameState(1);
                return new MockGameState(2);
            case 1:
                if (isLeft) return new MockGameState(3);
                return new MockGameState(4);
            case 2:
                if (isLeft) return new MockGameState(5);
                return new MockGameState(6);
        }
        return new MockGameState(0);
    }

    @Override
    public boolean terminalTest(MockGameState state) {
        int index = state.getIndex();
        return index >= 3;
    }

    @Override
    public double utility(MockGameState state, MockGamePlayer p) {
        int index = state.getIndex();
        MockGamePlayer.PlayerColor color = p.getColor();
        boolean isRed = color == MockGamePlayer.PlayerColor.RED;
        switch (index) {
            case 3:
                if (isRed) return 3;
                return 1;
            case 4:
                if (isRed) return 4;
                return 4;
            case 5:
                if (isRed) return 3;
                return 2;
            case 6:
                if (isRed) return 3;
                return 3;
            default:
                return 0;
        }
    }

    @Override
    public double eval(MockGameState state, MockGamePlayer p) {
        return 0;
    }
}
