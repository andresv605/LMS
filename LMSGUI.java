package cen3024c;
/**
 * Andres Vega
 * CEN 3024 - Software Development 1
 * November 17, 2024
 * LMSGUI.java
 * This class is the user interface for the Library Management System and provides the user with interactive buttons to perform each action.
 */
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LMSGUI extends JFrame {
    private JTextArea displayArea;

    public LMSGUI() {
        setupGUI();
    }

    /**
     * Sets up the GUI layout, buttons, text areas, and labels for the LMS.
     */
    private void setupGUI() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        JPanel panel = new JPanel(new BorderLayout());


        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        panel.add(scrollPane, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel(new GridLayout(6, 2));


        JButton displayButton = new JButton("Display Books");
        displayButton.addActionListener(e -> updateBookTable());
        buttonPanel.add(displayButton);


        JButton removeByIdButton = new JButton("Remove by Barcode");
        removeByIdButton.addActionListener(e -> removeBookById());
        buttonPanel.add(removeByIdButton);


        JButton removeByTitleButton = new JButton("Remove by Title");
        removeByTitleButton.addActionListener(e -> removeBookByTitle());
        buttonPanel.add(removeByTitleButton);


        JButton checkOutButton = new JButton("Check Out Book");
        checkOutButton.addActionListener(e -> checkOutBook());
        buttonPanel.add(checkOutButton);


        JButton checkInButton = new JButton("Check In Book");
        checkInButton.addActionListener(e -> checkInBook());
        buttonPanel.add(checkInButton);


        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        updateBookTable(); // Initial display of books
    }

    /**
     * Displays all books from the database in the display area.
     */
    private void updateBookTable() {
        displayArea.setText("");
        try (Connection conn = DriverManager.getConnection(LMSDataConnect.DB_URL, LMSDataConnect.DB_USER, LMSDataConnect.DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM books")) {

            StringBuilder builder = new StringBuilder();
            while (rs.next()) {
                builder.append(String.format("Barcode: %d, Title: %s, Author: %s, Genre: %s, Status: %s, Due Date: %s\n",
                        rs.getInt("barcode"), rs.getString("title"), rs.getString("author"),
                        rs.getString("genre"), rs.getString("status"),
                        rs.getString("due_date") != null ? rs.getString("due_date") : "None"));
            }
            displayArea.setText(builder.toString());

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching books: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Removes a book by its barcode from the database.
     */
    private void removeBookById() {
        String barcodeString = JOptionPane.showInputDialog(this, "Enter barcode to remove:");
        if (barcodeString == null) return;

        try {
            int barcode = Integer.parseInt(barcodeString);
            try (Connection conn = DriverManager.getConnection(LMSDataConnect.DB_URL, LMSDataConnect.DB_USER, LMSDataConnect.DB_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement("DELETE FROM books WHERE barcode = ?")) {

                pstmt.setInt(1, barcode);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Book removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                updateBookTable();

            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid barcode number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error removing book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Removes a book by its title from the database.
     */
    private void removeBookByTitle() {
        String title = JOptionPane.showInputDialog(this, "Enter title to remove:");
        if (title == null) return;

        try (Connection conn = DriverManager.getConnection(LMSDataConnect.DB_URL, LMSDataConnect.DB_USER, LMSDataConnect.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM books WHERE title = ?")) {

            pstmt.setString(1, title);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book removed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            updateBookTable();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error removing book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the book's status to "checked out" and sets a due date.
     */
    private void checkOutBook() {
        String title = JOptionPane.showInputDialog(this, "Enter title to check out:");
        if (title == null) return;

        try (Connection conn = DriverManager.getConnection(LMSDataConnect.DB_URL, LMSDataConnect.DB_USER, LMSDataConnect.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE books SET status = 'checked out', due_date = CURDATE() + INTERVAL 14 DAY WHERE title = ? AND status = 'checked in'")) {

            pstmt.setString(1, title);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book checked out successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Book not found or already checked out.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            updateBookTable();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error checking out book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the book's status to "checked in" and clears the due date.
     */
    private void checkInBook() {
        String title = JOptionPane.showInputDialog(this, "Enter title to check in:");
        if (title == null) return;

        try (Connection conn = DriverManager.getConnection(LMSDataConnect.DB_URL, LMSDataConnect.DB_USER, LMSDataConnect.DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE books SET status = 'checked in', due_date = NULL WHERE title = ? AND status = 'checked out'")) {

            pstmt.setString(1, title);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book checked in successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Book not found or already checked in.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            updateBookTable();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error checking in book: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LMSGUI().setVisible(true));
    }
}
