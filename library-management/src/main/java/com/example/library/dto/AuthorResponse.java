package com.example.library.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.library.model.Book;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class AuthorResponse {
    private String name;
    private String surname;
    private String nationality;
    private LocalDate dateOfBirth;

    @Lob
    private String biography;

    private List<Book> books;
    private List<Book> coAuthoredBooks;
}
