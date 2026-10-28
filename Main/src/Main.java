import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final Library library = new Library();
    private static final Map<String, Runnable> menuActions = new HashMap<>();
    private static final Map<Integer, Member> members = new HashMap<>();

    public static void main(String[] args) {
        initializeBooks();
        initializeMembers();
        displayMembers();
        setupMenu();
        runMenu();
        in.close();
    }

    private static void initializeBooks() {
        library.addBook(new Book(
                "978-0435905255",
                "Things Fall Apart",
                "Chinua Achebe",
                true
        ));

        library.addBook(new Book(
                "978-0132350884",
                "Clean Code",
                "Robert Martin",
                false
        ));

        library.addBook(new Book(
                "978-0000000001",
                "The Last Will",
                "Ebrima Ceesay",
                true
        ));

        library.addBook(new Book(
                "978-0802130301",
                "Fences",
                "August Wilson",
                true
        ));
    }

    private static void initializeMembers() {
        members.put(201, new Student(201, "Ebrima Saine"));
        members.put(202, new Faculty(202, "Mr. Johnson"));
    }

    private static void displayMembers() {
        System.out.println("\n=== Library Members ===");

        for (Member member : members.values()) {
            System.out.println(member);
        }
    }

    private static void setupMenu() {
        menuActions.put("1", Main::addBook);
        menuActions.put("2", library::printCatalog);
        menuActions.put("3", Main::searchBook);
        menuActions.put("4", Main::toggleBookStatus);
        menuActions.put("5", library::printAvailableBooksSorted);
        menuActions.put("6", Main::reserveBook);
        menuActions.put("7", Main::viewWaitlist);
        menuActions.put("8", library::printRecentlyReturnedBooks);
    }

    private static void runMenu() {
        while (true) {
            System.out.println("\n===== LibraTrack Library System =====");
            System.out.println("1. Add a Book");
            System.out.println("2. View Full Catalog");
            System.out.println("3. Search for a Book");
            System.out.println("4. Checkout / Return a Book");
            System.out.println("5. View Available Books (Sorted by Title)");
            System.out.println("6. Reserve a Book");
            System.out.println("7. View Reservation Waitlist");
            System.out.println("8. View Recently Returned Books");
            System.out.println("0. Exit");

            System.out.print("Choose an option: ");
            String choice = in.nextLine().trim();

            if (choice.equals("0")) {
                System.out.println("Thank you for using LibraTrack!");
                break;
            }

            Runnable action = menuActions.get(choice);

            if (action != null) {
                action.run();
            } else {
                System.out.println("Invalid option. Please choose from 0 to 8.");
            }
        }
    }

    private static void addBook() {
        System.out.println("\n=== Add a Book ===");

        System.out.print("Enter book ISBN: ");
        String isbn = in.nextLine().trim();

        if (isbn.isEmpty()) {
            System.out.println("ISBN cannot be empty.");
            return;
        }

        if (library.findBookByIsbn(isbn) != null) {
            System.out.println("A book with this ISBN already exists.");
            return;
        }

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

        System.out.print("Is the book available? (yes/no): ");
        String statusInput = in.nextLine().trim();

        boolean available;

        if (statusInput.equalsIgnoreCase("yes")) {
            available = true;
        } else if (statusInput.equalsIgnoreCase("no")) {
            available = false;
        } else {
            System.out.println("Invalid choice. Enter yes or no.");
            return;
        }

        library.addBook(new Book(isbn, title, author, available));
        System.out.println("Book added successfully!");
    }

    private static void searchBook() {
        System.out.print("\nEnter book title, author, or ISBN: ");
        String query = in.nextLine().trim();

        List<Book> results = library.searchBooks(query);

        if (results.isEmpty()) {
            System.out.println("No matching books found.");
            return;
        }

        System.out.println("\n=== Search Results ===");
        results.forEach(System.out::println);
    }

    private static void toggleBookStatus() {
        System.out.print("\nEnter the book title: ");
        String title = in.nextLine().trim();

        Book book = library.findBookByTitle(title);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.isAvailable()) {
            boolean borrowed = library.borrowBook(title);

            if (borrowed) {
                System.out.println(book.getTitle() + " has been checked out.");
            } else {
                System.out.println(
                        "Checkout unsuccessful. A reservation may be pending."
                );
            }
        } else {
            boolean returned = library.returnBook(title);

            if (returned) {
                System.out.println(book.getTitle() + " has been returned.");

                Member nextMember =
                        library.getNextReservation(book.getIsbn());

                if (nextMember != null) {
                    System.out.println(
                            "Next in the reservation queue: "
                                    + nextMember.getName()
                                    + " (Member ID: "
                                    + nextMember.getMemberId() + ")"
                    );
                }
            } else {
                System.out.println("Unable to return this book.");
            }
        }
    }

    private static void reserveBook() {
        System.out.print("\nEnter the ISBN of the book to reserve: ");
        String isbn = in.nextLine().trim();

        Book book = library.findBookByIsbn(isbn);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.print("Enter your member ID: ");
        int memberId;

        try {
            memberId = Integer.parseInt(in.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid member ID. Enter a number.");
            return;
        }

        Member member = members.get(memberId);

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        library.reserveBook(isbn, member);
    }

    private static void viewWaitlist() {
        System.out.print("\nEnter the ISBN of the book: ");
        String isbn = in.nextLine().trim();

        library.printReservationWaitlist(isbn);
    }
}