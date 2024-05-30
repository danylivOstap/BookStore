package com.example.bookstore.repository;

import com.example.bookstore.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("from OrderItem oi join fetch oi.order o where o.id=?1")
    Page<OrderItem> findAllByOrderId(Long orderId, Pageable pageable);
}
