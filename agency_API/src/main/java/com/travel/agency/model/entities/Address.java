package com.travel.agency.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {

    private String street;
    private Integer streetNumber;
    private String city;
    private String state;
    private String zipCode;
    private String country;


}

