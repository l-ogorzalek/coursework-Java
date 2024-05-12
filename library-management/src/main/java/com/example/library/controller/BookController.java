package com.example.library.controller;

import com.example.library.dto.BookCreateRequest;
import com.example.library.dto.BookResponse;
import com.example.library.service.BookServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookServiceImpl;

    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookServiceImpl.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookByID(@PathVariable UUID id) {
        return bookServiceImpl.findById(id);
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookCreateRequest bookRequest) {
        return bookServiceImpl.createBook(bookRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> replaceBook(@PathVariable UUID id,
            @RequestBody BookCreateRequest bookReplacement) {
        return bookServiceImpl.replaceBook(id, bookReplacement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBook(@PathVariable UUID id) {
        return bookServiceImpl.deleteBook(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookResponse> partiallyUpdateBook(@PathVariable UUID id,
            @RequestBody Map<String, Object> updatedBook) {
        return bookServiceImpl.partiallyUpdateBook(id, updatedBook);
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
