package com.example.library.service;

import com.example.library.model.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {
    List<Book> findAll();

    Book findById(UUID id);

    Book save(Book book);

    void deleteById(UUID id);
}
