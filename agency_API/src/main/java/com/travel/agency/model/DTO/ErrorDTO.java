package com.travel.agency.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {

    private int status;
    private String message;
    private String exceptionType;

    public ErrorDTO(int status, String message, String exceptionType) {
        this.status = status;
        this.message = message;
        this.exceptionType = exceptionType;
    }

}
