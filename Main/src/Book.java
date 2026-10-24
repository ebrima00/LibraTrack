public class Book {

    // Private fields: encapsulation
    private int id;
    private String title;
    private String author;
    private boolean available;

    // Constructor
    public Book(int id, String title, String author, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    // Getters
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

    // Setters
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

    // toString method
    @Override
    public String toString() {
        String status = available ? "Available" : "Not Available";

        return "Book ID: " + id
                + ", Title: " + title
                + ", Author: " + author
                + ", Status: " + status;
    }
}