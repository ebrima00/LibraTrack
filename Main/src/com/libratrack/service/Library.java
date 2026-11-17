package com.libratrack.service;

import com.libratrack.model.Book;
import com.libratrack.model.Member;
import com.libratrack.model.Transaction;
import com.libratrack.repository.BookRepository;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;

public class Library {

    private final BookRepository bookRepository = new BookRepository();

    private final Map<String, Queue<Member>> reservationWaitlists =
            new HashMap<>();

    private final Deque<Book> recentlyReturnedBooks =
            new ArrayDeque<>();

    public boolean addBook(Book book) {
        boolean added = bookRepository.addBook(book);

        if (added) {
            reservationWaitlists.put(book.getIsbn(), new ArrayDeque<>());
        }

        return added;
    }

    public List<Book> getBooks() {
        return bookRepository.getAllBooks();
    }

    public Book findBookByIsbn(String isbn) {
        return bookRepository.findBookByIsbn(isbn);
    }

    public Book findBookByTitle(String title) {
        return bookRepository.findBookByTitle(title);
    }

    public List<Book> searchBooks(String query) {
        return bookRepository.searchBooks(query);
    }

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

    public Member getNextReservation(String isbn) {
        Queue<Member> waitlist = reservationWaitlists.get(isbn);
        return waitlist == null ? null : waitlist.peek();
    }

    public Member serveNextReservation(String isbn) {
        Queue<Member> waitlist = reservationWaitlists.get(isbn);
        return waitlist == null ? null : waitlist.poll();
    }

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

    public Book getMostRecentlyReturnedBook() {
        return recentlyReturnedBooks.peek();
    }

    public Book removeMostRecentlyReturnedBook() {
        return recentlyReturnedBooks.poll();
    }

    public boolean containsBook(String title) {
        return bookRepository.containsBook(title);
    }

    public int getBookCount() {
        return bookRepository.getBookCount();
    }

    public List<Book> getBooksSortedByTitle() {
        return bookRepository.getBooksSortedByTitle();
    }
}