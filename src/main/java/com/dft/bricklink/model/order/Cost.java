package com.dft.bricklink.model.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Cost {
    private String currencyCode;
    private Double subtotal;
    private Double grandTotal;
    private Double salesTaxCollectedByBl;
    private Double finalTotal;
    private Double etc1;
    private Double etc2;
    private Double insurance;
    private Double shipping;
    private Double credit;
    private Double coupon;
    private Double vatRate;
    private Double vatAmount;
}
