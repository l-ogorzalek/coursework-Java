package com.example.library.model;

import org.hibernate.annotations.UuidGenerator;
import lombok.Data;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "author")
public class Author {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;
    private String surname;
    private String nationality;
    private LocalDate dateOfBirth;

    @Lob
    private String biography;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    @ManyToMany(mappedBy = "coAuthors")
    private List<Book> coAuthoredBooks;
}

/**

 * Powyższa encja posiada relację OneToMany względem encji 'Book', co oznacza, że jeden autor może być przypisany do wielu książek.
 * Z kolei encja 'Book' posiada odwrotną relację ManyToOne względem encji 'Author', czyli w tym przypadku przyjmujemy założenie, 
 * że jedna książka nie może mieć przypisanego więcej niż jednego autora. 
 *      ^Powyższy koncept został zmieniony, teraz książka ma głównego autora oraz listę współautorów^
 * 
 * Inne rodzaje relacji: 
 * - OneToOne - jeden do jednego (np. jeden autor ma jeden pseudonim)
 * - ManyToMany - wiele do wielu (np. jeden autor może napisać wiele książek, a jedna książka może mieć wielu autorów) 
 */