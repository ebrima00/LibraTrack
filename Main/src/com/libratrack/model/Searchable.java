package com.libratrack.model;

/**
 * Defines the contract for objects that can be searched.
 * Implementing classes must provide a method to check
 * whether an object matches a given search query.
 */
public interface Searchable {

    /**
     * Checks whether the object matches the specified search query.
     *
     * @param query the text used to search for the object
     * @return true if the object matches the query, otherwise false
     */
    boolean matches(String query);
}