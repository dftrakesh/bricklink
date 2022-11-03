package com.dft.bricklink.model.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Address {
    private String full;
    private String address1;
    private String address2;
    private String countryCode;
    private String city;
    private String state;
    private String postalCode;
    private String phoneNumber;
}
