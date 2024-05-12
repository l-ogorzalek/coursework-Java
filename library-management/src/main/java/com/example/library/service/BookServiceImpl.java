package com.example.library.service;

import com.example.library.dto.BookCreateRequest;
import com.example.library.dto.BookResponse;
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
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream()
                .map(book -> {
                    BookResponse response = new BookResponse();
                    response.setTitle(book.getTitle());
                    response.setAuthor(book.getAuthor());
                    response.setIsbn(book.getIsbn());
                    response.setPublicationYear(book.getPublicationYear());
                    response.setPublisher(book.getPublisher());
                    response.setNumberOfPages(book.getNumberOfPages());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<BookResponse> findById(UUID id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            BookResponse response = new BookResponse();
            response.setTitle(book.get().getTitle());
            response.setAuthor(book.get().getAuthor());
            response.setIsbn(book.get().getIsbn());
            response.setPublicationYear(book.get().getPublicationYear());
            response.setPublisher(book.get().getPublisher());
            response.setNumberOfPages(book.get().getNumberOfPages());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    public ResponseEntity<BookResponse> createBook(BookCreateRequest request) {
        Book newBook = new Book();
        newBook.setTitle(request.getTitle());
        newBook.setAuthor(request.getAuthor());
        newBook.setCoAuthors(request.getCoAuthors());
        newBook.setIsbn(request.getIsbn());
        newBook.setPublicationYear(request.getPublicationYear());
        newBook.setPublisher(request.getPublisher());
        newBook.setNumberOfPages(request.getNumberOfPages());
        newBook.setLanguage(request.getLanguage());
        newBook.setGenre(request.getGenre());
        Book savedBook = bookRepository.save(newBook);

        BookResponse response = new BookResponse();
        response.setTitle(savedBook.getTitle());
        response.setAuthor(savedBook.getAuthor());
        response.setCoAuthors(savedBook.getCoAuthors());
        response.setIsbn(savedBook.getIsbn());
        response.setPublicationYear(savedBook.getPublicationYear());
        response.setPublisher(savedBook.getPublisher());
        response.setNumberOfPages(savedBook.getNumberOfPages());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @Transactional
    public ResponseEntity<BookResponse> replaceBook(UUID id, BookCreateRequest bookReplacement) {
        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        existingBook.setTitle(bookReplacement.getTitle());
        existingBook.setAuthor(bookReplacement.getAuthor());
        existingBook.setCoAuthors(bookReplacement.getCoAuthors());
        existingBook.setIsbn(bookReplacement.getIsbn());
        existingBook.setPublicationYear(bookReplacement.getPublicationYear());
        existingBook.setPublisher(bookReplacement.getPublisher());
        existingBook.setNumberOfPages(bookReplacement.getNumberOfPages());
        existingBook.setLanguage(bookReplacement.getLanguage());
        existingBook.setGenre(bookReplacement.getGenre());
        Book savedBook = bookRepository.save(existingBook);

        BookResponse response = new BookResponse();
        response.setTitle(savedBook.getTitle());
        response.setAuthor(savedBook.getAuthor());
        response.setCoAuthors(savedBook.getCoAuthors());
        response.setIsbn(savedBook.getIsbn());
        response.setPublicationYear(savedBook.getPublicationYear());
        response.setPublisher(savedBook.getPublisher());
        response.setNumberOfPages(savedBook.getNumberOfPages());

        return ResponseEntity.ok(response);
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
                            case "author":
                                logger.info("Author cannot be updated using PATCH method.");
                                break;
                            case "coAuthors":
                                logger.info("Co-authors cannot be updated using PATCH method.");
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
                            case "language":
                                logger.info("Language cannot be updated using PATCH method.");
                                break;
                            case "genre":
                                logger.info("Genre cannot be updated using PATCH method.");
                                break;
                            default:
                                logger.warn("Unknown key '{}' has been ignored.", key);
                        }
                    });
                    Book savedBook = bookRepository.save(book);
                    return ResponseEntity.ok(convertToBookResponse(savedBook));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    private BookResponse convertToBookResponse(Book book) {
        BookResponse response = new BookResponse();
        response.setTitle(book.getTitle());
        response.setAuthor(book.getAuthor());
        response.setCoAuthors(book.getCoAuthors());
        response.setIsbn(book.getIsbn());
        response.setPublicationYear(book.getPublicationYear());
        response.setPublisher(book.getPublisher());
        response.setNumberOfPages(book.getNumberOfPages());
        return response;
    }
}
