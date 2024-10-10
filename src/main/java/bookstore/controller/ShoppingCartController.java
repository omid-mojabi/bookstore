package bookstore.controller;

import bookstore.dto.ShoppingCartDto;
import bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static bookstore.util.Mapper.mapper;

@RestController
@RequestMapping("/v1/shoppingcart")
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping("/purchase")
    public ResponseEntity<ShoppingCartDto> purchase(@RequestBody ShoppingCartDto cart) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper().toShoppingCardDto(shoppingCartService.purchase(mapper().fromShoppingCartDto(cart))));
    }
}

