/*
 * Copyright 2021 Marc Liberatore.
 */

package sequencer;

public class Fragment {

	private String nucleotides;

	/**
	 * Creates a new Fragment based upon a String representing a sequence of
	 * nucleotides, containing only the uppercase characters G, C, A and T.
	 * 
	 * @param nucleotides
	 * @throws IllegalArgumentException if invalid characters are in the sequence of
	 *                                  nucleotides
	 */
	public Fragment(String nucleotides) throws IllegalArgumentException {
		for (int i = 0; i < nucleotides.length(); i++) {
			char c = nucleotides.charAt(i);
			if (c != 'G' && c != 'C' && c != 'A' && c != 'T') {
				throw new IllegalArgumentException("Invalid Characters");
			}
		}
		this.nucleotides = nucleotides;

	}

	/**
	 * Returns the length of this fragment.
	 * 
	 * @return the length of this fragment
	 */
	public int length() {
		return nucleotides.length();
	}

	/**
	 * Returns a String representation of this fragment, exactly as was passed to
	 * the constructor.
	 * 
	 * @return a String representation of this fragment
	 */
	@Override
	public String toString() {
		return nucleotides;
	}

	/**
	 * Return true if and only if this fragment contains the same sequence of
	 * nucleotides as another sequence.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (!(o instanceof Fragment)) {
			return false;
		}

		Fragment f = (Fragment) o;

		// Don't unconditionally return false; check that
		// the relevant instances variables in this and f
		// are semantically equal
		if (this.nucleotides.length() != f.nucleotides.length()) {
			return false;
		}

		for (int i = 0; i < this.nucleotides.length(); i++) {
			if (this.nucleotides.charAt(i) != f.nucleotides.charAt(i)) { /// f.nucleotides references object made above
																			/// o=>f
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the number of nucleotides of overlap between the end of this fragment
	 * and the start of another fragment, f.
	 * 
	 * The largest overlap is found, for example, CAA and AAG have an overlap of 2,
	 * not 1.
	 * 
	 * @param f the other fragment
	 * @return the number of nucleotides of overlap
	 */
	public int calculateOverlap(Fragment f) {
		// assumes this.mnucleotides is the shorter string

		int maxLength = Math.min(this.nucleotides.length(), f.nucleotides.length());

		int size = 0;

		for (int i = 1; i <= maxLength; i++) {
			String end = this.nucleotides.substring(this.nucleotides.length() - i);
			String start = f.nucleotides.substring(0, i);

			if (end.equals(start)) {
				size = i;
			}
		}

		return size;
	}

	/**
	 * Returns a new fragment based upon merging this fragment with another fragment
	 * f.
	 * 
	 * This fragment will be on the left, the other fragment will be on the right;
	 * the fragments will be overlapped as much as possible during the merge.
	 * 
	 * @param f the other fragment
	 * @return a new fragment based upon merging this fragment with another fragment
	 */
	public Fragment mergedWith(Fragment f) {

		int overlapSize = this.calculateOverlap(f);

		String newFrag = this.nucleotides + f.nucleotides.substring(overlapSize);
		Fragment ok = new Fragment(newFrag);
		return ok;
	}
}
