package com.example.library.service;

import com.example.library.dto.AuthorAfterCreationResponse;
import com.example.library.dto.AuthorCreateRequest;
import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public ResponseEntity<Author> findById(UUID id) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            return ResponseEntity.ok(author.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<AuthorAfterCreationResponse> createAuthor(AuthorCreateRequest request) {
        Author author = new Author();
        author.setName(request.getName());
        author.setSurname(request.getSurname());
        Author saved = authorRepository.save(author);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthorAfterCreationResponse(saved.getName(), saved.getSurname()));
    }

    public ResponseEntity<Author> updateAuthor(UUID id, Author updatedAuthor) {
        return authorRepository.findById(id)
                .map(author -> {
                    author.setName(updatedAuthor.getName());
                    author.setSurname(updatedAuthor.getSurname());
                    authorRepository.save(author);
                    return ResponseEntity.ok(author);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<Object> deleteAuthor(UUID id) {
        return authorRepository.findById(id)
                .map(author -> {
                    authorRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<Author> partiallyUpdateAuthor(UUID id, Map<String, Object> updates) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (!authorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Author author = authorOptional.get();
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    author.setName((String) value);
                    break;
                case "surname":
                    author.setSurname((String) value);
                    break;
                case "nationality":
                    author.setNationality((String) value);
                    break;
                case "dateOfBirth":
                    updateDateOfBirth(author, value);
                    break;
                case "biography":
                    author.setBiography((String) value);
                    break;
                case "books":
                    updateBooks(author, value);
                    break;
                case "coAuthoredBooks":
                    updateCoAuthoredBooks(author, value);
                    break;
                default:
                    logger.warn("Unknown field: '{}' has been ignored.", key);
            }
        });
        authorRepository.save(author);
        return ResponseEntity.ok(author);
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
