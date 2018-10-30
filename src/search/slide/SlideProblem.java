package search.slide;

import search.Action;
import search.Node;
import search.Problem;
import search.State;

import java.util.ArrayList;
import java.util.Arrays;

public class SlideProblem implements Problem {

    private int[][] initialState = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 0, 0, 0},
    };
    private int[][] goalState = {
            {0, 0, 0, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
    };

    @Override
    public SlideState getInitialState() {
        return new SlideState(initialState);
    }

    @Override
    public boolean goalTest(State state) {
        return Arrays.deepEquals(((SlideState) state).getState(), goalState);
    }

    @Override
    public ArrayList<Action> getActions(Node n) {
        ArrayList<Action> actions = new ArrayList<>();
        int[][] board = ((SlideState) n.getState()).getState();
        int[] blankCords = getBlankCords(board);
        int blankRow = blankCords[0];
        int blankCol = blankCords[1];
        if (blankRow > 0) {
            actions.add(new SlideAction(SlideAction.directions.UP));
        }
        if (blankRow < 3) {
            actions.add(new SlideAction(SlideAction.directions.DOWN));
        }
        if (blankCol < 3) {
            actions.add(new SlideAction(SlideAction.directions.RIGHT));
        }
        if (blankCol > 0) {
            actions.add(new SlideAction(SlideAction.directions.LEFT));
        }
        return actions;
    }

    @Override
    public SlideState result(State parentState, Action action) {
        SlideAction slideAction = (SlideAction) action;
        SlideState slideParentState = (SlideState) parentState;
        int[][] board = slideParentState.getState();
        int[][] resultBoard = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
        };
        int[] blankCords = getBlankCords(board);
        int blankRow = blankCords[0];
        int blankCol = blankCords[1];
        switch (slideAction.getDirection()) {
            case LEFT:
                resultBoard[blankRow][blankCol - 1] = 1;
                break;
            case RIGHT:
                resultBoard[blankRow][blankCol + 1] = 1;
                break;
            case UP:
                resultBoard[blankRow - 1][blankCol] = 1;
                break;
            case DOWN:
                resultBoard[blankRow + 1][blankCol] = 1;
        }
        return new SlideState(resultBoard);
    }

    @Override
    public int stepCost(State parentState, Action action) {
        return 1;
    }

    private int[] getBlankCords(int[][] board) {
        int blankRow = 0;
        int blankCol = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 1) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{blankRow, blankCol};
    }

}
