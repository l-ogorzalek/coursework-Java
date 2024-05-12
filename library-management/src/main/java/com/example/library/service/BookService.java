package com.example.library.service;

import com.example.library.dto.BookResponse;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

public interface BookService {
    List<BookResponse> findAll();

    ResponseEntity<BookResponse> findById(UUID id);
}
