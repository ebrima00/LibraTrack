import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;

public class Main {

    static Scanner in = new Scanner(System.in);
    static Library library = new Library();
    static Map<String, Runnable> menuActions = new HashMap<>();

    public static void main(String[] args) {

        // Add initial books to the library
        library.addBook(
                new Book(101, "Things Fall Apart", "Chinua Achebe", true)
        );

        library.addBook(
                new Book(102, "Clean Code", "Robert Martin", false)
        );

        library.addBook(
                new Book(103, "The Last Will", "Ebrima Ceesay", true)
        );

        library.addBook(
                new Book(104, "Fences", "August Wilson", true)
        );

        // Create library members
        Member student = new Student(201, "Ebrima Saine");
        Member faculty = new Faculty(202, "Mr. Johnson");

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
        menuActions.put("2", library::printCatalog);
        menuActions.put("3", Main::searchBook);
        menuActions.put("4", Main::toggleBookStatus);
        menuActions.put("5", library::printAvailableBooksSorted);
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

        if (title.isEmpty()) {
            System.out.println("Book title cannot be empty.");
            return;
        }

        System.out.print("Enter author name: ");
        String author = in.nextLine().trim();

        if (author.isEmpty()) {
            System.out.println("Author name cannot be empty.");
            return;
        }

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

        library.addBook(new Book(id, title, author, available));

        System.out.println("Book added successfully!");
    }

    // Search for a book
    public static void searchBook() {

        System.out.print("\nEnter the book title to search: ");
        String title = in.nextLine().trim();

        Predicate<Book> condition =
                book -> book.getTitle().equalsIgnoreCase(title);

        List<Book> results = library.searchBooks(condition);

        if (results.isEmpty()) {
            System.out.println(title + " not found.");
        } else {
            System.out.println("\n=== Search Results ===");
            results.forEach(System.out::println);
        }
    }

    // Checkout or return a book
    public static void toggleBookStatus() {

        System.out.print("\nEnter the book title to checkout/return: ");
        String title = in.nextLine().trim();

        boolean found = library.containsBook(title);

        if (!found) {
            System.out.println(title + " not found.");
            return;
        }

        Book book = library.findBookByTitle(title);

        boolean wasAvailable = book.isAvailable();

        library.toggleBookStatus(title);

        if (wasAvailable) {
            System.out.println(book.getTitle() + " has been checked out.");
        } else {
            System.out.println(book.getTitle() + " has been returned.");
        }
    }
}