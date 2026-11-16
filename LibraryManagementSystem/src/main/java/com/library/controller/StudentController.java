package com.library.controller;

import com.library.entity.BorrowRecord;
import com.library.entity.User;
import com.library.service.BookService;
import com.library.service.BorrowService;
import com.library.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    private final BorrowService borrowService;
    private final BookService bookService;
    private final UserService userService;

    public StudentController(BorrowService borrowService, BookService bookService, UserService userService) {
        this.borrowService = borrowService;
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        User student = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<BorrowRecord> activeBorrows = borrowService.getStudentActiveBorrows(username);
        List<BorrowRecord> allBorrows = borrowService.getStudentBorrowRecords(username);

        model.addAttribute("student", student);
        model.addAttribute("activeBorrows", activeBorrows);
        model.addAttribute("allBorrows", allBorrows);
        model.addAttribute("activeCount", activeBorrows.size());
        model.addAttribute("maxBorrowLimit", 5);
        model.addAttribute("recommendedBooks", bookService.getAllBooks().stream().limit(4).toList());

        return "student/dashboard";
    }

    @PostMapping("/borrow/{isbn}")
    public String borrowBook(@PathVariable("isbn") String isbn,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes,
                             @RequestHeader(value = "Referer", required = false) String referer) {
        try {
            borrowService.borrowBook(authentication.getName(), isbn);
            redirectAttributes.addFlashAttribute("successMessage", "Book borrowed successfully! Due in 14 days.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        if (referer != null && !referer.isEmpty() && !referer.contains("/login")) {
            return "redirect:" + referer;
        }
        return "redirect:/student/dashboard";
    }

    @PostMapping("/return/{id}")
    public String returnBook(@PathVariable("id") Long recordId,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes,
                             @RequestHeader(value = "Referer", required = false) String referer) {
        try {
            borrowService.returnBook(recordId, authentication.getName(), false);
            redirectAttributes.addFlashAttribute("successMessage", "Book returned successfully. Thank you!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }

        if (referer != null && !referer.isEmpty()) {
            return "redirect:" + referer;
        }
        return "redirect:/student/dashboard";
    }

    @GetMapping("/history")
    public String borrowHistory(Authentication authentication, Model model) {
        String username = authentication.getName();
        User student = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<BorrowRecord> records = borrowService.getStudentBorrowRecords(username);
        model.addAttribute("student", student);
        model.addAttribute("borrowRecords", records);
        return "student/history";
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        String username = authentication.getName();
        User student = userService.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        model.addAttribute("student", student);
        return "student/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(Authentication authentication,
                                @RequestParam("fullName") String fullName,
                                @RequestParam("email") String email,
                                @RequestParam("phone") String phone,
                                @RequestParam(value = "age", required = false) Integer age,
                                RedirectAttributes redirectAttributes) {
        try {
            userService.updateUserProfile(authentication.getName(), fullName, email, phone, age);
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating profile: " + ex.getMessage());
        }
        return "redirect:/student/profile";
    }
}
