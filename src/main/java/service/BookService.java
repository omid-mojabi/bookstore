package bookstore.service;

import bookstore.exception.ResourceNotFoundException;
import bookstore.model.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> listBooks(String filter);

    Book findBook(Long id) throws ResourceNotFoundException;

    Book saveBook(Book book);

    void releaseQuantity(Book book,int quantity);
}
