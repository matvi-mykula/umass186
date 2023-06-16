/*
 * Copyright 2021 Marc Liberatore.
 */

package log;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class SuspectEntryTest {
	// @Rule
	// public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds

	private SuspectEntry marc1;
	private SuspectEntry marc2;
	private SuspectEntry garrett;

	@Before
	public void setup() {
		marc1 = new SuspectEntry(new String("Marc"), new String("413-545-3061"), new String("1234567890"));
		marc2 = new SuspectEntry(new String("Marc"), new String("413-545-3061"), new String("1234567890"));
		garrett = new SuspectEntry(new String("Garrett"), new String("(413) 545-1985"), new String("A72983752"));

		System.out.println(marc1.equals(marc2));
	}

	@Test
	public void testNotEquals() throws Exception {
		assertFalse(marc1.equals(garrett));
		assertFalse(garrett.equals(marc1));
	}

	@Test
	public void testEquals() throws Exception {
		assertTrue(marc1.equals(marc2));
		assertTrue(marc2.equals(marc1));
	}
}
