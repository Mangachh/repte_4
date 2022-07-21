package cbs.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Getter
@Setter
@Entity
@Table(name = "authors")
/**
 * Author model
 * To keep things easy we use this class as entity for the database
 * as to work with the controller. 
 */
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * The Author id. The value is generated automatically with an "identity" generation
     */
    private Long id;
    
    /**
     * Name of the auhtor
     */
    private String name;

    /**
     * Surname of the author
     */
    private String surname;

    /**
     * Constructor without id
     * @param name
     * @param surname
     */
    public Author(final String name, final String surname) {
        this.name = name;
        this.surname = surname;
    }
    
}
