/*
 * Copyright 2021 Marc Liberatore.
 */

package search;

import java.time.chrono.ThaiBuddhistEra;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Map;

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
		System.out.println(this.searchProblem.toString());
		System.out.println(this.searchProblem.getInitialState());
		System.out.println(this.searchProblem.getSuccessors(this.searchProblem.getInitialState()));

		// initialize queue strucutre
		Queue<T> queue = new ArrayDeque<>();
		// add start to queue
		queue.add(this.searchProblem.getInitialState());
		// initialize visited array of booleans false size of number of nodes
		Map<T, Boolean> visited = new HashMap<>();
		// visited[start] = true
		visited.put(this.searchProblem.getInitialState(), true);
		// initialize map object to track neighbors of node[i]
		// return value is going to come from finding shortest list from start to ennd
		Map<T, List<T>> neighborMap = new HashMap<>();

		// initialize parent map
		// key is node, value is parent
		Map<T, T> parentMap = new HashMap<>();

		/// keep track if find the goal
		// intitialize to start for typing reasons
		// this is kinda fucky
		T goal = this.searchProblem.getInitialState();

		// while queue is not empty
		while (queue.size() > 0) {
			// get current element and remove from queue
			T currentElement = queue.poll();
			// queue.remove(0);
			// mark visited
			visited.put(currentElement, true);
			// check if goal node
			if (this.searchProblem.isGoal(currentElement)) {
				goal = currentElement;
				break;
			}
			// get neighbors
			List<T> neighbors = this.searchProblem.getSuccessors(currentElement);
			// add to neighbor map
			neighborMap.put(currentElement, neighbors);
			// for each neighbor, if it hasnt been visited add to queue, add parent child
			// relationship
			for (T t : this.searchProblem.getSuccessors(currentElement)) {
				if (visited.get(t) == null) {
					queue.add(t);
					parentMap.put(t, currentElement);
				}

			}

			// return new ArrayList<>();
		}

		/// reconstruct path by working backward from goal and getting parent
		List<T> shortestPath = new ArrayList<>();
		T currentNode = goal;
		while (!currentNode.equals(this.searchProblem.getInitialState())) {
			shortestPath.add(currentNode);
			currentNode = parentMap.get(currentNode);
		}
		// add start to path then reverse
		shortestPath.add(this.searchProblem.getInitialState());

		// keep track of parent node in prev aray
		// return prev array

		// reconstruct path from start to end
		// reverse path
		// if they arent connect return empty list
		if (shortestPath.size() > 0) {
			Collections.reverse(shortestPath);
			return shortestPath;
		} else {
			return new ArrayList<>();
		}
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
}
// /// i dont really know whats going on here
// private List<T> constructSolutionPath(T currentState) {
// List<T> solutionPath = new ArrayList<>();
// solutionPath.add(currentState);

// // Trace back the path from the goal state to the initial state
// while (currentState != searchProblem.getInitialState()) {
// currentState = searchProblem.getPredecessorState(currentState);
// solutionPath.add(currentState);
// }

// // Reverse the path to get the correct order
// Collections.reverse(solutionPath);

// return solutionPath;
// }
// }
