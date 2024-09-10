package com.uade.tpo.libreria.tpolibreria.controllers.imagenes;

import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.libreria.tpolibreria.entity.Image;
import com.uade.tpo.libreria.tpolibreria.service.ImageService;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("images")
public class ImagesController {
    @Autowired
    private ImageService imageService;

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<ImageResponse> displayImage(@RequestParam("id") long id) throws IOException, SQLException {
        Image image = imageService.viewById(id);
        String encodedString = Base64.getEncoder()
                .encodeToString(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().body(ImageResponse.builder().file(encodedString).id(id).build());
    }

    @PostMapping()
    public String addImagePost(AddFileRequest request) throws IOException, SerialException, SQLException {
        byte[] bytes = request.getFile().getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        imageService.create(Image.builder().image(blob).build(), request.getIsbn());
        return "created";
    }
}
