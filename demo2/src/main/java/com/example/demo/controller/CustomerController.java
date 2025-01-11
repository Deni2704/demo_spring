package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final BookService bookService;

    @GetMapping("/user/books")
    public String viewBooks(Model model) {
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("title", "All Books");
        return "books"; // Vei folosi acest șablon
    }

    @GetMapping("/user/add-book")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("title", "Add Book");
        return "add-book";
    }

    @PostMapping("/user/add-book")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.addBook(book);
        return "redirect:/user/books"; // Redirect către lista de cărți pentru useri
    }
    @GetMapping("/user/delete-book")
    public String displayDeleteBookForm(Model model){
        model.addAttribute("title", "Delete Books");
        model.addAttribute("books", bookService.findAllBooks());
        return "delete-book";
    }

    @PostMapping("/user/delete-book")
    public String processDeleteBooksForm(@RequestParam(required = false) Long[] bookIds){

        if (bookIds != null) {
            for (Long id : bookIds) {
                bookService.deleteById(id);
            }
        }
        return "redirect:/user/books";
    }
}
