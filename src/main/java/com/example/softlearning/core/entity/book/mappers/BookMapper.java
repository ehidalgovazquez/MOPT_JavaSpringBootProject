package com.example.softlearning.core.entity.book.mappers;

import com.example.softlearning.core.entity.book.dtos.BookDTO;
import com.example.softlearning.core.entity.book.model.Book;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;

public class BookMapper {
    public static BookDTO BookToDTO(Book b){
        return new BookDTO(
            b.getId(),
            b.getName(),
            b.getPrice(),
            b.isAvailable(),
            b.getAuthor(),
            b.getPublicationDate(),
            b.getAvailabilityDate(),
            b.getWeight(),
            b.getWidth(),
            b.getHeight(),
            b.getDepth(),
            b.getIsFragile()
        );
    }

    public static Book DTOToBook(BookDTO dto) throws BuildException {
    return Book.getInstance(
        dto.getId(),
        dto.getName(),
        dto.getPrice(),
        dto.isAvailable(),
        dto.getAuthor(),
        dto.getPublicationDate(),
        dto.getAvailabilityDate(),
        dto.getWeight(),
        dto.getWidth(),
        dto.getHeight(),
        dto.getDepth(),
        dto.getIsFragile()
    );
}

}
