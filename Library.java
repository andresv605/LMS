package cen3024c;
/**
 * Andres Vega
 * CEN 3024 - Software Development 1
 * September 8, 2024
 * Library.java
 * This class manages the collection adding books from a file, removing books, and listing the books.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;

    //constructor
    public Library(){
        books = new ArrayList<>();
    }

    /**
     * method: addBooksFromFile
     * parameters: String filename
     * return: n/a
     * purpose: Reads text file and adds book to the collection
     */
    public void addBooksFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;
            while ((line = br.readLine()) !=null) {
                String[] details = line.split(",");
                if(details.length == 3) {
                    int id = Integer.parseInt(details[0].trim());
                    String title = details[1].trim();
                    String author = details[2].trim();
                    books.add(new Book(id,title, author));
                }
            }
            System.out.println("Books added successfully from " + filename);
        } catch (IOException e) {
            System.out.println("Unable to read file: " + e.getMessage());
        }
    }

    /**
     * method: removeBook
     * parameters: int id
     * return: n/a
     * purpose: Removes book with matching ID
     */
    public void removeBook(int id) {
        books.removeIf(book -> book.getId() == id);
        System.out.println("Book with the ID " + id + " has been removed.");
    }

    /**
     * method: listBooks
     * parameters: none
     * return: n/a
     * purpose: Lists books in the collection
     */
    public void listBooks(){
        if (books.isEmpty()) {
            System.out.println("There are no books in the library.");
        } else {
            for (Book book : books){
                System.out.println(book);
            }
        }
    }
}
