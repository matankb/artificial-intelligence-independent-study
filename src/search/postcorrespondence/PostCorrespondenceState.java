package search.postcorrespondence;

import search.State;

import java.util.ArrayList;

public class PostCorrespondenceState extends State {

    private ArrayList<String[]> state;

    public PostCorrespondenceState(ArrayList<String[]> state) {
        this.state = state;
    }

    public ArrayList<String[]> getState() {
        return state;
    }
    public String toString() {
        String[] strings = getFullStrings();
        return strings[0] + " " + strings[1];
    }

    public String[] getFullStrings() {
        StringBuilder topString = new StringBuilder();
        StringBuilder bottomString = new StringBuilder();
        ArrayList<String[]> strings = state;
        for (String[] str: strings) {
            topString.append(str[0]);
            bottomString.append(str[1]);
        }
        return new String[]{topString.toString(), bottomString.toString()};
    }

}
