package com.example.library.service;

import com.example.library.dto.AuthorAfterCreationResponse;
import com.example.library.dto.AuthorDto;
import com.example.library.mapper.AuthorMapper;
import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private static final AuthorMapper authorMapper = AuthorMapper.INSTANCE;
    private static final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::authorToAuthorDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<AuthorDto> findById(UUID id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(value -> ResponseEntity.ok(authorMapper.authorToAuthorDto(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<AuthorAfterCreationResponse> createAuthor() {
        Author author = authorMapper
                .authorDtoToAuthor(new AuthorDto());
        Author saved = authorRepository.save(author);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthorAfterCreationResponse(saved.getName(), saved.getSurname()));
    }

    public ResponseEntity<AuthorDto> updateAuthor(UUID id, Author author2) {
        return authorRepository.findById(id)
                .map(author -> {
                    author.setName(author2.getName());
                    author.setSurname(author2.getSurname());
                    authorRepository.save(author);
                    return ResponseEntity.ok(authorMapper.authorToAuthorDto(author));
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

    public ResponseEntity<AuthorDto> partiallyUpdateAuthor(UUID id, Map<String, Object> updates) {
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
                default:
                    logger.warn("Unknown field: '{}' has been ignored.", key);
            }
        });
        authorRepository.save(author);
        return ResponseEntity.ok(authorMapper.authorToAuthorDto(author));
    }

    private void updateDateOfBirth(Author author, Object value) {
        if (value instanceof String) {
            author.setDateOfBirth(LocalDate.parse((String) value));
        } else {
            logger.error("Invalid type for 'dateOfBirth' field, expected a string.");
        }
    }
}
