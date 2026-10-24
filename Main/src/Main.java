import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class Main {

    static Scanner in = new Scanner(System.in);
    static List<Book> books = new ArrayList<>();

    public static void main(String[] args) {

        // Initial book catalog
        books.add(new Book(101, "Things Fall Apart", "Chinua Achebe", true));
        books.add(new Book(102, "Clean Code", "Robert Martin", false));
        books.add(new Book(103, "The Last Will", "Ebrima Ceesay", true));
        books.add(new Book(104, "Fences", "August Wilson", true));

        addBook();
        printCatalog();
        searchBook();
        toggleBookStatus();
        printCatalog();

        in.close();
    }


    // Add new books
    public static void addBook() {

        while (true) {

            System.out.println("\nAre you adding a book? Y or N");
            String answer = in.nextLine().trim();

            if (answer.equalsIgnoreCase("n")) {
                break;
            }

            if (!answer.equalsIgnoreCase("y")) {
                System.out.println("Please enter Y or N.");
                continue;
            }

            System.out.println("Enter book name:");
            String name = in.nextLine().trim();

            System.out.println("Enter author name:");
            String author = in.nextLine().trim();

            int id;

            try {
                System.out.println("Enter book ID:");
                id = Integer.parseInt(in.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid book ID.");
                continue;
            }

            System.out.println("Is the book available? true or false:");
            String statusInput = in.nextLine().trim();

            boolean status;

            if (statusInput.equalsIgnoreCase("true")) {
                status = true;
            } else if (statusInput.equalsIgnoreCase("false")) {
                status = false;
            } else {
                System.out.println("Enter true or false.");
                continue;
            }

            books.add(new Book(id, name, author, status));

            System.out.println("Book added successfully!");
        }
    }


    // Display one book
    public static void printBook(Book book) {

        String status;

        if (book.isAvailable()) {
            status = "Available";
        } else {
            status = "Not Available";
        }

        System.out.println(
                book.getId() + " - "
                        + book.getTitle() + " - "
                        + book.getAuthor() + " - "
                        + status
        );
    }


    // Display the full catalog
    public static void printCatalog() {

        System.out.println("\n=== Full Book Catalog ===");

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


    // Search for a book by title
    public static void searchBook() {

        System.out.println("\nEnter the book title to search:");
        String title = in.nextLine().trim();

        List<Book> results = searchBooks(
                book -> book.getTitle().equalsIgnoreCase(title)
        );

        if (results.isEmpty()) {
            System.out.println(title + " not found.");
        } else {
            System.out.println("Book found:");

            for (Book book : results) {
                printBook(book);
            }
        }
    }


    // Check out or return a book
    public static void toggleBookStatus() {

        System.out.println("\nEnter the book title to checkout/return:");
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


    // Book class
    static class Book {

        private int id;
        private String title;
        private String author;
        private boolean available;

        public Book(int id, String title, String author, boolean available) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.available = available;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }
    }
}