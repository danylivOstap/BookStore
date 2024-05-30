package com.example.bookstore.repository;

import com.example.bookstore.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("from Order o join fetch o.user u join fetch o.orderItems oi where u.email=?1")
    Page<Order> findAllByUser(String email, Pageable pageable);
}
