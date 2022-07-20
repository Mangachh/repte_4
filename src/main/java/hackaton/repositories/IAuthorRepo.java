package hackaton.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hackaton.models.Author;

/**
 * The Author repository
 */
@Repository
public interface IAuthorRepo extends JpaRepository<Author, Long>  {
    
}
