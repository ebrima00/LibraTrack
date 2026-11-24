package com.libratrack.model;

/**
 * Defines the contract for objects that support borrowing and returning.
 * Implementing classes must provide methods to borrow an item
 * and return it.
 */
public interface Borrowable {

    /**
     * Attempts to borrow the item.
     *
     * @return true if the borrowing succeeds, otherwise false
     */
    boolean borrow();

    /**
     * Attempts to return the borrowed item.
     *
     * @return true if the return succeeds, otherwise false
     */
    boolean giveBack();
}