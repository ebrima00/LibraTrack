public class Book implements Searchable, Borrowable {

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

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Implement Searchable
    @Override
    public boolean matches(String query) {
        return title.equalsIgnoreCase(query)
                || author.equalsIgnoreCase(query)
                || String.valueOf(id).equals(query);
    }

    // Implement Borrowable
    @Override
    public boolean borrow() {
        if (!available) {
            return false;
        }

        available = false;
        return true;
    }

    @Override
    public boolean giveBack() {
        if (available) {
            return false;
        }

        available = true;
        return true;
    }

    @Override
    public String toString() {
        String status = available ? "Available" : "Not Available";

        return "Book ID: " + id
                + ", Title: " + title
                + ", Author: " + author
                + ", Status: " + status;
    }
}