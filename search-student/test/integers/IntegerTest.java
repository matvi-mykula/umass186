package integers;

// package search;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import mazes.Cell;
import mazes.Maze;
import mazes.MazeGenerator;
import search.SearchProblem;
import search.Searcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class IntegerTest {
    public static void main(String[] args) {
        testPositiveIntegerSearch();
        testNegativeIntegerSearch();
    }

    public static void testPositiveIntegerSearch() {
        SearchProblem<Integer> problem = new FindIntegersProblem(1, 10, true);
        Searcher<Integer> searcher = new Searcher<>(problem);
        List<Integer> solution = searcher.findSolution();

        System.out.println("Positive Integer Search Test:");
        System.out.println("Goal: Positive Integer");
        System.out.println("Range: 1 to 10");
        System.out.println("Solution: " + solution);
        System.out.println("States in Solution: " + solution.size());
        System.out.println();
    }

    public static void testNegativeIntegerSearch() {
        SearchProblem<Integer> problem = new FindIntegersProblem(-10, -1, false);
        Searcher<Integer> searcher = new Searcher<>(problem);
        List<Integer> solution = searcher.findSolution();

        System.out.println("Negative Integer Search Test:");
        System.out.println("Goal: Negative Integer");
        System.out.println("Range: -10 to -1");
        System.out.println("Solution: " + solution);
        System.out.println("States in Solution: " + solution.size());
        System.out.println();
    }
}
