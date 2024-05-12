package com.example.library.service;

import com.example.library.model.Author;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

public interface AuthorService {
    List<Author> findAll();

    ResponseEntity<Author> findById(UUID id);
}
