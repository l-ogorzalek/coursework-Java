package com.example.library.dto;

import lombok.Data;
import java.util.List;

@Data
public class BookCreateRequest {
    private String title;
    private AuthorDto author;
    private List<AuthorDto> coAuthors;
    private String isbn;
    private int publicationYear;
    private String publisher;
    private int numberOfPages;
    private String language;
    private String genre;
}
