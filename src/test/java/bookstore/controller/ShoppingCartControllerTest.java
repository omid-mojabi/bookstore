package bookstore.controller;

import bookstore.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShoppingCartControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ShoppingCartService shoppingCartService;

    public static final String body = """
            {
              "items": [
                {
                  "book": {
                    "title": "title_9",
                    "author": [
                      {
                        "name": "name_0"
                      }
                    ],
                    "price": 20.00,
                    "stock": 3,
                    "category": "ARTs",
                    "coverImage": [
                      0
                    ]
                  },
                  "quantity": 1
                }
              ]
            }
            """;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ShoppingCartController(shoppingCartService))
                .setControllerAdvice()
                .build();
    }

    @Test
    void testPurchase() throws Exception {
        mockMvc.perform(post("/v1/shoppingcart/purchase")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }
}