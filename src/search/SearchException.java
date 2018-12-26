package search;

class SearchException extends Exception {
    private int statesGenerated = 0; // used for DLS

    SearchException() {}
    SearchException(int statesGenerated) {
        this.statesGenerated = statesGenerated;
    }

    int getStatesGenerated() {
        return statesGenerated;
    }
}
