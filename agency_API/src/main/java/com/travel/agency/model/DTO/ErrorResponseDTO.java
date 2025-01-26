package com.travel.agency.model.DTO;


public record ErrorResponseDTO(
        int status,
        String message,
        String exceptionType
) {

}
