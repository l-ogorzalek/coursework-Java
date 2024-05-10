package com.example.library.controller;

import com.example.library.dto.AuthorCreateRequest;
import com.example.library.dto.AuthorAfterCreationResponse;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.service.AuthorService;
import com.example.library.repository.BookRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final BookRepository bookRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable UUID id) {
        Author author = authorService.findById(id);
        return author != null ? ResponseEntity.ok(author) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<AuthorAfterCreationResponse> createAuthor(@RequestBody AuthorCreateRequest author) {
        Author newAuthor = new Author();
        newAuthor.setName(author.getName());
        newAuthor.setSurname(author.getSurname());

        Author savedAuthor = authorService.save(newAuthor);

        AuthorAfterCreationResponse response = new AuthorAfterCreationResponse();
        response.setName(savedAuthor.getName());
        response.setSurname(savedAuthor.getSurname());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable UUID id, @RequestBody Author updatedAuthor) {
        Author existingAuthor = authorService.findById(id);
        if (existingAuthor != null) {
            updatedAuthor.setId(id);
            return ResponseEntity.ok(authorService.save(updatedAuthor));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id) {
        Author author = authorService.findById(id);
        if (author != null) {
            authorService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Author> partiallyUpdateAuthor(@PathVariable UUID id,
            @RequestBody Map<String, Object> updates) {
        Author existingAuthor = authorService.findById(id);
        if (existingAuthor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    existingAuthor.setName((String) value);
                    break;
                case "surname":
                    existingAuthor.setSurname((String) value);
                    break;
                case "nationality":
                    existingAuthor.setNationality((String) value);
                    break;
                case "dateOfBirth":
                    updateDateOfBirth(existingAuthor, value);
                    break;
                case "biography":
                    existingAuthor.setBiography((String) value);
                    break;
                case "books":
                    updateBooks(existingAuthor, value);
                    break;
                case "coAuthoredBooks":
                    updateCoAuthoredBooks(existingAuthor, value);
                    break;
                default:
                    logger.warn("Unknown field: '{}' has been ignored.", key);
            }
        });
        return ResponseEntity.ok(authorService.save(existingAuthor));
    }

    private void updateDateOfBirth(Author author, Object value) {
        if (value instanceof String) {
            try {
                author.setDateOfBirth(LocalDate.parse((String) value));
            } catch (DateTimeParseException e) {
                logger.error("Invalid date format for 'dateOfBirth' field.");
            }
        } else {
            logger.error("Invalid type for 'dateOfBirth' field, expected a string.");
        }
    }

    private void updateBooks(Author author, Object value) {
        if (value instanceof List<?>) {
            List<UUID> bookIds = new ArrayList<>();
            for (Object obj : (List<?>) value) {
                if (obj instanceof String) {
                    bookIds.add(UUID.fromString((String) obj));
                }
            }
            List<Book> books = bookRepository.findAllById(bookIds);
            author.setBooks(books);
        } else {
            logger.error("Invalid type for 'books' field, expected a list.");
        }
    }

    private void updateCoAuthoredBooks(Author author, Object value) {
        if (value instanceof List<?>) {
            List<UUID> coAuthoredIds = new ArrayList<>();
            for (Object obj : (List<?>) value) {
                if (obj instanceof String) {
                    coAuthoredIds.add(UUID.fromString((String) obj));
                }
            }
            List<Book> coAuthoredBooks = bookRepository.findAllById(coAuthoredIds);
            author.setCoAuthoredBooks(coAuthoredBooks);
        } else {
            logger.error("Invalid type for 'coAuthoredBooks' field, expected a list.");
        }
    }
}