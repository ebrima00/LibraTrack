public class Book implements Searchable, Borrowable {

    private String isbn;
    private String title;
    private String author;
    private boolean available;

    public Book(String isbn, String title, String author, boolean available) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = available;
    }

    public String getIsbn() {
        return isbn;
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

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    @Override
    public boolean matches(String query) {
        return title.equalsIgnoreCase(query)
                || author.equalsIgnoreCase(query)
                || isbn.equalsIgnoreCase(query);
    }

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

        return "ISBN: " + isbn
                + ", Title: " + title
                + ", Author: " + author
                + ", Status: " + status;
    }
}