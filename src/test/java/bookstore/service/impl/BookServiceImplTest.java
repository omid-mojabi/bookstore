package bookstore.service.impl;

import bookstore.exception.ItemInsufficientException;
import bookstore.model.entity.Book;
import bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.InsufficientResourcesException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    BookRepository repository;

    BookServiceImpl bookService;

    @Captor
    ArgumentCaptor<Book> bookArgumentCaptor;

    @BeforeEach
    public void init() {
        bookService = new BookServiceImpl(repository);
    }

    @Test
    void testListBooksWhenFilterIsNull() {
        // Given
        List<Book> books = List.of(
                Book.builder().id(1L).build(),
                Book.builder().id(2L).build()
        );
        when(repository.findAll()).thenReturn(books);

        // When
        List<Book> foundBooks = bookService.listBooks(null);

        // Then
        assertThat(foundBooks).hasSameElementsAs(books);
    }

    @Test
    void testListBooksWhenFilterIsNotNull() {
        // Given
        List<Book> books = List.of(
                Book.builder().title("A").id(1L).build()
        );
        when(repository.findBookByTitleContainingIgnoreCase("A")).thenReturn(books);

        // When
        List<Book> foundBooks = bookService.listBooks("A");

        // Then
        assertThat(foundBooks).hasSameElementsAs(books);
    }

    @Test
    void testSaveBookWhenTitleIsNotFound() {
        // Given
        Book book = Book.builder().title("A").build();
        when(repository.findBookByTitleContainingIgnoreCase("A")).thenReturn(Collections.emptyList());

        // When
        Book savedBook = bookService.saveBook(book);

        // Then
        verify(repository).save(bookArgumentCaptor.capture());
        assertThat(bookArgumentCaptor.getValue()).isEqualTo(book);
    }

    @Test
    void testSaveBookWhenTitleIsFound() {
        // Given
        Book book = Book.builder().title("A").build();
        when(repository.findBookByTitleContainingIgnoreCase("A")).thenReturn(List.of(book));

        // When
        Book savedBook = bookService.saveBook(book);

        // Then
        verify(repository, never()).save(any(Book.class));
    }

    @Test
    void testReleaseQuantity() {
        // Given
        Book book = Book.builder().title("A").stock(2).build();

        // When
        bookService.releaseQuantity(book, 1);

        // Then
        verify(repository).save(bookArgumentCaptor.capture());
        assertThat(bookArgumentCaptor.getValue().getStock()).isEqualTo(1L);
    }

    @Test
    void testReleaseQuantityWhenQuantityIsNotSufficient() {
        // Given
        Book book = Book.builder().title("A").stock(2).build();

        // When & Then
        assertThatThrownBy(() ->
                bookService.releaseQuantity(book, 3)).isInstanceOf(ItemInsufficientException.class);

    }

}