/*
 * Copyright 2021 Marc Liberatore.
 */

package scheduler;

import java.sql.Array;
import java.util.Arrays;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

	private List<Course> Courses;
	private List<Student> Students;

	/**
	 * Instantiates a new, empty scheduler.
	 */
	public Scheduler() {
		this.Courses = new ArrayList<>();
		this.Students = new ArrayList<>();

	}

	/**
	 * Adds a course to the scheduler.
	 * 
	 * @param course the course to be added
	 */
	public void addCourse(Course course) {
		this.Courses.add(course);
	}

	/**
	 * Returns the list of courses that this scheduler knows about.
	 * 
	 * This returned object does not share state with the internal state of the
	 * Scheduler.
	 *
	 * @return the list of courses
	 */
	public List<Course> getCourses() {
		List<Course> x = new ArrayList<>(this.Courses);
		return x;
	}

	/**
	 * Adds a student to the scheduler.
	 * 
	 * @param student the student to add
	 */
	public void addStudent(Student student) {
		this.Students.add(student);
	}

	/**
	 * Returns a list of the students this scheduler knows about.
	 * 
	 * This returned object does not share state with the internal state of the
	 * Scheduler.
	 * 
	 * @return
	 */
	public List<Student> getStudents() {
		List<Student> x = new ArrayList<>(this.Students);
		return x;
	}

	/// check if all classes are full or all students at max courseload or the only
	/// classes left are not in students preffered list
	// if so return false
	public boolean checkIfAssigningToDo() {

		List<Course> courses = this.getCourses();
		// for (Course course : courses) {
		// if (course.checkCapacity()) {
		// allClassesFull = false;
		// break;
		// }
		// }

		/// getting timed out here
		List<Student> students = this.getStudents();
		for (Student student : students) {
			if (student.checkAvailability()) { // if student can still enroll in classes
				for (Course course : courses) {
					if (course.checkCapacity() && student.isCourseInPreferences(course)) { // if there is a preffered
																							// class that has capacity
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Assigns all students to courses in the following manner:
	 * 
	 * For a given student, check their list of preferred courses. Add them to the
	 * course that:
	 * - exists in the scheduler's list of courses
	 * - the student most prefers (that is, comes first in their preference list)
	 * - the student is not not already enrolled in
	 * - and is not full (in other words, at capacity)
	 * Adds courses to the *end* of the student's current list of classes. Adds
	 * students to
	 * the *end* of the course's roster.
	 * 
	 * Repeat this process for each student, one-by-one; each student will now have
	 * one course,
	 * usually (but not always) their most preferred course.
	 * 
	 * Then repeat this whole process (adding one course per student, when possible,
	 * proceeding
	 * round-robin among students), until there is nothing left to do: Students
	 * might
	 * all be at their maximum number of courses, or there may be no available seats
	 ***** in courses
	 ***** that students want.
	 */
	public void assignAll() {
		List<Course> courses = new ArrayList<>(this.Courses);
		ArrayList<Student> students = new ArrayList<>(this.getStudents());

		// for each student
		// check if maxcourseload reached, if not
		// add top preference if that class is not full and they arent already in that
		// class
		while (checkIfAssigningToDo()) {
			for (int i = 0; i < students.size(); i++) {
				/// check if studnet is at maxcourseload
				if (students.get(i).checkAvailability()) {
					// try to assign preferences, if class is full or student already in class
					Student student = students.get(i);
					List<Course> preferences = student.getPreferences();

					for (Course course : preferences) {
						if (course.checkCapacity() && course.checkEnrollment(student)
								&& student.isCourseInPreferences(course)) {// class is not full and student
							// is
							// not already in it && class is
							// in students preferences
							// add student to course altering state
							course.roster.add(student);
							student.schedule.add(course);

						}

					}
					// continue and try again

					// else assign student to class
				} else {// skip student as they have full courseload
					continue;
				}

			}
			// repeat this loop until all students are at maxcourseload or until all classes
			// are at capacity

		}
		return;
	}

	/**
	 * Drops a student from a course.
	 * 
	 * @param student
	 * @param course
	 * @throws IllegalArgumentException if either the student or the course are not
	 *                                  known to this scheduler
	 */
	public void drop(Student student, Course course) throws IllegalArgumentException {

		if (this.Courses.contains(course) == false || this.Students.contains(student) == false) {
			throw new IllegalArgumentException("Invalid Characters");
		}

		student.removeCourse(course);
		course.removeStudent(student);
	}

	/**
	 * Drops a student from all of their courses.
	 * 
	 * @param student
	 * @throws IllegalArgumentException if the student is not known to this
	 *                                  scheduler
	 */
	public void unenroll(Student student) throws IllegalArgumentException {
	}

	public static void main(String[] args) {

		// Course courseOne = new Course("ONE1", 1);
		// List<Course> l = new ArrayList<>();
		// l.add(courseOne);
		// Student studentOne = new Student("one", 1, l);
		// Scheduler singleScheduler = new Scheduler();
		// singleScheduler.addStudent(studentOne);
		// singleScheduler.addCourse(courseOne);

		// List<Course> m = singleScheduler.getCourses();
		// m.clear();
		// singleScheduler.getCourses().contains(courseOne);

		Scheduler scheduler = new Scheduler();
		Course a = new Course("ANTHRO100", 2);
		Course b = new Course("BIO100", 2);

		List<Course> listA = Arrays.asList(new Course[] { a });

		Student s = new Student("s", 1, listA);
		List<Student> listS = Arrays.asList(new Student[] { s });

		scheduler.addStudent(s);
		scheduler.addCourse(b);
		scheduler.assignAll();
		int roster = a.getRoster().size();
		System.out.println(roster);

	}
}
