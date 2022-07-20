package hackaton.serveis;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Base class for all the jpa repositorys implementations.
 */
public abstract class BaseImpl<T, U> {
    
    /**
     * The repository to work with.
     * Use the basic JpaRepo 'cause all 
     * the sql repos come from here
     * TODO: if not sql, change this!
     */
    protected JpaRepository<T, U> repo;


    /**
     * Inserts <T> into the database
     * @param entity : <T> to insert
     * @return       : <T> inserted 
     */
    public T insert(final T entity) {
        return repo.save(entity);
    }

    /**
     * Finds all <T> from database
     * @return : List of <T>
     */
    public List<T> findAll() {
        return repo.findAll();
    }

    /**
     * Finds <T> from the database by its id 
     * @param id : id to search
     * @return   : Optional<T> 
     */
    public Optional<T> findById(final U id) {
        return repo.findById(id);
    }

    /**
     * Updates <T> by it's id
     * @param entity : entity to update
     * @param id     : id to update
     * @return       : Optional<T> with updated entity if exists
     */
    public abstract Optional<T> updateFromId(final T entity, final U id);
    
    /**
     * Deletes an entity from database
     * @param entity : entity to delete
     */
    public void delete(final T entity) {
        repo.delete(entity);
    }
    
    /**
     * Deletes an entity by its id
     * @param id
     */
    public void deleteById(final U id) {
        repo.deleteById(id);
    }
    
    /**
     * Checks if an entity exists with the id provided
     * @param id : id to check
     * @return   : do the entity exists?
     */
    public boolean existById(final U id) {
        return repo.existsById(id);
    }

    /**
     * Deletes all entites from the database
     */
    public void deleteAll() {
        repo.deleteAll();
    }

    

   
    
}
