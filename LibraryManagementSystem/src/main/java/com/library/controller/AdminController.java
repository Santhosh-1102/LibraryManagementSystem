package com.library.controller;

import com.library.entity.Book;
import com.library.entity.BorrowRecord;
import com.library.entity.User;
import com.library.service.BookService;
import com.library.service.BorrowService;
import com.library.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;
    private final BorrowService borrowService;
    private final UserService userService;

    public AdminController(BookService bookService, BorrowService borrowService, UserService userService) {
        this.bookService = bookService;
        this.borrowService = borrowService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("stats", borrowService.getDashboardStatistics());
        model.addAttribute("recentBorrows", borrowService.getRecentBorrows());
        model.addAttribute("categories", bookService.getAllCategories());
        model.addAttribute("books", bookService.getAllBooks());
        return "admin/dashboard";
    }

    @GetMapping("/books")
    public String listBooks(@RequestParam(value = "search", required = false) String search,
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
        return "admin/books";
    }

    @GetMapping("/books/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("isEdit", false);
        model.addAttribute("categories", bookService.getAllCategories());
        return "admin/book-form";
    }

    @PostMapping("/books/add")
    public String saveBook(@ModelAttribute Book book,
                           @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                           @RequestParam(value = "coverUrl", required = false) String coverUrl,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        try {
            if (book.getIsbn() == null || book.getIsbn().trim().isEmpty() ||
                book.getTitle() == null || book.getTitle().trim().isEmpty()) {
                model.addAttribute("errorMessage", "ISBN and Title are required.");
                model.addAttribute("book", book);
                model.addAttribute("isEdit", false);
                return "admin/book-form";
            }

            // Handle file upload
            if (imageFile != null && !imageFile.isEmpty()) {
                String uploadDir = "uploads";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
                Path filePath = Paths.get(uploadDir, filename);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                book.setCoverImageUrl("/uploads/" + filename);
            } else if (coverUrl != null && !coverUrl.trim().isEmpty()) {
                book.setCoverImageUrl(coverUrl.trim());
            }

            bookService.saveBook(book);
            redirectAttributes.addFlashAttribute("successMessage", "Book added successfully!");
            return "redirect:/admin/books";
        } catch (Exception ex) {
            model.addAttribute("errorMessage", "Error saving book: " + ex.getMessage());
            model.addAttribute("book", book);
            model.addAttribute("isEdit", false);
            return "admin/book-form";
        }
    }

    @GetMapping("/books/edit/{isbn}")
    public String editBookForm(@PathVariable("isbn") String isbn, Model model) {
        Book book = bookService.getBookByIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + isbn));
        model.addAttribute("book", book);
        model.addAttribute("isEdit", true);
        model.addAttribute("categories", bookService.getAllCategories());
        return "admin/book-form";
    }

    @PostMapping("/books/edit/{isbn}")
    public String updateBook(@PathVariable("isbn") String isbn,
                             @ModelAttribute Book formBook,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             @RequestParam(value = "coverUrl", required = false) String coverUrl,
                             RedirectAttributes redirectAttributes) {
        try {
            String imageUrl = coverUrl;
            if (imageFile != null && !imageFile.isEmpty()) {
                String uploadDir = "uploads";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9._-]", "_");
                Path filePath = Paths.get(uploadDir, filename);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                imageUrl = "/uploads/" + filename;
            }

            bookService.updateBook(isbn, formBook.getTitle(), formBook.getAuthor(), formBook.getCategory(),
                    formBook.getDescription(), formBook.getTotalQuantity(), imageUrl);

            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating book: " + ex.getMessage());
        }
        return "redirect:/admin/books";
    }

    @PostMapping("/books/delete/{isbn}")
    public String deleteBook(@PathVariable("isbn") String isbn, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBook(isbn);
            redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting book: " + ex.getMessage());
        }
        return "redirect:/admin/books";
    }

    @GetMapping("/students")
    public String listStudents(Model model) {
        List<User> students = userService.getAllStudents();
        model.addAttribute("students", students);
        return "admin/students";
    }

    @GetMapping("/borrows")
    public String listBorrows(Model model) {
        List<BorrowRecord> borrows = borrowService.getAllBorrowRecords();
        model.addAttribute("borrows", borrows);
        model.addAttribute("stats", borrowService.getDashboardStatistics());
        return "admin/borrows";
    }

    @PostMapping("/borrows/return/{id}")
    public String markReturned(@PathVariable("id") Long recordId, RedirectAttributes redirectAttributes) {
        try {
            borrowService.returnBook(recordId, "admin", true);
            redirectAttributes.addFlashAttribute("successMessage", "Book loan marked as returned.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating record: " + ex.getMessage());
        }
        return "redirect:/admin/borrows";
    }
}
