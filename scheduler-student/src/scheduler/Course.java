/*
 * Copyright 2021 Marc Liberatore.
 */

package scheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a Course.
 * 
 * @author liberato
 *
 */
public class Course {
	private String courseNumber;
	private int capacity;
	private List<Student> roster;

	/**
	 * Instantiates a new Course object. The course number must be non-empty, and
	 * the
	 * capacity must be greater than zero.
	 * 
	 * @param courseNumber a course number, like "COMPSCI190D"
	 * @param capacity     the maximum number of students that can be in the class
	 * @throws IllegalArgumentException thrown if the courseNumber or capacity are
	 *                                  invalid
	 */
	public Course(String courseNumber, int capacity) throws IllegalArgumentException {
		if (courseNumber.length() == 0) {
			throw new IllegalArgumentException("Invalid Characters");
		}
		if (capacity < 1) {
			throw new IllegalArgumentException("Invalid Characters");

		}

		this.courseNumber = courseNumber;
		this.capacity = capacity;
		this.roster = new ArrayList<>();
	}

	/**
	 * 
	 * @return the capacity of the course
	 */
	public int getCapacity() {
		int x = this.capacity;
		return x;
	}

	/**
	 * 
	 * @return the course number
	 */
	public String getCourseNumber() {
		String x = this.courseNumber;

		return x;
	}

	/**
	 * Returns the list of students enrolled in the course.
	 * 
	 * This returned object does not share state with the internal state of the
	 * Course.
	 * 
	 * @return the list of students currently in the course
	 */
	public List<Student> getRoster() {
		List<Student> x = this.roster;
		return x;
	}
}
