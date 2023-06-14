/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Assembler {
	private List<Fragment> fragments;

	/**
	 * Creates a new Assembler containing a list of fragments.
	 * 
	 * The list is copied into this assembler so that the original list will not be
	 * modified by the actions of this assembler.
	 * 
	 * @param fragments
	 */
	public Assembler(List<Fragment> fragments) {
		this.fragments = fragments;
	}

	/**
	 * Returns the current list of fragments this assembler contains.
	 * 
	 * @return the current list of fragments
	 */
	public List<Fragment> getFragments() {
		return this.fragments;
	}

	public Fragment chooseFragments(Fragment x, Fragment y) {

		int xLength = x.length();
		int yLength = y.length();

		return xLength >= yLength ? y : x;

	}

	/**
	 * Attempts to perform a single assembly, returning true iff an assembly was
	 * performed.
	 * 
	 * This method chooses the best assembly possible, that is, it merges the two
	 * fragments with the largest overlap, breaking ties between merged fragments by
	 * choosing the shorter merged fragment.
	 * 
	 * Merges must have an overlap of at least 1.
	 * 
	 * After merging two fragments into a new fragment, the new fragment is inserted
	 * into the list of fragments in this assembler, and the two original fragments
	 * are removed from the list.
	 * 
	 * @return true iff an assembly was performed
	 */

	// from assn...you can compare every fragment against every other (in both
	// orders) and find the pair with the largest overlap. What do we mean by both
	// orders? Consider each fragment as both a left fragment against every other on
	// its right, and a right fragment against every other on its left.
	// im assuming this means [a,b,c,d] you can try to overlap a-d

	public boolean assembleOnce() {

		List<Fragment> fragments = this.getFragments();
		if (fragments.size() < 2) {
			return false;
		}
		int indexLeft = 0;
		int indexRight = 1;
		int largestOverlap = 0;

		// compare each element to every element to the right of it
		for (int i = 0; i < fragments.size(); i++) {
			Fragment leftFrag = fragments.get(i);
			for (int j = 0; j < fragments.size(); j++) {
				if (j == i) {
					continue;
				}

				Fragment rightFrag = fragments.get(j);
				int overlap = leftFrag.calculateOverlap(rightFrag);
				if (overlap > 0 && overlap == largestOverlap) {
					Fragment merged1 = fragments.get(indexLeft).mergedWith(fragments.get(indexRight));
					Fragment merged2 = fragments.get(i).mergedWith(fragments.get(j));
					if (merged2.length() < merged1.length()) {
						indexLeft = i;
						indexRight = j;
					}

				} else if (overlap > largestOverlap) {
					indexLeft = i;
					indexRight = j;
					largestOverlap = overlap;
				}
			}
		}
		if (largestOverlap == 0) {
			return false;
		}

		Fragment mergedFragment = fragments.get(indexLeft).mergedWith(fragments.get(indexRight));
		List<Fragment> newFragments = new ArrayList<>();
		for (int i = 0; i < fragments.size(); i++) {
			if (i == indexLeft || i == indexRight) {
				continue;
			} else {
				newFragments.add(fragments.get(i)); // with or without index??
			}

		}
		newFragments.add(mergedFragment); /// why is this showing up as out of bounds?

		this.fragments = newFragments;
		return true;

	}

	/**
	 * Repeatedly assembles fragments until no more assembly can occur.
	 */
	public void assembleAll() {

		while (this.assembleOnce()) {
			this.assembleOnce();
		}

	}

}
