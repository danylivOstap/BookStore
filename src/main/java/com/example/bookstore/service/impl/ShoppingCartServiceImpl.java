package com.example.bookstore.service.impl;

import com.example.bookstore.dto.cart.ShoppingCartDto;
import com.example.bookstore.dto.cartitem.AddCartItemDto;
import com.example.bookstore.dto.cartitem.UpdateQuantityDto;
import com.example.bookstore.exception.CartAlreadyContainsItem;
import com.example.bookstore.mapper.CartItemMapper;
import com.example.bookstore.mapper.ShoppingCartMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.service.CartItemService;
import com.example.bookstore.service.ShoppingCartService;
import com.example.bookstore.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserService userService;
    private final CartItemMapper cartItemMapper;
    private final CartItemService cartItemService;

    @Override
    public ShoppingCartDto getUsersCart(final String userName) {
        return shoppingCartMapper.toDto(getShoppingCartByUser(userName));
    }

    private ShoppingCart getShoppingCartByUser(String userName) {
        return shoppingCartRepository.findUsersCart(userName)
            .orElseThrow(() -> new EntityNotFoundException(
                "Can't find cart for user with user name: '%s'".formatted(userName)));
    }

    @Override
    public ShoppingCartDto addBookToCart(
            final String username,
            final AddCartItemDto addCartItemDto) {
        final Optional<ShoppingCart> cartOptional = shoppingCartRepository.findUsersCart(username);
        final ShoppingCart shoppingCart = cartOptional.orElseGet(() -> createCart(username));
        final CartItem cartItem = cartItemMapper.toModel(addCartItemDto);
        cartItem.setShoppingCart(shoppingCart);
        if (cartAlreadyContainsItem(shoppingCart, cartItem)) {
            throw new CartAlreadyContainsItem("Shopping cart already contains this item");
        }
        shoppingCart.getCartItems().add(cartItem);
        final ShoppingCart savedCart = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(savedCart);
    }

    @Override
    public ShoppingCartDto updateCartItemQuantity(
            final String username,
            final Long cartItemId,
            final UpdateQuantityDto updateQuantityDto) {
        final ShoppingCart cart = getShoppingCartByUser(username);
        final Set<CartItem> cartItems = cart.getCartItems();
        final CartItem cartItem = getCartItemById(cartItemId, cartItems);
        cartItem.setQuantity(updateQuantityDto.quantity());
        return shoppingCartMapper.toDto(shoppingCartRepository.save(cart));
    }

    @Override
    public ShoppingCartDto deleteCartItem(final Long cartItemId, final String username) {
        final ShoppingCart cart = getShoppingCartByUser(username);
        final Set<CartItem> cartItems = cart.getCartItems();
        final CartItem cartItemToBeDeleted = getCartItemById(cartItemId, cartItems);
        cartItems.remove(cartItemToBeDeleted);
        cartItemService.delete(cartItemToBeDeleted);
        return shoppingCartMapper.toDto(cart);
    }

    private CartItem getCartItemById(final Long cartItemId, final Set<CartItem> cartItems) {
        return cartItems.stream()
            .filter(i -> Objects.equals(i.getId(), cartItemId))
            .findAny()
            .orElseThrow(() -> new CartAlreadyContainsItem(
                "No item by id '%s' in this users cart".formatted(cartItemId)));
    }

    private boolean cartAlreadyContainsItem(
            final ShoppingCart shoppingCart,
            final CartItem cartItem) {
        return shoppingCart.getCartItems().stream()
            .map(CartItem::getBook)
            .map(Book::getId)
            .anyMatch((id) -> id.equals(cartItem.getBook().getId()));
    }

    private ShoppingCart createCart(final String username) {
        final ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(userService.findByEmail(username));
        return shoppingCart;
    }
}
