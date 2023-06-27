/*
 * Copyright 2021 Marc Liberatore.
 */
package harp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import queue.CircularDoublesQueue;

public class HarpStringTest {
	@Test
	public void testPluck() {

		double[] values = { 1.0, 2.0, 3.0, 4.0, 5.0 };
		HarpString s = new HarpString(values);
		// Call the pluck() method to replace the items with random numbers
		s.pluck();

		// Verify that each item in the queue is replaced with a random number between
		// -0.5 and 0.5
		for (int i = 0; i < s.queue.size(); i++) {
			double item = s.queue.getItemByIndex(i);
			System.out.println(s.queue.getItemByIndex(i));
			assertTrue(item >= -0.5 && item <= 0.5);
		}
	}

	@Test
	public void testPluckLengths() {

		double[] values = { 1.0, 2.0, 3.0, 4.0, 5.0 };
		HarpString s = new HarpString(values);
		// Call the pluck() method to replace the items with random numbers
		s.pluck();
		assertEquals(values.length, s.queue.size());
	}

	@Test
	public void testNewString1() {
		HarpString s = new HarpString(440);
		assertEquals(0, s.time());
		s.sample();
		s.pluck();
	}

	@Test
	public void testNewString2() {
		double[] values = { 0.5, 0.4, 0.3, 0.2, 0.1 };
		HarpString s = new HarpString(values);
		assertEquals(0, s.time());
		assertEquals(0.5, s.sample(), 0);
	}

	@Test
	public void testStringTic() {
		double[] values = { 0.5, 0.4, 0.3, 0.2, 0.1 };
		HarpString s = new HarpString(values);
		assertEquals(0, s.time());
		assertEquals(0.5, s.sample(), 0);
		s.tic();
		assertEquals(1, s.time());
		assertEquals(0.4, s.sample(), 0);
	}

	@Test
	public void testTicExample() {
		// Initialize a KarplusString with a capacity of 10

		// Set up the initial contents of the queue
		double[] initialQueue = { 0.2, 0.4, 0.5, 0.3, -0.2, 0.4, 0.3, 0.0, -0.1, -0.3 };
		HarpString s = new HarpString(initialQueue);

		// Run the tic() method
		s.tic();
		System.out.println(s.queue);
		// Check the resulting queue after running tic()
		double[] expectedQueue = { 0.4, 0.5, 0.3, -0.2, 0.4, 0.3, 0.0, -0.1, -0.3, .2982 };

		for (int i = 0; i < expectedQueue.length; i++) {
			double actualItem = s.queue.getItemByIndex(i);
			assertEquals(expectedQueue[i], actualItem, 1e-6); // Allow for a small precision error
		}

		// Check the ticCount
		assertEquals(1, s.ticCount);
	}

	@Test
	public void testTic() {
		// Initialize a KarplusString with a capacity of 10

		// Set up the initial contents of the queue
		double[] initialQueue = { 0.5, 0.2, -0.3, 0.1, -0.4, 0.6, -0.2, 0.7, -0.5, 0.3 };
		HarpString s = new HarpString(initialQueue);

		// Run the tic() method
		s.tic();
		System.out.println(s.queue);
		// Check the resulting queue after running tic()
		double[] expectedQueue = { 0.2, -0.3, 0.1, -0.4, 0.6, -0.2, 0.7, -0.5, 0.3, .3479 };

		for (int i = 0; i < expectedQueue.length; i++) {
			double actualItem = s.queue.getItemByIndex(i);
			assertEquals(expectedQueue[i], actualItem, 1e-6); // Allow for a small precision error
		}

		// Check the ticCount
		assertEquals(1, s.ticCount);
	}

	@Test
	public void testStringManyTics() {
		double[] values = { 0.5, -0.4, 0.3, -0.2, 0.1 };
		double[] expected = { 0.5, -0.4, 0.3, -0.2, 0.1, 0.04969999999999999, -0.049700000000000015,
				0.04969999999999999, 0.0497, -0.02499910000000001, -1.379452108096757E-17, -1.379452108096757E-17,
				-6.897260540483785E-18, -0.012276347299999995, 0.012424552700000011, 1.3711753954481765E-17,
				1.0283815465861325E-17, 0.006101344608100001, -7.365808380000793E-5, -0.006175002691900013,
				-1.1925798001910514E-17, -0.0030323682702257057, -0.0029957602025770967, 0.00310558440552291,
				0.0030689763378743125, 0.0015070870303021816, 0.002995979850982993, -5.4582628864069305E-5,
				-0.00306875668946842, -0.0022743034939837177, -0.002238024239998732, -0.001461874419393105,
				0.0015522996412112473, 0.0026555009111757124, 0.002242626883789277, 0.001838849633717743,
				-4.4941335243616717E-5, -0.002091276874536319, -0.0024343695140976, -0.0020284938292009893,
				-8.915724243416408E-4, 0.0010617004502606283, 0.002249246255151058, 0.002218043081619399,
				0.0014512729280106872, -8.45536288817368E-5, -0.0016455405125896082, -0.0022202428003749173,
				-0.0018236500567861528, -6.792594916670883E-4, };
		HarpString s = new HarpString(values);
		for (int i = 0; i < 20; i++) {
			assertEquals(i, s.time());
			s.tic();
		}
	}

}
