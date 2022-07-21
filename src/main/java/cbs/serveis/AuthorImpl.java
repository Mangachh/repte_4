package cbs.serveis;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cbs.models.Author;
import cbs.repositories.IAuthorRepo;

@Service
public class AuthorImpl extends BaseImpl<Author, Long> {
    @Autowired
    private IAuthorRepo authorRepo;
    
    // not the prettiest thing, but it works
    @PostConstruct
    private void initRepo() {
        super.repo = this.authorRepo;
    }
    

    @Override
    public Optional<Author> updateFromId(final Author entity, final Long id) {
        // get by id
        Optional<Author> old = authorRepo.findById(id);

        // if present, modify and save
        if (old.isPresent()) {
            old.get().setName(entity.getName());
            old.get().setSurname(entity.getSurname());
            authorRepo.save(old.get());
        }

        return old;
    }

}
