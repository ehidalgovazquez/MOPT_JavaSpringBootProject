package com.example.softlearning.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.softlearning.core.entity.book.dtos.BookDTO;
import com.example.softlearning.core.entity.book.persistence.BookRepository;

import jakarta.transaction.Transactional;

@Repository
public interface JpaBookRepository extends JpaRepository<BookDTO, Integer>, BookRepository {
    
    public List<BookDTO> findAll();
    
    @Query("SELECT b FROM BookDTO b WHERE " +
       "(:name IS NULL OR :name = '' OR LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
       "(:author IS NULL OR :author = '' OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))) AND " +
       "(:maxPrice IS NULL OR :maxPrice <= 0.0 OR b.price <= :maxPrice)")
    List<BookDTO> findByFilters(String name, String author, Double maxPrice);

    public Optional<BookDTO> findById(int id);

    // Entiende que tiene que buscar por el nombre ya que la función se llama "findBy" y el nombre del atributo es "name"
    // Si no fuera así habría que usar la anotación @Query como en el caso de findByPartialName
    // @Query(value="SELECT b FROM BookDTO b WHERE b.name = :name")
    public List<BookDTO> findByName(String name);
 
    // En este caso no existe ninguna columna llamada "partialName", por lo que hay que usar la anotación @Query
    @Query(value="SELECT b FROM BookDTO b WHERE b.name LIKE %:name%")
    public List<BookDTO> findByPartialName(String name);

    // Lo mismo que en el caso anterior, no existe ninguna columna llamada "partialName", por lo que hay que usar la anotación
    @Query(value="SELECT count(*) FROM BookDTO b WHERE b.name LIKE %:name%")
    public Integer countByPartialName(String name);

    // MEJOR OPCIÓN:
    // Crear una función add y otra update para solo preguntar 1 vez si existe el book desde el controlador
    // y no 2 veces como sería con save. Ya que save hace un findById antes de hacer el insert o update.

    @Transactional
    public BookDTO save(BookDTO book);
    public void deleteById(int id);
}
