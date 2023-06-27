/*
 * Copyright 2021 Marc Liberatore.
 */
package harp;

import java.util.Random;

import queue.CircularDoublesQueue;

/**
 * A simulated harp string, built using the Karplus-Strong algorithm. Sounds
 * like
 * a synthesizer from the 80s, because that's what it is.
 * 
 * You'll need to look at the assignment writeup to implement these methods
 * correctly.
 * 
 * @author liberato
 *
 */
public class HarpString {
	public static final int SAMPLING_RATE = 44100;
	public static final double DECAY_FACTOR = -0.994;
	public CircularDoublesQueue queue;
	public int ticCount = 0;

	/**
	 * Create a harp string of the given frequency, using a sampling rate of
	 * 44,100 Hz; the string is created silent.
	 */
	public HarpString(double frequency) {
		int capacity = (int) Math.ceil(44100.0 / frequency);
		queue = new CircularDoublesQueue(capacity);

		// initialize the queue with zeros
		for (int i = 0; i < capacity; i++) {
			queue.enqueue(0.0);

		}

	}

	/**
	 * Create a harp string whose size and initial values are given by the array.
	 */
	public HarpString(double[] init) {
		queue = new CircularDoublesQueue(init.length);
		// Initialize the queue with the values from the array
		for (double value : init) {
			queue.enqueue(value);
		}
	}

	/**
	 * Pluck the string by filling the buffer with white noise.
	 */

	public void pluck() {
		Random r = new Random(0);

		// replace N items in queue with N random values between -.5 and .5
		for (int i = 0; i < queue.size(); i++) {
			queue.dequeue();
			queue.enqueue(r.nextDouble() - 0.5);

		}
	}

	/**
	 * Advance the string simulation one step by running the Karplus-Strong
	 * algorithm.
	 */
	public void tic() {
		// get next item in queue
		double itemToRemove = queue.dequeue();
		// average that with next first item in queue then times by -.0994
		double newNext = queue.peek();
		double KSdouble = .5 * (itemToRemove + newNext) * .994;
		// add that to end of queue
		queue.enqueue(KSdouble);
		this.ticCount += 1;
	}

	/**
	 * Return the current frequency sample from the string.
	 */
	public double sample() {
		return this.queue.peek();
	}

	/**
	 * Return the number of tics passed since this object was initialized.
	 */
	public int time() {
		return this.ticCount;
	}
}
