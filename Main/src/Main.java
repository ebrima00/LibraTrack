import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        String[] bookNames = {"Things Fall Apart", "Clean Code"};
        String[] authors = {"Chinua Achebe", "Robert Martin"};
        int[] bookIds = {101, 102};
        boolean[] available = {true, false};

            //Add new status to available array
            available = Arrays.copyOf(available, available.length + 1);
            available[available.length - 1] = status;
            System.out.println(Arrays.toString(available));

        } while (true);

        //Print books one-by-one
        for (int i = 0; i < bookNames.length; i++) {
            System.out.println(
                    bookIds[i] + " - "
                            + bookNames[i] + " - "
                            + authors[i] + " - "
                            + available[i]
            );
        }

        in.close();
    }
}