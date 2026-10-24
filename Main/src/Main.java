import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static Scanner in = new Scanner(System.in);

    static String[] bookNames = {
            "Things Fall Apart", "Clean Code", "The Last Will", "Fences"
    };

    static String[] authors = {
            "Chinua Achebe", "Robert Martin", "Ebrima Ceesay", "August Wilson"
    };

    static int[] bookIds = {101, 102, 103, 104};

    static boolean[] available = {true, false, true, true};


    public static void main(String[] args) {

        addBook();

        printCatalog();

        searchBook();

        toggleBookStatus();

        printCatalog();
    }


    // Add new books to the catalog
    public static void addBook() {

        while (true) {

            System.out.println("Are you adding a book? Y or N");
            String userInput = in.nextLine();

            if (userInput.equalsIgnoreCase("n")) {
                break;
            }

            if (!userInput.equalsIgnoreCase("y")) {
                System.out.println("Please enter Y or N.");
                continue;
            }

            System.out.println("Enter book name:");
            String name = in.nextLine();

            System.out.println("Enter author name:");
            String author = in.nextLine();

            System.out.println("Enter book ID:");
            int id = in.nextInt();
            in.nextLine();

            System.out.println("Is the book available? true or false:");
            boolean status = in.nextBoolean();
            in.nextLine();

            bookNames = Arrays.copyOf(bookNames, bookNames.length + 1);
            bookNames[bookNames.length - 1] = name;

            authors = Arrays.copyOf(authors, authors.length + 1);
            authors[authors.length - 1] = author;

            bookIds = Arrays.copyOf(bookIds, bookIds.length + 1);
            bookIds[bookIds.length - 1] = id;

            available = Arrays.copyOf(available, available.length + 1);
            available[available.length - 1] = status;

            System.out.println("Book added successfully!");
        }
    }


    // Display one book
    public static void printBook(int index) {

        String status;

        if (available[index]) {
            status = "Available";
        } else {
            status = "Not Available";
        }

        System.out.println(
                bookIds[index] + " - "
                        + bookNames[index] + " - "
                        + authors[index] + " - "
                        + status
        );
    }


    // Display the full book catalog
    public static void printCatalog() {

        System.out.println("\n=== Full Book Catalog ===");

        for (int i = 0; i < bookNames.length; i++) {
            printBook(i);
        }
    }


    // Search for a book by title
    public static void searchBook() {

        System.out.println("\nEnter the book title to search:");
        String searchTerm = in.nextLine();

        boolean found = false;

        for (int i = 0; i < bookNames.length; i++) {

            if (bookNames[i].equalsIgnoreCase(searchTerm)) {
                printBook(i);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println(searchTerm + " not found.");
        }
    }


    // Check out or return a book
    public static void toggleBookStatus() {

        System.out.println("\nEnter the book title to checkout/return:");
        String bookTitle = in.nextLine();

        boolean found = false;

        for (int i = 0; i < bookNames.length; i++) {

            if (bookNames[i].equalsIgnoreCase(bookTitle)) {

                if (available[i]) {
                    available[i] = false;
                    System.out.println(bookNames[i] + " has been checked out.");
                } else {
                    available[i] = true;
                    System.out.println(bookNames[i] + " has been returned.");
                }

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println(bookTitle + " not found.");
        }
    }
}