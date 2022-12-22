import search.exception.SearchCutoff;
import search.exception.SearchFailure;
import search.problems.mockgame.*;
import search.problems.postcorrespondence.*;
import search.problems.slide.*;
import search.problems.tictactoe.*;
import search.*;
import search.problems.postcorrespondence.PostCorrespondenceProblem;
import search.problems.slide.SlideProblem;
import search.problems.tictactoe.TicTacToeState;
import search.searcher.AdversarialSearcher;
import search.searcher.BasicSearcher;

import java.util.ArrayList;
import java.util.Comparator;

interface RunnableSearch {
    Solution runSearch() throws SearchFailure, SearchCutoff, InvalidProblem;
}

class BasicSearchDemo<Problem extends BasicSearchProblem<StateType, ActionType>, StateType extends State, ActionType extends Action> {

    // runs all search algorithms on the problem, printing results for each one
    public void runSearches(String problemName, Problem problem, int maxDepth, int maxIterativeDepth) {
        System.out.println(problemName.toUpperCase() + ":");
        BasicSearcher<Problem, StateType, ActionType> searcher = new BasicSearcher<>(problem);

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

    private void printSearch(String name, RunnableSearch search) {
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
        } catch (InvalidProblem e) {
            System.out.println("Invalid Search Problem for " + name);
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

}

class Main {

    public static void main(String[] args) {
        demonstrateAdversarialSearches();
    }

    // CHAPTER 5: ADVERSARIAL SEARCH
    private static void demonstrateAdversarialSearches() {
        System.out.println("TIC-TAC-TOE");
        TicTacToeProblem problem = new TicTacToeProblem();
        TicTacToePlayer player = new TicTacToePlayer(TicTacToePlayer.PlayerType.X);
        AdversarialSearcher<TicTacToeProblem, TicTacToeState, TicTacToeAction, TicTacToePlayer> searcher = new AdversarialSearcher<>(problem, player);
        TicTacToeState state = new TicTacToeState(player, new TicTacToeState.CellType[][]{
                {TicTacToeState.CellType.O, TicTacToeState.CellType.EMPTY, TicTacToeState.CellType.O},
                {TicTacToeState.CellType.X, TicTacToeState.CellType.EMPTY, TicTacToeState.CellType.EMPTY},
                {TicTacToeState.CellType.O, TicTacToeState.CellType.EMPTY, TicTacToeState.CellType.EMPTY}
        });

        RunnableSearch minimaxSearch = () -> searcher.miniMaxSearch(state);
        RunnableSearch alphaBetaSearch = () -> searcher.alphaBetaSearch(state);
        RunnableSearch minimaxCutoffSearch = () -> searcher.minMaxCutoffSearch(state, 0);

        System.out.println(state);
        long minimaxTime = runAdversarialSearch("Minimax", "" + player.getPlayerType(), minimaxSearch);
        long alphaBetaTime = runAdversarialSearch("Alpha-Beta", "" + player.getPlayerType(), alphaBetaSearch);
        float difference = (minimaxTime - alphaBetaTime) / 1000000;

        System.out.printf("⌛ Alpha-Beta Search was %.0f milliseconds quicker (%.3f seconds) than regular Minimax\n", difference, difference / 1000);
        runAdversarialSearch("Minimax Cutoff", "" + player.getPlayerType(), minimaxCutoffSearch);

        System.out.println("\nMOCK MULTIPLAYER GAME");
        MockGameProblem generalProblem = new MockGameProblem();
        MockGamePlayer generalPlayer = new MockGamePlayer(MockGamePlayer.PlayerColor.BLUE);
        MockGameState generalState = generalProblem.getInitialState();
        AdversarialSearcher<MockGameProblem, MockGameState, MockGameAction, MockGamePlayer> generalSearcher = new AdversarialSearcher<>(generalProblem, generalPlayer);

        RunnableSearch generalSearch = () -> generalSearcher.generalMinimaxSearch(generalState);
        runAdversarialSearch("General Search", " " + generalPlayer.getColor(), generalSearch);

    }

    // returns elapsed time
    private static long runAdversarialSearch(String searchName, String playerName, RunnableSearch search) {
        try {
            long startTime = System.nanoTime();
            Solution solution = search.runSearch();
            long endTime = System.nanoTime();
            Action action = solution.getAction();
            System.out.println(searchName + " result: " + playerName + " should \"" + action + "\"");
            return endTime - startTime;
        } catch (SearchFailure | SearchCutoff | InvalidProblem e) {
            // these errors will never throw
            return 0;
        }
    }

    // CHAPTER 3: SOLVING PROBLEMS BY SEARCHING
    private static void demonstrateBasicSearches() {
        ArrayList<String> lines = Util.getInputFileLines("postcorrespondence.txt");
        int maximumDepth = Integer.parseInt(lines.get(0));
        String[][] dominoes = parseDominoSet(lines);

        SlideProblem slideProblem = new SlideProblem();
        PostCorrespondenceProblem postCorrespondenceProblem = new PostCorrespondenceProblem(dominoes);

        new BasicSearchDemo<SlideProblem, SlideState, SlideAction>().runSearches("Slide Problem", slideProblem, maximumDepth, maximumDepth);
        new BasicSearchDemo<PostCorrespondenceProblem, PostCorrespondenceState, PostCorrespondenceAction>().runSearches("Post Correspondence Problem", postCorrespondenceProblem, maximumDepth, maximumDepth);
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

}
