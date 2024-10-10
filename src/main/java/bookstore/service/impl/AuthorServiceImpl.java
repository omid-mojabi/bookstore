package bookstore.service.impl;

import bookstore.exception.ResourceNotFoundException;
import bookstore.model.entity.Author;
import bookstore.repository.AuthorRepository;
import bookstore.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author findByName(String filter) {
        return authorRepository.findByName(filter);
    }

    @Override
    public Author findAuthor(Long id) throws ResourceNotFoundException {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
    }

}
