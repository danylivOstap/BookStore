package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.cartitem.AddCartItemDto;
import com.example.bookstore.dto.cartitem.CartItemDto;
import com.example.bookstore.dto.cartitem.CreateCartItemDto;
import com.example.bookstore.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {ShoppingCartMapper.class, BookMapper.class})
public interface CartItemMapper {
    @Mapping(target = "shoppingCart", source = "shoppingCartId",
            qualifiedByName = "shoppingCartById")
    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookById")
    CartItem toModel(CreateCartItemDto requestDto);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "bookById")
    CartItem toModel(AddCartItemDto addCartItemDto);

    @Mapping(target = "shoppingCartId", source = "shoppingCart.id")
    @Mapping(target = "bookId", source = "book.id")
    CartItemDto toDto(CartItem cartItem);
}
