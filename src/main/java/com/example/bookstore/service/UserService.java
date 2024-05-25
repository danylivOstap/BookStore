package com.example.bookstore.service;

import com.example.bookstore.security.model.User;

public interface UserService {

    User findByEmail(String email);

    User save(User user);

    boolean isUserRegistered(String email);
}
