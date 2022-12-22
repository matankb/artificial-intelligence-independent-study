package search.problems.tictactoe;

import search.Player;

public class TicTacToePlayer implements Player {
    public enum PlayerType {X, O}

    private PlayerType playerType;
    public TicTacToePlayer(PlayerType playerType) {
        this.playerType = playerType;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    @Override
    public String toString() {
        return this.getPlayerType().toString();
    }
}
