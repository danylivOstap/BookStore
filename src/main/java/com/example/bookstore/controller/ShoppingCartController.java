package com.example.bookstore.controller;

import com.example.bookstore.dto.cart.ShoppingCartDto;
import com.example.bookstore.dto.cartitem.AddCartItemDto;
import com.example.bookstore.dto.cartitem.UpdateQuantityDto;
import com.example.bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ShoppingCartDto getUsersCart(
            @AuthenticationPrincipal final UserDetails userDetails) {
        return shoppingCartService.getUsersCart(userDetails.getUsername());
    }

    @PostMapping
    public ShoppingCartDto addBookToCart(
            @AuthenticationPrincipal final UserDetails userDetails,
            @RequestBody final AddCartItemDto requestDto) {
        return shoppingCartService.addBookToCart(userDetails.getUsername(), requestDto);
    }

    @PutMapping("/{cartItemId}")
    public ShoppingCartDto updateBookQuantity(
            @AuthenticationPrincipal final UserDetails userDetails,
            @PathVariable final Long cartItemId,
            @RequestBody final UpdateQuantityDto updateQuantityDto) {
        return shoppingCartService.updateCartItemQuantity(userDetails.getUsername(), cartItemId,
                updateQuantityDto);
    }

    @DeleteMapping("/{cartItemId}")
    public ShoppingCartDto deleteCartItem(
            @AuthenticationPrincipal final UserDetails userDetails,
            @PathVariable final Long cartItemId) {
        return shoppingCartService.deleteCartItem(cartItemId, userDetails.getUsername());
    }
}
