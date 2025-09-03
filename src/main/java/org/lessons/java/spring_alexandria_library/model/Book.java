package org.lessons.java.spring_alexandria_library.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "ISBN must be not null, not empty or blank")
    @Size(min = 13, max = 13, message = "ISBN must be 13 characters")
    @Column(name = "isbn_code", length = 13, nullable = false)
    private String isbn;

    @NotBlank(message = "Title must be not null, not empty or blank")
    private String title;

    @NotBlank(message = "Author must be not null, not empty or blank")
    private String author;

    @NotBlank(message = "Publisher must be not null, not empty or blank")
    private String publisher;

    @Lob
    private String synopsis;

    @NotNull(message = "Publication Date must be not null, not empty or blank")
    private LocalDate pubblicationDate;

    @NotNull(message = "number of copies must be not null, not empty or blank")
    @Min(value = 0, message = "The number of copies cannot be negative")
    private Integer numberOfCopies;

    @OneToMany(mappedBy = "book")
    // Relazione tra [(ONE) libro][(TO) collegato a][(MANY)0,1 o più prestiti]
    //
    // {
    // Altro metodo invece che modificare la DELETE nel controller (vedi
    // BookController da ln.88 a ln.109) per poter
    // eliminare un libro e tutti i suoi borrowings contestualmente (rimuovere
    // vincolo chiave esterna (FK)):
    // |||--->[@OneToMany(mappedBy = "book", cascade= { CascadeType.REMOVE})]<---|||
    // }
    private List<Borrowing> borrowings;

    @ManyToMany
    @JoinTable(name = "book_category", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    // GETTER e SETTER
    public List<Borrowing> getBorrowings() {
        return this.borrowings;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSynopsis() {
        return this.synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public LocalDate getPubblicationDate() {
        return this.pubblicationDate;
    }

    public void setPubblicationDate(LocalDate pubblicationDate) {
        this.pubblicationDate = pubblicationDate;
    }

    public Integer getNumberOfCopies() {
        return this.numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    // OVERRIDE TOSTRING

    @Override
    public String toString() {
        return String.format("Autore: %s Titolo: %s %s, published by: %s", this.author, this.title, this.isbn,
                this.publisher);
    }

}
