package com.uade.tpo.libreria.tpolibreria.repository;
 
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.uade.tpo.libreria.tpolibreria.entity.Libro;
 
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
 
    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE %?1%")
    List<Libro> findByTituloContaining(String titulo);
 
    @Query("SELECT l FROM Libro l JOIN l.autor a WHERE a LIKE %?1%")
    List<Libro> findByAutorContaining(String autor);
 
    @Query("SELECT l FROM Libro l WHERE l.editorial LIKE %?1%")
    List<Libro> findByEditorialContaining(String editorial);
 
    @Query("SELECT l FROM Libro l WHERE l.idioma LIKE %?1%")
    List<Libro> findByIdiomaContaining(String idioma);

    @Modifying
    @Query("DELETE FROM Libro l WHERE l.isbn = ?1")
    void deleteByIsbn(Long isbn);
}