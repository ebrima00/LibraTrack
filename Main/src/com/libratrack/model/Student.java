package com.libratrack.model;

/**
 * Represents a student member of the LibraTrack library system.
 * A student member is allowed to borrow up to three books.
 */
public class Student extends Member {

    /**
     * Creates a student member with the specified ID and name.
     *
     * @param memberId the unique ID of the student
     * @param name the student's name
     */
    public Student(int memberId, String name) {
        super(memberId, name);
    }

    /**
     * Returns the maximum number of books a student can borrow.
     *
     * @return the borrowing limit of 3 books
     */
    @Override
    public int getBorrowLimit() {
        return 3;
    }

    /**
     * Returns a string representation of the student.
     *
     * @return the student's details and borrowing limit
     */
    @Override
    public String toString() {
        return "com.libratrack.model.Student - " + super.toString();
    }
}