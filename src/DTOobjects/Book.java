package DTOobjects;


public class Book {

    // Attributer for Book oprettes
    private long isbn;
    private String title;
    private String edition;
    private String author;

    public Book() {

    }

    //Opretter getters og setters
    public long getISBN() {
        return isbn;
    }

    public void setISBN(long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}

