package com.travel.agency.model.DTO.image;

public record ImageDTO(
        String filename,
        String imageType,
        byte[] imageData
) {
}
