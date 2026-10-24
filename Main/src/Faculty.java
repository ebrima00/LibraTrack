public class Faculty extends Member {

    public Faculty(int memberId, String name) {
        super(memberId, name);
    }

    @Override
    public int getBorrowLimit() {
        return 5;
    }

    @Override
    public String toString() {
        return "Faculty - " + super.toString();
    }
}
