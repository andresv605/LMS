package cen3024c;
/**
 * Andres Vega
 * CEN 3024 - Software Development 1
 * November 17, 2024
 * Book.java
 * This class represents each book individually in the collection.
 */

public class Book {
    private int id;
    private String title;
    private String author;
    private boolean isCheckedOut;

    /**
     * Constructs a Book object with the specified ID, title, and author.
     * By default, the book is not checked out.
     *
     * @param id     the unique identifier for the book
     * @param title  the title of the book
     * @param author the author of the book
     */
    public Book(int id, String title, String author) {
        this.id = id;
        this. title = title;
        this.author = author;
        this.isCheckedOut = false;
    }

    //getters
    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }
    /**
     * @return true if the book is checked out, false otherwise
     */
    public boolean isCheckedOut(){ return isCheckedOut; }

    /**
     * Marks the book as checked out.
     */
    public void checkOut(){
        isCheckedOut = true;
    }

    /**
     * Marks the book as checked in/available.
     */
    public void checkIn(){
        isCheckedOut = false;
    }


    /**
     * Returns a formatted string representation of the book.
     *
     * @return a string containing the book's ID, title, author, and status
     */
    public String toString(){
        String status = isCheckedOut ? "Checked Out" : "Available";
        return String.format("ID: %d, Title: %s, Author: %s, Status: %s", id, title, author, status);
    }
}

