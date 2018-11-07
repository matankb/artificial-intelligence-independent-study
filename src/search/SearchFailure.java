package search;

public class SearchFailure extends SearchException {
    SearchFailure() {super();}
    SearchFailure(int statesGenerated) {super(statesGenerated);}
}
