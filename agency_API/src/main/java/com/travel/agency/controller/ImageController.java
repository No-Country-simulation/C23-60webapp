package com.travel.agency.controller;

import com.travel.agency.model.entities.Image;
import com.travel.agency.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public ResponseEntity<?> getImages(){
        List<Image> images = this.imageService.getAllImages();
        String imgType = images.stream().map(Image::getContentType).toString();
        MediaType mediaType = MediaType.valueOf(imgType);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(mediaType)
                .body(images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id){
        Image img = this.imageService.getImageById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(img.getContentType()))
                .body(img);
    }

}
