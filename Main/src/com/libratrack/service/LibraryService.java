package com.libratrack.service;

import com.libratrack.model.Book;
import com.libratrack.model.Member;

import java.util.List;

/**
 * Defines the services provided by the LibraTrack library system.
 * Provides operations for managing books, searching the catalog,
 * borrowing and returning books, managing reservations, and
 * tracking recently returned books.
 */
public interface LibraryService {

    /**
     * Adds a book to the library.
     *
     * @param book the book to add
     * @return true if the book is successfully added, otherwise false
     */
    boolean addBook(Book book);

    /**
     * Retrieves all books in the library.
     *
     * @return a list of all books
     */
    List<Book> getBooks();

    /**
     * Finds a book using its ISBN.
     *
     * @param isbn the ISBN of the book
     * @return the matching book, or null if not found
     */
    Book findBookByIsbn(String isbn);

    /**
     * Finds a book using its title.
     *
     * @param title the title of the book
     * @return the matching book, or null if not found
     */
    Book findBookByTitle(String title);

    /**
     * Searches the library catalog using a search query.
     *
     * @param query the search text
     * @return a list of books matching the query
     */
    List<Book> searchBooks(String query);

    /**
     * Displays the library catalog.
     */
    void printCatalog();

    /**
     * Displays the library catalog sorted alphabetically by title.
     */
    void printCatalogSortedByTitle();

    /**
     * Displays all available books sorted by title.
     */
    void printAvailableBooksSorted();

    /**
     * Attempts to borrow a book for a library member.
     *
     * @param title the title of the book to borrow
     * @param member the member requesting the book
     * @return true if borrowing succeeds, otherwise false
     */
    boolean borrowBook(String title, Member member);

    /**
     * Processes the return of a borrowed book.
     *
     * @param title the title of the book being returned
     * @param member the member returning the book
     * @return true if the return succeeds, otherwise false
     */
    boolean returnBook(String title, Member member);

    /**
     * Adds a member to a book's reservation waitlist.
     *
     * @param isbn the ISBN of the book to reserve
     * @param member the member requesting the reservation
     */
    void reserveBook(String isbn, Member member);

    /**
     * Retrieves the next member in a book's reservation waitlist
     * without removing that member.
     *
     * @param isbn the ISBN of the reserved book
     * @return the next waiting member, or null if the waitlist is empty
     */
    Member getNextReservation(String isbn);

    /**
     * Removes and returns the next member in a book's reservation waitlist.
     *
     * @param isbn the ISBN of the reserved book
     * @return the next waiting member, or null if the waitlist is empty
     */
    Member serveNextReservation(String isbn);

    /**
     * Displays the reservation waitlist for a specified book.
     *
     * @param isbn the ISBN of the book
     */
    void printReservationWaitlist(String isbn);

    /**
     * Displays the recently returned books.
     */
    void printRecentlyReturnedBooks();

    /**
     * Retrieves the most recently returned book without removing it.
     *
     * @return the most recently returned book, or null if none exists
     */
    Book getMostRecentlyReturnedBook();

    /**
     * Removes and returns the most recently returned book.
     *
     * @return the removed book, or null if none exists
     */
    Book removeMostRecentlyReturnedBook();

    /**
     * Checks whether a book with the specified title exists.
     *
     * @param title the title to check
     * @return true if the book exists, otherwise false
     */
    boolean containsBook(String title);

    /**
     * Returns the total number of books in the library.
     *
     * @return the number of books
     */
    int getBookCount();

    /**
     * Retrieves all books sorted alphabetically by title.
     *
     * @return a list of books sorted by title
     */
    List<Book> getBooksSortedByTitle();
}