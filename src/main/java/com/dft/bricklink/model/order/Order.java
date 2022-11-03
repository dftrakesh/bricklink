package com.dft.bricklink.model.order;

import com.dft.bricklink.model.common.DateDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Order {
    private String orderId;
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDateTime dateOrdered;
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDateTime dateStatusChanged;
    private String sellerName;
    private String storeName;
    private String buyerName;
    private String buyerEmail;
    private Integer buyerOrderCount;
    private Boolean requireInsurance;
    private String status;
    private Boolean isInvoiced;
    private Boolean isFiled;
    private Boolean driveThruSent;
    private Boolean salesTaxCollectedByBl;
    private String remarks;
    private Integer totalCount;
    private Integer uniqueCount;
    private Double totalWeight;
    private Payment payment;
    private Shipping shipping;
    private Cost cost;
    private DispCost dispCost;
}
