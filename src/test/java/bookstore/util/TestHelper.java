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
                .price(BigDecimal.valueOf(34))
                .title("The Professional Scrum Team")
                .author(Set.of(Author.builder()
                        .name("Peter Gotz")
                        .id(2L).build()))
                .stock(15)
                .category(Category.TECHNOLOGY)
                .coverImage(null)
                .build();
    }
}
