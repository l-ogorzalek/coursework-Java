package com.example.library.mapper;

import com.example.library.dto.AuthorDto;
import com.example.library.dto.BookCreateRequest;
import com.example.library.dto.BookResponse;
import com.example.library.model.Author;
import com.example.library.model.Book;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-24T18:27:37+0200",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240417-1011, environment: Java 17.0.10 (Eclipse Adoptium)"
)
public class BookMapperImpl implements BookMapper {

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    @Override
    public BookResponse bookToBookResponse(Book book) {
        if ( book == null ) {
            return null;
        }

        BookResponse bookResponse = new BookResponse();

        bookResponse.setAuthor( authorMapper.authorToAuthorDto( book.getAuthor() ) );
        bookResponse.setCoAuthors( authorListToAuthorDtoList( book.getCoAuthors() ) );
        bookResponse.setIsbn( book.getIsbn() );
        bookResponse.setNumberOfPages( book.getNumberOfPages() );
        bookResponse.setPublicationYear( book.getPublicationYear() );
        bookResponse.setPublisher( book.getPublisher() );
        bookResponse.setTitle( book.getTitle() );

        return bookResponse;
    }

    @Override
    public Book bookCreateRequestToBook(BookCreateRequest bookCreateRequest) {
        if ( bookCreateRequest == null ) {
            return null;
        }

        Book book = new Book();

        book.setAuthor( authorMapper.authorDtoToAuthor( bookCreateRequest.getAuthor() ) );
        book.setCoAuthors( authorDtoListToAuthorList( bookCreateRequest.getCoAuthors() ) );
        book.setGenre( bookCreateRequest.getGenre() );
        book.setIsbn( bookCreateRequest.getIsbn() );
        book.setLanguage( bookCreateRequest.getLanguage() );
        book.setNumberOfPages( bookCreateRequest.getNumberOfPages() );
        book.setPublicationYear( bookCreateRequest.getPublicationYear() );
        book.setPublisher( bookCreateRequest.getPublisher() );
        book.setTitle( bookCreateRequest.getTitle() );

        return book;
    }

    protected List<AuthorDto> authorListToAuthorDtoList(List<Author> list) {
        if ( list == null ) {
            return null;
        }

        List<AuthorDto> list1 = new ArrayList<AuthorDto>( list.size() );
        for ( Author author : list ) {
            list1.add( authorMapper.authorToAuthorDto( author ) );
        }

        return list1;
    }

    protected List<Author> authorDtoListToAuthorList(List<AuthorDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Author> list1 = new ArrayList<Author>( list.size() );
        for ( AuthorDto authorDto : list ) {
            list1.add( authorMapper.authorDtoToAuthor( authorDto ) );
        }

        return list1;
    }
}
