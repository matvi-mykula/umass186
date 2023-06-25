/*
 * Copyright 2021 Marc Liberatore.
 */

package puzzle;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import java.util.concurrent.TimeUnit;

import search.SearchProblem;

public class EightPuzzleTest {
	// @Rule
	// public Timeout globalTimeout = new Timeout(500L, TimeUnit.MILLISECONDS);

	private SearchProblem<List<Integer>> solvedPuzzle;
	private SearchProblem<List<Integer>> oneStepPuzzle;
	private final List<Integer> solved = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 });
	private final List<Integer> oneStep = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 0, 7, 8, 6 });

	@Before
	public void before() {
		solvedPuzzle = new EightPuzzle(solved);
		oneStepPuzzle = new EightPuzzle(oneStep);
	}

	@Test
	public void testInitialState() {
		assertEquals(solved, solvedPuzzle.getInitialState());
		assertEquals(oneStep, oneStepPuzzle.getInitialState());
	}

	@Test
	public void testIsGoalState() {
		assertTrue(solvedPuzzle.isGoal(solved));
		assertTrue(oneStepPuzzle.isGoal(solved));
	}

	@Test
	public void testSuccessors() {
		List<List<Integer>> successors = oneStepPuzzle.getSuccessors(oneStep);
		assertEquals(3, successors.size());
		assertTrue(successors.contains(solved));
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 2, 0, 4, 5, 3, 7, 8, 6 })));
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 2, 3, 4, 0, 5, 7, 8, 6 })));
	}

	/// gpt generated tests
	@Test
	public void testSuccessorsWithEmptySpaceAtCorner() {
		List<Integer> puzzleState = Arrays.asList(new Integer[] { 0, 2, 3, 4, 5, 6, 7, 8, 1 });
		SearchProblem<List<Integer>> puzzle = new EightPuzzle(puzzleState);

		List<List<Integer>> successors = puzzle.getSuccessors(puzzleState);

		assertEquals(2, successors.size());
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 2, 0, 3, 4, 5, 6, 7, 8, 1 })));
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 4, 2, 3, 0, 5, 6, 7, 8, 1 })));
	}

	@Test
	public void testSuccessorsWithEmptySpaceAtMiddle() {
		List<Integer> puzzleState = Arrays.asList(new Integer[] { 1, 2, 3, 4, 0, 6, 7, 8, 5 });
		SearchProblem<List<Integer>> puzzle = new EightPuzzle(puzzleState);

		List<List<Integer>> successors = puzzle.getSuccessors(puzzleState);

		assertEquals(4, successors.size());
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 0, 3, 4, 2, 6, 7, 8, 5 })));
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 2, 3, 4, 6, 0, 7, 8, 5 })));
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 2, 3, 0, 4, 6, 7, 8, 5 })));
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 2, 3, 4, 8, 6, 7, 0, 5 })));
	}

	@Test
	public void testSuccessorsWithEmptySpaceAtEdge() {
		List<Integer> puzzleState = Arrays.asList(new Integer[] { 1, 2, 3, 0, 5, 6, 4, 7, 8 });
		SearchProblem<List<Integer>> puzzle = new EightPuzzle(puzzleState);

		List<List<Integer>> successors = puzzle.getSuccessors(puzzleState);

		assertEquals(3, successors.size());
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 2, 3, 5, 0, 6, 4, 7, 8 })));
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 0, 7, 8 })));
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 0, 2, 3, 1, 5, 6, 4, 7, 8 })));
	}

	@Test
	public void testSuccessorsWithEmptySpaceInCenter() {
		List<Integer> puzzleState = Arrays.asList(new Integer[] { 1, 2, 3, 4, 0, 5, 6, 7, 8 });
		SearchProblem<List<Integer>> puzzle = new EightPuzzle(puzzleState);

		List<List<Integer>> successors = puzzle.getSuccessors(puzzleState);

		assertEquals(4, successors.size());
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 2, 3, 0, 4, 5, 6, 7, 8 })));
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 0, 6, 7, 8 })));
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 0, 3, 4, 2, 5, 6, 7, 8 })));
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 2, 3, 4, 7, 5, 6, 0, 8 })));
	}

	@Test
	public void testSuccessorsWithEmptySpaceAdjacentToCorner() {
		List<Integer> puzzleState = Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 0, 7, 8 });
		SearchProblem<List<Integer>> puzzle = new EightPuzzle(puzzleState);

		List<List<Integer>> successors = puzzle.getSuccessors(puzzleState);

		assertEquals(2, successors.size());
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 0, 8 })));
		assertTrue(successors.contains(Arrays.asList(new Integer[] { 1, 2, 3, 0, 5, 6, 4, 7, 8 })));
	}
}