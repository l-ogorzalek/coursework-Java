package com.example.library.model;

import org.hibernate.annotations.UuidGenerator;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
    private String author;

    private String isbn;
    private int publicationYear;
    private String publisher;
    private int numberOfPages;
    private String language;
    private String genre;
}

/*

 * TO DO: consider implementing 'co-authors' field in 'Book' entity, which could be (idk?) a list of 'Author' objects,
 * as sticking to "one author per book" rule often doesn't reflect reality.
 */