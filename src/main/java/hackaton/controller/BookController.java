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

import hackaton.models.Book;
import hackaton.serveis.BookImpl;

@RestController
public class BookController {
    // post
    @Autowired
    private BookImpl imp;

    public static final String PREFIX = "/hack/book";
    public static final String NAME_PARAM = "name";
    public static final String ID_PARAM = "isbn";

    /*
     * Ruta de crear un model:
     * S'ha d'implementar amb el mètode POST
     * El cos de la petició HTTP ha de complir tots els camps del model, una vegada
     * això sigui validat es procedirà a crear el model o tornar un missatge a
     * l'usuari com que hi ha hagut un error.
     * Un cop creat el model s'ha de tornar amb un codi de resposta 201, i el cos de
     * la resposta ha de contenir el model creat.
     */
    // checked
    @PostMapping(PREFIX)
    public ResponseEntity<Book> postName(@RequestParam(name = NAME_PARAM, required = false) final String name,
            @RequestParam(name = ID_PARAM, required = false) final String isbn) {
        Book author = new Book(isbn, name);
        author = imp.insert(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);

    }

    /*
     * Ruta per obtenir tots els models:
     * S'ha d'implementar usant el mètode GET
     * Ha de tornar una llista amb les instàncies del model especificat:
     */
    // checked
    @GetMapping(PREFIX + "/all")
    public ResponseEntity<List<Book>> getAll() {
        List<Book> authors = this.imp.findAll();
        return ResponseEntity.ok().body(authors);
    }

    /*
     * Ruta per obtenir un model segons identificador:
     * 
     * Usar el mètode GET
     * 
     * Aquesta ruta ha d'obtenir un identificador per poder buscar el model, la
     * manera més senzilla de fer-ho és posar-lo a la URL. Per exemple:
     * http://localhost:3000/models/asdf1jk2 .
     * 
     * Quan s'hagi realitzat la cerca d'acord amb l'identificador, s'ha de tornar un
     * json amb la instància del model trobat.
     * 
     * En cas que no s'hagi trobat el model s'ha de tornar un codi de resposta 404.
     * 
     */
    // checked
    @GetMapping(PREFIX + "/{"+ ID_PARAM + "}")
    public ResponseEntity<Book> getById(@PathVariable(name = ID_PARAM) final String id) {
        Optional<Book> author = this.imp.findById(id);

        if (author.isPresent()) {
            return ResponseEntity.ok().body(author.get());
        }                

        return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Error", "No Book with the ISBN provided").body(null);
    }

    /*
     * Ruta per fer l'update:
     * 
     * S'ha d'implementar amb el mètode PUT
     * 
     * El cos de la petició HTTP ha de complir tots els camps del model, una vegada
     * això sigui validat es procedirà a modificar el model o tornar un missatge a
     * l'usuari com que hi ha hagut un error.
     * 
     * Un cop modificat el model s'ha de tornar amb un codi de resposta 201, i el
     * cos de la resposta ha de contenir el model modificat.
     * 
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

    /*
     * 
     * Ruta per esborrar un model
     * Usar el mètode DELETE
     * Aquesta ruta ha d'obtenir un identificador per poder buscar el model, la
     * manera més senzilla de fer-ho és posar-lo a la URL, com s'ha explicat abans.
     * Quan s'hagi realitzat la cerca d'acord a l'identificador, es procedirà a
     * esborrar el model ia respondre amb un 201 de codi de resposta.
     * En cas que no s'hagi trobat el model s'ha de tornar un codi de resposta 404.
     * 
     */
    // checkd
    @DeleteMapping(PREFIX + "/{"+ ID_PARAM + "}")
    public ResponseEntity<Boolean> deleteModel(@PathVariable(name = ID_PARAM, required = true) final String id) {

        if (this.imp.existById(id)) {
            this.imp.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(true);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        
    }    
}
