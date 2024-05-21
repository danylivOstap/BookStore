package com.example.bookstore.service;

import com.example.bookstore.model.CartItem;

public interface CartItemService {

    CartItem getItemById(Long cartItemId);

    CartItem save(CartItem cartItem);

    void delete(CartItem cartItemToBeDeleted);
}
