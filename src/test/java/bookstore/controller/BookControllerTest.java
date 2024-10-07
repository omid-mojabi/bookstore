package bookstore.controller;

import bookstore.model.entity.Book;
import bookstore.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static bookstore.util.TestHelper.createBook;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    protected MockMvc mockMvc;

    private static final String bodyString=
            """
                {
                  "title": "A BOOK",
                  "author": [
                    {
                      "name": "Tom Clark's"
                    }
                  ],
                  "price": 20.00,
                  "stock": 20,
                  "category": "ARTs",
                  "coverImage": [
                    0
                  ]
                }
                """;

    @Test
    void listBooks() throws Exception {
        // Given
        when(bookService.listBooks(anyString())).thenReturn(List.of(createBook()));
        // When and Then
        this.mockMvc
                .perform(get("/v1/books").param("filter", "A BOOK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("A BOOK"));
    }

    @Test
    void addBook() throws Exception {
        //Given
        when(bookService.saveBook(any(Book.class))).thenReturn(createBook());

        //When & Then
        MockPart body = new MockPart("body", null, bodyString.getBytes(), MediaType.APPLICATION_JSON);
        MockMultipartFile multipartFile = new MockMultipartFile("coverImage", null, MediaType.APPLICATION_OCTET_STREAM.toString(), new byte[]{0});
        this.mockMvc.perform(multipart("/v1/books/add")
                        .file(multipartFile)
                        .part(body)
                        .contentType("multipart/form-data"))
                .andExpect(status().is(201));
    }

}