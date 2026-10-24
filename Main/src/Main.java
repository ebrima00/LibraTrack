import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    static Scanner in = new Scanner(System.in);
    static List<Book> books = new ArrayList<>();
    static Map<String, Runnable> menuActions = new HashMap<>();

    public static void main(String[] args) {

        // Initial books
        books.add(new Book(101, "Things Fall Apart", "Chinua Achebe", true));
        books.add(new Book(102, "Clean Code", "Robert Martin", false));
        books.add(new Book(103, "The Last Will", "Ebrima Ceesay", true));
        books.add(new Book(104, "Fences", "August Wilson", true));

        // Create library members
        Member student = new Student(201, "Ebrima Saine");
        Member faculty = new Faculty(202, "Mr. Johnson");

        // Display member information
        System.out.println("\n=== Library Members ===");
        System.out.println(student);
        System.out.println(faculty);

        // Set up and run the menu
        setupMenu();
        runMenu();

        in.close();
    }

    // Register menu actions
    public static void setupMenu() {
        menuActions.put("1", Main::addBook);
        menuActions.put("2", Main::printCatalog);
        menuActions.put("3", Main::searchBook);
        menuActions.put("4", Main::toggleBookStatus);
        menuActions.put("5", Main::printAvailableBooksSorted);
    }

    // Display and run the menu
    public static void runMenu() {

        boolean running = true;

        while (running) {
            System.out.println("\n===== LibraTrack Library System =====");
            System.out.println("1. Add a Book");
            System.out.println("2. View Full Catalog");
            System.out.println("3. Search for a Book");
            System.out.println("4. Checkout / Return a Book");
            System.out.println("5. View Available Books (Sorted by Title)");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = in.nextLine().trim();

            if (choice.equals("0")) {
                running = false;
                System.out.println("Thank you for using LibraTrack!");
            } else {
                Runnable action = menuActions.get(choice);

                if (action != null) {
                    action.run();
                } else {
                    System.out.println("Invalid option. Please choose from 0 to 5.");
                }
            }
        }
    }

    // Add a book
    public static void addBook() {

        System.out.println("\n=== Add a Book ===");

        System.out.print("Enter book title: ");
        String title = in.nextLine().trim();

        System.out.print("Enter author name: ");
        String author = in.nextLine().trim();

        System.out.print("Enter book ID: ");
        int id;

        try {
            id = Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid book ID. Please enter a number.");
            return;
        }

        System.out.print("Is the book available? (true/false): ");
        String statusInput = in.nextLine().trim();

        boolean available;

        if (statusInput.equalsIgnoreCase("true")) {
            available = true;
        } else if (statusInput.equalsIgnoreCase("false")) {
            available = false;
        } else {
            System.out.println("Invalid status. Enter true or false.");
            return;
        }

        books.add(new Book(id, title, author, available));

        System.out.println("Book added successfully!");
    }

    // Display one book
    public static void printBook(Book book) {
        System.out.println(book);
    }

    // Display all books
    public static void printCatalog() {

        System.out.println("\n=== Full Book Catalog ===");

        if (books.isEmpty()) {
            System.out.println("No books in the catalog.");
            return;
        }

        for (Book book : books) {
            printBook(book);
        }
    }

    // Generic search using Predicate<Book>
    public static List<Book> searchBooks(Predicate<Book> condition) {

        List<Book> results = new ArrayList<>();

        for (Book book : books) {
            if (condition.test(book)) {
                results.add(book);
            }
        }

        return results;
    }

    // Search by title
    public static void searchBook() {

        System.out.print("\nEnter the book title to search: ");
        String title = in.nextLine().trim();

        List<Book> results = searchBooks(
                book -> book.getTitle().equalsIgnoreCase(title)
        );

        if (results.isEmpty()) {
            System.out.println(title + " not found.");
        } else {
            System.out.println("\n=== Search Results ===");

            for (Book book : results) {
                printBook(book);
            }
        }
    }

    // Checkout or return a book
    public static void toggleBookStatus() {

        System.out.print("\nEnter the book title to checkout/return: ");
        String title = in.nextLine().trim();

        List<Book> results = searchBooks(
                book -> book.getTitle().equalsIgnoreCase(title)
        );

        if (results.isEmpty()) {
            System.out.println(title + " not found.");
            return;
        }

        Book book = results.get(0);

        if (book.isAvailable()) {
            book.setAvailable(false);
            System.out.println(book.getTitle() + " has been checked out.");
        } else {
            book.setAvailable(true);
            System.out.println(book.getTitle() + " has been returned.");
        }
    }

    // Filter available books and sort by title using Streams
    public static void printAvailableBooksSorted() {

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

        availableBooks.forEach(Main::printBook);
    }
}