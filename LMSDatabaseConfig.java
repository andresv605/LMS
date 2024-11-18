package cen3024c;
/**
 * Andres Vega
 * CEN 3024 - Software Development 1
 * November 17, 2024
 * LMSDatabaseConfig.java
 * This class will handle database operations for connecting, querying, and updating book records.
 */

import java.sql.*;
import static cen3024c.LMSDataConnect.*;

public class LMSDatabaseConfig {
    /**
     * method: connectToDatabase
     * parameters: none
     * return: Connection, the database connection object
     * purpose: Establishes and returns a connection to the MySQL database using credentials
     */
    public static Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * method: getAllBooks
     * parameters: Connection conn (the active database connection)
     * return: ResultSet, the result set containing all records from the books table
     * purpose: Grabs all the books stored in the database and returns the results for processing
     */
    public static ResultSet getAllBooks(Connection conn) throws SQLException {
        String query = "SELECT * FROM books";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    /**
     * method: removeBookByTitle
     * parameters: Connection conn (the active database connection) and String title
     * return: boolean, true if the book was removed successfully, false otherwise
     * purpose: Removes a book from the database with the title typed by the user
     */
    public static boolean removeBookByTitle(Connection conn, String title) throws SQLException {
        String query = "DELETE FROM books WHERE title = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, title);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    }

    /**
     * method: removeBookByBarcode
     * parameters: Connection conn (the active database connection) and String barcode
     * return: boolean, true if the book was removed successfully, false otherwise
     * purpose: Removes a book from the database with the specified barcode
     */
    public static boolean removeBookByBarcode(Connection conn, String barcode) throws SQLException {
        String query = "DELETE FROM books WHERE barcode = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, barcode);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    }

    /**
     * method: checkOutBook
     * parameters: Connection conn (the active database connection) and String title
     * return: boolean, true if the book was checked out successfully, false otherwise
     * purpose: Updates the book's status to "checked out" and sets the due date to 14 days from the current date
     */
    public static boolean checkOutBook(Connection conn, String title) throws SQLException {
        String query = "UPDATE books SET status = 'checked out', due_date = NOW() + INTERVAL 14 DAY WHERE title = ? AND status = 'available'";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, title);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    }

    /**
     * method: checkInBook
     * parameters: Connection conn (the active database connection) and String title
     * return: boolean, true if the book was checked in successfully, false otherwise
     * purpose: Updates the book's status to "available" and resets the due date to NULL
     */
    public static boolean checkInBook(Connection conn, String title) throws SQLException {
        String query = "UPDATE books SET status = 'available', due_date = NULL WHERE title = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, title);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    }
}

