package com.example.library.service;

import com.example.library.model.Author;
import java.util.List;
import java.util.UUID;

public interface AuthorService {
    List<Author> findAll();

    Author findById(UUID id);

    Author save(Author author);

    void deleteById(UUID id);
}
