package com.libratrack.model;

/**
 * Represents a faculty member of the LibraTrack library system.
 * Faculty members are allowed to borrow up to five books.
 */
public class Faculty extends Member {

    /**
     * Creates a faculty member with the specified ID and name.
     *
     * @param memberId the unique ID of the faculty member
     * @param name the faculty member's name
     */
    public Faculty(int memberId, String name) {
        super(memberId, name);
    }

    /**
     * Returns the maximum number of books a faculty member can borrow.
     *
     * @return the borrowing limit of 5 books
     */
    @Override
    public int getBorrowLimit() {
        return 5;
    }

    /**
     * Returns a string representation of the faculty member.
     *
     * @return the faculty member's details and borrowing limit
     */
    @Override
    public String toString() {
        return "com.libratrack.model.Faculty - " + super.toString();
    }
}