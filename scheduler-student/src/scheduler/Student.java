/*
 * Copyright 2021 Marc Liberatore.
 */

package scheduler;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a student.
 * 
 * @author liberato
 *
 */
public class Student {
	private String name;
	private int maxCourses;
	List<Course> preferences;
	List<Course> schedule;

	/**
	 * 
	 * Instantiates a new Student object. The student's maximum course load must be
	 * greater
	 * than zero, and the preferences list must contain at least one course.
	 * 
	 * The preference list is copied into this Student object.
	 * 
	 * @param name        the student's name
	 * @param maxCourses  the maximum number of courses that can be on this
	 *                    student's schedule
	 * @param preferences the student's ordered list of preferred courses
	 * @throws IllegalArgumentException thrown if the maxCourses or preferences are
	 *                                  invalid
	 */
	public Student(String name, int maxCourses, List<Course> preferences) throws IllegalArgumentException {

		if (name.length() == 0) {
			throw new IllegalArgumentException("Invalid Characters");

		}
		if (maxCourses < 1) {
			throw new IllegalArgumentException("Invalid Characters");

		}
		if (preferences.size() < 1) {
			throw new IllegalArgumentException("Invalid Characters");

		}

		this.name = name;
		this.maxCourses = maxCourses;
		this.preferences = preferences;
		this.schedule = new ArrayList<>();
	}

	/**
	 * 
	 * @return the student's name
	 */
	public String getName() {
		String x = this.name;
		return x;
	}

	/**
	 * 
	 * @return the student's max course load
	 */
	public int getMaxCourses() {
		int x = this.maxCourses;
		return x;
	}

	/**
	 * Returns the student's list of course preferences, ordered from most- to
	 * least-desired.
	 * 
	 * This returned object does not share state with the internal state of the
	 * Student.
	 * 
	 * @return the student's preference list
	 */
	public List<Course> getPreferences() {
		return new ArrayList<>(this.preferences);
	}

	/**
	 * Returns the student's current schedule.
	 * 
	 * This returned object does not share state with the internal state of the
	 * Student.
	 *
	 * @return the student's schedule
	 */
	public List<Course> getSchedule() {

		return new ArrayList<>(this.schedule);
	}
}
