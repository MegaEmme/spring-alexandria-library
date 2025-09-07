package org.lessons.java.spring_alexandria_library.service;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_alexandria_library.model.Book;
import org.lessons.java.spring_alexandria_library.model.Borrowing;
import org.lessons.java.spring_alexandria_library.repository.BookRepository;
import org.lessons.java.spring_alexandria_library.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

//PRENDE LA LOGICA DELLA REPOSITORY NEL CONTROLLER E LA METTE QUA IN MODO DA SEPARARE I COMPITI, FRAPPONENDOSI TRA CONTROLLER E DB
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowingRepository borrowingRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findAllSortedByTitle() {
        return bookRepository.findAll(Sort.by("title"));
    }

    public List<Book> findAllSortedByAuthor() {
        return bookRepository.findAll(Sort.by("author"));
    }

    public Book getById(Integer id) {
        // per gestire la casistica in cui non viene trovata la risorsa, prima mi creo
        // un optional, poi lo metto in un if, se non c'è restituisco 404, altrimenti
        // procedo
        Optional<Book> bookAttempt = bookRepository.findById(id);
        if (bookAttempt.isEmpty()) {
            // lanciamo 404
        }
        return bookAttempt.get();
    }

    public List<Book> findByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    public List<Book> findByTitleOrAuthor(String query) {
        return bookRepository.findByTitleContainingOrAuthorContaining(query, query);
    }

    // QUI DIVIDO LA LOGICA DI UPDATE E CREATE, CHE IN BOOKCONTROLLER ERA UNICA
    public Book create(Book book) {
        // aggiorno solo di alcuni campi a seguito della creazione
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        // aggiorno solo di alcuni campi a seguito dell'aggiornamento
        return bookRepository.save(book);
    }

    // DELETE PURA
    public void delete(Book book) {
        for (Borrowing borrowingToDelete : book.getBorrowings()) {
            borrowingRepository.delete(borrowingToDelete);
        }
        bookRepository.delete(book);
    }

    // DELETE PER ID
    public void deleteById(Integer id) {
        Book book = getById(id);
        for (Borrowing borrowingToDelete : book.getBorrowings()) {
            borrowingRepository.delete(borrowingToDelete);
        }
        bookRepository.delete(book);

    }

    // ALTRI METODI PER VERIFICARE ESISTENZA LIBRO
    public Boolean existsById(Integer id) {
        return bookRepository.existsById(id);
    }

    public Boolean exists(Book book) {
        // AVENDO APPENA CREATO EXISTSBYID QUA SOTTO è INUTILE RICHIAMARE LA REPOSITORY,
        // RICHIAMO DIRETTAMENTE LUI (qui, ln.85)
        return existsById(book.getId());
    }

}
