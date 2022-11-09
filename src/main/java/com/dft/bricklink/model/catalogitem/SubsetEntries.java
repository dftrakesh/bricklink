package com.dft.bricklink.model.catalogitem;

import com.dft.bricklink.model.common.DataInterface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SubsetEntries implements DataInterface {
	private Integer matchNo;
	private List<SubsetEntry> entries;
}