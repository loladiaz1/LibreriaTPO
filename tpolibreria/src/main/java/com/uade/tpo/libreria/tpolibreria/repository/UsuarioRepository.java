package com.uade.tpo.libreria.tpolibreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.uade.tpo.libreria.tpolibreria.entity.Usuario;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    @Query(value = "select u from Usuario u where u.nombre_usuario = ?1")
    List<Usuario> findByNombre_usuario(String nombre_usuario);

    Optional<Usuario> findByMail (String mail);
}
