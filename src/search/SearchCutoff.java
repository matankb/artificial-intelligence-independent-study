package search;

public class SearchCutoff extends Exception {
    private int statesGenerated = 0;
    public SearchCutoff(int statesGenerated) {
        this.statesGenerated = statesGenerated;
    }
    public SearchCutoff() {};
    public int getStatesGenerated() {
        return statesGenerated;
    }
}
