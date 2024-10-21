package cen3024c;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    private Library library;
    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    public void setUp() {
        library = new Library();
        book1 = new Book(1, "Twilight", "Stephanie Meyer");
        book2 = new Book(2, "New Moon", "Stephanie Meyer");
        book3 = new Book(3, "Eclipse", "Stephanie Meyer");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
    }

    @Test
    public void testAddBook() {
        Book newBook = new Book(4, "Breaking Dawn", "Stephanie Meyer");
        library.addBook(newBook);

        library.printBooks();
        assertTrue(library.removeBookById(4), "Book was not added successfully.");
    }

    @Test
    public void testRemoveBookById() {
        assertTrue(library.removeBookById(1), "Book was not removed by ID successfully.");
        assertFalse(library.removeBookById(5), "Book with non-existing ID should not be removed.");
    }

    @Test
    public void testRemoveBookByTitle() {
        assertTrue(library.removeBookByTitle("New Moon"), "Book was not removed by title successfully.");
        assertFalse(library.removeBookByTitle("Non-Existent Title"), "Non-existing book should not be removed.");
    }

    @Test
    public void testCheckOutBook() {
        assertTrue(library.checkOutBook("Eclipse"), "Book was not checked out successfully.");
        assertTrue(book3.isCheckedOut(), "Book status could not be updated to checked out.");
        assertFalse(library.checkOutBook("Eclipse"), "This book is already checked out.");
    }

    @Test
    public void testCheckInBook() {
        library.checkOutBook("Eclipse");
        assertTrue(library.checkInBook("Eclipse"), "Book was not checked in successfully.");
        assertFalse(book3.isCheckedOut(), "Book status could not be updated to available.");
        assertFalse(library.checkInBook("Non-Existent Title"), "Non-existing book should not be checked in.");
    }

    @Test
    public void testPrintBooks() {
        library.printBooks();
        library.removeBookById(1);
        library.printBooks();
    }
}

