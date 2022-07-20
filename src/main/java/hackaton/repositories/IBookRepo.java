package hackaton.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hackaton.models.Book;

@Repository
public interface IBookRepo extends JpaRepository<Book, String> {
    
}
