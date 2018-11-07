package search;

public class SearchException extends Exception {
    private int statesGenerated = 0; // used for DLS

    public SearchException() {}
    public SearchException(int statesGenerated) {
        this.statesGenerated = statesGenerated;
    }

    public int getStatesGenerated() {
        return statesGenerated;
    }
}
