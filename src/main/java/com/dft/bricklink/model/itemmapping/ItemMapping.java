package com.dft.bricklink.model.itemmapping;

import com.dft.bricklink.model.common.DataInterface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemMapping implements DataInterface {
    private Item item;
    private Integer colorId;
    private String colorName;
    private String elementId;
}