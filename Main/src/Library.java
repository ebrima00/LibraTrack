import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Library {

    private final List<Book> books = new ArrayList<>();

    // Add a book
    public void addBook(Book book) {
        books.add(book);
    }

    // Return a copy of the book list
    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    // Search using the Searchable interface
    public List<Book> searchBooks(String query) {
        return books.stream()
                .filter(book -> book.matches(query))
                .collect(Collectors.toList());
    }

    // Find a book by title
    public Book findBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    // Display all books
    public void printCatalog() {
        System.out.println("\n=== Full Book Catalog ===");

        if (books.isEmpty()) {
            System.out.println("No books in the catalog.");
            return;
        }

        books.forEach(System.out::println);
    }

    // Display available books sorted by title
    public void printAvailableBooksSorted() {
        System.out.println("\n=== Available Books (Sorted by Title) ===");

        List<Book> availableBooks = books.stream()
                .filter(Book::isAvailable)
                .sorted(Comparator.comparing(
                        Book::getTitle,
                        String.CASE_INSENSITIVE_ORDER
                ))
                .collect(Collectors.toList());

        if (availableBooks.isEmpty()) {
            System.out.println("No books are currently available.");
            return;
        }

        availableBooks.forEach(System.out::println);
    }

    // Borrow a book using the Borrowable interface
    public boolean borrowBook(String title) {
        Book book = findBookByTitle(title);

        if (book == null) {
            return false;
        }

        return book.borrow();
    }

    // Return a book using the Borrowable interface
    public boolean returnBook(String title) {
        Book book = findBookByTitle(title);

        if (book == null) {
            return false;
        }

        return book.giveBack();
    }

    // Check whether a title exists
    public boolean containsBook(String title) {
        return findBookByTitle(title) != null;
    }

    // Return the number of books
    public int getBookCount() {
        return books.size();
    }
}