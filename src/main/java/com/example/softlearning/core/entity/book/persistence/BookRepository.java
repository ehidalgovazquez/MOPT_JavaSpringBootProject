package com.example.softlearning.core.entity.book.persistence;

import java.util.List;
import java.util.Optional;

import com.example.softlearning.core.entity.book.dtos.BookDTO;

public interface BookRepository  {

    public List<BookDTO> findAll();

    public List<BookDTO> findByFilters(String name, String author, Double maxPrice);

    public Optional<BookDTO> findById(int id);

    public List<BookDTO> findByName(String title);
 
    public List<BookDTO> findByPartialName(String name);

    public Integer countByPartialName(String name);

    public BookDTO save(BookDTO book);
    
    public void deleteById(int id);
}
