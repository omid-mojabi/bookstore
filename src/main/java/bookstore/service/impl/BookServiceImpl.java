package bookstore.service.impl;

import bookstore.exception.ItemInsufficientException;
import bookstore.exception.ResourceNotFoundException;
import bookstore.model.entity.Book;
import bookstore.repository.BookRepository;
import bookstore.service.AuthorService;
import bookstore.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public List<Book> listBooks(String filter) {
        if (filter != null) {
            return bookRepository.findBookByTitleContainingIgnoreCase(filter);
        }
        return bookRepository.findAll();
    }

    @Override
    public Book findBook(Long id) throws ResourceNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }


    @Override
    public Book saveBook(Book book) {
        //book.getAuthor().forEach(authorService::saveAuthor);
        List<Book> books = bookRepository.findBookByTitleContainingIgnoreCase(book.getTitle());
        if (books.isEmpty()) {
            return bookRepository.save(book);
        } else {
            return books.get(0);
        }
    }

    @Override
    public void releaseQuantity(Book book, int quantity) {
        Integer stock = book.getStock();
        if (stock - quantity >= 0) {
            book.setStock(stock - quantity);
            saveBook(book);
        } else {
            throw new ItemInsufficientException(book.getTitle());
        }
    }
}