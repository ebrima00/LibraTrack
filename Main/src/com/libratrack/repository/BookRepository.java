package com.libratrack.repository;

import com.libratrack.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Manages the storage and retrieval of books in the LibraTrack system.
 * Uses a HashMap for ISBN-based lookup and a TreeMap for
 * case-insensitive title-based lookup and alphabetical sorting.
 */
public class BookRepository {

    private final Map<String, Book> books = new HashMap<>();

    private final TreeMap<String, Book> booksByTitle =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Adds a book to the repository.
     * Rejects null books, invalid ISBNs, empty titles,
     * duplicate ISBNs, and duplicate titles.
     *
     * @param book the book to add
     * @return true if the book is successfully added, otherwise false
     */
    public boolean addBook(Book book) {

        if (book == null || book.getIsbn() == null
                || book.getTitle() == null
                || book.getIsbn().trim().isEmpty()
                || book.getTitle().trim().isEmpty()) {
            return false;
        }

        String isbn = book.getIsbn();
        String title = book.getTitle();

        if (books.containsKey(isbn) || booksByTitle.containsKey(title)) {
            return false;
        }

        books.put(isbn, book);
        booksByTitle.put(title, book);

        return true;
    }

    /**
     * Retrieves all books stored in the repository.
     *
     * @return a list containing all books
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    /**
     * Finds a book using its ISBN.
     *
     * @param isbn the ISBN of the book
     * @return the matching book, or null if it does not exist
     */
    public Book findBookByIsbn(String isbn) {
        return books.get(isbn);
    }

    /**
     * Finds a book using its title.
     * Title lookup is case-insensitive.
     *
     * @param title the title of the book
     * @return the matching book, or null if it does not exist
     */
    public Book findBookByTitle(String title) {
        return booksByTitle.get(title);
    }

    /**
     * Searches for books matching the specified query.
     * Searches are performed against book titles, authors, and ISBNs.
     *
     * @param query the search text
     * @return a list of books matching the query
     */
    public List<Book> searchBooks(String query) {

        List<Book> results = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.matches(query)) {
                results.add(book);
            }
        }

        return results;
    }

    /**
     * Retrieves all books sorted alphabetically by title.
     *
     * @return a list of books sorted by title
     */
    public List<Book> getBooksSortedByTitle() {
        return new ArrayList<>(booksByTitle.values());
    }

    /**
     * Checks whether a book with the specified title exists.
     *
     * @param title the title to check
     * @return true if the book exists, otherwise false
     */
    public boolean containsBook(String title) {
        return booksByTitle.containsKey(title);
    }

    /**
     * Returns the total number of books in the repository.
     *
     * @return the number of stored books
     */
    public int getBookCount() {
        return books.size();
    }

    /**
     * Checks whether the repository contains any books.
     *
     * @return true if the repository is empty, otherwise false
     */
    public boolean isEmpty() {
        return books.isEmpty();
    }
}