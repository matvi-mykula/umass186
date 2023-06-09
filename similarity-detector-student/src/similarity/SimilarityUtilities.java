/*
 * Copyright 2021 Marc Liberatore.
 */

package similarity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import sets.SetUtilities;

public class SimilarityUtilities {
	/**
	 * Returns the set of non-empty lines contained in a text, trimmed of
	 * leading and trailing whitespace.
	 * 
	 * @param text
	 * @return the trimmed set of lines
	 */
	public static Set<String> trimmedLines(String text) {
		String[] s = text.split("\\r?\\n");
		Set<String> trimmedSet = new LinkedHashSet<>();
		for (String word : s) {
			String trimmedWord = word.trim();
			if (!trimmedWord.isEmpty()) { // if not an empty string
				trimmedSet.add(trimmedWord);
			}
		}
		return trimmedSet;
	}

	/**
	 * Returns a list of words in the text, in the order they appeared in the text,
	 * converted to lowercase.
	 * 
	 * Words are defined as a contiguous, non-empty sequence of letters and numbers.
	 *
	 * @param text
	 * @return a list of lowercase words
	 */
	public static List<String> asLowercaseWords(String text) {
		if (text.length() == 0) {
			return new ArrayList<String>();
		}

		List<String> s = new ArrayList<String>();

		String[] splitText = text.split("[^a-zA-Z0-9]+");
		for (String string : splitText) {
			String trimmedWord = string.trim();
			if (!trimmedWord.isEmpty()) { // if not an empty string
				s.add(trimmedWord.toLowerCase());
			}
		}
		return s;
	}

	/**
	 * Returns the line-based similarity of two texts.
	 * 
	 * The line-based similarity is the Jaccard index between each text's line
	 * set.
	 * 
	 * A text's line set is the set of trimmed lines in that text, as defined by
	 * trimmedLines.
	 * 
	 * @param text1
	 *              a text
	 * @param text2
	 *              another text
	 * @return
	 */
	public static double lineSimilarity(String text1, String text2) {
		// make a set out of each trimmed lines
		// get union && interesection of sets
		Set<String> Set1 = trimmedLines(text1);
		Set<String> Set2 = trimmedLines(text2);

		double jaccard = SetUtilities.jaccardIndex(Set1, Set2);

		return jaccard;
	}

	/**
	 * Returns the line-based similarity of two texts.
	 * 
	 * The line-based similarity is the Jaccard index between each text's line
	 * set.
	 * 
	 * A text's line set is the set of trimmed lines in that text, as defined by
	 * trimmedLines, less the set of trimmed lines from the templateText. Removes
	 * the template text from consideration after trimming lines, not before.
	 * 
	 * @param text1
	 *                     a text
	 * @param text2
	 *                     another text
	 * @param templateText
	 *                     a template, representing things the two texts have in
	 *                     common
	 * @return
	 */
	public static double lineSimilarity(String text1, String text2, String templateText) {
		// make set for each text
		Set<String> Set1 = trimmedLines(text1);
		Set<String> Set2 = trimmedLines(text2);
		Set<String> templateSet = trimmedLines(templateText);
		// remove all template stuff
		Set1.removeAll(templateSet);
		Set2.removeAll(templateSet);

		// jaccard
		double jaccard = SetUtilities.jaccardIndex(Set1, Set2);

		return jaccard;
	}

	/**
	 * Returns a set of strings representing the shingling of the given length
	 * of a list of words.
	 * 
	 * A shingling of length k of a list of words is the set of all k-shingles
	 * of that list.
	 * 
	 * A k-shingle is the concatenation of k adjacent words.
	 * 
	 * For example, a 3-shingle of the list: ["a" "very" "fine" "young" "man"
	 * "I" "know"] is the set: {"averyfine" "veryfineyoung" "fineyoungman"
	 * "youngmanI" "manIknow"}.
	 * 
	 * @param words
	 * @param shingleLength
	 * @return
	 */
	public static Set<String> shingle(List<String> words, int shingleLength) {

		// instantiate shingle set of strings
		Set<String> shingleSet = new LinkedHashSet<>();

		// for each word in words until end with room for last shingle
		for (int i = 0; i < words.size() - (shingleLength - 1); i++) {
			String tempString = "";
			for (int j = i; j < i + shingleLength; j++) {
				tempString += words.get(j);
			}
			shingleSet.add(tempString);
		}
		// create a string by adding shinglelength words
		// add that to set

		return shingleSet;
	}

	/**
	 * Returns the shingled word similarity of two texts.
	 * 
	 * The shingled word similarity is the Jaccard index between each text's
	 * shingle set.
	 * 
	 * A text's shingle set is the set of shingles (of the given length) for the
	 * entire text, as defined by shingle and asLowercaseWords,
	 * less the shingle set of the templateText. Removes the templateText
	 * from consideration after shingling, not before.
	 * 
	 * @param text1
	 * @param text2
	 * @param templateText
	 * @param shingleLength
	 * @return
	 */
	public static double shingleSimilarity(String text1, String text2, String templateText, int shingleLength) {
		// trim and lowwercase texts
		Set<String> Set1 = trimmedLines(text1);
		List<String> List1 = new ArrayList<>();
		Set<String> Set2 = trimmedLines(text2);
		List<String> List2 = new ArrayList<>();
		Set<String> templateSet = trimmedLines(templateText);
		List<String> templateList = new ArrayList<>();

		for (String string : Set1) {
			List1.addAll(asLowercaseWords(string));

		}
		for (String string : Set2) {
			List2.addAll(asLowercaseWords(string));
		}
		for (String string : templateSet) {
			templateList.addAll(asLowercaseWords(string));
		}
		// shingle them
		Set<String> shingle1 = shingle(List1, shingleLength);
		Set<String> shingle2 = shingle(List2, shingleLength);
		Set<String> templateShingle = shingle(templateList, shingleLength);

		// remove templates
		shingle1.removeAll(templateShingle);
		shingle2.removeAll(templateShingle);
		// get union

		double jaccard = SetUtilities.jaccardIndex(shingle1, shingle2);

		return jaccard;
	}

	public static void main(String[] args) {
		String good = "Complete the following sentence:\nCOMPSCI 290NW is a perfectly cromulent class.\n";
		String bad = "Complete the following sentence:\nCOMPSCI 290NW is a hot mess.\n";
		shingleSimilarity(bad, good, "", 0);
	}
}
