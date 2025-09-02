package org.lessons.java.spring_alexandria_library.controller;

import java.util.List;

import org.lessons.java.spring_alexandria_library.model.Book;
import org.lessons.java.spring_alexandria_library.model.Borrowing;
import org.lessons.java.spring_alexandria_library.repository.BookRepository;
import org.lessons.java.spring_alexandria_library.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository repository;

    @Autowired
    private BorrowingRepository borrowingRepository;

    // INDEX
    @GetMapping
    public String index(Model model) {
        // SELECT * FROM 'books' ==> lista di oggetti di tipo book
        List<Book> books = repository.findAll();

        model.addAttribute("books", books);

        return "books/index";
    }

    // SHOW
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        // .findById(id) mi restituisce un optional, ovvero mi dice se c'è o non c'è
        // quello che cerco, per questo devo usare il .get() per prenderlo subito dopo
        Book book = repository.findById(id).get();
        model.addAttribute("book", book);
        return "books/show";
    }

    // STORE
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("book", new Book());
        return "books/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("book") Book formBook, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "books/create";
        }

        repository.save(formBook);
        return "redirect:/books";

    }

    // UPDATE
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("book", repository.findById(id).get());
        return "books/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("book") Book formBook, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        repository.save(formBook);
        return "redirect:/books";

    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // Qui ci si pone il problema di cancellare un libro a cui sono legate delle
        // recensioni, come fare?
        // 1) Prendere per ogni libro i prestiti ad esso collegati -->
        // repository.getBorrowings()
        // LI PRENDO DAL LIBRO
        // 2) Elinimare quei prestiti dalla tabella borrowings nel db LI PRENDO DAI
        // BORROWINGS (devo chiamare la repository di borrowing!) -->
        // borrowingRepository.delete(borrowing)
        // 3) A questo punto non ho piu vincoli di chiave esterna (FK) su book_id e
        // posso dunque cancellare il libro

        Book book = repository.findById(id).get();
        // Se usi cascade nel Book (vedi Book ln.52) le prossime tre linee di codice
        // sono inutili
        for (Borrowing borrowingToDelete : book.getBorrowings()) {
            borrowingRepository.delete(borrowingToDelete);
        }

        repository.delete(book);
        return "redirect:/books";
    }

    // BORROW
    @GetMapping("/{id}/borrow")
    public String borrow(@PathVariable Integer id, Model model) {
        Borrowing borrowing = new Borrowing();
        borrowing.setBook(repository.findById(id).get());
        model.addAttribute("borrowing", borrowing);
        return "borrowings/create-or-edit";
    }

    // ROTTE QUERY CUSTOM
    // @GetMapping("/searchByTitle")
    // public String searchByTitle(@RequestParam(name = "title") String title, Model
    // model) {

    // List<Book> books = repository.findByTitleContaining(title);
    // model.addAttribute("books", books);
    // return "books/index";
    // }

    // @GetMapping("/searchByTitleOrAuthor")
    // public String searchByTitleOrAuthor(@RequestParam(name = "query") String
    // query, Model model) {

    // List<Book> books = repository.findByTitleContainingOrAuthorContaining(query,
    // query);
    // model.addAttribute("books", books);
    // return "books/index";
    // }

}
