import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

public class Library {

    private final Map<String, Book> books = new HashMap<>();

    private final TreeMap<String, Book> booksByTitle =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private final Map<String, Queue<Member>> reservationWaitlists =
            new HashMap<>();

    private final Deque<Book> recentlyReturnedBooks =
            new ArrayDeque<>();

    // Add a book to the catalog.
    public boolean addBook(Book book) {
        if (book == null) {
            return false;
        }

        String isbn = book.getIsbn();
        String title = book.getTitle();

        if (isbn == null || title == null
                || isbn.trim().isEmpty()
                || title.trim().isEmpty()) {
            return false;
        }

        if (books.containsKey(isbn)
                || booksByTitle.containsKey(title)) {
            return false;
        }

        books.put(isbn, book);
        booksByTitle.put(title, book);
        reservationWaitlists.put(isbn, new ArrayDeque<>());

        return true;
    }

    // Return all books.
    public List<Book> getBooks() {
        return new ArrayList<>(books.values());
    }

    // Find a book using its ISBN.
    public Book findBookByIsbn(String isbn) {
        return books.get(isbn);
    }

    // Find a book using its title.
    public Book findBookByTitle(String title) {
        return booksByTitle.get(title);
    }

    // Search by title, author, or ISBN.
    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.matches(query)) {
                results.add(book);
            }
        }

        return results;
    }

    // Display the catalog in its normal storage order.
    public void printCatalog() {
        if (books.isEmpty()) {
            System.out.println("The library catalog is empty.");
            return;
        }

        System.out.println("\n===== Library Catalog =====");

        for (Book book : books.values()) {
            System.out.println(book);
        }
    }

    // Display the catalog sorted alphabetically by title.
    public void printCatalogSortedByTitle() {
        if (booksByTitle.isEmpty()) {
            System.out.println("The library catalog is empty.");
            return;
        }

        System.out.println("\n===== Catalog Sorted by Title =====");

        for (Book book : booksByTitle.values()) {
            System.out.println(book);
        }
    }

    // Display available books sorted alphabetically by title.
    public void printAvailableBooksSorted() {
        boolean found = false;

        System.out.println("\n===== Available Books (A-Z) =====");

        for (Book book : booksByTitle.values()) {
            if (book.isAvailable()) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books are currently available.");
        }
    }

    // Borrow a book by title.
    public boolean borrowBook(String title) {
        Book book = findBookByTitle(title);

        if (book == null || !book.isAvailable()) {
            return false;
        }

        Queue<Member> waitlist =
                reservationWaitlists.get(book.getIsbn());

        if (waitlist != null && !waitlist.isEmpty()) {
            System.out.println(
                    "This book has a reservation waitlist. "
                            + "Serve the next reservation first."
            );
            return false;
        }

        return book.borrow();
    }

    // Return a book by title.
    public boolean returnBook(String title) {
        Book book = findBookByTitle(title);

        if (book == null || book.isAvailable()) {
            return false;
        }

        if (book.giveBack()) {
            recentlyReturnedBooks.push(book);
            return true;
        }

        return false;
    }

    // Add a member to a book's reservation waitlist.
    public void reserveBook(String isbn, Member member) {
        Book book = books.get(isbn);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        Queue<Member> waitlist =
                reservationWaitlists.get(isbn);

        if (waitlist == null) {
            waitlist = new ArrayDeque<>();
            reservationWaitlists.put(isbn, waitlist);
        }

        for (Member queuedMember : waitlist) {
            if (queuedMember.getMemberId()
                    == member.getMemberId()) {
                System.out.println(
                        "This member is already on the waitlist."
                );
                return;
            }
        }

        if (book.isAvailable() && waitlist.isEmpty()) {
            System.out.println(
                    "The book is available. It can be borrowed directly."
            );
            return;
        }

        waitlist.offer(member);

        System.out.println(
                member.getName()
                        + " has been added to the reservation waitlist."
        );
    }

    // View the next member waiting for a book.
    public Member getNextReservation(String isbn) {
        Queue<Member> waitlist =
                reservationWaitlists.get(isbn);

        if (waitlist == null) {
            return null;
        }

        return waitlist.peek();
    }

    // Remove and return the next member in the waitlist.
    public Member serveNextReservation(String isbn) {
        Queue<Member> waitlist =
                reservationWaitlists.get(isbn);

        if (waitlist == null) {
            return null;
        }

        return waitlist.poll();
    }

    // Display the reservation waitlist for a book.
    public void printReservationWaitlist(String isbn) {
        Book book = books.get(isbn);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        Queue<Member> waitlist =
                reservationWaitlists.get(isbn);

        System.out.println(
                "\n===== Reservation Waitlist: "
                        + book.getTitle() + " ====="
        );

        if (waitlist == null || waitlist.isEmpty()) {
            System.out.println("No members are waiting.");
            return;
        }

        int position = 1;

        for (Member member : waitlist) {
            System.out.println(
                    position + ". " + member
            );
            position++;
        }
    }

    // Display recently returned books, newest first.
    public void printRecentlyReturnedBooks() {
        System.out.println("\n===== Recently Returned Books =====");

        if (recentlyReturnedBooks.isEmpty()) {
            System.out.println("No books have been returned yet.");
            return;
        }

        for (Book book : recentlyReturnedBooks) {
            System.out.println(book);
        }
    }

    // Get the most recently returned book.
    public Book getMostRecentlyReturnedBook() {
        return recentlyReturnedBooks.peek();
    }

    // Remove the most recently returned book from the history stack.
    public Book removeMostRecentlyReturnedBook() {
        return recentlyReturnedBooks.poll();
    }

    // Check whether a book title exists.
    public boolean containsBook(String title) {
        return booksByTitle.containsKey(title);
    }

    // Return the number of books.
    public int getBookCount() {
        return books.size();
    }

    // Return a copy of the catalog sorted by title.
    public List<Book> getBooksSortedByTitle() {
        return new ArrayList<>(booksByTitle.values());
    }
}