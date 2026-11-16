package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    List<Book> findByCategoryIgnoreCase(String category);

    @Query("SELECT b FROM Book b WHERE " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
           "LOWER(b.author) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
           "LOWER(b.isbn) LIKE LOWER(CONCAT('%', :kw, '%')) OR " +
           "LOWER(b.category) LIKE LOWER(CONCAT('%', :kw, '%')) " +
           "ORDER BY b.title ASC")
    List<Book> searchBooks(@Param("kw") String keyword);

    @Query("SELECT DISTINCT b.category FROM Book b WHERE b.category IS NOT NULL ORDER BY b.category ASC")
    List<String> findAllCategories();

    @Query("SELECT COALESCE(SUM(b.totalQuantity), 0) FROM Book b")
    long sumTotalQuantity();

    @Query("SELECT COALESCE(SUM(b.availableQuantity), 0) FROM Book b")
    long sumAvailableQuantity();
}
