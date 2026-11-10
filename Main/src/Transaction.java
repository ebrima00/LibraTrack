import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String isbn;
    private String bookTitle;
    private String transactionType;
    private LocalDateTime transactionDate;

    public Transaction(String isbn, String bookTitle, String transactionType) {
        this.isbn = isbn;
        this.bookTitle = bookTitle;
        this.transactionType = transactionType;
        this.transactionDate = LocalDateTime.now();
    }

    public String getIsbn() {
        return isbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return "Book: " + bookTitle
                + ", ISBN: " + isbn
                + ", Action: " + transactionType
                + ", Date: " + transactionDate.format(formatter);
    }
}