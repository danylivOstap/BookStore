package com.example.bookstore.service;

import com.example.bookstore.dto.cart.ShoppingCartDto;
import com.example.bookstore.dto.cartitem.AddCartItemDto;
import com.example.bookstore.dto.cartitem.UpdateQuantityDto;

public interface ShoppingCartService {

    ShoppingCartDto getUsersCart(String userName);

    ShoppingCartDto addBookToCart(String username, AddCartItemDto requestDto);

    ShoppingCartDto updateCartItemQuantity(String username, Long cartItemId,
            UpdateQuantityDto updateQuantityDto);

    ShoppingCartDto deleteCartItem(Long cartItemId, String username);
}