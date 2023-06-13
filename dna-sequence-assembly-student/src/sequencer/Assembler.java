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
	public boolean assembleOnce() {
		List<Fragment> fragments = this.getFragments();

		if (fragments.size() >= 2) {
			System.out.println(fragments);
			int indexer = 0;
			int largestOverlap = 0;

			for (int i = 0; i <= fragments.size() - 1; i++) {
				int overlap;
				if (i == fragments.size() - 1) { /// special case to check if fragment at end and beigning overlap
					overlap = fragments.get(i).calculateOverlap(fragments.get(0));

					if (overlap > largestOverlap) {
						indexer = i;
						largestOverlap = overlap;
					} else if (overlap == largestOverlap && overlap > 0) {
						Fragment merged1 = fragments.get(indexer).mergedWith(fragments.get(indexer + 1));
						Fragment merged2 = fragments.get(i).mergedWith(fragments.get(0));
						if (merged2.length() < merged1.length()) {
							indexer = i;
						}
					}

				} else {
					overlap = fragments.get(i).calculateOverlap(fragments.get(i + 1));

					if (overlap == largestOverlap && overlap > 0) {
						Fragment merged1 = fragments.get(indexer).mergedWith(fragments.get(indexer + 1));
						Fragment merged2 = fragments.get(i).mergedWith(fragments.get(i + 1));
						if (merged2.length() < merged1.length()) {
							indexer = i;
						}
					} else if (overlap > largestOverlap) {
						indexer = i;
						largestOverlap = overlap;
					}
				}
			}
			if (largestOverlap >= 1) {
				Fragment fragment1 = fragments.get(indexer);
				Fragment fragment2;
				fragment2 = (indexer == (fragments.size() - 1)) ? fragments.get(0) : fragments.get(indexer + 1);
				// Fragment fragment2 = fragments.get(indexer + 1);
				Fragment mergedFragment = fragment1.mergedWith(fragment2);
				System.out.println(mergedFragment.toString());
				System.out.println(fragments);
				System.out.println(fragment1.toString());
				System.out.println(fragment2.toString());

				fragments.add(indexer, mergedFragment);
				Iterator<Fragment> iterator = fragments.iterator();
				while (iterator.hasNext()) {
					Fragment fragment = iterator.next();
					if (fragment.equals(fragment1) || fragment.equals(fragment2)) {
						System.out.println(fragment);
						iterator.remove();
					}
				}

				this.fragments = fragments;
				// this.Assembler(fragments);
				System.out.println(this.fragments);
				return true;
			} else {
				return false;
			}

		} else {
			System.out.println("just one");

			return false;
		}
	}

	/**
	 * Repeatedly assembles fragments until no more assembly can occur.
	 */
	public void assembleAll() {
	}

	// public static void main(String[] args) {
	// // Code to be executed
	// // private List<Fragment> two;

	// ArrayList two = new ArrayList<Fragment>();
	// two.add(new Fragment("CATG"));
	// two.add(new Fragment("GCAT"));
	// Assembler a = new Assembler(two);
	// a.assembleOnce();
	// String A = a.toString();

	// System.out.println(A);

	// }
}
