package com.example.library.mapper;

import com.example.library.dto.AuthorDto;
import com.example.library.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDto authorToAuthorDto(Author author);

    @Mapping(target = "dateOfBirth", ignore = true)
    @Mapping(target = "books", ignore = true)
    @Mapping(target = "coAuthoredBooks", ignore = true)
    Author authorDtoToAuthor(AuthorDto authorDTO);
}
