package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.model.Book;
import com.example.demo.model.RegistrationRequest;
import com.example.demo.service.BookService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final BookService bookService;
    private final UserService userService;

    @GetMapping("/admin/books")
    public String viewBooks(Model model) {
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        model.addAttribute("title", "Books");
        return "books"; // Vei folosi acest șablon
    }

    @GetMapping("/add-book")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("title", "Add Book");
        return "add-book";
    }

    @PostMapping("/add-book")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.addBook(book);
        return "redirect:/admin/books"; // Redirect pentru admini
    }
    @GetMapping("/delete-book")
    public String displayDeleteBookForm(Model model){
        model.addAttribute("title", "Delete Books");
        model.addAttribute("books", bookService.findAllBooks());
        return "delete-book";
    }

    @PostMapping("/delete-book")
    public String processDeleteBooksForm(@RequestParam(required = false) Long[] bookIds){

        if (bookIds != null) {
            for (Long id : bookIds) {
                bookService.deleteById(id);
            }
        }
        return "redirect:/admin/books";
    }
    @GetMapping("/admin/create-user")
    public String showCreateUserForm(Model model) {
        model.addAttribute("title", "Admin - Create User");
        model.addAttribute("user", new RegistrationRequest());
        return "user/create-user"; // Noua pagină HTML pentru admin
    }
    @PostMapping("/admin/create-user")
    public String createUser(@ModelAttribute("user") RegistrationRequest registrationRequest, RedirectAttributes redirectAttributes){

        UserDto userDto = userService.registerUser(registrationRequest);

        redirectAttributes.addAttribute("registrationSuccess", "Success");

        return "redirect:/admin/create-user";
    }
    @GetMapping("/admin/delete-user")
    public String displayDeleteUserForm(Model model){
        model.addAttribute("title", "Delete Users");
        model.addAttribute("users", userService.getAllUsers());

        return "user/delete-user";
    }

    @PostMapping("/admin/delete-user")
    public String processDeleteUsersForm(@RequestParam(required = false) String[] usernames) {
        if (usernames != null) {
            for (String username : usernames) {
                userService.deleteUserByUsername(username); // Asigură-te că metoda există în UserService
            }
        }
        return "redirect:/admin/delete-user";
    }
}

