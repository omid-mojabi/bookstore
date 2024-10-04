package bookstore.controller;

import bookstore.model.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import bookstore.service.impl.BookServiceImpl;
import bookstore.util.ResourceNotFoundException;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookServiceImpl bookService;

    // List all books or search by title
    @GetMapping
    public List<Book> listBooks(@RequestParam(required = false) String filter) {
        return bookService.listBooks(filter);
    }

    // Buy a book if it's in stock
    @PostMapping("/{id}/buy")
    public ResponseEntity<String> buyBook(@PathVariable Long id, @RequestParam int quantity)
            throws ResourceNotFoundException {
        bookService.buyBook(id, quantity);
        return ResponseEntity.ok("Purchase successful!");
    }

    @PostMapping("/{id}/uploadCover")
    public ResponseEntity<String> uploadCover(@PathVariable Long id, @RequestParam("file") MultipartFile file)
            throws IOException, ResourceNotFoundException {
        // Save file to a directory, e.g., "/covers/"
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get("covers", fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Update book with image URL
        Book book = bookService.getBook(id);
        book.setCoverImageUrl(filePath.toString());
        bookService.saveBook(book);

        return ResponseEntity.ok("Cover uploaded successfully!");
    }

    @PostMapping("/{bookId}/uploadCover")
    public ResponseEntity<String> uploadBookCover(@PathVariable long bookId,
                                                  @RequestParam("file") MultipartFile file) {
        try {
            // Delegate the logic to the bookstore.service layer
            String imagePath = bookService.uploadBookCover(bookId, file);

            // Return the image path and success message
            return new ResponseEntity<>("Cover image uploaded successfully: " + imagePath, HttpStatus.OK);
        } catch (IOException e) {
            // Return error message if upload fails
            return new ResponseEntity<>("Failed to upload image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            // Handle case when book is not found
            return new ResponseEntity<>("Book not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

