package bookstore.service.impl;

import bookstore.model.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import bookstore.repository.BookRepository;
import bookstore.service.BookService;
import bookstore.util.ResourceNotFoundException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> listBooks(String filter) {
        if (filter != null) {
            return bookRepository.findByTitleContainingIgnoreCase(filter);
        }
        return bookRepository.findAll();
    }

    @Override
    public Book getBook(Long id) throws ResourceNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    @Override
    public Book buyBook(Long id, int quantity) throws ResourceNotFoundException {
        Book book = getBook(id);
        book.buyBook(quantity);
        return bookRepository.save(book);
    }

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public String uploadBookCover(Long bookId, MultipartFile file) throws IOException {
        // Check if the book exists
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (!bookOptional.isPresent()) {
            throw new IllegalArgumentException("Book not found with ID: " + bookId);
        }

        Book book = bookOptional.get();

        // Create the uploads directory if it doesn't exist
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Generate the file path where the image will be saved
        String filename = "book_" + bookId + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + filename);

        // Save the file locally
        Files.write(filePath, file.getBytes());

        // Save the image path to the book object
        book.setCoverImagePath(filePath.toString());
        bookRepository.save(book);

        // Return the file path for confirmation
        return filePath.toString();
    }

}
