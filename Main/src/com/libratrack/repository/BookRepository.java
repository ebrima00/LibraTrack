package com.libratrack.repository;

import com.libratrack.model.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BookRepository {

    private final Map<String, Book> books = new HashMap<>();

    private final TreeMap<String, Book> booksByTitle =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

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

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public Book findBookByIsbn(String isbn) {
        return books.get(isbn);
    }

    public Book findBookByTitle(String title) {
        return booksByTitle.get(title);
    }

    public List<Book> searchBooks(String query) {

        List<Book> results = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.matches(query)) {
                results.add(book);
            }
        }

        return results;
    }

    public List<Book> getBooksSortedByTitle() {
        return new ArrayList<>(booksByTitle.values());
    }

    public boolean containsBook(String title) {
        return booksByTitle.containsKey(title);
    }

    public int getBookCount() {
        return books.size();
    }

    public boolean isEmpty() {
        return books.isEmpty();
    }
}