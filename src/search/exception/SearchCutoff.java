package search.exception;

public class SearchCutoff extends SearchException {
    public SearchCutoff() {super();}
    public SearchCutoff(int statesGenerated) {super(statesGenerated);}
}
