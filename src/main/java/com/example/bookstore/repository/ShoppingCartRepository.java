package com.example.bookstore.repository;

import com.example.bookstore.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("FROM ShoppingCart sc JOIN FETCH sc.user u JOIN FETCH sc.cartItems ci "
            + "WHERE u.email = ?1")
    Optional<ShoppingCart> findUsersCart(String email);
}
