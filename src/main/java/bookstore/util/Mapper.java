package bookstore.util;

import bookstore.dto.AuthorDto;
import bookstore.dto.BookDto;
import bookstore.dto.CartItemDto;
import bookstore.dto.ShoppingCartDto;
import bookstore.model.entity.Author;
import bookstore.model.entity.Book;
import bookstore.model.entity.CartItem;
import bookstore.model.entity.ShoppingCart;

import java.util.stream.Collectors;


public class Mapper {
    private static final Mapper instance = new Mapper();

    private Mapper() {

    }

    public static Mapper mapper() {
        return instance;
    }

    public Book fromBookDto(BookDto book) {
        return Book.builder().price(book.getPrice())
                .title(book.getTitle())
                .author(book.getAuthor().stream().map(this::fromAuthorDto).collect(Collectors.toSet()))
                .stock(book.getStock())
                .category(book.getCategory())
                .coverImage(book.getCoverImage())
                .build();
    }

    public BookDto toBookDto(Book book) {
        return BookDto.builder().price(book.getPrice())
                .title(book.getTitle())
                .author(book.getAuthor().stream().map(this::toAuthorDto).collect(Collectors.toSet()))
                .stock(book.getStock())
                .category(book.getCategory())
                .coverImage(book.getCoverImage())
                .build();
    }

    public Author fromAuthorDto(AuthorDto author) {
        return Author.builder().name(author.getName()).build();
    }

    public AuthorDto toAuthorDto(Author author) {
        return AuthorDto.builder().name(author.getName()).build();
    }

    public ShoppingCartDto toShoppingCardDto(ShoppingCart shoppingCart) {
        return ShoppingCartDto.builder().items(shoppingCart
                        .getItems()
                        .stream()
                        .map(this::toCartItemDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public CartItemDto toCartItemDto(CartItem cartItem) {
        return CartItemDto.builder()
                .book(toBookDto(cartItem.getBook()))
                .quantity(cartItem.getQuantity())
                .build();
    }

    public ShoppingCart fromShoppingCartDto(ShoppingCartDto shoppingCart) {
        return ShoppingCart.builder().items(shoppingCart
                        .getItems()
                        .stream()
                        .map(this::fromCartItemDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public CartItem fromCartItemDto(CartItemDto cartItem) {
        return CartItem.builder()
                .book(fromBookDto(cartItem.getBook()))
                .quantity(cartItem.getQuantity())
                .build();
    }
}
