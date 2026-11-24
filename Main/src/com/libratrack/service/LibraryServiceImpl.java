package com.libratrack.service;

import com.libratrack.model.Book;
import com.libratrack.model.Member;
import com.libratrack.model.Transaction;
import com.libratrack.repository.BookRepository;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Provides the implementation of the LibraryService interface.
 * Manages book operations, borrowing, returning, reservations,
 * and recently returned books.
 *
 * Uses BookRepository for book storage, a Queue for reservation
 * waitlists, and a Deque for recently returned books.
 */
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository = new BookRepository();

    private final Map<String, Queue<Member>> reservationWaitlists =
            new HashMap<>();

    private final Deque<Book> recentlyReturnedBooks =
            new ArrayDeque<>();

    /**
     * Adds a book to the library and initializes its reservation waitlist.
     *
     * @param book the book to add
     * @return true if the book is successfully added, otherwise false
     */
    @Override
    public boolean addBook(Book book) {
        boolean added = bookRepository.addBook(book);

        if (added) {
            reservationWaitlists.put(book.getIsbn(), new ArrayDeque<>());
        }

        return added;
    }

    /**
     * Retrieves all books stored in the library.
     *
     * @return a list of all books
     */
    @Override
    public List<Book> getBooks() {
        return bookRepository.getAllBooks();
    }

    /**
     * Finds a book using its ISBN.
     *
     * @param isbn the ISBN of the book
     * @return the matching book, or null if not found
     */
    @Override
    public Book findBookByIsbn(String isbn) {
        return bookRepository.findBookByIsbn(isbn);
    }

    /**
     * Finds a book using its title.
     *
     * @param title the title of the book
     * @return the matching book, or null if not found
     */
    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findBookByTitle(title);
    }

    /**
     * Searches the library catalog for books matching a query.
     *
     * @param query the search text
     * @return a list of matching books
     */
    @Override
    public List<Book> searchBooks(String query) {
        return bookRepository.searchBooks(query);
    }

    /**
     * Displays all books in the library catalog.
     * Displays a message if the catalog is empty.
     */
    @Override
    public void printCatalog() {
        List<Book> books = bookRepository.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("The library catalog is empty.");
            return;
        }

        System.out.println("\n===== Library Catalog =====");

        for (Book book : books) {
            System.out.println(book);
        }
    }

    /**
     * Displays all books sorted alphabetically by title.
     * Displays a message if the catalog is empty.
     */
    @Override
    public void printCatalogSortedByTitle() {
        List<Book> books = bookRepository.getBooksSortedByTitle();

        if (books.isEmpty()) {
            System.out.println("The library catalog is empty.");
            return;
        }

        System.out.println("\n===== Catalog Sorted by Title =====");

        for (Book book : books) {
            System.out.println(book);
        }
    }

    /**
     * Displays all currently available books in alphabetical order.
     * Displays a message if no books are available.
     */
    @Override
    public void printAvailableBooksSorted() {
        boolean found = false;

        System.out.println("\n===== Available Books (A-Z) =====");

        for (Book book : bookRepository.getBooksSortedByTitle()) {
            if (book.isAvailable()) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books are currently available.");
        }
    }

    /**
     * Attempts to borrow a book for a library member.
     * Borrowing is rejected if the book does not exist, is unavailable,
     * the member is null, or the book has a reservation waitlist.
     *
     * @param title the title of the book to borrow
     * @param member the member requesting the book
     * @return true if borrowing succeeds, otherwise false
     */
    @Override
    public boolean borrowBook(String title, Member member) {
        Book book = findBookByTitle(title);

        if (book == null || member == null || !book.isAvailable()) {
            return false;
        }

        Queue<Member> waitlist =
                reservationWaitlists.get(book.getIsbn());

        if (waitlist != null && !waitlist.isEmpty()) {
            System.out.println("This book has a reservation waitlist.");
            return false;
        }

        if (book.borrow()) {
            Transaction transaction = new Transaction(
                    book.getIsbn(),
                    book.getTitle(),
                    "BORROWED"
            );

            member.addTransaction(transaction);
            return true;
        }

        return false;
    }

    /**
     * Processes the return of a borrowed book.
     * Updates the book's availability, records the transaction,
     * and adds the book to the recently returned collection.
     *
     * @param title the title of the book being returned
     * @param member the member returning the book
     * @return true if the return succeeds, otherwise false
     */
    @Override
    public boolean returnBook(String title, Member member) {
        Book book = findBookByTitle(title);

        if (book == null || member == null || book.isAvailable()) {
            return false;
        }

        if (book.giveBack()) {
            recentlyReturnedBooks.push(book);

            Transaction transaction = new Transaction(
                    book.getIsbn(),
                    book.getTitle(),
                    "RETURNED"
            );

            member.addTransaction(transaction);
            return true;
        }

        return false;
    }

    /**
     * Adds a member to a book's reservation waitlist.
     * Prevents duplicate reservations by the same member.
     *
     * @param isbn the ISBN of the book to reserve
     * @param member the member requesting the reservation
     */
    @Override
    public void reserveBook(String isbn, Member member) {
        Book book = findBookByIsbn(isbn);

        if (book == null || member == null) {
            System.out.println("Book or member not found.");
            return;
        }

        Queue<Member> waitlist =
                reservationWaitlists.get(isbn);

        if (waitlist == null) {
            waitlist = new ArrayDeque<>();
            reservationWaitlists.put(isbn, waitlist);
        }

        for (Member queuedMember : waitlist) {
            if (queuedMember.getMemberId() == member.getMemberId()) {
                System.out.println("Member is already on the waitlist.");
                return;
            }
        }

        if (book.isAvailable() && waitlist.isEmpty()) {
            System.out.println("The book is available for borrowing.");
            return;
        }

        waitlist.offer(member);

        System.out.println(
                member.getName() + " added to the reservation waitlist."
        );
    }

    /**
     * Retrieves the next member in a reservation waitlist
     * without removing them.
     *
     * @param isbn the ISBN of the reserved book
     * @return the next waiting member, or null if the waitlist is empty
     */
    @Override
    public Member getNextReservation(String isbn) {
        Queue<Member> waitlist = reservationWaitlists.get(isbn);
        return waitlist == null ? null : waitlist.peek();
    }

    /**
     * Removes and returns the next member in a reservation waitlist.
     *
     * @param isbn the ISBN of the reserved book
     * @return the next waiting member, or null if the waitlist is empty
     */
    @Override
    public Member serveNextReservation(String isbn) {
        Queue<Member> waitlist = reservationWaitlists.get(isbn);
        return waitlist == null ? null : waitlist.poll();
    }

    /**
     * Displays the reservation waitlist for a specified book.
     * Members are displayed in their reservation order.
     *
     * @param isbn the ISBN of the book
     */
    @Override
    public void printReservationWaitlist(String isbn) {
        Book book = findBookByIsbn(isbn);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        Queue<Member> waitlist = reservationWaitlists.get(isbn);

        System.out.println(
                "\n===== Reservation Waitlist: "
                        + book.getTitle() + " ====="
        );

        if (waitlist == null || waitlist.isEmpty()) {
            System.out.println("No members are waiting.");
            return;
        }

        int position = 1;

        for (Member member : waitlist) {
            System.out.println(position + ". " + member);
            position++;
        }
    }

    /**
     * Displays books in the recently returned collection.
     * The most recently returned book appears first.
     */
    @Override
    public void printRecentlyReturnedBooks() {
        System.out.println("\n===== Recently Returned Books =====");

        if (recentlyReturnedBooks.isEmpty()) {
            System.out.println("No books have been returned yet.");
            return;
        }

        for (Book book : recentlyReturnedBooks) {
            System.out.println(book);
        }
    }

    /**
     * Retrieves the most recently returned book without removing it.
     *
     * @return the most recently returned book, or null if none exists
     */
    @Override
    public Book getMostRecentlyReturnedBook() {
        return recentlyReturnedBooks.peek();
    }

    /**
     * Removes and returns the most recently returned book.
     *
     * @return the removed book, or null if the collection is empty
     */
    @Override
    public Book removeMostRecentlyReturnedBook() {
        return recentlyReturnedBooks.poll();
    }

    /**
     * Checks whether a book with the specified title exists.
     *
     * @param title the title to check
     * @return true if the book exists, otherwise false
     */
    @Override
    public boolean containsBook(String title) {
        return bookRepository.containsBook(title);
    }

    /**
     * Returns the total number of books in the library.
     *
     * @return the total number of stored books
     */
    @Override
    public int getBookCount() {
        return bookRepository.getBookCount();
    }

    /**
     * Retrieves all books sorted alphabetically by title.
     *
     * @return a list of books sorted by title
     */
    @Override
    public List<Book> getBooksSortedByTitle() {
        return bookRepository.getBooksSortedByTitle();
    }
}