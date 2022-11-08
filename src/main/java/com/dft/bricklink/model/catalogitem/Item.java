package com.dft.bricklink.model.catalogitem;

import com.dft.bricklink.model.common.DataInterface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Item implements DataInterface{
    private String no;
    private String name;
    private String type;
    private Integer categoryId;
    private String alternateNo;
    private String imageUrl;
    private String thumbnailUrl;
    private Double weight;
    private String dimX;
    private String dimY;
    private String dimZ;
    private Integer yearReleased;
    private String description;
    private Boolean isObsolete;
    private String languageCode;
}
