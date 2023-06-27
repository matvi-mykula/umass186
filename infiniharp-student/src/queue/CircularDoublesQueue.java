/*
 * Copyright 2021 Marc Liberatore.
 */
package queue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 
 * A specialized implementation of an array-based circular queue of doubles.
 * 
 * @author liberato
 *
 */
public class CircularDoublesQueue {
	private int front;
	private int rear;
	private int size;
	private ArrayList<Double> queue;
	private int maxCapacity;

	/**
	 * Create an empty circular queue of doubles, with the given maximum capacity.
	 */
	public CircularDoublesQueue(int maxCapacity) {
		this.front = 0;
		this.rear = -1;
		this.size = 0;
		this.queue = new ArrayList<>();
		this.maxCapacity = maxCapacity;
	}

	/**
	 * Returns the number of elements in this queue.
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns true if this queue contains no elements.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns true if enqueuing on this queue will exceed its maximum capacity.
	 */
	public boolean isFull() {
		return size == this.maxCapacity;
	}

	/**
	 * Inserts the specified value at the end of this queue if it is possible to do
	 * so immediately without violating capacity restrictions, throwing an
	 * IllegalStateException if no space is currently available.
	 */
	public void enqueue(double d) {
		if (isFull()) {
			throw new IllegalStateException("Queue is full");
		}

		// rear = (rear + 1) % queue.length;

		size++;
		queue.add(d);
	}

	/**
	 * Retrieves and removes the first element of this queue, throwing a
	 * NoSuchElement exception if this queue is empty.
	 */
	public double dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException("Queue is empty");
		}
		// double item = queue[front];
		// front = (front + 1) % queue.length;
		// queue[front]=
		size--;
		return queue.remove(0);
	}

	/**
	 * Retrieves but does not remove the first element of this queue, throwing a
	 * NoSuchElement exception if this queue is empty.
	 */
	public double peek() {
		if (isEmpty()) {
			throw new NoSuchElementException("Queue is empty");
		}
		return queue.get(front);
	}

	public double getItemByIndex(int index) {
		if (index < 0 || index >= queue.size()) {
			throw new IndexOutOfBoundsException("Invalid index: " + index);
		}
		return queue.get(index);
	}
}