/*
 * Copyright 2021 Marc Liberatore.
 */

package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import search.SearchProblem;
import search.Searcher;

/**
 * A class to represent an instance of the eight-puzzle.
 * 
 * The spaces in an 8-puzzle are indexed as follows:
 * 
 * 0 | 1 | 2
 * --+---+---
 * 3 | 4 | 5
 * --+---+---
 * 6 | 7 | 8
 * 
 * 
 * from the 4
 * -3
 * +1
 * -1
 * +3
 * 
 * The puzzle contains the eight numbers 1-8, and an empty space.
 * If we represent the empty space as 0, then the puzzle is solved
 * when the values in the puzzle are as follows:
 * 
 * 1 | 2 | 3
 * --+---+---
 * 4 | 5 | 6
 * --+---+---
 * 7 | 8 | 0
 * 
 * That is, when the space at index 0 contains value 1, the space
 * at index 1 contains value 2, and so on.
 * 
 * From any given state, you can swap the empty space with a space
 * adjacent to it (that is, above, below, left, or right of it,
 * without wrapping around).
 * 
 * For example, if the empty space is at index 2, you may swap
 * it with the value at index 1 or 5, but not any other index.
 * 
 * Only half of all possible puzzle states are solvable! See:
 * https://en.wikipedia.org/wiki/15_puzzle
 * for details.
 * 
 * 
 * @author liberato
 *
 */
public class EightPuzzle implements SearchProblem<List<Integer>> {

	List<Integer> startingValues;

	/**
	 * Creates a new instance of the 8 puzzle with the given starting values.
	 * 
	 * The values are indexed as described above, and should contain exactly the
	 * nine integers from 0 to 8.
	 * 
	 * @param startingValues
	 *                       the starting values, 0 -- 8
	 * @throws IllegalArgumentException
	 *                                  if startingValues is invalid
	 */
	public EightPuzzle(List<Integer> startingValues) {

		this.startingValues = startingValues;

	}

	@Override
	public List<Integer> getInitialState() {

		return this.startingValues;

	}

	public boolean isIndexValid(List<Integer> list, int index1, int index2) {
		// chceck if index is out of bounds for array
		if (index1 < 0 || index1 >= list.size() || index2 < 0 || index2 >= list.size()) {
			return false;
		}
		// cehck if index 2 goes out of bounds of the row its on
		if (index1 % 3 == 2 && index2 % 3 == 0) {
			return false;
		}
		if (index2 % 3 == 2 && index1 % 3 == 0) {
			return false;
		}

		return true;
	}

	public List<Integer> swapValuesAtIndex(List<Integer> list, int index1, int index2) {
		List<Integer> newState = new ArrayList<>(list);

		Integer value1 = list.get(index1);
		Integer value2 = list.get(index2);

		newState.set(index1, value2);
		newState.set(index2, value1);
		return newState;

	}

	@Override
	public List<List<Integer>> getSuccessors(List<Integer> currentState) {

		// intiialize list of possible moves
		List<List<Integer>> possibleMoves = new ArrayList<>();

		int zeroIndex = 0;
		// find where the zero is
		for (int i = 0; i < currentState.size(); i++) {
			if (currentState.get(i) == 0) {
				zeroIndex = i;
			}

		}
		// make list of possible moves following the rules
		// move swap with north tile
		if (isIndexValid(currentState, zeroIndex, zeroIndex + 3)) {
			possibleMoves.add(swapValuesAtIndex(currentState, zeroIndex, zeroIndex + 3));
		}
		// move swap with south tile
		if (isIndexValid(currentState, zeroIndex, zeroIndex - 3)) {
			possibleMoves.add(swapValuesAtIndex(currentState, zeroIndex, zeroIndex - 3));
		}
		// move swap with east tile
		if (isIndexValid(currentState, zeroIndex, zeroIndex + 1)) {
			possibleMoves.add(swapValuesAtIndex(currentState, zeroIndex, zeroIndex + 1));
		}
		// move swap with west tile
		if (isIndexValid(currentState, zeroIndex, zeroIndex - 1)) {
			possibleMoves.add(swapValuesAtIndex(currentState, zeroIndex, zeroIndex - 1));
		}

		// turn to set to remove duplicates
		// prop unneccessary
		Set<List<Integer>> setWithoutDuplicates = new HashSet<>(possibleMoves);

		// return as list
		List<List<Integer>> listWithoutDuplicates = new ArrayList<>(setWithoutDuplicates);

		return listWithoutDuplicates;
	}

	@Override
	public boolean isGoal(List<Integer> state) {

		for (int i = 0; i < state.size() - 1; i++) {
			if (state.get(i) != i + 1) {
				return false;
			}
		}
		if (state.get(8) != 0) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		EightPuzzle eightPuzzle = new EightPuzzle(Arrays.asList(new Integer[] { 1, 2, 3, 4, 0, 6, 7, 5, 8 }));

		List<List<Integer>> solution = new Searcher<List<Integer>>(eightPuzzle).findSolution();
		for (List<Integer> state : solution) {
			System.out.println(state);
		}
		System.out.println(solution.size() + " states in solution");
	}
}
