package com.library.service;

import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findById(isbn);
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return bookRepository.findAll();
        }
        return bookRepository.searchBooks(keyword.trim());
    }

    public List<Book> getBooksByCategory(String category) {
        if (category == null || category.trim().isEmpty() || category.equalsIgnoreCase("All")) {
            return bookRepository.findAll();
        }
        return bookRepository.findByCategoryIgnoreCase(category.trim());
    }

    public List<String> getAllCategories() {
        return bookRepository.findAllCategories();
    }

    public long getTotalBooksCount() {
        return bookRepository.count();
    }

    public long getTotalCopiesCount() {
        return bookRepository.sumTotalQuantity();
    }

    public long getAvailableCopiesCount() {
        return bookRepository.sumAvailableQuantity();
    }

    @Transactional
    public Book saveBook(Book book) {
        // If it's a new book or availableQuantity is uninitialized
        if (book.getAvailableQuantity() <= 0 && book.getTotalQuantity() > 0) {
            book.setAvailableQuantity(book.getTotalQuantity());
        }
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(String isbn, String title, String author, String category, String description, int totalQuantity, String coverImageUrl) {
        Book existing = bookRepository.findById(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ISBN: " + isbn));

        int diff = totalQuantity - existing.getTotalQuantity();
        existing.setTitle(title.trim());
        existing.setAuthor(author.trim());
        existing.setCategory(category.trim());
        existing.setDescription(description != null ? description.trim() : "");
        existing.setTotalQuantity(totalQuantity);
        existing.setAvailableQuantity(Math.max(0, existing.getAvailableQuantity() + diff));

        if (coverImageUrl != null && !coverImageUrl.trim().isEmpty()) {
            existing.setCoverImageUrl(coverImageUrl.trim());
        }

        return bookRepository.save(existing);
    }

    @Transactional
    public void deleteBook(String isbn) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ISBN: " + isbn));
        bookRepository.delete(book);
    }
}
