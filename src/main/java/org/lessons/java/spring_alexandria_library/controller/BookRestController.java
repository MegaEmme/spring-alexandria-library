package org.lessons.java.spring_alexandria_library.controller;

import java.util.List;

import org.lessons.java.spring_alexandria_library.model.Book;
import org.lessons.java.spring_alexandria_library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

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
    public Book show(@PathVariable("id") Integer id) {
        Book book = service.getById(id);
        return book;
    }

    // CREATE
    @PostMapping
    public Book store(@RequestBody Book book) {
        service.create(book);
        return book;
    }

    // UPDATE
    @PutMapping("{id}")
    public Book update(@RequestBody Book book, @PathVariable("id") Integer id) {
        book.setId(id);
        return service.update(book);
    }

    // DELETE
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Integer id) {
        service.deleteById(id);
    }

}
