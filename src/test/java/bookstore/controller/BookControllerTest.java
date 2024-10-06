package bookstore.controller;

import bookstore.model.entity.Book;
import bookstore.model.enumeration.Category;
import bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({BookController.class})
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    protected MockMvc mockMvc;

    @Test
    void listBooks() throws Exception {
        // Given
        when(bookService.listBooks(anyString())).thenReturn(List.of(createBook()));
        // When and Then
        this.mockMvc
                .perform(get("/api/books").param("filter", "A BOOK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("A BOOK"));
    }


    Book createBook() {
        return Book.builder()
                .id(1L)
                .price(BigDecimal.valueOf(20))
                .title("A BOOK")
                //.author(Collections.emptyList())
                .stock(20)
                .category(Category.Business)
                .coverImage(null)
                .build();
    }
}