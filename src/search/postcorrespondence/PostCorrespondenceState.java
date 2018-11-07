package search.postcorrespondence;

import search.State;

import java.util.ArrayList;

public class PostCorrespondenceState implements State {

    private String[] state;

    PostCorrespondenceState(String[] state) {
        this.state = state;
    }

    String[] getState() {
        return state;
    }
    public String toString() {
//        String[] strings = getFullStrings();
        return state[0] + " " + state[1];
    }

//    String[] getFullStrings() {
//        StringBuilder topString = new StringBuilder();
//        StringBuilder bottomString = new StringBuilder();
//        String[] strings = state;
//        for (String[] str: strings) {
//            topString.append(str[0]);
//            bottomString.append(str[1]);
//        }
//        return new String[]{topString.toString(), bottomString.toString()};
//    }

}
