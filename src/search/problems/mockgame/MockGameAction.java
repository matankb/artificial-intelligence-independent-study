package search.problems.mockgame;

import search.Action;

public class MockGameAction implements Action {
    public enum Direction { RIGHT, LEFT }

    private final Direction direction;
    public MockGameAction(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "move " + direction.toString().toLowerCase();
    }
}
