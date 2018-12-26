package search.mockgame;

import search.Player;

public class MockGamePlayer implements Player {
    public enum PlayerColor { RED, BLUE }

    private PlayerColor color;

    public MockGamePlayer(PlayerColor color) {
        this.color = color;
    }

    public PlayerColor getColor() {
        return color;
    }
}
