import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);
    private static final Library library = new Library();

    private static final Map<String, Runnable> menuActions =
            new HashMap<>();

    private static final Map<Integer, Member> members =
            new HashMap<>();

    public static void main(String[] args) {
        initializeBooks();
        initializeMembers();
        setupMenu();

        boolean running = true;

        while (running) {
            displayMenu();

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
                    System.out.println("Invalid option. Try again.");
                }
            }

            System.out.println();
        }

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

    private static void setupMenu() {
        menuActions.put("1", Main::addBook);
        menuActions.put("2", library::printCatalog);
        menuActions.put("3", Main::searchBook);
        menuActions.put("4", Main::toggleBookStatus);
        menuActions.put("5", library::printAvailableBooksSorted);
        menuActions.put("6", Main::reserveBook);
        menuActions.put("7", Main::viewWaitlist);
        menuActions.put("8", library::printRecentlyReturnedBooks);
        menuActions.put("9", library::printCatalogSortedByTitle);
    }

    private static void displayMenu() {
        System.out.println("========== LibraTrack ==========");
        System.out.println("1. Add a Book");
        System.out.println("2. Display Full Catalog");
        System.out.println("3. Search for a Book");
        System.out.println("4. Borrow or Return a Book");
        System.out.println("5. Display Available Books (A-Z)");
        System.out.println("6. Reserve a Book");
        System.out.println("7. View Reservation Waitlist");
        System.out.println("8. View Recently Returned Books");
        System.out.println("9. Display Catalog Sorted by Title");
        System.out.println("0. Exit");
        System.out.println("================================");
    }

    private static void addBook() {
        System.out.print("Enter ISBN: ");
        String isbn = in.nextLine().trim();

        System.out.print("Enter book title: ");
        String title = in.nextLine().trim();

        System.out.print("Enter author: ");
        String author = in.nextLine().trim();

        System.out.print("Is the book available? (yes/no): ");
        String answer = in.nextLine().trim();

        boolean available =
                answer.equalsIgnoreCase("yes")
                        || answer.equalsIgnoreCase("y");

        Book book = new Book(isbn, title, author, available);

        if (library.addBook(book)) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println(
                    "Book was not added. Check the ISBN and title; "
                            + "duplicates are not allowed."
            );
        }
    }

    private static void searchBook() {
        System.out.print("Enter title, author, or ISBN to search: ");
        String query = in.nextLine().trim();

        List<Book> results = library.searchBooks(query);

        if (results.isEmpty()) {
            System.out.println("No matching books found.");
            return;
        }

        System.out.println("\n===== Search Results =====");

        for (Book book : results) {
            System.out.println(book);
        }
    }

    private static void toggleBookStatus() {
        System.out.print("Enter the book title: ");
        String title = in.nextLine().trim();

        Book book = library.findBookByTitle(title);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.isAvailable()) {
            if (library.borrowBook(title)) {
                System.out.println("Book borrowed successfully.");
            } else {
                System.out.println(
                        "The book could not be borrowed."
                );
            }
        } else {
            if (library.returnBook(title)) {
                System.out.println("Book returned successfully.");

                Member nextMember =
                        library.getNextReservation(book.getIsbn());

                if (nextMember != null) {
                    System.out.println(
                            "Next reservation: "
                                    + nextMember.getName()
                    );
                }
            } else {
                System.out.println(
                        "The book could not be returned."
                );
            }
        }
    }

    private static void reserveBook() {
        System.out.print("Enter the book ISBN: ");
        String isbn = in.nextLine().trim();

        System.out.print("Enter your member ID: ");

        try {
            int memberId = Integer.parseInt(in.nextLine().trim());
            Member member = members.get(memberId);

            if (member == null) {
                System.out.println("Member ID not found.");
                return;
            }

            library.reserveBook(isbn, member);

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid numeric member ID.");
        }
    }

    private static void viewWaitlist() {
        System.out.print("Enter the book ISBN: ");
        String isbn = in.nextLine().trim();

        library.printReservationWaitlist(isbn);
    }
}