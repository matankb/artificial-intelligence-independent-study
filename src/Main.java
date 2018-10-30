import search.*;
import search.postcorrespondence.PostCorrespondenceProblem;
import search.slide.SlideProblem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

interface RunnableSearch {
    Node runSearch() throws SearchFailure, SearchCutoff;
}

class Main {

    // Demonstrates the Breadth-First, Depth-First, Depth-Limited, and Iterative-Deepening Search
    // Using the Slide Problem and the Post Correspondence Problem
    // (the post correspondence problem receives input from input/postcorrespondence.txt, as described in the assignment)
    public static void main(String[] args) {
        ArrayList<String> lines = getInputFileLines("postcorrespondence.txt");
        int maximumDepth = Integer.parseInt(lines.get(0));
        String[][] dominoes = parseDominoSet(lines);

        SlideProblem slideProblem = new SlideProblem();
        PostCorrespondenceProblem postCorrespondenceProblem = new PostCorrespondenceProblem(dominoes);

        runSearches("Slide Problem", slideProblem, maximumDepth, maximumDepth);
        runSearches("Post Correspondence Problem", postCorrespondenceProblem, maximumDepth, maximumDepth);
    }

    // runs all search algorithms on the problem, printing results for each one
    private static void runSearches(String problemName, Problem problem, int maxDepth, int maxIterativeDepth) {
        System.out.println(problemName + ":");
        Searcher searcher = new Searcher(problem);

        RunnableSearch breadthSearch = searcher::breadthSearch;
        RunnableSearch depthSearch = searcher::depthSearch;
        RunnableSearch depthLimitedSearch = () -> searcher.depthLimitedSearch(maxDepth);
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

    // demonstration of PriorityQueue class
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

    private static String[][] parseDominoSet(ArrayList<String> lines) {
        int dominoesLength = Integer.parseInt(lines.get(1));
        String[] dominoes = lines.subList(2, lines.size()).toArray(new String[dominoesLength]);
        String[][] set = new String[dominoes.length][2];
        for (int i = 0; i < dominoes.length; i++) {
            String[] parts = dominoes[i].split(" ");
            set[i] = new String[]{parts[1], parts[2]};
        }
        return set;
    }

    private static ArrayList<String> getInputFileLines(String fileName) {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            Files.lines(Paths.get("./input/" + fileName)).forEach(lines::add);
        }  catch (IOException e) {
            System.out.println("Error reading post correspondence input file");
        }
        return lines;
    }

}
