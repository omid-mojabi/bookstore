package bookstore.util;

import bookstore.model.entity.Author;
import bookstore.model.entity.Book;
import bookstore.model.enumeration.Category;

import java.math.BigDecimal;
import java.util.Set;

public class TestHelper {

    public static Book createBook() {
        return Book.builder()
                .id(1L)
                .price(BigDecimal.valueOf(20))
                .title("A BOOK")
                .author(Set.of(Author.builder()
                        .name("Tom Clark's")
                        .id(2L).build()))
                .stock(20)
                .category(Category.ARTs)
                .coverImage(null)
                .build();
    }
}
