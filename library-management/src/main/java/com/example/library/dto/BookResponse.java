package com.example.library.dto;

import lombok.Data;

import java.util.List;

import com.example.library.model.Author;

@Data
public class BookResponse {
    private String title;
    private Author author;
    private List<Author> coAuthors;
    private String isbn;
    private int publicationYear;
    private String publisher;
    private int numberOfPages;
}
