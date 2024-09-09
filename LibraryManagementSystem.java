package cen3024c;
/**
 * Andres Vega
 * CEN 3024 - Software Development 1
 * September 8, 2024
 * LibraryManagementSystem.java
 * This class contains the main method and provides the user menu requiring input to initiate a task.
 */

import java.util.Scanner;

public class LibraryManagementSystem {
    private static Library library = new Library();

    /**
     * method: main
     * parameters: none
     * return: n/a
     * purpose: Initiates method for the chosen option from the menu.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while(!exit) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1:
                    System.out.println("Enter filename to add books: ");
                    String filename = scanner.nextLine();
                    library.addBooksFromFile(filename);
                    break;
                case 2:
                    System.out.println("Enter the ID of the book to remove from the library: ");
                    int id = scanner.nextInt();
                    library.removeBook(id);
                    break;
                case 3:
                    System.out.println("Books currently available in the library: ");
                    library.listBooks();
                    break;
                case 4:
                    System.out.println("Closing system.");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid Entry...");
            }
        }

        scanner.close();
    }

    /**
     * method: displayMenu
     * parameters: none
     * return: n/a
     * purpose: Displays menu in the console.
     */
    private static void displayMenu() {
        System.out.println("Welcome to the Library");
        System.out.println("1.Add books from a file");
        System.out.println("2.Remove a book using ID");
        System.out.println("3.List all books in the collection");
        System.out.println("4.Exit");
        System.out.println("Choose an option from the menu: ");
    }
}
