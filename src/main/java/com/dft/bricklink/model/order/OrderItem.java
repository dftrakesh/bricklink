package com.dft.bricklink.model.order;

import com.dft.bricklink.model.common.DataInterface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderItem implements DataInterface {
    private Integer inventoryId;
    private Item item;
    private Integer colorId;
    private String colorName;
    private Integer quantity;
    private String newOrUsed;
    private String completeness;
    private Double unitPrice;
    private Double unitPriceFinal;
    private Double dispUnitPrice;
    private Double dispUnitPriceFinal;
    private String currencyCode;
    private String dispCurrencyCode;
    private String remarks;
    private String description;
    private Double weight;
}