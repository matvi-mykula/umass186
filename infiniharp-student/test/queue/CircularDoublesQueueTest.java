/*
 * Copyright 2021 Marc Liberatore.
 */
package queue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CircularDoublesQueueTest {
	@Test
	public void testInitialize() {
		CircularDoublesQueue q = new CircularDoublesQueue(1);
	}

	@Test
	public void testEmpty() {
		CircularDoublesQueue q = new CircularDoublesQueue(1);
		assertTrue(q.isEmpty());
		q.enqueue(0);
		assertFalse(q.isEmpty());
		q.dequeue();
		assertTrue(q.isEmpty());
	}

	@Test
	public void testFull() {
		CircularDoublesQueue q = new CircularDoublesQueue(2);
		assertFalse(q.isFull());
		q.enqueue(1);
		q.enqueue(2);
		assertTrue(q.isFull());
		q.dequeue();
		assertFalse(q.isFull());
	}

	@Test
	public void testEnqueueDequeue() {
		CircularDoublesQueue q = new CircularDoublesQueue(3);
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3);
		assertTrue(q.isFull());

	}

	@Test(expected = IllegalStateException.class)
	public void testEnqueueFullQueue() {
		CircularDoublesQueue q = new CircularDoublesQueue(2);
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3); // Should not enqueue as the queue is already full
		assertTrue(q.isFull());

	}
}
