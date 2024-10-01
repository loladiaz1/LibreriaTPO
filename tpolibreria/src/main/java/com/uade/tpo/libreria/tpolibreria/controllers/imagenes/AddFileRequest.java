package com.uade.tpo.libreria.tpolibreria.controllers.imagenes;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddFileRequest {
    private String name;
    private MultipartFile file;
    private int isbn;
}
