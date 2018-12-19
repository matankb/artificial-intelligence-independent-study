import search.tictactoe.TicTacToePlayer;
import search.tictactoe.TicTacToeProblem;
import search.*;
import search.postcorrespondence.PostCorrespondenceProblem;
import search.slide.SlideProblem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

interface RunnableSearch {
    Solution runSearch() throws SearchFailure, SearchCutoff;
}

class Main {


    public static void main(String[] args) {
        demonstrateAdversarialSearch();
        demonstrateBasicSearches();
    }

    // CHAPTER 5: ADVERSARIAL SEARCH
    private static void demonstrateAdversarialSearch() {
        TicTacToeProblem problem = new TicTacToeProblem();
        TicTacToePlayer player = new TicTacToePlayer(TicTacToePlayer.PlayerType.X);
        Searcher searcher = new Searcher(problem, player);
        State state = problem.getInitialState();
        Action action = searcher.minMaxSearch(state);
        System.out.println("Player " + player.getPlayerType() + " should \"" + action + "\" when the board is:");
        System.out.println(state);
    }

    // CHAPTER 3: SOLVING PROBLEMS BY SEARCHING
    private static void demonstrateBasicSearches() {
        ArrayList<String> lines = getInputFileLines("postcorrespondence.txt");
        int maximumDepth = Integer.parseInt(lines.get(0));
        String[][] dominoes = parseDominoSet(lines);

        SlideProblem slideProblem = new SlideProblem();
        PostCorrespondenceProblem postCorrespondenceProblem = new PostCorrespondenceProblem(dominoes);

        runSearches("Slide BasicSearchProblem", slideProblem, maximumDepth, maximumDepth);
        runSearches("Post Correspondence BasicSearchProblem", postCorrespondenceProblem, maximumDepth, maximumDepth);
    }


    // CHAPTER 3
    private static void demonstratePriorityQueue() {
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

    // runs all search algorithms on the problem, printing results for each one
    private static void runSearches(String problemName, BasicSearchProblem problem, int maxDepth, int maxIterativeDepth) {
        System.out.println(problemName.toUpperCase() + ":");
        Searcher searcher = new Searcher(problem);

        RunnableSearch iterativeDeepeningSearch = () -> searcher.iterativeDeepeningSearch(maxIterativeDepth);
        RunnableSearch breadthSearch = searcher::breadthSearch;
        RunnableSearch depthSearch = searcher::depthSearch;
        RunnableSearch depthLimitedSearch = () -> searcher.depthLimitedSearch(maxDepth);

        printSearch("Iterative Deepening Search", iterativeDeepeningSearch);
        printSearch("Depth-Limited Search", depthLimitedSearch);
        printSearch("Breadth-First Search", breadthSearch);
        printSearch("Depth-First Search", depthSearch);
        System.out.println("------------");
    }

    private static void printSearch(String name, RunnableSearch search) {
        try {
            Solution solution = search.runSearch();
            Node node = solution.getNode();
            StringBuilder steps = new StringBuilder();
            while (node.getParent() != null) {
                steps.append(node.getAction()).append(" ");
                node = node.getParent();
            }
            System.out.println("Solution found for \033[0;1m" + name + "\033[0;0m! Steps: " + steps);
            System.out.println(solution.getNode().getState());
            if (solution.getStatesGeneratedPerLevel() != null) { // special case for recursive DLS printing
                printRecursiveDLSLevels(solution);
            }
        } catch (SearchCutoff e) {
            System.out.println("Search cutoff reached for " + name);
        } catch (SearchFailure e) {
            System.out.println("No solution found for " + name);
        }
    }

    private static void printRecursiveDLSLevels(Solution solution) {
        int[] statesGenerated = solution.getStatesGeneratedPerLevel();
        int prevStatesGenerated = 0;
        for (int j = 0; j < statesGenerated.length; j++) {
            int currentStatesGenerated = statesGenerated[j] - prevStatesGenerated;
            if (currentStatesGenerated < 0) {
                continue; // solution found in previous iterations
            }
            System.out.println("Level " + j + ": " + currentStatesGenerated + " states generated.");
            prevStatesGenerated = statesGenerated[j];
        }
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
