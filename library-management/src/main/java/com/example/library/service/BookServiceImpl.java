package com.example.library.service;

import com.example.library.dto.BookCreateRequest;
import com.example.library.dto.BookResponse;
import com.example.library.mapper.BookMapper;
import com.example.library.model.Book;
import com.example.library.repository.BookRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private static final BookMapper bookMapper = BookMapper.INSTANCE;
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::bookToBookResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<BookResponse> findById(UUID id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(value -> ResponseEntity.ok(bookMapper.bookToBookResponse(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Transactional
    public ResponseEntity<BookResponse> createBook(BookCreateRequest request) {
        Book newBook = bookMapper.bookCreateRequestToBook(request);
        Book savedBook = bookRepository.save(newBook);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookMapper.bookToBookResponse(savedBook));
    }

    @Transactional
    public ResponseEntity<BookResponse> replaceBook(UUID id, BookCreateRequest bookReplacement) {
        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Book updatedBook = bookMapper.bookCreateRequestToBook(bookReplacement);
        updatedBook.setId(existingBook.getId());
        Book savedBook = bookRepository.save(updatedBook);
        return ResponseEntity.ok(bookMapper.bookToBookResponse(savedBook));
    }

    public ResponseEntity<Object> deleteBook(UUID id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    public ResponseEntity<BookResponse> partiallyUpdateBook(UUID id, Map<String, Object> updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    updatedBook.forEach((key, value) -> {
                        switch (key) {
                            case "title":
                                book.setTitle((String) value);
                                break;
                            case "isbn":
                                book.setIsbn((String) value);
                                break;
                            case "publicationYear":
                                book.setPublicationYear((Integer) value);
                                break;
                            case "publisher":
                                book.setPublisher((String) value);
                                break;
                            case "numberOfPages":
                                book.setNumberOfPages((Integer) value);
                                break;
                            default:
                                logger.warn("Unknown key '{}' has been ignored.", key);
                        }
                    });
                    Book savedBook = bookRepository.save(book);
                    return ResponseEntity.ok(bookMapper.bookToBookResponse(savedBook));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
