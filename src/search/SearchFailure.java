package search;

public class SearchFailure extends Exception {
    private int statesGenerated = 0;
    public SearchFailure(int statesGenerated) {
        this.statesGenerated = statesGenerated;
    }
    public SearchFailure() {}

    public int getStatesGenerated() {
        return statesGenerated;
    }
}
