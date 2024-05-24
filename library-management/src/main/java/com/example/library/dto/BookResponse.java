package com.example.library.dto;

import lombok.Data;
import java.util.List;

@Data
public class BookResponse {
    private String title;
    private AuthorDto author;
    private List<AuthorDto> coAuthors;
    private String isbn;
    private int publicationYear;
    private String publisher;
    private int numberOfPages;
}
