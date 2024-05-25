package com.example.bookstore.exception;

public class CartAlreadyContainsItem extends RuntimeException {

    public CartAlreadyContainsItem(String message) {
        super(message);
    }
}
