package com.dft.bricklink.model.catalogitem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubsetEntry {
    private EntryItem item;
    private Integer colorId;
    private Integer quantity;
    private Integer extraQuantity;
    private String isAlternate;
    private Boolean isCounterpart;
}