package com.example.library.controller;

import com.example.library.dto.BookCreateRequest;
import com.example.library.dto.BookResponse;
import com.example.library.model.Book;
import com.example.library.service.BookService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable UUID id) {
        Book book = bookService.findById(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookCreateRequest bookRequest) {
        Book newBook = new Book();
        newBook.setTitle(bookRequest.getTitle());
        newBook.setAuthor(bookRequest.getAuthor());
        newBook.setIsbn(bookRequest.getIsbn());
        newBook.setPublicationYear(bookRequest.getPublicationYear());
        newBook.setPublisher(bookRequest.getPublisher());
        newBook.setNumberOfPages(bookRequest.getNumberOfPages());
        newBook.setLanguage(bookRequest.getLanguage());
        newBook.setGenre(bookRequest.getGenre());

        Book savedBook = bookService.save(newBook);

        BookResponse response = new BookResponse();
        response.setTitle(savedBook.getTitle());
        response.setAuthor(savedBook.getAuthor());
        response.setIsbn(savedBook.getIsbn());
        response.setPublicationYear(savedBook.getPublicationYear());
        response.setPublisher(savedBook.getPublisher());
        response.setNumberOfPages(savedBook.getNumberOfPages());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> replaceBook(@PathVariable UUID id, @RequestBody Book newBook) {
        Book existingBook = bookService.findById(id);
        if (existingBook == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        newBook.setId(id);
        Book updatedBook = bookService.save(newBook);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        Book book = bookService.findById(id);
        if (book != null) {
            bookService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> partiallylUpdateBook(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
        Book book = bookService.findById(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        updates.forEach((key, value) -> {
            switch (key) {
                case "title":
                    book.setTitle((String) value);
                    break;
                case "author":
                    book.setAuthor((String) value);
                    break;
                case "coAuthors":
                    logger.info("Co-authors cannot be updated using PATCH method.");
                    break;
                case "isbn":
                    book.setIsbn((String) value);
                    break;
                case "publicationYear":
                    book.setPublicationYear((int) value);
                    break;
                case "publisher":
                    book.setPublisher((String) value);
                    break;
                case "numberOfPages":
                    book.setNumberOfPages((int) value);
                    break;
                case "language":
                    book.setLanguage((String) value);
                    break;
                case "genre":
                    book.setGenre((String) value);
                    break;
                default:
                    logger.warn("Unknown key '{}' has been ignored.", key);
            }
        });
        return ResponseEntity.ok(bookService.save(book));
    }
}

/**

 * Do zapamiętania:
 * 
 * Kontroler to klasa, która zapewnia interfejs umożliwiający komunikację z aplikacją. W tym przypadku @BookController
 * odbiera żądania HTTP i korzysta z @BookService do zarządzania danymi książek.
 * 
 * @RequestMapping określa prefiks URL poprzedzający ścieżki obsługiwane przez kontroler.
 * 
 * Metody kontrolera oznaczone jako @GetMapping, @PostMapping, @PutMapping i @DeleteMapping obsługują żądania 
 * HTTP: GET, POST, PUT i DELETE.
 * 
 * Żądania to wiadomości wysyłane przez klienta (przeglądarka, aplikacja mobilna itp.) do serwera, aby uzyskać dostęp
 * do zasobów, dokonać operacji lub wymienić informacje. W aplikacji webowej są to żadania HTTP.
 * 
 * Typy żądań HTTP:
 * 
 * GET - pobiera dane z serwera (np. uzyskanie listy książek lub pojedynczej książki);
 * POST - wysyła nowe dane do serwera w celu utworzenia nowego zasobu (np. dodanie nowej książki do biblioteki);
 * PUT - aktualizuje istniejący zasób na serwerze lub go zastępuje (np. zmiana książki na inną);
 * DELETE - usuwa zasób z serwera (np. usunięcie książki o podanym identyfikatorze);
 * PATCH - aktualizuje część zasobu (np. aktualizacja tylko tytułu książki);
 * i inne...
 */
