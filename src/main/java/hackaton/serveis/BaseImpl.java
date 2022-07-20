package hackaton.serveis;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseImpl<T, U> {
    
    
    protected JpaRepository<T, U> repo;

    /*public BaseImpl(JpaRepository<T, U> repo) {
        this.repo = repo;
    }*/

    // create model
    public T insert(final T entity) {
        return repo.save(entity);
    }

    // all models
    public List<T> findAll() {
        return repo.findAll();
    }

    // obtain from id
    public Optional<T> findById(final U id) {
        return repo.findById(id);
    }

    // update from id
    // as the update varies between entities, it's easier
    // to modify that in class, instead of working with some
    // magic
    public abstract Optional<T> updateFromId(final T entity, final U id);
    
    // esborrar
    public void delete(final T entity) {
        repo.delete(entity);
    }
    
    public void deleteById(final U id) {
        repo.deleteById(id);
    }
    
    public boolean existById(final U id) {
        return repo.existsById(id);
    }

    public void deleteAll() {
        repo.deleteAll();
    }

    

   
    
}
