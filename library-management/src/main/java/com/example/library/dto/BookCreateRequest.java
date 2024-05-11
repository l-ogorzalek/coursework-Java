package com.example.library.dto;

import java.util.List;

import com.example.library.model.Author;

import lombok.Data;

@Data
public class BookCreateRequest {
    private String title;
    private Author author;
    private List<Author> coAuthors;
    private String isbn;
    private int publicationYear;
    private String publisher;
    private int numberOfPages;
    private String language;
    private String genre;
}
