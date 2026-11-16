package com.library.repository;

import com.library.entity.Book;
import com.library.entity.BorrowRecord;
import com.library.entity.BorrowStatus;
import com.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    List<BorrowRecord> findByUserOrderByBorrowDateDesc(User user);

    List<BorrowRecord> findByUserAndStatus(User user, BorrowStatus status);

    List<BorrowRecord> findAllByOrderByBorrowDateDesc();

    long countByStatus(BorrowStatus status);

    boolean existsByUserAndBookAndStatus(User user, Book book, BorrowStatus status);

    List<BorrowRecord> findTop10ByOrderByBorrowDateDesc();

    @Query("SELECT COUNT(br) FROM BorrowRecord br WHERE br.status = 'ACTIVE' AND br.dueDate < :today")
    long countOverdue(LocalDate today);
}
