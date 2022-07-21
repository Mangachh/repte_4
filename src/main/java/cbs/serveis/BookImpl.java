package cbs.serveis;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cbs.models.Book;
import cbs.repositories.IBookRepo;

@Service
public class BookImpl extends BaseImpl<Book, String> {
   
    @Autowired
    private IBookRepo bookRepo;

    @PostConstruct
    private void addRepo() {
        super.repo = bookRepo;
    }

    
    @Override
    public Optional<Book> updateFromId(Book entity, String id) {

        // get by id
        Optional<Book> old = bookRepo.findById(id);

        // if present, modify and save
        if (old.isPresent()) {
            old.get().setName(entity.getName());
            bookRepo.save(old.get());
        }

        return old;

    }

}
