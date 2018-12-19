package search.tictactoe;

import search.Player;
import search.State;

public class TicTacToeState implements State {
    public enum CellType { X, O, EMPTY }

    private CellType[][] board;
    private Player player;

    public TicTacToeState(Player player, CellType[][] board) {
        this.player = player;
        this.board = board;
    }

    public CellType[][] getBoard() {
        return board;
    }
    public Player getCurrentPlayer() {
        return player;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            str.append("[");
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == CellType.EMPTY) {
                    str.append("_");
                } else {
                    str.append(board[i][j]);
                }
                if (j < 2) {
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
