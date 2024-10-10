package bookstore.service;

import bookstore.exception.ResourceNotFoundException;
import bookstore.model.entity.Author;

public interface AuthorService {

    Author findByName(String filter);

    Author findAuthor(Long id) throws ResourceNotFoundException;

}
