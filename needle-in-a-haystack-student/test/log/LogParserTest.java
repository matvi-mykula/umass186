/*
 * Copyright 2021 Marc Liberatore.
 */

package log;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class LogParserTest {
	// @Rule
	// public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds

	private final SuspectEntry marc = new SuspectEntry("Marc", "413-545-3061", "1234567890");
	private final String marcLine = "Marc,413-545-3061,1234567890";

	private final SuspectEntry garrett = new SuspectEntry("Garrett", "(413) 545-1985", "A72983752");
	private final String garrettLine = "Garrett,(413) 545-1985,A72983752";

	private final SuspectEntry mark = new SuspectEntry("Mark", "413 545 2744", "1234567890");
	private final String markLine = "Mark,413 545 2744,1234567890";

	@Test
	public void testSimpleParse() throws Exception {
		System.out.println(Arrays.asList(marc));
		System.out.println(new StringReader(marcLine));
		assertEquals(Arrays.asList(marc),
				LogParser.parseLog(new StringReader(marcLine)));
	}

	@Test
	public void testParseTwo() throws Exception {
		assertEquals(Arrays.asList(marc, garrett),
				LogParser.parseLog(new StringReader(marcLine + "\n" + garrettLine)));
	}

	@Test
	public void testNoLogs() throws Exception {
		assertEquals(Arrays.asList(),
				LogParser.findCommonEntries(Arrays.asList()));
	}

	@Test
	public void testOneLog() throws Exception {
		assertEquals(Arrays.asList(marc, garrett),
				LogParser.findCommonEntries(
						Arrays.asList(
								LogParser.parseLog(new StringReader(marcLine + "\n" + garrettLine)))));
	}

	@Test
	public void testNoCommonEntries() throws Exception {
		assertEquals(Arrays.asList(),
				LogParser.findCommonEntries(
						Arrays.asList(
								LogParser.parseLog(new StringReader(marcLine)),
								LogParser.parseLog(new StringReader(garrettLine)))));
	}

	@Test
	public void testSameCommonEntries() throws Exception {
		assertEquals(Arrays.asList(marc),
				LogParser.findCommonEntries(
						Arrays.asList(
								LogParser.parseLog(new StringReader(marcLine)),
								LogParser.parseLog(new StringReader(garrettLine + "\n" + marcLine)))));
	}

	@Test
	public void testSameAliasedEntries() throws Exception {
		assertEquals(Arrays.asList(marc, mark),
				LogParser.findCommonEntries(
						Arrays.asList(
								LogParser.parseLog(new StringReader(marcLine)),
								LogParser.parseLog(new StringReader(markLine)))));
	}

	@Test
	public void testSameAliasedEntriesOutOfOrder() throws Exception {
		assertEquals(Arrays.asList(marc, mark),
				LogParser.findCommonEntries(
						Arrays.asList(
								LogParser.parseLog(new StringReader(markLine)),
								LogParser.parseLog(new StringReader(marcLine)))));
	}

	@Test
	public void testFull() throws Exception {
		assertEquals(Arrays.asList(
				new SuspectEntry("C. Sandiego", "(101) 117-0787", "1128946472366"),
				new SuspectEntry("Carmen S. Diego", "+1 222 345 6789", "1128946472366"),
				new SuspectEntry("Carmen Sandiego", "(888)007-0787x267", "1128946472366"),
				new SuspectEntry("Colonel Sanders", "1-800-CALL-KFC", "1128946472366"),
				new SuspectEntry("La Mulana", "762-812-4698", "1128946472366")),
				LogParser.findCommonEntries(
						Arrays.asList(
								LogParser.parseLog(
										new FileReader("test" + File.separator + "log" + File.separator + "data0.csv")),
								LogParser.parseLog(
										new FileReader("test" + File.separator + "log" + File.separator + "data1.csv")),
								LogParser.parseLog(
										new FileReader("test" + File.separator + "log" + File.separator + "data2.csv")),
								LogParser.parseLog(
										new FileReader("test" + File.separator + "log" + File.separator + "data3.csv")),
								LogParser.parseLog(new FileReader(
										"test" + File.separator + "log" + File.separator + "data4.csv")))));
	}
}
