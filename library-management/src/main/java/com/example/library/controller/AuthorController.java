package com.example.library.controller;

import com.example.library.dto.AuthorCreateRequest;
import com.example.library.dto.AuthorDto;
import com.example.library.dto.AuthorAfterCreationResponse;
import com.example.library.model.Author;
import com.example.library.service.AuthorServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorServiceImpl authorServiceImpl;

    @GetMapping
    public List<AuthorDto> getAllAuthors() {
        return authorServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> findById(@PathVariable UUID id) {
        return authorServiceImpl.findById(id);
    }

    @PostMapping
    public ResponseEntity<AuthorAfterCreationResponse> createAuthor(@RequestBody AuthorCreateRequest request) {
        return authorServiceImpl.createAuthor();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable UUID id, @RequestBody Author author) {
        return authorServiceImpl.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable UUID id) {
        return authorServiceImpl.deleteAuthor(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuthorDto> partiallyUpdateAuthor(@PathVariable UUID id,
            @RequestBody Map<String, Object> updates) {
        return authorServiceImpl.partiallyUpdateAuthor(id, updates);
    }
}