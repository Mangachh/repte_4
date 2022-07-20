package hackaton.demo.serveis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import hackaton.models.Book;
import hackaton.serveis.BookImpl;

@SpringBootTest
@Transactional

public class BookImplTest {
    @Autowired    
    private BookImpl service;

    List<Book> books;

    @BeforeEach
    public void populateBooks() {
        this.books = new ArrayList<Book>();
        this.books.add(new Book("9781603094986", "Ballad For Sophie"));
        this.books.add(new Book("0670813028", "It"));
        this.books.add(new Book("9788472238374", "Kitchen"));
    }

    @BeforeEach
    public void deleteServiceList() {
        this.service.deleteAll();
    }

    private void insertAll() {
        this.books.stream().forEach(p -> service.insert(p));
    }

    @Test
    public void insertBookTest() {
        Book expected = this.books.get(0);
        Book actual = this.service.insert(expected);

        assertEquals(expected, actual);
    }

    @Test
    public void findAllTest() {

        this.insertAll();

        List<Book> actual = this.service.findAll();

        assertTrue(actual.size() == this.books.size());
        
        assertFalse(Collections.disjoint(actual, this.books));
    }
    
    @Test
    public void findByIdTest() {
        Book expected = books.get(2);

        // los metemos todos y así emos que funca bien
        this.insertAll();

        Optional<Book> actual = service.findById(expected.getIsbn());
        
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
        
    }
    
    @Test
    public void updateFromIdTest() {
        this.insertAll();
        Book modified = this.books.get(1);
        modified.setName("New Book by me");        

        Optional<Book> actual = service.updateFromId(modified, modified.getIsbn());
        
        assertTrue(actual.isPresent());
        assertEquals(modified, actual.get());
    
    }
    
    @Test
    public void deleteTest() {
        this.insertAll();
        Book toDelete = this.books.get(0);
        service.delete(toDelete);

        List<Book> books = service.findAll();

        assertFalse(books.contains(toDelete));
    }
    
    @Test
    public void deleteByIdTest() {
        this.insertAll();
        Book toDelete = books.get(0);
        service.deleteById(toDelete.getIsbn());

        List<Book> books = service.findAll();

        assertFalse(books.contains(toDelete));
    }
}
