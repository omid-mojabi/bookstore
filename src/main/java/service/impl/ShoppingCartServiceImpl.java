package bookstore.service.impl;

import bookstore.model.entity.Book;
import bookstore.model.entity.ShoppingCart;
import bookstore.repository.CartItemRepository;
import bookstore.repository.ShoppingCartRepository;
import bookstore.service.BookService;
import bookstore.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final BookService bookService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional
    public ShoppingCart purchase(ShoppingCart shoppingCart) {
        shoppingCart.getItems().forEach(cartItem -> {
            Book book = bookService.saveBook(cartItem.getBook());
            bookService.releaseQuantity(book, cartItem.getQuantity());
            cartItem.setBook(book);
            cartItemRepository.save(cartItem);
        });

        shoppingCart.setSum(shoppingCart.getItems().stream()
                .map(cartItem -> cartItem
                        .getBook()
                        .getPrice()
                        .multiply(new BigDecimal(cartItem.getQuantity()))
                ).reduce(BigDecimal.ZERO, BigDecimal::add));
        return shoppingCartRepository.save(shoppingCart);
    }
}

