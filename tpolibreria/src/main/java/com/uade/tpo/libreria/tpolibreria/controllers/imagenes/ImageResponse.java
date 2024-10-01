package com.uade.tpo.libreria.tpolibreria.controllers.imagenes;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageResponse {
    private Long id;
    private String file;
}
