package com.dft.bricklink.model.notification;

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
public class Notification {

    private String eventType;
    private Integer resourceId;

    @JsonDeserialize(using = DateDeserializer.class)
    private LocalDateTime timestamp;
}