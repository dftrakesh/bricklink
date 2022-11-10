package com.dft.bricklink.model.catalogitem;

import com.dft.bricklink.model.common.DataInterface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PriceGuide implements DataInterface {
	private BasicItem item;
	private String newOrUsed;
	private String currencyCode;
	private Double minPrice;
	private Double maxPrice;
	private Double avgPrice;
	private Double qtyAvgPrice;
	private String unitQuantity;
	private String totalQuantity;
	private ArrayList<PriceDetail> priceDetail;
}