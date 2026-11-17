package com.libratrack.service;

import com.libratrack.model.Book;
import com.libratrack.model.Member;

import java.util.List;

public interface LibraryService {

    boolean addBook(Book book);

    List<Book> getBooks();

    Book findBookByIsbn(String isbn);

    Book findBookByTitle(String title);

    List<Book> searchBooks(String query);

    void printCatalog();

    void printCatalogSortedByTitle();

    void printAvailableBooksSorted();

    boolean borrowBook(String title, Member member);

    boolean returnBook(String title, Member member);

    void reserveBook(String isbn, Member member);

    Member getNextReservation(String isbn);

    Member serveNextReservation(String isbn);

    void printReservationWaitlist(String isbn);

    void printRecentlyReturnedBooks();

    Book getMostRecentlyReturnedBook();

    Book removeMostRecentlyReturnedBook();

    boolean containsBook(String title);

    int getBookCount();

    List<Book> getBooksSortedByTitle();
}