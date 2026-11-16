package com.library.service;

import com.library.entity.Book;
import com.library.entity.BorrowRecord;
import com.library.entity.BorrowStatus;
import com.library.entity.User;
import com.library.repository.BookRepository;
import com.library.repository.BorrowRecordRepository;
import com.library.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BorrowService {

    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BorrowService(BorrowRecordRepository borrowRecordRepository,
                         BookRepository bookRepository,
                         UserRepository userRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BorrowRecord borrowBook(String username, String isbn) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ISBN: " + isbn));

        // 1. Check if user already has an active borrow for this specific book
        if (borrowRecordRepository.existsByUserAndBookAndStatus(user, book, BorrowStatus.ACTIVE)) {
            throw new IllegalStateException("You already have an active loan for this book: '" + book.getTitle() + "'. Please return it first.");
        }

        // 2. Check if student has reached max active borrow limit (e.g. 5 books)
        List<BorrowRecord> activeBorrows = borrowRecordRepository.findByUserAndStatus(user, BorrowStatus.ACTIVE);
        if (activeBorrows.size() >= 5) {
            throw new IllegalStateException("You have reached your maximum limit of 5 active borrowed books. Please return a book before borrowing another.");
        }

        // 3. Check book stock
        if (book.getAvailableQuantity() <= 0) {
            throw new IllegalStateException("Sorry, all copies of '" + book.getTitle() + "' are currently checked out.");
        }

        // 4. Decrement available quantity
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);

        // 5. Create Borrow Record
        BorrowRecord record = new BorrowRecord(user, book);
        return borrowRecordRepository.save(record);
    }

    @Transactional
    public BorrowRecord returnBook(Long recordId, String username, boolean isAdmin) {
        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("Borrow record not found: " + recordId));

        if (!isAdmin && !record.getUser().getUsername().equals(username)) {
            throw new SecurityException("You are not authorized to return this book.");
        }

        if (record.getStatus() == BorrowStatus.RETURNED) {
            return record; // Already returned
        }

        record.setStatus(BorrowStatus.RETURNED);
        record.setReturnDate(LocalDateTime.now());

        // Increment book available quantity
        Book book = record.getBook();
        book.setAvailableQuantity(Math.min(book.getTotalQuantity(), book.getAvailableQuantity() + 1));
        bookRepository.save(book);

        return borrowRecordRepository.save(record);
    }

    public List<BorrowRecord> getStudentBorrowRecords(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        return borrowRecordRepository.findByUserOrderByBorrowDateDesc(user);
    }

    public List<BorrowRecord> getStudentActiveBorrows(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        return borrowRecordRepository.findByUserAndStatus(user, BorrowStatus.ACTIVE);
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.findAllByOrderByBorrowDateDesc();
    }

    public List<BorrowRecord> getRecentBorrows() {
        return borrowRecordRepository.findTop10ByOrderByBorrowDateDesc();
    }

    public long getActiveBorrowsCount() {
        return borrowRecordRepository.countByStatus(BorrowStatus.ACTIVE);
    }

    public long getReturnedBorrowsCount() {
        return borrowRecordRepository.countByStatus(BorrowStatus.RETURNED);
    }

    public long getOverdueCount() {
        return borrowRecordRepository.countOverdue(LocalDate.now());
    }

    public Map<String, Object> getDashboardStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBooks", bookRepository.count());
        stats.put("totalCopies", bookRepository.sumTotalQuantity());
        stats.put("availableCopies", bookRepository.sumAvailableQuantity());
        stats.put("activeBorrows", getActiveBorrowsCount());
        stats.put("returnedBorrows", getReturnedBorrowsCount());
        stats.put("overdueBorrows", getOverdueCount());
        stats.put("totalStudents", userRepository.countByRole(com.library.entity.Role.ROLE_STUDENT));
        return stats;
    }
}
