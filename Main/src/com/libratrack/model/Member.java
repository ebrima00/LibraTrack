package com.libratrack.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents an abstract library member.
 * Stores member information and maintains a history of borrowing transactions.
 * Subclasses must define their own borrowing limits.
 */
public abstract class Member {

    private int memberId;
    private String name;

    private final LinkedList<Transaction> borrowHistory =
            new LinkedList<>();

    /**
     * Creates a library member with an ID and name.
     *
     * @param memberId the unique ID of the member
     * @param name the name of the member
     */
    public Member(int memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    /**
     * Returns the member's ID.
     *
     * @return the member ID
     */
    public int getMemberId() {
        return memberId;
    }

    /**
     * Returns the member's name.
     *
     * @return the member name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the maximum number of books this member can borrow.
     *
     * @return the member's borrowing limit
     */
    public abstract int getBorrowLimit();

    /**
     * Adds a transaction to the member's borrowing history.
     *
     * @param transaction the transaction to add
     */
    public void addTransaction(Transaction transaction) {
        borrowHistory.add(transaction);
    }

    /**
     * Returns a copy of the member's transaction history.
     *
     * @return a list containing the member's transactions
     */
    public List<Transaction> getBorrowHistory() {
        return new LinkedList<>(borrowHistory);
    }

    /**
     * Displays the member's borrowing and return transaction history.
     * Displays a message if no transaction history exists.
     */
    public void printBorrowHistory() {
        System.out.println("\n===== Borrow History: " + name + " =====");

        if (borrowHistory.isEmpty()) {
            System.out.println("No transaction history found.");
            return;
        }

        for (Transaction transaction : borrowHistory) {
            System.out.println(transaction);
        }
    }

    /**
     * Returns a string representation of the member.
     *
     * @return the member's ID, name, and borrowing limit
     */
    @Override
    public String toString() {
        return "com.libratrack.model.Member ID: " + memberId
                + ", Name: " + name
                + ", Borrow Limit: " + getBorrowLimit() + " books";
    }
}