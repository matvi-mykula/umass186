/*
 * Copyright 2021 Marc Liberatore.
 */

package index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import comparators.TfIdfComparator;
import documents.DocumentId;

/**
 * A simplified document indexer and search engine.
 * 
 * Documents are added to the engine one-by-one, and uniquely identified by a
 * DocumentId.
 *
 * Documents are internally represented as "terms", which are lowercased
 * versions of each word
 * in the document.
 * 
 * Queries for terms are also made on the lowercased version of the term. Terms
 * are
 * therefore case-insensitive.
 * 
 * Lookups for documents can be done by term, and the most relevant document(s)
 * to a specific term
 * (as computed by tf-idf) can also be retrieved.
 *
 * See:
 * - <https://en.wikipedia.org/wiki/Inverted_index>
 * - <https://en.wikipedia.org/wiki/Search_engine_(computing)>
 * - <https://en.wikipedia.org/wiki/Tf%E2%80%93idf>
 * 
 * @author Marc Liberatore
 *
 */
public class SearchEngine {

	Map<String, Set<DocumentId>> termsToDocIds = new HashMap<String, Set<DocumentId>>();
	Map<DocumentId, Map<String, Integer>> stringCountsByDoc = new HashMap<DocumentId, Map<String, Integer>>();

	/**
	 * Inserts a document into the search engine for later analysis and retrieval.
	 * 
	 * The document is uniquely identified by a documentId; attempts to re-insert
	 * the same
	 * document are ignored.
	 * 
	 * The document is supplied as a Reader; this method stores the document
	 * contents for
	 * later analysis and retrieval.
	 * 
	 * @param documentId
	 * @param reader
	 * @throws IOException iff the reader throws an exception
	 */
	public void addDocument(DocumentId documentId, Reader reader) throws IOException {
		BufferedReader br = new BufferedReader(reader);
		Set<DocumentId> documentIds = new HashSet<>();
		Map<String, Integer> termCountMap = new HashMap<>(); /// counts occurences of each term in document

		for (String line = br.readLine(); line != null; line = br.readLine()) {
			String[] terms = line.split("\\W+");

			for (String term : terms) {
				term = term.toLowerCase();
				if (!termsToDocIds.containsKey(term)) {
					termsToDocIds.put(term, new HashSet<>());
				}
				termsToDocIds.get(term).add(documentId);
				if (termCountMap.containsKey(term)) {
					termCountMap.put(term, termCountMap.get(term) + 1);
				} else {
					termCountMap.put(term, 1);
				}
				// termCountMap.put(term, termsToDocIds.get(term).size());

				stringCountsByDoc.put(documentId, termCountMap);
			}
		}
	}

	/**
	 * Returns the set of DocumentIds contained within the search engine that
	 * contain a given term.
	 * 
	 * @param term
	 * @return the set of DocumentIds that contain a given term
	 */
	public Set<DocumentId> indexLookup(String term) {
		return termsToDocIds.getOrDefault(term.toLowerCase(), new HashSet<>());
	}

	/**
	 * Returns the term frequency of a term in a particular document.
	 * 
	 * The term frequency is number of times the term appears in a document.
	 * 
	 * See
	 * 
	 * @param documentId
	 * @param term
	 * @return the term frequency of a term in a particular document
	 * @throws IllegalArgumentException if the documentId has not been added to the
	 *                                  engine
	 */

	public int termFrequency(DocumentId documentId, String term) throws IllegalArgumentException {
		if (documentId == null) {
			throw new IllegalArgumentException("Invalid documentId");
		}
		// Check if the documentId has been added to the engine
		if (!stringCountsByDoc.containsKey(documentId)) {
			throw new IllegalArgumentException("Document not found");
		}

		if (stringCountsByDoc.containsKey(documentId)) {

			Map<String, Integer> termCounts = stringCountsByDoc.get(documentId);
			if (termCounts.containsKey(term)) {
				return termCounts.get(term);
			}
		}

		return 0;
	}

	/**
	 * Returns the inverse document frequency of a term across all documents in the
	 * index.
	 * 
	 * For our purposes, IDF is defined as log ((1 + N) / (1 + M)) where
	 * N is the number of documents in total, and M
	 * is the number of documents where the term appears.
	 * 
	 * @param term
	 * @return the inverse document frequency of term
	 */
	public double inverseDocumentFrequency(String term) {

		// N is total number of documents
		double N = stringCountsByDoc.size();

		// m is number of documents that have that term in it
		/// initialize m at 0
		double M = 0;
		// get list of documents
		for (Map.Entry<DocumentId, Map<String, Integer>> entry : stringCountsByDoc.entrySet()) {
			DocumentId documentId = entry.getKey();
			Map<String, Integer> termCounts = entry.getValue();
			// if (stringCountsByDoc.get(documentId).containsValue(term))

			System.out.println("Document ID: " + documentId);

			// go through each term in each document
			for (Map.Entry<String, Integer> termEntry : termCounts.entrySet()) {
				String termKey = termEntry.getKey();
				int count = termEntry.getValue();
				// if term shows up count it
				if (termKey.equals(term) && count > 0) {
					M += 1;
				}

				System.out.println("Term: " + term + ", Count: " + count);
			}
		}

		double IDF = Math.log((1 + N) / (1 + M));
		return IDF;
	}

	/**
	 * Returns the tfidf score of a particular term for a particular document.
	 * 
	 * tfidf is the product of term frequency and inverse document frequency for the
	 * given term and document.
	 * 
	 * @param documentId
	 * @param term
	 * @return the tfidf of the the term/document
	 * @throws IllegalArgumentException if the documentId has not been added to the
	 *                                  engine
	 */
	public double tfIdf(DocumentId documentId, String term) throws IllegalArgumentException {

		if (!stringCountsByDoc.keySet().contains(documentId)) {
			throw new IllegalArgumentException("No Document");
		}
		// get term frequency
		double tf = termFrequency(documentId, term);
		// get inverse document frequency for term and document
		double idf = inverseDocumentFrequency(term);

		double product = tf * idf;

		return product;
	}

	/**
	 * Returns a sorted list of documents, most relevant to least relevant, for the
	 * given term.
	 * 
	 * A document with a larger tfidf score is more relevant than a document with a
	 * lower tfidf score.
	 * 
	 * Each document in the returned list must contain the term.
	 * 
	 * @param term
	 * @return a list of documents sorted in descending order by tfidf
	 */
	public List<DocumentId> relevanceLookup(String term) {
		// get documents with the term
		List<DocumentId> docsWithTerm = new ArrayList<>();

		for (Map.Entry<DocumentId, Map<String, Integer>> entry : stringCountsByDoc.entrySet()) {
			DocumentId documentId = entry.getKey();
			Map<String, Integer> termCounts = entry.getValue();

			// go through each term in each document
			for (Map.Entry<String, Integer> termEntry : termCounts.entrySet()) {
				String termKey = termEntry.getKey();
				int count = termEntry.getValue();
				// if term shows up count it
				if (termKey.equals(term) && count > 0) {
					docsWithTerm.add(documentId);
					continue;
				}

			}

		}
		// sort into list with higher scores first
		// create map {document:tfidf score}
		Map<DocumentId, Double> docToTfidfMap = new HashMap<>(); /// counts occurences of each term in document
		for (DocumentId documentId : docsWithTerm) {
			docToTfidfMap.put(documentId, tfIdf(documentId, term));
		}
		// make sorted list of map entries
		List<Map.Entry<DocumentId, Double>> entryList = new ArrayList<>(docToTfidfMap.entrySet());
		entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
		/// get keys into list in order
		List<DocumentId> orderedKeys = new ArrayList<>();
		for (Map.Entry<DocumentId, Double> entry : entryList) {
			orderedKeys.add(entry.getKey());
		}

		return orderedKeys;

	}
}
