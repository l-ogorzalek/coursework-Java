package com.example.library.controller;

import com.example.library.dto.AuthorCreateRequest;
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
    public List<Author> getAllAuthors() {
        return authorServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> findById(@PathVariable UUID id) {
        return authorServiceImpl.findById(id);
    }

    @PostMapping
    public ResponseEntity<AuthorAfterCreationResponse> createAuthor(@RequestBody AuthorCreateRequest request) {
        return authorServiceImpl.createAuthor(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable UUID id, @RequestBody Author author) {
        return authorServiceImpl.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable UUID id) {
        return authorServiceImpl.deleteAuthor(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Author> partiallyUpdateAuthor(@PathVariable UUID id,
            @RequestBody Map<String, Object> updates) {
        return authorServiceImpl.partiallyUpdateAuthor(id, updates);
    }
}