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

@RestController
public class AuthorController {

    // post
    @Autowired
    private AuthorImpl imp;

    public static final String PREFIX = "/hack/author";
    public static final String NAME_PARAM = "name";
    public static final String SURNAME_PARAM = "surname";
    public static final String ID_PARAM = "id";

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
    // TODO: test!
    @PostMapping(PREFIX)
    public ResponseEntity<Author> postName(@RequestParam(name = NAME_PARAM, required = false) final String name,
            @RequestParam(name = SURNAME_PARAM, required = false) final String surname) {
        Author author = new Author(name, surname);
        author = imp.insert(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);

    }

    /*
     * Ruta per obtenir tots els models:
     * S'ha d'implementar usant el mètode GET
     * Ha de tornar una llista amb les instàncies del model especificat:
     */
    // checked
    // TODO: test!
    @GetMapping(PREFIX + "/all")
    public ResponseEntity<List<Author>> getAll() {
        List<Author> authors = this.imp.findAll();
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
    // TODO: test
    @GetMapping(PREFIX + "/{"+ ID_PARAM + "}")
    public ResponseEntity<Author> getById(@PathVariable(name = ID_PARAM) final Long id) {
        Optional<Author> author = this.imp.findById(id);

        if (author.isPresent()) {
            return ResponseEntity.ok().body(author.get());
        }                

        return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Error", "No Author with the ID provided").body(null);
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
    public ResponseEntity<Author> updateById(@PathVariable(name = ID_PARAM, required = true) final Long id,
            @RequestParam(name = NAME_PARAM) final String name,
            @RequestParam(name = SURNAME_PARAM) final String surname) {
        
        Optional<Author> toMod = this.imp.findById(id);

        if (toMod.isPresent()) {
            Author mod = toMod.get();

            if (name != null) {
                mod.setName(name);
            }

            if (surname != null) {
                mod.setSurname(surname);
            }

            // hemos hecho el check antes, así que no hay problema
            // ademas modificamos el mismo obj, no problem
            toMod = this.imp.updateFromId(mod, mod.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(toMod.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

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
    // TODO: test
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
