package com.example.library.mapper;

import com.example.library.dto.BookCreateRequest;
import com.example.library.dto.BookResponse;
import com.example.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = AuthorMapper.class)
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookResponse bookToBookResponse(Book book);

    @Mapping(target = "id", ignore = true)
    Book bookCreateRequestToBook(BookCreateRequest bookCreateRequest);
}
