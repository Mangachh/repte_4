package cbs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cbs.models.Book;
import cbs.serveis.BookImpl;

/**
 * RestController for the Book. 
 * Enpoints for:
 *   - insert Book
 *   - update Book
 *   - delete Book
 *   - list_all Book
 *   - list by id Book
 */
@RestController
public class BookController {
    // post
    @Autowired
    private BookImpl imp;

    public static final String PREFIX = "/cbs/book";
    public static final String NAME_PARAM = "name";
    public static final String ID_PARAM = "isbn";

     /**
     * Save a book into the database 
     * @param name    : the books name
     * @param surname : the books surname
     * @return        : ResponseEntity & the author inserted
     */
    @PostMapping(PREFIX)
    public ResponseEntity<Book> insertBook(@RequestParam(name = NAME_PARAM, required = false) final String name,
            @RequestParam(name = ID_PARAM, required = false) final String isbn) {
        Book author = new Book(isbn, name);

        if (imp.existById(isbn)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("Error", "A book already exists with the isbn provided").body(null);
        }
        author = imp.insert(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);

    }

    /**
     * Gets all the books from the database
     * @return : ResponseEntity & List of authors
     */
    @GetMapping(PREFIX + "/all")
    public ResponseEntity<List<Book>> getAll() {
        List<Book> authors = this.imp.findAll();
        return ResponseEntity.ok().body(authors);
    }

    /**
     * Gets a book for it's id. If not exists, returns 404
     * @param id : the id to check
     * @return   : ResponseEntity & book
     */
    @GetMapping(PREFIX + "/{"+ ID_PARAM + "}")
    public ResponseEntity<Book> getById(@PathVariable(name = ID_PARAM) final String id) {
        Optional<Book> author = this.imp.findById(id);

        if (author.isPresent()) {
            return ResponseEntity.ok().body(author.get());
        }                

        return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Error", "No Book with the ISBN provided").body(null);
    }

    /**
     * Updates a book if exists. If so, returns 201, else 404
     * @param id     : id to check 
     * @param name   : new name for the book
     * @param surname : new surname for the book
     * @return        : ResponseEntity & modified book
     */
    @PutMapping(PREFIX + "/{"+ ID_PARAM + "}")
    public ResponseEntity<Book> updateById(@PathVariable(name = ID_PARAM, required = true) final String id,
            @RequestParam(name = NAME_PARAM) final String name) {
        
        Optional<Book> toMod = this.imp.findById(id);

        if (toMod.isPresent()) {
            Book mod = toMod.get();

            if (name != null) {
                mod.setName(name);
            }
            // hemos hecho el check antes, así que no hay problema
            // ademas modificamos el mismo obj, no problem
            toMod = this.imp.updateFromId(mod, mod.getIsbn());

            return ResponseEntity.status(HttpStatus.CREATED).body(toMod.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Error", "No Book with the ISBN provided").body(null);
    }

    /**
     * Deletes a book if exists
     * @param id : id to check
     * @return   : Responseentity
     */
    @DeleteMapping(PREFIX + "/{"+ ID_PARAM + "}")
    public ResponseEntity<Boolean> deleteModel(@PathVariable(name = ID_PARAM, required = true) final String id) {

        if (this.imp.existById(id)) {
            this.imp.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        
    }    
}
