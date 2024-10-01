package com.uade.tpo.libreria.tpolibreria.service;

import com.uade.tpo.libreria.tpolibreria.entity.Image;
import org.springframework.stereotype.Service;


@Service
public interface ImageService {
    public Image create(Image image, int isbn);

    public Image viewById(long id);
}
