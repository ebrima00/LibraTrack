public abstract class Member {

    private int memberId;
    private String name;

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

    // Subclasses must provide their own borrowing limit
    public abstract int getBorrowLimit();

    @Override
    public String toString() {
        return "Member ID: " + memberId
                + ", Name: " + name
                + ", Borrow Limit: " + getBorrowLimit() + " books";
    }
}