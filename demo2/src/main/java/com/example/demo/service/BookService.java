package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }
    public void deleteBook(Book book) {
         bookRepository.delete(book);
    }
    public void deleteById(Long bookId){
        bookRepository.deleteById(bookId);
    }
}
