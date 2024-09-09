package cen3024c;
/**
 * Andres Vega
 * CEN 3024 - Software Development 1
 * September 8, 2024
 * Book.java
 * This class represents each book individually in the collection.
 */


public class Book {
    private int id;
    private String title;
    private String author;

    //constructor
    public Book(int id, String title, String author) {
        this.id = id;
        this. title = title;
        this.author = author;
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

    //format book information
    public String toString(){
        return id + ", " + title + ", " + author;
    }
}
