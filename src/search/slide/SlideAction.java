package search.slide;

import search.Action;

public class SlideAction implements Action {

    public enum directions { LEFT, RIGHT, UP, DOWN }

    final private directions direction;

    SlideAction(directions d) {
        this.direction = d;
    }

    directions getDirection() {
        return direction;
    }

    public String toString() {
        switch (getDirection()) {
            case LEFT: return "Left";
            case RIGHT: return "Right";
            case UP: return "Up";
            case DOWN: return "Down";
        }
        return "Unknown";
    }

}
