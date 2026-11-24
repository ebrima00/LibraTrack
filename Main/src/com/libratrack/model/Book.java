package com.libratrack.model;

/**
 * Represents a book in the LibraTrack library system.
 * Stores the book's ISBN, title, author, and availability status.
 * Implements searching and borrowing operations.
 */
public class Book implements Searchable, Borrowable {

    private String isbn;
    private String title;
    private String author;
    private boolean available;

    /**
     * Creates a new book with the specified details.
     *
     * @param isbn      the unique ISBN of the book
     * @param title     the title of the book
     * @param author    the author of the book
     * @param available the availability status of the book
     */
    public Book(String isbn, String title, String author, boolean available) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    /**
     * Returns the ISBN of the book.
     *
     * @return the book's ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns the title of the book.
     *
     * @return the book's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author's name.
     *
     * @return the book's author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Checks whether the book is available for borrowing.
     *
     * @return true if the book is available, otherwise false
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Updates the ISBN of the book.
     *
     * @param isbn the new ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Updates the title of the book.
     *
     * @param title the new book title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Updates the author's name.
     *
     * @param author the new author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Updates the availability status of the book.
     *
     * @param available the new availability status
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Checks whether the book matches a search query.
     * The query is compared against the title, author, and ISBN.
     *
     * @param query the search text
     * @return true if the query matches the title, author, or ISBN
     */
    @Override
    public boolean matches(String query) {
        return title.equalsIgnoreCase(query)
                || author.equalsIgnoreCase(query)
                || isbn.equalsIgnoreCase(query);
    }

    /**
     * Marks the book as borrowed if it is currently available.
     *
     * @return true if borrowing succeeds, otherwise false
     */
    @Override
    public boolean borrow() {
        if (!available) {
            return false;
        }

        available = false;
        return true;
    }

    /**
     * Marks the book as available after it has been returned.
     *
     * @return true if the return succeeds, otherwise false
     */
    @Override
    public boolean giveBack() {
        if (available) {
            return false;
        }

        available = true;
        return true;
    }

    /**
     * Returns a string representation of the book and its details.
     *
     * @return the book's ISBN, title, author, and availability status
     */
    @Override
    public String toString() {
        String status = available ? "Available" : "Not Available";

        return "ISBN: " + isbn
                + ", Title: " + title
                + ", Author: " + author
                + ", Status: " + status;
    }
}