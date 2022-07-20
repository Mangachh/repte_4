package hackaton.demo.serveis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hackaton.models.Author;
import hackaton.serveis.AuthorImpl;

@SpringBootTest
@Transactional
public class AuthorImplTest {

    @Autowired    
    private AuthorImpl service;

    List<Author> authors;

    @BeforeEach
    public void populateAuthors() {
        this.authors = new ArrayList<Author>();
        this.authors.add(new Author("Pepote", "Maximus"));
        this.authors.add(new Author("Mariela", "Minumus"));
        this.authors.add(new Author("Filipondio", "Equalimus"));
    }

    @BeforeEach
    public void deleteServiceList() {
        this.service.deleteAll();
    }

    private void insertAll() {
        this.authors.stream().forEach(p -> service.insert(p));
    }

    @Test
    public void insertAuthorTest() {
        Author expected = this.authors.get(0);
        Author actual = this.service.insert(expected);

        assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() {

        this.insertAll();

        List<Author> actual = this.service.findAll();

        assertTrue(actual.size() == this.authors.size());
        
        assertFalse(Collections.disjoint(actual, this.authors));
    }
    
    @Test
    public void findByIdTest() {
        Author expected = authors.get(2);

        // los metemos todos y así emos que funca bien
        this.insertAll();

        Optional<Author> actual = service.findById(expected.getId());
        
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
        
    }
    
    @Test
    public void updateFromIdTest() {
        this.insertAll();
        Author modified = this.authors.get(1);
        modified.setName("modifiedName");
        modified.setSurname("modifiedSurname");

        Optional<Author> actual = service.updateFromId(modified, modified.getId());
        
        assertTrue(actual.isPresent());
        assertEquals(modified, actual.get());
    
    }
    
    @Test
    public void deleteTest() {
        this.insertAll();
        Author toDelete = this.authors.get(0);
        service.delete(toDelete);

        List<Author> authors = service.findAll();

        assertFalse(authors.contains(toDelete));
    }
    
    @Test
    public void deleteByIdTest() {
        this.insertAll();
        Author toDelete = authors.get(0);
        service.deleteById(toDelete.getId());

        List<Author> authors = service.findAll();

        assertFalse(authors.contains(toDelete));
    }
}
