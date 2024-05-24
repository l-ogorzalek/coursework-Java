package com.example.library.mapper;

import com.example.library.dto.AuthorDto;
import com.example.library.model.Author;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-24T18:27:37+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240417-1011, environment: Java 17.0.10 (Eclipse Adoptium)"
)
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorDto authorToAuthorDto(Author author) {
        if ( author == null ) {
            return null;
        }

        AuthorDto authorDto = new AuthorDto();

        authorDto.setBiography( author.getBiography() );
        authorDto.setId( author.getId() );
        authorDto.setName( author.getName() );
        authorDto.setNationality( author.getNationality() );
        authorDto.setSurname( author.getSurname() );

        return authorDto;
    }

    @Override
    public Author authorDtoToAuthor(AuthorDto authorDTO) {
        if ( authorDTO == null ) {
            return null;
        }

        Author author = new Author();

        author.setBiography( authorDTO.getBiography() );
        author.setId( authorDTO.getId() );
        author.setName( authorDTO.getName() );
        author.setNationality( authorDTO.getNationality() );
        author.setSurname( authorDTO.getSurname() );

        return author;
    }
}
