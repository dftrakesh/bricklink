package com.dft.bricklink.model.catalogitem;

import com.dft.bricklink.model.common.Type;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EntryItem {
    private String no;
    private Type type;
    private String name;
    private Integer categoryId;
}