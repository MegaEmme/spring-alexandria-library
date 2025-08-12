package org.lessons.java.spring_alexandria_library.repository;

import org.lessons.java.spring_alexandria_library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
