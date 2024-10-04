package bookstore.service.impl;

import bookstore.model.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bookstore.repository.AuthorRepository;
import bookstore.service.AuthorService;
import bookstore.util.ResourceNotFoundException;

import java.util.List;


@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> listAuthors(String filter) {
        if (filter != null) {
            return authorRepository.findByTitleContainingIgnoreCase(filter);
        }
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthor(Long id) throws ResourceNotFoundException {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));
    }

    @Override
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }
}
