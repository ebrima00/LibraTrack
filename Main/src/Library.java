import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Library {

    private final Map<String, Book> books = new HashMap<>();

    public void addBook(Book book) {
        if (books.containsKey(book.getIsbn())) {
            System.out.println("A book with this ISBN already exists.");
            return;
        }

        books.put(book.getIsbn(), book);
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books.values());
    }

    public List<Book> searchBooks(String query) {
        return books.values().stream()
                .filter(book -> book.matches(query))
                .collect(Collectors.toList());
    }

    public Book findBookByIsbn(String isbn) {
        return books.get(isbn);
    }

    public Book findBookByTitle(String title) {
        return books.values().stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    public void printCatalog() {
        System.out.println("\n=== Full Book Catalog ===");

        if (books.isEmpty()) {
            System.out.println("No books in the catalog.");
            return;
        }

        books.values().forEach(System.out::println);
    }

    public void printAvailableBooksSorted() {
        System.out.println("\n=== Available Books (Sorted by Title) ===");

        List<Book> availableBooks = books.values().stream()
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

    public boolean borrowBook(String title) {
        Book book = findBookByTitle(title);

        if (book == null) {
            return false;
        }

        return book.borrow();
    }

    public boolean returnBook(String title) {
        Book book = findBookByTitle(title);

        if (book == null) {
            return false;
        }

        return book.giveBack();
    }

    public boolean containsBook(String title) {
        return findBookByTitle(title) != null;
    }

    public int getBookCount() {
        return books.size();
    }
}