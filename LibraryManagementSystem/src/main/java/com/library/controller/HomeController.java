package com.library.controller;

import com.library.entity.Book;
import com.library.service.BookService;
import com.library.service.BorrowService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final BookService bookService;
    private final BorrowService borrowService;

    public HomeController(BookService bookService, BorrowService borrowService) {
        this.bookService = bookService;
        this.borrowService = borrowService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("featuredBooks", books.stream().limit(6).toList());
        model.addAttribute("stats", borrowService.getDashboardStatistics());
        model.addAttribute("categories", bookService.getAllCategories());
        return "index";
    }

    @GetMapping("/catalog")
    public String catalog(@RequestParam(value = "search", required = false) String search,
                          @RequestParam(value = "category", required = false) String category,
                          Model model) {
        List<Book> books;
        if (search != null && !search.trim().isEmpty()) {
            books = bookService.searchBooks(search);
        } else if (category != null && !category.trim().isEmpty() && !category.equalsIgnoreCase("All")) {
            books = bookService.getBooksByCategory(category);
        } else {
            books = bookService.getAllBooks();
        }

        model.addAttribute("books", books);
        model.addAttribute("categories", bookService.getAllCategories());
        model.addAttribute("selectedCategory", category != null ? category : "All");
        model.addAttribute("searchKeyword", search);
        return "catalog";
    }

    @GetMapping("/book/{isbn}")
    public String bookDetails(@PathVariable("isbn") String isbn, Model model) {
        Book book = bookService.getBookByIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ISBN: " + isbn));
        model.addAttribute("book", book);
        return "book-details";
    }
}
