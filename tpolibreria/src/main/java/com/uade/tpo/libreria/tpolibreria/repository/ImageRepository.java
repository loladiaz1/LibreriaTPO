package com.uade.tpo.libreria.tpolibreria.repository;

import com.uade.tpo.libreria.tpolibreria.entity.Image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
