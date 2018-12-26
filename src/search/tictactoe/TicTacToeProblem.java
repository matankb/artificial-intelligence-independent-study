package search.tictactoe;

import search.Action;
import search.AdversarialSearchProblem;
import search.Player;
import search.State;

import java.util.ArrayList;

public class TicTacToeProblem implements AdversarialSearchProblem {

    @Override
    public State getInitialState() {
        return new TicTacToeState(new TicTacToePlayer(TicTacToePlayer.PlayerType.X), new TicTacToeState.CellType[][]{
            {TicTacToeState.CellType.EMPTY, TicTacToeState.CellType.EMPTY, TicTacToeState.CellType.EMPTY},
            {TicTacToeState.CellType.EMPTY, TicTacToeState.CellType.EMPTY, TicTacToeState.CellType.EMPTY},
            {TicTacToeState.CellType.EMPTY, TicTacToeState.CellType.EMPTY, TicTacToeState.CellType.EMPTY}
        });
    }

    @Override
    public ArrayList<Action> getActions(State state) {
        TicTacToeState.CellType[][] board = ((TicTacToeState) state).getBoard();
        ArrayList<Action> actions = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == TicTacToeState.CellType.EMPTY) {
                    actions.add(new TicTacToeAction(new int[]{i, j}));
                }
            }
        }
        return actions;
    }

    @Override
    public State result(State state, Action action) {
        TicTacToeState gameState = (TicTacToeState) state;
        TicTacToePlayer player = (TicTacToePlayer) gameState.getCurrentPlayer();
        TicTacToeState.CellType[][] board = cloneBoard(((TicTacToeState) state).getBoard());
        int[] position = ((TicTacToeAction) action).getPosition();
        Player nextPlayer = null;
        switch (player.getPlayerType()) {
            case X:
                board[position[0]][position[1]] = TicTacToeState.CellType.X;
                nextPlayer = new TicTacToePlayer(TicTacToePlayer.PlayerType.O);
                break;
            case O:
                board[position[0]][position[1]] = TicTacToeState.CellType.O;
                nextPlayer = new TicTacToePlayer(TicTacToePlayer.PlayerType.X);
                break;
        }
        return new TicTacToeState(nextPlayer, board);
    }

    @Override
    public boolean terminalTest(State state) {
        TicTacToeState gameState = (TicTacToeState) state;
        return isBoardFull(gameState) || getWinnerCellType(gameState) != null;
    }

    @Override
    public double utility(State state, Player p) {
        TicTacToeState.CellType cellType = getWinnerCellType((TicTacToeState) state);
        if (cellType == null) {
            return 1; // tie;
        }
        TicTacToePlayer.PlayerType playerType = cellTypeToPlayerType(cellType);
        if (playerType == ((TicTacToePlayer) p).getPlayerType()) {
            return 2; // win
        }
        return 0; // loss
    }

    @Override
    public double eval(State state, Player p) {
        if (this.terminalTest(state)) {
            return this.utility(state, p);
        } else {
            TicTacToeState gameState = (TicTacToeState) state;
            TicTacToePlayer gamePlayer = (TicTacToePlayer) gameState.getCurrentPlayer();
            return gamePlayer.getPlayerType() == ((TicTacToePlayer) p).getPlayerType() ? 2 : 0;
        }
    }

    private boolean isBoardFull(TicTacToeState state) {
        TicTacToeState.CellType[][] board =  state.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                 if (board[i][j] == TicTacToeState.CellType.EMPTY) {
                     return false;
                 }
            }
        }
        return true;
    }

    private TicTacToeState.CellType getWinnerCellType(TicTacToeState state) {
        TicTacToeState.CellType[][] board = state.getBoard();
        TicTacToeState.CellType winner = null;
        for (int i = 0; i < board.length; i++) {
            TicTacToeState.CellType[] row = board[i];
            if (row[0] == row[1] && row[1] == row[2]) {
                winner = row[0];
                break;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                winner = board[0][i];
                break;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            winner = board[0][0];
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            winner = board[1][1];
        }
        return winner == TicTacToeState.CellType.EMPTY ? null : winner;
    }

    private TicTacToePlayer.PlayerType cellTypeToPlayerType(TicTacToeState.CellType cellType) {
        switch (cellType) {
            case X: return TicTacToePlayer.PlayerType.X;
            case O: return TicTacToePlayer.PlayerType.O;
            case EMPTY: return null;
            default: return null;
        }
    }

    // deep clones board
    private TicTacToeState.CellType[][] cloneBoard(TicTacToeState.CellType[][] board) {
        TicTacToeState.CellType[][] newBoard = new TicTacToeState.CellType[3][3];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board[i].length);
        }
        return newBoard;
    }
}
