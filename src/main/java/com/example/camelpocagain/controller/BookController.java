package com.example.camelpocagain.controller;

import com.example.camelpocagain.spec.api.BooksApi;
import com.example.camelpocagain.spec.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class BookController implements BooksApi {

    //Synchronized list to hold all books
    private static final List<Book> books = new CopyOnWriteArrayList<>();

    @Override
    public ResponseEntity<Book> createBook(Book book) {
        book.setId((long) (books.size() + 1));
        books.add(book);
        URI uri = URI.create("/books/" + book.getId());
        return ResponseEntity.created(uri).body(book);
    }

    @Override
    public ResponseEntity<Void> deleteBook(Long id) {
        books.removeIf(book -> book.getId().equals(id));
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Book> getBookById(Long id) {
        return books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .map(b -> ResponseEntity.ok().body(b))
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(books);
    }

    @Override
    public ResponseEntity<Book> updateBook(Long id, Book book) {
        return books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .map(b -> {
                    b.setAuthor(book.getAuthor());
                    b.setName(book.getName());
                    b.setPublisher(book.getPublisher());
                    b.setFirstPublished(book.getFirstPublished());
                    b.setTotalEdition(book.getTotalEdition());
                    return ResponseEntity.ok().body(b);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
