/*
 * Copyright 2021 Marc Liberatore.
 */

package log;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opencsv.CSVReader;

public class LogParser {
	/**
	 * Returns a list of SuspectEntries corresponding to the CSV data supplied by
	 * the given Reader.
	 * 
	 * The data contains one or more lines of the format:
	 * 
	 * Marc,413-545-3061,1234567890
	 * 
	 * representing a name, phone number, and passport number.
	 * 
	 * @param r an open Reader object
	 * @return a list of SuspectEntries
	 * @throws IOException
	 */
	public static List<SuspectEntry> parseLog(Reader r) throws IOException {
		CSVReader csvReader = new CSVReader(r);

		List<String[]> allList = csvReader.readAll();

		List<SuspectEntry> suspectEntries = new ArrayList<>();
		if (allList.size() == 0) {
			// throw new IOException("Invalid Characters");
			return suspectEntries;
		}

		for (String[] data : allList) {
			String name = data[0];
			String phoneNumber = data[1];
			String passportNumber = data[2];

			SuspectEntry suspectEntry = new SuspectEntry(name, phoneNumber, passportNumber);
			suspectEntries.add(suspectEntry);
		}

		return suspectEntries;
	}

	public static boolean checkIfInEveryList(List<List<String>> listOfLists, String value) {
		for (List<String> list : listOfLists) {
			if (!list.contains(value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns a sorted list of SuspectEntries whose passport numbers are common to
	 * all
	 * of the supplied entryLists.
	 * 
	 * The list is sorted lexicographically by passport number, breaking ties by
	 * name
	 * and then by phone number.
	 * 
	 * @param entryLists a list of lists of SuspectEntries
	 * @return a sorted list of SuspectEntries whose passport numbers are common to
	 *         all
	 *         of the supplied entryLists
	 */

	/// get list of passport numbers from each list
	/// get intersection set from all lists
	// go through all lists and create list of suspects that have a passport number
	/// in the intersection list
	// sort

	public static List<SuspectEntry> findCommonEntries(List<List<SuspectEntry>> entryLists) {
		Set<String> commonPassportNumbers = new HashSet<>();
		/// get list of passport numbers from each list
		List<List<String>> listOfLists = new ArrayList();

		if (entryLists.size() == 0) {
			return new ArrayList<>();
		}

		for (List<SuspectEntry> list : entryLists) {
			List<String> listOfPassports = new ArrayList<>();
			for (SuspectEntry suspect : list) {
				listOfPassports.add(suspect.getPassportNumber());

			}
			listOfLists.add(listOfPassports);
		}

		/// get intersection set from all lists
		for (int i = 0; i < listOfLists.get(0).size(); i++) {
			if (checkIfInEveryList(listOfLists, listOfLists.get(0).get(i))) {
				commonPassportNumbers.add(listOfLists.get(0).get(i));
			}
		}

		// go through all lists and create list of suspects that have a passport number
		/// in the intersection list
		Set<SuspectEntry> suspects = new HashSet<>();
		for (int i = 0; i < entryLists.size(); i++) {
			List<SuspectEntry> entryList = entryLists.get(i);
			for (SuspectEntry entry : entryList) {
				if (commonPassportNumbers.contains(entry.getPassportNumber()) && !suspects.contains(entry)) {

					suspects.add(entry);
				}
			}
		}
		List<SuspectEntry> suspectList = new ArrayList<>(suspects);
		// sort
		Collections.sort(suspectList);
		System.out.println(suspectList.toString());

		return suspectList;
	}

	public static void main(String[] args) {

	}
}
