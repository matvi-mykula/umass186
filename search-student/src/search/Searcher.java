/*
 * Copyright 2021 Marc Liberatore.
 */

package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * An implementation of a Searcher that performs an iterative search,
 * storing the list of next states in a Queue. This results in a
 * breadth-first search.
 * 
 * @author liberato
 *
 * @param <T> the type for each vertex in the search graph
 */
public class Searcher<T> {
	private final SearchProblem<T> searchProblem;

	/**
	 * Instantiates a searcher.
	 * 
	 * @param searchProblem
	 *                      the search problem for which this searcher will find and
	 *                      validate solutions
	 */
	public Searcher(SearchProblem<T> searchProblem) {
		this.searchProblem = searchProblem;
	}

	/**
	 * Finds and return a solution to the problem, consisting of a list of
	 * states.
	 * 
	 * The list should start with the initial state of the underlying problem.
	 * Then, it should have one or more additional states. Each state should be
	 * a successor of its predecessor. The last state should be a goal state of
	 * the underlying problem.
	 * 
	 * If there is no solution, then this method should return an empty list.
	 * 
	 * @return a solution to the problem (or an empty list)
	 */
	public List<T> findSolution() {
		// TODO
		// Create a queue to keep track of states to explore
		Queue<T> queue = new LinkedList<>();

		// Create a set to keep track of visited states
		Set<T> visited = new HashSet<>();

		// Get the initial state from the search problem
		T initialState = searchProblem.getInitialState();

		// Enqueue the initial state
		queue.offer(initialState);

		// Start the search loop
		while (!queue.isEmpty()) {
			T currentState = queue.poll();

			// Check if the current state is a goal state
			if (searchProblem.isGoal(currentState)) {
				// Construct and return the solution path
				return constructSolutionPath(currentState);
			}

			// Mark the current state as visited
			visited.add(currentState);

			// Get the successor states of the current state
			List<T> successors = searchProblem.getSuccessors(currentState);

			// Enqueue unvisited successor states
			for (T successor : successors) {
				if (!visited.contains(successor)) {
					queue.offer(successor);
				}
			}
		}

		// No solution found, return an empty list
		return new ArrayList<>();
	}

	/**
	 * Checks that a solution is valid.
	 * 
	 * A valid solution consists of a list of states. The list should start with
	 * the initial state of the underlying problem. Then, it should have one or
	 * more additional states. Each state should be a successor of its
	 * predecessor. The last state should be a goal state of the underlying
	 * problem.
	 * 
	 * @param solution
	 * @return true iff this solution is a valid solution
	 * @throws NullPointerException
	 *                              if solution is null
	 */
	public final boolean isValidSolution(List<T> solution) {
		if (solution.isEmpty()) {
			return false;
		}
		// Check if the first state in the solution is the starting state
		// compare what is passed in the the searchProblem value
		T firstState = solution.get(0);
		if (!searchProblem.getInitialState().equals(firstState)) {
			return false;
		}
		T lastState = solution.get(solution.size() - 1);
		if (!searchProblem.isGoal(lastState)) {
			return false;
		}
		for (int i = 1; i < solution.size(); i++) {
			// check if the current state is a valid successor of theprevious state
			// checks if the previeous solution state successors contains current solution
			// state
			if (!searchProblem.getSuccessors(solution.get(i - 1)).contains(solution.get(i))) {
				return false;
			}

		}
		// TODO
		return true;
	}

	private List<T> constructSolutionPath(T currentState) {
		List<T> solutionPath = new ArrayList<>();
		solutionPath.add(currentState);

		// Trace back the path from the goal state to the initial state
		while (currentState != searchProblem.getInitialState()) {
			currentState = searchProblem.getPredecessorState(currentState);
			solutionPath.add(currentState);
		}

		// Reverse the path to get the correct order
		Collections.reverse(solutionPath);

		return solutionPath;
	}
}
