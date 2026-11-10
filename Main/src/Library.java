import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class Library {

    private final Map<String, Book> books = new HashMap<>();

    private final TreeMap<String, Book> booksByTitle =
            new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private final Map<String, Queue<Member>> reservationWaitlists =
            new HashMap<>();

    private final Deque<Book> recentlyReturnedBooks =
            new ArrayDeque<>();

    public boolean addBook(Book book) {
        if (book == null || book.getIsbn() == null
                || book.getTitle() == null
                || book.getIsbn().trim().isEmpty()
                || book.getTitle().trim().isEmpty()) {
            return false;
        }

        String isbn = book.getIsbn();
        String title = book.getTitle();

        if (books.containsKey(isbn) || booksByTitle.containsKey(title)) {
            return false;
        }

        books.put(isbn, book);
        booksByTitle.put(title, book);
        reservationWaitlists.put(isbn, new ArrayDeque<>());

        return true;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books.values());
    }

    public Book findBookByIsbn(String isbn) {
        return books.get(isbn);
    }

    public Book findBookByTitle(String title) {
        return booksByTitle.get(title);
    }

    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();

        for (Book book : books.values()) {
            if (book.matches(query)) {
                results.add(book);
            }
        }

        return results;
    }

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

    public boolean borrowBook(String title, Member member) {
        Book book = findBookByTitle(title);

        if (book == null || member == null || !book.isAvailable()) {
            return false;
        }

        Queue<Member> waitlist =
                reservationWaitlists.get(book.getIsbn());

        if (waitlist != null && !waitlist.isEmpty()) {
            System.out.println("This book has a reservation waitlist.");
            return false;
        }

        if (book.borrow()) {
            Transaction transaction = new Transaction(
                    book.getIsbn(),
                    book.getTitle(),
                    "BORROWED"
            );

            member.addTransaction(transaction);
            return true;
        }

        return false;
    }

    public boolean returnBook(String title, Member member) {
        Book book = findBookByTitle(title);

        if (book == null || member == null || book.isAvailable()) {
            return false;
        }

        if (book.giveBack()) {
            recentlyReturnedBooks.push(book);

            Transaction transaction = new Transaction(
                    book.getIsbn(),
                    book.getTitle(),
                    "RETURNED"
            );

            member.addTransaction(transaction);
            return true;
        }

        return false;
    }

    public void reserveBook(String isbn, Member member) {
        Book book = books.get(isbn);

        if (book == null || member == null) {
            System.out.println("Book or member not found.");
            return;
        }

        Queue<Member> waitlist =
                reservationWaitlists.get(isbn);

        if (waitlist == null) {
            waitlist = new ArrayDeque<>();
            reservationWaitlists.put(isbn, waitlist);
        }

        for (Member queuedMember : waitlist) {
            if (queuedMember.getMemberId() == member.getMemberId()) {
                System.out.println("Member is already on the waitlist.");
                return;
            }
        }

        if (book.isAvailable() && waitlist.isEmpty()) {
            System.out.println("The book is available for borrowing.");
            return;
        }

        waitlist.offer(member);

        System.out.println(
                member.getName() + " added to the reservation waitlist."
        );
    }

    public Member getNextReservation(String isbn) {
        Queue<Member> waitlist = reservationWaitlists.get(isbn);
        return waitlist == null ? null : waitlist.peek();
    }

    public Member serveNextReservation(String isbn) {
        Queue<Member> waitlist = reservationWaitlists.get(isbn);
        return waitlist == null ? null : waitlist.poll();
    }

    public void printReservationWaitlist(String isbn) {
        Book book = books.get(isbn);

        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        Queue<Member> waitlist = reservationWaitlists.get(isbn);

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
            System.out.println(position + ". " + member);
            position++;
        }
    }

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

    public Book getMostRecentlyReturnedBook() {
        return recentlyReturnedBooks.peek();
    }

    public Book removeMostRecentlyReturnedBook() {
        return recentlyReturnedBooks.poll();
    }

    public boolean containsBook(String title) {
        return booksByTitle.containsKey(title);
    }

    public int getBookCount() {
        return books.size();
    }

    public List<Book> getBooksSortedByTitle() {
        return new ArrayList<>(booksByTitle.values());
    }
}