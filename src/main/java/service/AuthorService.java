package service;

import model.entity.Author;
import util.ResourceNotFoundException;

import java.util.List;

public interface AuthorService {

    public List<Author> listAuthors(String filter);

    public Author getAuthor(Long id) throws ResourceNotFoundException;

    public void saveAuthor(Author author);
}
