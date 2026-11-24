package com.libratrack.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a library transaction, such as borrowing or returning a book.
 * Stores the book details, transaction type, and date and time of the transaction.
 */
public class Transaction {

    private String isbn;
    private String bookTitle;
    private String transactionType;
    private LocalDateTime transactionDate;

    /**
     * Creates a transaction with the specified book and transaction details.
     * The transaction date and time are set automatically.
     *
     * @param isbn the ISBN of the book
     * @param bookTitle the title of the book
     * @param transactionType the type of transaction performed
     */
    public Transaction(String isbn, String bookTitle, String transactionType) {
        this.isbn = isbn;
        this.bookTitle = bookTitle;
        this.transactionType = transactionType;
        this.transactionDate = LocalDateTime.now();
    }

    /**
     * Returns the ISBN of the book involved in the transaction.
     *
     * @return the book's ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns the title of the book involved in the transaction.
     *
     * @return the book's title
     */
    public String getBookTitle() {
        return bookTitle;
    }

    /**
     * Returns the type of transaction performed.
     *
     * @return the transaction type
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Returns the date and time when the transaction occurred.
     *
     * @return the transaction date and time
     */
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    /**
     * Returns a formatted string containing the transaction details.
     *
     * @return the book title, ISBN, transaction action, and formatted date
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return "com.libratrack.model.Book: " + bookTitle
                + ", ISBN: " + isbn
                + ", Action: " + transactionType
                + ", Date: " + transactionDate.format(formatter);
    }
}