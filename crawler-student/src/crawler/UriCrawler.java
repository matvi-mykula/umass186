/*
 * Copyright 2021 Marc Liberatore.
 */

package crawler;

import java.net.URI;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Document;

import document.RetrievedDocument;

/**
 * A simplified web crawler, specialized to crawl local URIs rather
 * than to retrieve remote documents.
 * 
 * @author liberato
 *
 */
public class UriCrawler {

	Integer visitQuota;
	Set<URI> visitedUris;
	Set<RetrievedDocument> retrievedDocuments;
	ArrayDeque<URI> urisToVisit;

	/**
	 * Instantiates a new UriCrawler. The maximum number of documents a crawler
	 * will attempt to visit, ever, is limited to visitQuota.
	 * 
	 * @param visitQuota
	 *                   the maximum number of documents a crawler will attempt to
	 *                   visit
	 * @throws IllegalArgumentException
	 *                                  if maximumRetrievalAttempts is less than one
	 */
	public UriCrawler(int visitQuota) throws IllegalArgumentException {
		if (visitQuota < 1) {
			throw new IllegalArgumentException();
		}
		this.visitQuota = visitQuota;
		this.visitedUris = new HashSet<URI>();
		this.retrievedDocuments = new HashSet<RetrievedDocument>();
		this.urisToVisit = new ArrayDeque<URI>();

	}

	/**
	 * Returns the set of URIs that this crawler has attempted to visit
	 * (successfully or not).
	 * 
	 * @return the set of URIs that this crawler has attempted to visit
	 */
	public Set<URI> getVistedUris() {
		return new HashSet<>(this.visitedUris);
	}

	/**
	 * Returns the set of RetrievedDocuments corresponding to the URIs
	 * this crawler has successfully visited.
	 * 
	 * @return the set of RetrievedDocuments corresponding to the URIs
	 *         this crawler has successfully visited
	 */
	public Set<RetrievedDocument> getVisitedDocuments() {
		System.out.println(this.retrievedDocuments);
		return this.retrievedDocuments;
	}

	/**
	 * Adds a URI to the collections of URIs that this crawler should attempt to
	 * visit. Does not visit the URI.
	 * 
	 * @param uri
	 *            the URI to be visited (later!)
	 */
	public void addUri(URI uri) {
		this.urisToVisit.add(uri);
	}

	/**
	 * Attempts to visit a single as-yet unattempted URI in this crawler's
	 * collection of to-be-visited URIs.
	 * 
	 * Visiting a document entails parsing the text and links from the URI.
	 * 
	 * If the parse succeeds:
	 * 
	 * - The "file:" links should be added to this crawler's collection of
	 * to-be-visited URIs.
	 * 
	 * - A new RetrievedDocument should be added to this crawler's collection of
	 * successfully visited documents.
	 * 
	 * If the parse fails, this method considers the visit attempted but
	 * unsuccessful.
	 * 
	 * @throws MaximumVisitsExceededException
	 *                                        if this crawler has already attempted
	 *                                        to visit its quota of
	 *                                        visits
	 * @throws NoUnvisitedUrisException
	 *                                        if no more unattempted URI remain in
	 *                                        this crawler's
	 *                                        collection of URIs to visit
	 */
	public void visitOne() throws MaximumVisitsExceededException, NoUnvisitedUrisException {
		// check visit quota
		if (this.getVistedUris().size() >= this.visitQuota) {
			throw new MaximumVisitsExceededException();
		}
		// check if there are uris to visit
		/// this might need to be a set
		if (this.urisToVisit.size() <= 0) {
			throw new NoUnvisitedUrisException();
		}

		// parse and add links to list of uris to visit
		URI nextUri = this.urisToVisit.poll();
		Document newDoc = CrawlerUtils.parse(nextUri);
		if (newDoc != null && !this.getVistedUris().contains(nextUri)) {

			List<URI> newUriList = CrawlerUtils.getFileUriLinks(newDoc);
			this.urisToVisit.addAll(newUriList);

			RetrievedDocument newRetrievedDocument = new RetrievedDocument(nextUri, newDoc.text(), newUriList);
			// add new retrieved doc to list
			if (!this.retrievedDocuments.contains(newRetrievedDocument)) {
				this.retrievedDocuments.add(newRetrievedDocument);
				System.out.println(this.retrievedDocuments);
			}
		}
		// wether parse is succesfful or unssuceful add link to list of visited
		if (!this.visitedUris.contains(nextUri)) {

			this.visitedUris.add(nextUri);
		}

	}

	/**
	 * Attempts to visit all URIs in this crawler (and any URIs they reference,
	 * and so on).
	 * 
	 * This method will not raise a MaximumVisitsExceededException if there are
	 * more URIs than can be visited. It will instead stop once the UriCrawler's
	 * quota has been reached.
	 */
	public void visitAll() {
		try {
			// if visitquota is reached then stop
			if (this.getVistedUris().size() >= this.visitQuota) {
				return;
			}
			if (this.urisToVisit.size() <= 0) {
				return;
			}
			// else visit one then visit all
			this.visitOne();
			visitAll();
		} catch (MaximumVisitsExceededException | NoUnvisitedUrisException e) {
			e.printStackTrace();

			return;
		}
	}
}
