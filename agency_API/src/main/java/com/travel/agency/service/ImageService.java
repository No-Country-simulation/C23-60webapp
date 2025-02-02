package com.travel.agency.service;

import com.travel.agency.model.entities.Image;
import com.travel.agency.model.entities.TravelBundle;
import com.travel.agency.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Base64;
import java.util.List;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image createImage(MultipartFile multipartFile, TravelBundle travelBundle) throws IOException {
       return new Image(
               multipartFile.getOriginalFilename(),
               multipartFile.getContentType(),
               Base64.getEncoder().encodeToString(multipartFile.getBytes()),
               travelBundle);
    }

    public List<Image> getAllImages(){
        return this.imageRepository.findAll();
    }

    @Transactional
    public void deteleImage(Long id){
        this.imageRepository.deleteById(id);
    }

    public Image getImageById(Long id){
        return this.imageRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Image not found"));
    }

    public Image getImageByName(String imageName) throws IOException {
        return this.imageRepository.findByFilename(imageName).orElseThrow(()->new IOException("Image not found"));
    }

    public byte[] decodeImage(Image img){
        return Base64.getDecoder().decode(img.getImageData());
    }

}
