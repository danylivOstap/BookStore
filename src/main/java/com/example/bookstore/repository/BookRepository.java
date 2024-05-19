package com.example.bookstore.repository;

import com.example.bookstore.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn);

    @Query("FROM Book b JOIN FETCH b.categories c WHERE c.id = ?1")
    List<Book> findAllByCategory(Long categoryId);

    @Query("FROM Book b JOIN FETCH b.categories")
    Page<Book> findAllWithCategories(Pageable pageable);
}
