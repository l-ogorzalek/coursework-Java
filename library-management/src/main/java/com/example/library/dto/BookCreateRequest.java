package com.example.library.dto;

import lombok.Data;

@Data
public class BookCreateRequest {
    private String title;
    private String author;
    private String isbn;
    private int publicationYear;
    private String publisher;
    private int numberOfPages;
    private String language;
    private String genre;
}
