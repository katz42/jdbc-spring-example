package ru.ezhidkova.jdbc_spring_example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.ezhidkova.jdbc_spring_example.model.Book;
import ru.ezhidkova.jdbc_spring_example.repository.BookRepository;

import java.util.List;

@RestController
public class BookController {


    @Autowired
    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/book/all")
    List<Book> getAllBooks(){
        return bookRepository.findAllBooks();
    }

    @GetMapping("/book/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookRepository.findBookById(id);
    }
}