package hackaton.controller;

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

import hackaton.models.Author;
import hackaton.serveis.AuthorImpl;

/**
 * RestController for the Author. 
 * Enpoints for:
 *   - insert Author
 *   - update Author
 *   - delete Author
 *   - list_all Authors
 *   - list by id Author
 */
@RestController
public class AuthorController {

    @Autowired
    private AuthorImpl imp;

    /**
     * prefix for all the endpoints.
     */
    public static final String PREFIX = "/hack/author";
    public static final String NAME_PARAM = "name";
    public static final String SURNAME_PARAM = "surname";
    public static final String ID_PARAM = "id";

    /**
     * Save an author into the database 
     * @param name    : the authors name
     * @param surname : the authors surname
     * @return        : ResponseEntity & the author inserted
     */
    @PostMapping(PREFIX)
    public ResponseEntity<Author> postAuthor(@RequestParam(name = NAME_PARAM, required = false) final String name,
            @RequestParam(name = SURNAME_PARAM, required = false) final String surname) {
        Author author = new Author(name, surname);
        author = imp.insert(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);

    }

    /**
     * Gets all the authors from the database
     * @return : ResponseEntity & List of authors
     */
    @GetMapping(PREFIX + "/all")
    public ResponseEntity<List<Author>> getAll() {
        List<Author> authors = this.imp.findAll();
        return ResponseEntity.ok().body(authors);
    }

    /**
     * Gets an author for it's id. If not exists, returns 404
     * @param id : the id to check
     * @return   : ResponseEntity<Author>
     */
    @GetMapping(PREFIX + "/{"+ ID_PARAM + "}")
    public ResponseEntity<Author> getById(@PathVariable(name = ID_PARAM) final Long id) {
        Optional<Author> author = this.imp.findById(id);

        if (author.isPresent()) {
            return ResponseEntity.ok().body(author.get());
        }                

        return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Error", "No Author with the ID provided").body(null);
    }

    /**
     * Updates an author if exists. If so, returns 201, else 404
     * @param id     : id to check 
     * @param name   : new name for the author
     * @param surname : new surname for the author
     * @return        : ResponseEntity & modified author
     */
    @PutMapping(PREFIX + "/{"+ ID_PARAM + "}")
    public ResponseEntity<Author> updateById(@PathVariable(name = ID_PARAM, required = true) final Long id,
            @RequestParam(name = NAME_PARAM, required = false) final String name,
            @RequestParam(name = SURNAME_PARAM, required = false) final String surname) {
        
        Optional<Author> toMod = this.imp.findById(id);

        if (toMod.isPresent()) {
            Author mod = toMod.get();

            if (name != null) {
                mod.setName(name);
            }

            if (surname != null) {
                mod.setSurname(surname);
            }

            // checked before, use the same instance to keep it easy
            toMod = this.imp.updateFromId(mod, mod.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(toMod.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

    }

    /**
     * Deletes an author if exists
     * @param id : id to check
     * @return   : Responseentity
     */
    @DeleteMapping(PREFIX + "/{"+ ID_PARAM + "}")
    public ResponseEntity<Boolean> deleteModel(@PathVariable(name = ID_PARAM, required = true) final Long id) {
        // hay varias maneras de hacer esto, podemos hacer un existById en el repo, 
        // podemos pillar el author y demás,... 
        // creo que usaré un exists

        if (this.imp.existById(id)) {
            this.imp.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        
    }
}
