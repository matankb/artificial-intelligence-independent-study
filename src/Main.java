import search.*;
import search.postcorrespondence.PostCorrespondenceProblem;
import search.slide.SlideProblem;

import java.util.Comparator;

interface RunnableSearch {
    Node runSearch() throws SearchFailure, SearchCutoff;
}

public class Main {

    public static void main(String[] args) {
        SlideProblem slideProblem = new SlideProblem();
        PostCorrespondenceProblem postCorrespondenceProblem = new PostCorrespondenceProblem(new String[][]{{"aa", "aaa"}, {"bba", "b"}, {"a", "abba"}});

        runSearches("Slide Problem", slideProblem, 10, 10);
        runSearches("Post Correspondence Problem", postCorrespondenceProblem, 10, 10);
    }

    private static void runSearches(String problemName, Problem problem, int maxDepth, int maxIterativeDepth) {
        System.out.println(problemName + ":");
        Searcher searcher = new Searcher(problem);

        RunnableSearch breadthSearch = () -> searcher.breadthSearch();
        RunnableSearch depthSearch = () -> searcher.depthSearch();
        RunnableSearch depthLimitedSearch = () -> searcher.depthLimitedSearch(4);
        RunnableSearch iterativeDeepeningSearch = () -> searcher.iterativeDeepeningSearch(maxIterativeDepth);

        printSearch("Breadth-First Search", breadthSearch);
        printSearch("Depth-First Search", depthSearch);
        printSearch("Depth-Limited Search", depthLimitedSearch);
        printSearch("Iterative Deepening Search", iterativeDeepeningSearch);
        System.out.println("------------");
    }

    private static void printSearch(String name, RunnableSearch search) {
        try {
            Node result = search.runSearch();
            Node node = result;
            StringBuilder steps = new StringBuilder();
            while (node.getParent() != null) {
                steps.append(node.getAction()).append(" ");
                node = node.getParent();
            }
            System.out.println("Solution found for \033[0;1m" + name + "\033[0;0m! Steps: " + steps);
            System.out.println(result.getState());
        } catch (SearchCutoff e) {
            System.out.println("Search cutoff reached for " + name);
        } catch (SearchFailure e) {
            System.out.println("No solution found for " + name);
        }
    }

    private static void runPriorityQueue() {
        PriorityQueue<String> myQueue = new PriorityQueue<>(Comparator.<String>naturalOrder());
        System.out.println(myQueue.isEmpty());
        myQueue.insert("a");
        myQueue.insert("c");
        myQueue.insert("b");
        System.out.println(myQueue.pop());
        System.out.println(myQueue.pop());
        myQueue.insert("z");
        System.out.println(myQueue.pop());
        System.out.println(myQueue.isEmpty());
    }

}
