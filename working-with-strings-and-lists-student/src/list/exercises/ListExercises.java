/*
 * Copyright 2021 Marc Liberatore.
 */

package list.exercises;

import java.util.ArrayList;
import java.util.List;

public class ListExercises {

	/**
	 * Counts the number of characters in total across all strings in the supplied
	 * list;
	 * in other words, the sum of the lengths of the all the strings.
	 * 
	 * @param l a non-null list of strings
	 * @return the number of characters
	 */
	public static int countCharacters(List<String> l) {

		int count = 0;
		for (String s : l) {
			count += s.length();
		}

		return count;
	}

	/**
	 * Splits a string into words and returns a list of the words.
	 * If the string is empty, split returns a list containing an empty string.
	 * 
	 * @param s a non-null string of zero or more words
	 * @return a list of words
	 */
	public static List<String> split(String s) {
		String[] words = s.split("\\s+");
		return List.of(words);
	}

	/**
	 * Returns a copy of the list of strings where each string has been
	 * uppercased (as by String.toUpperCase).
	 * 
	 * The original list is unchanged.
	 * 
	 * @param l a non-null list of strings
	 * @return a list of uppercased strings
	 */
	public static List<String> uppercased(List<String> l) {

		List<String> list = new ArrayList<>();
		for (String s : l) {

			String newString = s.toUpperCase();
			list.add(newString);

		}
		return list;
	}

	/**
	 * Returns true if and only if each string in the supplied list of strings
	 * starts with an uppercase letter. If the list is empty, returns false.
	 * 
	 * @param l a non-null list of strings
	 * @return true iff each string starts with an uppercase letter
	 */
	public static boolean allCapitalizedWords(List<String> l) {

		boolean allCap = true;
		if (l.size() == 0) {
			return false;
		}
		for (String s : l) {
			if (s.length() == 0) {
				allCap = false;
			} else if (!Character.isUpperCase(s.charAt(0))) {
				allCap = false;
			}

		}

		return allCap;
	}

	/**
	 * Returns a list of strings selected from a supplied list, which contain the
	 * character c.
	 * 
	 * The returned list is in the same order as the original list, but it omits all
	 * strings
	 * that do not contain c.
	 * 
	 * The original list is unmodified.
	 * 
	 * @param l a non-null list of strings
	 * @param c the character to filter on
	 * @return a list of strings containing the character c, selected from l
	 */
	public static List<String> filterContaining(List<String> l, char c) {
		List<String> list = new ArrayList<>();

		for (String s : l) {
			if (s.indexOf(c) >= 0) {
				list.add(s);

			}
		}

		return list;
	}

	/**
	 * Inserts a string into a sorted list of strings, maintaining the sorted
	 * property of the list.
	 * 
	 * @param s the string to insert
	 * @param l a non-null, sorted list of strings
	 */
	public static void insertInOrder(String s, List<String> l) {

		int index = 0;
		if (l.size() == 0) {
			l.add(s);
			return;
		}

		for (String m : l) {
			if (s.compareTo(m) < 0) {
				l.add(index, s);
				return;
			}
			index++;
			if (l.size() == index) {
				l.add(s);
				return;
			}

		}

	}
}
