package com.example.library.model;

import org.hibernate.annotations.UuidGenerator;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @UuidGenerator
    private UUID id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany
    @JoinTable(name = "book_coauthors", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> coAuthors;

    private String isbn;
    private int publicationYear;
    private String publisher;
    private int numberOfPages;
    private String language;
    private String genre;
}
