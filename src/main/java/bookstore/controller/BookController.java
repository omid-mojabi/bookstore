package bookstore.controller;

import bookstore.dto.BookDto;
import bookstore.exception.ResourceNotFoundException;
import bookstore.model.entity.Book;
import bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static bookstore.util.Mapper.mapper;

@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // List all books or search by title
    @GetMapping
    public List<BookDto> listBooks(@RequestParam(required = false) String filter) {
        return bookService.listBooks(filter).stream().map(book -> mapper().toBookDto(book)).toList();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BookDto> addBook(@RequestParam(name = "coverImage", required = false) MultipartFile multipartFile, @RequestPart(name = "body") BookDto book) throws IOException {
        book.setCoverImage(multipartFile.getBytes());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper().toBookDto(bookService.saveBook(mapper().fromBookDto(book))));

    }

    // Find a specific book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) throws ResourceNotFoundException {
        Book book = bookService.findBook(id);
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(mapper().toBookDto(book));
    }
}
