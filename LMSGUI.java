package cen3024c;
/**
 * Andres Vega
 * CEN 3024 - Software Development 1
 * November 3, 2024
 * LMSGUI.java
 * This class is the user interface for the Library Management System and provides the user with interactive buttons to perform each action.
 */
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class LMSGUI extends JFrame{
    private Library library;
    private JTextArea displayArea;

    public LMSGUI() {
        library = new Library();
        setupGUI();
    }
    /**
     * method: setupGUI
     * parameters: none
     * return: n/a
     * purpose: Sets up the GUI layout, buttons, text areas,
     *          and labels for the LMS.
     */
    private void setupGUI() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //layout setup
        JPanel panel = new JPanel(new BorderLayout());

        //text area for displaying books
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        //buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(6, 2));

        //load books from file button
        JButton loadButton = new JButton("Load Books from File");
        loadButton.addActionListener(e -> loadBooksFromFile());
        buttonPanel.add(loadButton);

        //print books to display area
        JButton printButton = new JButton("Display Books");
        printButton.addActionListener(e -> updateBookTable());
        buttonPanel.add(printButton);

        //remove by ID button
        JButton removeByIdButton = new JButton("Remove by Barcode");
        removeByIdButton.addActionListener(e -> removeBookById());
        buttonPanel.add(removeByIdButton);

        //remove by title button
        JButton removeByTitleButton = new JButton("Remove by Title");
        removeByTitleButton.addActionListener(e -> removeBookByTitle());
        buttonPanel.add(removeByTitleButton);

        //check out book button
        JButton checkOutButton = new JButton("Check Out Book");
        checkOutButton.addActionListener(e -> checkOutBook());
        buttonPanel.add(checkOutButton);

        //check in book button
        JButton checkInButton = new JButton("Check In Book");
        checkInButton.addActionListener(e -> checkInBook());
        buttonPanel.add(checkInButton);

        //exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);
    }
    /**
     * method: updateBookTable
     * parameters: none
     * return: n/a
     * purpose: Updates the display area with the current list of books in the library.
     */
    private void updateBookTable() {
        displayArea.setText("");

        //redirect System.out to capture printBooks() output
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        //call the printBooks method
        library.printBooks();

        //reset System.out and update display
        System.setOut(originalOut);
        displayArea.setText(outContent.toString());
    }
    /**
     * method: loadBooksFromFile
     * parameters: none
     * return: n/a
     * purpose: Prompts the user for a file name, reads the file, and adds each book
     *          from the file to the library. Displays an error if the file is not found.
     */
    private void loadBooksFromFile() {
        String filename = JOptionPane.showInputDialog(this, "Enter file name:");
        if (filename == null) return;

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0].trim());
                    String title = parts[1].trim();
                    String author = parts[2].trim();
                    library.addBook(new Book(id, title, author));
                }
            }
            JOptionPane.showMessageDialog(this, "Books loaded successfully.");
            updateBookTable();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "File not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * method: removeBookById
     * parameters: none
     * return: n/a
     * purpose: Prompts the user for a book ID, removes the book with that ID from the
     *          library, and displays a confirmation or error message based on the result.
     */
    private void removeBookById() {
        String idString = JOptionPane.showInputDialog(this, "Enter barcode to remove:");
        if (idString == null) return;

        try {
            int id = Integer.parseInt(idString);
            boolean removed = library.removeBookById(id);
            if (removed) {
                JOptionPane.showMessageDialog(this, "Book removed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            updateBookTable();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid barcode number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * method: removeBookByTitle
     * parameters: none
     * return: n/a
     * purpose: Prompts the user for a book title, removes the book with that title from
     *          the library, and displays a confirmation or error message based on the result.
     */
    private void removeBookByTitle() {
        String title = JOptionPane.showInputDialog(this, "Enter book title to remove:");
        if (title == null) return;

        boolean removed = library.removeBookByTitle(title);
        if (removed) {
            JOptionPane.showMessageDialog(this, "Book removed successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateBookTable();
    }
    /**
     * method: checkOutBook
     * parameters: none
     * return: n/a
     * purpose: Prompts the user for a book title to check out, marks the book as checked out,
     *          and displays a confirmation or error message based on the result.
     */
    private void checkOutBook() {
        String title = JOptionPane.showInputDialog(this, "Enter book title to check out:");
        if (title == null) return;

        boolean checkedOut = library.checkOutBook(title);
        if (checkedOut) {
            JOptionPane.showMessageDialog(this, "Book checked out successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Book not available or already checked out.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateBookTable();
    }
    /**
     * method: checkInBook
     * parameters: none
     * return: n/a
     * purpose: Prompts the user for a book title to check in, marks the book as checked in,
     *          and displays a confirmation or error message based on the result.
     */
    private void checkInBook() {
        String title = JOptionPane.showInputDialog(this, "Enter book title to check in:");
        if (title == null) return;

        boolean checkedIn = library.checkInBook(title);
        if (checkedIn) {
            JOptionPane.showMessageDialog(this, "Book checked in successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Book not found or already checked in.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateBookTable();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LMSGUI().setVisible(true));
    }
}

