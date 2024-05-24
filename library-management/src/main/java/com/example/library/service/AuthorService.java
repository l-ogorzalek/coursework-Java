package com.example.library.service;

import com.example.library.dto.AuthorDto;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

public interface AuthorService {
    List<AuthorDto> findAll();

    ResponseEntity<AuthorDto> findById(UUID id);
}
