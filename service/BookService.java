package bookstore.service;

import bookstore.model.entity.Book;
import org.springframework.web.multipart.MultipartFile;
import bookstore.util.ResourceNotFoundException;

import java.io.IOException;
import java.util.List;

public interface BookService {

    public List<Book> listBooks(String filter);

    public Book getBook(Long id) throws ResourceNotFoundException;

    public Book buyBook(Long id, int quantity) throws ResourceNotFoundException;

    public void saveBook(Book book);

    public String uploadBookCover(Long bookId, MultipartFile file) throws IOException;
}
