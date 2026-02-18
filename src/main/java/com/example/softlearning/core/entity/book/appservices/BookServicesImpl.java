package com.example.softlearning.core.entity.book.appservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.softlearning.core.entity.book.dtos.BookDTO;
import com.example.softlearning.core.entity.book.mappers.BookMapper;
import com.example.softlearning.core.entity.book.persistence.BookRepository;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializer;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.Serializers;
import com.example.softlearning.core.entity.sharedkernel.appservices.serializers.SerializersCatalog;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.BuildException;
import com.example.softlearning.core.entity.sharedkernel.model.exceptions.ServiceException;

@Controller
public class BookServicesImpl implements BookServices {

    @Autowired
    private BookRepository bookRepository;
    private Serializer<BookDTO> serializer;

    protected BookDTO getDTOById(int id)  {
        return bookRepository.findById(id).orElse(null);
    }

    protected BookDTO getById(int id) throws ServiceException {
        BookDTO bdto = this.getDTOById(id);

        if ( bdto == null ) {
            throw new ServiceException("book " + id + " not found");
        }
        
        return bdto;
    }
    
    protected BookDTO checkInputData(String book) throws ServiceException {
        try {
            BookDTO bdto = (BookDTO) this.serializer.deserialize(book, BookDTO.class);
            BookMapper.DTOToBook(bdto);
            return bdto;
        } catch (BuildException e) {
            throw new ServiceException("error in the input book data: " + e.getMessage());
        }
    }

    protected BookDTO addBook(String book) throws ServiceException {
        BookDTO bdto = this.checkInputData(book);
          
        if (this.getDTOById(bdto.getId()) == null) {
            return bookRepository.save(bdto);
        } 
        
        throw new ServiceException("book " + bdto.getId() + " already exists");
    }

    protected BookDTO updateBook(String book) throws ServiceException {
        BookDTO bdto = this.checkInputData(book);
        this.getById(bdto.getId());
        return bookRepository.save(bdto);
    }

    @Override
    public String getAllToJson() throws ServiceException {
        Iterable<BookDTO> books = bookRepository.findAll();
        return SerializersCatalog.getInstance(Serializers.JSON_BOOK).serialize(books);
    }

    @Override
    public String getAllToXml() throws ServiceException {
        Iterable<BookDTO> books = bookRepository.findAll();
        return SerializersCatalog.getInstance(Serializers.XML_BOOK).serialize(books);
    }

    @Override
    public String getByIdToJson(int id) throws ServiceException {
        return SerializersCatalog.getInstance(Serializers.JSON_BOOK).serialize(this.getById(id));
    }

    @Override
    public String getByIdToXml(int id) throws ServiceException {
        return SerializersCatalog.getInstance(Serializers.XML_BOOK).serialize(this.getById(id));
    }
    
    @Override
    public String addFromJson(String book) throws ServiceException {
        this.serializer = SerializersCatalog.getInstance(Serializers.JSON_BOOK);
        return serializer.serialize(this.addBook(book));
    }

    @Override
    public String addFromXml(String book) throws ServiceException {
        this.serializer = SerializersCatalog.getInstance(Serializers.XML_BOOK);
        return serializer.serialize(this.addBook(book));
    }

    @Override
    public String updateOneFromJson(String book) throws ServiceException {
        this.serializer = SerializersCatalog.getInstance(Serializers.JSON_BOOK);
        return serializer.serialize(this.updateBook(book));
    }

    @Override
    public String updateOneFromXml(String book) throws ServiceException {
        this.serializer = SerializersCatalog.getInstance(Serializers.XML_BOOK);
        return serializer.serialize(this.updateBook(book));
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        this.getById(id);
        bookRepository.deleteById(id);
    }

    @Override
    public String findByFiltersToJson(String name, String author, Double maxPrice) throws ServiceException {
        Iterable<BookDTO> books = bookRepository.findByFilters(name, author, maxPrice);
        return SerializersCatalog.getInstance(Serializers.JSON_BOOK).serialize(books);
    }

    @Override
    public String findByFiltersToXml(String name, String author, Double maxPrice) throws ServiceException {
        Iterable<BookDTO> books = bookRepository.findByFilters(name, author, maxPrice);
        return SerializersCatalog.getInstance(Serializers.XML_BOOK).serialize(books);
    }
}