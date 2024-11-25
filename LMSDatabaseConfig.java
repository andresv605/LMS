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
     * Establishes and returns a connection to the MySQL database using credentials.
     * @return Connection the database connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Retrieves all books from the database.
     * @param conn the active database connection
     * @return ResultSet the result set containing all records from the books table
     * @throws SQLException if a database access error occurs
     */
    public static ResultSet getAllBooks(Connection conn) throws SQLException {
        String query = "SELECT * FROM books";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    /**
     * Removes a book from the database with the specified title.
     * @param conn the active database connection
     * @param title the title of the book to be removed
     * @return boolean true if the book was removed successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public static boolean removeBookByTitle(Connection conn, String title) throws SQLException {
        String query = "DELETE FROM books WHERE title = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, title);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    }

    /**
     * Removes a book from the database with the specified barcode.
     * @param conn the active database connection
     * @param barcode the barcode of the book to be removed
     * @return boolean true if the book was removed successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public static boolean removeBookByBarcode(Connection conn, String barcode) throws SQLException {
        String query = "DELETE FROM books WHERE barcode = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, barcode);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    }

    /**
     * Checks out a book by updating its status and setting the due date.
     * @param conn the active database connection
     * @param title the title of the book to be checked out
     * @return boolean true if the book was checked out successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public static boolean checkOutBook(Connection conn, String title) throws SQLException {
        String query = "UPDATE books SET status = 'checked out', due_date = NOW() + INTERVAL 14 DAY WHERE title = ? AND status = 'available'";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, title);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    }

    /**
     * Checks in a book by updating its status and clearing the due date.
     * @param conn the active database connection
     * @param title the title of the book to be checked in
     * @return boolean true if the book was checked in successfully, false otherwise
     * @throws SQLException if a database access error occurs
     */
    public static boolean checkInBook(Connection conn, String title) throws SQLException {
        String query = "UPDATE books SET status = 'available', due_date = NULL WHERE title = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, title);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0;
    }
}

