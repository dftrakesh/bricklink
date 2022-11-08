package com.dft.bricklink.model;

import lombok.Data;

@Data
public class AccessCredentials {
    private String consumerKey;
    private String consumerSecret;
    private String tokenValue;
    private String tokenSecret;
}