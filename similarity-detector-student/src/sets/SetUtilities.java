/*
 * Copyright 2021 Marc Liberatore.
 */

package sets;

import java.util.LinkedHashSet;
import java.util.Set;

public class SetUtilities {
	/**
	 * Returns a new set representing the union of s and t.
	 * 
	 * Does not modify s or t.
	 * 
	 * @param s
	 * @param t
	 * @return a new set representing the union of s and t
	 */
	public static <E> Set<E> union(Set<E> s, Set<E> t) {
		Set<E> unionSet = new LinkedHashSet<>(s);

		unionSet.addAll(t);
		return unionSet;
	}

	/**
	 * Returns a new set representing the intersection of s and t.
	 * 
	 * Does not modify s or t.
	 * 
	 * @param s
	 * @param t
	 * @return a new set representing the intersection of s and t
	 */
	public static <E> Set<E> intersection(Set<E> s, Set<E> t) {
		Set<E> intersectionSet = new LinkedHashSet<>();

		for (E e : t) {
			if (s.contains(e)) {
				intersectionSet.add(e);
			}
		}

		return intersectionSet;
	}

	/**
	 * Returns a new set representing the set difference s and t,
	 * that is, s \ t.
	 * 
	 * Does not modify s or t.
	 * 
	 * @param s
	 * @param t
	 * @return a new set representing the difference of s and t
	 */
	public static <E> Set<E> setDifference(Set<E> s, Set<E> t) {
		Set<E> differenceSet = new LinkedHashSet<>();
		for (E e : s) {
			if (!t.contains(e)) {
				differenceSet.add(e);
			}
		}
		// for (E e : t) {
		// if (!s.contains(e)) {
		// differenceSet.add(e);
		// }
		// }
		return differenceSet;
	}

	/**
	 * Returns the Jaccard index of the two sets s and t.
	 * 
	 * It is defined as 1 if both sets are empty.
	 *
	 * Otherwise, it is defined as the size of the intersection of the sets,
	 * divided by the size of the union of the sets.
	 * 
	 * Does not modify s or t.
	 * 
	 * @param s
	 * @param t
	 * @return the Jaccard index of s and t
	 */
	public static <E> double jaccardIndex(Set<E> s, Set<E> t) {
		if (s.size() == 0 && t.size() == 0) {
			return 1;
		}
		Set<E> intersectionSet = intersection(s, t);
		Set<E> unionSet = union(s, t);

		double unionSize = unionSet.size();
		double interSize = intersectionSet.size();

		double jaccardIndex = interSize / unionSize;

		return jaccardIndex;
	}
}
