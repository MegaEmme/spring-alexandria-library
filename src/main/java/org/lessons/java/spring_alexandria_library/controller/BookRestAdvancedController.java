package org.lessons.java.spring_alexandria_library.controller;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_alexandria_library.model.Book;
import org.lessons.java.spring_alexandria_library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2/books")
public class BookRestAdvancedController {

    @Autowired
    private BookService service;

    // INDEX
    @GetMapping
    public List<Book> index() {
        List<Book> books = service.findAll();
        return books;
    }

    // SHOW
    @GetMapping("{id}")
    public ResponseEntity<Book> show(@PathVariable("id") Integer id) {
        Optional<Book> bookAttempt = service.findById(id);

        if (bookAttempt.isEmpty()) {
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Book>(bookAttempt.get(), HttpStatus.OK);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Book> store(@Valid @RequestBody Book book) {
        return new ResponseEntity<Book>(service.create(book), HttpStatus.OK);
    }

    // UPDATE
    @PutMapping("{id}")
    public ResponseEntity<Book> update(@Valid @RequestBody Book book, @PathVariable("id") Integer id) {

        if (service.findById(id).isEmpty()) {
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }

        book.setId(id);
        return new ResponseEntity<Book>(service.update(book), HttpStatus.OK);
    }

    // DELETE
    @DeleteMapping("{id}")
    public ResponseEntity<Book> delete(@PathVariable("id") Integer id) {
        if (service.findById(id).isEmpty()) {
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }

        service.deleteById(id);
        return new ResponseEntity<Book>(HttpStatus.OK);
    }

}
