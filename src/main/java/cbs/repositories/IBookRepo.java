package cbs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cbs.models.Book;

/**
 * The book repository
 */
@Repository
public interface IBookRepo extends JpaRepository<Book, String> {
    
}
