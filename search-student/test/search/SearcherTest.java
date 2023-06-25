/*
 * Copyright 2021 Marc Liberatore.
 */

package search;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import mazes.Cell;
import mazes.Maze;
import mazes.MazeGenerator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class SearcherTest {
	// @Rule
	// public Timeout globalTimeout = new Timeout(500L, TimeUnit.MILLISECONDS);

	private Maze maze;

	@Before
	public void before() {
		maze = new MazeGenerator(3, 3, 2).generateDfs();
		/*
		 * maze should now be:
		 * #0#1#2#
		 * 0 S 0
		 * # # # #
		 * 1 1
		 * # ### #
		 * 2 G 2
		 * #0#1#2#
		 */
	}

	@Test
	public void testIsValidSolution() {
		List<Cell> solution = new ArrayList<Cell>();
		final Searcher<Cell> s = new Searcher<Cell>(maze);
		System.out.println(s);
		solution.add(new Cell(1, 0));
		solution.add(new Cell(0, 0));
		solution.add(new Cell(0, 1));
		solution.add(new Cell(0, 2));
		solution.add(new Cell(1, 2));
		assertTrue(s.isValidSolution(solution));
	}

	@Test
	public void testSolutionStartsNotAtInitialState() {
		List<Cell> solution = new ArrayList<Cell>();
		final Searcher<Cell> s = new Searcher<Cell>(maze);
		solution.add(new Cell(0, 0));
		solution.add(new Cell(0, 1));
		solution.add(new Cell(0, 2));
		solution.add(new Cell(1, 2));
		assertFalse(s.isValidSolution(solution));
	}

	@Test
	public void testSolutionDoesNotReachGoal() {
		List<Cell> solution = new ArrayList<Cell>();
		final Searcher<Cell> s = new Searcher<Cell>(maze);
		solution.add(new Cell(1, 0));
		solution.add(new Cell(0, 0));
		solution.add(new Cell(0, 1));
		solution.add(new Cell(0, 2));
		assertFalse(s.isValidSolution(solution));
	}

	@Test
	public void testSolutionSkipsState() {
		List<Cell> solution = new ArrayList<Cell>();
		final Searcher<Cell> s = new Searcher<Cell>(maze);
		solution.add(new Cell(1, 0));
		solution.add(new Cell(0, 0));
		solution.add(new Cell(0, 2));
		solution.add(new Cell(1, 2));
		assertFalse(s.isValidSolution(solution));
	}

	@Test
	public void testSolutionNotAdjancentStates() {
		List<Cell> solution = new ArrayList<Cell>();
		final Searcher<Cell> s = new Searcher<Cell>(maze);
		solution.add(new Cell(1, 0));
		solution.add(new Cell(1, 1));
		solution.add(new Cell(1, 2));
		assertFalse(s.isValidSolution(solution));
	}

	@Test
	public void testSolver() {
		final Searcher<Cell> s = new Searcher<Cell>(maze);
		assertTrue(s.isValidSolution(s.findSolution()));
	}

	@Test
	public void testSolverAgain() {
		maze = new MazeGenerator(3, 3, 1).generateDfs();

		final Searcher<Cell> s = new Searcher<Cell>(maze);
		assertTrue(s.isValidSolution(s.findSolution()));
	}

	@Test
	public void testSolverNearby() {
		// Create a maze with no solution
		MazeGenerator mazeGenerator = new MazeGenerator(3, 3, 0);
		Maze maze = mazeGenerator.generateDfs();
		System.out.println(maze.toString());
		List<Cell> s = new ArrayList<Cell>();

		s.add(new Cell(1, 0));
		s.add(new Cell(1, 0));
		s.add(new Cell(2, 0));
		Searcher<Cell> searcher = new Searcher<>(maze);
		List<Cell> solution = searcher.findSolution();

		assertTrue(searcher.isValidSolution(solution));
	}

	@Test
	public void testSolverWithLargeMaze() {
		// Create a larger maze
		MazeGenerator mazeGenerator = new MazeGenerator(10, 10, 10);
		Maze maze = mazeGenerator.generateDfs();

		Searcher<Cell> searcher = new Searcher<>(maze);
		List<Cell> solution = searcher.findSolution();

		assertTrue(searcher.isValidSolution(solution));
	}

	@Test
	public void testSolverWithComplexMaze() {
		// Create a maze with complex paths
		MazeGenerator mazeGenerator = new MazeGenerator(5, 5, 5);
		Maze maze = mazeGenerator.generateDfs();

		Searcher<Cell> searcher = new Searcher<>(maze);
		List<Cell> solution = searcher.findSolution();

		assertTrue(searcher.isValidSolution(solution));
	}
}
