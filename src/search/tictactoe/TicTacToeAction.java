package search.tictactoe;
import search.Action;

public class TicTacToeAction implements Action {
    // x-y coordinate of new position
    private int[] position = new int[2];
    public TicTacToeAction(int[] position) {
        this.position = position;
    }
    public int[] getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Place at " + position[0] + ", " + position[1];
    }
}
