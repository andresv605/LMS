package cen3024c;
/**
 * Andres Vega
 * CEN 3024 - Software Development 1
 * November 17, 2024
 * Library.java
 * This class manages the collection adding books, removing books from ID or title, checking out/in, and listing the books.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Library {
    private List<Book> books;


    public Library(){
        books = new ArrayList<>();
    }

    /**
     * Adds a book to the library collection.
     *
     * @param book the book to add
     */
    public void addBook(Book book){
        books.add(book);
    }

    /**
     * Removes a book with the specified ID from the library.
     *
     * @param id the ID of the book to remove
     * @return true if the book was removed, false otherwise
     */
    public boolean removeBookById(int id){
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId() == id) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
    /**
     * Removes a book with the specified title from the library.
     *
     * @param title the title of the book to remove
     * @return true if the book was removed, false otherwise
     */
    public boolean removeBookByTitle(String title){
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getTitle().equalsIgnoreCase(title)) {
                iterator.remove();
                return true;

            }
        }
        return false;
    }
    /**
     * Checks out a book with the specified title from the library.
     *
     * @param title the title of the book to check out
     * @return true if the book was successfully checked out, false otherwise
     */
    public boolean checkOutBook(String title){
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title.trim()) && !book.isCheckedOut()) {
                book.checkOut();
                return true;
            }
        }
        return false;
    }

    /**
     * Checks in a book with the specified title to the library.
     *
     * @param title the title of the book to check in
     * @return true if the book was successfully checked in, false otherwise
     */
    public boolean checkInBook(String title){
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title.trim()) && book.isCheckedOut()) {
                book.checkIn();
                return true;
            }
        }
        return false;
    }


    /**
     * Prints the list of books in the library.
     * If no books exist, a message indicating that is printed instead.
     */
    public void printBooks(){
        if(books.isEmpty()){
            System.out.println("There are no books in the library.");
        } else{
            System.out.println("The books in the library are: ");
            for (Book book : books){
                System.out.println(book);
            }
        }
    }
}
