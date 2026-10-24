import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Library {

    // The Library owns and manages its collection of books
    private final List<Book> books = new ArrayList<>();

    // Add a book
    public void addBook(Book book) {
        books.add(book);
    }

    // Get a copy of the book list
    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    // Find books using a Predicate
    public List<Book> searchBooks(Predicate<Book> condition) {
        return books.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    // Find a book by its title
    public Book findBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    // Display the complete catalog
    public void printCatalog() {
        System.out.println("\n=== Full Book Catalog ===");

        if (books.isEmpty()) {
            System.out.println("No books in the catalog.");
            return;
        }

        books.forEach(System.out::println);
    }

    // Display available books sorted alphabetically
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

    // Checkout or return a book
    public boolean toggleBookStatus(String title) {
        Book book = findBookByTitle(title);

        if (book == null) {
            return false;
        }

        book.setAvailable(!book.isAvailable());
        return true;
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