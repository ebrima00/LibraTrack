import java.util.LinkedList;
import java.util.List;

public abstract class Member {

    private int memberId;
    private String name;

    private final LinkedList<Transaction> borrowHistory =
            new LinkedList<>();

    public Member(int memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public abstract int getBorrowLimit();

    // Add a transaction to the member's history.
    public void addTransaction(Transaction transaction) {
        borrowHistory.add(transaction);
    }

    // Return the member's transaction history.
    public List<Transaction> getBorrowHistory() {
        return new LinkedList<>(borrowHistory);
    }

    // Display the member's transaction history.
    public void printBorrowHistory() {
        System.out.println("\n===== Borrow History: " + name + " =====");

        if (borrowHistory.isEmpty()) {
            System.out.println("No transaction history found.");
            return;
        }

        for (Transaction transaction : borrowHistory) {
            System.out.println(transaction);
        }
    }

    @Override
    public String toString() {
        return "Member ID: " + memberId
                + ", Name: " + name
                + ", Borrow Limit: " + getBorrowLimit() + " books";
    }
}