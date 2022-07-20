package hackaton.models;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Entity
@Table(name = "books")
/**
 * Book model
 * To keep things easy we use this class as entity for the database
 * as to work with the controller. 
 */
public class Book {

    @Id
    @Column(length = 13)
    /**
     * The isbn of the book. Used as an id. 
     * TODO: in production, change to a auto-generated id
     */
    private String isbn;

    /**
     * Name of the book
     */
    private String name;

    /**
     * Constructor only with name
     * @param name
     */
    public Book(final String name) {
        this.name = name;
    }

    
}
