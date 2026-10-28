import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

public class Library {

    private final Map<String, Book> books = new HashMap<>();

    // Reservation queue for each book.
    private final Map<String, Queue<Member>> reservationWaitlists =
            new HashMap<>();

    // Stack containing recently returned books.
    private final Deque<Book> recentlyReturnedBooks = new ArrayDeque<>();

    public void addBook(Book book) {
        if (books.containsKey(book.getIsbn())) {
            System.out.println("A book with this ISBN already exists.");
            return;
        }

        books.put(book.getIsbn(), book);
        reservationWaitlists.put(book.getIsbn(), new ArrayDeque<>());
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

        Queue<Member> waitlist = reservationWaitlists.get(book.getIsbn());

        // Do not bypass members waiting for the book.
        if (waitlist != null && !waitlist.isEmpty()) {
            return false;
        }

        return book.borrow();
    }

    public boolean returnBook(String title) {
        Book book = findBookByTitle(title);

        if (book == null || book.isAvailable()) {
            return false;
        }

        if (!book.giveBack()) {
            return false;
        }

        // Push the returned book onto the top of the stack.
        recentlyReturnedBooks.push(book);

        return true;
    }

    public boolean reserveBook(String isbn, Member member) {
        Book book = books.get(isbn);

        if (book == null || member == null) {
            System.out.println("Book or member not found.");
            return false;
        }

        Queue<Member> waitlist = reservationWaitlists.get(isbn);

        if (book.isAvailable() && waitlist.isEmpty()) {
            System.out.println(
                    "This book is available. You can borrow it directly."
            );
            return false;
        }

        boolean alreadyWaiting = waitlist.stream()
                .anyMatch(existing ->
                        existing.getMemberId() == member.getMemberId());

        if (alreadyWaiting) {
            System.out.println(
                    member.getName() + " is already on this waitlist."
            );
            return false;
        }

        waitlist.offer(member);

        System.out.println(
                member.getName() + " has been added to the waitlist for "
                        + book.getTitle() + "."
        );

        return true;
    }

    public Member getNextReservation(String isbn) {
        Queue<Member> waitlist = reservationWaitlists.get(isbn);

        if (waitlist == null) {
            return null;
        }

        return waitlist.peek();
    }

    public Member serveNextReservation(String isbn) {
        Queue<Member> waitlist = reservationWaitlists.get(isbn);

        if (waitlist == null) {
            return null;
        }

        return waitlist.poll();
    }

    public void printReservationWaitlist(String isbn) {
        Book book = books.get(isbn);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        Queue<Member> waitlist = reservationWaitlists.get(isbn);

        System.out.println(
                "\n=== Reservation Waitlist: " + book.getTitle() + " ==="
        );

        if (waitlist == null || waitlist.isEmpty()) {
            System.out.println("No members are waiting for this book.");
            return;
        }

        int position = 1;

        for (Member member : waitlist) {
            System.out.println(
                    position + ". " + member.getName()
                            + " (Member ID: " + member.getMemberId() + ")"
            );
            position++;
        }
    }

    public void printRecentlyReturnedBooks() {
        System.out.println("\n=== Recently Returned Books ===");

        if (recentlyReturnedBooks.isEmpty()) {
            System.out.println("No books have been returned during this session.");
            return;
        }

        int position = 1;

        for (Book book : recentlyReturnedBooks) {
            System.out.println(position + ". " + book);
            position++;
        }
    }

    public Book getMostRecentlyReturnedBook() {
        return recentlyReturnedBooks.peek();
    }

    public Book removeMostRecentlyReturnedBook() {
        return recentlyReturnedBooks.poll();
    }

    public boolean containsBook(String title) {
        return findBookByTitle(title) != null;
    }

    public int getBookCount() {
        return books.size();
    }
}