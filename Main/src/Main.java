import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        String[] bookNames = {"Things Fall Apart", "Clean Code", "The Last Will", "Fences"};
        String[] authors = {"Chinua Achebe", "Robert Martin", "Ebrima Ceesay", "August Wilson"};
        int[] bookIds = {101, 102, 103, 104};
        boolean[] available = {true, false, true, true};

        //Add new book using Scanner
        do {
            System.out.println("Are you adding a book?: Y or N");
            String userInput = in.next();

            if (userInput.equalsIgnoreCase("n")) break;

            System.out.println("Enter book name: ");
            String name = in.next();

            System.out.println("Enter author name: ");
            String author = in.next();

            System.out.println("Enter book ID: ");
            int id = in.nextInt();

            System.out.println("Enter book status: ");
            boolean status = in.nextBoolean();

            System.out.println("Name \t Author \t ID \t Status");
            System.out.println(name + "\t" + author + "\t" + id + "\t" + status);

            //Add new book to booknames array
            bookNames = Arrays.copyOf(bookNames,bookNames.length + 1);
            bookNames[bookNames.length - 1] = name;
            System.out.println(Arrays.toString(bookNames));

            //Add new author to authors array
            authors = Arrays.copyOf(authors, authors.length + 1);
            authors[authors.length - 1] = author;
            System.out.println(Arrays.toString(authors));

            //Add new ID to bookIds array
            bookIds = Arrays.copyOf(bookIds, bookIds.length + 1);
            bookIds[bookIds.length - 1] = id;
            System.out.println(Arrays.toString(bookIds));

            //Add new status to available array
            available = Arrays.copyOf(available, available.length + 1);
            available[available.length - 1] = status;
            System.out.println(Arrays.toString(available));

        } while (true);

        //Print books one-by-one
        for (int i = 0; i < bookNames.length; i++) {
            System.out.println(bookIds[i] + " " + bookNames[i] + " " + authors[i] + " " + available[i]);
        }

        // Print full catalog in a loop
        System.out.println("\n=== Full Book Catalog ===");

        for (int i = 0; i < bookNames.length; i++) {

            String check;

            if (available[i]) {
                check = "Available";
            } else {
                check = "Not Available";
            }

            System.out.println(
                    bookIds[i] + " - "
                            + bookNames[i] + " - "
                            + authors[i] + " - "
                            + check
            );
        }

        // Linear search by book title
        System.out.println("Enter the book title to search: ");
        in.nextLine(); // clear the leftover Enter key
        String searchTerm = in.nextLine();

        boolean found = false;

        for (int i = 0; i < bookNames.length; i++) {

            if (bookNames[i].equalsIgnoreCase(searchTerm)) {

                String check;

                if (available[i]) {
                    check = "Available";
                } else {
                    check = "Not Available";
                }

                System.out.println(
                        bookIds[i] + " - "
                                + bookNames[i] + " - "
                                + authors[i] + " - "
                                + check
                );

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println(searchTerm + " not found");
        }


        // Checkout / Return book
        System.out.println("\nEnter the book title to checkout/return: ");
        String bookTitle = in.nextLine();

        boolean bookFound = false;

        for (int i = 0; i < bookNames.length; i++) {

            if (bookNames[i].equalsIgnoreCase(bookTitle)) {

                if (available[i]) {
                    available[i] = false;
                    System.out.println(bookNames[i] + " has been checked out.");
                } else {
                    available[i] = true;
                    System.out.println(bookNames[i] + " has been returned.");
                }

                bookFound = true;
                break;
            }
        }

        if (!bookFound) {
            System.out.println(bookTitle + " not found.");
        }
    }
}