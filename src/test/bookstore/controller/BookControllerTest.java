package bookstore.controller;

import bookstore.model.entity.Book;
import bookstore.model.enumeration.Category;
import bookstore.service.BookService;
import bookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest({BookController.class})
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    protected MockMvc mockMvc;

    @Test
    void listBooks() throws Exception {
        when(bookService.listBooks(anyString())).thenReturn(List.of(createBook()));
        this.mockMvc
                .perform(get("/api/books").param("filter","A BOOK"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));

    }

    @Test
    void buyBook() {
    }

    @Test
    void uploadCover() {
    }

    @Test
    void uploadBookCover() {
    }

    Book createBook() {
        return Book.builder()
                .id(1L)
                .price(20.00)
                .title("A BOOK")
                .author(Collections.emptyList())
                .stock(20)
                .category(Category.Business)
                .coverImageUrl(null)
                .build();
    }
}