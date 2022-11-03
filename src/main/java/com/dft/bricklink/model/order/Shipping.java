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
public class Shipping {
    private String method;
    private String methodId;
    private String trackingNo;
    private String trackingLink;
    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDateTime dateShipped;
    private Address address;
    private Name name;
}
